package userInfo;

import mainSystemControl.Role;
import userInfoControl.DoctorCtrl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Doctor extends User{
	static int[] myPatientID;
	
	public Doctor(String hospitalID, String name) {
		super(Role.DOCTOR, hospitalID, name);
		Doctor.myPatientID = DoctorCtrl.getPatientList(hospitalID); 
	}
	
	public static int[] getPatientID() {
		return myPatientID;
	}
}