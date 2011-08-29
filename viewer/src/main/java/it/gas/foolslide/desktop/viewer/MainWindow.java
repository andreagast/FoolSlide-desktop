package it.gas.foolslide.desktop.viewer;

import java.awt.CardLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String COMICS = "comics";
	private static final String CHAPTERS = "chapters";
	private static final String PAGES = "pages";
		
	public MainWindow() {
		super("FoOlSlide Desktop");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		CardLayout cl = new CardLayout();
		setLayout(cl);
		
		getContentPane().add(new PanelComics(), COMICS);
	}

}
