package ShowUserMenu;


//show the admin menu to the admin
public class ShowAdministratorMenu implements ShowMenu {

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("1. Staff Management");
		System.out.println("2. Appointment Management");
		System.out.println("3. Inventory Management");
		System.out.println("4. Logout");

	}

}
