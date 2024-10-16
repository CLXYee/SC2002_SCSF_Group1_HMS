package userInfo;

import java.util.ArrayList;

import mainSystemControl.Role;

public class Patient extends User{
    MedicalRecord medicalRecord;
    
    public Patient(String hospitalID, String name) {
    	super(Role.PATIENT, hospitalID, name);
    	this.medicalRecord = new MedicalRecord(hospitalID);
    }
    
    public MedicalRecord getMedicalRecord() {
    	return medicalRecord;
    }
}