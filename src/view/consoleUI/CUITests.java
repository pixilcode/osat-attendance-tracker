package view.consoleUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class CUITests {
	
	private ListenerStream out;
	private Controller c;
	
	@Before
	public void setUp() throws Exception {
		
		out = new ListenerStream(System.out);
		
		Sequence.setController(new Controller());
		c = Sequence.getController();
		
		System.setOut(out);
		Console.updateOut();
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		// TODO make temp directory that is cleared every time
		
		String[] tempFiles = { "outFile.txt", "in.txt", "test/abc.xml", "test/empty.xml" };
		
		for(String loc : tempFiles) {
			File file = new File(loc);
			if(file.exists())
				file.delete();
		}
		
		(new File("outFile.txt")).delete();
		
		File inFile = new File("in.txt");
		if(inFile.exists())
			inFile.delete();
		
		System.setOut(out.getMainStream());
		Console.updateOut();
		
	}
	
	// Console testing
	
	@Test
	public void testSetUp() throws Exception {
		
		System.out.println("Hello world!");
		
		String output = readOutput();
		
		assertEquals("Hello world!\n", output);
		
	}
	
	@Test
	public void testBasicPrinting() throws Exception {
		
		String expected = "";
		
		Console.print("Hello world!\n");
		expected += "Hello world!\n";
		
		Console.printLines(1);
		expected += "\n";
		
		Console.println("Hello!");
		expected += "Hello!\n";
		
		Console.printTitle("HI!");
		expected += toTitle("HI!");
		
		String output = readOutput();
		
		assertEquals(expected, output);
		
	}
	
	@Test
	public void testOptions() throws Exception {
		
		Console.printOptions(new Sequence[] { Sequence.MAIN_MENU, Sequence.WELCOME, Sequence.EXIT });
		
		String expected = "";
		expected += "1. Main Menu" + "\n" + "2. Welcome" + "\n" + "3. Exit" + "\n";
		
		String output = readOutput();
		
		assertEquals(expected, output);
		
	}
	
	// Testing Sequence output
	
	@Test
	public void testWelcome() throws Exception {
		
		Sequence welcome = Sequence.WELCOME;
		assertEquals(Sequence.WELCOME, welcome);
		
		Sequence[] expected = { Sequence.LOAD_ROSTER, Sequence.NEW_ROSTER };
		
		optionsReturnSequence(welcome, expected);
		
	}
	
	@Test
	public void testLoadRoster() throws Exception {
		
		setupInput("test.xml\n");
		
		Controller c = Sequence.getController();
		
		Sequence load = Sequence.LOAD_ROSTER;
		Sequence result = load.run();
		
		String expected = toTitle(load.toString());
		expected += "\nWarning: Not entering the full path may cause problems";
		expected += "\n\nRoster Path >> ";
		
		assertEquals(expected, readOutput());
		
		assertEquals(Sequence.MAIN_MENU, result);
		assertTrue(c.rosterIsLoaded());
		
	}
	
	@Test
	public void testNewRoster() throws Exception {
		
		setupInput("test" + "\n" + "abc.xml");
		
		Sequence newR = Sequence.NEW_ROSTER;
		Sequence result = newR.run();
		
		assertEquals(Sequence.MAIN_MENU, result);
		assertTrue(c.rosterIsLoaded());
		
	}
	
	@Test
	public void testMain() throws Exception {
		
		Sequence seq = Sequence.MAIN_MENU;
		
		Sequence[] expected =
				{ Sequence.MANAGE_ROSTER, Sequence.MARK_ROSTER, Sequence.PRINT_ROSTER, Sequence.SAVE_ROSTER };
		
		optionsReturnSequence(seq, expected);
		
	}
	
	@Test
	public void testManageRoster() throws Exception {
		
		// Load the roster
		c.loadRoster("test.xml");
		
		Sequence seq = Sequence.MANAGE_ROSTER;
		
		Sequence[] expected = { Sequence.ADD_MEMBER, Sequence.REMOVE_MEMBER, Sequence.MAIN_MENU };
		
		optionsReturnSequence(seq, expected);
		
	}
	
	@Test
	public void testAddAMemberAndRemoveAMember() throws Exception {
		
		// Load the roster and set up input
		c.loadRoster("test.xml");
		
		// Test Add Member
		setupInput("John");
		
		Sequence seq = Sequence.ADD_MEMBER;
		Sequence result = seq.run();
		
		assertEquals(Sequence.MANAGE_ROSTER, result);
		assertTrue(c.rosterContainsMember("John"));
		
		// Test Remove Member
		setupInput("John");
		
		seq = Sequence.REMOVE_MEMBER;
		result = seq.run();
		
		assertEquals(Sequence.MANAGE_ROSTER, result);
		assertFalse(c.rosterContainsMember("John"));
		
	}
	
	@Test
	public void testMarkRoster() throws Exception {
		
		c.loadRoster("test.xml");
		
		Sequence seq = Sequence.MARK_ROSTER;
		
		Sequence[] expected = { Sequence.MARK_ALL, Sequence.MARK_ONE, Sequence.MARK_ALL_PREVIOUS,
				Sequence.MARK_ONE_PREVIOUS, Sequence.MAIN_MENU, Sequence.EXIT };
		
		optionsReturnSequence(seq, expected);
		
	}
	
	@Test
	public void testMarkOptions() throws Exception {
		
		// THIS TEST NEEDS FIXING WHEN ACCESS TO MEMBERS IS
		// ALPHABETIZED AND ACCESS TO MEMBER ATTENDANCE IS
		// FIXED
		
		c.loadRoster("test.xml");
		
		String input = "";
		
		// Mark STOCK PERSON as present and STOCK PERSON 2 as absent
		input += "a\n" + "p\n";
		
		// Mark STOCK PERSON 2 as present
		input += "STOCK PERSON 2\n" + "p\n";
		
		// Mark STOCK PERSON as present and STOCK PERSON 2 as absent
		// on January 1, 2000
		input += "2000\n" + "1\n" + "1\n" + "a\n" + "p\n";
		
		// Mark STOCK PERSON 2 as present
		// on January 1, 2000
		input += "2000\n" + "1\n" + "1\n" + "STOCK PERSON 2\n" + "p\n";
		
		setupInput(input);
		
		// Test Mark All
		Sequence seq = Sequence.MARK_ALL;
		Sequence result = seq.run();
		
		// TODO fix this to ensure that the date added is today
		// TODO fix name system to ensure that names are presented
		// alphabetically (as well as when saving to XML file, etc.)
		// This will fix having to check for STOCK PERSON 2 before STOCK PERSON
		
		// Consider limiting access to member attendance (don't return the
		// HashMap for attendance)
		
		assertEquals(Sequence.MARK_ROSTER, result);
		assertEquals(1, c.getMemberAttendance("STOCK PERSON").size());
		assertEquals(0, c.getMemberAttendance("STOCK PERSON 2").size());
		
		// Test Mark One
		seq = Sequence.MARK_ONE;
		result = seq.run();
		
		assertEquals(Sequence.MARK_ROSTER, result);
		assertEquals(1, c.getMemberAttendance("STOCK PERSON").size());
		assertEquals(1, c.getMemberAttendance("STOCK PERSON 2").size());
		
		// Test Mark All Previous
		seq = Sequence.MARK_ALL_PREVIOUS;
		result = seq.run();
		
		assertEquals(Sequence.MARK_ROSTER, result);
		assertEquals(2, c.getMemberAttendance("STOCK PERSON").size());
		assertEquals(1, c.getMemberAttendance("STOCK PERSON 2").size());
		
		// Test Mark One Previous
		seq = Sequence.MARK_ONE_PREVIOUS;
		result = seq.run();
		
		assertEquals(Sequence.MARK_ROSTER, result);
		assertEquals(2, c.getMemberAttendance("STOCK PERSON").size());
		assertEquals(2, c.getMemberAttendance("STOCK PERSON 2").size());
		
	}
	
	@Test
	public void testPrintRoster() throws Exception {
		
		c.newRoster("test/empty.xml");
		
		Sequence seq = Sequence.PRINT_ROSTER;
		Sequence result = seq.run();
		
		String expected = toTitle("Roster");
		expected += "There are no members on the roster\n" + "Choose 'Manage Roster' to add members\n";
		
		assertEquals(Sequence.MAIN_MENU, result);
		assertEquals(expected, readOutput());
		
		c.addMember("John");
		
		seq.run();
		
		expected += toTitle("Roster");
		expected += "===================" + "\n" + "|Name|Days Present|" + "\n" + "===================" + "\n"
				+ "|John|0           |" + "\n" + "-------------------" + "\n";
		
		assertEquals(expected, readOutput());
		
	}
	
	@Test
	public void testSaveRoster() throws Exception {
		
		c.newRoster("test/abc.xml");
		assertFalse(c.rosterContainsMember("John"));
		
		c.addMember("John");
		
		Sequence seq = Sequence.SAVE_ROSTER;
		Sequence result = seq.run();
		
		assertEquals(Sequence.MAIN_MENU, result);
		
		c.removeMember("John");
		
		assertFalse(c.rosterContainsMember("John"));
		
		c.loadRoster("test/abc.xml");
		assertTrue(c.rosterContainsMember("John"));
		
	}
	
	// Helper methods
	
	private void setupInput(String input) throws FileNotFoundException {
		
		PrintStream in = new PrintStream("in.txt");
		in.append(input);
		in.close();
		
		System.setIn(new FileInputStream("in.txt"));
		Console.updateIn();
		
	}
	
	/**
	 * @param seq
	 *            The initial sequence
	 * @param expected
	 *            The expected sequences for the options in the sequence
	 * @throws IOException
	 *             The XML file isn't read correctly
	 */
	private void optionsReturnSequence(Sequence seq, Sequence[] expected) throws IOException {
		
		String input = "";
		for(int i = 0; i < expected.length; i++)
			input += (i + 1) + "\n";
		setupInput(input);
		
		Sequence result;
		
		for(Sequence expect : expected) {
			result = seq.run();
			assertEquals(expect, result);
		}
		
	}
	
	private String readOutput() {
		
		return out.readOutput();
		
	}
	
	private String toTitle(String title) {
		
		StringBuilder string = new StringBuilder();
		
		string.append('\n').append('\n');
		
		for(int i = 0; i < title.length(); i++)
			string.append('=');
		string.append('\n');
		
		string.append(title).append('\n');
		
		for(int i = 0; i < title.length(); i++)
			string.append('=');
		string.append('\n');
		
		return string.toString();
		
	}
	
	private static class ListenerStream extends PrintStream {
		
		private static final String lineSeparator = "\n";
		private final PrintStream mainStream;
		private StringBuilder output;
		
		public ListenerStream(PrintStream mainStream) {
			super(mainStream);
			this.mainStream = mainStream;
			this.output = new StringBuilder();
		}
		
		// Printing methods
		@Override
		public void println(String s) {
			output.append(s).append(lineSeparator);
			mainStream.println(s);
		}
		
		@Override
		public void print(String s) {
			output.append(s);
			mainStream.print(s);
		}
		
		@Override
		public void println(char c) {
			output.append(c).append(lineSeparator);
			mainStream.println(c);
		}
		
		@Override
		public void print(char c) {
			output.append(c);
			mainStream.print(c);
		}
		
		@Override
		public void println(Object obj) {
			output.append(obj).append(lineSeparator);
			mainStream.println(obj);
		}
		
		@Override
		public void println() {
			output.append(lineSeparator);
			mainStream.println();
		}
		
		@Override
		public void print(Object obj) {
			output.append(obj);
			mainStream.print(obj);
		}
		
		// Listener methods
		
		public PrintStream getMainStream() {
			return mainStream;
		}
		
		public String readOutput() {
			return output.toString();
		}
		
	}
	
	@Test
	public void testListenerStream() throws Exception {
		
		System.out.println("Hello!");
		assertEquals("Hello!\n", out.readOutput());
		
		System.out.print("Hello again!");
		assertTrue(out.readOutput().endsWith("Hello again!"));
		
		System.out.println('H');
		assertTrue(out.readOutput().endsWith("H\n"));
		
		System.out.print('i');
		assertTrue(out.readOutput().endsWith("i"));
		
		// Test object printing
		System.out.println((Object) "Hi");
		assertTrue(out.readOutput().endsWith("Hi\n"));
		
		System.out.print((Object) "Hi");
		assertTrue(out.readOutput().endsWith("Hi"));
		
		System.out.println();
		assertTrue(out.readOutput().endsWith("\n"));
		
	}
	
}
