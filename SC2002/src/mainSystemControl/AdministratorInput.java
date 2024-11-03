package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowAdministratorMenu;
import userInfoControl.AppointmentOutcomeRecordCtrl;
import userInfoControl.AdministratorCtrl;
import userInfoControl.IMedicineView;
import userInfoControl.IViewAppointment;
import userInfoControl.StaffManagement;
import userInfoControl.InventoryManagement;


public class AdministratorInput implements IGetOperationInput{
	private AdministratorCtrl administratorCtrl = null;
	private IMedicineView iMedicineView;
	private StaffManagement staffManagement;
	private IViewAppointment iViewAppointment;
	private InventoryManagement inventoryManagement;


	private ShowMenu menu = null;
	//private AppointmentOutcomeRecordCtrl appointmentOutcomeRecordCtrl; 

	public AdministratorInput(String hospitalID) {
		this.administratorCtrl = new AdministratorCtrl(hospitalID);
		this.iMedicineView = this.administratorCtrl;
		this.iViewAppointment = this.administratorCtrl;
		this.staffManagement = this.administratorCtrl;
		this.inventoryManagement = this.administratorCtrl;
		this.menu = new ShowAdministratorMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		int choice;
		switch(input) {
		case 1:
			staffManagement.displayStaff();
			System.out.println("1. Add staff");
			System.out.println("2. Update staff");
			System.out.println("3. Remove staff");
			System.out.println("4. Exit");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
				staffManagement.addStaff();
				break;
			case 2:
				staffManagement.updateStaff();	
				break;
			case 3:
				staffManagement.removeStaff();
				break;
			case 4:
				break;
			default:
				break;
			}
			break;
		case 2:
			iViewAppointment.viewAppointments();
			break;
		case 3:
			iMedicineView.viewMedicationInventory();
			System.out.println("1. Add new medication");
			System.out.println("2. Remove medication");
			System.out.println("3. Update medication stock level");
			System.out.println("4. Update medication low stock level alert");
			System.out.println("5. Approve medication replenish request");
			System.out.println("6. Exit");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
				inventoryManagement.addNewMedication();
				break;
			case 2:
				inventoryManagement.removeMedication();
				break;
			case 3:
				inventoryManagement.updateStockLevel();;
				break;
			case 4:
				inventoryManagement.updateStockLowLevelAlert();
				break;
			case 5:
				inventoryManagement.approveReplenishRequest();
				break;
			case 6:
				break;
			default:
				break;
			}
			break;
		case 4:
			//Logout
			administratorCtrl.EntityUpdate();
			break;
		}
		System.out.println();
	}
}
