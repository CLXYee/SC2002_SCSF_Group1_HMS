package userInfoControl;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import userInfo.MedicalRecord;
import userInfo.Doctor;

public class DoctorCtrl{
	
	public DoctorCtrl(String hospitalID) {
		getPatientList(hospitalID);
	}
	
	public static int[] getPatientList(String doctorID) {
		ArrayList<Integer> patientIDs = new ArrayList<>();
		String filePath = "./Patient_List.csv"; 

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			
			// Skip header line if CSV has one
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(","); 
				
				String csvDoctorID = values[10].trim(); //Doctor ID
				if (csvDoctorID.equals(doctorID)) {
					int patientID = Integer.parseInt(values[2].trim());  //Patient ID
					patientIDs.add(patientID);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Convert ArrayList to an array
		return patientIDs.stream().mapToInt(Integer::intValue).toArray();
	}
	
	public void showMedicalRecord(String patientID) {
		//patient ID found in the doctor's record
		if(Arrays.stream(Doctor.getPatientID()).anyMatch(ID -> Integer.toString(ID).equals(patientID))) {
			MedicalRecord medicalRecord = new MedicalRecord(patientID);
			System.out.println("Show medical record for patient");
			System.out.println("===============================");
			System.out.println("Patient ID 	| " + medicalRecord.getPatientID());
			System.out.println("Name 		| " + medicalRecord.getName());
			System.out.println("Gender 		| " + medicalRecord.getGender());
			System.out.println("Phone No. 	| " + medicalRecord.getPhoneNumber());
			System.out.println("Email Address | " + medicalRecord.getEmailAddress());
			System.out.println("===============================");
		}
		//if patient id not found
		else {
			System.out.println("Patient ID not found!");
		}
	}

	public void updateMedicalRecord(String patientID) { 
		MedicalRecord medicalRecord = new MedicalRecord(patientID);
		Scanner sc = new Scanner(System.in);
		System.out.println("You are now adding new diagnoses, presecription, and treatment plan under the patient");
		System.out.println("Enter new diagnoses");
		String diagnose = sc.next();
		System.out.println("Enter prescriptions");
		String prescription = sc.next();
		System.out.println("Enter treatment plans");
		String plan = sc.next();
		
		//store [diagnose,prescription,plan] as an array and append into ArrayList<String> pastDiagnosesAndTreatment
		String record = String.format("[%s, %s, %s]", diagnose, prescription, plan);

	    // Append to the ArrayList
	    //medicalRecord.pastDiagnosesAndTreatment.add(record);
	    
		System.out.println("New record added successfully");
	}
	
	public void viewPersonalSchedule() {
		System.out.println("=====Showing Personal Schedule=====");
		
	}
	
	public void setAvailability() {
		
	}
	
	public void updateAppointmentRequest(String doctorID) {
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
	        if (appointment[2].equals(doctorID)) {  // Assuming Doctor ID is at index 1
	            System.out.printf("Patient ID: %s, Status: %s, Date: %s, Time: %s, Service: %s%n", 
	                              appointment[1], appointment[3], appointment[4], appointment[5], appointment[6]);
	        }
	    }

	    System.out.println("\nUpdating Appointment Status for Patient ID:");
	    String patientID = sc.next();
	    
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
	        if (appointment[0].equals(patientID) && appointment[1].equals(doctorID)) {  // Check patient and doctor IDs
	            appointment[3] = status;  // Assuming Status is at index 3
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
		
	}
	
	public void recordAppointmentOutcome() { //还没改好
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Date of Appointment (dd/mm/yy): "); //or choose date of appointment?
		//scan
		System.out.println("Type of service provided: ");
		//scan
		System.out.println("Prescribed medication: ");
		//scan
		System.out.println("Consultation notes: ");
		//scan
		//store into database
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			//View patient medical record
			System.out.println("Enter the patient ID to view: ");
			String id = sc.next();
			showMedicalRecord(id); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			//update patient medical record
			System.out.println("Enter the patient ID to update medical record: ");
			String id1 = sc.next();
			showMedicalRecord(id1); 
			updateMedicalRecord(id1); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
		case 3:
			//view personal schedule
			viewPersonalSchedule();
			break;
		case 4:
			//Set availability for appointment
			setAvailability();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 5:
			//Accept or Decline Appointment Requests
			System.out.println("Enter the patient ID to update medical record: ");
			String id2 = sc.next();
			updateAppointmentRequest(id2);
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 6:
			//View Upcoming Appointments
			viewUpcomingAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 7:
			//Record Appointment Outcome
			recordAppointmentOutcome();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 8:
			//Logout
			break;
	}
		
	}
}