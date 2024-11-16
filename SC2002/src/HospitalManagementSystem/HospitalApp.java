package HospitalManagementSystem;

import java.io.File;
import java.util.Scanner;

import mainSystemControl.*;

/**
 * The HospitalApp class serves as the entry point for the Hospital NTU Management System.
 * It facilitates user authentication and interaction with the main control system of the hospital.
 */
public class HospitalApp{
	/**
     * The main method serves as the entry point of the Hospital NTU Management System.
     * It prompts the user to log in, authenticates the user, and then allows interaction with the system.
     * 
     * @param args Command line arguments (not used in this implementation).
     */
	public static void main(String[] args){
		boolean isLogin = false;
		updateData();
		
		System.out.println("Welcome to Hospital NTU.");
		System.out.println("Please login to system to continue.");
		System.out.println("===================================");
		String[] data;
		data = getLoginInput();
		if (data!=null) {isLogin = true;}
		
		MainCtrl mainCtrl = new MainCtrl(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
		
		do {
			isLogin = mainCtrl.getInput();
		} while (isLogin);
    }
	
	/**
     * Prompts the user for login input (role, ID, and password) and attempts to authenticate the user.
     * Repeats until valid credentials are provided.
     * 
     * @return A String array containing the authenticated user's data, or null if authentication fails.
     */
	public static String[] getLoginInput() {
		String loginRole;
		String loginHospitalID;
		String loginPassword;
		String[] data = null;
		
		Scanner sc = new Scanner(System.in);	
    	
    	do {
	    	System.out.print("Input Role: ");
	    	loginRole = sc.next();
	    	System.out.print("Input ID: ");
	    	loginHospitalID = sc.next();
	    	System.out.print("Input password: ");
	    	loginPassword = sc.next();
	    	
			loginRole = loginRole.toUpperCase();
	    	data = LoginCtrl.authenticate(loginRole, loginHospitalID, loginPassword);
    	} while (data==null);
    	
    	// Return the data of user with ID and Name
    	return data;
	}
	
	/**
     * Checks for the existence of temporary files that store updated staff and patient data.
     * If the files exist, it renames them to replace the original CSV files.
     * If no updates are detected, it prints a message indicating no updates.
     */
	public static void updateData() {
		File updatedFile1 = new File("./tempStaff.csv");
		File updatedFile2 = new File("./tempPatient.csv");
		
        if (updatedFile1.exists()) {
        	File originalFile = new File("./Staff_List.csv");
        	if (originalFile.delete()) {
                updatedFile1.renameTo(originalFile);
            }
        }else if (updatedFile2.exists()) {
        	File originalFile = new File("./Patient_List.csv");
        	if (originalFile.delete()) {
                updatedFile2.renameTo(originalFile);
            }
        }else System.out.println("Data updated");
        
	}
	
}