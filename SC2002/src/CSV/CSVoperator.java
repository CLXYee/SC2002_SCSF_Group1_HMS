package CSV;

import java.util.*;

public abstract class CSVoperator {
	public abstract ArrayList<String> readFile(String id); // read the file of the CSV and return the specific line of data we need
	public abstract boolean addLineToFile(List<String> dataAdd); // adding a line to CSV file
	public abstract boolean changeSpecificInformation(String id, int type ,String newInfo); // changing a specific block in CSV
	public abstract boolean deleteSpecificLine(String id); // delete a specific line
	
	private String[] splitCommaCSVLine(String line) // Split a CSV line into the proper format (used for Appointment)
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
