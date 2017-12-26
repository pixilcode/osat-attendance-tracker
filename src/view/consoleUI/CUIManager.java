package view.consoleUI;

import java.io.*;

import controller.Controller;
import view.UserInterface;

public class CUIManager implements UserInterface {
	
	public CUIManager() {
		
	}
	
	public UserInterface run() {
		while(!Controller.rosterIsLoaded()) {
			try {
				String loc = welcomeSequence();
				Controller.loadRoster(loc);
			} catch(IOException ioe) {
				ConsoleUtility.println("Invalid path or file name");
				ConsoleUtility.printLines(1);
			}
		}
		
		mainSequence();
		
		return this;
	}
	
	private String welcomeSequence() {
		
		ConsoleUtility.printTitle("Welcome");
		ConsoleUtility.println("Load a roster or make a new one");
		ConsoleUtility.printLines(1);
		ConsoleUtility.println("1. Load Roster");
		ConsoleUtility.println("2. New Roster");
		ConsoleUtility.println("3. Exit");
		
		int option = ConsoleUtility.promptOption(3);
		switch(option) {
		case 1:
			return loadRosterSequence();
		case 2:
			return newRosterSequence();
		case 3:
			System.exit(0);
		}
		
		return "";
		
	}

	private String loadRosterSequence() {
		
		ConsoleUtility.printLines(1);
		ConsoleUtility.printTitle("Load Roster");
		
		return ConsoleUtility.promptString("Roster Path >> ");
		
	}
	
	private String newRosterSequence() {
		
		ConsoleUtility.printLines(1);
		ConsoleUtility.printTitle("New Roster");
		
		String dir = ConsoleUtility.promptString("Roster Directory >> ");
		String name = ConsoleUtility.promptString("File Name >> ");
		
		if(!dir.endsWith(File.separator)) dir += File.separator;
		return dir + name;
		
	}
	
	private void mainSequence() {
		
	}

}
