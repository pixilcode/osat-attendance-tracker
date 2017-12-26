package view.consoleUI;

import java.io.*;

import controller.Controller;
import view.UserInterface;

public class CUIManager implements UserInterface {
	
	public CUIManager() {
		
	}
	
	public UserInterface run() {
		boolean loaded = false;
		while(!loaded) {
			try {
				String loc = welcomeSequence();
				Controller.loadRoster(loc);
				loaded = true;
			} catch(IOException ioe) {
				ConsoleUtility.println("Invalid path or file name");
				ConsoleUtility.printLines(1);
			}
		}
		return this;
	}
	
	private String welcomeSequence() {
		
		ConsoleUtility.printTitle("Welcome");
		ConsoleUtility.println("Load a roster or make a new one");
		ConsoleUtility.printLines(1);
		ConsoleUtility.println("1. Load Roster");
		ConsoleUtility.println("2. New Roster");
		ConsoleUtility.println("3. Exit");
		
		int option = 0;
		do {
			option = ConsoleUtility.promptInt("Option [#] >> ");
			ConsoleUtility.printLines(1);
			
			switch(option) {
			case 1:
				return loadRosterSequence();
			case 2:
				return newRosterSequence();
			case 3:
				System.exit(0);
			default:
				ConsoleUtility.println("Please input 1, 2, or 3");
				ConsoleUtility.printLines(1);
			}
		} while(option < 1 || option > 3);
		
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

}
