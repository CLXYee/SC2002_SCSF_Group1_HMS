package mainSystemControl;

import java.util.Scanner;

import userInfo.User;

/**
 * The main controller for handling user operations within the hospital management system.
 * It initializes the appropriate input handler based on the user's role and delegates operations.
 */
public class MainCtrl {
	/**
     * The interface for handling role-specific operations.
     */
	private IGetOperationInput operationInput = null;
	
	/**
     * Constructs a `MainCtrl` object, initializing the appropriate operation input handler
     * based on the user's role.
     *
     * @param inputRole  The role of the user (e.g., PATIENT, DOCTOR, PHARMACIST, ADMINISTRATOR).
     * @param hospitalID The hospital ID of the user.
     * @param name       The name of the user.
     * @param gender     The gender of the user.
     * @param age        The age of the user.
     */
	public MainCtrl(String inputRole, String hospitalID, String name, String gender, int age) {
        // Initialize the appropriate operation input handler based on the user's role
		switch (Role.valueOf(inputRole)) {
		case Role.PATIENT: 
			this.operationInput = new PatientInput(hospitalID, name, gender, age);
			break;
		case Role.DOCTOR:
			this.operationInput = new DoctorInput(hospitalID);
			break;
		case Role.PHARMACIST: 
			this.operationInput = new PharmacistInput(hospitalID);
			break;
		case Role.ADMINISTRATOR: 
			this.operationInput = new AdministratorInput(hospitalID);
			break;
		}
	}
	
	 /**
     * Displays the menu and processes the user's selected operation input.
     * Delegates operations to the role-specific input handler.
     */
	public void getInput() {
		operationInput.showMenu();
		operationInput.getOperationInput(getOperationInput());
	}
	
	/**
     * Prompts the user to enter their input for the desired operation.
     *
     * @return The integer value representing the user's operation choice.
     */
	public static int getOperationInput() {
		System.out.println("Enter input for operation: ");
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
	}
}