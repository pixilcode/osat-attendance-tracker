package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsoleArgParserTest {
	
	private Controller controller;
	
	// Reused arguments
	private static final String NO_GUI = "--no-gui";
	private static final String FAULTY = "faulty arg";
	private static final String NEW = "--new";
	private static final String FILE_LOC = System.getProperty("user.dir") + File.separator + "test.xml";
	private static final String NEW_FILE_LOC = System.getProperty("user.dir") + File.separator + "newFile.xml";

	@Before
	public void setUp() throws Exception {
		controller = new Controller();
	}
	
	@After
	public void tearDown() throws Exception {
		File newFile = new File(NEW_FILE_LOC);
		if(newFile.exists())
			newFile.delete();
	}
	
	@Test
	public void testEmptyParse() throws Exception {
		controller.parseArgs(new String[] {});
		assertTrue(controller.isUsingGUI());
	}
	
	@Test
	public void testNoGUI() throws Exception {
		controller.parseArgs(new String[] {NO_GUI});
		assertFalse(controller.isUsingGUI());
	}
	
	@Test
	public void testLocation() throws Exception {
		controller.parseArgs(new String[] {FILE_LOC});
		assertTrue(controller.isUsingGUI());
		assertTrue(controller.rosterIsLoaded());
		assertEquals(controller.rosterLocation(), FILE_LOC);
		assertFalse(controller.rosterIsEmpty());
	}
	
	@Test
	public void testLocAndNoGUI() throws Exception {
		assertFalse(controller.rosterIsLoaded());
		controller.parseArgs(new String[] {NO_GUI, FILE_LOC});
		assertFalse(controller.isUsingGUI());
		assertEquals(controller.rosterLocation(), FILE_LOC);
		assertFalse(controller.rosterIsEmpty());
	}
	
	@Test
	public void testFaultyArg() throws Exception {
		try {
			controller.parseArgs(new String[] {FAULTY});
			fail();
		} catch(IOException ioe) {
			assertTrue(controller.isUsingGUI());
		}
	}
	
	@Test
	public void testNewWithLocation() throws Exception {
		controller.parseArgs(new String[] {NEW, NEW_FILE_LOC});
		assertTrue(controller.isUsingGUI());
		assertTrue(controller.rosterIsEmpty());
	}
	
	@Test
	public void testNewNoLocation() throws Exception {
		try {
			controller.parseArgs(new String[] {NEW});
		} catch(IOException ioe) {
			assertTrue(controller.isUsingGUI());
			assertFalse(controller.rosterIsLoaded());
		}
	}
	
	@Test
	public void testNewWithExistingFile() throws Exception {
		try {
			controller.parseArgs(new String[] {NEW, FILE_LOC});
			fail();
		} catch(IOException ioe) {
			assertFalse(controller.rosterIsLoaded());
		}
	}

}
