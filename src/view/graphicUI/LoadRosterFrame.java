package view.graphicUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoadRosterFrame extends BasicFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private final JFileChooser file;
	private final JButton cancelButton;
	private final JButton selectButton;
	
	public LoadRosterFrame() {
		
		// Setup frame
		super("Load Roster", 1000, 1000, 500);
		
		// Create a panel to store everything in
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// Add instructions
		JLabel label = new JLabel("Select roster file");
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		panel.add(wrap(label, new FlowLayout(FlowLayout.LEFT)));
		
		// Add a file selection area
		file = new JFileChooser();
		file.setControlButtonsAreShown(false);
		panel.add(wrap(file, new FlowLayout(FlowLayout.LEFT)));
		
		// Add 'cancel' and 'select' buttons
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		selectButton = new JButton("Select");
		selectButton.addActionListener(this);
		panel.add(wrap(new Component[] {cancelButton, selectButton}, new FlowLayout(FlowLayout.RIGHT)));
		
		// Add panel and clean up frame
		add(panel);
		cleanUp();
		
	}

	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		
		if(source == cancelButton) {
			dispose();
			new WelcomeFrame();
		}else if(source == selectButton) {
			// Do something else
		}
		
	}

}
