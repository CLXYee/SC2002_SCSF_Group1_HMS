package CSV;

import java.util.*;

/**
 * An abstract class that defines operations for reading, adding, changing, and deleting lines in CSV files. 
 * The file can store information related to appointments for different roles such as patient, doctor, pharmacist, and admin.
 */
public abstract class CSVoperator {
	
	/**
     * Default constructor for CSVoperator.
     * This constructor is implicitly provided by Java and is used to instantiate subclasses
     * that inherit from this class. No specific initialization is needed in this class.
     */
    public CSVoperator() {
        // No explicit initialization needed here, the constructor is provided for subclass usage.
    }
	
	/**
     * Reads the file of the CSV and returns specific information for a specific role with its ID.
     * 
     * @param id The unique identifier for the entity (e.g., patient, doctor, pharmacist, admin).
     * @param role The role associated with the ID. 
     *              0 represents patient, 1 represents doctor, 2 represents pharmacist, 3 represents admin.
     * @return An ArrayList of strings containing the specific data for the given role and ID.
     */
	public abstract ArrayList<String> readFile(String id, int role); // read the file of the CSV and return the specific information for the specific role with its id
	//0 represents patient
	//1 represents doctor
	//2 represents pharmacist
	//3 represents admin
	
	/**
     * Adds a new line to the CSV file.
     * 
     * @param dataAdd A list of strings containing the data to be added to the file.
     * @return True if the line is successfully added, otherwise false.
     */
	public abstract boolean addLineToFile(List<String> dataAdd); // adding a line to CSV file
	
	/**
     * Changes a specific piece of information in the file, based on the provided ID and changes.
     * 
     * @param id The unique identifier of the entry to be updated.
     * @param changesIndex A list of integer indices specifying which fields in the line to update.
     * @param changes A list of strings containing the new values for the fields to be updated.
     * @return True if the information is successfully changed, otherwise false.
     */
	public abstract boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes);//change a specific information with the specific index and this changes
	
	/**
     * Deletes a specific line from the CSV file.
     * 
     * @param id The unique identifier for the entry to be deleted.
     * @return True if the line is successfully deleted, otherwise false.
     */
	public abstract boolean deleteSpecificLine(String id); // delete a specific line
	
	/**
     * Splits a CSV line into individual tokens, taking into account quoted values.
     * This method is particularly useful for handling CSV lines that contain commas inside quotes.
     * 
     * @param line The CSV line to be split.
     * @return An array of strings, where each string is a token (field) from the CSV line.
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