package userInfoControl;

/**
 * Interface for managing medical records.
 * Provides methods to view and update medical records.
 */
public interface MedicalRecordCtrl {
	
	/**
     * Displays the details of a medical record.
     */
	public abstract void showMedicalRecord();
	
	 /**
     * Updates the details of an existing medical record with new information.
     */
	public abstract void updateMedicalRecord();
}
