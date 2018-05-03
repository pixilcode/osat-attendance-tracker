package view.consoleUI;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CUITests {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
		System.setOut(new PrintStream("outFile.txt"));
		Console.updateOut();
		
	}

	@After
	public void tearDown() throws Exception {
		
		(new File("outFile.txt")).delete();
		
	}
	
	@Test
	public void testSetUp() throws Exception {
		
		System.out.println("Hello world!");
		
		Scanner file = new Scanner(new File("outFile.txt"));
		String output = "";
		while(file.hasNextLine())
			output += file.nextLine() + " ";
		
		assertEquals("Hello world! ", output);
		
		file.close();
		
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
		expected += "\n";
		expected += "===" + "\n" +
					"HI!" + "\n" +
					"===" + "\n";
		
		Scanner outputFile = new Scanner(new File("outFile.txt"));
		String output = "";
		
		while(outputFile.hasNextLine())
			output += outputFile.nextLine() + "\n";
		
		assertEquals(expected, output);
		
		outputFile.close();
		
	}
	
	@Test
	public void testOptions() throws Exception {
		
		String expected = "";
		
		Console.printOptions(new String[] {
				"Option 1",
				"Option 2",
				"Option 3"
		});
		
		expected += "1. Option 1" + "\n" +
					"2. Option 2" + "\n" +
					"3. Option 3" + "\n";
		
		Scanner outputFile = new Scanner(new File("outFile.txt"));
		String output = "";
		
		while(outputFile.hasNextLine())
			output += outputFile.nextLine() + "\n";
		
		assertEquals(expected, output);
		
		outputFile.close();
		
	}
	
	@Test
	public void testWelcomeSequence() throws Exception {
		
	}

}
