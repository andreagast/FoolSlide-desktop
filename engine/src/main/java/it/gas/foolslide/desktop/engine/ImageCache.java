package it.gas.foolslide.desktop.engine;

import it.gas.foolslide.desktop.engine.persistence.Page;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class ImageCache {
	private static ImageCache INSTANCE;
	private HashMap<String, File> db;
	
	public ImageCache getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ImageCache();
		return INSTANCE;
	}
	
	private ImageCache() {
		db = new HashMap<String, File>();
	}
	
	public Image getForPage(Page p) throws IOException {
		File f = db.get(p.getUrl());
		if (f == null) {
			f = File.createTempFile("", "");
			FileUtils.copyURLToFile(new URL(p.getUrl()), f);
			db.put(p.getUrl(), f);
		}
		return ImageIO.read(f);
	}

}
