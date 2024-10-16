package operations;

public class DoctorOperation implements Operation {

	@Override
	public void showOperation() {
		// TODO Auto-generated method stub
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
