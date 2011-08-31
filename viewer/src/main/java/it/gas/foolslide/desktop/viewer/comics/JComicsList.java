package it.gas.foolslide.desktop.viewer.comics;

import javax.swing.JList;

public class JComicsList extends JList {
	private static final long serialVersionUID = 1L;

	public JComicsList() {
		super();
		setCellRenderer(new ComicCellRenderer());
	}
	
}
