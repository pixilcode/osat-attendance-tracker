package model;

import java.util.ArrayList;
import java.util.HashMap;

import nu.xom.*;

public class Person {
	
	private String name;
	
	/**
	 * Info associated with the person
	 */
	private HashMap<String, String> info;
	
	/*
	
	Recommended attributes for Robotics
	
	int grade;
	String team;
	String position;
	String email;
	String discordAccount
	
	*/
	
	/**
	 * A collection of days that the person has attended
	 */
	private ArrayList<Date> attendance;
	
	/**
	 * The tasks assigned to the person
	 */
	private ArrayList<Task> tasks;
	
	/**
	 * @param name the name of the person
	 */
	public Person(String name) {
		this.name = name;
		this.info = new HashMap<String, String>();
		this.attendance = new ArrayList<Date>();
		this.tasks = new ArrayList<Task>();
	}
	
	/**
	 * @param name the name of the person
	 * @param info a collection of info about the person
	 */
	public Person(String name, HashMap<String, String> info) {
		this.name = name;
		this.info = info;
		this.attendance = new ArrayList<Date>();
		this.tasks = new ArrayList<Task>();
	}
	
	/**
	 * @param name the name of the person
	 * @param info a collection of info about the person
	 * @param attendance a collection of days the person has attended
	 * @param tasks
	 */
	public Person(String name, HashMap<String, String> info, ArrayList<Date> attendance, ArrayList<Task> tasks) {
		this.name = name;
		this.info = info;
		this.attendance = attendance;
		this.tasks = tasks;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<String, String> getInfo() {
		return info;
	}
	
	public ArrayList<Date> getAttendance() {
		return attendance;
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public void addInfo(String attribute, String value) {
		info.put(attribute, value);
	}
	
	public void removeInfo(String attribute) {
		info.remove(attribute);
	}
	
	public void addAttendedDay(Date date) {
		attendance.add(date);
	}
	
	public void removeAttendedDay(Date date) {
		attendance.remove(date);
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	/**
	 * @param prevTask the task to be edited
	 * @param newTask the new version of the task
	 */
	public void editTask(Task prevTask, Task newTask) {
		tasks.add(tasks.indexOf(prevTask), newTask);
		tasks.remove(prevTask);
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}

	/**
	 * Convert the person to an XML element
	 * @return the person as an XML element
	 */
	public Element toXMLelement() {
		
		//Create elements (info elements created later)
		Element name = new Element("name");
		Element attendance = new Element("attendance");
		Element tasks = new Element("tasks");
		
		//Create main element
		Element person = new Element("person");
		
		//Add name to name element and add name element
		name.appendChild(this.name);
		person.appendChild(name);
		
		//Create and add info elements from the hash map
		for(String textDescriptor : info.keySet()) {
			
			//Create a new element, add a descriptor, and add the actual text
			Element info = new Element("info");
			Attribute detail = new Attribute("descriptor", textDescriptor);
			info.addAttribute(detail);
			info.appendChild(this.info.get(textDescriptor));
			
			person.appendChild(info);
			
		}
		
		//Add each attendance date correctly
		for(int i = 0; i < this.attendance.size(); i++) {
			
			//Get the next date
			Date date = this.attendance.get(i);
			Element formattedDate = new Element("date");
			
			//Build date element in correct format (YYYY-MM-DD)
			String year = Integer.toString(date.year());
			String month = Integer.toString(date.month());
			String day = Integer.toString(date.day());
			
			if(date.day() < 10) {
				day = "0" + day;
			}
			
			if(date.month() < 10) {
				month = "0" + month;
			}
			
			if(date.year() < 10) {
				year = "000" + year;
			} else if(date.year() < 100) {
				year = "00" + year;
			} else if(date.year() < 1000) {
				year = "0" + year;
			}
			
			
			//Add the formatted date to the date element
			formattedDate.appendChild(year + "-" + month + "-" + day);
			
			//Add the date element to the attendance element
			attendance.appendChild(formattedDate);
		}
		
		//Add the attendance element
		person.appendChild(attendance);
		
		//Add the tasks
		for(int i = 0; i < this.tasks.size(); i++) {
			Element task = this.tasks.get(i).toXMLelement();
			tasks.appendChild(task);
		}
		
		//Add the tasks element
		person.appendChild(tasks);
		
		//Return the person element
		return person;
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		if (attendance == null) {
			if (other.attendance != null)
				return false;
		} else if (!attendance.equals(other.attendance))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tasks == null) {
			if (other.tasks != null)
				return false;
		} else if (!tasks.equals(other.tasks))
			return false;
		return true;
	}
	
}
