package userInfoControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import userInfo.Medicine;
import CSV.MedicineCSVOperator;
import CSV.AppointmentCSVOperator;

import java.io.*;
import java.util.*;

/**
 * The {@code PharmacistCtrl} class provides functionality to manage appointments, 
 * appointment outcome records, and medication inventory for a pharmacist in a hospital.
 * This includes viewing and updating prescription status, submitting replenish requests, 
 * and saving updated information to CSV files.
 */
public class PharmacistCtrl implements AppointmentOutcomeRecordCtrl, ISubmitReplenishRequest, IMedicineView{
    /** The hospital ID associated with the pharmacist. */
	private String hospitalID;
    
	/** List of appointments. */
	private List<Appointment> appointments = new ArrayList<>();
    
	/** List of appointment outcome records. */
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	
    /** List of medicines. */
	private List<Medicine> medicines = new ArrayList<>();

	private int counter = 0; 
	
	private AppointmentCSVOperator appointmentoperator = new AppointmentCSVOperator();
	private MedicineCSVOperator medicineoperator = new MedicineCSVOperator();
	
	/**
     * Constructor for the {@code PharmacistCtrl} class.
     *
     * @param hospitalID the ID of the hospital
     */
	public PharmacistCtrl(String hospitalID) {
		this.hospitalID = hospitalID;
		ArrayList<String> tempData = new ArrayList<>();
		
		tempData = appointmentoperator.readFile(null, 2);
		
		for(String i: tempData) {
			String[] temp = appointmentoperator.splitCommaCSVLine(i);
					
			 Appointment appointment = new Appointment(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
			 AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(temp[0]), temp[4], temp[6], temp[7].split("\\s*,\\s*"), temp[8], temp[9]);

		     this.appointments.add(appointment);
		     this.appointmentsOutcomeRecord.add(appointmentOutcomeRecord);
		}
		
		tempData = new ArrayList<>();
		tempData = medicineoperator.readFile(null, 2);
		
		for(String i: tempData) {
			String[] temp = medicineoperator.splitCommaCSVLine(i);
 			
			ArrayList<String> submitter = new ArrayList<>(Arrays.asList(temp[5].split("\\s*,\\s*")));
			
			Medicine medicine = new Medicine(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3], Integer.parseInt(temp[4]), submitter, temp[6]);

	        this.medicines.add(medicine);
		}
	}

	/**
     * Displays appointment outcome records with pending prescription statuses.
     */
	public void viewAppointmentOutcomeRecord() {		
    	int counter = 0;
		System.out.println("================================================================================");

        // Check if Prescription Status is empty or unfulfilled
		for (int i = 0; i < this.appointmentsOutcomeRecord.size(); i++) {	
			if (appointmentsOutcomeRecord.get(i).getPrescriptionStatus().equals("Pending")) {
				if (counter == 0){
			   		System.out.println("This is the list of appointments with unfulfilled medication prescription orders.");
			   		counter = 1;
				}
				// Print the appointment outcome record details (modify based on your class structure)
				System.out.println("Appointment ID: " + appointmentsOutcomeRecord.get(i).getAppointmentID()); // Example
				System.out.println("Type of Service: " + appointmentsOutcomeRecord.get(i).getTypeOfService());
				System.out.print("Prescribed Medications: ");
				for (int j = 0; j < appointmentsOutcomeRecord.get(i).getPrescribedMedications().length; j++){
					System.out.print(appointmentsOutcomeRecord.get(i).getPrescribedMedications()[j]);
					if (j != appointmentsOutcomeRecord.get(i).getPrescribedMedications().length - 1)
							System.out.print(", ");
				}
				System.out.println(" ");
				System.out.println("Prescription Status: " + appointmentsOutcomeRecord.get(i).getPrescriptionStatus());
				System.out.println("Consultation Notes: " + appointmentsOutcomeRecord.get(i).getConsultationNotes());
				System.out.println("------------------------------------------------------------------------");
			}
		}
		if (counter == 0) {
	   		System.out.println("All appointments have fufilled medication prescription orders.");
		}
		System.out.println("================================================================================");		
	}
	
	/**
     * Updates the prescription status of an appointment to fulfilled.
     */
	public void updateAppointmentOutcomeRecord() {
		int Found = 0;
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input the appointment ID for the appointment to change: ");
		int appointmentID = sc.nextInt();
		for (int i = 0; i < appointmentsOutcomeRecord.size(); i++) {
			if (appointmentsOutcomeRecord.get(i).getAppointmentID() == appointmentID && appointmentsOutcomeRecord.get(i).getPrescriptionStatus().equals("Pending")) {
				String[] prescribedMedicines = appointmentsOutcomeRecord.get(i).getPrescribedMedications();
				for (int j = 0; j < prescribedMedicines.length; j++)
				{
					Found = 0;
					for (int k = 0; k < medicines.size(); k++) {
						if (prescribedMedicines[j].equals(medicines.get(k).getName())){
							if (medicines.get(k).getStockLevel() > 0) {
								Found = 1;
								break;
							}
							else {
								System.out.println(prescribedMedicines[j] + " is out of stock.");
								return;
							}
						}
					}
					if (Found == 0) {
						System.out.println(prescribedMedicines[j] + " not found.");
						return;
					}
				}
				
				for (int j = 0; j < prescribedMedicines.length; j++)
				{
					for (int k = 0; k < medicines.size(); k++) {
						if (prescribedMedicines[j].equals(medicines.get(k).getName())){
							medicines.get(k).setStockLevel(medicines.get(k).getStockLevel() - 1);
							break;
						}
					}
				}
				appointmentsOutcomeRecord.get(i).updatePrescriptionStatus();
				System.out.println("Updated Prescription Status for appointment ID " + appointmentID + ": " + appointmentsOutcomeRecord.get(i).getPrescriptionStatus() );
				return;
			}
		}
		System.out.println("Appointment ID " + appointmentID + " not found or prescription status not pending ");
	}
	
	/**
     * Displays the medication inventory.
     */
	public void viewMedicationInventory() {
		System.out.println("================================================================================");
   		System.out.println("List of medicines");
		System.out.println("");
   		for (int i = 0; i < this.medicines.size(); i++) {
   			System.out.println("Medicine Name: " + medicines.get(i).getName());
   			System.out.println("Stock Level: " + medicines.get(i).getStockLevel());
   			System.out.println("Low Stock Level Alert: " + medicines.get(i).getLowStockLevelAlert());
   			System.out.println("Replenish Request Status: " + medicines.get(i).getReplenishRequestStatus());
   			System.out.println("Replenish Request Amount: " + medicines.get(i).getReplenishRequestAmount());
   			System.out.println("Replenish Request Submitted By: " + String.join(", ", medicines.get(i).getReplenishRequestSubmittedBy()));
   			System.out.println("Replenish Request Approved By: " + medicines.get(i).getReplenishRequestApprovedBy());
			System.out.println("--------------------------");
   		}
		System.out.println("================================================================================");
	}
	
	/**
     * Submits a request to replenish medicine stock.
     */
	public void submitReplenishRequest() {
		System.out.println("Please input the medicine name for replenish:");
		Scanner sc = new Scanner(System.in);
		String medicineName = sc.next();
		for (int i = 0; i < this.medicines.size(); i++) {
   			if (medicineName.equalsIgnoreCase(medicines.get(i).getName())){
   				if (medicines.get(i).getReplenishRequestSubmittedBy().get(0).equals("NA")) {
   					System.out.println("Please input the replenish request amount: ");
   	   				int amount = sc.nextInt();
	   	   			System.out.println("Please type 'CONFIRM' if you insist on requesting: ");
	                String confirmation = sc.next();
	                if ("CONFIRM".equals(confirmation)) {
	                	medicines.get(i).clearReplenishRequestSubmittedBy();
	   	   				medicines.get(i).setReplenishRequestStatus("Pending");
	   	   				medicines.get(i).setReplenishRequestAmount(amount);
	   	   				medicines.get(i).setReplenishRequestSubmittedBy(hospitalID);
	   	   				return;
	                } else {
	                    System.out.println("Request canceled.");
	                    return;
	                }
   	   				
   				}
   				else if (medicines.get(i).getReplenishRequestStatus().equals("Pending")){
   					if (medicines.get(i).getReplenishRequestSubmittedBy().contains(hospitalID)) {
   	   					System.out.println("You have already submitted a request previously. Please wait for the administrator to approve it before you can submit another request.");
   	   					return;
   					}
   					System.out.println("Please input the replenish request amount: ");
   	   				int amount = sc.nextInt();
   	   				int previousRequest = medicines.get(i).getReplenishRequestAmount();
	   	   			System.out.println("Please type 'CONFIRM' if you insist on requesting: ");
	                String confirmation = sc.next();
	                if ("CONFIRM".equals(confirmation)) {
	                	medicines.get(i).clearReplenishRequestSubmittedBy();
	   	   				medicines.get(i).setReplenishRequestStatus("Pending");
	   	   				medicines.get(i).setReplenishRequestAmount(amount + previousRequest);
	   	   				medicines.get(i).setReplenishRequestSubmittedBy(hospitalID);
	   	   				return;
	                } else {
	                    System.out.println("Request canceled.");
	                    return;
	                }
   				}
   				else if (medicines.get(i).getReplenishRequestStatus().equals("Approved")){
   					System.out.println("Please input the replenish request amount: ");
   	   				int amount = sc.nextInt();
	   	   			System.out.println("Please type 'CONFIRM' if you insist on requesting: ");
	                String confirmation = sc.next();
	                if ("CONFIRM".equals(confirmation)) {
	                	medicines.get(i).clearReplenishRequestSubmittedBy();
	   	   				medicines.get(i).setReplenishRequestStatus("Pending");
	   	   				medicines.get(i).setReplenishRequestAmount(amount);
	   	   				medicines.get(i).setReplenishRequestSubmittedBy(hospitalID);
	   	   				medicines.get(i).setReplenishRequestApprovedBy("NA");
	   	   				return;
	                } else {
	                    System.out.println("Request canceled.");
	                    return;
	                }
	   			}
   				System.out.println("Replenish request for " + medicineName + " submitted");
   				return;
   			}
   		}
		System.out.println("Medicine " + medicineName + " not found");
	}
	
	
	/**
     * Updates the medicine entity records by preparing data from the `medicines` list
     * and formatting it into CSV rows. Each medicine's details, including replenish
     * request information, are processed and converted into a CSV-compatible format.
     *
     * Special handling is applied to the `ReplenishRequestSubmittedBy` field to format 
     * the list as a comma-separated string enclosed in double quotes.
     *
     * @return {@code true} if the CSV file was successfully updated; {@code false} otherwise.
     */
	public boolean updateMedicineEntity() {
		ArrayList<String> dataStore = new ArrayList<>(); //use to pass the entity class to the database
		
		for(Medicine i: medicines) {
			String temp = i.getReplenishRequestSubmittedBy().toString();
			temp = temp.replace("[", "").replace("]", "").replace(" ", "");
			
			dataStore.add(String.format("%s,%s,%s,%s,%s,%s,%s", i.getName(), i.getStockLevel(), i.getLowStockLevelAlert(), i.getReplenishRequestStatus(), i.getReplenishRequestAmount(), "\"" + temp + "\"", i.getReplenishRequestApprovedBy()));
		}
		
		//since it is update the whole CSV file, using the same way like the admin update the CSV file
		if(medicineoperator.updateCSVForAdmin(dataStore)) return true;
		return false;
	}
	
	/**
	 * Updates the appointment entity records by combining data from the `appointments` list
	 * and the corresponding `appointmentsOutcomeRecord` list. The combined data is formatted
	 * into CSV rows and passed to the CSV updater.
	 *
	 * Each record includes details such as appointment ID, patient ID, doctor ID, status,
	 * date and time, type of service, prescribed medications, prescription status, and consultation notes.
	 *
	 * @return {@code true} if the CSV file was successfully updated; {@code false} otherwise.
	 */
	public boolean updateAppointmentEntity() {
		ArrayList<String> dataStore = new ArrayList<>();
		
		// Iterate through appointments and find corresponding outcome records
		for(int i = 0; i < appointments.size(); i++) {
			Appointment a = appointments.get(i);//use to hold the data of the appointment temporary
			AppointmentOutcomeRecord b = appointmentsOutcomeRecord.get(i);// use to hold the data of the appointment outcome record for temporary
			
			String prescribedMedications = "\"" + String.join(", ", b.getPrescribedMedications()) + "\"";
			String consultationNotes = "\"" + b.getConsultationNotes() + "\"";
			
			dataStore.add(String.format("%d,%s,%s,%s,%s,%s,\"%s\",%s,%s,\"%s\"",
                    a.getAppointmentID(),
                    a.getPatientID(),
                    a.getDoctorID(),
                    a.getAppointmentStatus(),
                    a.getDateOfAppointment(),
                    a.getTimeOfAppointment(),
                    b.getTypeOfService(),
                    prescribedMedications,
                    b.getPrescriptionStatus(),
                    consultationNotes));	 
		}
		
		if(appointmentoperator.updateCSVForAdmin(dataStore)) return true;
		return false;
	}
}