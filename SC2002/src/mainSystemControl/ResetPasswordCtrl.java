package mainSystemControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * The ResetPasswordCtrl class is responsible for handling the process of resetting a user's password. 
 * It provides a mechanism to reset the password if it is set to the default value, and updates the user 
 * data CSV file accordingly with the new password.
 */
public class ResetPasswordCtrl {
	private static String loginHospitalID = null;
	private static String csvFile = null;
	private static String newPassword = null;
	
	/**
     * Constructor for initializing the ResetPasswordCtrl object. It sets the CSV file and login 
     * hospital ID, then resets the password for the user.
     *
     * @param csvFile         The file path to the user data CSV file (Patient or Staff).
     * @param loginHospitalID The hospital ID of the user requesting the password reset.
     */
	public ResetPasswordCtrl(String csvFile, String loginHospitalID) {
		this.loginHospitalID = loginHospitalID;
		this.csvFile = csvFile;
		this.newPassword = resetPassword(csvFile,loginHospitalID);
	}
	
	/**
     * Prompts the user to reset their password if it is set to the default "password". 
     * It ensures that the new password is not "password", and then updates the password 
     * in the user data CSV file.
     *
     * @param csvFile         The file path to the user data CSV file (Patient or Staff).
     * @param loginHospitalID The hospital ID of the user.
     * @return The new password entered by the user.
     */
    public static String resetPassword(String csvFile, String loginHospitalID) {
        Scanner sc = new Scanner(System.in);
        String newPassword;

        System.out.println("Your password is set to the default. Please reset your password.");
        do {
            System.out.print("Enter a new password (must not be 'password'): ");
            newPassword = sc.nextLine();
        } while ("password".equals(newPassword));
        
        updatePasswordInFile(loginHospitalID, newPassword, csvFile);
        return newPassword;
    }
    
    /**
     * Updates the user's password in the specified CSV file by creating a temporary file, 
     * replacing the old password with the new one, and then writing the updated data to the temporary file.
     * The original file is then replaced by the temporary file.
     *
     * @param loginHospitalID The hospital ID of the user whose password is to be updated.
     * @param newPassword     The new password to be set for the user.
     * @param csvFile         The path to the CSV file containing the user data (Patient or Staff).
     */
	private static void updatePasswordInFile(String loginHospitalID, String newPassword, String csvFile) {
		String tempFile;
		if (csvFile.equals("./Patient_List.csv")) tempFile = "./tempPatient.csv";
		else tempFile = "./tempStaff.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (loginHospitalID.equals(data[2])) {
                    data[0] = newPassword; // Update the password field
                }
                bw.write(String.join(",", data));
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Password successfully updated.");
    }
}
