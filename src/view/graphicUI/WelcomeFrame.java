package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomeFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 300;
	
	private final JTextField locText;
	private final JButton browseButton;
	private final JButton loadButton;
	private final JButton newButton;
	
	public WelcomeFrame() {
		
		//Format JFrame
		super("ON A ROLL");
		setLookAndFeel();
		setSize(new Dimension(600, 150));
		setLayout(new BorderLayout());
		
		//Create spacers
		JPanel[] spacer = new JPanel[4];
		int top = 0;
		int right = 1;
		int bottom = 2;
		int left = 3;
		for(int i = 0; i < spacer.length; i++) spacer[i] = new JPanel();
		spacer[top].setSize(new Dimension(WINDOW_WIDTH, 50));
		spacer[bottom].setSize(new Dimension(WINDOW_WIDTH, 50));
		spacer[right].setSize(new Dimension(50, WINDOW_HEIGHT-100));
		spacer[right].setSize(new Dimension(50, WINDOW_HEIGHT-100));
		
		//Add spacers
		add(spacer[top], BorderLayout.NORTH);
		add(spacer[bottom], BorderLayout.SOUTH);
		add(spacer[left], BorderLayout.WEST);
		add(spacer[right], BorderLayout.EAST);
		
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
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout());
		JPanel location = new JPanel();
		location.setLayout(new BoxLayout(location, BoxLayout.X_AXIS));
		locText = new JTextField(10);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(this);
		location.add(locText);
		location.add(browseButton);
		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		wrapper.add(location);
		wrapper.add(loadButton);
		panel.add(wrapper);
		
		//Add a new button
		wrapper = new JPanel();
		newButton = new JButton("New");
		newButton.addActionListener(this);
		wrapper.add(newButton);
		panel.add(wrapper);
		
		//Add panel to the frame
		add(panel, BorderLayout.CENTER);
		
		//Set the program to exit on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Make visible
		setVisible(true);
		
	}
	
	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			//and ignore
		}
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		//Discover the source
		if(source == browseButton) {
			//Do something
		}else if(source == loadButton) {
			//Do something else
		}else if(source == newButton) {
			dispose();
			new NewRosterFrame();
		}
		
	}
	
}
