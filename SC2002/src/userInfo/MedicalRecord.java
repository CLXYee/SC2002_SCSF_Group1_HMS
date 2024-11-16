package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import CSV.MedicalRecordCSVOperator;

/**
 * Represents a patient's medical record in the system.
 * This class contains patient details, past diagnoses, treatments, and functionality for updating the medical record.
 */
public class MedicalRecord {
    
    /**
     * The unique patient ID.
     */
    private String patientID;
    
    /**
     * The name of the patient.
     */
    private String name;
    
    /**
     * The patient's date of birth.
     */
    private String dateOfBirth;
    
    /**
     * The patient's age.
     */
    private String age;
    
    /**
     * The patient's gender.
     */
    private String gender;
    
    /**
     * The patient's phone number.
     */
    private String phoneNumber;
    
    /**
     * The patient's email address.
     */
    private String emailAddress;
    
    /**
     * The patient's blood type.
     */
    private String bloodType;
    
    /**
     * The doctor in charge of the patient.
     */
    private String doctorInCharge;
    
    /**
     * A list of the patient's past diagnoses and treatments.
     */
    private ArrayList<String> pastDiagnosesTreatment = null;
    
    /**
     * The CSV operator used to read and update medical records.
     */
    private MedicalRecordCSVOperator csv = new MedicalRecordCSVOperator();

    /**
     * Constructs a MedicalRecord object using patient data from the specified hospital ID.
     * 
     * @param hospitalID The hospital ID for which the record is retrieved.
     */
    public MedicalRecord(String hospitalID){
        List<String> data = csv.readFile(hospitalID, 0);
        
        this.patientID = data.get(2);
        this.name = data.get(3);
        this.gender = data.get(4);
        this.age = data.get(5);
        this.dateOfBirth = data.get(6);
        this.phoneNumber = data.get(7);
        this.emailAddress = data.get(8);
        this.bloodType = data.get(9);
        this.doctorInCharge = data.get(10);
        
        String diagnosesTreatmentData = data.get(11);
        
        this.pastDiagnosesTreatment = new ArrayList<>(
        	    Arrays.asList(diagnosesTreatmentData.split("(?<=\\])\\s*;\\s*(?=\\[)"))
        	);
        // System.out.println(pastDiagnosesTreatment);
    }

    /**
     * Gets the unique patient ID.
     * 
     * @return The patient ID.
     */
    public String getPatientID(){
        return patientID;
    }

    /**
     * Gets the name of the patient.
     * 
     * @return The name of the patient.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the date of birth of the patient.
     * 
     * @return The patient's date of birth.
     */
    public String getDateOfBirth(){
        return dateOfBirth;
    }

    /**
     * Gets the gender of the patient.
     * 
     * @return The patient's gender.
     */
    public String getGender(){
        return gender;
    }

    /**
     * Gets the phone number of the patient.
     * 
     * @return The patient's phone number.
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }

    /**
     * Gets the email address of the patient.
     * 
     * @return The patient's email address.
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * Gets the blood type of the patient.
     * 
     * @return The patient's blood type.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Gets the doctor in charge of the patient.
     * 
     * @return The doctor in charge of the patient.
     */
    public String getDoctor() {
        return doctorInCharge;
    }

    /**
     * Sets the phone number of the patient.
     * Validates that the phone number is 8 digits long and contains only numeric characters.
     * 
     * @param holder The new phone number to set.
     * @return True if the phone number is valid and was set; false otherwise.
     */
    public boolean setPhoneNumber(String holder) {
        if (holder == null || holder.length() != 8) {
            return false;
        }
        try {
            // parseDouble converts the string to a number, throwing NumberFormatException if not numeric
            double d = Double.parseDouble(holder);
        } catch (NumberFormatException nfe) {
            return false;
        }
        this.phoneNumber = holder;
        return true;
    }

    /**
     * Sets the email address of the patient.
     * Validates the email format using a regular expression.
     * 
     * @param holder The new email address to set.
     * @return True if the email is valid and was set; false otherwise.
     */
    public boolean setEmailAddress(String holder) {
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+.$";
        if (holder.matches(regex)) {
            this.emailAddress = holder;
            return true;
        }
        return false;
    }

    /**
     * Adds a past diagnosis, prescription, and treatment plan to the patient's medical record.
     * Updates the medical record CSV file with the new diagnosis and treatment.
     * 
     * @param diagnose The diagnosis made by the doctor.
     * @param prescription The prescribed treatment for the diagnosis.
     * @param plan The treatment plan.
     */
    public void addPastDiagnosisAndTreatment(String diagnose, String prescription, String plan) {
        String record = String.format("[%s; %s; %s]", diagnose, prescription, plan);
        pastDiagnosesTreatment.add(record);
        
        StringBuilder diagnosesString = new StringBuilder();
        for (int i = 0; i < pastDiagnosesTreatment.size(); i++) {
            diagnosesString.append(pastDiagnosesTreatment.get(i));
            if (i < pastDiagnosesTreatment.size() - 1) {
                diagnosesString.append("; ");
            }
        }
        
        // System.out.println("Debug: " + diagnosesString);

        ArrayList<Integer> changesIndex = new ArrayList<>();
        ArrayList<String> changes = new ArrayList<>();

        // Add the index for the past diagnoses and treatments column
        changesIndex.add(11);
        changes.add(diagnosesString.toString());

        // Call the method with the correct parameter types
        csv.changeSpecificInformation(this.patientID, changesIndex, changes);
    }

    /**
     * Gets the list of the patient's past diagnoses and treatments.
     * 
     * @return The list of past diagnoses and treatments.
     */
    public ArrayList<String> getPastDiagnosesAndTreatment() {
        return pastDiagnosesTreatment;
    }
}
