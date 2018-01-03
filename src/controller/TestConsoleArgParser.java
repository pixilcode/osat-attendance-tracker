package controller;

import java.io.File;
import java.io.IOException;

public final class TestConsoleArgParser {
	
	private final static String[] ARGS = {
			"--no-gui",
			"test.xml", // Note: test.xml has people preloaded
			"invalid arg",
			"--new",
			"newFile.xml"
			};
	
	private final static int NO_GUI = 0, LOC = 1, FAULTY = 2, NEW = 3, NEW_LOC = 4;
	
	private static Controller controller;
	
	public static void run() {
		
		controller = new Controller();
		
		printTestName("Parse empty args");
		boolean correctArgs = testArgs(new String[] {});
		System.out.println(controller.isUsingGUI() == true);
		System.out.println(correctArgs == true);
		
		controller = new Controller();
		
		printTestName("Don't Use GUI");
		correctArgs = testArgs(new String[] {ARGS[NO_GUI]});
		System.out.println(controller.isUsingGUI() == false);
		System.out.println(correctArgs = true);
		
		controller = new Controller();
		
		printTestName("Use location: " + ARGS[0]);
		correctArgs = testArgs(new String[] {ARGS[LOC]});
		System.out.println(controller.isUsingGUI() == true);
		System.out.println(controller.rosterLocation().equals(ARGS[LOC]));
		System.out.println(controller.rosterIsEmpty() == false);
		System.out.println(correctArgs = true);
		
		controller = new Controller();
		
		printTestName("Don't Use GUI and use location: " + ARGS[LOC]);
		correctArgs = testArgs(new String[] {ARGS[NO_GUI], ARGS[LOC]});
		System.out.println(controller.isUsingGUI() == false);
		System.out.println(controller.rosterLocation().equals(ARGS[LOC]));
		System.out.println(controller.rosterIsEmpty() == false);
		System.out.println(correctArgs = true);
		
		controller = new Controller();
		
		printTestName("Use Faulty Arg");
		correctArgs = testArgs(new String[] {ARGS[FAULTY]});
		System.out.println(controller.isUsingGUI() == true);
		System.out.println(correctArgs == false);
		
		controller = new Controller();
		
		printTestName("New Roster, No Location"); // Must include location when using --new
		correctArgs = testArgs(new String[] {ARGS[NEW]});
		System.out.println(controller.isUsingGUI() == true);
		System.out.println(correctArgs == false);
		
		controller = new Controller();
		
		printTestName("New Roster With Location");
		correctArgs = testArgs(new String[] {ARGS[NEW], ARGS[NEW_LOC]});
		System.out.println(controller.isUsingGUI() == true);
		System.out.println(correctArgs == true);
		System.out.println(controller.rosterIsEmpty());
		(new File(ARGS[NEW_LOC])).delete();
		
	}
	
	private static boolean testArgs(String[] args) {
		try {
			return controller.parseArgs(args);
		} catch(IOException ioe) {
			return false;
		}
	}
	
	private static void printTestName(String name) {
		System.out.println();
		System.out.println(name);
		System.out.println("==============================");
	}

}
