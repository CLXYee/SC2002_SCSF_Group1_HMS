package mainSystemControl;

import java.util.Scanner;

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
		//this.PersonalSchedule = new PersonalSchedule();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public void getOperationInput(int input) {
		Scanner sc = new Scanner(System.in);
		switch(input) {
		case 1:
			//View patient medical record
			System.out.println("Enter the patient ID to view: ");
			String id = sc.next();
			DoctorCtrl.showMedicalRecord(id); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
			break;
		case 2:
			//update patient medical record
			System.out.println("Enter the patient ID to update medical record: ");
			String id1 = sc.next();
			DoctorCtrl.showMedicalRecord(id1); 
			DoctorCtrl.updateMedicalRecord(id1); 
			System.out.print("Press <Enter> to continue:");
			// Dummy scanner to let the system stop for user to check information
			sc.nextLine();
		case 3:
			//view personal schedule
			DoctorCtrl.viewPersonalSchedule();
			break;
		case 4:
			//Set availability for appointment
			DoctorCtrl.setAvailability();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 5:
			//Accept or Decline Appointment Requests
			System.out.println("Enter the patient ID to update medical record: ");
			String id2 = sc.next();
			DoctorCtrl.updateAppointmentRequest(id2);
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 6:
			//View Upcoming Appointments
			DoctorCtrl.viewUpcomingAppointment();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 7:
			//Record Appointment Outcome
			DoctorCtrl.recordAppointmentOutcome();
			System.out.print("Press <Enter> to continue:");
			sc.nextLine();
			break;
		case 8:
			//Logout
			break;
	}
	}
}