package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import model.Date;
import model.Person;
import model.Roster;
import view.consoleUI.Sequence;

public class Controller {
	
	public static final int PRESENT = 0;
	public static final int ABSENT = 1;
	public static final String[] MARKS = { "p", "a" };
	
	private Roster roster;
	private boolean loaded;
	private boolean usingGUI;
	
	public void run() {
		Sequence.setController(this);
	}
	
	public void loadRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.LOAD);
		loaded = true;
	}
	
	public void newRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.NEW);
		loaded = true;
	}
	
	public void saveRoster() throws IOException {
		roster.writeXMLfile();
	}
	
	public boolean rosterIsLoaded() {
		return loaded;
	}
	
	public boolean rosterIsEmpty() {
		return roster.getMembers().isEmpty();
	}
	
	public String rosterLocation() {
		return roster.getLocation().toString();
	}
	
	public String rosterTable() {
		return roster.toTable();
	}
	
	public LinkedList<String> getNames() {
		
		LinkedList<String> names = new LinkedList<String>();
		for(Person member : roster) {
			names.add(member.getName());
		}
		return names;
	}
	
	public void markAttendance(HashMap<String, String> attendance, GregorianCalendar day) {
		
		for(String name : attendance.keySet())
			markAttendance(name, attendance.get(name), day);
		
	}
	
	public boolean markAttendance(String name, String marking, GregorianCalendar day) {
		
		Date date = new Date(day);
		
		try {
			Person person = roster.getMember(name);
			if(marking.equals(MARKS[PRESENT])) {
				person.addAttendedDay(date);
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException ae) {
			return false;
		}
		
	}
	
	public void addMember(String name) {
		roster.addPerson(name);
	}
	
	public void removeMember(String name) {
		roster.removePerson(name);
	}
	
	public boolean rosterContainsMember(String name) {
		return roster.contains(name);
	}
	
	public ArrayList<GregorianCalendar> getMemberAttendance(String name) {
		
		Person member = roster.getMember(name);
		
		ArrayList<GregorianCalendar> attendance = new ArrayList<GregorianCalendar>();
		
		for(Date day : member.getAttendance()) {
			attendance.add(new GregorianCalendar(day.year(), day.month(), day.day()));
		}
		
		return attendance;
	}
	
	/**
	 * @param args
	 *            the arguments to parse
	 * @return whether the argument parsing succeeded or not
	 */
	public void parseArgs(String[] args) throws IOException {
		
		usingGUI = true;
		Roster.Init init = Roster.Init.LOAD;
		
		String location = null;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i] == "--no-gui")
				usingGUI = false;
			else if(args[i] == "--new")
				init = Roster.Init.NEW;
			else
				location = args[i];
		}
		
		if(location != null) {
			roster = new Roster(location, init);
			loaded = true;
		}
		
	}
	
	public boolean isUsingGUI() {
		return usingGUI;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (loaded ? 1231 : 1237);
		result = prime * result + ( (roster == null) ? 0 : roster.hashCode());
		result = prime * result + (usingGUI ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(! (obj instanceof Controller))
			return false;
		Controller other = (Controller) obj;
		if(loaded != other.loaded)
			return false;
		if(roster == null) {
			if(other.roster != null)
				return false;
		} else if(!roster.equals(other.roster))
			return false;
		if(usingGUI != other.usingGUI)
			return false;
		return true;
	}
	
}
