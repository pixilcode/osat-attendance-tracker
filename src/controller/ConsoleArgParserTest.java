package controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ConsoleArgParserTest {
	
	Controller controller;
	boolean correctArgs;
	
	// Reused arguments
	private static final String NO_GUI = "--no-gui";
	private static final String LOC = "test.xml"; // Has preloaded information
	private static final String FAULTY = "faulty arg";
	private static final String NEW = "--new";
	private static final String NEW_LOC = "newTest.xml";

	@Before
	public void setUp() throws Exception {
		controller = new Controller();
	}
	
	@Test
	public void testEmptyParse() throws Exception {
		correctArgs = controller.parseArgs(new String[] {});
		assertTrue(controller.isUsingGUI());
		assertTrue(correctArgs);
	}
	
	@Test
	public void testNoGUI() throws Exception {
		correctArgs = controller.parseArgs(new String[] {NO_GUI});
		assertFalse(controller.isUsingGUI());
		assertTrue(correctArgs);
	}
	
	@Test
	public void testLocation() throws Exception {
		correctArgs = controller.parseArgs(new String[] {LOC});
		assertTrue(controller.isUsingGUI());
		assertTrue(controller.rosterIsLoaded());
		assertEquals(controller.rosterLocation(), LOC);
		assertFalse(controller.rosterIsEmpty());
		assertTrue(correctArgs);
	}
	
	@Test
	public void testLocAndNoGUI() throws Exception {
		assertFalse(controller.rosterIsLoaded());
		correctArgs = controller.parseArgs(new String[] {NO_GUI, LOC});
		assertFalse(controller.isUsingGUI());
		assertEquals(controller.rosterLocation(), LOC);
		assertFalse(controller.rosterIsEmpty());
		assertTrue(correctArgs);
	}
	
	@Test
	public void testFaultyArg() throws Exception {
		correctArgs = controller.parseArgs(new String[] {FAULTY});
		assertTrue(controller.isUsingGUI());
		assertFalse(correctArgs);
	}
	
	@Test
	public void testNewWithLocation() throws Exception {
		correctArgs = controller.parseArgs(new String[] {NEW, NEW_LOC});
		assertTrue(controller.isUsingGUI());
		assertTrue(correctArgs);
		assertTrue(controller.rosterIsEmpty());
		(new File(NEW_LOC)).delete();
	}
	
	@Test
	public void testNewNoLocation() throws Exception {
		correctArgs = controller.parseArgs(new String[] {NEW});
		assertTrue(controller.isUsingGUI());
		assertFalse(correctArgs);
	}

}
