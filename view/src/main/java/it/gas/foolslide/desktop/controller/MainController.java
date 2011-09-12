package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import java.util.LinkedList;
import java.util.List;

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
	 * Delete all the lists; download again the comics list; show the
	 * comics list;
	 */
	public void requestReset() {
		for (MainControllerListener l : listeners)
			l.showLoadingPane();
		engine.reset();
		List<Comic> list = engine.getComics();
		for (MainControllerListener l : listeners)
			l.setComicsList(list);
		showComics();
	}

	/**
	 * Change to the comics pane.
	 */
	public void showComics() {
		for (MainControllerListener l : listeners)
			l.showComicsPane();
	}

	/**
	 * Retrieve the chapters list and show it switching to the chapters pane.
	 * 
	 * @param comic
	 *            the comic to retrieve; if null, simply switch to the view.
	 */
	public void showChapters(Comic comic) {
		if (comic != null) {
			for (MainControllerListener l : listeners)
				l.showLoadingPane();
			List<Chapter> list = engine.getChapters(comic);		
			for (MainControllerListener l : listeners)				
				l.setChaptersList(list);
		}
		for (MainControllerListener l : listeners)
			l.showChaptersPane();
	}

	/**
	 * Retrieve the pages list and show it switching to the pages pane.
	 * 
	 * @param chapter the chapters from which retrieve the pages
	 */
	public void showPages(Chapter chapter) {
		for (MainControllerListener l : listeners) {
			List<Page> list = engine.getPages(chapter);
			l.setPagesList(list);
		}
		for (MainControllerListener l : listeners)
			l.showPagesPane();
	}

	/**
	 * Call when you want to close the app.
	 */
	public void requestExit() {
		for (MainControllerListener l : listeners)
			l.exitApp();
	}

}
