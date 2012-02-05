package it.gas.foolslide.desktop.view;

import it.gas.foolslide.desktop.persistence.Comic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import it.gas.foolslide.desktop.controller.MainController;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public class PanelComics extends AbstractMainPanel {
	private static final long serialVersionUID = 1L;
	
	private MainController controller;
	
	private JList comicsList;
	private JScrollPane scroll;
	private JButton btnReset, btnChapters;
	private JPanel detailsPane;
	private JLabel lblImage, lblTitle;
	private JTextArea txtDescription;

	public PanelComics(MainController controller) {
		this.controller = controller;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		
		comicsList = new JList();
		comicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(comicsList);
		add(scroll, BorderLayout.WEST);
		comicsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnChapters.setEnabled(! comicsList.isSelectionEmpty());
				
				//details implementation
				if (! comicsList.isSelectionEmpty()) {
					Comic c = (Comic) comicsList.getSelectedValue();
					lblImage.setIcon(new ImageIcon(c.getThumb()));
					lblTitle.setText(c.getName());
					txtDescription.setText(c.getDescription());
				}
			}
		});
		
		//DETAILS PANEL
		
		detailsPane = new JPanel(new MigLayout("fill"));
		add(detailsPane);
		
		lblImage = new JLabel();
		lblImage.setBackground(Color.WHITE);
		detailsPane.add(lblImage);
		
		lblTitle = new JLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		Font f = lblTitle.getFont();
		lblTitle.setFont(new Font(f.getFamily(), Font.BOLD, f.getSize()));
		detailsPane.add(lblTitle, "growx, pushx, wrap");
		
		txtDescription = new JTextArea();
		txtDescription.setEditable(false);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		
		detailsPane.add(new JScrollPane(txtDescription), "push, grow, spanx 2");
		
		//BOTTOM PANEL
		
		JPanel pnlBottom = new JPanel(new FlowLayout());
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnReset = new JButton("Reset");
		pnlBottom.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
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
