package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowAdministratorMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.AdministratorCtrl;
import userInfoControl.IMedicineView;
import userInfoControl.IAdminMedicineCtrl;
import userInfoControl.StaffManagement;

public class AdministratorInput implements IGetOperationInput{
	private AdministratorCtrl administratorCtrl = null;
	private IMedicineView iMedicineView;
	private IAdminMedicineCtrl iAdminMedicineCtrl;
	private StaffManagement staffManagement;

	private ShowMenu menu = null;
	//private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 

	public AdministratorInput() {
		this.administratorCtrl = new AdministratorCtrl();
		this.iMedicineView = this.administratorCtrl;
		this.iAdminMedicineCtrl = this.administratorCtrl;
		this.staffManagement = this.administratorCtrl;
		this.menu = new ShowAdministratorMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			System.out.println("1. Display list of staff");
			System.out.println("2. Manage staff");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				staffManagement.viewStaff();
			case 2:
				staffManagement.manageStaff();		
			}
			break;
		case 2:
			
			break;
		case 3:
			break;
		case 4:
			//Logout
			administratorCtrl.EntityUpdate();
			break;
		}
		System.out.println();
	}
}
