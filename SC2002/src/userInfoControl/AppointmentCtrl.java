package userInfoControl;

/**
 * Interface for managing appointments. Provides methods to schedule, reschedule,
 * cancel, and view scheduled appointments.
 */
public interface AppointmentCtrl {
	
	/**
     * View available timing slot for appointment
     */
	public abstract void viewAvailableSlots();
	
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
	
	/**
     * View past appointment outcome record
     */
	public abstract void viewPastRecords();
}
