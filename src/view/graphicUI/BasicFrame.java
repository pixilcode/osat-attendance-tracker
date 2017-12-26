package view.graphicUI;

import java.awt.*;
import javax.swing.*;

public abstract class BasicFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected final int windowHeight;
	protected final int windowWidth;
	
	public BasicFrame(String frameName, int windowHeight, int windowWidth, int paddingSize) {
		
		// Name the frame and assign the variables
		super(frameName + " - ON A ROLL");
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		
		setLayout(new BorderLayout());
		addSpacers(paddingSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void addSpacers(int paddingSize) {
		
		//Create spacers
		JPanel[] spacer = new JPanel[4];
		int top = 0;
		int right = 1;
		int bottom = 2;
		int left = 3;
		for(int i = 0; i < spacer.length; i++) spacer[i] = new JPanel();
		spacer[top].setSize(new Dimension(windowWidth, paddingSize));
		spacer[bottom].setSize(new Dimension(windowWidth, paddingSize));
		spacer[right].setSize(new Dimension(paddingSize, windowHeight-(2*paddingSize)));
		spacer[left].setSize(new Dimension(paddingSize, windowHeight-(2*paddingSize)));
		
		//Add spacers
		add(spacer[top], BorderLayout.NORTH);
		add(spacer[bottom], BorderLayout.SOUTH);
		add(spacer[left], BorderLayout.WEST);
		add(spacer[right], BorderLayout.EAST);
		
	}
	
	/**
	 * add the center panel, pack, and set to visible
	 */
	protected void cleanUp() {
		pack();
		setVisible(true);
	}
	
	protected JPanel wrap(Component[] components, LayoutManager layout) {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(layout);
		for(Component component : components)
			wrapper.add(component);
		return wrapper;
	}
	
	protected JPanel wrap(Component component, LayoutManager layout) {
		return wrap(new Component[] {component}, layout);
	}
	
	@Override
	public Component add(Component component) {
		super.add(component, BorderLayout.CENTER);
		return component;
	}

}
