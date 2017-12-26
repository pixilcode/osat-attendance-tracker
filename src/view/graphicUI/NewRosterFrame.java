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
		super("New Roster", 500, 400, 50);
		
		//Panel to hold everything
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Add instructions
		JLabel instructions = new JLabel("Pick location and roster name");
		instructions.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		panel.add(wrap(instructions, new FlowLayout(FlowLayout.LEFT)));
		
		//Add a way to pick a location
		dir = new JFileChooser();
		dir.setControlButtonsAreShown(false);
		dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		panel.add(wrap(dir, new FlowLayout(FlowLayout.LEFT)));
		
		//Add a place for the name of the file
		JLabel label = new JLabel("File name: ");
		name = new JTextField(20);
		name.setText("roster1.xml");
		panel.add(wrap(new Component[] {label, name}, new FlowLayout(FlowLayout.CENTER)));
		
		//Add buttons to continue/cancel
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		next = new JButton("Next");
		next.addActionListener(this);
		panel.add(wrap(new Component[] {cancel, next}, new FlowLayout(FlowLayout.RIGHT)));
		
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
