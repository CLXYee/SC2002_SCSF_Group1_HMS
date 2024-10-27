package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPharmacistMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.IMedicineView;
import userInfoControl.IReplenishRequest;
import userInfoControl.PharmacistCtrl;

public class PharmacistInput implements IGetOperationInput{
	private PharmacistCtrl pharmacistCtrl = null;
	private ShowMenu menu = null;
	private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 
	private IMedicineView iMedicineView;
	private IReplenishRequest iReplenishRequest;

	public PharmacistInput() {
		this.pharmacistCtrl = new PharmacistCtrl();
		this.appointmentOutcomeRecordCtrl = this.pharmacistCtrl;
		this.iReplenishRequest = this.pharmacistCtrl;
		this.iMedicineView = this.pharmacistCtrl;
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
			iMedicineView.viewMedicationInventory();
			break;
		case 4:
			iReplenishRequest.submitReplenishRequest();;
			break;
		case 5:
			//Logout
			pharmacistCtrl.EntityUpdate();
			break;
		}
		System.out.println();
	}
}
