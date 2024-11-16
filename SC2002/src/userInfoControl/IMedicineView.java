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
	
	/**
     * Updates the medication entity records in persistent storage, such as a database or file.
     *
     * @return {@code true} if the medication records were successfully updated; {@code false} otherwise.
     */
	public abstract boolean updateMedicineEntity();
}
