package userInfoControl;

/**
 * Interface for managing appointments. Provides methods to schedule, reschedule,
 * cancel, and view scheduled appointments.
 */
public interface AppointmentCtrl {
	
	/**
     * Schedules a new appointment based on the provided details.
     */
	public abstract void scheduleAppointment();
	
	/**
     * Reschedules an existing appointment by modifying its details.
     */
	public abstract void rescheduleAppointment();
	
	/**
     * Cancels a previously scheduled appointment.
     */
	public abstract void cancelAppointment();
	
	/**
     * Views the details of scheduled appointments.
     */
	public abstract void viewScheduledAppointment();
}
