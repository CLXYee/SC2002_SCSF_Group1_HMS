package SC2002.src.HospitalManagementSystem;

import HospitalManagementSystem.Users.role;

public class Test {
	public static void main(String[] args) {
		Users user = new Users("hj", "hi1234", role.PATIENT);
		
		System.out.println(user.gethospitalId());
	}
}
