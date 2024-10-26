package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Appointment 
{
	private int appointmentID;
	private String patientID;
    private String doctorID;
    private String appointmentStatus;
    private String dateOfAppointment;  //Format Example: "13/10/2025", "02/01/2022", "09/11/2011", "23/06/2019" (Always 10 chars)
    private String timeOfAppointment;  //Format Example: "09:30-10:00", "17:30-19:00", "00:00-00:30", "12:00-13:30" (Always 11 chars)
    
    //When a patient schedules an appointment (create an Appointment object)
    //Remember to update the Doctor's available appointment slot
    
    //When a doctor wants to view his appointment requests
    /*
    In the doctor's control class, the viewUpcomingAppointment() will find all appointments that are "Pending" or "Confirmed"
    1. Iterate through the Appointment_List.csv file 
    2. Save the current line in a String (Can refer to MedicalRecord.java)
    3. Split the String by "," into a string list
    4. When the Doctor ID == doctorID:
    	a. If Appointment Status == "Pending" or "Confirmed":
    		Add an Appointment object storing the values into the upcomingAppointment list
    	b. Else
    		Read the next line
    */
    
    //When a patient wants to view his appointment requests
    /*
    In the patient's control class, the viewScheduledAppointment() will find all appointments that are not "Completed"
    1. Iterate through the Appointment_List.csv file 
    2. Save the current line in a String (Can refer to MedicalRecord.java)
    3. Split the String by "," into a string list
    4. When the Patient ID == patientID:
    	a. If Appointment Status != "Completed":
    		Add an Appointment object storing the values into the scheduledAppointment list
    	b. Else
    		Read the next line
    */
    
    //When an administrator wants to view the appointment details 
    /*
    *** Assume the administrator can view all the appointments, because they can't make changes anyway,
        so viewing only 1 specific appointment makes less sense, and it's also more troublesome.
    
    In the admin's control class, the viewAppointment() will find all appointments and print them out line by line
    1. Iterate through the Appointment_List.csv file 
    2. Save the current line in a String (Can refer to MedicalRecord.java)
    3. Split the String by "," into a string list
    4. Print out the appointment line by line (repeat step 2 to 3)
    	a. Check if Appointment Status == "Completed"
    		i. Create an AppointmentOutcomeRecord object and 
    */
    public Appointment(int aID, String pID, String dID, String aS, String dA, String tA)
    {
    	this.appointmentID = aID;
    	this.patientID = pID;
    	this.doctorID = dID;
    	this.appointmentStatus = aS;
    	this.dateOfAppointment = dA;
    	this.timeOfAppointment = tA;
    }
    
    public int getAppointmentID()
    {
    	return this.appointmentID;
    }
    
    public String getPatientID()
    {
    	return this.patientID;
    }
    
    public String getDoctorID()
    {
    	return this.doctorID;
    }
    
    public String getAppointmentStatus()
    {
    	return this.appointmentStatus;
    }
    
    public String getDateOfAppointment()
    {
    	return this.dateOfAppointment;
    }
    
    public String getTimeOfAppointment()
    {
    	return this.timeOfAppointment;
    }
    
    //When a patient reschedule appointment
    public void setDateOfAppointment(String date)
    {
    	this.dateOfAppointment = date;
    }
    
    //When a patient reschedule appointment
    public void setTimeOfAppointment(String time)
    {
    	this.timeOfAppointment = time;
    }
    
    //When the following happens:
    /*
    1. Patient cancel appointment (set to Canceled)
    2. Doctor accepts appointment request (set to Confirmed)
    3. Doctor declines appointment request (set to Canceled)
    4. Doctor completed the appointment (set to Completed)
    	a. Remember to create an AppointmentOutcomeRecord object when appointment is completed
    */
    public void setAppointmentStatus(String Status)
    {
    	this.appointmentStatus = Status;
    }
    
    public void appendLineToCSV(String filePath) 
    {
        try (FileWriter writer = new FileWriter(filePath, true)) 
        {
            // Create the CSV string from the appointment object
            String newLine = appointmentID + "," +  patientID + "," + doctorID + "," + appointmentStatus + "," + dateOfAppointment + "," + timeOfAppointment + ",,," + "\n";
            writer.write(newLine);
            System.out.println("Appointment has been made successfully!"); 
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred while saving the appointment.");
            e.printStackTrace();
        }
    }
}
