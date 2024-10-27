package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentOutcomeRecord 
{
	private int appointmentID;
	private String dateOfAppointment;
	private String typeOfService;
	private String[] prescribedMedications;
	private String prescriptionStatus;
	private String consultationNotes;

    public AppointmentOutcomeRecord(int aID, String dA, String tS, String[] pM, String pS, String cN) 
    {
    	this.appointmentID = aID;
    	this.dateOfAppointment = dA;
    	this.typeOfService = tS;
    	this.prescribedMedications = pM;
    	this.prescriptionStatus = pS;
    	this.consultationNotes = cN;
        /*List<String> lines = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("./Appointment_List.csv"))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                lines.add(line);
            }

            Collections.reverse(lines);
            
            for (String currentLine : lines) 
            {
                String[] columns = splitCSVLine(currentLine); 
                String csvPatientID = columns[1];
                String appointmentStatus = columns[3];
                
                if (csvPatientID.equals(patientID) && appointmentStatus.equals("Completed")) 
                {
                    this.dateOfAppointment = columns[4];
                    this.typeOfService = columns[6];

                    this.prescribedMedications = (columns.length > 7 && !columns[7].isEmpty()) 
                        ? columns[6].split("\\s*,\\s*") 
                        : new String[0];

                    this.prescriptionStatus = (columns.length > 8 && !columns[8].isEmpty()) 
                        ? columns[7].split("\\s*,\\s*") 
                        : new String[0];
                    
                    this.consultationNotes = columns.length > 9 ? columns[9] : "";
                    break; 
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }*/
    }
    
    public int getAppointmentID()
    {
    	return this.appointmentID;
    }
    
    public String getDateOfAppointment()
    {
    	return this.dateOfAppointment;
    }
    
    public String getTypeOfService()
    {
    	return this.typeOfService;
    }
    
    public String[] getPrescribedMedications()
    {
    	return this.prescribedMedications;
    }
    
    public String getPrescriptionStatus()
    {
    	return this.prescriptionStatus;
    }
    
    public String getConsultationNotes()
    {
    	return this.consultationNotes;
    }

    // Set the Prescription Status of a specific Medication from "Pending" to "Dispensed"
    public void updatePrescriptionStatus()
    {
    	this.prescriptionStatus = "Dispensed";
    }
    
    public void setTypeOfService(String service)
    {
    	this.typeOfService = service;
    }
    
    public void setPrescribedMedications(String[] medications)
    {
    	this.prescribedMedications = new String[medications.length];
		this.prescriptionStatus = "Pending";
    	for (int i = 0; i < medications.length; i++)
    	{
    		this.prescribedMedications[i] = medications[i];
    	}
    }
    
    public void setConsultationNotes(String notes)
    {
    	this.consultationNotes = notes;
    }
}