package controller;

import java.io.IOException;
import java.util.*;

import model.*;
import view.consoleUI.*;

public class Controller {
	
	public static final int PRESENT = 0;
	public static final int ABSENT = 1;
	public static final String[] MARKS = {"p", "a"};
	
	private Roster roster;
	private boolean loaded;
	private boolean usingGUI;
	
	public void run() {
		(new CUIManager(this)).run();
	}

	public void loadRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.LOAD);
		loaded = true;
	}
	
	public void newRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.NEW);
		loaded = true;
	}
	
	public boolean rosterIsLoaded() {
		return loaded;
	}

	public String rosterLocation() {
		return roster.getLocation().toString();
	}

	public void saveRoster() throws IOException {
		roster.writeXMLfile();
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
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean markAttendance(String name, String marking, GregorianCalendar day) {
		
		try {
			Person person = roster.getMembers().get(roster.getMembers().indexOf(new Name(name)));
			if(marking.equals(MARKS[PRESENT])) {
				person.addAttendedDay(day);
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException ae) {
			return false;
		}
		
	}
	
	public boolean rosterContainsMember(String name) {
		return roster.contains(new Name(name));
	}

	public void addMember(String name) {
		roster.addPerson(name);
	}
	
	public void removeMember(String name) {
		roster.removePerson(new Name(name));
	}

	public boolean rosterIsEmpty() {
		return roster.getMembers().isEmpty();
	}
	
	/**
	 * @param args the arguments to parse
	 * @return whether the argument parsing succeeded or not
	 */
	public boolean parseArgs(String[] args) {
		
		usingGUI = true;
		Roster.Init init = Roster.Init.LOAD;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i] == "--no-gui")
				usingGUI = false;
			else if(args[i] == "--new"){
				init = Roster.Init.NEW;
			} else {
				try {
					roster = new Roster(args[i], init);
				} catch (IOException e) {
					return false;
				}
			}
		}
		
		// Roster must have location when --new is used
		// Otherwise, parseArgs fails
		if(init.equals(Roster.Init.NEW) && roster == null)
			return false;
		
		return true;
		
	}
	
	public boolean isUsingGUI() {
		return usingGUI;
	}

	private static void printHelp() {
		System.out.println("HALP!");
	}

}
