package userInfoControl;

/**
 * Interface for viewing medication-related information.
 * Provides a method to view the current inventory of medications.
 */
public interface IMedicineView {
	
	/**
     * Displays the current medication inventory, including stock levels and details.
     */
	public abstract void viewMedicationInventory();
}
