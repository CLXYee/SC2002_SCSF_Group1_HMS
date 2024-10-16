package HospitalManagementSystem;

import operations.*;

public class MainCtrl {
	private Role role;
	private String hospitalID;
	private String name;
	private PatientCtrl patientCtrl = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private Perform performOperation;
	Operation operation = null;
	
	public MainCtrl(String inputRole, String hospitalID, String name) {
		this.role = Role.valueOf(inputRole);
		this.hospitalID = hospitalID;
		this.name = name;
		switch (role) {
		case Role.PATIENT: 
			this.patientCtrl = new PatientCtrl(this.hospitalID);
			this.medicalRecordCtrl = this.patientCtrl;
			this.performOperation = this.patientCtrl;
			this.operation = new PatientOperation();
			break;
		}
	}
	
	public void showOperation() {
		operation.showOperation();
	}
	
	public void getOperationInput(int i) {
		performOperation.perform(i);
	}
}
