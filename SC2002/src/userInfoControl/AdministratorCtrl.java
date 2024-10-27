package userInfoControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mainSystemControl.Role;
import userInfo.Appointment;
import userInfo.AppointmentOutcomeRecord;
import userInfo.Medicine;
import userInfo.User;

public class AdministratorCtrl implements IMedicineView, IAdminMedicineCtrl, StaffManagement{
	private List<User> staffList = new ArrayList<>();
	private List<Medicine> medicines = new ArrayList<>();
	private int counter = 0; 
	
	public AdministratorCtrl() {
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
		        Medicine medicine = new Medicine(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));

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
    
    public void viewMedicationInventory() {
		System.out.println("================================================================================");
   		System.out.println("List of medicines");
		System.out.println("");
   		for (int i = 0; i < this.medicines.size(); i++) {
   			System.out.println("Medicine name: " + medicines.get(i).getName());
   			System.out.println("Initial Stock: " + medicines.get(i).getInitialStock());
   			System.out.println("Low Stock Level Alert: " + medicines.get(i).getLowStockLevelAlert());
			System.out.println("--------------------------");
   		}
		System.out.println("================================================================================");
	}
    
    public void viewStaff() {
		for (int i = 0; i < staffList.size(); i++) {
			System.out.println("Role: " + staffList.get(i).getRole().name());
			System.out.println("Staff ID: " + staffList.get(i).getHospitalId());
			System.out.println("Name: " + staffList.get(i).getName());
			System.out.println("Gender: " + staffList.get(i).getGender());
			System.out.println("Age: " + staffList.get(i).getAge());
		}
	}
	
	public void appointmentManagement() {
		
	}
	
	public void inventoryManagement() {
		
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
