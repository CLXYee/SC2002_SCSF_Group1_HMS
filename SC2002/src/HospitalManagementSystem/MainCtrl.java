package HospitalManagementSystem;

public class MainCtrl {
	private String hospitalID;
	private Role role;
	private String name;
	
	public MainCtrl(String role, String hospitalID, String name) {
		this.hospitalID = hospitalID;
		this.role = Role.valueOf(role);
		this.name = name;
	}
	
	public String getID() {
		return hospitalID;
	}
}
