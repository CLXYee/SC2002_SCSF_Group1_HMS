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
 * This class provides methods for reading, writing, updating, and deleting records
 * in a CSV file specifically related to doctor data.
 * It extends the abstract class {@link CSVoperator}.
 * 
 * <p> The CSV file is expected to contain doctor data, and this class allows operations like: </p>
 * <ul>
 *   <li>Reading a specific doctor's data by ID.</li>
 *   <li>Adding a new doctor record.</li>
 *   <li>Updating specific fields for a doctor record.</li>
 *   <li>Deleting a doctor record.</li>
 * </ul>
 * 
 * <p> This class assumes the CSV file format follows a specific structure where columns
 * represent various data points such as doctor ID, name, schedule, etc. </p>
 */
public class DoctorCSVOperator extends CSVoperator
{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	/**
     * Constructor that initializes the file path for the CSV file.
     * The default file path is set to "./Doctor_List.csv".
     */
	public DoctorCSVOperator()
	{
		this.filePath = "./Doctor_List.csv";
	}
	
	/**
     * Reads the CSV file and retrieves the data for a specific doctor based on the given ID.
     * 
     * @param id The ID of the doctor whose data is to be retrieved.
     * @param role The role of the user (not used in this implementation but can be extended for role-based data retrieval).
     * @return A list of strings containing the doctor's data, or an empty list if the doctor is not found.
     */
	public ArrayList<String> readFile(String id, int role)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				String[] tempData = line.split(",");
				
				if (id.equals(tempData[2])) 
				{
					for (int i = 0; i < tempData.length; i++) 
					{
						data.add(tempData[i]);
					}
					break;
				}
			}
		}
		catch (IOException e)  
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/**
     * Adds a new line to the CSV file with the data for a new doctor.
     * 
     * @param dataAdd A list containing the new doctor's data.
     * @return {@code true} if the new line is successfully added; {@code false} otherwise.
     */

	public boolean addLineToFile(List<String> dataAdd)
	{
		try (FileWriter writer = new FileWriter(filePath, true)) {
	        String newLine = dataAdd.get(0) + ",DOCTOR," + dataAdd.get(1) + "," + dataAdd.get(2) + ",NA," + dataAdd.get(3) + ",NA,NA,NA";
	        writer.write(newLine);

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
     * Changes specific information for a doctor in the CSV file.
     * The fields to be updated are identified by their column indices, and the new values are provided in the {@code changes} list.
     * 
     * @param id The ID of the doctor whose information is to be changed.
     * @param indexChanges A list of indices representing the columns to be updated.
     * @param changes A list of new values for the specified columns.
     * @return {@code true} if the information was successfully updated; {@code false} otherwise.
     */
	public boolean changeSpecificInformation(String id, ArrayList<Integer> indexChanges, ArrayList<String> changes)
	{
		String tempFile = "./temp.csv"; // temporary file for the data changing
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try 
		{
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            
            while ((line = reader.readLine()) != null) 
            {
            	String[] tempData = splitCommaCSVLine(line); // Split the row into columns
            	tempData[9] = "\"" + tempData[9] + "\"";
                if (tempData[2].equals(id)) 
                {
                	for(int i: indexChanges) {
                		if (!tempData[i].equals(changes.get(i))) // change when the data is not equal to the data in the CSV
                		{
                			if (i == 9) // specifically for the personal schedule
                			{
                				tempData[i] = "\"" + changes.get(i) + "\"";
                			}
                			else
                			{
                				tempData[i] = changes.get(i);
                			}
                			
                		}
                	}
                }
                
                writer.write(String.join(",", tempData));
                writer.newLine();
            }
		} 
		catch (FileNotFoundException e) 
		{
            System.out.println("Error: File not found. Please check the file path.");
            e.printStackTrace();
            return false;
        } 
		catch (IOException e) 
		{
            System.out.println("Error: An I/O error occurred while reading or writing the file.");
            e.printStackTrace();
            return false;
        } 
		finally 
		{
            // Close the resources in the finally block to ensure they are closed even if an exception occurs
            try 
            {
                if (reader != null) 
                {
                    reader.close();
                }
                if (writer != null) 
                {
                    writer.close();
                }
            } 
            catch (IOException e) 
            {
                System.out.println("Error: Failed to close the file.");
                e.printStackTrace();
            }
        }
		
		try 
		{
			File originalFile = new File(filePath);
			File newFile = new File(tempFile);
			
			if (originalFile.delete()) 
			{
				newFile.renameTo(originalFile);
			}
		}
		catch (Exception e) 
		{
			System.out.println("Error: unable to delete or rename file.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
     * Deletes the record of a specific doctor from the CSV file.
     * 
     * @param id The ID of the doctor to be deleted.
     * @return {@code true} if the record was successfully deleted; {@code false} otherwise.
     */
	public boolean deleteSpecificLine(String id)
	{
		String tempFile = "./temp.csv"; // temporary file for the data changing
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try 
		{
			// Initialize BufferedReader and BufferedWriter inside a try block to catch exceptions
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            
            while ((line = reader.readLine()) != null) 
            {
            	String[] tempData = splitCommaCSVLine(line); // Split the row into columns
                if (tempData[2].equals(id)) 
                {
                	continue;
                }
                
                writer.write(String.join(",", tempData));
                writer.newLine();
            }
		} 
		catch (FileNotFoundException e) 
		{
            System.out.println("Error: File not found. Please check the file path.");
            e.printStackTrace();
            return false;
        } 
		catch (IOException e) 
		{
            System.out.println("Error: An I/O error occurred while reading or writing the file.");
            e.printStackTrace();
            return false;
        } 
		finally 
		{
            // Close the resources in the finally block to ensure they are closed even if an exception occurs
            try 
            {
                if (reader != null) 
                {
                    reader.close();
                }
                if (writer != null) 
                {
                    writer.close();
                }
            } 
            catch (IOException e) 
            {
                System.out.println("Error: Failed to close the file.");
                e.printStackTrace();
            }
        }
		
		try 
		{
			File originalFile = new File(filePath);
			File newFile = new File(tempFile);
			
			if (originalFile.delete()) 
			{
				newFile.renameTo(originalFile);
			}
		}
		catch (Exception e) 
		{
			System.out.println("Error: unable to delete or rename file.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
     * Splits a CSV line into its individual tokens, considering quoted fields.
     * This method ensures that commas inside quoted strings do not split the field.
     * 
     * @param line The CSV line to be split.
     * @return An array of strings, where each element represents a token (column) from the CSV line.
     */
	public String[] splitCommaCSVLine(String line) // Split a CSV line into the proper format (used for Appointment)
    {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) 
        {
            char currentChar = line.charAt(i);
            
            if (currentChar == '"') 
            {
                inQuotes = !inQuotes; 
            } 
            else if (currentChar == ',' && !inQuotes) 
            {
                tokens.add(currentToken.toString());
                currentToken.setLength(0);
            } 
            else 
            {
                currentToken.append(currentChar);
            }
        }
        
        tokens.add(currentToken.toString());
        return tokens.toArray(new String[0]);
    }
}