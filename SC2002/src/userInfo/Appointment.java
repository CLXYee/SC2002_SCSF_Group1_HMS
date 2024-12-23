package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import CSV.AppointmentCSVOperator;


/**
 * Represents an appointment for a patient with a doctor. It stores details like the appointment ID, patient ID,
 * doctor ID, status, date, and time. The class provides methods to add, reschedule, or cancel appointments,
 * and to interact with the underlying CSV file for persistence.
 */
public class Appointment 
{
	private Integer appointmentID;
	private String patientID;
    private String doctorID;
    private String appointmentStatus;
    private String dateOfAppointment;  //Format Example: "13/10/2025", "02/01/2022", "09/11/2011", "23/06/2019" (Always 10 chars)
    private String timeOfAppointment;  //Format Example: "09:30-10:00", "17:30-19:00", "00:00-00:30", "12:00-13:30" (Always 11 chars)
    private AppointmentCSVOperator appointmentcsv = new AppointmentCSVOperator();
    
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
    
    /**
     * Constructs an Appointment object with the specified details.
     *
     * @param aID The ID of the appointment.
     * @param pID The ID of the patient.
     * @param dID The ID of the doctor.
     * @param aS The status of the appointment (e.g., "Pending", "Confirmed", "Completed").
     * @param dA The date of the appointment (in the format "dd/MM/yyyy").
     * @param tA The time of the appointment (in the format "HH:mm-HH:mm").
     */
    public Appointment(Integer aID, String pID, String dID, String aS, String dA, String tA)
    {
    	this.appointmentID = aID;
    	this.patientID = pID;
    	this.doctorID = dID;
    	this.appointmentStatus = aS;
    	this.dateOfAppointment = dA;
    	this.timeOfAppointment = tA;
    }
    
    /**
     * Gets the appointment ID.
     *
     * @return The appointment ID.
     */
    public Integer getAppointmentID()
    {
    	return this.appointmentID;
    }
    
    /**
     * Gets the patient ID.
     *
     * @return The patient ID.
     */
    public String getPatientID()
    {
    	return this.patientID;
    }
    
    /**
     * Gets the doctor ID.
     *
     * @return The doctor ID.
     */
    public String getDoctorID()
    {
    	return this.doctorID;
    }
    
    /**
     * Gets the current status of the appointment (e.g., "Pending", "Confirmed", "Completed").
     *
     * @return The appointment status.
     */
    public String getAppointmentStatus()
    {
    	return this.appointmentStatus;
    }
    
    /**
     * Gets the date of the appointment.
     *
     * @return The appointment date in the format "dd/MM/yyyy".
     */
    public String getDateOfAppointment()
    {
    	return this.dateOfAppointment;
    }
    
    /**
     * Gets the time of the appointment.
     *
     * @return The appointment time in the format "HH:mm-HH:mm".
     */
    public String getTimeOfAppointment()
    {
    	return this.timeOfAppointment;
    }
    
    /**
     * Reschedules the appointment by updating the date of the appointment.
     *
     * @param date The new date for the appointment.
     */
    //When a patient reschedule appointment
    public void setDateOfAppointment(String date)
    {
    	this.dateOfAppointment = date;
    }
    
    /**
     * Reschedules the appointment by updating the time of the appointment.
     *
     * @param time The new time for the appointment.
     */
    //When a patient reschedule appointment
    public void setTimeOfAppointment(String time)
    {
    	this.timeOfAppointment = time;
    }
    
    /**
     * Updates the status of the appointment (e.g., "Canceled", "Confirmed", "Completed").
     * When this happens:
     * 1. Patient cancels appointment (set to Canceled)
     * 2. Doctor accepts appointment request (set to Confirmed)
     * 3. Doctor declines appointment request (set to Canceled)
     * 4. Doctor completes the appointment (set to Completed)
     *    a. Remember to create an AppointmentOutcomeRecord object when appointment is completed
     *
     * @param Status The new status for the appointment.
     */
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
    
    /**
     * Adds the new appointment to the CSV file for persistence.
     *
     * @return true if the appointment was successfully added, false otherwise.
     */
    public boolean addNewAppointmentToCSV() 
    {
    	ArrayList<String> dataAdd = new ArrayList<>();
    	dataAdd.add(appointmentID.toString());
    	dataAdd.add(patientID);
    	dataAdd.add(doctorID);
    	dataAdd.add(appointmentStatus);
    	dataAdd.add(dateOfAppointment);
    	dataAdd.add(timeOfAppointment);
    	
    	return appointmentcsv.addLineToFile(dataAdd);
    }
    
    /**
     * Reschedules the appointment and updates the corresponding CSV record.
     *
     * @return true if the appointment was successfully rescheduled, false otherwise.
     */
    public boolean rescheduleAppointmentInCSV()
    {
    	ArrayList<String> dataEdit = new ArrayList<>();
    	ArrayList<Integer> editIndex = new ArrayList<>();
    	editIndex.add(3);
    	editIndex.add(4);
    	editIndex.add(5);
    	dataEdit.add(appointmentStatus);
    	dataEdit.add(dateOfAppointment);
    	dataEdit.add(timeOfAppointment);
    	
    	return appointmentcsv.changeSpecificInformation(appointmentID.toString(), editIndex, dataEdit);
    }
}
