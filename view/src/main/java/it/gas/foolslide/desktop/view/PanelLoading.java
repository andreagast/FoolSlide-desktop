package it.gas.foolslide.desktop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelLoading extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelLoading() {
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		add(new JLabel("Loading... Please wait..."), 0);
		c.gridy = 1;
		JProgressBar progBar = new JProgressBar();
		progBar.setIndeterminate(true);
		add(progBar, c);
	}
	
}
