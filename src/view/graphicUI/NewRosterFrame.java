package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewRosterFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 400;
	
	private final JFileChooser dir;
	private final JTextField name;
	private final JButton cancel;
	private final JButton next;
	
	public NewRosterFrame() {
		
		//Setup the frame
		super("New Roster - ON A ROLL");
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
		
		//Panel to hold everything
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//A wrapper panel
		JPanel wrapper;
		
		//Add instructions
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel instructions = new JLabel("Pick location and roster name");
		instructions.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		wrapper.add(instructions);
		panel.add(wrapper);
		
		//Add a way to pick a location
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
		dir = new JFileChooser();
		dir.setControlButtonsAreShown(false);
		dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		panel.add(dir);
		
		//Add a place for the name of the file
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("File name: ");
		name = new JTextField(20);
		name.setText("roster1.xml");
		wrapper.add(label);
		wrapper.add(name);
		panel.add(wrapper);
		
		//Add buttons to continue/cancel
		wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		next = new JButton("Next");
		next.addActionListener(this);
		wrapper.add(cancel);
		wrapper.add(next);
		panel.add(wrapper);
		
		//Add panel to the center
		add(panel, BorderLayout.CENTER);
		
		//Clean up and show
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		
		if(source == cancel) {
			dispose();
			new WelcomeFrame();
		}else if(source == next) {
			
		}
		
	}

}
