package userInfoControl;

/**
 * Interface for managing scheduling operations.
 * Provides methods to view personal schedules and set availability.
 */
public interface ISchedule {
	/**
     * Displays the personal schedule, showing upcoming appointments or tasks.
     */
	public abstract void viewPersonalSchedule();
	
	 /**
     * Sets the availability for scheduling appointments or tasks.
     */
	public abstract void setAvailability();
}