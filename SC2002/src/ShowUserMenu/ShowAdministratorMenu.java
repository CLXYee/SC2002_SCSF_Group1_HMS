package ShowUserMenu;


/**
 * Displays the menu options for the Administrator role in the hospital management system.
 * Implements the {@link ShowMenu} interface.
 */
public class ShowAdministratorMenu implements ShowMenu {
	
	/**
     * Displays the menu options available for the Administrator.
     * The menu includes:
     * <ul>
     *     <li>Staff Management</li>
     *     <li>Appointment Management</li>
     *     <li>Inventory Management</li>
     *     <li>Logout</li>
     * </ul>
     */
	@Override
	public void showMenu() {
		System.out.println("1. Staff Management");
		System.out.println("2. Appointment Management");
		System.out.println("3. Inventory Management");
		System.out.println("4. Logout");
	}
}
