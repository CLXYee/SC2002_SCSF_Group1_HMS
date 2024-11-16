package userInfoControl;

/**
 * Interface for managing inventory-related operations for medications.
 * Provides methods to add, remove, update medication stock, and approve replenishment requests.
 */
public interface InventoryManagement {
	
	/**
     * Adds a new medication to the inventory.
     */
	public abstract void addNewMedication();
	
	/**
     * Removes a medication from the inventory.
     */
	public abstract void removeMedication();
	
	/**
     * Updates the stock level of an existing medication in the inventory.
     */
	public abstract void updateStockLevel();
	
	/**
     * Updates the alert level for low stock of a medication.
     */
	public abstract void updateStockLowLevelAlert();
	
	/**
     * Approves a replenish request for a medication, typically when stock is low.
     */
	public abstract void approveReplenishRequest();
}