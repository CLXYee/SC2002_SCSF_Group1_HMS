package HospitalManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class HospitalApp{
    public static void main(String[] args){
        try {
            List<List<String>> data = new ArrayList<>();
            String filePath = "SC2002/Patient_List.xlsx"; // replace with the path to your own CSV file
            FileReader fr = new FileReader(filePath);
            CSVReader reader = new CSVReader(fr);

            String[] lineData = reader.readNext();
            while (lineData != null) {
                data.add(Arrays.asList(lineData));
                lineData = reader.readNext();
            }

            for (List<String> list : data) {
                for (String str : list) {
                    System.out.print(str + " ");
                }
                System.out.println();
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}