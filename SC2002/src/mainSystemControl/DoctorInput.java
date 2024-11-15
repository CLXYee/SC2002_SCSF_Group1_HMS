package mainSystemControl;

import java.util.Scanner;

import HospitalManagementSystem.HospitalApp;
import ShowUserMenu.ShowMenu;
import ShowUserMenu.ShowDoctorMenu;
import userInfoControl.*;

public class DoctorInput implements IGetOperationInput{
	private DoctorCtrl DoctorCtrl = null;
	private ShowMenu menu = null;
	//private PersonalSchedule PersonalSchedule = null;

	public DoctorInput(String hospitalID) {
		this.DoctorCtrl = new DoctorCtrl(hospitalID);
		this.menu = new ShowDoctorMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public boolean getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1: 
			DoctorCtrl.viewPatientIDs();
			DoctorCtrl.showMedicalRecord(); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;
		
		case 2: 
			//update patient medical record
			DoctorCtrl.viewPatientIDs();
			DoctorCtrl.updateMedicalRecord(); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			return true;

			
		case 3:
			//view personal schedule
			DoctorCtrl.viewPersonalSchedule();
			return true;
			
		case 4:
			//Set availability for appointment
			DoctorCtrl.viewPersonalSchedule();
			DoctorCtrl.setAvailability();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 5: 
			//Accept or Decline Appointment Requests
			DoctorCtrl.updateAppointmentRequest();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 6: 
			//View Upcoming Appointments
			DoctorCtrl.viewUpcomingAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 7: 
			//Record Appointment Outcome
			DoctorCtrl.recordAppointmentOutcome();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			return true;
			
		case 8:
			//Logout
			System.out.println("Loging out...\n");
			HospitalApp.main(null);
			return false;
		default:
			//Logout
			System.out.println("Loging out...\n");
			HospitalApp.main(null);
			return false;
		}
	}
}