package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import CSV.medicalRecordCSVOperator;

public class MedicalRecord {
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    /*
    private String bloodType;
    */
    private ArrayList<String> pastDiagnosesAndTreatment = new ArrayList<>();
    private medicalRecordCSVOperator csv = new medicalRecordCSVOperator();
    

    public MedicalRecord(String hospitalID){
    	List<String> data = csv.readFile(hospitalID);
    	
    	this.patientID = data.get(2);
    	this.name = data.get(3);
    	this.dateOfBirth = data.get(4);
    	this.gender = data.get(5);
    	this.phoneNumber = data.get(6);
    	this.emailAddress = data.get(7);
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
    
    /*
    public String getBloodType(){
        return bloodType;
    }
    */
    public void addPastDiagnosisAndTreatment(String diagnose, String prescription, String plan) {
        String record = String.format("[%s, %s, %s]", diagnose, prescription, plan);
        pastDiagnosesAndTreatment.add(record);
    }

    // Optional: Add a method to get pastDiagnosesAndTreatment if needed for display
    public ArrayList<String> getPastDiagnosesAndTreatment() {
        return pastDiagnosesAndTreatment;
    }
}