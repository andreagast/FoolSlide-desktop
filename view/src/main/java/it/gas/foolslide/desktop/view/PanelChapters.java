package it.gas.foolslide.desktop.view;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.persistence.Chapter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelChapters extends AbstractMainPanel {
	private static final long serialVersionUID = 1L;
	
	private MainController controller;
	
	private JScrollPane scroll;
	private JList chaptersList;
	private JButton btnComics, btnPages;

	public PanelChapters(MainController controller) {
		this.controller = controller;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		
		chaptersList = new JList();
		chaptersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(chaptersList);
		add(scroll);
		chaptersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnPages.setEnabled(! chaptersList.isSelectionEmpty());
			}
		});
		
		JPanel pnlBottom = new JPanel(new FlowLayout());
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnComics = new JButton("Comic's list");
		pnlBottom.add(btnComics);
		btnComics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showComics();
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnPages = new JButton("Look it!");
		btnPages.setEnabled(false);
		pnlBottom.add(btnPages);
		btnPages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: migliorare
				controller.showPages((Chapter) chaptersList.getSelectedValue());
			}
		});
		
	}

	@Override
	public void setChaptersList(List<Chapter> l) {
		chaptersList.setListData(l.toArray());
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}
	
}
