package mainSystemControl;

import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowDoctorMenu;
import userInfoControl.*;

/**
 * This class is responsible for handling input operations for the Doctor.
 * It allows the doctor to perform various operations such as viewing patient records,
 * updating medical records, managing appointments, and managing their personal schedule.
 */
public class DoctorInput implements IGetOperationInput{
	private DoctorCtrl doctorCtrl = null;
	private ShowMenu menu = null;
	private MedicalRecordCtrl medicalRecordCtrl=null;
	private IDocAppointmentCtrl iDocAppointmentCtrl;
	private IPatientList iPatientList;
	private ISchedule iSchedule;

	/**
     * Constructor that initializes the DoctorCtrl object and the menu for the doctor.
     * 
     * @param hospitalID the unique identifier for the hospital
     */
	public DoctorInput(String hospitalID) {
		this.doctorCtrl = new DoctorCtrl(hospitalID);
		this.menu = new ShowDoctorMenu();
		this.medicalRecordCtrl = this.doctorCtrl;
		this.iDocAppointmentCtrl = this.doctorCtrl;
		this.iPatientList = this.doctorCtrl;
		this.iSchedule = this.doctorCtrl;
	}
	
	/**
     * Displays the doctor's menu.
     */
	public void showMenu() {
		menu.showMenu();
	}
	
	/**
     * Handles the operation input from the user. Based on the input, it performs specific actions.
     * 
     * @param input the user's menu option choice
     * @return true if the user wants to continue operations, false if the user logs out
     */
	public boolean getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1: 
			iPatientList.viewPatientIDs();
			medicalRecordCtrl.showMedicalRecord(); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;
		
		case 2: 
			//update patient medical record
			iPatientList.viewPatientIDs();
			medicalRecordCtrl.updateMedicalRecord(); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;

			
		case 3:
			//view personal schedule
			iSchedule.viewPersonalSchedule();
			return true;
			
		case 4:
			//Set availability for appointment
			iSchedule.viewPersonalSchedule();
			iSchedule.setAvailability();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 5: 
			//Accept or Decline Appointment Requests
			iDocAppointmentCtrl.updateAppointmentRequest();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 6: 
			//View Upcoming Appointments
			iDocAppointmentCtrl.viewUpcomingAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 7: 
			//Record Appointment Outcome
			iDocAppointmentCtrl.recordAppointmentOutcome();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 8:
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