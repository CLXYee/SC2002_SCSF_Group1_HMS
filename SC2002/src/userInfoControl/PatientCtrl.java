package userInfoControl;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

import userInfo.MedicalRecord;
import userInfo.Patient;
import userInfo.PersonalSchedule;
import CSV.MedicalRecordCSVOperator;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import java.io.*;
import java.util.*;
import CSV.AppointmentCSVOperator;
import CSV.DoctorCSVOperator;

/**
 * PatientCtrl is responsible for managing patient-related operations, including viewing and updating medical records,
 * scheduling appointments, and viewing available slots for doctors.
 * It also interacts with various CSV operators to handle patient data, appointments, and medical records.
 */
public class PatientCtrl implements MedicalRecordCtrl, AppointmentCtrl {
	private Patient patient;
	private MedicalRecord medicalRecord;
	private MedicalRecordCSVOperator medicalcsv = new MedicalRecordCSVOperator();
	private AppointmentCSVOperator appointmentcsv = new AppointmentCSVOperator();
	private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<Integer> rows = new ArrayList<>();
	private Integer counter; // use to remark the most bottom line in the CSV file of appointment
	
	/**
     * Constructs a new PatientCtrl instance with the provided patient details.
     * It reads all appointments for the given patient and categorizes them into appointments and completed outcome records.
     *
     * @param hospitalID The unique identifier for the patient in the hospital system.
     * @param name The name of the patient.
     * @param gender The gender of the patient.
     * @param age The age of the patient.
     */
	public PatientCtrl(String hospitalID, String name, String gender, int age) {
		this.patient = new Patient(hospitalID, name, gender, age);
		this.medicalRecord = new MedicalRecord(hospitalID);
		
		//use to get all the appointment for the patient (the integer represent the patient is 0) 
		ArrayList<String> tempHolderForAppointment = appointmentcsv.readFile(hospitalID, 0);
		
		//loop through the data read and make it into two different entity
		for(String appointmentHolder: tempHolderForAppointment) {
			String[] tempAppointment = appointmentcsv.splitCommaCSVLine(appointmentHolder);
			
			//if the status is not completed, then we put into the appointment entity
			if (tempAppointment[1].equals(this.medicalRecord.getPatientID()) && !tempAppointment[3].equals("Completed")) 
	        {
	        	Appointment appointment = new Appointment(Integer.valueOf(tempAppointment[0]), tempAppointment[1], tempAppointment[2], tempAppointment[3], tempAppointment[4], tempAppointment[5]);
	        	this.appointments.add(appointment);
	        }//if the status is completed, we will only show the history of it
	        else if (tempAppointment[1].equals(this.medicalRecord.getPatientID()) && tempAppointment[3].equals("Completed"))
	        {
	        	AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(tempAppointment[0]), tempAppointment[4], tempAppointment[6], tempAppointment[7].split("\\s*,\\s*"), tempAppointment[8], tempAppointment[9]);
	        	this.appointmentOutcomeRecords.add(appointmentOutcomeRecord);
	        }
		}
		
		//extract the last counter for us to add the new appointment
		counter = appointmentcsv.getCounter();
	}
	
	/**
     * Displays the medical record of the patient, including personal details and past diagnoses and treatments.
     */
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
	
	/**
     * Allows the user to update their medical record. The patient can modify their phone number or email address.
     * After updating, changes are saved to the corresponding CSV file.
     */
	public void updateMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		int input = 0;
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
			try {
				input = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a nmumber.");
				sc.next();
				continue;
			}
			
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
					if(medicalcsv.changeSpecificInformation(medicalRecord.getPatientID(), index, changes)) {
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
	
	/**
     * Displays a list of available doctors and allows the user to view the available slots of a chosen doctor.
     */
	public void viewAvailableSlots()
	{
		DoctorCSVOperator doctorcsv = new DoctorCSVOperator();
		Scanner sc = new Scanner(System.in);
		String input = "y";
		// Show a list of all doctors (ID, Name, Gender)
		System.out.println("===============================================");
		System.out.println("ID\tGender\t\tName");
		System.out.println("===============================================");
		int docIDnum = 1000;
		ArrayList<String> docInfo = new ArrayList<>();
		while (true)
		{
			docInfo.clear();
			docInfo = doctorcsv.readFile("D" + ++docIDnum, 1);
			if (docInfo.isEmpty())
			{
				break;
			}
			System.out.println(docInfo.get(2) + "\t" + docInfo.get(5) + "\t\t" + docInfo.get(3));
		}
		docInfo.clear();
		while (!input.equals("n"))
		{
			while (docInfo.isEmpty())
			{
				System.out.println("======================================================================");
				System.out.println("Please choose a doctor to view his/her available appointment slots!");
				System.out.println("======================================================================");
				System.out.print("Doctor ID: ");
				input = sc.nextLine();
				docInfo = doctorcsv.readFile(input, 1);
				if (docInfo.isEmpty())
				{
					System.out.println("Invalid Doctor ID! Please input a valid Doctor ID!");
				}
			}
			docInfo.clear();
			System.out.println("===============================================================================================================================================");
			PersonalSchedule schedule = new PersonalSchedule(input);
			schedule.viewFreeSlots();
			System.out.println("===============================================================================================================================================");
			System.out.print("Do you want to view other doctor's schedule? (y/n): ");
			input = sc.nextLine();
		}
	}
	
	/**
     * Handles the scheduling of an appointment for the patient. The user selects a doctor, date, and time slot for the appointment.
     * The system checks for availability and confirms the booking.
     */
	public void scheduleAppointment()
	{
		int day = -2, start = -2, end = -2;
		String doctorID = "", dateOfAppointment, timeOfAppointment;
		
		DoctorCSVOperator doctorcsv = new DoctorCSVOperator();
		ArrayList<String> docInfo = new ArrayList<>();
		
		LocalDate today = LocalDate.now(); // Get today's date
		DayOfWeek dayOfWeek = today.getDayOfWeek(); // Get today's day
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=========================================================");
		System.out.println("*** Enter -1 to cancel booking at any time ***");
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.println("=========================================================");
		while (docInfo.isEmpty())
		{
			System.out.print("Doctor ID: ");
			doctorID = sc.nextLine();
			docInfo = doctorcsv.readFile(doctorID, 1);
			if (doctorID.equals("-1"))
			{
				System.out.println("Booking is cancelled!");
				return;
			}
			if (docInfo.isEmpty())
			{
				System.out.println("Invalid Doctor ID! Please input a valid Doctor ID!");
				System.out.println("=========================================================");
			}
		}

		PersonalSchedule schedule = new PersonalSchedule(doctorID);
		
		while (day < 0 + dayOfWeek.getValue() || day > 7)
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
			
			try {
				day = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
			
			if (day == -1)
			{
				System.out.println("Booking is cancelled!");
				return;
			}
			switch (day)
			{
				case 1, 2, 3, 4, 5, 6, 7:
					day--;
					break;
				default:
					System.out.println("Invalid Choice! Please choose again!");
			}
			
			if (day <= dayOfWeek.getValue() - 1)
			{
				System.out.println("Booking must be made at least 1 day before!\nPlease choose another day!");
			}
		}
		
		boolean isFree = false;
		
		while (!isFree)
		{
			start = -2;
			end = -2;
			while (start < 0 || start > 16)
			{
				System.out.println("===========================================");
				System.out.println("           Starting Time Session           ");
				System.out.println("===========================================");
				System.out.println("1.  10:00\t 9.  14:00");
				System.out.println("2.  10:30\t 10. 14:30");
				System.out.println("3.  11:00\t 11. 15:00");
				System.out.println("4.  11:30\t 12. 15:30");
				System.out.println("5.  12:00\t 13. 16:00");
				System.out.println("6.  12:30\t 14. 16:30");
				System.out.println("7.  13:00\t 15. 17:00");
				System.out.println("8.  13:30\t 16. 17:30");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				
				try {
					start = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a number.");
					sc.next();
					continue;
				}
				
				if (start == -1)
				{
					System.out.println("Booking is cancelled!");
					return;
				}
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
				System.out.println("1.  10:30\t 9.  14:30");
				System.out.println("2.  11:00\t 10. 15:00");
				System.out.println("3.  11:30\t 11. 15:30");
				System.out.println("4.  12:00\t 12. 16:00");
				System.out.println("5.  12:30\t 13. 16:30");
				System.out.println("6.  13:00\t 14. 17:00");
				System.out.println("7.  13:30\t 15. 17:30");
				System.out.println("8.  14:00\t 16. 18:00");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				
				try {
					end = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a number.");
					sc.next();
					continue;
				}
				
				if (end == -1)
				{
					System.out.println("Booking is cancelled!");
					return;
				}
				switch (end)
				{
					case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16:
						end--;
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
				
			}
			
			if (end<start) {
				System.out.println("Invalid choice! Ending time cannot be earlier than starting time");
				continue;
			}
			
			if (!schedule.slotsAreFree(day, start, end))
			{
				System.out.println("==================================================================");
				System.out.println("Not all of the time sessions selected are available for booking!");
				System.out.println("Please choose time sessions that are free for booking!");
				System.out.println("==================================================================");
			}
			else
			{
				isFree = true;
			}
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		today = today.plusDays(day + 1 - dayOfWeek.getValue());
		dateOfAppointment = today.format(formatter);
		
		String[] timeSlots = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
		timeOfAppointment = timeSlots[start] + "-" + timeSlots[end+1];
		
		System.out.println("=========================================================");
		
		Appointment appointment = new Appointment(appointmentcsv.getCounter(), this.medicalRecord.getPatientID(), doctorID, "Pending", dateOfAppointment, timeOfAppointment);
		this.appointments.add(appointment);
		
		if(appointment.addNewAppointmentToCSV()) {
			System.out.println("Schedule add successfully!");
		}else {
			System.out.println("Schedule failed to added");
		}
		
		schedule.editSchedule(day, start, end, appointment.getAppointmentID());
		ArrayList<Integer> indexChanges = new ArrayList<>(Arrays.asList(9));
		ArrayList<String> changes = new ArrayList<>(Arrays.asList("","","","","","","","","",schedule.translateSchedule()));
		doctorcsv.changeSpecificInformation(doctorID, indexChanges, changes);

	}
	
	/**
     * Reschedules an existing appointment by allowing the user to select a new date and time.
     * Updates both the user's and the doctor's schedules, and modifies the CSV files accordingly.
     * The user can cancel the rescheduling process at any time by entering -1.
     */
	public void rescheduleAppointment()
	{
		String newDate, newTime;
		int day = -2, start = -2, end = -2;
		int appNum = -1;
		
		DoctorCSVOperator doctorcsv = new DoctorCSVOperator();
		
		LocalDate today = LocalDate.now(); // Get today's date
		DayOfWeek dayOfWeek = today.getDayOfWeek(); // Get today's day
		
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
		System.out.println("*** Enter -1 to cancel reschedule at any time ***");
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.println("=========================================================");

		while (appNum <= 0 || appNum > appointments.size())
		{
			System.out.print("Appointment Number: ");
			
			try {
				appNum = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a nmumber.");
				sc.next();
				continue;
			}
			if (appNum == -1)
			{
				System.out.println("Rescheduling is cancelled!");
				return;
			}
			if (appNum <= 0 || appNum > appointments.size())
			{
				System.out.println("Invalid choice! Please input a valid Appointment Number!");
				System.out.println("=========================================================");
			}
		}
		
		Appointment newAppointment = this.appointments.get(appNum-1);
		PersonalSchedule schedule = new PersonalSchedule(newAppointment.getDoctorID());
		
		while (day < 0 + dayOfWeek.getValue() || day > 7)
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
			
			try {
				day = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
			
			if (day == -1)
			{
				System.out.println("Rescheduling is cancelled!");
				return;
			}
			switch (day)
			{
				case 1, 2, 3, 4, 5, 6, 7:
					day--;
					break;
				default:
					System.out.println("Invalid Choice! Please choose again!");
			}
			
			if (day <= dayOfWeek.getValue() - 1)
			{
				System.out.println("Booking must be made at least 1 day before!\nPlease choose another day!");
			}
		}
		
		boolean isFree = false;
		
		while (!isFree)
		{
			start = -2;
			end = -2;
			while (start < 0 || start > 16)
			{
				System.out.println("===========================================");
				System.out.println("           Starting Time Session           ");
				System.out.println("===========================================");
				System.out.println("1.  10:00\t 9.  14:00");
				System.out.println("2.  10:30\t 10. 14:30");
				System.out.println("3.  11:00\t 11. 15:00");
				System.out.println("4.  11:30\t 12. 15:30");
				System.out.println("5.  12:00\t 13. 16:00");
				System.out.println("6.  12:30\t 14. 16:30");
				System.out.println("7.  13:00\t 15. 17:00");
				System.out.println("8.  13:30\t 16. 17:30");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				
				try {
					start = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a number.");
					sc.next();
					continue;
				}
				
				if (start == -1)
				{
					System.out.println("Rescheduling is cancelled!");
					return;
				}
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
				System.out.println("1.  10:30\t 9.  14:30");
				System.out.println("2.  11:00\t 10. 15:00");
				System.out.println("3.  11:30\t 11. 15:30");
				System.out.println("4.  12:00\t 12. 16:00");
				System.out.println("5.  12:30\t 13. 16:30");
				System.out.println("6.  13:00\t 14. 17:00");
				System.out.println("7.  13:30\t 15. 17:30");
				System.out.println("8.  14:00\t 16. 18:00");
				System.out.println("===========================================");
				System.out.print("Enter your choice: ");
				
				try {
					end = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a number.");
					sc.next();
					continue;
				}
				
				if (end == -1)
				{
					System.out.println("Rescheduling is cancelled!");
					return;
				}
				switch (end)
				{
					case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16:
						end--;
						break;
					default:
						System.out.println("Invalid Choice! Please choose again!");
				}
			}
			
			if (end<start) {
				System.out.println("Invalid choice! Ending time cannot be earlier than starting time");
				continue;
			}
			
			if (!schedule.slotsAreFree(day, start, end))
			{
				System.out.println("==================================================================");
				System.out.println("Not all of the time sessions selected are available for booking!");
				System.out.println("Please choose time sessions that are free for booking!");
				System.out.println("==================================================================");
			}
			else
			{
				isFree = true;
			}
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		today = today.plusDays(day + 1 - dayOfWeek.getValue());
		newDate = today.format(formatter);
		
		String[] timeSlots = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
		newTime = timeSlots[start] + "-" + timeSlots[end+1];

		System.out.println("=========================================================");
		
		//Set doctor's personal schedule's old appointment slot to free
		List<String> timeSlotList = Arrays.asList(timeSlots);
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate olddate = LocalDate.parse(newAppointment.getDateOfAppointment(), formatter2);
        DayOfWeek olddayOfWeek = olddate.getDayOfWeek();
        
		int oldday = olddayOfWeek.getValue() - 1;
	    int oldstart = timeSlotList.indexOf(newAppointment.getTimeOfAppointment().substring(0,5));
	    int oldend = timeSlotList.indexOf(newAppointment.getTimeOfAppointment().substring(6)) - 1;
		schedule.editSchedule(oldday, oldstart, oldend, 'F');
		
		//Set new appointment in doctor's personal schedule
		schedule.editSchedule(day, start, end, newAppointment.getAppointmentID());
		
		//Update the current appointments List
		newAppointment.setDateOfAppointment(newDate);
		newAppointment.setTimeOfAppointment(newTime);
		newAppointment.setAppointmentStatus("Pending");
		this.appointments.set(appNum-1, newAppointment);
		
		//Update the csv file
		if(newAppointment.rescheduleAppointmentInCSV()) {
			System.out.println("Appointment has been rescheduled successfully!");
		}else {
			System.out.println("Failed to reschedule!");
		}

		//Update doctor's csv file
		ArrayList<Integer> indexChanges = new ArrayList<>(Arrays.asList(9));
		ArrayList<String> changes = new ArrayList<>(Arrays.asList("","","","","","","","","",schedule.translateSchedule()));
		doctorcsv.changeSpecificInformation(newAppointment.getDoctorID(), indexChanges, changes);
	}
	
	/**
     * Cancels an existing appointment. The appointment is marked as "Canceled" in the system 
     * and the doctor's schedule is updated accordingly. The user can exit the cancellation process 
     * by entering -1.
     */
	public void cancelAppointment()
	{
		int appNum = -1;
		DoctorCSVOperator doctorcsv = new DoctorCSVOperator();
		
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
		System.out.println("*** Enter -1 to exit booking cancellation ***");
		System.out.println("=========================================================");
		System.out.println("Please fill in the following information:");
		System.out.println("=========================================================");
		while (appNum <= 0 || appNum > appointments.size())
		{
			System.out.print("Appointment Number: ");
			
			try {
				appNum = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a nmumber.");
				sc.next();
				continue;
			}
			
			if (appNum == -1)
			{
				System.out.println("Cancellation is terminated!");
				return;
			}
			if (appNum <= 0 || appNum > appointments.size())
			{
				System.out.println("Invalid choice! Please input a valid Appointment Number!");
				System.out.println("=========================================================");
			}
		}
		
		System.out.println("=========================================================");
		
		Appointment newAppointment = this.appointments.get(appNum-1);
		PersonalSchedule schedule = new PersonalSchedule(newAppointment.getDoctorID());
		
		//Set doctor's personal schedule's old appointment slot to free
		String[] timeSlots = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
		List<String> timeSlotList = Arrays.asList(timeSlots);
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate olddate = LocalDate.parse(newAppointment.getDateOfAppointment(), formatter2);
        DayOfWeek olddayOfWeek = olddate.getDayOfWeek();
        
		int oldday = olddayOfWeek.getValue() - 1;
	    int oldstart = timeSlotList.indexOf(newAppointment.getTimeOfAppointment().substring(0,5));
	    int oldend = timeSlotList.indexOf(newAppointment.getTimeOfAppointment().substring(6)) - 1;
		schedule.editSchedule(oldday, oldstart, oldend, 'F');
				
		//Update the current appointments List
		newAppointment.setAppointmentStatus("Canceled");
		this.appointments.set(appNum-1, newAppointment);
		
		//Update the csv file
		if(newAppointment.rescheduleAppointmentInCSV()) {
			System.out.println("Appointment has been cancelled successfully!");
		}else {
			System.out.println("Failed to cancel!");
		}

		//Update doctor's csv file
		ArrayList<Integer> indexChanges = new ArrayList<>(Arrays.asList(9));
		ArrayList<String> changes = new ArrayList<>(Arrays.asList("","","","","","","","","",schedule.translateSchedule()));
		doctorcsv.changeSpecificInformation(newAppointment.getDoctorID(), indexChanges, changes);
	}
	
	/**
     * Displays a list of all scheduled appointments for the user, including details like the doctor's ID, 
     * appointment status, date, and time. If there are no scheduled appointments, it notifies the user.
     */
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
	
	/**
     * Displays the user's past completed appointment records. Each record includes the appointment date, 
     * type of service, prescribed medications, and consultation notes. If there are no completed appointments, 
     * it notifies the user.
     */
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
}
