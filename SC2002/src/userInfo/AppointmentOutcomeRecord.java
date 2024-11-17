package userInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import CSV.AppointmentCSVOperator;

/**
 * Represents the outcome of a medical appointment, including details such as prescribed medications, service type,
 * prescription status, and consultation notes. It allows recording and updating appointment details in a CSV file.
 */
public class AppointmentOutcomeRecord {

    private int appointmentID;
    private String dateOfAppointment;
    private String typeOfService;
    private String[] prescribedMedications;
    private String prescriptionStatus;
    private String consultationNotes;
    private AppointmentCSVOperator appointmentcsv = new AppointmentCSVOperator();

    /**
     * Constructs an AppointmentOutcomeRecord with the specified details.
     *
     * @param aID The ID of the appointment.
     * @param dA The date of the appointment.
     * @param tS The type of service provided during the appointment.
     * @param pM The prescribed medications during the appointment.
     * @param pS The prescription status (e.g., "Pending", "Dispensed").
     * @param cN The consultation notes for the appointment.
     */
    public AppointmentOutcomeRecord(int aID, String dA, String tS, String[] pM, String pS, String cN) {
        this.appointmentID = aID;
        this.dateOfAppointment = dA;
        this.typeOfService = tS;
        this.prescribedMedications = pM;
        this.prescriptionStatus = pS;
        this.consultationNotes = cN;
    }

    /**
     * Gets the appointment ID for this record.
     *
     * @return The appointment ID.
     */
    public int getAppointmentID() {
        return this.appointmentID;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return The date of the appointment.
     */
    public String getDateOfAppointment() {
        return this.dateOfAppointment;
    }

    /**
     * Gets the type of service provided during the appointment.
     *
     * @return The type of service.
     */
    public String getTypeOfService() {
        return this.typeOfService;
    }

    /**
     * Gets the list of prescribed medications during the appointment.
     *
     * @return An array of prescribed medications.
     */
    public String[] getPrescribedMedications() {
    	return this.prescribedMedications[0].split("; ");
    }

    /**
     * Gets the prescription status (e.g., "Pending", "Dispensed").
     *
     * @return The prescription status.
     */
    public String getPrescriptionStatus() {
        return this.prescriptionStatus;
    }

    /**
     * Gets the consultation notes from the appointment.
     *
     * @return The consultation notes.
     */
    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    /**
     * Updates the prescription status to "Dispensed".
     */
    public void updatePrescriptionStatus() {
        this.prescriptionStatus = "Dispensed";
    }

    /**
     * Sets the type of service provided during the appointment.
     *
     * @param service The type of service.
     */
    public void setTypeOfService(String service) {
        this.typeOfService = service;
    }

    /**
     * Sets the prescribed medications during the appointment and updates the prescription status to "Pending".
     *
     * @param medications The array of prescribed medications.
     */
    public void setPrescribedMedications(String[] medications) {
        this.prescribedMedications = new String[medications.length];
        this.prescriptionStatus = "Pending";
        System.arraycopy(medications, 0, this.prescribedMedications, 0, medications.length);
    }

    /**
     * Sets the consultation notes for the appointment.
     *
     * @param notes The consultation notes.
     */
    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }

    /**
     * Records the outcome of the appointment by updating the appointment information in the CSV file.
     * This includes the type of service, prescribed medications, prescription status, and consultation notes.
     *
     * @return {@code true} if the outcome was successfully recorded, otherwise {@code false}.
     */
    public boolean recordOutcomeInCSV() {
        ArrayList<String> dataEdit = new ArrayList<>();
        ArrayList<Integer> editIndex = new ArrayList<>();
        editIndex.add(6);  // Index for the type of service
        editIndex.add(7);  // Index for prescribed medications
        editIndex.add(8);  // Index for prescription status
        editIndex.add(9);  // Index for consultation notes

        // Add type of service to dataEdit list
        dataEdit.add(this.typeOfService);

        // Format prescribed medications as a comma-separated string
        String prescribedMed = String.join("; ", this.prescribedMedications);
        dataEdit.add("\"" + prescribedMed + "\"");

        // Add prescription status to dataEdit list
        dataEdit.add(this.prescriptionStatus);

        // Add consultation notes to dataEdit list
        dataEdit.add("\"" + this.consultationNotes + "\"");

        // Update the appointment record in the CSV file
        return appointmentcsv.changeSpecificInformation(Integer.toString(this.appointmentID), editIndex, dataEdit);
    }
}
