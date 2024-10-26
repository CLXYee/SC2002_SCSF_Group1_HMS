package userInfoControl;

import userInfo.MedicalRecord;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import java.io.*;
import java.util.*;

public class PatientCtrl implements MedicalRecordCtrl, GetOperationInput, EntityUpdate {
	private MedicalRecord medicalRecord;
	private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<Integer> rows = new ArrayList<>();
	private int counter = 0; 
	
	public PatientCtrl(String hospitalID) {
		this.medicalRecord = new MedicalRecord(hospitalID);
		try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = splitCSVLine(line);
		        for (int i = 0; i < data.length; i++) 
		        {
		        	System.out.println(data[i]);
		        }
		        if (data[1].equals(this.medicalRecord.getPatientID()) && !data[3].equals("Completed")) 
		        {
		        	Appointment appointment = new Appointment(Integer.valueOf(data[0]), data[1], data[2], data[3], data[4], data[5]);
		        	this.appointments.add(appointment);
		        	this.rows.add(counter);
		        }
		        else if (data[1].equals(this.medicalRecord.getPatientID()) && data[3].equals("Completed"))
		        {
		        	AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(data[4], data[6], data[7].split("\\s*,\\s*"), data[8].split("\\s*,\\s*"), data[9]);
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
				case 3:
					if(updateSpecificInfo(medicalRecord.getPatientID())) {
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
	
	public void viewPastRecords()
	{
		
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
    
	public boolean updateSpecificInfo(String target) {
		String filePath = "./Patient_List.csv"; // original file
		String tempFile = "./temp.csv"; // temporary file for the data changing
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Split the row into columns

                if(medicalRecord.getPatientID().equals(data[2])) {
                	if(!medicalRecord.getPhoneNumber().equals(data[6])) {
                		data[6] = medicalRecord.getPhoneNumber();
                	}
                	
                	if(!medicalRecord.getEmailAddress().equals(data[7])) {
                		data[7] = medicalRecord.getEmailAddress();
                	}
                }
                
                writer.write(String.join(",", data));
                writer.newLine();
            }
		} catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please check the file path.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Error: An I/O error occurred while reading or writing the file.");
            e.printStackTrace();
            return false;
        } finally {
            // Close the resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error: Failed to close the file.");
                e.printStackTrace();
            }
        }
		
		try {
			File originalFile = new File(filePath);
			File newFile = new File(tempFile);
			
			if(originalFile.delete()) {
				newFile.renameTo(originalFile);
			}
		}catch(Exception e) {
			System.out.println("Error: unable to delet or rename file.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			updateMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
		case 3:
			//View Available Appointment Slots
			break;
		case 4:
			//Schedule an Appointment
			scheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 5:
			//Reschedule an Appointment
			rescheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 6:
			//Cancel an Appointment
			cancelAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 7:
			//View Scheduled Appointments
			viewScheduledAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 8:
			//View Past Appointment Outcome Records
			break;
		case 9:
			//Logout
			break;
		}
	}

	
}
