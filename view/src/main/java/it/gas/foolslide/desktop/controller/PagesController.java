package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.engine.ImageCache;
import it.gas.foolslide.desktop.persistence.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagesController {
	private Logger log;
	private List<PagesControllerListener> listeners;
	
	private List<Page> pagesList;
	private int index;
	
	public PagesController() {
		log = LoggerFactory.getLogger(PagesController.class);
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
		pagesList = list;
		index = 0;
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
			//TODO
			try {
				l.setCurrentPageImage(ImageCache.getInstance().get(pagesList.get(index).getUrl()));
				log.debug("ID: " + pagesList.get(index).getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void requestNextPage() {
		if (index == pagesList.size() - 1)
			return;
		index++;
		for (PagesControllerListener l : listeners) {
			if (index == pagesList.size() - 1)
				l.setNextButtonEnabled(false);
			l.setPrevButtonEnabled(true);
			l.setCurrentPageNumber(index + 1);
			//TODO
			try {
				l.setCurrentPageImage(ImageCache.getInstance().get(pagesList.get(index).getUrl()));
				log.debug("ID: " + pagesList.get(index).getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void requestPrevPage() {
		if (index == 0)
			return;
		index--;
		for (PagesControllerListener l : listeners) {
			if (index == 0)
				l.setPrevButtonEnabled(false);
			l.setNextButtonEnabled(true);
			l.setCurrentPageNumber(index + 1);
			//TODO
			try {
				l.setCurrentPageImage(ImageCache.getInstance().get(pagesList.get(index).getUrl()));
				log.debug("ID: " + pagesList.get(index).getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
