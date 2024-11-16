package mainSystemControl;

import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPharmacistMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.IMedicineView;
import userInfoControl.ISubmitReplenishRequest;
import userInfoControl.PharmacistCtrl;

/**
 * Handles the input and menu interactions for the pharmacist role within the hospital management system.
 * This class provides an interface for pharmacists to perform various operations such as viewing appointment outcomes,
 * managing medication inventory, and submitting replenish requests.
 */
public class PharmacistInput implements IGetOperationInput{
	private PharmacistCtrl pharmacistCtrl = null;
	private ShowMenu menu = null;
	private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 
	private IMedicineView iMedicineView;
	private ISubmitReplenishRequest iSubmitReplenishRequest;

	/**
     * Constructor for the PharmacistInput class.
     *
     * @param hospitalID The unique identifier for the hospital where the pharmacist is associated.
     */
	public PharmacistInput(String hospitalID) {
		this.pharmacistCtrl = new PharmacistCtrl(hospitalID);
		this.appointmentOutcomeRecordCtrl = this.pharmacistCtrl;
		this.iSubmitReplenishRequest = this.pharmacistCtrl;
		this.iMedicineView = this.pharmacistCtrl;
		this.menu = new ShowPharmacistMenu();
	}
	
	/**
     * Displays the menu options available to the pharmacist.
     * The menu is specific to pharmacist operations and is displayed using the associated `ShowPharmacistMenu` implementation.
     */
	public void showMenu() {
		menu.showMenu();
	}
	
	/**
     * Handles user input for various pharmacist operations and invokes the appropriate functionality.
     *
     * @param input The input corresponding to the pharmacist's chosen operation.
     *              Options include viewing or updating appointment outcomes, viewing inventory, submitting replenish requests, or logging out.
     */
	public boolean getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
            // View appointment outcome records
			appointmentOutcomeRecordCtrl.viewAppointmentOutcomeRecord();
			return true;
		case 2:
            // Update appointment outcome records
			appointmentOutcomeRecordCtrl.updateAppointmentOutcomeRecord();
			pharmacistCtrl.updateAppointmentEntity();
			return true;
		case 3:
            // View medication inventory
			iMedicineView.viewMedicationInventory();
			return true;
		case 4:
            // Submit a replenish request
			iSubmitReplenishRequest.submitReplenishRequest();
			pharmacistCtrl.updateMedicineEntity();
			return true;
		case 5:
			//Logout
			System.out.println("Loging out...\n");
			pharmacistCtrl.updateMedicineEntity();
			return false;	
		default:
			//logging out
			System.out.println("Loging out...\n");
			pharmacistCtrl.updateMedicineEntity();
			return false;
		}
	}
}
