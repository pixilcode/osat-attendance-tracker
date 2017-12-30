package model;

import java.io.IOException;

public class TestRosterToTable {
	
	public static void run() {
		
		try {
			
		Roster roster = new Roster("test.xml", Roster.Init.NEW);
		roster.addPerson("John Smith");
		roster.addPerson("Jake Smith");
		roster.addPerson("James");
		
		System.out.println(roster.toTable());
		
		} catch(IOException ioe) {
			System.out.println("Invalid roster");
		}
		
	}
	
}
