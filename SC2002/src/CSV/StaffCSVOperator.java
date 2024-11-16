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

/**
 * This class provides methods for managing staff records in a CSV file.
 * It extends the abstract class {@link CSVoperator} and implements functionalities for reading,
 * updating, and modifying staff records based on different user roles.
 *
 * <p> The CSV file is expected to contain staff data, and the class allows the following operations:</p>
 * <ul>
 *   <li>Reading the file contents based on user role (admin).</li>
 *   <li>Adding a new staff record (placeholder method).</li>
 *   <li>Updating specific staff information (placeholder method).</li>
 *   <li>Deleting a specific staff record (placeholder method).</li>
 *   <li>Updating the CSV file for an admin user, including adding new data.</li>
 * </ul>
 * 
 * <p> The data in the CSV file is structured with columns for each staff record. The class handles
 * different functionalities based on the role of the user (admin). It tracks the role counters for
 * specific staff roles like Doctor, Pharmacist, and Admin. </p>
 */
public class StaffCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	private int[] roleIdCounters = new int[3];
	
	/**
     * Constructor that initializes the file path for the staff CSV file.
     * The default file path is set to "./Staff_List.csv".
     */
	public StaffCSVOperator() {
		filePath = "./Staff_List.csv";
	}
	
	/**
     * Reads the contents of the CSV file based on the user role.
     * 
     * @param id The ID of the user (not used in this implementation for role 3).
     * @param role The role of the user. If the role is 3, the user is an admin, and the ID is not required.
     *             For role 3 (admin), the file is read and parsed.
     * @return A list of strings containing the data from the file, or an empty list if no data is found.
     */
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
						
						if(tracker <= 2) {
							String[] tempData = super.splitCommaCSVLine(line);
		 					roleIdCounters[tracker++] = Integer.parseInt(tempData[6]);
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
	
	/**
     * Gets the tracker value for Doctor ID.
     * 
     * @return The Doctor ID tracker value.
     */
	public int getDoctorIDTracker() {
		return roleIdCounters[0];
	}
	
	/**
     * Gets the tracker value for Pharmacist ID.
     * 
     * @return The Pharmacist ID tracker value.
     */
	public int getPharIDTracker() {
		return roleIdCounters[1];
	}
	
	/**
     * Gets the tracker value for Admin ID.
     * 
     * @return The Admin ID tracker value.
     */
	public int getAdminIDTracker() {
		return roleIdCounters[2];
	}
	
	/**
     * Placeholder method for adding a new staff record to the CSV file.
     * Currently, this method does nothing but should be implemented to add a new line to the file.
     * 
     * @param dataAdd A list containing the new staff record data.
     * @return {@code true} if the new record is successfully added; {@code false} otherwise.
     */
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	/**
     * Placeholder method for changing a specific block of data in the CSV file.
     * Currently, this method does nothing but should be implemented to update specific fields.
     * 
     * @param id The ID of the staff record to be changed.
     * @param changesIndex A list of indices representing the columns to be updated.
     * @param changes A list of new values for the specified columns.
     * @return {@code true} if the information was successfully updated; {@code false} otherwise.
     */
	// changing a specific block in CSV
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes) {
		return true;
	}
	
	/**
     * Placeholder method for deleting a specific staff record from the CSV file.
     * Currently, this method does nothing but should be implemented to remove the specific line.
     * 
     * @param id The ID of the staff record to be deleted.
     * @return {@code true} if the record is successfully deleted; {@code false} otherwise.
     */
	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
	
	/**
     * Updates the CSV file for an admin user, including adding new data.
     * The method reads the existing data, appends the provided new data, and writes it back to the file.
     * 
     * @param dataStore A list of strings representing the new data to be added to the file.
     * @return {@code true} if the update is successful; {@code false} otherwise.
     */
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
