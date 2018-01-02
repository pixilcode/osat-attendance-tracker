package controller;

import java.util.ArrayList;

/**
 * Lesson: 	to find an object in an ArrayList by just
 * 			one of it's elements, one must make a
 * 			wrapper object and override the equals as
 * 			shown below to find it
 * 
 * @author dragonfire
 *
 */

public final class TestArrayFind {
	
	@SuppressWarnings("unlikely-arg-type")
	public void run() {
		
		ArrayList<TestObject> array = new ArrayList<TestObject>();
		
		array.add(new TestObject("One", 1));
		array.add(new TestObject("Two", 2));
		
		System.out.println(array.contains("One"));
		System.out.println(array.contains("Two"));
		System.out.println(array.contains("Three"));
		
		System.out.println(array.contains(new NameWrapper("One")));
		System.out.println(array.contains(new NameWrapper("Two")));
		System.out.println(array.contains(new NameWrapper("Three")));
		
		System.out.println(array.contains(1));
		System.out.println(array.contains(2));
		System.out.println(array.contains(3));
		
		System.out.println(array.contains(new IntWrapper(1)));
		System.out.println(array.contains(new IntWrapper(2)));
		System.out.println(array.contains(new IntWrapper(3)));
		
		System.out.println(array.indexOf("One"));
		System.out.println(array.indexOf("Two"));
		System.out.println(array.indexOf("Three"));
		
		System.out.println(array.indexOf(new NameWrapper("One")));
		System.out.println(array.indexOf(new NameWrapper("Two")));
		System.out.println(array.indexOf(new NameWrapper("Three")));
		
		System.out.println(array.indexOf(1));
		System.out.println(array.indexOf(2));
		System.out.println(array.indexOf(3));
		
		System.out.println(array.indexOf(new IntWrapper(1)));
		System.out.println(array.indexOf(new IntWrapper(2)));
		System.out.println(array.indexOf(new IntWrapper(3)));
		
	}
	
	private class TestObject {
		
		public final String name;
		public final int value;
		
		public TestObject(String name, int value) {
			this.name = name;
			this.value = value;
		}
		
	}
	
	private class NameWrapper {
		
		public final String name;
		
		public NameWrapper(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj instanceof TestObject) {
				TestObject other = (TestObject) obj;
				if(name.equals(other.name))
					return true;
			}
			if(obj instanceof String) {
				String other = (String) obj;
				if (other.equals(name))
					return true;
				return false;
			}
			if(obj instanceof NameWrapper) {
				NameWrapper other = (NameWrapper) obj;
				if (!getOuterType().equals(other.getOuterType()))
					return false;
				if (name == null) {
					if (other.name != null)
						return false;
				} else if (!name.equals(other.name))
					return false;
				return true;
			}
			return false;
		}

		private TestArrayFind getOuterType() {
			return TestArrayFind.this;
		}
		
	}
	
	private class IntWrapper {
		
		public final int value;
		
		public IntWrapper(int value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + value;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj instanceof TestObject) {
				TestObject other = (TestObject) obj;
				if(value == other.value)
					return true;
			}
			if (obj instanceof Integer) {
				int other = ((Integer) obj).intValue();
				return other == value;
			}
			if (obj instanceof IntWrapper) {
				IntWrapper other = (IntWrapper) obj;
				if (!getOuterType().equals(other.getOuterType()))
					return false;
				if (value != other.value)
					return false;
				return true;
			}
			return false;
		}

		private TestArrayFind getOuterType() {
			return TestArrayFind.this;
		}
		
	}
	
}
