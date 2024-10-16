package operations;

public class PharmacistOperation implements Operation {

	@Override
	public void showOperation() {
		// TODO Auto-generated method stub
		System.out.println("1. View Appointment Outcome Record");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Request ");
		System.out.println("5. Logout");
	}

}
