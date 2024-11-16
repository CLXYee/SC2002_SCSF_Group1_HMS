package userInfoControl;

/**
 * Interface for managing staff information and operations.
 * Provides methods to display, add, update, and remove staff,
 * as well as to update staff records in persistent storage.
 */
public interface StaffManagement {
	
	/**
     * Displays the list of staff members and their details.
     */
	public abstract void displayStaff();
	
	/**
     * Adds a new staff member to the system.
     */
	public abstract void addStaff();
	
	/**
     * Updates the details of an existing staff member.
     */
	public abstract void updateStaff();
	
	 /**
     * Removes a staff member from the system.
     */
	public abstract void removeStaff();
	
	/**
     * Updates the staff entity records in persistent storage, such as a database or file.
     *
     * @return {@code true} if the staff records were successfully updated; {@code false} otherwise.
     */
	public abstract boolean updateStaffEntity();
}
