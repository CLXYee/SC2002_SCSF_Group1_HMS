package userInfo;

import mainSystemControl.Role;
import userInfoControl.DoctorCtrl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a doctor in the system. This class extends the User class
 * and provides functionality for managing a list of patients assigned to the doctor.
 */
public class Doctor extends User {

    /**
     * An array of patient IDs assigned to the doctor.
     */
    static String[] myPatientID;

    /**
     * Constructs a Doctor object with the given hospital ID, name, gender, and age.
     * Initializes the list of patients assigned to the doctor using the DoctorCtrl class.
     *
     * @param hospitalID The hospital ID for the doctor.
     * @param name The name of the doctor.
     * @param gender The gender of the doctor.
     * @param age The age of the doctor.
     */
    public Doctor(String hospitalID, String name, String gender, int age) {
        super(Role.DOCTOR, hospitalID, name, gender, age);
        Doctor.myPatientID = DoctorCtrl.getPatientList(hospitalID); // Get the list of patients assigned to the doctor.
    }

    /**
     * Gets the list of patient IDs assigned to the doctor.
     *
     * @return An array of patient IDs.
     */
    public static String[] getPatientID() {
        return myPatientID;
    }
}
