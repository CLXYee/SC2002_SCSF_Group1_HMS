package mainSystemControl;

import java.util.Scanner;

import userInfo.User;

public class MainCtrl {
	private User user;
	private IGetOperationInput operationInput = null;
	
	public MainCtrl(String inputRole, String hospitalID, String name) {
		user = new User(Role.valueOf(inputRole), hospitalID, name);
		switch (user.getRole()) {
		case Role.PATIENT: 
			this.operationInput = new PatientInput(user.getHospitalId());
			break;
		case Role.PHARMACIST: 
			this.operationInput = new PharmacistInput();
			break;
		}
	}
	
	public void getInput() {
		operationInput.showMenu();
		operationInput.getOperationInput(getOperationInput());
	}
	
	public static int getOperationInput() {
		System.out.println("Enter input for operation: ");
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
	}
}