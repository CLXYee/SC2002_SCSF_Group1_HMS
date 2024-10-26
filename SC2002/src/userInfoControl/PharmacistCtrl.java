package userInfoControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import userInfo.Appointment;
import userInfo.MedicalRecord;
import userInfo.AppointmentOutcomeRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PharmacistCtrl implements AppointmentOutcomeRecordCtrl, GetOperationInput{
	private List<Appointment> appointments = new ArrayList<>();
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	private List<Integer> rows = new ArrayList<>();
	private int counter = 0; 

	private static final String CSV_REGEX = "\"([^\"]*)\"|([^,]*)";
    private static final Pattern CSV_PATTERN = Pattern.compile(CSV_REGEX);

	public PharmacistCtrl() {
		try (BufferedReader br = new BufferedReader(new FileReader("test/Appointment_List.csv"))) 
		{		    
			String line;
    		while ((line = br.readLine()) != null) 
    		{
		        // Split the line into columns using the delimiter
		        String[] data = splitCSVLine(line);
				if (counter == 0){
					counter = 1;
					continue;
				}
		        Appointment appointment = new Appointment(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);
				AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(data[0]), data[4], data[6], data[7].split("\\s*,\\s*"), data[8], data[9]);

		        this.appointments.add(appointment);
				this.appointmentsOutcomeRecord.add(appointmentOutcomeRecord);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
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

	private List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        Matcher matcher = CSV_PATTERN.matcher(line);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                fields.add(matcher.group(1)); // Quoted value
            } else {
                fields.add(matcher.group(2)); // Unquoted value
            }
        }
        return fields;
    }

	public void viewAppointmentOutcome() {		
		System.out.println();
    	System.out.println("=========================================================");
   		System.out.println("This is the list of appointments with unfulfilled medication prescription orders.");

        // Check if Prescription Status is empty or unfulfilled
		for (int i = 0; i < this.appointmentsOutcomeRecord.size(); i++)	
		if (appointmentsOutcomeRecord.get(i).getPrescriptionStatus().equals("Pending")) {
				// Print the appointment outcome record details (modify based on your class structure)
				System.out.println("Appointment ID: " + appointmentsOutcomeRecord.get(i).getAppointmentID()); // Example
				System.out.println("Prescription Status: " + appointmentsOutcomeRecord.get(i).getPrescriptionStatus());
				System.out.print("Prescribed Medications: ");
				for (int j = 0; j < appointmentsOutcomeRecord.get(i).getPrescribedMedications().length; j++){
					System.out.print(appointmentsOutcomeRecord.get(i).getPrescribedMedications()[j]);
					System.out.print(", ");
				}
				System.out.println(" ");
				System.out.println("Consultation Notes: " + appointmentsOutcomeRecord.get(i).getConsultationNotes());
				System.out.println("---------------------------------------------------------");
			}

    System.out.println("=========================================================");
    System.out.println();
	}
	
	public void updateAppointmentOutcomeRecord() {
	}
	public void viewMedicationInventory() {
	}
	public void submitReplenishRequest() {
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			viewAppointmentOutcome();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			updateAppointmentOutcomeRecord();
			break;
		case 3:
			viewMedicationInventory();
			break;
		case 4:
			submitReplenishRequest();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		}
	}
}