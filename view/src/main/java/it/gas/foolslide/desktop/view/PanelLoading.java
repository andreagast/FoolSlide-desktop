package it.gas.foolslide.desktop.view;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelLoading extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelLoading() {
		initComponents();
	}
	
	private void initComponents() {
		//setLayout(new BorderLayout());
		JProgressBar progBar = new JProgressBar();
		progBar.setIndeterminate(true);
		add(progBar);
	}
	
}
