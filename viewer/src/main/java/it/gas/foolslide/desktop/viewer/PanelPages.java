package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.ImageCache;
import it.gas.foolslide.desktop.engine.persistence.Chapter;
import it.gas.foolslide.desktop.engine.persistence.Comic;
import it.gas.foolslide.desktop.engine.persistence.Page;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PanelPages extends JPanel {
	private static final long serialVersionUID = 1L;

	private Switcher switcher;
	private Comic comic;
	//private Chapter chapter;
	private List<Page> pages;
	private int index;
	
	private JButton btnChapters, btnNext, btnPrev;
	private JScrollPane scroll;
	private JLabel lblImage, lblTitle;
	private Logger logger;
	
	public PanelPages(Switcher s) {
		this.switcher = s;
		this.logger = LoggerFactory.getLogger(PanelPages.class);
		initComponents();
	}
	
	public void setChapter(Comic comic, Chapter chapter) {
		this.comic = comic;
		//this.chapter = chapter;
		if (chapter != null) {
			StringBuilder build = new StringBuilder();
			build.append("<html><b>");
			build.append(chapter.getChapter());
			if (chapter.getSubchapter() != 0) {
				build.append('.');
				build.append(chapter.getSubchapter());
			}
			build.append("</b> ");
			build.append(chapter.getName());
			lblTitle.setText(build.toString());
			if (! chapter.isPagesGot()) {
				Engine.getInstance().getPages(chapter);
				chapter.setPagesGot(true);
			}
			pages = Engine.getInstance().getPages(chapter);
			index = 0;
			showPage();
		}
		scroll.getHorizontalScrollBar().setValue(0);
		scroll.getVerticalScrollBar().setValue(0);
		
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		
		lblTitle = new JLabel();
		add(lblTitle, BorderLayout.NORTH);
		
		lblImage = new JLabel();
		scroll = new JScrollPane(lblImage);
		add(scroll);
		
		JPanel buttoner = new JPanel(new FlowLayout());
		add(buttoner, BorderLayout.SOUTH);
		
		btnChapters = new JButton("< Chapters");
		buttoner.add(btnChapters);
		btnChapters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switcher.showChapters(comic);
			}
		});
		
		buttoner.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnPrev = new JButton("<");
		buttoner.add(btnPrev);
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					index--;
					showPage();
				}
			}
		});
		
		btnNext = new JButton(">");
		buttoner.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index < pages.size() - 1) {
					index++;
					showPage();
				}
			}
		});
	}
	
	private void showPage() {
		try {
			Page p = pages.get(index);
			Image i = ImageCache.getInstance().get(p.getUrl());
			lblImage.setText("");
			lblImage.setIcon(new ImageIcon(i));
		} catch (IOException e) {
			lblImage.setText(e.getMessage());
			lblImage.setIcon(null);
			logger.warn("Exception showing the page", e);
		}
	}
	
}
