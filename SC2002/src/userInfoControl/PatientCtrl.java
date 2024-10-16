package userInfoControl;

import userInfo.MedicalRecord;
import java.util.Scanner;

public class PatientCtrl implements MedicalRecordCtrl, GetOperationInput {
	private MedicalRecord medicalRecord;
	
	public PatientCtrl(String hospitalID) {
		this.medicalRecord = new MedicalRecord(hospitalID);
	}
	
	public void showMedicalRecord() {
		System.out.println("Show medical record for patient");
		System.out.println("===============================");
		System.out.println("Patient ID 	| " + medicalRecord.getPatientID());
		System.out.println("Name 		| " + medicalRecord.getName());
		System.out.println("Gender 		| " + medicalRecord.getGender());
		System.out.println("===============================");
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
		}
	}
}
