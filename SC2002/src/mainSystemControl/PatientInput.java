package mainSystemControl;

import java.io.File;
import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPatientMenu;
import userInfoControl.AppointmentCtrl;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

/**
 * The PatientInput class is responsible for handling the operations related to patient 
 * interactions in the system. It shows the patient menu and processes user input 
 * to perform various actions, such as viewing medical records, scheduling appointments, 
 * and logging out.
 */
public class PatientInput implements IGetOperationInput {
	private PatientCtrl patientCtrl = null;
	private ShowMenu menu = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private AppointmentCtrl appointmentCtrl;
	
	/**
     * Constructor for initializing the PatientInput object with the given patient details.
     * It also initializes necessary controllers and the menu for the patient interface.
     *
     * @param hospitalID the unique ID of the hospital
     * @param name the name of the patient
     * @param gender the gender of the patient
     * @param age the age of the patient
     */
	public PatientInput(String hospitalID, String name, String gender, int age) {
		this.patientCtrl = new PatientCtrl(hospitalID, name, gender, age);
		this.medicalRecordCtrl = this.patientCtrl;
		this.appointmentCtrl = this.patientCtrl;
		this.menu = new ShowPatientMenu();
	}
	
	/**
     * Displays the menu for patient operations.
     */
	public void showMenu() {
		menu.showMenu();
	}
	
	/**
     * Processes the input operation provided by the user. It handles various actions 
     * such as viewing and updating medical records, scheduling, rescheduling, and 
     * canceling appointments, as well as logging out.
     *
     * @param input the integer input representing the operation to be performed
     * @return true if the operation was successfully processed, false if the user logs out
     */
	public boolean getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			medicalRecordCtrl.showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;
		case 2:
			medicalRecordCtrl.updateMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;
		case 3:
			//View Available Appointment Slots
			appointmentCtrl.viewAvailableSlots();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 4:
			//Schedule an Appointment
			appointmentCtrl.scheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 5:
			//Reschedule an Appointment
			appointmentCtrl.rescheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 6:
			//Cancel an Appointment
			appointmentCtrl.cancelAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 7:
			//View Scheduled Appointments
			appointmentCtrl.viewScheduledAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 8:
			//View Past Appointment Outcome Records
			appointmentCtrl.viewPastRecords();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 9:
			//Logout
			System.out.println("Loging out...\n");
			return false;
		default:
			//Invalid input
			System.out.println("Invalid input\n");
			return true;
		}
	}

}
