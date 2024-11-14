package userInfoControl;

import userInfo.MedicalRecord;
import userInfo.Patient;
import CSV.MedicalRecordCSVOperator;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import java.io.*;
import java.util.*;

public class PatientCtrl implements MedicalRecordCtrl, AppointmentCtrl {
	private Patient patient;
	private MedicalRecord medicalRecord;
	private MedicalRecordCSVOperator csv = new MedicalRecordCSVOperator();
	private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<Integer> rows = new ArrayList<>();
	private int counter = 0; // use to remark the most bottom line in the CSV file of appointment
	
	public PatientCtrl(String hospitalID, String name, String gender, int age) {
		this.patient = new Patient(hospitalID, name, gender, age);
		this.medicalRecord = new MedicalRecord(hospitalID);
		try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = splitAppointmentCSVLine(line);
		        // Only if the appointment is completed, system will only print the appointment outcome record
		        if (data[1].equals(this.medicalRecord.getPatientID()) && !data[3].equals("Completed")) 
		        {
		        	Appointment appointment = new Appointment(Integer.valueOf(data[0]), data[1], data[2], data[3], data[4], data[5]);
		        	this.appointments.add(appointment);
		        	this.rows.add(counter);
		        }
		        else if (data[1].equals(this.medicalRecord.getPatientID()) && data[3].equals("Completed"))
		        {
		        	AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(data[0]), data[4], data[6], data[7].split("\\s*,\\s*"), data[8], data[9]);
		        	this.appointmentOutcomeRecords.add(appointmentOutcomeRecord);
		        }
		        this.counter++;
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void showMedicalRecord() {
		System.out.println("Show medical record for patient");
		System.out.println("=================================================");
		System.out.println("Patient ID		| " + medicalRecord.getPatientID());
		System.out.println("Name			| " + medicalRecord.getName());
		System.out.println("Gender			| " + medicalRecord.getGender());
		System.out.println("Phone No.		| " + medicalRecord.getPhoneNumber());
		System.out.println("Email Address	| " + medicalRecord.getEmailAddress());
		System.out.println("Blood Type		| " + medicalRecord.getBloodType());
		System.out.println("Doctor In Charge| " + medicalRecord.getDoctor());
		System.out.println("=================================================");
		System.out.println("Past Diagnoses and Treatments:");
	    System.out.println("==============================");

	    for (String record : medicalRecord.getPastDiagnosesAndTreatment()) {
	        String[] parts = record.split(";\\s*");  // Split by ';' with optional whitespace

	        if (parts.length == 3) {
	            String diagnose = parts[0].trim();
	            String prescription = parts[1].trim();
	            String plan = parts[2].trim();

	            System.out.println("Diagnosis: " + diagnose);
	            System.out.println("Prescription: " + prescription);
	            System.out.println("Plan: " + plan);
	            System.out.println("------------------------------");
	        } else {
	            System.out.println("Error: Invalid record format.");
	        }
	    }
	}
	
	public void updateMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		int input;
		boolean checker;
		do {
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("Which of the following information you willing to change:");
			System.out.println("1. Phone number");
			System.out.println("2. Email address");
			System.out.println("3. Exit");
			System.out.println("=========================================================");
			System.out.println();
			input = sc.nextInt();
			
			switch(input){
				case 1:
					System.out.println("Please enter a new phone number");
					checker = medicalRecord.setPhoneNumber(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid phone number:");
						checker = medicalRecord.setPhoneNumber(sc.next());
					}
					System.out.println("Phone number has been updated successfully!");
					System.out.println();
					break;
				case 2:
					System.out.println("Please enter a new email address:");
					checker = medicalRecord.setEmailAddress(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid email address:");
						checker = medicalRecord.setEmailAddress(sc.next());
					}
					System.out.println("Email Address has been updated successfully!");
					System.out.println();
					break;
				case 3:
					//put all the changes into an array list for the function changing the specific information
					ArrayList<String> changes = new ArrayList<String>();
					ArrayList<Integer> index = new ArrayList<Integer>();
					Collections.addAll(changes, medicalRecord.getPhoneNumber(), medicalRecord.getEmailAddress());
					Collections.addAll(index, 7, 8);
					
					//for the changeSpecificInformation function, u need to put in the patient id, the index to change and the relevant changes u want to make
					if(csv.changeSpecificInformation(medicalRecord.getPatientID(), index, changes)) {
						System.out.println("Exiting.......");
					}else {
						System.out.println("System updated failed!");
					}
					break;
				default:
					System.out.println("Please enter a valid option!");
					System.out.println();
			}
		}while(input != 3);
	}
	
	public void scheduleAppointment()
	{
		String doctorID, dateOfAppointment, timeOfAppointment;
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.print("The Doctor's ID    : ");
		doctorID = sc.nextLine();
		
		System.out.print("Date of Appointment: ");
		dateOfAppointment = sc.nextLine();
		
		System.out.print("Time of Appointment: ");
		timeOfAppointment = sc.nextLine();
		
		System.out.println("=========================================================");
		
		Appointment appointment = new Appointment(counter, this.medicalRecord.getPatientID(), doctorID, "Pending", dateOfAppointment, timeOfAppointment);
		this.appointments.add(appointment);
		this.rows.add(this.counter);
		this.counter++;
		appointment.addNewAppointmentToCSV("./Appointment_List.csv");
	}
	
	public void updateAppointmentCSVFile(int lineNumber, Appointment newAppointment)
	{
		List<String> allAppointments = new ArrayList<>();
        
		//Get all the appointments in the file
        try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
            	allAppointments.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
            return;
        }
        
        //Make changes to the appointment in the appointment list
        String updatedLine = newAppointment.getAppointmentID() + "," +
        					 newAppointment.getPatientID() + "," +
                             newAppointment.getDoctorID() + "," +
                             newAppointment.getAppointmentStatus() + "," +
                             newAppointment.getDateOfAppointment() + "," +
                             newAppointment.getTimeOfAppointment() + ",,,";
                                 
        allAppointments.set(lineNumber, updatedLine);
        
        //Make changes to the csv file
        try (FileWriter writer = new FileWriter("./Appointment_List.csv", false)) 
        {
            for (String l : allAppointments) {
                writer.write(l + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
	}
	
	public void rescheduleAppointment()
	{
		String newDate, newTime;
		int appNum;
		Scanner sc = new Scanner(System.in);
		if (this.appointments.size() == 0)
		{
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("You don't have any scheduled appointments");
			System.out.println("=========================================================");
			return;
		}
		System.out.println();
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.print("Appointment Number     : ");
		appNum = sc.nextInt();
		System.out.print("New Date of Appointment: ");
		newDate = sc.nextLine(); //Dummy for removing the enter
		newDate = sc.nextLine();
		System.out.print("New Time of Appointment: ");
		newTime = sc.nextLine();
		System.out.println("=========================================================");
		
		//Update the current appointments List
		Appointment newAppointment = this.appointments.get(appNum-1);
		newAppointment.setDateOfAppointment(newDate);
		newAppointment.setTimeOfAppointment(newTime);
		this.appointments.set(appNum-1, newAppointment);
		
		//Update the csv file
		updateAppointmentCSVFile(rows.get(appNum-1), newAppointment);
		System.out.println("Appointment has been rescheduled successfully!");
	}
	
	public void cancelAppointment()
	{
		int appNum;
		Scanner sc = new Scanner(System.in);
		if (this.appointments.size() == 0)
		{
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("You don't have any scheduled appointments");
			System.out.println("=========================================================");
			return;
		}
		System.out.println();
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.print("Appointment Number     : ");
		appNum = sc.nextInt();
		System.out.println("=========================================================");
		
		//Update the current appointments List
		Appointment newAppointment = this.appointments.get(appNum-1);
		newAppointment.setAppointmentStatus("Canceled");
		this.appointments.set(appNum-1, newAppointment);
		
		//Update the csv file
		updateAppointmentCSVFile(rows.get(appNum-1), newAppointment);
		System.out.println("Appointment has been canceled successfully!");
	}
	
	public void viewScheduledAppointment()
	{
		if (this.appointments.size() == 0)
		{
			System.out.println();
			System.out.println("=========================================================================");
			System.out.println("You don't have any scheduled appointments");
			System.out.println("=========================================================================");
			return;
		}
		System.out.println();
		System.out.println("Your scheduled appointments");
		System.out.println("============================================================================================");
		System.out.println("No.\tDoctor\t\tAppointment Status\tDate of Appointment\tTime of Appointment");
		System.out.println("============================================================================================");
		for (int i = 0; i < this.appointments.size(); i++)
		{
			Appointment app = this.appointments.get(i);
			System.out.print((i+1) + "\t");
			System.out.print(app.getDoctorID() + "\t\t");
			System.out.print(app.getAppointmentStatus() + "  \t\t");
			System.out.print(app.getDateOfAppointment() + "\t\t");
			System.out.println(app.getTimeOfAppointment());
		}
		System.out.println("============================================================================================");
	}
	
	public void viewPastRecords()
	{
		if (this.appointmentOutcomeRecords.size() == 0)
		{
			System.out.println();
			System.out.println("=========================================================================");
			System.out.println("You don't have any completed appointments");
			System.out.println("=========================================================================");
			return;
		}
		System.out.println();
		System.out.println("Your appointment outcome records");
		System.out.println("============================================================================================");
		for (int i = 0; i < this.appointmentOutcomeRecords.size(); i++)
		{
			AppointmentOutcomeRecord rec = this.appointmentOutcomeRecords.get(i);
			System.out.print((i+1) + ".");
			System.out.println("\tDate of Appointment: " + rec.getDateOfAppointment());
			System.out.println("\tType of Service: " + rec.getTypeOfService());
			System.out.println("\tPrescribed Medications:");
			for (int j = 0; j < rec.getPrescribedMedications().length; j++)
			{
				System.out.println("\t\t" + (j+1) + ". " + rec.getPrescribedMedications()[j]);
			}
			System.out.println("\tConsultation Notes:");
			System.out.println("\t\t" + rec.getConsultationNotes());
			System.out.println();
		}
		System.out.println("============================================================================================");
	}

    private String[] splitAppointmentCSVLine(String line) // Split a CSV line into the proper format (used for Appointment)
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
}
