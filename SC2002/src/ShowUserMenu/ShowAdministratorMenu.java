package ShowUserMenu;


//show the admin menu to the admin
public class ShowAdministratorMenu implements ShowMenu {

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("1. View and Manage Hospital Staff");
		System.out.println("2. View Appointments details");
		System.out.println("3. View and Manage Medication Inventory");
		System.out.println("4. Approve Replenishment Requests");
		System.out.println("5. Logout");

	}

}
