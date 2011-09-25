package it.gas.foolslide.desktop.controller;

import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import java.util.List;

public interface MainControllerListener {

	public void showComicsPane();
	public void showChaptersPane();
	public void showPagesPane();
	public void showOverlay(boolean b);
	
	public void setComicsList(List<Comic> l);
	public void setChaptersList(List<Chapter> l);
	public void setPagesList(List<Page> l);
	
	public void showLoadingPane();
	public void showPopupMessage(String str, int type);
	
}
