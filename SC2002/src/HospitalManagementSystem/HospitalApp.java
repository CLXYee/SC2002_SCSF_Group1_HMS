package HospitalManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.util.Scanner;


public class HospitalApp{
    public static void main(String[] args){
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Input ID:");
    	String inputID = sc.next();
    	System.out.println("Input password");
    	String inputPass = sc.next();
    	
		String csvFile = "./Patient_List.csv"; // Specify the correct path to your file 
		String line;
		String csvSplitBy = ","; // Delimiter for CSV columns
		 
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {		    
			while ((line = br.readLine()) != null) {
		        // Split the line into columns using the delimiter
		        String[] data = line.split(csvSplitBy);
		        
		        if (inputID.equals(data[1]) && inputPass.equals(data[0])) {
		        	System.out.println("Login successful");
		        	break;
		        }
		    }
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}