package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewRosterFrame extends BasicFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private final JFileChooser dir;
	private final JTextField name;
	private final JButton cancel;
	private final JButton next;
	
	public NewRosterFrame() {
		
		//Setup the frame
		super("New Roster - ON A ROLL", 500, 400, 50);
		
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
		
		add(panel);
		cleanUp();
		
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
