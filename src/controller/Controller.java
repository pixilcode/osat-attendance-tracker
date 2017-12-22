package controller;

import java.nio.file.Path;

import model.*;
import view.*;

public class Controller {
	
	private static int nextPersonID = 0;
	private static int nextTaskID = 0;
	
	public static void run() {
		
	}
	
	public static void test() {
		new WelcomeFrame();
	}

	public static void resetPersonID() {
		nextPersonID = 0;
	}
	
	public static void resetTaskID() {
		nextTaskID = 0;
	}
	
	public static int getPersonID() {
		return nextPersonID++;
	}
	
	public static int getTaskID() {
		return nextTaskID++;
	}

	public static Path getLocation() {
		// TODO Get location through GUI
		return null;
	}

}
