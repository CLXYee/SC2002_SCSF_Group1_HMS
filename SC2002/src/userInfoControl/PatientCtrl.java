package userInfoControl;

import userInfo.MedicalRecord;

public class PatientCtrl implements MedicalRecordCtrl, GetOperationInput {
	private MedicalRecord medicalRecord;
	
	public PatientCtrl(String hospitalID) {
		this.medicalRecord = new MedicalRecord(hospitalID);
	}
	
	public void showMedicalRecord() {
		System.out.println("Show medical record for patient");
		System.out.println("Patient ID 	| " + medicalRecord.getPatientID());
		System.out.println("Name 		| " + medicalRecord.getName());
		System.out.println("Gender 		| " + medicalRecord.getGender());
	}
	
	public void getOperationInput(int i) {
		switch(i) {
		case 1:
			showMedicalRecord();
		}
	}
}
