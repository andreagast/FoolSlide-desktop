package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.persistence.Comic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class PanelComics extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	private JList comicsList;
	private JButton refreshButton;
	
	public PanelComics() {
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		comicsList = new JList();
		scroll = new JScrollPane(comicsList);
		add(scroll);
		
		//button
		refreshButton = new JButton("Refresh list");
		add(refreshButton, BorderLayout.SOUTH);
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshButton.setEnabled(false);
				new Loader().execute();
			}
		});
	}
	
	private class Loader extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			Engine.getInstance().refresh();
			return null;
		}
		
		@Override
		protected void done() {
			refreshButton.setEnabled(true);
			List<Comic> l = Engine.getInstance().getComics();
			comicsList.setListData(l.toArray());
		}
		
	}

}
