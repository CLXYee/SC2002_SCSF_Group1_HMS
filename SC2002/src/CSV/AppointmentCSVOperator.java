package CSV;

import java.io.BufferedReader;
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
						String[] tempData = super.splitCommaCSVLine(line);
						counter++;
						
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
	
	public synchronized boolean addLineToFile(List<String> dataAdd) // adding a line to CSV file
	{
		try (FileWriter writer = new FileWriter(filePath, true)){
			String newLine = dataAdd.get(0) + "," + dataAdd.get(1) + "," + dataAdd.get(2) + "," + dataAdd.get(3) + "," + dataAdd.get(4) + "," + dataAdd.get(5) + "," + "NA" + "," + "NA" + "," + "NA" + "," + "NA" + "\n";
			writer.write(newLine);
			return true;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
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
