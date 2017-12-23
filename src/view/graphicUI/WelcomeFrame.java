package view.graphicUI;

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
		panel.setLayout(new GridLayout(3,1));
		
		//A general wrapper for each part
		JPanel wrapper;
		
		//Make the welcome greeting
		wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(2,1));
		JLabel welcomeGreetingLine1 = new JLabel("Welcome to ON A ROLL!", JLabel.CENTER);
		JLabel welcomeGreetingLine2 = new JLabel("To get started, load a roster or create a new one", JLabel.CENTER);
		welcomeGreetingLine1.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		welcomeGreetingLine2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		wrapper.add(welcomeGreetingLine1);
		wrapper.add(welcomeGreetingLine2);
		panel.add(wrapper);
		
		//Add a place to find a file
		// TODO make a specialized browsing button that inserts location in location
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout());
		JPanel location = new JPanel();
		location.setLayout(new BoxLayout(location, BoxLayout.X_AXIS));
		JTextField locationText = new JTextField(10);
		JButton browse = new JButton("Browse");
		location.add(locationText);
		location.add(browse);
		wrapper.add(location);
		JButton load = new JButton("Load");
		wrapper.add(load);
		panel.add(wrapper);
		
		//Add a new button
		wrapper = new JPanel();
		JButton newRoster = new JButton("New");
		wrapper.add(newRoster);
		panel.add(wrapper);
		
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
