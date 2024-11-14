package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import CSV.MedicalRecordCSVOperator;

public class MedicalRecord {
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String age;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String bloodType;
    private String doctorInCharge;
    private ArrayList<String> pastDiagnosesTreatment = null;
    private MedicalRecordCSVOperator csv = new MedicalRecordCSVOperator();
    

    public MedicalRecord(String hospitalID){
    	List<String> data = csv.readFile(hospitalID);
    	
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
    	
    	this.pastDiagnosesTreatment = new ArrayList<>(Arrays.asList(diagnosesTreatmentData.split("]; \\[")));

    	/*for (int i = 0; i < pastDiagnosesTreatment.size(); i++) {
    	    String record = pastDiagnosesTreatment.get(i).replace("[", "").replace("]", "").trim();
    	    pastDiagnosesTreatment.set(i, record);  
    	}*/
    }

    public String getPatientID(){
        return patientID;
    }

    public String getName(){
        return name;
    }
    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public String getGender(){
        return gender;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public String getEmailAddress(){
        return emailAddress;
    }
    
    public String getBloodType() {
    	return bloodType;
    }
    
    public String getDoctor() {
    	return doctorInCharge;
    }
    
    public boolean setPhoneNumber(String holder) {
    	if (holder == null || holder.length()!=8) {
            return false;
        }
        try {
        	// parseDouble convert the string to its number
        	// if the string is not a number, throw NumberFormatException Error
            double d = Double.parseDouble(holder);
        } catch (NumberFormatException nfe) {
            return false;
        }
        this.phoneNumber = holder;
        return true;
    }
    
    public boolean setEmailAddress(String holder) {
    	// Regular Expression to specify how an email address will look like
    	// [anyCharacters] @ [anyCharacters] . [anyCharacters]
    	// Google Regular Expression for more
    	String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+.$";
    	if (holder.matches(regex)) {
    		this.emailAddress = holder;
    		return true;
    	}
    	return false;
    }
    
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
        
        System.out.println("Debug: " + diagnosesString);

        ArrayList<Integer> changesIndex = new ArrayList<>();
        ArrayList<String> changes = new ArrayList<>();

        // Add the index for the past diagnoses and treatments column
        changesIndex.add(11);
        changes.add(diagnosesString.toString());

        // Call the method with the correct parameter types
        csv.changeSpecificInformation(this.patientID, changesIndex, changes);
    }

    // get pastDiagnosesAndTreatment if needed for display
    public ArrayList<String> getPastDiagnosesAndTreatment() {
        return pastDiagnosesTreatment;
    }
}