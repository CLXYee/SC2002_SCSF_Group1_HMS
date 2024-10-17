package userInfoControl;

import userInfo.MedicalRecord;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class PatientCtrl implements MedicalRecordCtrl, GetOperationInput, EntityUpdate {
	private MedicalRecord medicalRecord;
	
	public PatientCtrl(String hospitalID) {
		this.medicalRecord = new MedicalRecord(hospitalID);
	}
	
	public void showMedicalRecord() {
		System.out.println("Show medical record for patient");
		System.out.println("===============================");
		System.out.println("Patient ID 	| " + medicalRecord.getPatientID());
		System.out.println("Name 		| " + medicalRecord.getName());
		System.out.println("Gender 		| " + medicalRecord.getGender());
		System.out.println("Phone No. 	| " + medicalRecord.getPhoneNumber());
		System.out.println("Email Address | " + medicalRecord.getEmailAddress());
		System.out.println("===============================");
	}
	
	public void updateMedicalRecord() {
		Scanner sc = new Scanner(System.in);
		int input;
		boolean checker;
		do {
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("Which of the following information you willing to change:");
			System.out.println("1. Phone number");
			System.out.println("2. Email address");
			System.out.println("3. Exit");
			System.out.println("=========================================================");
			System.out.println();
			input = sc.nextInt();
			
			switch(input){
				case 1:
					System.out.println("Please enter a new phone number");
					checker = medicalRecord.setPhoneNumber(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid phone number");
						checker = medicalRecord.setPhoneNumber(sc.next());
					}
					System.out.println("Phone number has been updated successfully!");
					System.out.println();
					break;
				case 2:
					System.out.println("Please enter a new email address");
					checker = medicalRecord.setEmailAddress(sc.next());
					while(!checker) {
						System.out.println("Please enter a valid email address");
						checker = medicalRecord.setEmailAddress(sc.next());
					}
					System.out.println("Email Address has been updated successfully!");
					System.out.println();
					break;
				case 3:
					if(updateSpecificInfo(medicalRecord.getPatientID())) {
						System.out.println("Exiting.......");
					}else {
						System.out.println("System updated failed!");
					}
					break;
				default:
					System.out.println("Please enter a valid option!");
					System.out.println();
			}
		}while(input != 3);
	}
	
	public boolean updateSpecificInfo(String target) {
		String filePath = "./Patient_List.csv"; // original file
		String tempFile = "./temp.csv"; // temporary file for the data changing
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Split the row into columns

                if(medicalRecord.getPatientID().equals(data[2])) {
                	if(!medicalRecord.getPhoneNumber().equals(data[6])) {
                		data[6] = medicalRecord.getPhoneNumber();
                	}
                	
                	if(!medicalRecord.getEmailAddress().equals(data[7])) {
                		data[7] = medicalRecord.getEmailAddress();
                	}
                }
                
                writer.write(String.join(",", data));
                writer.newLine();
            }
		} catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please check the file path.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Error: An I/O error occurred while reading or writing the file.");
            e.printStackTrace();
            return false;
        } finally {
            // Close the resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error: Failed to close the file.");
                e.printStackTrace();
            }
        }
		
		try {
			File originalFile = new File(filePath);
			File newFile = new File(tempFile);
			
			if(originalFile.delete()) {
				newFile.renameTo(originalFile);
			}
		}catch(Exception e) {
			System.out.println("Error: unable to delet or rename file.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			updateMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
		}
	}

	
}
