package HospitalManagementSystem;

import java.util.Scanner;

import mainSystemControl.*;

public class HospitalApp{
	public static void main(String[] args){
		boolean isLogin = false;
		int input = 0;
		
		System.out.println("Welcome to Hospital NTU.");
		System.out.println("Please login to system to continue.");
		System.out.println("===================================");
		String[] data;
		data = getLoginInput();
		if (data!=null) {isLogin = true;}
		
		MainCtrl mainCtrl = new MainCtrl(data[0], data[1], data[2]);
		
		do {
			mainCtrl.showMenu();
			input = getOperationInput();
			mainCtrl.getOperationInput(input);
		} while (isLogin);
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
	
	public static int getOperationInput() {
		System.out.println("Enter input for operation: ");
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
	}
}