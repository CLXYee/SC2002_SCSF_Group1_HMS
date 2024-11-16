package CSV;

import java.util.*;
import java.io.*;

/**
 * This class provides methods for reading, modifying, and managing medical record data
 * in a CSV file. It extends the abstract class {@link CSVoperator}.
 *
 * <p> The CSV file is expected to contain patient data, and this class allows operations such as:</p>
 * <ul>
 *   <li>Reading a specific patient's medical record by ID.</li>
 *   <li>Adding a new patient record (placeholder method).</li>
 *   <li>Modifying specific fields of a patient's medical record.</li>
 *   <li>Deleting a patient record (placeholder method).</li>
 * </ul>
 * 
 * <p> The data is expected to be structured in columns, where each row represents a patient
 * with associated fields such as ID, name, and other medical details.</p>
 */
public class MedicalRecordCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	/**
     * Constructor that initializes the file path for the CSV file.
     * The default file path is set to "./Patient_List.csv".
     */
	public MedicalRecordCSVOperator() {
		this.filePath = "./Patient_List.csv";
	}
	
	/**
     * Reads the CSV file and retrieves the data for a specific patient based on the given ID.
     * 
     * @param id The ID of the patient whose medical record is to be retrieved.
     * @param role The role of the user (not used in this implementation but can be extended for role-based data retrieval).
     * @return A list of strings containing the patient's medical record data, or an empty list if the patient is not found.
     */
	public ArrayList<String> readFile(String id, int role) {
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
	
	/**
     * Placeholder method for adding a new medical record to the CSV file.
     * Currently, this method does nothing but should be implemented to add a new line to the file.
     * 
     * @param dataAdd A list containing the new patient's medical record data.
     * @return {@code true} if the new record is successfully added; {@code false} otherwise.
     */
	// adding a line to CSV file
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	/**
     * Changes specific information for a patient in the CSV file.
     * The fields to be updated are identified by their column indices, and the new values are provided in the {@code changes} list.
     * 
     * @param id The ID of the patient whose medical record is to be changed.
     * @param changesIndex A list of indices representing the columns to be updated.
     * @param changes A list of new values for the specified columns.
     * @return {@code true} if the information was successfully updated; {@code false} otherwise.
     */
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
            	System.out.println(line);
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
	
	/**
     * Placeholder method for deleting a patient's medical record from the CSV file.
     * Currently, this method does nothing but should be implemented to remove the specific line from the file.
     * 
     * @param id The ID of the patient whose record is to be deleted.
     * @return {@code true} if the record is successfully deleted; {@code false} otherwise.
     */

	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
}