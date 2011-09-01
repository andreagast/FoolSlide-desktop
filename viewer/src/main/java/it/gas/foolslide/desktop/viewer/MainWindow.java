package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.persistence.Chapter;
import it.gas.foolslide.desktop.engine.persistence.Comic;

import java.awt.CardLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements Switcher {
	private static final long serialVersionUID = 1L;
	private static final String COMICS = "comics";
	private static final String CHAPTERS = "chapters";
	private static final String PAGES = "pages";
	
	private CardLayout cl;
	
	private PanelComics pnlComics;
	private PanelChapters pnlChapters;
		
	public MainWindow() {
		super("FoOlSlide Desktop");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		cl = new CardLayout();
		setLayout(cl);
		
		pnlComics = new PanelComics(this);
		getContentPane().add(pnlComics, COMICS);
		pnlChapters = new PanelChapters(this);
		getContentPane().add(pnlChapters, CHAPTERS);
	}

	@Override
	public void showComics() {
		cl.show(getContentPane(), COMICS);
	}

	@Override
	public void showChapters(Comic c) {
		pnlChapters.setComic(c);
		cl.show(getContentPane(), CHAPTERS);
	}

	@Override
	public void showPages(Chapter c) {
		//TODO: set the panel to show the first picture
		cl.show(getContentPane(), PAGES);		
	}

}
