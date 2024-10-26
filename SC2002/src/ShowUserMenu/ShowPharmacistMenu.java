package ShowUserMenu;


//show the pharmacist menu to the pharmacist
public class ShowPharmacistMenu implements ShowMenu {

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("1. View Appointment Outcome Record");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Request ");
		System.out.println("5. Logout");
	}

}
