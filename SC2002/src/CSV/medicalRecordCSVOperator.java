package CSV;

import java.util.*;
import java.io.*;

public class medicalRecordCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public medicalRecordCSVOperator() {
		this.filePath = "./Patient_List.csv";
	}
	
	
	public ArrayList<String> readFile(String id) {
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			while((line = br.readLine()) != null) {
				//Split the line into columns using the delimiter
				String[] tempData = line.split(",");
				
				if(id.equals(tempData[2])) {
					for(int i = 0; i < tempData.length; i++) {
						data.add(tempData[i]);
					}
					break;
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	// adding a line to CSV file
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	// changing a specific block in CSV
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes) {
		String tempFile = "./temp.csv"; // temporary file for the data changing
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] tempData = line.split(","); // Split the row into columns

                if(tempData[2].equals(id)) {
                	int counterOfChanges = 0;
                	for(int i: changesIndex) {
                		tempData[i] = changes.get(counterOfChanges++);
                	}
                }
                
                writer.write(String.join(",", tempData));
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
			System.out.println("Error: unable to delete or rename file.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
	
	
	
}
