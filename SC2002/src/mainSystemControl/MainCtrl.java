package mainSystemControl;

import ShowUserMenu.*;
import userInfo.User;
import userInfoControl.GetOperationInput;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

public class MainCtrl {
	private User user;
	private PatientCtrl patientCtrl = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private GetOperationInput operationInput;
	ShowMenu showMenu = null;
	
	public MainCtrl(String inputRole, String hospitalID, String name) {
		user = new User(Role.valueOf(inputRole), hospitalID, name);
		switch (user.getRole()) {
		case Role.PATIENT: 
			this.patientCtrl = new PatientCtrl(user.getHospitalId());
			this.medicalRecordCtrl = this.patientCtrl;
			this.operationInput = this.patientCtrl;
			this.showMenu = new ShowPatientMenu();
			break;
			
		case Role.DOCTOR:
			//this.doctorCtrl = new doctorCtrl(user.getHospitalId());
			break;
		}
	}
	
	public void showMenu() {
		showMenu.showMenu();
	}
	
	public void getOperationInput(int input) {
		operationInput.getOperationInput(input);
	}
}
