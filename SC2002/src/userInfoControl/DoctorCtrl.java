package userInfoControl;

import CSV.MedicalRecordCSVOperator;
import CSV.DoctorCSVOperator;
import CSV.AppointmentCSVOperator;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import userInfo.*;
import userInfoControl.*;

/**
 * Controller class that handles the doctor's medical record, appointment scheduling,
 * personal schedule management, and other related functions.
 * Implements the MedicalRecordCtrl, IDocAppointmentCtrl, ISchedule, and AppointmentOutcomeRecordCtrl interfaces.
 */
public class DoctorCtrl implements MedicalRecordCtrl, IDocAppointmentCtrl, ISchedule, AppointmentOutcomeRecordCtrl{
	private String doctorID = null;
	private String[] myPatientID = null;
	private PersonalSchedule schedule = null;
	private DoctorCSVOperator csv = new DoctorCSVOperator();
	
	/**
     * Constructor that initializes the DoctorCtrl with the given hospital ID.
     * @param hospitalID the unique identifier for the doctor at the hospital
     */
	public DoctorCtrl(String hospitalID) {
		this.doctorID = hospitalID;
		this.myPatientID = getPatientList(hospitalID);
		this.schedule = new PersonalSchedule(hospitalID);
	}
	
	/**
     * Retrieves the list of patient IDs assigned to the doctor based on the hospital ID.
     * @param doctorID the unique identifier for the doctor
     * @return an array of patient IDs assigned to the doctor
     */
	public static String[] getPatientList(String doctorID) {
	    ArrayList<String> patientIDs = new ArrayList<>();
	    String filePath = "./Patient_List.csv";  

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        // Skip header line if CSV has one
	        br.readLine();
	        
	        while ((line = br.readLine()) != null) {
	            String[] values = line.split(",");  
	            
	            String csvDoctorID = values[10].trim();  //DoctorID in 11th column
	            if (csvDoctorID.equals(doctorID)) {
	                String patientID = values[2].trim();  // Patient ID in 3rd column
	                patientIDs.add(patientID);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // Convert ArrayList to an array
	    return patientIDs.toArray(new String[0]);
	}
	
	/**
     * Displays the list of patient IDs assigned to the doctor.
     */
	public void viewPatientIDs() {
	    if (myPatientID.length == 0) {
	        System.out.println("No patients found under your record.");
	    } else {
	        // Print each patient ID
	    	System.out.println("These are patients under your record:");
	        for (String patientID : myPatientID) {
	            System.out.println("- " + patientID);
	        }
	    }
	}

	/**
     * Allows the doctor to view a patient's medical record based on the patient ID.
     */
	public void showMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		if (myPatientID.length == 0) return;
		System.out.println("Enter the patient ID to view: ");
		String patientID = sc.next();
		//patient ID found in the doctor's record
		if (Arrays.stream(myPatientID)
	              .anyMatch(ID -> ID.equals(patientID))) {
		    MedicalRecord medicalRecord = new MedicalRecord(patientID);
		    
		    System.out.println("Show medical record for patient");
		    System.out.println("===============================");
		    System.out.println("Patient ID		| " + medicalRecord.getPatientID());
		    System.out.println("Name			| " + medicalRecord.getName());
		    System.out.println("Gender			| " + medicalRecord.getGender());
		    System.out.println("Phone No.		| " + medicalRecord.getPhoneNumber());
		    System.out.println("Email Address	| " + medicalRecord.getEmailAddress());
			System.out.println("Blood Type		| " + medicalRecord.getBloodType());
			System.out.println("Doctor In Charge| " + medicalRecord.getDoctor());
		    System.out.println("===============================");
		    System.out.println("Past Diagnoses and Treatments:");
		    System.out.println("==============================");

		    for (String record : medicalRecord.getPastDiagnosesAndTreatment()) {
		        String[] parts = record.split(";\\s*");  // Split by ';' with optional whitespace

		        if (parts.length == 3) {
		            String diagnose = parts[0].replace("[", "").replace("]", "").trim();
		            String prescription = parts[1].trim();
		            String plan = parts[2].replace("[", "").replace("]", "").trim();

		            System.out.println("Diagnosis: " + diagnose);
		            System.out.println("Prescription: " + prescription);
		            System.out.println("Plan: " + plan);
		            System.out.println("------------------------------");
		        } else {
		            System.out.println("Error: Invalid record format.");
		        }
		    }
		}
		//if patient id not found
		else {
			System.out.println("Patient ID not found in your record!");
		}
	}

	/**
     * Allows the doctor to update a patient's medical record by adding new diagnoses,
     * prescriptions, and treatment plans.
     */
	public void updateMedicalRecord() { 
		if (myPatientID.length == 0) return;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the patient ID to update: ");
		String patientID = sc.next();
		if (Arrays.stream(myPatientID)
	              .anyMatch(ID -> ID.equals(patientID))) {
			MedicalRecord medicalRecord = new MedicalRecord(patientID);
			System.out.println("You are now adding new diagnoses, presecription, and treatment plan under the patient");
			System.out.println("Enter new diagnoses");
			String diagnose = sc.next();
			System.out.println("Enter prescriptions");
			String prescription = sc.next();
			System.out.println("Enter treatment plans");
			String plan = sc.next();
		
			medicalRecord.addPastDiagnosisAndTreatment(diagnose, prescription, plan);
			System.out.println("New record added successfully");
			}
		else {
			System.out.println("Patient ID not found in your record!");
			}
	}
	
	/**
     * Displays the doctor's personal schedule.
     */
	public void viewPersonalSchedule() {
		System.out.println("===============================================================================================================================================");
		System.out.println("                                                           Showing Personal Schedule                                                           ");
		System.out.println("===============================================================================================================================================\n");
		this.schedule.viewSchedule();
		System.out.println("\n===============================================================================================================================================");
	}
	
	/**
     * Allows the doctor to set their availability for specific times and dates.
     */
	public void setAvailability() 
	{
		int choice = -1, day = -1, start = -1, end = -1;
		Character task = 'F';
		Scanner sc = new Scanner(System.in);
		
		while (choice == -1)
		{
			while (day < 0 || day > 7)
			{
				System.out.println("===========================================");
				System.out.println("              Day of the Week              ");
				System.out.println("===========================================");
				System.out.println("1. Monday");
				System.out.println("2. Tuesday");
				System.out.println("3. Wednesday");
				System.out.println("4. Thursday");
				System.out.println("5. Friday");
				System.out.println("6. Saturday");
				System.out.println("7. Sunday");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				day = sc.nextInt();
				switch (day)
				{
					case 1, 2, 3, 4, 5, 6, 7:
						day--;
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
			}
			
			while (start < 0 || start > 16)
			{
				System.out.println("===========================================");
				System.out.println("           Starting Time Session           ");
				System.out.println("===========================================");
				System.out.println("1.  10:00 - 10:30\t 9.  14:00 - 14:30");
				System.out.println("2.  10:30 - 11:00\t 10. 14:30 - 15:00");
				System.out.println("3.  11:00 - 11:30\t 11. 15:00 - 15:30");
				System.out.println("4.  11:30 - 12:00\t 12. 15:30 - 16:00");
				System.out.println("5.  12:00 - 12:30\t 13. 16:00 - 16:30");
				System.out.println("6.  12:30 - 13:00\t 14. 16:30 - 17:00");
				System.out.println("7.  13:00 - 13:30\t 15. 17:00 - 17:30");
				System.out.println("8.  13:30 - 14:00\t 16. 17:30 - 18:00");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				start = sc.nextInt();
				switch (start)
				{
					case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16:
						start--;
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
			}
			
			while (end < 0 || end > 16)
			{
				System.out.println("===========================================");
				System.out.println("            Ending Time Session            ");
				System.out.println("===========================================");
				System.out.println("1.  10:00 - 10:30\t 9.  14:00 - 14:30");
				System.out.println("2.  10:30 - 11:00\t 10. 14:30 - 15:00");
				System.out.println("3.  11:00 - 11:30\t 11. 15:00 - 15:30");
				System.out.println("4.  11:30 - 12:00\t 12. 15:30 - 16:00");
				System.out.println("5.  12:00 - 12:30\t 13. 16:00 - 16:30");
				System.out.println("6.  12:30 - 13:00\t 14. 16:30 - 17:00");
				System.out.println("7.  13:00 - 13:30\t 15. 17:00 - 17:30");
				System.out.println("8.  13:30 - 14:00\t 16. 17:30 - 18:00");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				end = sc.nextInt();
				switch (end)
				{
					case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16:
						end--;
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
			}
			
			while (choice <= 0 || choice > 16)
			{
				System.out.println("===========================================");
				System.out.println("                Task To Do                 ");
				System.out.println("===========================================");
				System.out.println("1: Free Slot (Available for booking)");
				System.out.println("2: Meeting");
				System.out.println("3: Surgical Operations");
				System.out.println("4: Break Time");
				System.out.println("5: Training Session");
				System.out.println("6: Personal Leave");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				choice = sc.nextInt();
				switch (choice)
				{
					case 1:
						task = 'F';
						break;
					case 2:
						task = 'M';
						break;
					case 3:
						task = 'S';
						break;
					case 4:
						task = 'B';
						break;
					case 5:
						task = 'T';
						break;
					case 6:
						task = 'P';
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
			}
			
			this.schedule.editSchedule(day, start, end, task);
			System.out.println("===========================================");
			System.out.println("Do you wish to keep editing your schedule?");
			System.out.println("===========================================");
			System.out.println("1: Keep Editing");
			System.out.println("2: Quit");
			choice = sc.nextInt();
			switch (choice)
			{
				case 1:
					choice = -1;
					day = -1;
					start = -1;
					end = -1;
					break;
			}
		}
		
		ArrayList<Integer> indexChanges = new ArrayList<>(Arrays.asList(9));
		ArrayList<String> changes = new ArrayList<>(Arrays.asList("","","","","","","","","",this.schedule.translateSchedule()));
		this.csv.changeSpecificInformation(doctorID, indexChanges, changes);
	}
	
	/**
     * Updates the status of an appointment (e.g., Confirmed, Canceled, Completed).
     */
	public void updateAppointmentRequest() {
	    Scanner sc = new Scanner(System.in);
	    
	    AppointmentCSVOperator appcsv = new AppointmentCSVOperator();
	    ArrayList<String> appointmentsraw = new ArrayList<>();
	    appointmentsraw = appcsv.readFile(this.doctorID, 1);
	    
	    ArrayList<Appointment> appointmentsList = new ArrayList<>();
	    
	    for (String appointment : appointmentsraw) {
	    	String[] formatted = appointment.split(";");
	    	Appointment app = new Appointment(Integer.valueOf(formatted[0]), formatted[1], formatted[2], formatted[3], formatted[4], formatted[5]);
	    	if (app.getAppointmentStatus().equals("Pending") || app.getAppointmentStatus().equals("Confirmed"))
	    	{
	    		appointmentsList.add(app);
	    	}
	    }
	    
	    System.out.println("===== Showing Appointment Requests and Status =====");
	    // Display relevant appointments
	    for (Appointment appointment : appointmentsList) {
	    	System.out.printf("Appointment ID: %d, Patient ID: %s, Status: %s, Date: %s, Time: %s\n", 
	                           appointment.getAppointmentID(), appointment.getPatientID(), appointment.getAppointmentStatus(), 
	                           appointment.getDateOfAppointment(), appointment.getTimeOfAppointment());
	    }

	    System.out.println("\nUpdating Appointment Status for Appointment ID:");
	    int appointmentID = sc.nextInt();
	    
	    System.out.println("Choose status to update:");
	    System.out.println("1. Confirmed");
	    System.out.println("2. Canceled");
	    System.out.println("3. Completed");
	    int choice = sc.nextInt();
	    
	    String status;
	    switch (choice) {
	        case 1 -> status = "Confirmed";
	        case 2 -> status = "Canceled";
	        case 3 -> status = "Completed";
	        default -> status = "Pending";
	    }

	    // Update the appointment status in the list
	    boolean updated = false;
	    for (Appointment appointment : appointmentsList) {
	        if (appointment.getAppointmentID().equals(appointmentID) && appointment.getDoctorID().equals(this.doctorID)) {  // Check patient and doctor IDs
	            appointment.setAppointmentStatus(status);
	            
	            // Delete the appointment from the doctor's personal schedule 
	            if (status.equals("Canceled") || status.equals("Completed"))
	            {
	            	String[] timeSlots = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
	        		List<String> timeSlotList = Arrays.asList(timeSlots);
	        		
	        		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");
	                LocalDate olddate = LocalDate.parse(appointment.getDateOfAppointment(), formatter2);
	                DayOfWeek olddayOfWeek = olddate.getDayOfWeek();
	                
	        		int oldday = olddayOfWeek.getValue() - 1;
	        	    int oldstart = timeSlotList.indexOf(appointment.getTimeOfAppointment().substring(0,5));
	        	    int oldend = timeSlotList.indexOf(appointment.getTimeOfAppointment().substring(6)) - 1;
	        	    System.out.println(oldday);
	        	    System.out.println(oldstart);
	        	    System.out.println(oldend);
	        		this.schedule.editSchedule(oldday, oldstart, oldend, 'F');
	        		
	        		// Update the Doctor CSV File
	        		ArrayList<Integer> indexChanges = new ArrayList<>(Arrays.asList(9));
	        		ArrayList<String> changes = new ArrayList<>(Arrays.asList("","","","","","","","","",schedule.translateSchedule()));
	        		this.csv.changeSpecificInformation(this.doctorID, indexChanges, changes);
	        		
	        		System.out.println("Personal Schedule Updated!");
	            }
	            
	            // Update the Appointment CSV File
	            if(appointment.rescheduleAppointmentInCSV()) {
	    			System.out.println("Appointment Request Updated!");
	    		}else {
	    			System.out.println("Failed to update request!");
	    		}
	            updated = true;
	            break;
	        }
	    }
	    if (!updated) {
	        System.out.println("Appointment not found for the given Patient ID and Doctor ID.");
	        return;
	    }
	}
	
	/**
	 * Displays the list of upcoming appointments that are either pending or confirmed.
	 * The list is filtered by checking the date and only shows appointments that occur in the future.
	 * Displays the appointment ID, patient ID, status, date, and time.
	 */
	public void viewUpcomingAppointment() {
		AppointmentCSVOperator appcsv = new AppointmentCSVOperator();
	    ArrayList<String> appointmentsraw = new ArrayList<>();
	    appointmentsraw = appcsv.readFile(this.doctorID, 1);
	    
	    ArrayList<Appointment> appointmentsList = new ArrayList<>();
	    
	    for (String appointment : appointmentsraw) {
	    	String[] formatted = appointment.split(";");
	    	Appointment app = new Appointment(Integer.valueOf(formatted[0]), formatted[1], formatted[2], formatted[3], formatted[4], formatted[5]);
	    	if (app.getAppointmentStatus().equals("Pending") || app.getAppointmentStatus().equals("Confirmed"))
	    	{
	    		appointmentsList.add(app);
	    	}
	    }
	    
	    // Define date format
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	    LocalDate today = LocalDate.now();
	    
	    System.out.println("===== Showing Upcoming Appointments =====");
	    // Display relevant appointments
	    boolean showed = false;
	    for (Appointment appointment : appointmentsList) {
	    	String date = appointment.getDateOfAppointment().trim();
            LocalDate appointmentDate = LocalDate.parse(date, dateFormatter);

            // Display only if the appointment date is in the future
            if (appointmentDate.isAfter(today))
            {
            	showed = true;
            	System.out.printf("Appointment ID: %d, Patient ID: %s, Status: %s, Date: %s, Time: %s\n", 
                        		   appointment.getAppointmentID(), appointment.getPatientID(), appointment.getAppointmentStatus(), 
                        		   appointment.getDateOfAppointment(), appointment.getTimeOfAppointment());
            }
	    }
	    
	    if (showed == false)
	    {
	    	System.out.print("You have no upcoming appointments!\n");
	    }
	    
	    System.out.println();
	}
	
	/**
	 * Records the outcome of completed appointments. This includes collecting the service type, prescribed medication,
	 * and consultation notes for the appointment. The system will then update the outcome record for the specified appointment.
	 */
	public void recordAppointmentOutcome() {
		Scanner sc = new Scanner(System.in);
		// File path for the appointment list
		AppointmentCSVOperator appcsv = new AppointmentCSVOperator();
	    ArrayList<String> appointmentsraw = new ArrayList<>();
	    appointmentsraw = appcsv.readFile(this.doctorID, 1);
	    
	    ArrayList<Appointment> appointmentsList = new ArrayList<>();
	    
	    for (String appointment : appointmentsraw) {
	    	String[] formatted = appointment.split(";");
	    	Appointment app = new Appointment(Integer.valueOf(formatted[0]), formatted[1], formatted[2], formatted[3], formatted[4], formatted[5]);
	    	if (app.getAppointmentStatus().equals("Completed") && formatted[6].equals("NA"))
	    	{
	    		appointmentsList.add(app);
	    	}
	    }
	    
	    System.out.println("======= Showing Appointment Requests and Status =======");
	    // Display relevant appointments
	    if (appointmentsList.isEmpty())
	    {
	    	System.out.println("You have no completed appointments pending outcome record!");
	    	return;
	    }
	    for (Appointment appointment : appointmentsList) {
	    	System.out.printf("Appointment ID: %d, Patient ID: %s, Status: %s, Date: %s, Time: %s\n", 
	                           appointment.getAppointmentID(), appointment.getPatientID(), appointment.getAppointmentStatus(), 
	                           appointment.getDateOfAppointment(), appointment.getTimeOfAppointment());
	    }

	    //choose appointment ID to update
	    System.out.println("\nUpdating Appointment Outcome for Appointment ID:");
	    int appointmentID = sc.nextInt();
		
		System.out.println("Type of service provided: ");
		String serviceType = sc.nextLine(); // Acts as a dummy
		serviceType = sc.nextLine();
		System.out.println("Enter Prescribed medication: (Write each medicine in a new line, type -1 to stop)");
		ArrayList<String> prescribedMed = new ArrayList<>();
		while (true)
		{
			String med = sc.nextLine();
			if (med.equals("-1"))
			{
				break;
			}
			prescribedMed.add(med);
		}
		String[] prescribedMedication = prescribedMed.toArray(new String[0]);
		
		System.out.println("Consultation notes: ");
		String notes = sc.nextLine();
		
		// Update the appointment outcome in the list
	    boolean updated = false;
	    for (Appointment appointment : appointmentsList) {
	        if (appointment.getAppointmentID().equals(appointmentID)) {
	        	AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(appointmentID, appointment.getDateOfAppointment(), serviceType, prescribedMedication, "Pending", notes);
	        	if (record.recordOutcomeInCSV())
	        	{
	        		System.out.println("Appointment outcome recorded successfully!");
	        	}
	        	else
	        	{
	        		System.out.println("Failed to record appointment outcome!");
	        	}
	            updated = true;
	            break;
	        }
	    }
	    if (!updated) {
	        System.out.println("Appointment not found for the given Patient ID and Doctor ID.");
	        return;
	    }
	}

	/**
	 * Displays the appointment outcome records for completed appointments.
	 * The method retrieves and shows the records of outcomes for appointments that are marked as completed.
	 */
	public void viewAppointmentOutcomeRecord() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Updates the appointment outcome record. This method allows modifications to existing outcome records.
	 * The details can include updating service type, prescribed medication, or consultation notes.
	 */
	public void updateAppointmentOutcomeRecord() {
		// TODO Auto-generated method stub
		
	}
	
}
