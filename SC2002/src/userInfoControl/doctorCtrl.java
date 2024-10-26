package userInfoControl;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import userInfo.MedicalRecord;
import userInfo.Doctor;

public class doctorCtrl{
	
	public doctorCtrl(String hospitalID) {
		getPatientList(hospitalID);
	}
	
	public static int[] getPatientList(String doctorID) {
		ArrayList<Integer> patientIDs = new ArrayList<>();
		String filePath = "./Patient_List.csv";  // Ensure this path is correct relative to your project

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			
			// Skip header line if CSV has one
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(" ");  // Assuming CSV is space-separated
				
				String csvDoctorID = values[3].trim();  // "Doctor ID" in 4th column
				if (csvDoctorID.equals(doctorID)) {
					int patientID = Integer.parseInt(values[2].trim());  // "Patient ID" in 3rd column
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
	
	public void updateAppointmentRequest() {
		
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
			updateAppointmentRequest();
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