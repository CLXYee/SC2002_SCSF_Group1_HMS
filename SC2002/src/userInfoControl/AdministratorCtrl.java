package userInfoControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * AdministratorCtrl manages hospital staff, appointments, and medication inventory. 
 * It provides functionalities such as adding, updating, viewing, and removing staff and medication.
 */
public class AdministratorCtrl implements IMedicineView, InventoryManagement, StaffManagement, IViewAppointment{
	private String hospitalID;
	private List<User> staffList = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	private List<Medicine> medicines = new ArrayList<>();
    private Map<String, Integer> roleIdCounters = new HashMap<>();
	private int counter = 0; 
	private int tracker = 0;
	
	/**
     * Constructor to initialize the AdministratorCtrl with hospital ID and data from CSV files.
     *
     * @param hospitalID the unique identifier for the hospital
     */
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
				// Extract the tracker for the roles from first three rows
				if (tracker <= 2) {
					if (tracker == 0) {
			            roleIdCounters.put(Role.DOCTOR.toString(), Math.max(roleIdCounters.getOrDefault(Role.DOCTOR.toString(), 0), Integer.parseInt(data[6])));
			            tracker++;
					}
					else if (tracker == 1) {
			            roleIdCounters.put(Role.PHARMACIST.toString(), Math.max(roleIdCounters.getOrDefault(Role.PHARMACIST.toString(), 0), Integer.parseInt(data[6])));
			            tracker++;
					}
					else if (tracker == 2) {
			            roleIdCounters.put(Role.ADMINISTRATOR.toString(), Math.max(roleIdCounters.getOrDefault(Role.ADMINISTRATOR.toString(), 0), Integer.parseInt(data[6])));
			            tracker++;
					}
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
				ArrayList<String> submitter = new ArrayList<>(Arrays.asList(data[5].split("\\s*,\\s*")));
				
				Medicine medicine = new Medicine(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), submitter, data[6]);

		        this.medicines.add(medicine);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
     * Splits a CSV line into an array of strings, handling quoted fields.
     *
     * @param line the CSV line to split
     * @return an array of strings representing the line's fields
     */
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
    
    /**
     * Displays the staff list sorted by a user-specified criterion.
     */
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
    
    /**
     * Adds a new staff member to the hospital.
     */
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
                rolePrefix = "H";
                break;
            case "ADMINISTRATOR":
                rolePrefix = "A";
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        hospitalID = rolePrefix + String.format("%04d", nextIdNumber);
        
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
        
        System.out.println(role.toString() + " " + hospitalID + " added.");
        User newStaff = new User(role, hospitalID, name, gender, age);
        this.staffList.add(newStaff);
    }
    
    /**
     * Updates details of an existing staff member.
     */
    public void updateStaff() {
        System.out.println("Please input staff ID to update: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        for (int i = 0; i < staffList.size(); i++) {
        	if (staffList.get(i).getHospitalId().equals(id)) {
                System.out.println("Please select the following to update: ");
                System.out.println("1. Name ");
                System.out.println("2. Age ");
                System.out.println("3. Gender ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                	case 1:
                        System.out.print("Please input updated name: ");
                        String newName = sc.nextLine();
                        staffList.get(i).setName(newName);
                        System.out.println("Name updated");
                        return;
                	case 2:
                        System.out.print("Please input updated age: ");
                        int newAge = sc.nextInt();
                        staffList.get(i).setAge(newAge);
                        System.out.println("Age updated");
                        return;
                	case 3:
                        System.out.print("Please input updated gender: ");
                        String newGender = sc.nextLine();
                        staffList.get(i).setGender(newGender);
                        System.out.println("Gender updated");
                        return;
                }
        	}
        }
        System.out.println("Hospital ID "+ id + " not found.");
    }
    
    /**
     * Removes a staff member from the hospital.
     */
    public void removeStaff() {
        System.out.println("Please input staff ID to remove: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        for (int i = 0; i < staffList.size(); i++) {
        	if (staffList.get(i).getHospitalId().equals(id)) {
                System.out.println("Please type 'REMOVE' if you insist on deleting: ");
                String confirmation = sc.next();
                if ("REMOVE".equals(confirmation)) {
                    staffList.remove(i);
                	System.out.println("Staff removed successfully.");
                    return;
                } else {
                    System.out.println("Removal canceled.");
                    return;
                }
        	}
        }
        System.out.println("Hospital ID "+ id + " not found.");
    }
    
    /**
     * Displays details of all appointments.
     */
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
    
    /**
     * Displays the medication inventory.
     */
    public void viewMedicationInventory() {
		System.out.println("================================================================================");
   		System.out.println("List of medicines");
		System.out.println("");
   		for (int i = 0; i < this.medicines.size(); i++) {
   			System.out.println("Medicine Name: " + medicines.get(i).getName());
   			System.out.println("Stock Level: " + medicines.get(i).getStockLevel());
   			System.out.println("Low Stock Level Alert: " + medicines.get(i).getLowStockLevelAlert());
   			System.out.println("Replenish Request Status: " + medicines.get(i).getReplenishRequestStatus());
   			System.out.println("Replenish Request Amount: " + medicines.get(i).getReplenishRequestAmount());
   			System.out.println("Replenish Request Submitted By: " + String.join(", ", medicines.get(i).getReplenishRequestSubmittedBy()));
   			System.out.println("Replenish Request Approved By: " + medicines.get(i).getReplenishRequestApprovedBy());
			System.out.println("--------------------------");
   		}
		System.out.println("================================================================================");
	}
    
    /**
     * Adds a new medication to the inventory.
     */
    public void addNewMedication() {
   		Scanner sc = new Scanner(System.in);
    	System.out.println("Please input new medication name: ");
    	String newMedicationName = sc.nextLine();
    	System.out.println("Please input new medication stock level: ");
    	int stockLevel = sc.nextInt();
    	System.out.println("Please input new medication low stock level alert: ");
    	int lowStockLevelAlert = sc.nextInt();
    	ArrayList <String> requester = new ArrayList<String>();
    	requester.add("NA");
    	Medicine newMedicine = new Medicine(newMedicationName, stockLevel, lowStockLevelAlert, "NA", 0, requester, "NA");
    	medicines.add(newMedicine);
        System.out.println("New medicine created successfully.");
    }
    
    /**
     * Removes a medication from the inventory.
     */
    public void removeMedication() {
    	System.out.println("Please input medication name to remove: ");
        Scanner sc = new Scanner(System.in);
        String medicationName = sc.nextLine();
        for (int i = 0; i < medicines.size(); i++) {
        	if (medicines.get(i).getName().equalsIgnoreCase(medicationName)) {
                System.out.println("Please type 'REMOVE' if you insist on deleting: ");
                String confirmation = sc.next();
                if ("REMOVE".equals(confirmation)) {
                    medicines.remove(i);
                	System.out.println("Medication removed successfully.");
                    return;
                } else {
                    System.out.println("Removal canceled.");
                    return;
                }
        	}
        }
        System.out.println("Medication "+ medicationName + " not found.");
    }
    
    /**
     * Updates the stock level of a medication.
     */
    public void updateStockLevel() {
    	System.out.println("Please input medication name to update stock level: ");
        Scanner sc = new Scanner(System.in);
        String medicationName = sc.nextLine();
        for (int i = 0; i < medicines.size(); i++) {
        	if (medicines.get(i).getName().equalsIgnoreCase(medicationName)) {
                System.out.println("Please input new stock level: ");
                int newStockLevel = sc.nextInt();
                medicines.get(i).setStockLevel(newStockLevel);
                System.out.println("New stock level updated successfully.");
                return;
        	}
        }
        System.out.println("Medication "+ medicationName + " not found.");
    }
    
    /**
     * Updates the low stock level alert for a medication.
     */
    public void updateStockLowLevelAlert() {
    	System.out.println("Please input medication name to update low stock level alert: ");
        Scanner sc = new Scanner(System.in);
        String medicationName = sc.nextLine();
        for (int i = 0; i < medicines.size(); i++) {
        	if (medicines.get(i).getName().equalsIgnoreCase(medicationName)) {
                System.out.println("Please input new low stock level alert: ");
                int newLowStockLevel = sc.nextInt();
                medicines.get(i).setLowStockLevelAlert(newLowStockLevel);
                System.out.println("New low stock level alert updated successfully.");
                return;
        	}
        }
        System.out.println("Medication "+ medicationName + " not found.");
    }
    
    /**
     * Approves a replenish request for a medication.
     */
    public void approveReplenishRequest() {
    	System.out.println("Please input the medicine name for replenish request:");
		Scanner sc = new Scanner(System.in);
		String medicineName = sc.next();
		for (int i = 0; i < this.medicines.size(); i++) {
   			if (medicineName.equalsIgnoreCase(medicines.get(i).getName())){
   				if (medicines.get(i).getReplenishRequestStatus().equals("Pending")) {
   					System.out.println("Please type 'APPROVE' to approve replenish request of amount " + medicines.get(i).getReplenishRequestAmount() + " for " + medicines.get(i).getName());
   	                String confirmation = sc.next();
   	                if ("APPROVE".equals(confirmation)) {
   	                	medicines.get(i).setReplenishRequestStatus("Approved");
   	   	   				medicines.get(i).setStockLevel(medicines.get(i).getStockLevel() + medicines.get(i).getReplenishRequestAmount());
   	   	   				System.out.println("Replenish request for " + medicineName + " approved");
   	                    return;
   	                } else {
   	                    System.out.println("Approval canceled.");
   	                    return;
   	                }	
   				}
   				else {
   	   				System.out.println("No replenish request for " + medicineName);
   	   				return;
   				}
   			}
   		}
		System.out.println("Medicine " + medicineName + " not found");
    }
    
	
	public void EntityUpdate() {
		//saveMedicinesToCSV();
	}
	
	// Method to save updated medicines to Medicine_List.csv
//	private void saveMedicinesToCSV() {
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./Medicine_List.csv"))) {
//			// Write header for CSV file
//			writer.write("Medicine Name,Stock,Low Stock Level Alert\n");
//			for (Medicine medicine : medicines) {
//				writer.write(String.format("%s,%d,%d\n",
//					medicine.getName(),
//					//medicine.getInitialStock(),
//					medicine.getLowStockLevelAlert()));
//			}
//			System.out.println("Medicine inventory updated in Medicine_List.csv.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
}
