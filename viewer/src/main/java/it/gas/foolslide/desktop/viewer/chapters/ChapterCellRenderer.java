package it.gas.foolslide.desktop.viewer.chapters;

import it.gas.foolslide.desktop.engine.persistence.Chapter;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ChapterCellRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public ChapterCellRenderer() {
		super();
		setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		StringBuilder build = new StringBuilder();
		Chapter c = (Chapter) value;
		build.append("<html><b>");
		build.append(c.getChapter());
		if (c.getSubchapter() != 0) {
			build.append('.');
			build.append(c.getSubchapter());
		}
		build.append("</b> ");
		build.append(c.getName());
		setText(build.toString());
		if (isSelected)
			setBackground(Color.BLUE);
		else
			this.setBackground(Color.WHITE);
		return this;
	}

}
