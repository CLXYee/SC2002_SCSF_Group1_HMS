package SC2002.src.HospitalManagementSystem;

import java.util.ArrayList;

public class Patient extends Users{
    private String patientID;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String bloodType;
    private ArrayList<String> pastDiagnosesAndTreatment;

    public Patient(String patientID, String name, String dateOfBirth, String gender, String phoneNumber, String emailAddress, String bloodType, ArrayList<String> pastDiagnosesAndTreatment){
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.pastDiagnosesAndTreatment = pastDiagnosesAndTreatment;
        super(patienID, 2); //build account as a user in the hospital
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
    public String getBloodType(){
        return bloodType;
    }
    public ArrayList<String> getPastDiagnosesAndTreatment(){
        return pastDiagnosesAndTreatment;
    }


}