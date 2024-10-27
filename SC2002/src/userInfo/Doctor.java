package userInfo;

import mainSystemControl.Role;
import userInfoControl.DoctorCtrl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Doctor extends User{
	static String[] myPatientID;
	
	public Doctor(String hospitalID, String name, String gender, int age) {
		super(Role.DOCTOR, hospitalID, name, gender, age);
		Doctor.myPatientID = DoctorCtrl.getPatientList(hospitalID); 
	}
	
	public static String[] getPatientID() {
		return myPatientID;
	}
}