package model;

import java.util.ArrayList;

public class TestWrapper {
	
	@SuppressWarnings("unlikely-arg-type")
	public static boolean run() {
		
		ArrayList<Person> array = new ArrayList<Person>();
		
		array.add(new Person("John Smith"));
		array.add(new Person("Abel Calf"));
		
		System.out.println(array.contains("John Smith") == false);
		System.out.println(array.contains("Abel Calf") == false);
		
		System.out.println(array.contains(new Name("John Smith")) == true);
		System.out.println(array.contains(new Name("Abel Calf")) == true);
		
		return true;
		
	}

}
