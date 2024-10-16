package mainSystemControl;

import ShowUserMenu.*;
import userInfoControl.GetOperationInput;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

public class MainCtrl {
	private Role role;
	private String hospitalID;
	private String name;
	private PatientCtrl patientCtrl = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private GetOperationInput operationInput;
	ShowMenu showMenu = null;
	
	public MainCtrl(String inputRole, String hospitalID, String name) {
		this.role = Role.valueOf(inputRole);
		this.hospitalID = hospitalID;
		this.name = name;
		switch (role) {
		case Role.PATIENT: 
			this.patientCtrl = new PatientCtrl(this.hospitalID);
			this.medicalRecordCtrl = this.patientCtrl;
			this.operationInput = this.patientCtrl;
			this.showMenu = new ShowPatientMenu();
			break;
		}
	}
	
	public void showMenu() {
		showMenu.showMenu();
	}
	
	public void getOperationInput(int i) {
		operationInput.getOperationInput(i);
	}
}
