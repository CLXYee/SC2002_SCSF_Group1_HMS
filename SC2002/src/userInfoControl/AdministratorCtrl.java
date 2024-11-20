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
import java.util.InputMismatchException;
import java.util.Map;


import mainSystemControl.Role;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import userInfo.Medicine;
import userInfo.User;
import CSV.MedicineCSVOperator;
import CSV.StaffCSVOperator;
import CSV.AppointmentCSVOperator;
import CSV.DoctorCSVOperator;

/**
 * AdministratorCtrl manages hospital staff, appointments, and medication inventory. 
 * It provides functionalities such as adding, updating, viewing, and removing staff and medication.
 */
public class AdministratorCtrl implements IMedicineView, InventoryManagement, StaffManagement, IViewAppointment{
	private String curhospitalID;
	private List<User> staffList = new ArrayList<>();
	private List<Appointment> appointments = new ArrayList<>();
	private List<AppointmentOutcomeRecord> appointmentsOutcomeRecord = new ArrayList<>();
	private List<Medicine> medicines = new ArrayList<>();
    private Map<String, Integer> roleIdCounters = new HashMap<>();
    private Map<String, String> password = new HashMap<>();
    private MedicineCSVOperator medicineoperator = new MedicineCSVOperator();
    private StaffCSVOperator staffoperator = new StaffCSVOperator();
    private DoctorCSVOperator doctoroperator = new DoctorCSVOperator();
    private AppointmentCSVOperator appointmentoperator = new AppointmentCSVOperator();
	private int counter = 0; 
	private int tracker = 0;
	
	/**
     * Constructor to initialize the AdministratorCtrl with hospital ID and data from CSV files.
     *
     * @param hospitalID the unique identifier for the hospital
     */
	public AdministratorCtrl(String hospitalID) {
		this.curhospitalID = hospitalID;
		ArrayList<String> tempData = new ArrayList<>();
		
		tempData = staffoperator.readFile(null, 3);
		
		//use to get the id tracker
		roleIdCounters.put(Role.DOCTOR.toString(), staffoperator.getDoctorIDTracker());
		roleIdCounters.put(Role.PHARMACIST.toString(), staffoperator.getPharIDTracker());
		roleIdCounters.put(Role.ADMINISTRATOR.toString(), staffoperator.getAdminIDTracker());
		
		//use to extract the data into the entity class
		for(String i: tempData) {
			String[] temp = staffoperator.splitCommaCSVLine(i);
			
			Role role = Role.valueOf(temp[1]);
			User staff = new User(role, temp[2], temp[3], temp[4], Integer.parseInt(temp[5]));
			
	        this.staffList.add(staff);
	        
	        password.put(temp[2], temp[0]);
		}
		
		tempData = new ArrayList<>();
		tempData = appointmentoperator.readFile(null, 3);
		
		for(String i: tempData) {
			String[] temp = appointmentoperator.splitCommaCSVLine(i);
			
			Appointment appointment = new Appointment(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
			AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(Integer.valueOf(temp[0]), temp[4], temp[6], temp[7].split("\\s*,\\s*"), temp[8], temp[9]);

	        this.appointments.add(appointment);
			this.appointmentsOutcomeRecord.add(appointmentOutcomeRecord);
		}
		
		tempData = new ArrayList<>();
		tempData = medicineoperator.readFile(null, 3);
		
		for(String i: tempData) {
			String[] temp = medicineoperator.splitCommaCSVLine(i);
			
			ArrayList<String> submitter = new ArrayList<>(Arrays.asList(temp[5].split("\\s*,\\s*")));
			
			Medicine medicine = new Medicine(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3], Integer.parseInt(temp[4]), submitter, temp[6]);

	        this.medicines.add(medicine);
		}
		
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

    	Scanner sc = new Scanner(System.in);
    	boolean correctInput = false;
    	int choice = -1;
    	do {
	    	System.out.println("Staff list sort by: ");
	    	System.out.println("1. Role");
	    	System.out.println("2. Name");
	    	System.out.println("3. Age");
	    	System.out.println("4. Gender");
	    	System.out.println("5. Hospital ID");
	
	    	try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
    	} while (!correctInput);
	    
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
    	
    	boolean correctInput = false;
    	int choice = -1;
    	do {
	    	System.out.println("Please select role: ");
	    	System.out.println("1. Doctor");
	    	System.out.println("2. Pharmacist");
	    	System.out.println("3. Administrator");
	    	try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
    	} while (!correctInput);
    	
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

        String hospitalID = rolePrefix + String.format("%04d", nextIdNumber);
        
        System.out.println("Please input name: ");
        String name = sc.next();
        
        correctInput = false;
        choice = -1;
        do {
	        System.out.println("Please select gender: ");
	        System.out.println("1. Male");
	    	System.out.println("2. Female");
	    	
	    	try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
        } while (!correctInput);
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
        correctInput = false;
        int age = -1;
        do {
	    	try {
				age = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
        } while (!correctInput);
        
        System.out.println(role.toString() + " " + hospitalID + " added.");
        User newStaff = new User(role, hospitalID, name, gender, age);
        password.put(hospitalID, "password");
        this.staffList.add(newStaff);
        
        if(role.toString().equals("DOCTOR")) {
        	ArrayList<String> dataAdd = new ArrayList<>();
        	dataAdd.add(password.get(hospitalID));
        	dataAdd.add(hospitalID);
        	dataAdd.add(name);
        	dataAdd.add(gender);
        	
        	doctoroperator.addLineToFile(dataAdd);
        }
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
                boolean correctInput = false;
                int choice = -1;
        		do {
	        		System.out.println("Please select the following to update: ");
	                System.out.println("1. Name ");
	                System.out.println("2. Age ");
	                System.out.println("3. Gender ");
	                try {
						choice = sc.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Invalid input. Please enter a number.");
						sc.next();
						continue;
					}
					correctInput = true;
                } while (!correctInput);
        		
                switch (choice) {
                	case 1:
                        System.out.print("Please input updated name: ");
                        String newName = sc.nextLine();
                        staffList.get(i).setName(newName);
                        System.out.println("Name updated");
                        return;
                	case 2:
                        System.out.print("Please input updated age: ");
                        correctInput = false;
                        int newAge = -1;
                        do {
                        	try {
        						newAge = sc.nextInt();
        					} catch (InputMismatchException e) {
        						System.out.println("Invalid input. Please enter a number.");
        						sc.next();
        						continue;
        					}
        					correctInput = true;
                        } while (!correctInput);
                        staffList.get(i).setAge(newAge);
                        System.out.println("Age updated");
                        return;
                	case 3:
                        correctInput = false;
                        choice = -1;
                        do {
                	        System.out.println("Please select gender: ");
                	        System.out.println("1. Male");
                	    	System.out.println("2. Female");
                	    	
                	    	try {
                				choice = sc.nextInt();
                			} catch (InputMismatchException e) {
                				System.out.println("Invalid input. Please enter a number.");
                				sc.next();
                				continue;
                			}
                	    	correctInput = true;
                        } while (!correctInput);
                        
                    	String gender  = "Male";
                    	switch (choice) {
                    		case 1:
                    			gender = "Male";
                    			break;
                    		case 2:
                    			gender = "Female";
                    			break;
                    	}
                        
                        staffList.get(i).setGender(gender);
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
                    password.remove(id);
                    doctoroperator.deleteSpecificLine(id);
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
    	boolean correctInput = false;
   		int stockLevel = -1;
   		do {
	    	try {
				stockLevel = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
        } while (!correctInput);
    	
    	System.out.println("Please input new medication low stock level alert: ");
    	correctInput = false;
    	int lowStockLevelAlert = -1;
    	do {
	    	try {
	    		lowStockLevelAlert = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				sc.next();
				continue;
			}
	    	correctInput = true;
        } while (!correctInput);
    	
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
                boolean correctInput = false;
           		int newStockLevel = -1;
           		do {
        	    	try {
        	    		newStockLevel = sc.nextInt();
        			} catch (InputMismatchException e) {
        				System.out.println("Invalid input. Please enter a number.");
        				sc.next();
        				continue;
        			}
        	    	correctInput = true;
                } while (!correctInput);
           		
                medicines.get(i).setStockLevel(newStockLevel );
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
                boolean correctInput = false;
           		int newLowStockLevel = -1;
           		do {
        	    	try {
        	    		newLowStockLevel = sc.nextInt();
        			} catch (InputMismatchException e) {
        				System.out.println("Invalid input. Please enter a number.");
        				sc.next();
        				continue;
        			}
        	    	correctInput = true;
                } while (!correctInput);
           		
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
   	                	medicines.get(i).setReplenishRequestApprovedBy(curhospitalID);
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
    
	
    /**
     * Updates the staff entity records by preparing data from the `staffList` 
     * and formatting it into CSV rows. Includes special handling for initial roleIdCounter  
     * (Doctor, Pharmacist, Administrator) based on the `roleIdCounters` map.
     *
     * Each staff record includes password, role, hospital ID, name, gender, age, 
     * and optionally a role ID for specific roles.
     *
     * @return {@code true} if the CSV file was successfully updated; {@code false} otherwise.
     */
    public boolean updateStaffEntity() {
		ArrayList<String> dataStore = new ArrayList<>();// use to pass the entity class to the database
		int checker = 0; // use to store back the id tracker
		
		for(User i: staffList) {
			if(checker <= 2) {
				if(checker++ == 0) dataStore.add(String.format("%s,%s,%s,%s,%s,%s,%s", password.get(i.getHospitalId()), i.getRole().toString(), i.getHospitalId(), i.getName(), i.getGender(), i.getAge(), roleIdCounters.get("DOCTOR")));
				if(checker++ == 1) dataStore.add(String.format("%s,%s,%s,%s,%s,%s,%s", password.get(i.getHospitalId()), i.getRole().toString(), i.getHospitalId(), i.getName(), i.getGender(), i.getAge(), roleIdCounters.get("PHARMACIST")));
				if(checker++ == 2) dataStore.add(String.format("%s,%s,%s,%s,%s,%s,%s", password.get(i.getHospitalId()), i.getRole().toString(), i.getHospitalId(), i.getName(), i.getGender(), i.getAge(), roleIdCounters.get("ADMINISTRATOR")));
			}else {
				dataStore.add(String.format("%s,%s,%s,%s,%s,%s", password.get(i.getHospitalId()), i.getRole().toString(), i.getHospitalId(), i.getName(), i.getGender(), i.getAge()));
			}
		}
		
		if(staffoperator.updateCSVForAdmin(dataStore)) return true;
		return false;
	}
	
    /**
     * Updates the medicine entity records by preparing data from the `medicines` list
     * and formatting it into CSV rows. Each medicine's details, including replenish
     * request information, are processed and converted into a CSV-compatible format.
     *
     * Special handling is applied to the `ReplenishRequestSubmittedBy` field to format 
     * the list as a comma-separated string enclosed in double quotes.
     *
     * @return {@code true} if the CSV file was successfully updated; {@code false} otherwise.
     */
    public boolean updateMedicineEntity() {
		ArrayList<String> dataStore = new ArrayList<>(); //use to pass the entity class to the database
		
		for(Medicine i: medicines) {
			String temp = i.getReplenishRequestSubmittedBy().toString();
			temp = temp.replace("[", "").replace("]", "").replace(" ", "");
			
			dataStore.add(String.format("%s,%s,%s,%s,%s,%s,%s", i.getName(), i.getStockLevel(), i.getLowStockLevelAlert(), i.getReplenishRequestStatus(), i.getReplenishRequestAmount(), "\"" + temp + "\"", i.getReplenishRequestApprovedBy()));
		}
		
		if(medicineoperator.updateCSVForAdmin(dataStore)) return true;
		return false;
	}
}
