package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainController {
	private List<MainControllerListener> listeners;
	private Engine engine;

	public MainController() {
		listeners = new LinkedList<MainControllerListener>();
		engine = Engine.getInstance();
	}

	public void addListener(MainControllerListener l) {
		listeners.add(l);
	}

	public void removeListener(MainControllerListener l) {
		listeners.remove(l);
	}

	/**
	 * Delete all the lists; download again the comics list; show the comics
	 * list;
	 */
	public void requestReset() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fireShowLoadingPane();
			}
		});
		new Thread(new Runnable() {
			public void run() {
				try {
					engine.reset();
					List<Comic> list;
					list = engine.getComics();
					fireSetComicsList(list);
					fireShowComicsPane();
				} catch (Exception e) {
					fireShowPopupMessage("Can't download comics list.",
							JOptionPane.ERROR_MESSAGE);
					fireExitApp();
				}
			}
		}).run();
	}

	/**
	 * Change to the comics pane.
	 */
	public void showComics() {
		fireShowComicsPane();
	}

	/**
	 * Retrieve the chapters list and show it switching to the chapters pane.
	 * 
	 * @param comic
	 *            the comic to retrieve; if null, simply switch to the view.
	 */
	public void showChapters(final Comic comic) {
		fireShowLoadingPane();
		if (comic != null) {
			
			new Thread(new Runnable() {
				public void run() {
					List<Chapter> list;
					try {
						list = engine.getChapters(comic);
						fireSetChaptersList(list);
						fireShowChaptersPane();
					} catch (Exception e) {
						fireShowPopupMessage("Can't download chapters list.",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}).run();

		} else {
			fireShowChaptersPane();
		}
	}

	/**
	 * Retrieve the pages list and show it switching to the pages pane.
	 * 
	 * @param chapter
	 *            the chapters from which retrieve the pages
	 */
	public void showPages(final Chapter chapter) {
		fireShowLoadingPane();
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Page> list;
				try {
					list = engine.getPages(chapter);
					fireSetPagesList(list);
					fireShowPagesPane();
				} catch (Exception e) {
					fireShowPopupMessage("Can't download pages list.",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}).run();
	}

	/**
	 * Call when you want to close the app.
	 */
	public void requestExit() {
		fireExitApp();
	}

	private void fireShowComicsPane() {
		for (MainControllerListener l : listeners)
			l.showComicsPane();
	}

	private void fireShowChaptersPane() {
		for (MainControllerListener l : listeners)
			l.showChaptersPane();
	}

	private void fireShowPagesPane() {
		for (MainControllerListener l : listeners)
			l.showPagesPane();
	}

	private void fireSetComicsList(List<Comic> li) {
		for (MainControllerListener l : listeners)
			l.setComicsList(li);
	}

	private void fireSetChaptersList(List<Chapter> li) {
		for (MainControllerListener l : listeners)
			l.setChaptersList(li);
	}

	private void fireSetPagesList(List<Page> li) {
		for (MainControllerListener l : listeners)
			l.setPagesList(li);
	}

	private void fireShowLoadingPane() {
		for (MainControllerListener l : listeners)
			l.showLoadingPane();
	}

	private void fireShowPopupMessage(String str, int type) {
		for (MainControllerListener l : listeners)
			l.showPopupMessage(str, type);
	}

	private void fireExitApp() {
		for (MainControllerListener l : listeners)
			l.exitApp();
	}

}
