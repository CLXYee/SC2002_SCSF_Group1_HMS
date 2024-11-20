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
 * The AppointmentCSVOperator class handles operations related to reading from and writing to
 * the appointment data CSV file. It provides methods for reading specific appointment data,
 * adding new appointment records, changing specific information in an appointment, and updating
 * the CSV file for administrative purposes.
 * 
 * This class extends {@link CSVoperator} and provides synchronized methods for ensuring thread safety
 * when modifying the appointment CSV file.
 */
public class AppointmentCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	private Integer counter = 0;
	
	/**
     * Constructs an AppointmentCSVOperator instance.
     * Initializes the file path to the default appointment CSV file location.
     */
	public AppointmentCSVOperator() {
		this.filePath = "./Appointment_List.csv";
	}
	
	/**
     * Reads the appointment CSV file and returns the specific data based on the user's role.
     * 
     * @param id The ID used to filter the data (e.g., patient ID, doctor ID).
     * @param role The role of the user that determines which data is retrieved:
     *             <ul>
     *                 <li>0: Patient role</li>
     *                 <li>1: Doctor role</li>
     *                 <li>2: Administrator role (skip the first line)</li>
     *                 <li>3: Administrator role (skip the first line, for a different purpose)</li>
     *             </ul>
     * @return An ArrayList containing the filtered data from the CSV file.
     */
	public synchronized ArrayList<String> readFile(String id, int role) // read the file of the CSV and return the specific line of data we need
	{
		switch(role) {
			case 0:
				try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
				{
					String line;
					while ((line = br.readLine()) != null) 
					{
						counter++;
						String[] tempData = super.splitCommaCSVLine(line);
						
						if (id.equals(tempData[1])) 
						{
							data.add(String.join(",", tempData));
						}
					}
				}
				catch (IOException e)  
				{
					e.printStackTrace();
				}
				break;
			
			case 1:
				try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
				{
					String line;
					while ((line = br.readLine()) != null) 
					{
						counter++;
						String[] tempData = super.splitCommaCSVLine(line);
						
						if (id.equals(tempData[2])) 
						{
							data.add(String.join(";", tempData));
						}
					}
				}
				catch (IOException e)  
				{
					e.printStackTrace();
				}
				break;
				
			case 2:
				try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
				{
					String line;
					int counterForSkip = 0;
					while ((line = br.readLine()) != null) 
					{
						if(counterForSkip == 0) {
							counterForSkip++;
							continue;
						}
						
						data.add(line);
					}
				}
				catch (IOException e)  
				{
					e.printStackTrace();
				}
				break;
				
			case 3:
				try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
				{
					String line;
					int counterForSkip = 0;
					while ((line = br.readLine()) != null) 
					{
						if(counterForSkip == 0) {
							counterForSkip++;
							continue;
						}
						
						data.add(line);
					}
				}
				catch (IOException e)  
				{
					e.printStackTrace();
				}
				break;
		}
		
		return data;
	}
	
	/**
     * Gets the current line count for the appointment CSV file.
     * 
     * @return The number of lines read from the file.
     */
	public Integer getCounter() {
		return counter;
	}
	
	/**
     * Adds a new line to the appointment CSV file.
     * 
     * @param dataAdd A list of strings containing the data to be added.
     * @return True if the new line is successfully added, false otherwise.
     */
	public synchronized boolean addLineToFile(List<String> dataAdd) {
	    try (FileWriter writer = new FileWriter(filePath, true)) {
	        String newLine = dataAdd.get(0) + "," + dataAdd.get(1) + "," + dataAdd.get(2) + "," + dataAdd.get(3) + "," + dataAdd.get(4) + "," + dataAdd.get(5) + "," + "NA" + "," + "NA" + "," + "NA" + "," + "NA" + "\n";
	        writer.write(newLine);
	        counter++;

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
     * Changes specific information in a particular appointment record identified by its ID.
     * 
     * @param id The ID of the appointment to be updated.
     * @param changesIndex An ArrayList of indexes specifying which columns to update.
     * @param changes An ArrayList of strings representing the new values for the specified columns.
     * @return True if the update is successful, false otherwise.
     */
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes)//change a specific information with the specific index and this changes
	{
		String tempFile = "./temp.csv"; // temporary file for the data changing
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            boolean checker = true; // make sure the csv file only change for the first line
            
            while ((line = reader.readLine()) != null) {
            	//System.out.println(line);
                String[] tempData = line.split(","); // Split the row into columns
                
                if(tempData[0].equals(id)) {
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
     * Deletes a specific line from the appointment CSV file based on the appointment ID.
     * 
     * @param id The ID of the appointment to be deleted.
     * @return True if the line is successfully deleted, false otherwise.
     */
	public boolean deleteSpecificLine(String id) // delete a specific line
	{
		return true;
	}
	
	/**
     * Updates the CSV file with new data for the administrator.
     * This method overwrites the first line and appends the new data below it.
     * 
     * @param dataStore An ArrayList containing the new data to be stored.
     * @return True if the update is successful, false otherwise.
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