package mainSystemControl;

import java.util.Scanner;

import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowPatientMenu;
import userInfoControl.AppointmentCtrl;
import userInfoControl.MedicalRecordCtrl;
import userInfoControl.PatientCtrl;

public class PatientInput implements GetOperationInput {
	private PatientCtrl patientCtrl = null;
	private ShowMenu menu = null;
	private MedicalRecordCtrl medicalRecordCtrl; 
	private AppointmentCtrl appointmentCtrl;

	public PatientInput(String hospitalID) {
		this.patientCtrl = new PatientCtrl(hospitalID);
		this.medicalRecordCtrl = this.patientCtrl;
		this.appointmentCtrl = this.patientCtrl;
		this.menu = new ShowPatientMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			medicalRecordCtrl.showMedicalRecord();
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			medicalRecordCtrl.updateMedicalRecord();
			break;
		case 3:
			//View Available Appointment Slots
			break;
		case 4:
			//Schedule an Appointment
			appointmentCtrl.scheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 5:
			//Reschedule an Appointment
			appointmentCtrl.rescheduleAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 6:
			//Cancel an Appointment
			appointmentCtrl.cancelAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 7:
			//View Scheduled Appointments
			appointmentCtrl.viewScheduledAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 8:
			//View Past Appointment Outcome Records
			break;
		case 9:
			//Logout
			break;
		}
	}

}
