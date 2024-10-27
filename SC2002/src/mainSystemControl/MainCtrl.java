package mainSystemControl;

import java.util.Scanner;

import userInfo.User;

public class MainCtrl {
	private User user;
	private IGetOperationInput operationInput = null;
	
	public MainCtrl(String inputRole, String hospitalID, String name, String gender, int age) {
		user = new User(Role.valueOf(inputRole), hospitalID, name, gender, age);
		switch (user.getRole()) {
		case Role.PATIENT: 
			this.operationInput = new PatientInput(user.getHospitalId());
			break;
		case Role.PHARMACIST: 
			this.operationInput = new PharmacistInput();
			break;
		case Role.ADMINISTRATOR: 
			this.operationInput = new AdministratorInput();
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