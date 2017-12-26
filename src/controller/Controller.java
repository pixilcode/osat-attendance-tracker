package controller;

import java.io.IOException;
import java.nio.file.Path;
import model.*;
import view.consoleUI.*;
import view.graphicUI.*;

public class Controller {
	
	private static Roster roster;
	private static boolean loaded;
	
	public static void run() {
		
	}
	
	public static void test() {
		(new CUIManager()).run();
	}

	public static Path getLocation() {
		// TODO Get location through GUI
		return null;
	}

	public static void loadRoster(String loc) throws IOException {
		roster = new Roster(loc);
		loaded = true;
	}
	
	public static boolean rosterIsLoaded() {
		return loaded;
	}

}
