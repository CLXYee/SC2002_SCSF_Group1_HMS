package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MedicalRecord {
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    /*
    private String bloodType;
    private ArrayList<String> pastDiagnosesAndTreatment;
    */

    public MedicalRecord(String hospitalID){
    	this.patientID = hospitalID;
    	try (BufferedReader br = new BufferedReader(new FileReader("./Patient_List.csv"))) {		    
			String line;
    		while ((line = br.readLine()) != null) {
		        // Split the line into columns using the delimiter
		        String[] data = line.split(",");
		        
		        if (hospitalID.equals(data[2])) {
		        	this.name = data[3];
		        	this.dateOfBirth = data[4];
		        	this.gender = data[5];
		        	this.phoneNumber = data[6];
		        	this.emailAddress = data[7];
		        }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
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
    	if(holder.length() != 8) {
    		return false;
    	}else {
    		this.phoneNumber = holder;
    		return true;
    	}
    }
    
    public boolean setEmailAddress(String holder) {
    	this.emailAddress = holder;
    	return true;
    }
    
    /*
    public String getBloodType(){
        return bloodType;
    }
    public ArrayList<String> getPastDiagnosesAndTreatment(){
        return pastDiagnosesAndTreatment;
    }
    */
}