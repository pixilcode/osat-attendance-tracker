package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import nu.xom.*;

public class Roster implements Iterable<Person> {
	
	/**
	 * The location of the XML file
	 */
	private Path location;
	
	/**
	 * The members on the roster
	 */
	private ArrayList<Person> members;
	
	/**
	 * @param location the location of the roster
	 * @param type the type of roster initialization
	 * @throws IOException the XML file is unreadable
	 */
	//Throw IOException so that UI can alert person of error
	public Roster(String locationString, Init type) throws IOException {
		
		//Create a new array for members
		members = new ArrayList<Person>();
		
		location = Paths.get(locationString);
		
		//Check the type of initialization and respond accordingly
		switch(type) {
		case LOAD:
			if(location.toFile().exists()) {
				readXMLfile(location);
			} else {
				throw new IOException("File doesn't exist");
			}
			break;
		case NEW:
			if(location.toFile().exists()) {
				throw new IOException("File already exists");
			} else {
				writeXMLfile();
			}
			break;
		}
		
	}
	
	/**
	 * @param location the location of the XML file 
	 * @throws IOException the XML file is unreadable
	 */
	//Throw IOException so that UI can alert person of error
	private void readXMLfile(Path location) throws IOException {
			
		try {
			//Create the XML doc
			Builder builder = new Builder();
			Document doc = builder.build(location.toFile());
			
			//Get the root element
			Element roster = doc.getRootElement();
			
			//Get each person element
			Elements membersElements = roster.getChildElements();
			
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
				
				//Get attendance by getting each date
				//Parameter for Person object
				ArrayList<Date> attendance = new ArrayList<Date>();
				Elements attendanceElements = person.getFirstChildElement("attendance").getChildElements();
				
				for(int j = 0; j < attendanceElements.size(); j++) {
					
					//Get the date string
					String date = attendanceElements.get(j).getChild(0).toXML();
					
					//Get the year, month, and day from YYYY-MM-DD
					int year = Integer.parseInt(date.substring(0, 4));
					int month = Integer.parseInt(date.substring(5, 7));
					int day = Integer.parseInt(date.substring(8));
					
					//Convert to a Date and add to array
					attendance.add(new Date(year, month, day));
					
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
					
					//Convert to a Date
					Date due = new Date(year, month, day);
					
					//Create new Task and add to array
					tasks.add(new Task(title, description, due));
					
				}
				
				//Create Person object and add to array
				members.add(new Person(name, info, attendance, tasks));
				
			}
			
		} catch(ValidityException ve) {
			throw new IOException("Invalid XML");
		} catch(ParsingException pe) {
			throw new IOException("Unable to parse");
		}
		
	}
	
	/**
	 * Compile the XML document and write it
	 * @throws IOException error when creating file
	 */
	//Throws IOException so that UI can alert person of error
	public void writeXMLfile() throws IOException {
		
		try {
			//If the file does not exist, make it
			File f = new File(location.toString());
			f.getParentFile().mkdirs();
			f.createNewFile();
		} catch(NullPointerException npe) {
//			//If the file doesn't have parent files, make one without referencing it
//			File f = new File(location.toString());
//			f.createNewFile();
			// Above method makes a file in the Java file
			throw new IOException("Location does not exist");
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
		members.add(new Person(name));
	}
	
	public void addPerson(String name, HashMap<String, String> info) {
		members.add(new Person(name, info));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void removePerson(String name) {
		members.remove(new Name(name));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean contains(String name) {
		return members.contains(new Name(name));
	}
	
	public Path getLocation() {
		return location;
	}
	
	public ArrayList<Person> getMembers() {
		return members;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public Person getMember(String name) {
		return members.get(members.indexOf(new Name(name)));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Roster [members=");
		builder.append(members);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * @return a string containing the roster in table form
	 */
	public String toTable() {
		
		StringBuilder builder = new StringBuilder();
		int[] columnLength = new int[2];
		
		// Find the greatest length
		for(Person member : members) {
			
			if(member.getName().length() > columnLength[0])
				columnLength[0] = member.getName().length();
			if(member.getAttendance().size() > columnLength[1])
				columnLength[1] = Integer.toString(member.getAttendance().size()).length();
			
		}
		
		if(columnLength[0] < "Name".length()) columnLength[0] = "Name".length();
		if(columnLength[1] < "Days Present".length()) columnLength[1] = "Days Present".length();
		
		// Create the divider between cells
		// as well as something to draw spaces from
		StringBuilder mainDivider = new StringBuilder();
		StringBuilder divider = new StringBuilder();
		StringBuilder spaces = new StringBuilder();
		for(int i = 0; i < columnLength[0] + columnLength[1] + 3; i++) {
			mainDivider.append('=');
			divider.append('-');
			spaces.append(' ');
		}
		
		// Build the title => |Names      |Days Present|
		builder.append(mainDivider).append('\n');
		builder.append(
				'|').append(
				"Name").append(
				spaces.substring("Name".length(), columnLength[0])).append(
				'|').append(
				"Days Present").append(
				spaces.substring("Days Present".length(), columnLength[1])).append(
				'|').append(
				'\n');
		builder.append(mainDivider).append('\n');
		
		boolean first = true;
		
		for(Person member : members) {
			
			if(first) first = false;
			else builder.append(divider).append('\n');
			
			String name = member.getName();
			int nameLength = name.length();
			int daysPresent = member.getAttendance().size();
			int daysPresentLength = Integer.toString(daysPresent).length();
			
			builder.append(
					'|').append(
					name).append(
					spaces.substring(nameLength, columnLength[0])).append(
					'|').append(
					daysPresent).append(
					spaces.substring(daysPresentLength, columnLength[1])).append(
					'|').append(
					'\n');
		}
		
		builder.append(divider);
		
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Roster))
			return false;
		Roster other = (Roster) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		return true;
	}

	public enum Init {
		LOAD, NEW;
	}
	
	public Iterator<Person> iterator() {
		return new RosterIterator(members);
	}
	
	//For iteration in a foreach loop
	private class RosterIterator implements Iterator<Person> {
		
		private final ArrayList<Person> members;
		private int cursor;
		
		RosterIterator(ArrayList<Person> memberList) {
			
			cursor = 0;
			members = memberList;
			
		}

		public boolean hasNext() {
			if(cursor >= members.size())
				return false;
			else
				return true;
		}
		
		public Person next() {
			if(!hasNext())
				throw new NoSuchElementException();
			
			return members.get(cursor++);
			
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		
		
	}
	
}
