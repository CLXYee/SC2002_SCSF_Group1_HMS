package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPharmacistMenu;
import userInfoControl.AppointmentCtrl;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.PharmacistCtrl;

public class PharmacistInput implements IGetOperationInput{
	private PharmacistCtrl pharmacistCtrl = null;
	private ShowMenu menu = null;
	private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 

	public PharmacistInput() {
		this.pharmacistCtrl = new PharmacistCtrl();
		this.appointmentOutcomeRecordCtrl = this.pharmacistCtrl;
		this.menu = new ShowPharmacistMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			appointmentOutcomeRecordCtrl.viewAppointmentOutcomeRecord();
			break;
		case 2:
			appointmentOutcomeRecordCtrl.updateAppointmentOutcomeRecord();
			break;
		case 3:
			pharmacistCtrl.viewMedicationInventory();
			break;
		case 4:
			pharmacistCtrl.submitReplenishRequest();;
			break;
		case 5:
			//Logout
			pharmacistCtrl.EntityUpdate();
			break;
		}
		System.out.println();
	}
}
