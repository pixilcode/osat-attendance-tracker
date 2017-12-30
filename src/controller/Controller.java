package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import model.*;
import view.consoleUI.*;

public class Controller {
	
	public static final int PRESENT = 0;
	public static final int ABSENT = 1;
	public static final String[] MARKS = {"p", "a"};
	
	private static Roster roster;
	private static boolean loaded;
	
	public static void run() {
		(new CUIManager()).run();
	}
	
	public static void test() {
		TestRosterToTable.run();
	}

	public static Path getLocation() {
		// TODO Get location through GUI
		return null;
	}

	public static void loadRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.LOAD);
		loaded = true;
	}
	
	public static void newRoster(String loc) throws IOException {
		roster = new Roster(loc, Roster.Init.NEW);
		loaded = true;
	}
	
	public static boolean rosterIsLoaded() {
		return loaded;
	}

	public static String rosterLocation() {
		return roster.getLocation().toString();
	}

	public static void saveRoster() throws IOException {
		roster.writeXMLfile();
	}

	public static String rosterTable() {
		return roster.toTable();
	}

	public static LinkedList<String> getNames() {
		
		LinkedList<String> names = new LinkedList<String>();
		for(Person member : roster) {
			names.add(member.getName());
		}
		return names;
	}

	
	public static void markAttendance(HashMap<String, String> attendance, GregorianCalendar day) {
		
		for(String name : attendance.keySet())
			markAttendance(name, attendance.get(name), day);
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public static boolean markAttendance(String name, String marking, GregorianCalendar day) {
		
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
	
	public static boolean rosterContainsMember(String name) {
		return roster.contains(new Name(name));
	}

	public static void addMember(String name) {
		roster.addPerson(name);
	}
	
	public static void removeMember(String name) {
		roster.removePerson(new Name(name));
	}

	public static boolean rosterIsEmpty() {
		return roster.getMembers().isEmpty();
	}

}
