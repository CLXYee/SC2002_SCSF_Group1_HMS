package userInfoControl;

import userInfo.MedicalRecord;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientCtrl implements MedicalRecordCtrl, AppointmentCtrl {
	private MedicalRecord medicalRecord;
	private List<Appointment> appointments = new ArrayList<>();
	private List<Integer> rows = new ArrayList<>();
	private int counter = 0; 
	
	public PatientCtrl(String hospitalID) 
	{
		this.medicalRecord = new MedicalRecord(hospitalID);
		try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = line.split(",");
		        if (data[0].equals(this.medicalRecord.getPatientID()) && !data[2].equals("Completed")) 
		        {
		        	Appointment appointment = new Appointment(data[0], data[1], data[2], data[3], data[4]);
		        	this.appointments.add(appointment);
		        	this.rows.add(counter);
		        }
		        this.counter++;
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void showMedicalRecord() {
		System.out.println("Show medical record for patient");
		System.out.println("===============================");
		System.out.println("Patient ID 	| " + medicalRecord.getPatientID());
		System.out.println("Name 		| " + medicalRecord.getName());
		System.out.println("Gender 		| " + medicalRecord.getGender());
		System.out.println("Phone No. 	| " + medicalRecord.getPhoneNumber());
		System.out.println("Email Address | " + medicalRecord.getEmailAddress());
		System.out.println("===============================");
	}
	
	public void updateMedicalRecord() 
	{
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
						System.out.println("Please enter a valid phone number");
						checker = medicalRecord.setPhoneNumber(sc.next());
					}
					System.out.println("Phone number has been updated successfully!");
					System.out.println();
					break;
				case 2:
					System.out.println("Please enter a new email address");
					checker = medicalRecord.setEmailAddress(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid email address");
						checker = medicalRecord.setEmailAddress(sc.next());
					}
					System.out.println("Email Address has been updated successfully!");
					System.out.println();
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
		Appointment appointment = new Appointment(this.medicalRecord.getPatientID(), doctorID, "Pending", dateOfAppointment, timeOfAppointment);
		this.appointments.add(appointment);
		this.rows.add(this.counter);
		this.counter++;
		appointment.appendLineToCSV("./Appointment_List.csv");
	}
	
	public void updateCSVFile(int lineNumber, Appointment newAppointment)
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
        String updatedLine = newAppointment.getPatientID() + "," +
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
		updateCSVFile(rows.get(appNum-1), newAppointment);
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
		updateCSVFile(rows.get(appNum-1), newAppointment);
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
}