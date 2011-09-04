package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.persistence.Chapter;
import it.gas.foolslide.desktop.engine.persistence.Comic;

public interface Switcher {
	
	public void showComics();
	public void showChapters(Comic c);
	public void showPages(Comic comic, Chapter chapter);

}
