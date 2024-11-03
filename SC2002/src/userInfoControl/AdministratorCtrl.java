package userInfoControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mainSystemControl.Role;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import userInfo.Medicine;
import userInfo.User;

public class AdministratorCtrl implements IMedicineView, InventoryManagement, StaffManagement, IViewAppointment{
	private String hospitalID;
	private List<User> staffList = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	private List<Medicine> medicines = new ArrayList<>();
	private int counter = 0; 
	
	public AdministratorCtrl(String hospitalID) {
		this.hospitalID = hospitalID;
		try (BufferedReader br = new BufferedReader(new FileReader("./Staff_List.csv"))) 
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
				Role role = Role.valueOf(data[1]);
				User staff = new User(role, data[2], data[3], data[4], Integer.parseInt(data[5]));

		        this.staffList.add(staff);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
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
		
		counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("./Medicine_List.csv"))) 
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
		        Medicine medicine = new Medicine(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), data[5], data[6]);

		        this.medicines.add(medicine);
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
    
    public void viewStaff() {
		System.out.println("");
    	for (int i = 0; i < staffList.size(); i++) {
			System.out.println("Role: " + staffList.get(i).getRole().name());
			System.out.println("Staff ID: " + staffList.get(i).getHospitalId());
			System.out.println("Name: " + staffList.get(i).getName());
			System.out.println("Gender: " + staffList.get(i).getGender());
			System.out.println("Age: " + staffList.get(i).getAge());
			System.out.println("---------------");
		}
	}
	
    public void manageStaff() {
    	
    }
    
    public void viewAppointments() {
		System.out.println("================================================================================");
		for (int i = 0; i < this.appointmentsOutcomeRecord.size(); i++) {	
			// Print the appointment outcome record details (modify based on your class structure)
			System.out.println("Appointment ID: " + appointments.get(i).getAppointmentID());
			System.out.println("Patient ID: " + appointments.get(i).getPatientID());
			System.out.println("Doctor ID: " + appointments.get(i).getDoctorID());
			System.out.println("Appointment Status: " + appointments.get(i).getAppointmentStatus());
			System.out.println("Date of Appointment: " + appointments.get(i).getDateOfAppointment());
			System.out.println("Time of Appointment: " + appointments.get(i).getTimeOfAppointment());
			System.out.println("Type of Service: " + appointmentsOutcomeRecord.get(i).getTypeOfService());
			System.out.print("Prescribed Medications: ");
			for (int j = 0; j < appointmentsOutcomeRecord.get(i).getPrescribedMedications().length; j++){
				System.out.print(appointmentsOutcomeRecord.get(i).getPrescribedMedications()[j]);
				if (j != appointmentsOutcomeRecord.get(i).getPrescribedMedications().length - 1)
						System.out.print(", ");
			}
			System.out.println(" ");
			System.out.println("Prescription Status: " + appointmentsOutcomeRecord.get(i).getPrescriptionStatus());
			System.out.println("Consultation Notes: " + appointmentsOutcomeRecord.get(i).getConsultationNotes());
			System.out.println("------------------------------------------------------------------------");
		}
		System.out.println("================================================================================");		
	
    }
    public void viewMedicationInventory() {
		System.out.println("================================================================================");
   		System.out.println("List of medicines");
		System.out.println("");
   		for (int i = 0; i < this.medicines.size(); i++) {
   			System.out.println("Medicine name: " + medicines.get(i).getName());
   			System.out.println("Initial Stock: " + medicines.get(i).getStockLevel());
   			System.out.println("Low Stock Level Alert: " + medicines.get(i).getLowStockLevelAlert());
   			System.out.println("Replenish Request Status: " + medicines.get(i).getReplenishRequestStatus());
   			System.out.println("Replenish Request Amount: " + medicines.get(i).getReplenishRequestAmount());
   			System.out.println("Replenish Request Submitted By: " + medicines.get(i).getReplenishRequestSubmittedBy());
   			System.out.println("Replenish Request Approved By: " + medicines.get(i).getReplenishRequestApprovedBy());
			System.out.println("--------------------------");
   		}
		System.out.println("================================================================================");
	}
    
    public void approveReplenishRequest() {
    	System.out.println("Please input the medicine name for replenish:");
		Scanner sc = new Scanner(System.in);
		String medicineName = sc.next();
		for (int i = 0; i < this.medicines.size(); i++) {
   			if (medicineName.equalsIgnoreCase(medicines.get(i).getName())){
   				medicines.get(i).setReplenishRequest();
   				System.out.println("Replenish request for " + medicineName + " submitted");
   				return;
   			}
   		}
		System.out.println("Medicine " + medicineName + " not found");
    }
    
	
	public void EntityUpdate() {
		saveMedicinesToCSV();
	}
	
	// Method to save updated medicines to Medicine_List.csv
	private void saveMedicinesToCSV() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Medicine_List.csv"))) {
			// Write header for CSV file
			writer.write("Medicine Name,Stock,Low Stock Level Alert\n");
			for (Medicine medicine : medicines) {
				writer.write(String.format("%s,%d,%d\n",
					medicine.getName(),
					medicine.getInitialStock(),
					medicine.getLowStockLevelAlert()));
			}
			System.out.println("Medicine inventory updated in Medicine_List.csv.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
