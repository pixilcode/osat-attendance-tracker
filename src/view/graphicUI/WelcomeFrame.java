package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomeFrame extends BasicFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JButton loadButton;
	private final JButton newButton;
	
	public WelcomeFrame() {
		
		//Format JFrame
		super("Welcome", 500, 100, 20);
		setLookAndFeel();
		
		//Make a JPanel to add everything to and format it
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		//Make the welcome greeting
		JLabel welcomeGreetingLine1 = new JLabel("Welcome to ON A ROLL!", JLabel.CENTER);
		JLabel welcomeGreetingLine2 = new JLabel("To get started, load a roster or create a new one", JLabel.CENTER);
		welcomeGreetingLine1.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		welcomeGreetingLine2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		panel.add(wrap(new Component[] {welcomeGreetingLine1, welcomeGreetingLine2}, new GridLayout(2, 1)));
		
		//Add a new button
		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		newButton = new JButton("New");
		newButton.addActionListener(this);
		panel.add(wrap(new Component[] {loadButton, newButton}, new FlowLayout(FlowLayout.CENTER)));
		
		//Add panel to the frame
		add(panel);
		
		cleanUp();
		
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		//Discover the source
		if(source == loadButton) {
			dispose();
			new LoadRosterFrame();
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
