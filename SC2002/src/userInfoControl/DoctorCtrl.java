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
	private String doctorID;
	private String[] myPatientID;
	
	public DoctorCtrl(String hospitalID) {
		this.doctorID = hospitalID;
		this.myPatientID = getPatientList(hospitalID);
	}
	
	public static String[] getPatientList(String doctorID) {
	    ArrayList<String> patientIDs = new ArrayList<>();
	    String filePath = "./Patient_List.csv";  // Ensure this path is correct relative to your project

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        // Skip header line if CSV has one
	        br.readLine();
	        
	        while ((line = br.readLine()) != null) {
	            String[] values = line.split(",");  // Assuming CSV is comma-separated
	            
	            String csvDoctorID = values[1].trim();  // Assuming "Doctor ID" is the second column
	            if (csvDoctorID.equals(doctorID)) {
	                String patientID = values[0].trim();  // Assuming "Patient ID" is the first column
	                patientIDs.add(patientID);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Convert ArrayList to an array
	    return patientIDs.toArray(new String[0]);
	}

	
	public void showMedicalRecord(String patientID) {
		//patient ID found in the doctor's record
		if (Arrays.stream(myPatientID)
	              .anyMatch(ID -> ID.equals(patientID))) {
		    MedicalRecord medicalRecord = new MedicalRecord(patientID);
		    System.out.println("Show medical record for patient");
		    System.out.println("===============================");
		    System.out.println("Patient ID     | " + medicalRecord.getPatientID());
		    System.out.println("Name           | " + medicalRecord.getName());
		    System.out.println("Gender         | " + medicalRecord.getGender());
		    System.out.println("Phone No.      | " + medicalRecord.getPhoneNumber());
		    System.out.println("Email Address  | " + medicalRecord.getEmailAddress());
		    System.out.println("===============================");
		}
		//if patient id not found
		else {
			System.out.println("Patient ID not found in your record!");
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
	        if (appointment[2].equals(doctorID)) {  
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
	        if (appointment[1].equals(patientID) && appointment[2].equals(doctorID)) {  // Check patient and doctor IDs
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
	
}