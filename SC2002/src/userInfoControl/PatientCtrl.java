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
		System.out.println("Phone No. 	| " + medicalRecord.getPhoneNumber());
		System.out.println("Email Address | " + medicalRecord.getEmailAddress());
		System.out.println("===============================");
	}
	
	public void updateMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		int input;
		boolean checker;
		do {
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("Which of the following information you willing to change:");
			System.out.println("1. Phone number");
			System.out.println("2. Email address");
			System.out.println("3. Exit");
			System.out.println("=========================================================");
			System.out.println();
			input = sc.nextInt();
			
			switch(input){
				case 1:
					System.out.println("Please enter a new phone number");
					checker = medicalRecord.setPhoneNumber(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid phone number");
						checker = medicalRecord.setPhoneNumber(sc.next());
					}
					System.out.println("Phone number has been updated successfully!");
					System.out.println();
					break;
				case 2:
					System.out.println("Please enter a new email address");
					checker = medicalRecord.setEmailAddress(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid email address");
						checker = medicalRecord.setEmailAddress(sc.next());
					}
					System.out.println("Email Address has been updated successfully!");
					System.out.println();
					break;
				default:
					System.out.println("Please enter a valid option!");
					System.out.println();
			}
		}while(input != 3);
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			updateMedicalRecord();
		}
	}
}
