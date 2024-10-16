package HospitalManagementSystem;

public class MainCtrl {
	private Role role;
	private String hospitalID;
	private String name;
	private MedicalRecordCtrl medicalRecordCtrl; 
	
	public MainCtrl(String inputRole, String hospitalID, String name) {
		this.role = Role.valueOf(inputRole);
		this.hospitalID = hospitalID;
		this.name = name;
		switch (role) {
		case Role.PATIENT: 
			medicalRecordCtrl = new PatientCtrl(this.hospitalID);
			break;
		}
	}
	
	
}
