package mainSystemControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Handles user authentication for the hospital management system.
 * This class validates user credentials against role-specific data stored in CSV files.
 */
public class LoginCtrl {
	/**
     * Authenticates a user based on their role, hospital ID, and password.
     *
     * @param loginRole       The role of the user attempting to log in (e.g., PATIENT, DOCTOR, PHARMACIST, ADMINISTRATOR).
     * @param loginHospitalID The hospital ID associated with the user.
     * @param loginPassword   The password provided by the user for authentication.
     * @return A string array containing the user's details if authentication is successful.
     *         The array includes selected columns of user data from the corresponding CSV file.
     *         Returns {@code null} if authentication fails.
     */
	public static String[] authenticate(String loginRole, String loginHospitalID, String loginPassword) {
		String csvFile = ""; // Path to the CSV file based on the user's role
		String line;
		String csvSplitBy = ","; // Delimiter for CSV columns
		
        // Determine the role and corresponding file path
		Role role = Role.valueOf(loginRole);
		switch (role) {
		case PATIENT:
			csvFile = "./Patient_List.csv";
			break;
		case DOCTOR:
			csvFile = "./Staff_List.csv";
			break;
		case PHARMACIST:
			csvFile = "./Staff_List.csv";
			break;
		case ADMINISTRATOR:
			csvFile = "./Staff_List.csv";
			break;
		}

        // Attempt to read and authenticate from the CSV file 
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {		    
			while ((line = br.readLine()) != null) {
		        // Split the line into columns using the delimiter
		        String[] data = line.split(csvSplitBy);
		        
		        if (loginHospitalID.equals(data[2]) && loginPassword.equals(data[0])) {
		        	System.out.println("Login successful");
		        	// Return the list of data read
		        	data = Arrays.copyOfRange(data, 1, 6);
		        	return data;
		        }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
        // If authentication fails
		System.out.println("Invalid ID or password, please try again");
		return null;
	}
}