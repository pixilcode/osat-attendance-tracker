package view.consoleUI;

import java.io.File;
import java.io.IOException;

import controller.Controller;

public enum Sequence {
	
	WELCOME("Welcome"),
	LOAD_ROSTER("Load Roster"),
	NEW_ROSTER("New Roster"),
	MAIN_MENU("Main Menu"),
	MANAGE_ROSTER("Manage Roster"),
	MARK_ROSTER("Mark Roster"),
	PRINT_ROSTER("Print Roster"),
	EXIT("Exit");
	
	private String id;
	private static Controller controller = new Controller();
	
	private Sequence(String id){
		this.id = id;
	}

	public Sequence run() throws IOException {
		
		switch(this) {
		case WELCOME:
			return welcome();
		case LOAD_ROSTER:
			return loadRoster();
		case NEW_ROSTER:
			return newRoster();
		case MAIN_MENU:
			return mainMenu();
		case EXIT:
			return exit();
		}
		
		return MAIN_MENU;
		
	}
	
	// Sequence Methods
	
	private Sequence welcome() {
		
		Sequence[] options = {
				LOAD_ROSTER,
				NEW_ROSTER,
				EXIT
		};
		
		Console.printTitle("Welcome");
		Console.println("Load a roster or make a new one");
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private Sequence loadRoster() throws IOException {
		
		Console.printTitle("Load Roster");
		Console.printLines(1);
		Console.println("Warning: Not entering the full path may cause problems");
		Console.printLines(1);
		
		String loc = Console.promptString("Roster Path >> ");
		controller.loadRoster(loc);
		
		return MAIN_MENU;
		
	}
	
	private Sequence newRoster() throws IOException {
		
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
		
		return MAIN_MENU;
		
	}
	
	private Sequence mainMenu() {
		
		Sequence[] options = {
				MANAGE_ROSTER,
				MARK_ROSTER,
				PRINT_ROSTER,
//				SAVE_ROSTER,
				EXIT
		};
		
		Console.printTitle("ON A ROLL");
		Console.println("What would you like to do?");
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private Sequence exit() throws IOException {
		
		System.exit(0);
		return MAIN_MENU;
		
	}
	
	// Helper methods
	
	private Sequence option(Sequence[] options) {
		
		int option = Console.promptOption(options.length);
		
		if(option >= 1 && option <= options.length) return options[option-1];
		else return MAIN_MENU;
		
	}
	
	// Outside methods
	
	public static Sequence fromString(String id) {
		
		for(Sequence seq : values())
			if(id.equals(seq.id))
				return seq;
		
		return MAIN_MENU;
		
	}
	
	public String toString() {
		
		return id;
		
	}

}
