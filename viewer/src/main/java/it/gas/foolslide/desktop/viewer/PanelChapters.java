package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.persistence.Chapter;
import it.gas.foolslide.desktop.engine.persistence.Comic;
import it.gas.foolslide.desktop.viewer.chapters.JChaptersList;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelChapters extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	private JChaptersList chapList;
	private JButton btnComics, btnPages;
	private JLabel lblTitle;
	
	private Switcher switcher;
	private Comic comic;
	
	public PanelChapters(Switcher s) {
		this.switcher = s;
		this.comic = null;
		initComponents();
	}
	
	public void setComic(Comic c) {
		this.comic = c;
		if (c != null) {
			chapList.setListData(Engine.getInstance().getChapters(c).toArray());
			lblTitle.setText(c.getName());
		}
		scroll.getHorizontalScrollBar().setValue(0);
		scroll.getVerticalScrollBar().setValue(0);
		
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		chapList = new JChaptersList();
		scroll = new JScrollPane(chapList);
		add(scroll);
		
		lblTitle = new JLabel();
		Font tmp = lblTitle.getFont();
		lblTitle.setFont(new Font(tmp.getFamily(), Font.BOLD, tmp.getSize()));
		add(lblTitle, BorderLayout.NORTH);
		
		JPanel buttoner = new JPanel();
		add(buttoner, BorderLayout.SOUTH);
		
		btnComics = new JButton("< Back");
		buttoner.add(btnComics);
		btnComics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switcher.showComics();
			}
		});
		
		btnPages = new JButton("Next >");
		buttoner.add(btnPages);
		btnPages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chapList.isSelectionEmpty())
					return;
				Chapter c = (Chapter) chapList.getSelectedValue();
				switcher.showPages(comic, c);
			}
		});
	}

}
