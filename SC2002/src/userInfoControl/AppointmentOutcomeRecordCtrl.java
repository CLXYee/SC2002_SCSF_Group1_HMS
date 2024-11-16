package userInfoControl;

/**
 * Interface for managing appointment outcome records. 
 * Provides methods to view and update the records of appointment outcomes.
 */
public interface AppointmentOutcomeRecordCtrl 
{

	/**
     * Views the details of an appointment outcome record.
     */
	public abstract void viewAppointmentOutcomeRecord();
	
	/**
     * Updates an existing appointment outcome record with new information.
     */
	public abstract void updateAppointmentOutcomeRecord();	
}
