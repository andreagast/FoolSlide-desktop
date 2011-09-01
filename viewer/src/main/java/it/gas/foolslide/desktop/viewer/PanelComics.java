package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.persistence.Comic;
import it.gas.foolslide.desktop.viewer.comics.JComicsList;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class PanelComics extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	private JComicsList comicsList;
	private JButton btnInitialize, btnChapters;
	
	private Switcher switcher;
	
	public PanelComics(Switcher s) {
		this.switcher = s;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		comicsList = new JComicsList();
		scroll = new JScrollPane(comicsList);
		add(scroll);
		
		JPanel buttoner = new JPanel();
		add(buttoner, BorderLayout.SOUTH);
		
		//button
		btnInitialize = new JButton("Refresh list");
		buttoner.add(btnInitialize);
		btnInitialize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lockForm(true);
				new Loader().execute();
			}
		});
		
		btnChapters = new JButton("Next >");
		buttoner.add(btnChapters);
		btnChapters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comicsList.isSelectionEmpty())
					return;
				Comic c = (Comic) comicsList.getSelectedValue();
				switcher.showChapters(c);
			}
		});
	}
	
	private void lockForm(boolean b) {
		btnInitialize.setEnabled(!b);
		btnChapters.setEnabled(!b);
		comicsList.setEnabled(!b);
	}
	
	private class Loader extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			Engine.getInstance().initialize();
			return null;
		}
		
		@Override
		protected void done() {
			lockForm(false);
			List<Comic> l = Engine.getInstance().getComics();
			comicsList.setListData(l.toArray());
		}
		
	}

}
