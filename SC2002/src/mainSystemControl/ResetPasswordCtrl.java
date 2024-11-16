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

public class ResetPasswordCtrl {
	private static String loginHospitalID = null;
	private static String csvFile = null;
	private static String newPassword = null;
	
	public ResetPasswordCtrl(String csvFile, String loginHospitalID) {
		this.loginHospitalID = loginHospitalID;
		this.csvFile = csvFile;
		this.newPassword = resetPassword(csvFile,loginHospitalID);
	}
	/**
     * Resets the user's password if their current password is the default "password".
     *
     * @param csvFile         The file path to the user data CSV file.
     * @param loginHospitalID The hospital ID of the user.
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
