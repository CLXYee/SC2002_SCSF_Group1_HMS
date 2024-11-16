package mainSystemControl;

import java.util.Scanner;
import java.util.InputMismatchException;

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
	 * Displays the menu to the user and processes their selected operation.
	 * Delegates the execution of the selected operation to the appropriate input handler.
	 * 
	 * @return true if the operation was successfully executed and the user wishes to continue, 
	 *         false if the operation involves logging out or exiting.
	 */
	public boolean getInput() {
		operationInput.showMenu();
		int input = 0;
		boolean correctInput=false;
		while (!correctInput) {
			try {
				input = getOperationInput();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, try again.");
				continue;
			}
			correctInput=true;
		}
		return operationInput.getOperationInput(input);
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