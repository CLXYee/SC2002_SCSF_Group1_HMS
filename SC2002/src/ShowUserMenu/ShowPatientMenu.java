package ShowUserMenu;


/**
 * Class to display the menu options for patients.
 * This class implements the {@link ShowMenu} interface to show specific menu options available to patients.
 */
public class ShowPatientMenu implements ShowMenu {
	
	/**
     * Displays the menu options for a patient.
     * Each number corresponds to a specific action a patient can take, such as viewing medical records,
     * scheduling appointments, and managing existing appointments.
     */
	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("1. View Medical Record");
		System.out.println("2. Update Personal Information");
		System.out.println("3. View Available Appointment Slots");
		System.out.println("4. Schedule an Appointment");
		System.out.println("5. Reschedule an Appointment");
		System.out.println("6. Cancel an Appointment");
		System.out.println("7. View Scheduled Appointments");
		System.out.println("8. View Past Appointment Outcome Records");
		System.out.println("9. Logout");
	}
}
