package view.consoleUI;

import java.io.*;
import java.util.*;

import controller.Controller;
import view.UserInterface;

public class CUIManager implements UserInterface {
	
	Controller controller;
	
	public CUIManager(Controller c) {
		
		controller = c;
		
	}
	
	public UserInterface run() {
		
		while(!controller.rosterIsLoaded()) {
			try {
				welcomeSequence();
			} catch(IOException ioe) {
				Console.println("Invalid path or file name");
			}
		}
		
		mainSequence();
		
		return this;
		
	}
	
	private void welcomeSequence() throws IOException {
		
		Console.printTitle("Welcome");
		Console.println("Load a roster or make a new one");
		Console.println("1. Load Roster");
		Console.println("2. New Roster");
		Console.println("3. Exit");
		
		int option = Console.promptOption(3);
		switch(option) {
		case 1:
			loadRosterSequence();
			break;
		case 2:
			newRosterSequence();
			break;
		case 3:
			System.exit(0);
		}
		
		mainSequence();
		
	}

	private void loadRosterSequence() throws IOException {
		
		Console.printTitle("Load Roster");
		Console.printLines(1);
		Console.println("Warning: Not entering the full path may cause problems");
		Console.printLines(1);
		
		String loc = Console.promptString("Roster Path >> ");
		controller.loadRoster(loc);
		
	}
	
	private void newRosterSequence() throws IOException {
		
		Console.printTitle("New Roster");
		Console.printLines(1);
		Console.println("Warning: Not entering the full path may cause problems");
		Console.printLines(1);
		
		String dir = Console.promptString("Roster Directory >> ");
		String name = Console.promptString("File Name >> ");
		
		if(!dir.endsWith(File.separator)) dir += File.separator;
		if(!name.endsWith(".xml")) name += ".xml";
		String loc = dir + name;
		
		controller.newRoster(loc);
		
	}
	
	private void mainSequence() {
		
		Console.printTitle("ON A ROLL");
		Console.println("What would you like to do?");
		Console.println("1. Manage Roster");
		Console.println("2. Mark Roster");
		Console.println("3. Print Roster Summary");
		Console.println("4. Save Roster");
		Console.println("5. Exit");
		
		int option = Console.promptOption(5);
		switch (option) {
		case 1:
			manageRosterSequence();
			break;
		case 2:
			markRosterSequence();
			break;
		case 3:
			printRosterSequence();
			break;
		case 4:
			saveRosterSequence();
			break;
		case 5:
			System.exit(0);
		}
	}
	
	private void manageRosterSequence() {
		
		Console.printTitle("Manage Roster");
		Console.println("What would you like to do?");
		Console.println("1. Add a member");
		Console.println("2. Remove a member");
//		Console.println("3. Add member info");
//		Console.println("4. Edit member info");
//		Console.println("5. Remove member info");
//		Console.println("6. Add task to member");
//		Other task related stuff
		Console.println("3. Return to Main Menu");
		Console.println("4. Exit");
		
		int option = Console.promptOption(4);
		
		switch(option) {
		case 1:
			addMemberSequence();
			break;
		case 2:
			removeMemberSequence();
			break;
		case 3:
			mainSequence();
			break;
		case 4:
			System.exit(0);
		}
		
	}

	private void addMemberSequence() {
		
		Console.printTitle("Add Member");
		Console.println("Input the name of the member\nthat you would like to add");
		
		String name = Console.promptString("Name >> ");
		
		controller.addMember(name);
		
		mainSequence();
		
	}
	
	private void removeMemberSequence() {
		
		Console.printTitle("Remove Member");
		Console.println("Input the name of the member\nthat you would like to remove");
		
		String name = Console.promptString("Name >> ");
		
		if(controller.rosterContainsMember(name))
			controller.removeMember(name);
		else
			Console.println("\nMember does not exist");
		
		mainSequence();
		
	}

	private void markRosterSequence() {
		
		Console.printTitle("Mark Roster");
		Console.println("What would you like to do?");
		Console.println("1. Mark all members");
		Console.println("2. Mark one member");
		Console.println("3. Mark all members for a previous day");
		Console.println("4. Mark one member for a previous day");
		Console.println("5. Return to the Main Menu");
		Console.println("6. Exit");
		
		int option = Console.promptOption(6);
		
		switch(option) {
		case 1:
			markAllSequence();
			break;
		case 2:
			markOneSequence();
			break;
		case 3:
			markAllPreviousDaySequence();
			break;
		case 4:
			markOnePreviousDaySequence();
			break;
		case 5:
			mainSequence();
			break;
		case 6:
			System.exit(0);
		}
		
	}
	
	private void markAllSequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the marking for each member after their name");
		Console.println("Input 'p' to mark present and 'a' to mark absent");
		Console.printLines(1);
		
		LinkedList<String> names = controller.getNames();
		HashMap<String, String> attendance = new HashMap<String, String>();
		
		if(controller.rosterIsEmpty())
			Console.println("There are no members on the roster\n"
					+ "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", controller.MARKS);
			attendance.put(name, marking);
		}
		
		controller.markAttendance(attendance, new GregorianCalendar());
		
		mainSequence();
		
	}

	private void markOneSequence() {
		
		Console.printTitle("Mark One");
		Console.println("Input the name of the member\nthat you would like to mark");
		
		String name = Console.promptString("Name >> ");
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		
		String marking = Console.promptMarking("Marking >> ", controller.MARKS);
		
		boolean success = controller.markAttendance(name, marking, new GregorianCalendar());
		if(!success) Console.println("Member does not exist\n");
		
		mainSequence();
	}
	
	private void markAllPreviousDaySequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the date");
		
		int[] date = Console.promptDate();
		
		LinkedList<String> names = controller.getNames();
		HashMap<String, String> attendance = new HashMap<String, String>();
		
		Console.printTitle("Mark All");
		Console.println("Input the marking for each member after their name");
		Console.println("Input 'p' to mark present");
		Console.printLines(1);
		
		if(controller.rosterIsEmpty())
			Console.println("There are no members on the roster\n"
					+ "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", controller.MARKS);
			attendance.put(name, marking);
		}
		
		controller.markAttendance(attendance, new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		
		mainSequence();
		
	}
	
	private void markOnePreviousDaySequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the date");
		
		int[] date = Console.promptDate();
		
		Console.println("Input the name of the member\nthat you would like to mark");
		String name = Console.promptString("Name >> ");
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		String marking = Console.promptMarking("Marking >> ", controller.MARKS);
		
		boolean success = controller.markAttendance(name, marking, new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		if(!success) Console.println("Member does not exist\n");
		
		mainSequence();
		
	}

	private void printRosterSequence() {
		Console.printTitle("Roster");
		
		if(controller.rosterIsEmpty())
			Console.println("There are no members on the roster\n"
					+ "Choose 'Manage Roster' to add members");
		else
			Console.println(controller.rosterTable());
		
		mainSequence();
	}
	
	private void saveRosterSequence() {
		
		// Alert where the roster is being saved to
		Console.printTitle("Save Roster");
		Console.print("Saving roster to: ");
		Console.println(controller.rosterLocation());
		
		// Try to save the roster
		try {
			controller.saveRoster();
			Console.println("Saved!");
		} catch(IOException ioe) {
			Console.print("Error saving roster: ");
			Console.println(ioe.getMessage());
		}
		
		// Return to the main sequence
		mainSequence();
		
	}

}
