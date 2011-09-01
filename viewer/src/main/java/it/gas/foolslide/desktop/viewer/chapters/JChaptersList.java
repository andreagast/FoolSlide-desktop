package it.gas.foolslide.desktop.viewer.chapters;

import javax.swing.JList;

public class JChaptersList extends JList {
	private static final long serialVersionUID = 1L;

	public JChaptersList() {
		super();
		setCellRenderer(new ChapterCellRenderer());
	}
	
}
