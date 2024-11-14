package userInfoControl;

import CSV.MedicalRecordCSVOperator;
import CSV.DoctorCSVOperator;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import userInfo.*;
import userInfoControl.*;

public class DoctorCtrl implements MedicalRecordCtrl, IDocAppointmentCtrl, ISchedule, AppointmentOutcomeRecordCtrl{
	private String doctorID;
	private String[] myPatientID;
	private PersonalSchedule schedule;
	private DoctorCSVOperator csv = new DoctorCSVOperator();
	
	public DoctorCtrl(String hospitalID) {
		this.doctorID = hospitalID;
		this.myPatientID = getPatientList(hospitalID);
		this.schedule = new PersonalSchedule(hospitalID);
	}
	
	
	
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
<<<<<<< HEAD
			System.out.println("Past Diagnoses and Treatment:");
			// Add past diagnoses and treatment
			System.out.println("===============================");
			for (String records : medicalRecord.getPastDiagnosesAndTreatment()) {
		        String[] parts = records.split(";");
		        
		        if (parts.length == 3) { // Ensure correct format
		            String diagnose = parts[0];
		            String prescription = parts[1];
		            String plan = parts[2];
		            
=======
		    System.out.println("Past Diagnoses and Treatments:");
		    System.out.println("==============================");

		    for (String record : medicalRecord.getPastDiagnosesAndTreatment()) {
		        String[] parts = record.split(";\\s*");  // Split by ';' with optional whitespace

		        if (parts.length == 3) {
		            String diagnose = parts[0].replace("[", "").replace("]", "").trim();
		            String prescription = parts[1].trim();
		            String plan = parts[2].replace("[", "").replace("]", "").trim();

>>>>>>> 2fd44b9dffc8bb101dec8d5eec58bcd5f8c79fd5
		            System.out.println("Diagnosis: " + diagnose);
		            System.out.println("Prescription: " + prescription);
		            System.out.println("Plan: " + plan);
		            System.out.println("------------------------------");
		        } else {
		            System.out.println("Error: Invalid record format.");
		        }
		    }
<<<<<<< HEAD
			
=======
>>>>>>> 2fd44b9dffc8bb101dec8d5eec58bcd5f8c79fd5
		}
		//if patient id not found
		else {
			System.out.println("Patient ID not found in your record!");
		}
	}

	
	
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
	
	
	
	public void viewPersonalSchedule() {
		System.out.println("===============================================================================================================================================");
		System.out.println("                                                           Showing Personal Schedule                                                           ");
		System.out.println("===============================================================================================================================================\n");
		this.schedule.viewSchedule();
		System.out.println("\n===============================================================================================================================================");
	}

	
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
	
	
	
	public void updateAppointmentRequest() {
	    Scanner sc = new Scanner(System.in);
	    
	    // File path for the appointment list
	    String filePath = "./Appointment_List.csv";
	    List<String[]> appointments = new ArrayList<>();

	    // Reading the CSV file
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] appointment = line.split(",");
	            appointments.add(appointment);
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + e.getMessage());
	        return;
	    }

	    System.out.println("=====Showing Appointment Requests and Status=====");
	    // Display relevant appointments
	    for (String[] appointment : appointments) {
	        if (appointment[2].equals(this.doctorID)) {  
	            System.out.printf("Appointment ID: %s, Patient ID: %s, Status: %s, Date: %s, Time: %s, Service: %s%n", 
	                              appointment[0], appointment[1], appointment[3], appointment[4], appointment[5], appointment[6]);
	        }
	    }

	    System.out.println("\nUpdating Appointment Status for Appointment ID:");
	    String appointmentID = sc.next();
	    
	    System.out.println("Choose status to update:");
	    System.out.println("1. Confirmed");
	    System.out.println("2. Cancelled");
	    System.out.println("3. Pending");
	    int choice = sc.nextInt();
	    
	    String status;
	    switch (choice) {
	        case 1 -> status = "Confirmed";
	        case 2 -> status = "Cancelled";
	        default -> status = "Pending";
	    }

	    // Update the appointment status in the list
	    boolean updated = false;
	    for (String[] appointment : appointments) {
	        if (appointment[0].equals(appointmentID) && appointment[2].equals(doctorID)) {  // Check patient and doctor IDs
	            appointment[3] = status; 
	            updated = true;
	            break;
	        }
	    }
	    if (!updated) {
	        System.out.println("Appointment not found for the given Patient ID and Doctor ID.");
	        return;
	    }

	    // Write updated data back to the CSV file
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
	        for (String[] appointment : appointments) {
	            bw.write(String.join(",", appointment));
	            bw.newLine();
	        }
	    } catch (IOException e) {
	        System.out.println("Error writing to file: " + e.getMessage());
	        return;
	    }

	    System.out.println("Appointment Request Updated!");
	}
	
	
	
	public void viewUpcomingAppointment() {
		String filePath = "./Appointment_List.csv";
	    List<String[]> appointments = new ArrayList<>();

	    // Reading the CSV file
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] appointment = line.split(",");
	            appointments.add(appointment);
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + e.getMessage());
	        return;
	    }

	    System.out.println("=====Showing Upcoming Appointments=====");
	    
	    // Define date format
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	    LocalDate today = LocalDate.now();

	    // Display relevant appointments
	    for (String[] appointment : appointments) {
	        try {
	            // Check if doctor ID matches
	            if (appointment[2].equals(this.doctorID)) {
	                // Parse only the date field
	                String date = appointment[4].trim();
	                LocalDate appointmentDate = LocalDate.parse(date, dateFormatter);

	                // Display only if the appointment date is in the future
	                if (appointmentDate.isAfter(today)) {
	                    System.out.printf("Appointment ID: %s, Patient ID: %s, Status: %s, Date: %s, Time: %s, Service: %s%n",
	                            appointment[0], appointment[1], appointment[3], appointment[4], appointment[5], appointment[6]);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Error parsing date for an appointment: " + e.getMessage());
	        }
	    }
	}
	
	
	
	public void recordAppointmentOutcome() {
		Scanner sc = new Scanner(System.in);
		// File path for the appointment list
	    String filePath = "./Appointment_List.csv";
	    List<String[]> appointments = new ArrayList<>();

	    // Reading the CSV file
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] appointment = line.split(",");
	            appointments.add(appointment);
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + e.getMessage());
	        return;
	    }

	    System.out.println("=====Showing Appointment Requests and Status=====");
	    // Display relevant appointments
	    for (String[] appointment : appointments) {
	        if (appointment[2].equals(this.doctorID)) {  
	            System.out.printf("Appointment ID: %s, Patient ID: %s, Status: %s, Date: %s, Time: %s, Service: %s%n", 
	                              appointment[0], appointment[1], appointment[3], appointment[4], appointment[5], appointment[6]);
	        }
	    }
	    //choose appointment ID to update
	    System.out.println("\nUpdating Appointment Outcome for Appointment ID:");
	    String appointmentID = sc.next();
		
		System.out.println("Enter Date of Appointment (d/M/yyyy): "); 
		String appointmentDate = sc.next();
		System.out.println("Type of service provided: ");
		String serviceType = sc.next();
		System.out.println("Enter Prescribed medication: ");
		String prescribedMedication = sc.next();	
		System.out.println("Choose Prescription Status: ");
		System.out.println("1. Completed");
		System.out.println("2. Cancelled");
		System.out.println("3. Pending");
		int choice = sc.nextInt();
		String prescriptionStatus;
		switch (choice) {
		case 1 -> prescriptionStatus = "Completed";
		case 2 -> prescriptionStatus = "Cancelled";
		default -> prescriptionStatus = "Pending";
		}
		
		System.out.println("Consultation notes: ");
		String notes = sc.next();
		
		// Update the appointment outcome in the list
	    boolean updated = false;
	    for (String[] appointment : appointments) {
	        if (appointment[0].equals(appointmentID) && appointment[2].equals(doctorID)) {  // Check patient and doctor IDs
	            appointment[4] = appointmentDate;
	            appointment[6] = serviceType;
	            appointment[7] = prescribedMedication;
	            appointment[8] = prescriptionStatus;
	            appointment[9] = notes;
	            updated = true;
	            break;
	        }
	    }
	    if (!updated) {
	        System.out.println("Appointment not found for the given Patient ID and Doctor ID.");
	        return;
	    }

	    // Write updated data back to the CSV file
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
	        for (String[] appointment : appointments) {
	            bw.write(String.join(",", appointment));
	            bw.newLine();
	        }
	    } catch (IOException e) {
	        System.out.println("Error writing to file: " + e.getMessage());
	        return;
	    }

	    System.out.println("Appointment Outcome Updated!");
		
	}


	public void viewAppointmentOutcomeRecord() {
		// TODO Auto-generated method stub
		
	}



	public void updateAppointmentOutcomeRecord() {
		// TODO Auto-generated method stub
		
	}
	
}
