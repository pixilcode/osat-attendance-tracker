package view.consoleUI;

import java.io.PrintStream;
import java.util.Scanner;

final class ConsoleUtility {
	
	private static final Scanner in = new Scanner(System.in);
	private static final PrintStream out = System.out;
	private static StringBuilder string;
	
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
	
}
