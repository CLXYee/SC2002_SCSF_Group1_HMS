package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowAdministratorMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.AdministratorCtrl;

public class AdministratorInput implements IGetOperationInput{
	private AdministratorCtrl administratorCtrl = null;
	private ShowMenu menu = null;
	//private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 

	public AdministratorInput() {
		this.administratorCtrl = new AdministratorCtrl();
		//this.appointmentOutcomeRecordCtrl = this.pharmacistCtrl;
		this.menu = new ShowAdministratorMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			administratorCtrl.staffManagement();
			break;
		case 2:
			administratorCtrl.appointmentManagement();;
			break;
		case 3:
			administratorCtrl.inventoryManagement();;
			break;
		case 4:
			//Logout
			administratorCtrl.EntityUpdate();
			break;
		}
		System.out.println();
	}
}
