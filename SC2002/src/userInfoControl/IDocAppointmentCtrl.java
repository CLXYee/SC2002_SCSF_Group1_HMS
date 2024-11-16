package userInfoControl;

/**
 * Interface for controlling doctor appointment-related operations.
 * Provides methods for updating appointment requests, viewing upcoming appointments,
 * and recording appointment outcomes.
 */
public interface IDocAppointmentCtrl {
	
	/**
     * Updates an existing appointment request with new information.
     */
	public abstract void updateAppointmentRequest();
	
	/**
     * Views the details of upcoming appointments.
     */
	public abstract void viewUpcomingAppointment();
	
	/**
     * Records the outcome of a completed appointment, including any notes or prescriptions.
     */
	public abstract void recordAppointmentOutcome();
}