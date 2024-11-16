package userInfo;

import java.time.LocalDate;
import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.List;
import CSV.DoctorCSVOperator;

/**
 * Represents a doctor's personal schedule and provides methods for managing and viewing their schedule.
 * The schedule consists of 7 days (Monday to Sunday), each with 16 time slots.
 * Each time slot can be marked as Free (F) or assigned to a specific task such as meetings, surgical operations, etc.
 */
public class PersonalSchedule 
{
	private DoctorCSVOperator csv = new DoctorCSVOperator();
	private Object[][] schedule = new Object[7][16];
	
	/**
     * Constructs a PersonalSchedule object for a specific doctor based on the provided doctorID.
     * Initializes the schedule with data read from a CSV file, setting each time slot to 'F' (Free) initially.
     *
     * @param doctorID the ID of the doctor whose schedule is being initialized
     */
	public PersonalSchedule(String doctorID)
	{
		int day, startTime, endTime;
		Object task;
		List<String> data = csv.readFile(doctorID, 1);
		
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				this.schedule[i][j] = 'F';
			}
		}
		
		for (int n = 9; n < data.size(); n += 4)
		{
			day = Integer.parseInt(String.valueOf(data.get(n).replace("\"", "")));
			startTime = Integer.parseInt(String.valueOf(data.get(n+1).replace("\"", "")));
			endTime = Integer.parseInt(String.valueOf(data.get(n+2).replace("\"", "")));
			
			if (Character.isDigit(data.get(n+3).charAt(0)))
			{
				task = Integer.parseInt(data.get(n+3).replace("\"", ""));
			}
			else
			{
				task = data.get(n+3).charAt(0);
			}
			 
			for (int time = startTime; time <= endTime; time++)
			{
				this.schedule[day][time] = task;
			}
		}
	}
	
	/**
     * Edits the schedule by assigning a task to a specific time range.
     *
     * @param day the day of the week (0 = Monday, 6 = Sunday)
     * @param startTime the starting time slot
     * @param endTime the ending time slot
     * @param task the task to assign (e.g., 'M' for meeting, 'S' for surgery)
     */
	public void editSchedule(int day, int startTime, int endTime, Object task)
	{
		for (int time = startTime; time <= endTime; time++)
		{
			this.schedule[day][time] = task;
		}
	}
	
	
	/**
     * Translates the schedule into a string representation suitable for saving or displaying.
     * The translation lists day, start time, end time, and task for each task block.
     *
     * @return the string representation of the schedule
     */
	public String translateSchedule()
	{
		int startTime, endTime;
		Object currentTask;
		String translation = "";
		for (int day = 0; day < 7; day++)
		{
			currentTask = 'F';
			startTime = 0;
			endTime = 0;
			for (int time = 0; time < 16; time++)
			{
				if (this.schedule[day][time] == currentTask)
				{
					endTime++;
				}
				else if (!currentTask.equals('F'))
				{
					translation += day + "," + startTime + "," + endTime + "," + currentTask + ",";
					currentTask = this.schedule[day][time];
					startTime = time;
					endTime = time;
				}
				else
				{
					currentTask = this.schedule[day][time];
					startTime = time;
					endTime = time;
				}
			}
			if (!currentTask.equals('F'))
			{
				translation += day + "," + startTime + "," + endTime + "," + currentTask + ",";
			}
		}
		
		return translation.substring(0, translation.length() - 1);
	}
	
	
	/**
     * Displays the current schedule, showing each day's tasks in a time slot grid.
     * It labels each task with a specific character code (e.g., 'M' for meeting, 'S' for surgery, etc.).
     */
	public void viewSchedule()
	{
		System.out.println("F  : Free Slot");
		System.out.println("M  : Meeting");
		System.out.println("S  : Surgical Operations");
		System.out.println("B  : Break Time");
		System.out.println("T  : Training Session");
		System.out.println("P  : Personal Leave");
		System.out.println("No.: Number of Appointment\n");
		System.out.println("\t  10:00\t  10:30\t  11:00\t  11:30\t  12:00\t  12:30\t  13:00\t  13:30\t"
						 + "  14:00\t  14:30\t  15:00\t  15:30\t  16:00\t  16:30\t  17:00\t  17:30\t  18:00");
		
		for (int i = 0; i < 7; i++)
		{
			switch (i)
			{
				case 0:
					System.out.print("Monday\t");
					break;
				case 1:
					System.out.print("Tuesday\t");
					break;
				case 2:
					System.out.print("Wednesday");
					break;
				case 3:
					System.out.print("Thursday");
					break;
				case 4:
					System.out.print("Friday\t");
					break;
				case 5:
					System.out.print("Saturday");
					break;
				case 6:
					System.out.print("Sunday\t");
					break;
			}
			
			for (int j = 0; j < 16; j++)
			{
				System.out.print("\t" + this.schedule[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
     * Checks if the given time range is free for a specific day.
     *
     * @param day the day of the week (0 = Monday, 6 = Sunday)
     * @param startTime the starting time slot
     * @param endTime the ending time slot
     * @return true if the time range is free, false otherwise
     */
	public boolean slotsAreFree(int day, int startTime, int endTime)
	{
		for (int time = startTime; time <= endTime; time++)
		{
			if (!this.schedule[day][time].equals('F'))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
     * Displays the available slots for appointment booking from the next day until the end of the week.
     * Available slots are marked as 'O', and unavailable slots are marked as '-'.
     */
	public void viewFreeSlots()
	{
		LocalDate today = LocalDate.now(); // Get today's date
		DayOfWeek dayOfWeek = today.getDayOfWeek(); // Get today's day
        
		System.out.println("O  : Available Slot");
		System.out.println("-  : Not Available");
		System.out.println("*** Note that you are only allowed to book appointments at least one day before the appointment ***\n");
		System.out.println("\t  10:00\t  10:30\t  11:00\t  11:30\t  12:00\t  12:30\t  13:00\t  13:30\t"
						 + "  14:00\t  14:30\t  15:00\t  15:30\t  16:00\t  16:30\t  17:00\t  17:30\t  18:00");
		
		// It will only show the schedule for the next day until the end of the week (Sunday)
		// So the patient can only book appointments for the next day onwards (Sunday shows nothing)
		for (int i = 0 + dayOfWeek.getValue(); i < 7; i++)
		{
			switch (i)
			{
				case 0:
					System.out.print("Monday\t");
					break;
				case 1:
					System.out.print("Tuesday\t");
					break;
				case 2:
					System.out.print("Wednesday");
					break;
				case 3:
					System.out.print("Thursday");
					break;
				case 4:
					System.out.print("Friday\t");
					break;
				case 5:
					System.out.print("Saturday");
					break;
				case 6:
					System.out.print("Sunday\t");
					break;
			}
			
			for (int j = 0; j < 16; j++)
			{
				if (this.schedule[i][j].equals('F'))
				{
					System.out.print("\tO");
				}
				else
				{
					System.out.print("\t-");
				}
			}
			System.out.println();
		}
	}
}
