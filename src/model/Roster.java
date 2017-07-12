package model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import controller.Controller;
import nu.xom.*;

public class Roster implements Iterable<Person> {
	
	//The location of the xml file
	private Path location;
	private ArrayList<Person> members;
	
	boolean exists;
	
	//Throw IOException so that GUI can alert person of error
	public Roster(String location) throws IOException {
		
		//Reset Controller variables
		Controller.resetPersonID();
		Controller.resetTaskID();
		
		//Create a new array for members
		this.members = new ArrayList<Person>();
		
		this.location = Paths.get(location);
		
		//If the location
		if(this.location.toFile().exists()) {
			readXMLfile(this.location);
			exists = true;
		}
		else {
			exists = false;
		}
		
		
	}
	
	//Throw IOException so that GUI can alert person of error
	private void readXMLfile(Path location) throws IOException {
			
		try {
			//Create the XML doc
			Builder builder = new Builder();
			Document doc = builder.build(location.toFile());
			
			//Get the root element
			Element roster = doc.getRootElement();
			
			//Get each person element
			Elements membersElements = roster.getChildElements();
			ArrayList<Person> members = new ArrayList<Person>();
			
			//For each person, create a new person element
			for(int i = 0; i < membersElements.size(); i++) {
				
				//Get each person
				Element person = membersElements.get(i);
				
				//Get their name and convert to text
				//Parameter for Person object
				String name = person.getFirstChildElement("name").getChild(0).toXML();
				
				//Get each info and add it to the info hashmap
				//Parameter for Person object
				HashMap<String, String> info = new HashMap<String, String>();
				Elements infoElements = person.getChildElements("info");
				
				for(int j = 0; j < infoElements.size(); j++) {
					
					String attribute = infoElements.get(j).getAttributeValue("descriptor");
					String value = infoElements.get(j).getChild(0).toXML();
					
					info.put(attribute, value);
					
				}
				
				//Get attendance by getting each date and converting it to a GregorianCalendar
				//Parameter for Person object
				ArrayList<GregorianCalendar> attendance = new ArrayList<GregorianCalendar>();
				Elements attendanceElements = person.getFirstChildElement("attendance").getChildElements();
				
				for(int j = 0; j < attendanceElements.size(); j++) {
					
					//Get the date string
					String date = attendanceElements.get(j).getChild(0).toXML();
					
					//Get the year, month, and day from YYYY-MM-DD
					int year = Integer.parseInt(date.substring(0, 4));
					int month = Integer.parseInt(date.substring(5, 7));
					int day = Integer.parseInt(date.substring(8));
					
					//Convert to a GregorianCalendar and add to array
					attendance.add(new GregorianCalendar(year, month - 1, day));
					
				}
				
				//Get each task
				//Parameter for Person object
				ArrayList<Task> tasks = new ArrayList<Task>();
				Elements taskElements = person.getFirstChildElement("tasks").getChildElements();
				
				for(int j = 0; j < taskElements.size(); j++) {
					
					//Get the task element
					Element taskElement = taskElements.get(j);
					
					//Get the title
					String title = taskElement.getFirstChildElement("title").toXML();
					
					//Get the description
					String description = taskElement.getFirstChildElement("description").toXML();
					
					//Get the date string
					String date = taskElement.getFirstChildElement("due").toXML();
					
					//Get the year, month, and day from YYYY-MM-DD
					int year = Integer.parseInt(date.substring(0, 4));
					int month = Integer.parseInt(date.substring(5, 7));
					int day = Integer.parseInt(date.substring(8));
					
					//Convert to a GregorianCalendar
					GregorianCalendar due = new GregorianCalendar(year, month, day);
					
					//Create new Task and add to array
					tasks.add(new Task(title, description, due, Controller.getTaskID()));
					
				}
				
				//Create Person object and add to array
				members.add(new Person(name, info, attendance, tasks, Controller.getPersonID()));
				
			}
			
		} catch(ValidityException ve) {
			throw new IOException("Invalid XML");
		} catch(ParsingException pe) {
			throw new IOException("Unable to parse");
		}
		
	}
	
	//Throws IOException so that GUI can alert person of error
	public void writeXMLfile() throws IOException {
		
		try {
			//If the file does not exist, make it
			File f = new File(location.toString());
			f.getParentFile().mkdirs();
			f.createNewFile();
		} catch(NullPointerException npe) {
			//If the file doesn't have parent files, make one without referencing it
			File f = new File(location.toString());
			f.createNewFile();
		}
		
		//Create the root element
		Element roster = new Element("roster");
		
		//Add each person into the root element
		for(int i = 0; i < members.size(); i++) {
			roster.appendChild(members.get(i).toXMLelement());
		}
		
		//Create a document to hold the root element
		Document doc = new Document(roster);
		
		//Write the XML file
		try {
			
			//Format the file
			FileOutputStream fos = new FileOutputStream(location.toString());
			Serializer output = new Serializer(fos, "ISO-8859-1");
			output.setIndent(2);
			output.write(doc);
			
		} catch (UnsupportedEncodingException e) {
			
			//If the encoding is not supported, don't format the file
			FileWriter fw = new FileWriter(location.toString());
			BufferedWriter out = new BufferedWriter(fw);
			out.write(doc.toXML());
			out.close();
			
		}
		
	}
	
	public void addPerson(String name) {
		members.add(new Person(name, Controller.getPersonID()));
	}
	
	public void addPerson(String name, HashMap<String, String> info) {
		members.add(new Person(name, info, Controller.getPersonID()));
	}
	
	public Path getLocation() {
		return location;
	}
	
	public ArrayList<Person> getMembers() {
		return members;
	}
	
	public Iterator<Person> iterator() {
		return new RosterIterator(members);
	}
	
	//For iteration in a foreach loop
	private class RosterIterator implements Iterator<Person> {
		
		private final ArrayList<Person> members;
		private int cursor;
		
		RosterIterator(ArrayList<Person> members) {
			
			cursor = 0;
			this.members = members;
			
		}

		public boolean hasNext() {
			if(cursor >= members.size()) {
				return false;
			} else {
				return true;
			}
		}

		public Person next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			return members.get(cursor++);
			
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		
		
	}
	
}
