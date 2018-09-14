package view.consoleUI;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import controller.Controller;
import view.UserInterface;

public enum Sequence implements UserInterface {
	
	WELCOME("Welcome") {
		
		public Sequence run() {
			return welcome();
		}
		
	},
	
	LOAD_ROSTER("Load Roster") {
		
		public Sequence run() {
			return loadRoster();
		}
		
	},
	
	NEW_ROSTER("New Roster") {
		
		public Sequence run() {
			return newRoster();
		}
		
	},
	
	MAIN_MENU("Main Menu") {
		
		public Sequence run() {
			return mainMenu();
		}
		
	},
	
	MANAGE_ROSTER("Manage Roster") {
		
		public Sequence run() {
			return manageRoster();
		}
		
	},
	
	ADD_MEMBER("Add A Member") {
		
		public Sequence run() {
			return addMember();
		}
		
	},
	
	REMOVE_MEMBER("Remove A Member") {
		
		public Sequence run() {
			return removeMember();
		}
		
	},
	
	MARK_ROSTER("Mark Roster") {
		
		public Sequence run() {
			return markRoster();
		}
		
	},
	
	MARK_ALL("Mark All Members") {
		
		public Sequence run() {
			return markAll();
		}
		
	},
	
	MARK_ONE("Mark One Member") {
		
		public Sequence run() {
			return markOne();
		}
		
	},
	
	MARK_ALL_PREVIOUS("Mark All Members (Previous Day)") {
		
		public Sequence run() {
			return markAllPrevious();
		}
		
	},
	
	MARK_ONE_PREVIOUS("Mark One Member (Previous Day)") {
		
		public Sequence run() {
			return markOnePrevious();
		}
		
	},
	
	PRINT_ROSTER("Print Roster Summary") {
		
		public Sequence run() {
			return printRoster();
		}
		
	},
	
	SAVE_ROSTER("Save Roster") {
		
		public Sequence run() {
			return saveRoster();
		}
		
	},
	EXIT("Exit") {
		
		public Sequence run() {
			return exit();
		}
		
	},
	END("End") {
		
		public Sequence run() {
			return END;
		}
		
	};
	
	private final String id;
	private static Controller controller = new Controller();
	
	private Sequence(String id) {
		this.id = id;
	}
	
	public Sequence run() {
		return MAIN_MENU;
	}
	
	// Sequence Methods
	
	private static Sequence welcome() {
		
		Sequence[] options = { LOAD_ROSTER, NEW_ROSTER, EXIT };
		
		Console.printTitle("Welcome");
		Console.println("Load a roster or make a new one");
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private static Sequence loadRoster() {
		
		try {
			
			Console.printTitle("Load Roster");
			Console.printLines(1);
			Console.printLines(1);
			
			String loc = Console.promptString("Roster Path >> ");
			controller.loadRoster(loc);
			
			return MAIN_MENU;
			
		} catch(IOException ioe) {
			
			Console.println(ioe.getMessage());
			return LOAD_ROSTER;
			
		}
		
	}
	
	private static Sequence newRoster() {
		
		try {
			
			Console.printTitle("New Roster");
			Console.printLines(1);
			Console.printLines(1);
			
			String dir = Console.promptString("Roster Directory >> ");
			String name = Console.promptString("File Name >> ");
			
			if(!dir.endsWith(File.separator))
				dir += File.separator;
			if(!name.endsWith(".xml"))
				name += ".xml";
			String loc = dir + name;
			
			controller.newRoster(loc);
			
			return MAIN_MENU;
			
		} catch(IOException ioe) {
			
			Console.println(ioe.getMessage());
			return NEW_ROSTER;
			
		}
		
	}
	
	private static Sequence mainMenu() {
		
		Sequence[] options = { MANAGE_ROSTER, MARK_ROSTER, PRINT_ROSTER, SAVE_ROSTER, EXIT };
		
		Console.printTitle("ON A ROLL");
		Console.println("What would you like to do?");
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private static Sequence manageRoster() {
		
		Sequence[] options = { ADD_MEMBER, REMOVE_MEMBER,
				// "Add member info",
				// "Edit member info",
				// "Remove member info",
				// "Add task to member",
				// "Other task related stuff",
				MAIN_MENU, EXIT };
		
		Console.printTitle("Manage Roster");
		Console.println("What would you like to do?");
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private static Sequence addMember() {
		
		Console.printTitle("Add Member");
		Console.println("Input the name of the member\nthat you would like to add");
		
		String name = Console.promptString("Name >> ");
		
		controller.addMember(name);
		
		return MANAGE_ROSTER;
		
	}
	
	private static Sequence removeMember() {
		
		Console.printTitle("Remove Member");
		Console.println("Input the name of the member\nthat you would like to remove");
		
		String name = Console.promptString("Name >> ");
		
		if(controller.rosterContainsMember(name))
			controller.removeMember(name);
		else
			Console.println("\nMember does not exist");
		
		return MANAGE_ROSTER;
		
	}
	
	private static Sequence markRoster() {
		
		Console.printTitle("Mark Roster");
		Console.println("What would you like to do?");
		
		Sequence[] options = { MARK_ALL, MARK_ONE, MARK_ALL_PREVIOUS, MARK_ONE_PREVIOUS, MAIN_MENU, EXIT };
		
		Console.printOptions(options);
		
		return option(options);
		
	}
	
	private static Sequence markAll() {
		
		Console.printTitle("Mark All");
		Console.println("Input the marking for each member after their name");
		Console.println("Input 'p' to mark present and 'a' to mark absent");
		Console.printLines(1);
		
		LinkedList<String> names = controller.getNames();
		HashMap<String, String> attendance = new HashMap<String, String>();
		
		if(controller.rosterIsEmpty())
			Console.println("There are no members on the roster\n" + "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", Controller.MARKS);
			attendance.put(name, marking);
		}
		
		controller.markAttendance(attendance, new GregorianCalendar());
		
		return Sequence.MARK_ROSTER;
		
	}
	
	private static Sequence markOne() {
		
		Console.printTitle("Mark One");
		Console.println("Input the name of the member\nthat you would like to mark");
		
		String name = Console.promptString("Name >> ");
		
		// TODO Add test here to see if the member exists ( as well as in
		// markOnePrevious() )
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		
		String marking = Console.promptMarking("Marking >> ", Controller.MARKS);
		
		boolean success = controller.markAttendance(name, marking, new GregorianCalendar());
		if(!success)
			Console.println("Member does not exist\n");
		
		return MARK_ROSTER;
		
	}
	
	private static Sequence markAllPrevious() {
		
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
			Console.println("There are no members on the roster\n" + "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", Controller.MARKS);
			attendance.put(name, marking);
		}
		
		// Month counting starts at 0
		controller.markAttendance(attendance,
				new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		
		return MARK_ROSTER;
		
	}
	
	private static Sequence markOnePrevious() {
		
		Console.printTitle("Mark One");
		Console.println("Input the date");
		
		int[] date = Console.promptDate();
		
		Console.println("Input the name of the member\nthat you would like to mark");
		String name = Console.promptString("Name >> ");
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		String marking = Console.promptMarking("Marking >> ", Controller.MARKS);
		
		// Month counting starts at 0
		boolean success = controller.markAttendance(name, marking,
				new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		if(!success)
			Console.println("Member does not exist\n");
		
		return Sequence.MARK_ROSTER;
		
	}
	
	private static Sequence printRoster() {
		
		Console.printTitle("Roster");
		
		if(controller.rosterIsEmpty())
			Console.println("There are no members on the roster\n" + "Choose 'Manage Roster' to add members");
		else
			Console.println(controller.rosterTable());
		
		return MAIN_MENU;
		
	}
	
	private static Sequence saveRoster() {
		
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
		
		return MAIN_MENU;
		
	}
	
	private static Sequence exit() {
		
		Console.printTitle("EXIT?");
		
		String save = "";
		
		while(!save.matches("y|n|c"))
			save = Console.promptString(
						"Would you like to save your roster?\n"
						+ "(Y(es)/N(o)/C(ancel)) >> "
						).substring(0, 1).toLowerCase();
		
		if(save.equals("y")) {
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
		} else if(save.equals("c"))
			return MAIN_MENU;
		
		return END;
		
	}
	
	// Helper methods
	
	private static Sequence option(Sequence[] options) {
		
		int option = Console.promptOption(options.length);
		
		if(option >= 1 && option <= options.length)
			return options[option - 1];
		else
			return MAIN_MENU;
		
	}
	
	// Outside methods
	
	public static Sequence fromString(String id) {
		
		for(Sequence seq : values())
			if(id.equals(seq.id))
				return seq;
			
		return MAIN_MENU;
		
	}
	
	public static Controller getController() {
		return controller;
	}
	
	public static void setController(Controller c) {
		controller = c;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
