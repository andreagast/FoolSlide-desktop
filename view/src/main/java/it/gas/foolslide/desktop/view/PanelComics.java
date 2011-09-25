package it.gas.foolslide.desktop.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.persistence.Comic;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelComics extends AbstractMainPanel {
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
		comicsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				btnChapters.setEnabled(! comicsList.isSelectionEmpty());
			}
		});
		
		JPanel pnlBottom = new JPanel(new FlowLayout());
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnReset = new JButton("Reset");
		pnlBottom.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: aggiungere richiesta?
				controller.requestReset();
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnChapters = new JButton("Next >");
		btnChapters.setEnabled(false);
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
	public void setComicsList(List<Comic> l) {
		comicsList.setListData(l.toArray());
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}
	
}
