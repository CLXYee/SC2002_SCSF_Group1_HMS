package mainSystemControl;

import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowDoctorMenu;
import userInfoControl.*;

public class DoctorInput implements IGetOperationInput{
	private DoctorCtrl doctorCtrl = null;
	private ShowMenu menu = null;
	private MedicalRecordCtrl medicalRecordCtrl=null;
	private IDocAppointmentCtrl iDocAppointmentCtrl;
	private IPatientList iPatientList;
	private ISchedule iSchedule;

	public DoctorInput(String hospitalID) {
		this.doctorCtrl = new DoctorCtrl(hospitalID);
		this.menu = new ShowDoctorMenu();
		this.medicalRecordCtrl = this.doctorCtrl;
		this.iDocAppointmentCtrl = this.doctorCtrl;
		this.iPatientList = this.doctorCtrl;
		this.iSchedule = this.doctorCtrl;
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
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
			//Logout
			System.out.println("Loging out...\n");
			return false;
		}
	}
}