package view;

import java.awt.*;
import javax.swing.*;

public class WelcomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public WelcomeFrame() {
		
		//Format JFrame
		super("ON A ROLL");
		setLookAndFeel();
		setSize(new Dimension(600, 150));
		
		//Make a JPanel to add everything to and format it
		JPanel panel = new JPanel();
		
		//Make the welcome greeting
		JLabel welcomeGreetingLine1 = new JLabel("Welcome to ON A ROLL!", JLabel.CENTER);
		JLabel welcomeGreetingLine2 = new JLabel("To get started, load a roster or create a new one", JLabel.CENTER);
		welcomeGreetingLine1.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		welcomeGreetingLine2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		panel.add(welcomeGreetingLine1);
		panel.add(welcomeGreetingLine2);
		
		//Add a place to find a file
		// TODO make a specialized browsing button that inserts location in location
		JPanel location = new JPanel();
		location.setLayout(new BoxLayout(location, BoxLayout.X_AXIS));
		JTextField locationText = new JTextField(15);
		JButton browse = new JButton("Browse");
		location.add(locationText);
		location.add(browse);
		panel.add(location);
		
		//Add a load button
		JButton load = new JButton("Load");
		panel.add(load);
		
		//Add a new button
		JButton newRoster = new JButton("New");
		panel.add(newRoster);
		
		//Add panel to the frame
		add(panel);
		
		//Set the program to exit on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Pack and make visible
		setVisible(true);
		
	}
	
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			//and ignore
		}
	}
	
}
