package it.gas.foolslide.desktop.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.MainControllerListener;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class PanelComics extends JPanel implements MainControllerListener {
	private static final long serialVersionUID = 1L;
	
	private MainController controller;
	
	private JList comicsList;
	private JScrollPane scroll;
	private JButton btnReset, btnChapters;

	public PanelComics(MainController controller) {
		this.controller = controller;
		initComponents();
	}
	
	private void initComponents() {
setLayout(new BorderLayout());
		
		comicsList = new JList();
		comicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(comicsList);
		add(scroll);
		
		JPanel pnlBottom = new JPanel(new FlowLayout());
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnReset = new JButton("Reset");
		pnlBottom.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: migliorare
				controller.requestReset();
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnChapters = new JButton("Next >");
		pnlBottom.add(btnChapters);
		btnChapters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO migliorare
				controller.showChapters((Comic) comicsList.getSelectedValue());
			}
		});
		
	}

	@Override
	public void showComicsPane() {}

	@Override
	public void showChaptersPane() {}

	@Override
	public void showPagesPane() {}

	@Override
	public void setComicsList(List<Comic> l) {
		comicsList.setListData(l.toArray());
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}

	@Override
	public void setChaptersList(List<Chapter> l) {}

	@Override
	public void setPagesList(List<Page> l) {}

	@Override
	public void showLoadingPane() {}

	@Override
	public void showPopupMessage(String str, int type) {}
	
	@Override
	public void exitApp() {}
	
}
