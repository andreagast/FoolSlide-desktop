package it.gas.foolslide.desktop.view;

import it.gas.foolslide.desktop.persistence.Comic;

import java.util.List;

import javax.swing.JPanel;

import it.gas.foolslide.desktop.controller.MainControllerListener;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Page;

public abstract class AbstractMainPanel extends JPanel implements MainControllerListener {
	private static final long serialVersionUID = 1L;

	public void showComicsPane() {
	}

	public void showChaptersPane() {
	}

	public void showPagesPane() {	
	}

	public void showOverlay(boolean b) {	
	}

	public void setComicsList(List<Comic> l) {	
	}

	public void setChaptersList(List<Chapter> l) {	
	}

	public void setPagesList(List<Page> l) {	
	}

	public void showLoadingPane() {
	}

	public void showPopupMessage(String str, int type) {
	}

}
