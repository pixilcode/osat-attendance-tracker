package controller;

import java.io.IOException;

public class EntryPoint {

	/**
	 * @param args the arguments given by the console
	 */
	public static void main(String[] args) {
		
		Controller c = new Controller();
		
		try {
			c.parseArgs(args);
		} catch(IOException ioe) {
			System.err.println("Error: " + ioe.getMessage());
		}
		
		c.run();

	}

}
