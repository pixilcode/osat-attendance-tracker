package controller;

public final class TestConsoleArgParser {
	
	final static private String[] ARGS = {
			"--no-gui",
			"/home/dragonfire/test.xml"
			};
	
	public static void run() {
		
		printTestName("Parse empty args");
		Controller.parseArgs(new String[] {});
		System.out.println(Controller.isUsingGUI() == true);
		
		printTestName("Parse " + ARGS[0]);
		Controller.parseArgs(new String[] {ARGS[0]});
		System.out.println(Controller.isUsingGUI() == false);
		
		printTestName("Parse location");
		Controller.parseArgs(new String[] {ARGS[1]});
		System.out.println(Controller.isUsingGUI() == true);
		System.out.println(Controller.rosterLocation().equals(ARGS[1]));
		
		printTestName("Parse " + ARGS[0] + " and location");
		Controller.parseArgs(new String[] {ARGS[0], ARGS[1]});
		System.out.println(Controller.isUsingGUI() == false);
		System.out.println(Controller.rosterLocation().equals(ARGS[1]));
		
	}
	
	private static void printTestName(String name) {
		System.out.println();
		System.out.println(name);
		System.out.println("====================");
	}

}
