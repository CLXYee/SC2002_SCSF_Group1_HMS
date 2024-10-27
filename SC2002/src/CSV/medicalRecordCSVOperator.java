package CSV;

import java.util.*;
import java.io.*;

public class medicalRecordCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public medicalRecordCSVOperator() {
		this.filePath = "./Patient_List.csv";
	}
	
	
	public ArrayList<String> readFile(String id) {
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
	
	// adding a line to CSV file
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	// changing a specific block in CSV
	public boolean changeSpecificInformation(String id, int type ,String newInfo) {
		return true;
	}
	
	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
	
	
	
}
