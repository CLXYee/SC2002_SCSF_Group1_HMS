package mainSystemControl;

import ShowUserMenu.*;
import userInfo.User;
import userInfoControl.GetOperationInput;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

public class MainCtrl {
	private User user;
	private PatientCtrl patientCtrl = null;
	private PharmacistCtrl pharmacistCtrl = null;
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
		case Role.PHARMACIST: 
			this.pharmacistCtrl = new PharmacistCtrl();
			this.AppointmentOutcomeRecordCtrl = this.pharmacistCtrl;
			this.operationInput = this.pharmacistCtrl;
			this.showMenu = new ShowPharmacistMenu();
			break;
		case Role.DOCTOR: 
			break;
		case Role.ADMINISTRATOR: 
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
