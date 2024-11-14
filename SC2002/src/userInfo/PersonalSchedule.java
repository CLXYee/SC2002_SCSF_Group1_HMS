package userInfo;

import java.util.ArrayList;
import java.util.List;
import CSV.DoctorCSVOperator;

public class PersonalSchedule 
{
	private DoctorCSVOperator csv = new DoctorCSVOperator();
	private Object[][] schedule = new Object[7][16];
	
	public PersonalSchedule(String doctorID)
	{
		int day, startTime, endTime;
		Object task;
		List<String> data = csv.readFile(doctorID);
		
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
	
	
	public void editSchedule(int day, int startTime, int endTime, Object task)
	{
		for (int time = startTime; time <= endTime; time++)
		{
			this.schedule[day][time] = task;
		}
	}
	
	
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
}
