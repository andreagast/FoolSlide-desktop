package it.gas.foolslide.desktop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		JProgressBar progBar = new JProgressBar();
		progBar.setIndeterminate(true);
		add(progBar, c);
	}
	
}
