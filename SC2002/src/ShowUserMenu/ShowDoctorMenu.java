package ShowUserMenu;

/**
 * Displays the menu options for the Doctor role in the hospital management system.
 * Implements the {@link ShowMenu} interface.
 */
public class ShowDoctorMenu implements ShowMenu {
	
	/**
     * Displays the menu options available for the Doctor.
     * The menu includes:
     * <ul>
     *     <li>View Patient Medical Records</li>
     *     <li>Update Patient Medical Records</li>
     *     <li>View Personal Schedule</li>
     *     <li>Set Availability for Appointments</li>
     *     <li>Accept or Decline Appointment Requests</li>
     *     <li>View Upcoming Appointments</li>
     *     <li>Record Appointment Outcome</li>
     *     <li>Logout</li>
     * </ul>
     */
	@Override
	public void showMenu() {
		System.out.println("1. View Patient Medical Records");
		System.out.println("2. Update Patient Medical Records ");
		System.out.println("3. View Personal Schedule");
		System.out.println("4. Set Availability for Appointments");
		System.out.println("5. Accept or Decline Appointment Requests ");
		System.out.println("6. View Upcoming Appointments");
		System.out.println("7. Record Appointment Outcome");
		System.out.println("8. Logout");
	}

}
