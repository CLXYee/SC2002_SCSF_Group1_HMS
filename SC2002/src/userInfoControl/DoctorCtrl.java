package userInfoControl;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import userInfo.MedicalRecord;
import userInfo.Doctor;

public class DoctorCtrl implements MedicalRecordCtrl{
	private String doctorID;
	private String[] myPatientID;
	
	public DoctorCtrl(String hospitalID) {
		this.doctorID = hospitalID;
		this.myPatientID = getPatientList(hospitalID);
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
	            
	            String csvDoctorID = values[9].trim();  //DoctorID in 10th column
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
	    System.out.println("These are patients under your record:");

	    if (myPatientID == null) {
	        System.out.println("No patients found under your record.");
	    } else {
	        // Print each patient ID
	        for (String patientID : myPatientID) {
	            System.out.println("- " + patientID);
	        }
	    }
	}


	public void showMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the patient ID to view: ");
		String patientID = sc.next();
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

	
	
	public void updateMedicalRecord() { 
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
		System.out.println("=====Showing Personal Schedule=====");
		//pending personal schedule
		
	}
	
	public void setAvailability() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select time slot to set availability: ");
		//pending personal schedule
		
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
	
	
	
	public void recordAppointmentOutcome() { //还没改好
		Scanner sc = new Scanner(System.in);
		//choose appointment ID to update
		System.out.println("Enter Date of Appointment (d/M/yyyy): "); //or choose date of appointment?
		String appointmentDate = sc.next();
		System.out.println("Type of service provided: ");
		String serviceType = sc.next();
		System.out.println("Prescribed medication: ");
		String prescribedMedication = sc.next();
		System.out.println("Consultation notes: ");
		String notes = sc.next();
		//store into database
	}
	
}