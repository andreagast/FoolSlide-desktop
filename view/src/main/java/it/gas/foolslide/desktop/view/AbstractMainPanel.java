package it.gas.foolslide.desktop.view;

import java.util.List;

import javax.swing.JPanel;

import it.gas.foolslide.desktop.controller.MainControllerListener;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

public abstract class AbstractMainPanel extends JPanel implements MainControllerListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void showComicsPane() {
	}

	@Override
	public void showChaptersPane() {
	}

	@Override
	public void showPagesPane() {	
	}

	@Override
	public void showOverlay(boolean b) {	
	}

	@Override
	public void setComicsList(List<Comic> l) {	
	}

	@Override
	public void setChaptersList(List<Chapter> l) {	
	}

	@Override
	public void setPagesList(List<Page> l) {	
	}

	@Override
	public void showLoadingPane() {
	}

	@Override
	public void showPopupMessage(String str, int type) {
	}

}
