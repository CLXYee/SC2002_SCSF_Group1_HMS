package CSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineCSVOperator extends CSVoperator{
	private String filePath;
	private ArrayList<String> data = new ArrayList<>();
	
	public MedicineCSVOperator() {
		filePath = "./Medicine_List.csv";
	}
	
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
						
						data.add(line);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
		}
		
		return data;
	}
	
	public boolean addLineToFile(List<String> dataAdd){
		return true;
	}
	
	// changing a specific block in CSV
	public boolean changeSpecificInformation(String id,ArrayList<Integer> changesIndex, ArrayList<String> changes) {
		return true;
	}
	
	// delete a specific line
	public boolean deleteSpecificLine(String id) {
		return true;
	}
}
