package mainSystemControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LoginCtrl {
	public static String[] authenticate(String loginRole, String loginHospitalID, String loginPassword) {
		String csvFile = "";
		String line;
		String csvSplitBy = ","; // Delimiter for CSV columns
		
    	Role role = Role.valueOf(loginRole);
		
		switch (role) {
		case PATIENT:
			csvFile = "./Patient_List.csv";
			break;
		case DOCTOR:
		case PHARMACIST:
		case ADMINISTRATOR:
			csvFile = "./Staff_List.csv";
		}

		 
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {		    
			while ((line = br.readLine()) != null) {
		        // Split the line into columns using the delimiter
		        String[] data = line.split(csvSplitBy);
		        
		        if (loginHospitalID.equals(data[2]) && loginPassword.equals(data[0])) {
		        	System.out.println("Login successful");
		        	// Return the list of data read
		        	data = Arrays.copyOfRange(data, 1, 4);
		        	return data;
		        }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		System.out.println("Invalid ID or password, please try again");
		return null;
	}
}
