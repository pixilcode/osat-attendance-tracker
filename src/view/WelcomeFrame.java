package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class WelcomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public WelcomeFrame() {
		
		//Format JFrame
		super("ON A ROLL");
		setLookAndFeel();
		
		//Make a JPanel to add everything to and format it
		JPanel panel = new JPanel();
		
		//Make the welcome greeting
		JLabel welcomeGreetingLine1 = new JLabel("Welcome to ON A ROLL!", JLabel.CENTER);
		JLabel welcomeGreetingLine2 = new JLabel("To get started, load a roster or create a new one", JLabel.CENTER);
		panel.add(welcomeGreetingLine1);
		panel.add(welcomeGreetingLine2);
		
		//Add a place to put the location and a button for browsing
		// TODO make a specialized browsing button that inserts location in location
		JTextField location = new JTextField(15);
		JButton browse = new JButton("Browse");
		panel.add(location);
		panel.add(browse);
		
		JButton load = new JButton("Load");
		panel.add(load);
		
		//Add panel to the frame
		add(panel);
		
		//Set the program to exit on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Pack and make visible
		pack();
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
