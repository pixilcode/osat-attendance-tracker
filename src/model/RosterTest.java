package model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RosterTest {
	
	private Roster roster;
	
	private static final String NEW_FILE_LOC = System.getProperty("user.dir") + File.separator + "newFile.xml";
	private static final String FILE_LOC = System.getProperty("user.dir") + File.separator + "test.xml";
	private static final String STOCK_PERSON = "STOCK PERSON";
	private static final String STOCK_PERSON2 = "STOCK PERSON 2";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		File newFile = new File(NEW_FILE_LOC);
		if(newFile.exists())
			newFile.delete();
	}
	
	@Test
	public void testLoadRoster() throws Exception {
		
		// Also tests readXMLFile()
		
		roster = new Roster(FILE_LOC, Roster.Init.LOAD);
		
		assertEquals(FILE_LOC, roster.getLocation().toString());
		
		assertTrue(roster.contains(STOCK_PERSON));
		assertTrue(roster.contains(STOCK_PERSON2));
		
	}
	
	@Test
	public void testNewRoster() throws Exception {
		
		// Also tests writeXMLFile()
		
		roster = new Roster(NEW_FILE_LOC, Roster.Init.NEW);
		
		assertEquals(NEW_FILE_LOC, roster.getLocation().toString());
		assertEquals(0, roster.getMembers().size());
		
	}
	
	@Test
	public void testAddingRemoving() throws Exception {
		
		// Also tests contains(name)
		
		roster = new Roster(NEW_FILE_LOC, Roster.Init.NEW);
		
		assertEquals(0, roster.getMembers().size());
		
		roster.addPerson(STOCK_PERSON);
		
		assertTrue(roster.contains(STOCK_PERSON));
		
		roster.removePerson(STOCK_PERSON);
		
		assertEquals(0, roster.getMembers().size());
		
	}
	
	@Test
	public void testGetMember() throws Exception {
		
		roster = new Roster(NEW_FILE_LOC, Roster.Init.NEW);
		Person expected = new Person("John");
		
		roster.addPerson("John");
		
		Person received = roster.getMember("John");
		
		assertEquals(expected, received);
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		Roster roster1 = new Roster(FILE_LOC, Roster.Init.LOAD);
		Roster roster2 = new Roster(FILE_LOC, Roster.Init.LOAD);
		
		assertEquals(roster1, roster1);
		assertEquals(roster1.hashCode(), roster1.hashCode());
		assertEquals(roster1, roster2);
		assertEquals(roster1.hashCode(), roster2.hashCode());
		
	}
	
	@Test
	public void testIterator() throws Exception {
		
		ArrayList<String> members = new ArrayList<String>();
		members.add(STOCK_PERSON);
		members.add(STOCK_PERSON2);
		
		roster = new Roster(FILE_LOC, Roster.Init.LOAD);
		
		for(Person member : roster) {
			assertTrue(members.contains(member.getName()));
		}
		
	}
	
	@Test
	public void testTable() throws Exception {
		
		Roster roster = new Roster(NEW_FILE_LOC, Roster.Init.NEW);
		roster.addPerson("John Smith");
		roster.addPerson("James");
		
		String table =	"=========================" + "\n" +
						"|Name      |Days Present|" + "\n" +
						"=========================" + "\n" +
						"|John Smith|0           |" + "\n" +
						"-------------------------" + "\n" +
						"|James     |0           |" + "\n" +
						"-------------------------";
		
		assertEquals(table, roster.toTable());
		
	}

}
