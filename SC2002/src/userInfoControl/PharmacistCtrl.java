package userInfoControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import userInfo.Medicine;

import java.io.*;
import java.util.*;

public class PharmacistCtrl implements AppointmentOutcomeRecordCtrl, IReplenishRequest, IMedicineView{
	private List<Appointment> appointments = new ArrayList<>();
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	private List<Medicine> medicines = new ArrayList<>();

	private int counter = 0; 
	public PharmacistCtrl() {
		try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = splitCSVLine(line);
				if (counter == 0){
					counter = 1;
					continue;
				}
		        Appointment appointment = new Appointment(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);
				AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(data[0]), data[4], data[6], data[7].split("\\s*,\\s*"), data[8], data[9]);

		        this.appointments.add(appointment);
				this.appointmentsOutcomeRecord.add(appointmentOutcomeRecord);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("./Medicine_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = splitCSVLine(line);
				if (counter == 0){
					counter = 1;
					continue;
				}
		        Medicine medicine = new Medicine(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));

		        this.medicines.add(medicine);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	// Split a CSV line into the proper format (used for Appointment)
    private String[] splitCSVLine(String line) 
    {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) 
        {
            char currentChar = line.charAt(i);
            
            if (currentChar == '"') 
            {
                inQuotes = !inQuotes; 
            } 
            else if (currentChar == ',' && !inQuotes) 
            {
                tokens.add(currentToken.toString());
                currentToken.setLength(0);
            } 
            else 
            {
                currentToken.append(currentChar);
            }
        }
        
        tokens.add(currentToken.toString());
        return tokens.toArray(new String[0]);
    }

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
				System.out.println("Prescription Status: " + appointmentsOutcomeRecord.get(i).getPrescriptionStatus());
				System.out.print("Prescribed Medications: ");
				for (int j = 0; j < appointmentsOutcomeRecord.get(i).getPrescribedMedications().length; j++){
					System.out.print(appointmentsOutcomeRecord.get(i).getPrescribedMedications()[j]);
					System.out.print(", ");
				}
				System.out.println(" ");
				System.out.println("Consultation Notes: " + appointmentsOutcomeRecord.get(i).getConsultationNotes());
				System.out.println("------------------------------------------------------------------------");
			}
		}
		if (counter == 0) {
	   		System.out.println("All appointments have fufilled medication prescription orders.");
		}
		System.out.println("================================================================================");		
	}
	
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
							if (medicines.get(k).getInitialStock() > 0) {
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
							medicines.get(k).setInitialStock(medicines.get(k).getInitialStock() - 1);
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
	
	public void viewMedicationInventory() {
		System.out.println("================================================================================");
   		System.out.println("List of medicines");
		System.out.println("");
		System.out.println("--------------------------");
		for (int i = 0; i < this.medicines.size(); i++) {
   			System.out.println("Medicine name: " + medicines.get(i).getName());
   			System.out.println("Initial Stock: " + medicines.get(i).getInitialStock());
   			System.out.println("Low Stock Level Alert: " + medicines.get(i).getLowStockLevelAlert());
   			System.out.println("Replenish Request Status: " + medicines.get(i).getReplenishRequest());
			System.out.println("--------------------------");
   		}
		System.out.println("================================================================================");

	}
	
	public void submitReplenishRequest() {
		System.out.println("Please input the medicine name for replenish:");
		Scanner sc = new Scanner(System.in);
		String medicineName = sc.next();
		for (int i = 0; i < this.medicines.size(); i++) {
   			if (medicineName.equalsIgnoreCase(medicines.get(i).getName())){
   				medicines.get(i).setReplenishRequest();
   				System.out.println("Replenish request for " + medicineName + " submitted");
   				return;
   			}
   		}
		System.out.println("Medicine " + medicineName + " not found");
	}
	
	public void EntityUpdate() {
		saveMedicinesToCSV();
		saveAppointmentsToCSV();
	}
	
	// Method to save updated medicines to Medicine_List.csv
		private void saveMedicinesToCSV() {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Medicine_List.csv"))) {
				// Write header for CSV file
				writer.write("Medicine Name,Stock,Low Stock Level Alert\n");
				for (Medicine medicine : medicines) {
					writer.write(String.format("%s,%d,%d\n",
						medicine.getName(),
						medicine.getInitialStock(),
						medicine.getLowStockLevelAlert()));
				}
				System.out.println("Medicine inventory updated in Medicine_List.csv.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Method to save updated appointment records to Appointment_List.csv
		private void saveAppointmentsToCSV() {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Appointment_List.csv"))) {
		        // Write headers for the CSV file
		        writer.write("Appointment ID, Patient ID, Doctor ID, Appointment Status, Date of Appointment, Time of Appointment, Type of Service, Prescribed Medications, Prescription Status, Consultation Notes\n");

		        // Iterate through appointments and find corresponding outcome records
		        for (int i = 0; i < appointments.size(); i++) {
		            Appointment a = appointments.get(i);
		            AppointmentOutcomeRecord b = appointmentsOutcomeRecord.get(i);
	                String prescribedMedications = "\"" + String.join(", ", b.getPrescribedMedications()) + "\"";
		            
		            writer.write(String.format("%d,%s,%s,%s,%s,%s,\"%s\",%s,%s,\"%s\"\n",
	                    a.getAppointmentID(),
	                    a.getPatientID(),
	                    a.getDoctorID(),
	                    a.getAppointmentStatus(),
	                    a.getDateOfAppointment(),
	                    a.getTimeOfAppointment(),
	                    b.getTypeOfService(),
	                    prescribedMedications,
	                    b.getPrescriptionStatus(),
	                    b.getConsultationNotes()));	 
		        }

		        System.out.println("Appointments and outcomes updated in Appointment_List.csv.");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
}