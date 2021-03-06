package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.engine.ImageCache;
import it.gas.foolslide.desktop.persistence.Page;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class PagesController {
	private Logger log;
	private List<PagesControllerListener> listeners;
	private List<Page> pagesList;
	private int index;

	public PagesController() {
		log = Logger.getLogger(PagesController.class.getName());
		listeners = new LinkedList<PagesControllerListener>();
		pagesList = new ArrayList<Page>();
		index = 0;
	}

	public void addListener(PagesControllerListener l) {
		listeners.add(l);
	}

	public void removeListener(PagesControllerListener l) {
		listeners.remove(l);
	}

	public void setPagesList(List<Page> list) {
		if (list == null)
			return;
		pagesList = list;
		ImageCache.getInstance().clear();
		for (Page p : pagesList)
			ImageCache.getInstance().preload(p.getUrl());
		index = 0;
		try {
			Image m = ImageCache.getInstance().get(
					pagesList.get(index).getUrl());
			for (PagesControllerListener l : listeners) {
				l.setPrevButtonEnabled(false);
				if (pagesList.size() > 0)
					l.setNextButtonEnabled(true);
				else
					l.setNextButtonEnabled(false);
				l.setPageCountNumber(pagesList.size());
				if (pagesList.size() > 0)
					l.setCurrentPageNumber(index + 1);
				else
					l.setCurrentPageNumber(0);
				l.setCurrentPageImage(m);
			}
		} catch (IOException e) {
			for (PagesControllerListener l : listeners)
				l.setMessage(e.getMessage());
			log.warning("Error while retrieving the first image.");
		}

	}

	public void requestNextPage() {
		if (index == pagesList.size() - 1)
			return;
		index++;
		for (PagesControllerListener l : listeners) {
			l.setNextButtonEnabled(false);
			l.setPrevButtonEnabled(false);
			l.setLoading(true);
		}
		new RequestNextPageTask(Application.getInstance()).execute();
		/*
		 * for (PagesControllerListener l : listeners) { if (index ==
		 * pagesList.size() - 1) l.setNextButtonEnabled(false);
		 * l.setPrevButtonEnabled(true); l.setCurrentPageNumber(index + 1); try
		 * { l.setCurrentPageImage(ImageCache.getInstance().get(pagesList
		 * .get(index).getUrl())); log.debug("ID: " +
		 * pagesList.get(index).getId()); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */
	}

	public void requestPrevPage() {
		if (index == 0)
			return;
		index--;
		for (PagesControllerListener l : listeners) {
			l.setNextButtonEnabled(false);
			l.setPrevButtonEnabled(false);
			l.setLoading(true);
		}
		new RequestPrevPageTask(Application.getInstance()).execute();
		/*
		 * for (PagesControllerListener l : listeners) { if (index == 0)
		 * l.setPrevButtonEnabled(false); l.setNextButtonEnabled(true);
		 * l.setCurrentPageNumber(index + 1); try { l.setCurrentPageImage(
		 * ImageCache.getInstance().get(pagesList.get(index).getUrl()));
		 * log.debug("ID: " + pagesList.get(index).getId()); } catch
		 * (IOException e) { e.printStackTrace(); } }
		 */
	}

	private class RequestNextPageTask extends Task<Image, Void> {

		public RequestNextPageTask(Application application) {
			super(application);
		}

		@Override
		protected Image doInBackground() throws Exception {
			return ImageCache.getInstance().get(pagesList.get(index).getUrl());
		}

		@Override
		protected void succeeded(Image i) {
			for (PagesControllerListener l : listeners) {
				if (index < pagesList.size() - 1)
					l.setNextButtonEnabled(true);
				l.setPrevButtonEnabled(true);
				l.setCurrentPageNumber(index + 1);
				l.setCurrentPageImage(i);
				l.setLoading(false);
			}
			log.fine("ID: " + pagesList.get(index).getId());
		}

		@Override
		protected void failed(Throwable t) {
			log.warning("Exception while loading image.\n" + t.getMessage());
			for (PagesControllerListener l : listeners) {
				if (index < pagesList.size() - 1)
					l.setNextButtonEnabled(false);
				l.setPrevButtonEnabled(true);
				l.setCurrentPageNumber(index + 1);
				l.setMessage("Page not available.");
				l.setLoading(false);
			}
		}

	}

	private class RequestPrevPageTask extends Task<Image, Void> {

		public RequestPrevPageTask(Application application) {
			super(application);
		}

		@Override
		protected Image doInBackground() throws Exception {
			return ImageCache.getInstance().get(pagesList.get(index).getUrl());
		}

		@Override
		protected void succeeded(Image i) {
			for (PagesControllerListener l : listeners) {
				if (index > 0)
					l.setPrevButtonEnabled(true);
				l.setNextButtonEnabled(true);
				l.setCurrentPageNumber(index + 1);
				l.setCurrentPageImage(i);
				l.setLoading(false);
			}
			log.fine("ID: " + pagesList.get(index).getId());
		}

		@Override
		protected void failed(Throwable t) {
			log.warning("Exception while loading image.\n" + t.getMessage());
			for (PagesControllerListener l : listeners) {
				if (index > 0)
					l.setPrevButtonEnabled(true);
				l.setNextButtonEnabled(true);
				l.setCurrentPageNumber(index + 1);
				l.setMessage("Page not available.");
				l.setLoading(false);
			}
		}

	}
}
