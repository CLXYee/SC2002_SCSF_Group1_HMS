package ShowUserMenu;

/**
 * Class to display the menu options for pharmacists.
 * This class implements the {@link ShowMenu} interface to show specific menu options available to pharmacists.
 */
public class ShowPharmacistMenu implements ShowMenu {
	
	/**
     * Displays the menu options for a pharmacist.
     * Each number corresponds to a specific action a pharmacist can take, such as viewing appointment outcomes,
     * updating prescription statuses, managing the medication inventory, and submitting replenishment requests.
     */
	@Override
	public void showMenu() {
		System.out.println("1. View Appointment Outcome Record");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Request ");
		System.out.println("5. Logout");
	}

}
