package controller;

import java.io.IOException;

import model.*;
import view.*;

public class Controller {
	
	private static int nextPersonID = 0;
	private static int nextTaskID = 0;
	
	public static void run() {
		
	}
	
	public static void test() {
		
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

}
