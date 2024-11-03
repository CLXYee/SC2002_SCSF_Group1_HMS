package userInfoControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


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
    private Map<String, Integer> roleIdCounters = new HashMap<>();

	
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
		
		for (User staff : staffList) {
            String role = staff.getRole().name();
            String id = staff.getHospitalId();

            // Extract the numeric part of the ID (e.g., "D1001" -> 1001, "A1001" -> 1001, "H1001" -> 1001)
            int idNumber = Integer.parseInt(id.replaceAll("\\D", ""));

            // Update the counter for the role if the current ID number is higher
            roleIdCounters.put(role, Math.max(roleIdCounters.getOrDefault(role, 0), idNumber));
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
    
    public void displayStaff() {
    	List<User> clonedStaffList = new ArrayList<>();
    	for (User staff : staffList) {
    	    try {
    	    	clonedStaffList.add((User) staff.clone());
    	    } catch (CloneNotSupportedException e) {
    	        e.printStackTrace();
    	    }
    	}

    	
    	System.out.println("Staff list sort by: ");
    	System.out.println("1. Role");
    	System.out.println("2. Name");
    	System.out.println("3. Age");
    	System.out.println("4. Gender");
    	System.out.println("5. Hospital ID");

    	Scanner sc = new Scanner(System.in);
    	int choice = sc.nextInt();
    	switch (choice) {
	        case 1:
	            Collections.sort(staffList, Comparator.comparing(User::getRole));
	            break;
	        case 2:
	            Collections.sort(staffList, Comparator.comparing(User::getName));
	            break;
	        case 3:
	            Collections.sort(staffList, Comparator.comparingInt(User::getAge));
	            break;
	        case 4:
	            Collections.sort(staffList, Comparator.comparing(User::getGender));
	            break;
	        case 5:
	            Collections.sort(staffList, Comparator.comparing(User::getHospitalId));
	            break;
	        default:
	            System.out.println("Invalid sort option. Showing unsorted list.");
	            break;
    	}

	    for (int i = 0; i < staffList.size(); i++) {
	        System.out.println("Role: " + staffList.get(i).getRole().name());
	        System.out.println("Staff ID: " + staffList.get(i).getHospitalId());
	        System.out.println("Name: " + staffList.get(i).getName());
	        System.out.println("Gender: " + staffList.get(i).getGender());
	        System.out.println("Age: " + staffList.get(i).getAge());
	        System.out.println("---------------");
	    }
	}
	
    public void addStaff() {
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Please select role: ");
    	System.out.println("1. Doctor");
    	System.out.println("2. Pharmacist");
    	System.out.println("3. Administrator");
    	int choice = sc.nextInt();
    	Role role = Role.DOCTOR;
    	switch (choice) {
    		case 1:
    			role = Role.DOCTOR;
    			break;
    		case 2:
    			role = Role.PHARMACIST;
    			break;
    		case 3:
    			role = Role.ADMINISTRATOR;
    			break;
    	}
    	
    	int nextIdNumber = roleIdCounters.getOrDefault(role.toString(), 0) + 1;
        roleIdCounters.put(role.toString(), nextIdNumber);

        String rolePrefix;
        switch (role.toString()) {
            case "DOCTOR":
                rolePrefix = "D";
                break;
            case "PHARMACIST":
                rolePrefix = "P";
                break;
            case "ADMINISTRATOR":
                rolePrefix = "A";
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        hospitalID = rolePrefix + String.format("%04d", nextIdNumber);
        System.out.println(hospitalID);
        
        System.out.println("Please input name: ");
        String name = sc.next();
        
        System.out.println("Please select gender: ");
        System.out.println("1. Male");
    	System.out.println("2. Female");
    	choice = sc.nextInt();
    	String gender  = "Male";
    	switch (choice) {
    		case 1:
    			gender = "Male";
    			break;
    		case 2:
    			gender = "Female";
    			break;
    	}
    	
        System.out.println("Please input age: ");
        int age = sc.nextInt();
        
        User newStaff = new User(role, hospitalID, name, gender, age);
        this.staffList.add(newStaff);
    }
    
    public void updateStaff() {
    	
    }
    
    public void removeStaff() {
    	
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
    
    public void addNewMedication() {
    	
    }
    
    public void removeMedication() {
    	
    }
    
    public void updateStockLevel() {
    	
    }
    
    public void updateStockLowLevelAlert() {
    	
    }
    
    
    public void approveReplenishRequest() {
    	System.out.println("Please input the medicine name for replenish:");
		Scanner sc = new Scanner(System.in);
		String medicineName = sc.next();
		for (int i = 0; i < this.medicines.size(); i++) {
   			if (medicineName.equalsIgnoreCase(medicines.get(i).getName())){
   				//medicines.get(i).setReplenishRequest();
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
					//medicine.getInitialStock(),
					medicine.getLowStockLevelAlert()));
			}
			System.out.println("Medicine inventory updated in Medicine_List.csv.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
