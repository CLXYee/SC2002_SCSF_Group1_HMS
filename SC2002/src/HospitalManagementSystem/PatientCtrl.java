package HospitalManagementSystem;

public class PatientCtrl implements MedicalRecordCtrl, Perform {
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
	
	public void perform(int i) {
		switch(i) {
		case 1:
			showMedicalRecord();
		}
	}
}
