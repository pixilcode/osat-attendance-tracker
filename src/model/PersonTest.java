package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nu.xom.Element;

public class PersonTest {

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
	}

	@Test
	public void testHashCodeEquals() {
		
		Person p1 = new Person("John");
		Person p2 = new Person("John");
		Person p3 = new Person("Jake");
		
		assertEquals(p1, p1);
		assertEquals(p1.hashCode(), p1.hashCode());
		assertEquals(p1, p2);
		assertEquals(p1.hashCode(), p2.hashCode());
		
		assertNotEquals(p1, p3);
		assertNotEquals(p1.hashCode(), p3.hashCode());
		
		HashMap<String, String> info = new HashMap<String, String>();
		info.put("key", "value");
		info.put("key2", "value");
		
	}

	@Test
	public void testPersonString() {
		
		Person p1 = new Person("John");
		assertEquals("John", p1.getName());
		
	}

	@Test
	public void testSetName() {
		
		Person p1 = new Person("John");
		assertEquals("John", p1.getName());
		p1.setName("Jake");
		assertEquals("Jake", p1.getName());
		
	}

	@Test
	public void testAttendance() {
		
		Person p1 = new Person("John");
		assertEquals(0, p1.getAttendance().size());
		
		Date today = new Date();
		p1.addAttendedDay(today);
		assertTrue(p1.getAttendance().contains(today));
		assertEquals(1, p1.getAttendance().size());
		
		Date christmas = new Date(new GregorianCalendar(2017, 12, 25));
		p1.addAttendedDay(christmas);
		assertTrue(p1.getAttendance().contains(christmas));
		assertEquals(2, p1.getAttendance().size());
		
		Date newYearsEve = new Date(new GregorianCalendar(2018, 1, 1));
		p1.addAttendedDay(newYearsEve);
		assertTrue(p1.getAttendance().contains(newYearsEve));
		assertEquals(3, p1.getAttendance().size());
		
		p1.removeAttendedDay(today);
		p1.removeAttendedDay(newYearsEve);
		p1.removeAttendedDay(christmas);
		assertEquals(0, p1.getAttendance().size());
		
	}

	@Test
	public void testToXMLelement() {
		
		Person p1 = new Person("John");
		Element p1Element = p1.toXMLelement();
		
		Element testElement = new Element("person");
		
		Element name = new Element("name");
		name.appendChild("John");
		
		testElement.appendChild(name);
		testElement.appendChild(new Element("attendance"));
		testElement.appendChild(new Element("tasks"));
		
		assertEquals(testElement.toXML(), p1Element.toXML());
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testNameWrapper() throws Exception {
		
		ArrayList<Person> array = new ArrayList<Person>();
		
		array.add(new Person("John Smith"));
		array.add(new Person("Abel Calf"));
		
		assertFalse(array.contains("John Smith"));
		assertFalse(array.contains("Abel Calf"));
		
		assertTrue(array.contains(new Name("John Smith")));
		assertTrue(array.contains(new Name("Abel Calf")));
		
	}

}
