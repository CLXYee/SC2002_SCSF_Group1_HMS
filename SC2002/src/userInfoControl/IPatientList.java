package userInfoControl;

/**
 * The IPatientList interface provides a method for viewing patient IDs.
 * This interface is designed to be implemented by classes that handle 
 * patient-related functionalities, particularly the retrieval and display 
 * of patient identification details.
 */
public interface IPatientList {

	/**
     * Displays the list of patient IDs.
     * Implementing classes should provide a concrete implementation for 
     * how patient IDs are retrieved and displayed.
     */
	public abstract void viewPatientIDs();
}
