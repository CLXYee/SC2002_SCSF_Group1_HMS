package HospitalManagementSystem;

import java.util.Scanner;

public class HospitalApp{
	public static void main(String[] args){
		String[] data;
		data = getLoginInput();
		MainCtrl mainCtrl = new MainCtrl(data[0], data[1], data[2]);
		System.out.println(mainCtrl.getID());
    }
	
	public static String[] getLoginInput() {
		String loginRole;
		String loginHospitalID;
		String loginPassword;
		String[] data;
		
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
}