package mainSystemControl;

import java.io.File;
import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPatientMenu;
import userInfoControl.AppointmentCtrl;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

public class PatientInput implements IGetOperationInput {
	private PatientCtrl patientCtrl = null;
	private ShowMenu menu = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private AppointmentCtrl appointmentCtrl;

	public PatientInput(String hospitalID, String name, String gender, int age) {
		this.patientCtrl = new PatientCtrl(hospitalID, name, gender, age);
		this.medicalRecordCtrl = this.patientCtrl;
		this.appointmentCtrl = this.patientCtrl;
		this.menu = new ShowPatientMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
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
			return true;
		case 3:
			//View Available Appointment Slots
			patientCtrl.viewAvailableSlots();
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
			patientCtrl.viewPastRecords();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
		case 9:
			//Logout
			System.out.println("Loging out...\n");
			return false;
		default:
			//Logout
			System.out.println("Loging out...\n");
			return false;
		}
	}

}
