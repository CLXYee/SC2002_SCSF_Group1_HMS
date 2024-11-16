package userInfo;

import java.util.ArrayList;

import mainSystemControl.Role;

/**
 * Represents a Patient in the hospital management system.
 * This class extends the {@link User} class and adds a medical record specific to the patient.
 * The Patient class allows access to the patient's medical record and stores information related to the patient's role and personal details.
 */
public class Patient extends User{
	/**
     * The medical record associated with this patient.
     * Each patient has their own medical record, which is used to store information related to their health history.
     */
	MedicalRecord medicalRecord;
    
	/**
     * Constructs a new Patient object with the given details.
     * The patient's role is set to {@link Role#PATIENT} and a new medical record is created for the patient.
     *
     * @param hospitalID The unique identifier for the patient in the hospital system.
     * @param name The name of the patient.
     * @param gender The gender of the patient.
     * @param age The age of the patient.
     */
	public Patient(String hospitalID, String name, String gender, int age) {
    	super(Role.PATIENT, hospitalID, name, gender, age);
    	this.medicalRecord = new MedicalRecord(hospitalID);
    }
    
	/**
     * Retrieves the medical record associated with the patient.
     * 
     * @return The {@link MedicalRecord} object containing the patient's health information.
     */
    public MedicalRecord getMedicalRecord() {
    	return medicalRecord;
    }
}