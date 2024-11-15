package ShowUserMenu;

/**
 * Interface for displaying the menu options to users based on their role.
 * Each role (e.g., Patient, Doctor, Administrator, Pharmacist) will have a specific implementation of this interface
 * to show the appropriate menu.
 */
public interface ShowMenu {
	/**
     * Displays the menu options for the user.
     * This method should be implemented by classes representing different user roles to show the corresponding menu.
     */
	public abstract void showMenu();
}
