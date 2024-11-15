package CSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public MedicineCSVOperator() {
		filePath = "./Medicine_List.csv";
	}
	
	public ArrayList<String> readFile(String id, int role)// if the role equals to 3 means it is admin, the id is not important
	{
		switch(role) {
			case 3:
				try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
					String line;
					int tracker = 0, counter = 0;
					while((line = br.readLine()) != null) {
						if(counter == 0) {
							counter++;
							continue;
						}
						
						data.add(line);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
		}
		
		return data;
	}
	
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	// changing a specific block in CSV
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes) {
		return true;
	}
	
	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
	
	public boolean updateCSVForAdmin(ArrayList<String> dataStore) {
		String tempFile = "./temp.csv"; // temporary file for the data changing
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            int counter = 0;
            
            while ((line = reader.readLine()) != null) {
            	if(counter == 1) {
            		break;
            	}
            	
            	counter++;
                writer.write(line);
                writer.newLine();
            }
            
            for(String i: dataStore) {
            	writer.write(i);
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
}
