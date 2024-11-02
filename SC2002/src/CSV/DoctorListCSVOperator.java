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

public class DoctorListCSVOperator extends CSVoperator
{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public DoctorListCSVOperator()
	{
		this.filePath = "./Doctor_List.csv";
	}
	
	public ArrayList<String> readFile(String id)
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
	
	public boolean addLineToFile(List<String> dataAdd)
	{
		return true;
	}
	
	public boolean changeSpecificInformation(String id, ArrayList<String> changes)
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
            	System.out.println(line);
                String[] tempData = line.split(","); // Split the row into columns
                if (tempData[2].equals(id)) 
                {
                	if (!tempData[7].equals(changes.get(0))) 
                	{
                		tempData[7] = changes.get(0);
                	}
                	
                	if (!tempData[8].equals(changes.get(1))) 
                	{
                		tempData[8] = changes.get(1);
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
	
	public boolean deleteSpecificLine(String id)
	{
		return true;
	}
}
