package view.consoleUI;

import java.io.PrintStream;
import java.util.Scanner;

final class Console {
	
	private static final Scanner in = new Scanner(System.in);
	private static final PrintStream out = System.out;
	private static StringBuilder string;
	
	public static final int YEAR = 0;
	public static final int MONTH = 1;
	public static final int DAY = 2;
	
	static void printLines(int number) {
		
		string = new StringBuilder(number);
		for(int i = 0; i < number; i++)
			string.append('\n');
		out.print(string);
		
	}
	
	static void printTitle(String title) {
		
		// Add 1 for the '\n' character at the end of each line
		string = new StringBuilder((title.length() + 1)*3);
		for(int i = 0; i < title.length(); i++)
			string.append('=');
		string.append('\n');
		string.append(title);
		string.append('\n');
		string.append(string.substring(0, title.length()));
		string.append('\n');
		
		out.print('\n');
		out.print(string);
		
	}
	
	static void print(Object obj) {
		out.print(obj);
	}
	
	static void println(Object obj) {
		out.println(obj);
	}
	
	static String readLine() {
		return in.nextLine();
	}
	
	static String promptString(String prompt) {
		out.print(prompt);
		return readLine();
	}
	
	static int promptInt(String prompt) {
		while(true) {
			try {
				return Integer.parseInt(promptString(prompt));
			} catch(NumberFormatException nfe) {
				out.println("Please input a valid integer");
				out.println();
			}
		}
	}
	
	static int promptOption(int numOfOptions) {
		
		int option = promptInt("Option [#] >> ");
		while(option < 1 || option > numOfOptions) {
			option = Console.promptInt("Option [#] >> ");
			Console.printLines(1);
			out.println("Please input a value between 1 and " + numOfOptions);
			out.println();
		}
		return option;
		
	}
	
	public static int[] promptDate() {
		
		int[] date = new int[3];
		date[YEAR] = promptInt("Year [YYYY] >> ");
		date[MONTH] = promptInt("Month [MM] >> ");
		date[DAY] = promptInt("Day [DD] >> ");
		
		return date;
		
	}
	
	public static String promptMarking(String prompt, String[] availableMarkings) {
		
		while(true) {
			String marking = promptString(prompt);
			if(markingIsIn(availableMarkings, marking)) return marking;
		}
		
	}

	private static boolean markingIsIn(String[] availableMarkings, String givenMarking) {
		
		for(String marking : availableMarkings)
			if(marking.equals(givenMarking)) return true;
		return false;
		
	}
	
}
