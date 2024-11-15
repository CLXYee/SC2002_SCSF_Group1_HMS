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

public class AppointmentCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	private Integer counter = 0;
	
	public AppointmentCSVOperator() {
		this.filePath = "./Appointment_List.csv";
	}
	
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
				return data;
		}
		
		return data;
	}
	
	public Integer getCounter() {
		return counter;
	}
	
	public synchronized boolean addLineToFile(List<String> dataAdd) {
	    try (FileWriter writer = new FileWriter(filePath, true)) {
	        String newLine = dataAdd.get(0) + "," + dataAdd.get(1) + "," + dataAdd.get(2) + "," + dataAdd.get(3) + "," + dataAdd.get(4) + "," + dataAdd.get(5) + "," + "NA" + "," + "NA" + "," + "NA" + "," + "NA" + "\n";
	        writer.write(newLine);

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
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
	
	public boolean deleteSpecificLine(String id) // delete a specific line
	{
		return true;
	}
}