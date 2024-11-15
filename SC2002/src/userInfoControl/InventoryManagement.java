package userInfoControl;

public interface InventoryManagement {
	public abstract void addNewMedication();
	public abstract void removeMedication();
	public abstract void updateStockLevel();
	public abstract void updateStockLowLevelAlert();
	public abstract void approveReplenishRequest();
	public abstract boolean updateMedicineEntity();
}