package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class WelcomeFrame extends BasicFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JButton loadButton;
	private final JButton newButton;
	
	public WelcomeFrame() {
		
		//Format JFrame
		super("ON A ROLL", 500, 100, 20);
		setLookAndFeel();
		
		//Make a JPanel to add everything to and format it
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
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
		
		//Add a new button
		wrapper = new JPanel();
		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		newButton = new JButton("New");
		newButton.addActionListener(this);
		wrapper.add(loadButton);
		wrapper.add(newButton);
		panel.add(wrapper);
		
		//Add panel to the frame
		add(panel);
		
		cleanUp();
		
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		//Discover the source
		if(source == loadButton) {
			//Do something
		}else if(source == newButton) {
			dispose();
			new NewRosterFrame();
		}
		
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			//and ignore
		}
	}
	
}
