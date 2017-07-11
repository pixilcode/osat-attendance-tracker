package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nu.xom.*;

public class Task {
	
	String title;
	String description;
	GregorianCalendar due;
	
	public Task(String title, String description, int yearDue, int monthDue, int dayDue) {
		
		//Set title and description of task
		this.title = title;
		this.description = description;
		
		//Set the day that the task is due (month is 0-based)
		this.due = new GregorianCalendar(yearDue, --monthDue, dayDue);
		
	}
	
	public Task(String title, String description, GregorianCalendar due) {
		
		//Set title and description of task
		this.title = title;
		this.description = description;
		
		//Set the due date
		this.due = due;
		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GregorianCalendar getDue() {
		return due;
	}

	public void setDue(GregorianCalendar due) {
		this.due = due;
	}

	//Check to see if the task is due
	public boolean isDue() {
		
		GregorianCalendar today = new GregorianCalendar();
		
		//If today's year, month, and day is greater than or equal to the year, month, and day
		if(today.get(Calendar.YEAR) >= due.get(Calendar.YEAR) && today.get(Calendar.MONTH) >= due.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) >= due.get(Calendar.DAY_OF_MONTH)) return true;
		else return false;
		
	}
	
	public Element toXMLelement() {
		
		//Create elements
		Element title = new Element("title");
		Element description = new Element("description");
		Element due = new Element("due");
		
		//Add info to title and description elements
		title.appendChild(this.title);
		description.appendChild(this.description);
		
		//Build due element in correct format (YYYY-MM-DD)
		GregorianCalendar date = this.due;
		
		String year = Integer.toString(date.get(Calendar.YEAR));
		String month = Integer.toString(date.get(Calendar.MONTH) + 1);
		String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
		
		if(date.get(Calendar.DAY_OF_MONTH) < 10) {
			day = "0" + day;
		}
		
		if(date.get(Calendar.MONTH) < 10) {
			month = "0" + month;
		}
		
		if(date.get(Calendar.YEAR) < 10) {
			year = "000" + year;
		} else if(date.get(Calendar.YEAR) < 100) {
			year = "00" + year;
		} else if(date.get(Calendar.YEAR) < 1000) {
			year = "0" + year;
		}
		
		//Add the formatted date to the due element
		due.appendChild(year + "-" + month + "-" + day);
		
		//Build the task element and return it
		Element task = new Element("task");
		task.appendChild(title);
		task.appendChild(description);
		task.appendChild(due);
		
		return task;
		
	}

}
