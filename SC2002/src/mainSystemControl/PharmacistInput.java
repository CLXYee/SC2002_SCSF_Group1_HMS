package mainSystemControl;

import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPharmacistMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.IMedicineView;
import userInfoControl.ISubmitReplenishRequest;
import userInfoControl.PharmacistCtrl;

public class PharmacistInput implements IGetOperationInput{
	private PharmacistCtrl pharmacistCtrl = null;
	private ShowMenu menu = null;
	private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 
	private IMedicineView iMedicineView;
	private ISubmitReplenishRequest iSubmitReplenishRequest;

	public PharmacistInput(String hospitalID) {
		this.pharmacistCtrl = new PharmacistCtrl(hospitalID);
		this.appointmentOutcomeRecordCtrl = this.pharmacistCtrl;
		this.iSubmitReplenishRequest = this.pharmacistCtrl;
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
			iSubmitReplenishRequest.submitReplenishRequest();;
			break;
		case 5:
			//Logout
			pharmacistCtrl.EntityUpdate();
			System.out.println("Loging out...\n");
			HospitalApp.main(null);
			break;
		}
		System.out.println();
	}
}
