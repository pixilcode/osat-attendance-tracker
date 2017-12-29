package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import model.*;
import view.consoleUI.*;
import view.graphicUI.*;

public class Controller {
	
	public static final int PRESENT = 0;
	public static final int ABSENT = 1;
	public static final String[] MARKS = {"p", "a"};
	
	private static Roster roster;
	private static boolean loaded;
	
	public static void run() {
		
	}
	
	public static void test() {
//		(new CUIManager()).run();
		System.out.println(TestWrapper.run());
	}

	public static Path getLocation() {
		// TODO Get location through GUI
		return null;
	}

	public static void loadRoster(String loc) throws IOException {
		roster = new Roster(loc);
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

	public static String rosterToString() {
		return roster.toString();
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

	public static boolean markAttendance(String name, String marking, GregorianCalendar day) {
		
		try {
			Person person = roster.getMembers().get(roster.getMembers().indexOf(name));
			if(marking.equals(MARKS[PRESENT])) {
				person.addAttendedDay(day);
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException ae) {
			return false;
		}
		
	}

	public static void addMember(String name) {
		roster.addPerson(name);
	}
	
	public static void removeMember(String name) {
		roster.removePerson(new Name(name));
	}

}
