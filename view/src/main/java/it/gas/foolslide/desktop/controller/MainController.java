package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
	private List<MainControllerListener> listeners;
	private Engine engine;
	private Logger logger;

	public MainController() {
		listeners = new LinkedList<MainControllerListener>();
		engine = Engine.getInstance();
		logger = LoggerFactory.getLogger(this.getClass());
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
		fireShowLoadingPane();
		/*new Thread(new Runnable() {
			public void run() {
				try {
					engine.reset();
					List<Comic> list = engine.getComics();
					fireSetComicsList(list);
					fireShowComicsPane();
				} catch (Exception e) {
					fireShowPopupMessage("Can't download comics list.",
							JOptionPane.ERROR_MESSAGE);
					requestExit();
				}
			}
		}).run();*/
		new ResetTask(Application.getInstance()).execute();
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
	public void showChapters(Comic comic) {
		fireShowLoadingPane();
		if (comic != null) {
			
			/*new Thread(new Runnable() {
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
			}).run();*/
			new ShowChaptersTask(Application.getInstance(), comic).execute();
		} else {
			fireShowChaptersPane();
		}
		
	}

	/**
	 * Retrieve the pages list and show it switching to the pages pane.
	 * 
	 * @param chapter
	 *            the chapters from which retrieve the pages; if null do nothing
	 */
	public void showPages(Chapter chapter) {
		if (chapter == null)
			return;
		fireShowLoadingPane();
		/*new Thread(new Runnable() {
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
		}).run();*/
		new ShowPagesTask(Application.getInstance(), chapter).execute();
	}

	/**
	 * Call when you want to close the app.
	 */
	public void requestExit() {
		Application.getInstance().exit();
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
	
	private class ResetTask extends Task<Void, Void> {

		public ResetTask(Application application) {
			super(application);
		}

		@Override
		protected Void doInBackground() throws Exception {
			engine.reset();
			List<Comic> list = engine.getComics();
			fireSetComicsList(list);
			return null;
		}
		
		@Override
		protected void succeeded(Void l) {
			//fireSetComicsList(l);
			fireShowComicsPane();
		}
		
		@Override
		protected void failed(Throwable t) {
			logger.warn("Can't download comics list.", t);
			fireShowPopupMessage("Can't download comics list.",
					JOptionPane.ERROR_MESSAGE);
			requestExit();
		}
		
	}

	private class ShowChaptersTask extends Task<Void, Void> {
		private Comic comic;

		public ShowChaptersTask(Application application, Comic c) {
			super(application);
			this.comic = c;
		}

		@Override
		protected Void doInBackground() throws Exception {
			List<Chapter> list = engine.getChapters(comic);
			fireSetChaptersList(list);
			return null;
		}
		
		@Override
		protected void succeeded(Void l) {
			//fireSetChaptersList(l);
			fireShowChaptersPane();
		}
		
		@Override
		protected void failed(Throwable t) {
			logger.warn("Can't download chapters list.", t);
			fireShowPopupMessage("Can't download chapters list.",
					JOptionPane.ERROR_MESSAGE);
			fireShowComicsPane();
		}
		
	}

	private class ShowPagesTask extends Task<Void, Void> {
		private Chapter chapter;
		
		public ShowPagesTask(Application application, Chapter c) {
			super(application);
			this.chapter = c;
		}

		@Override
		protected Void doInBackground() throws Exception {
			List<Page> list = engine.getPages(chapter);
			fireSetPagesList(list);
			return null;
		}
		
		@Override
		protected void succeeded(Void l) {
			//fireSetPagesList(l);
			fireShowPagesPane();
		}
		
		@Override
		protected void failed(Throwable t) {
			logger.warn("Can't download pages list.", t);
			fireShowPopupMessage("Can't download pages list.",
					JOptionPane.ERROR_MESSAGE);
			fireShowChaptersPane();
		}
	}
	
}
