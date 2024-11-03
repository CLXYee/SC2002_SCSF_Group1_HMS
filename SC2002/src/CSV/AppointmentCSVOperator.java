package CSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public AppointmentCSVOperator() {
		this.filePath = "./Appointment_List.csv";
	}
	
	public ArrayList<String> readFile(String id, int role) // read the file of the CSV and return the specific line of data we need
	{
		switch(role) {
			case 0:
				try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
				{
					String line;
					while ((line = br.readLine()) != null) 
					{
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
	
	public boolean addLineToFile(List<String> dataAdd) // adding a line to CSV file
	{
		return true;
	}
	
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes)//change a specific information with the specific index and this changes
	{
		return true;
	}
	
	public boolean deleteSpecificLine(String id) // delete a specific line
	{
		return true;
	}
}
