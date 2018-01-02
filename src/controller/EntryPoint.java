package controller;

public class EntryPoint {

	/**
	 * @param args the arguments given by the console
	 */
	public static void main(String[] args) {
		
		//To test
		test();
		
		//To run
//		run();

	}
	
	public static void run() {
		(new Controller()).run();;
	}
	
	public static void test() {
		TestConsoleArgParser.run();
	}

}
