package it.gas.foolslide.desktop.engine;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageCache {
	private static ImageCache INSTANCE;
	private HashMap<String, File> db;
	private Logger logger;

	public static ImageCache getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ImageCache();
		return INSTANCE;
	}

	private ImageCache() {
		logger = LoggerFactory.getLogger(ImageCache.class);
		db = new HashMap<String, File>();
		logger.debug("ImageCache initialized");
	}

	public void preload(String s) {
		File f = db.get(s);
		if (f != null)
			return;
		try {
			f = File.createTempFile("fool", "");
			FileUtils.copyURLToFile(new URL(s), f);
			db.put(s, f);
			logger.debug("Added " + s);
		} catch (IOException e) {
			logger.warn("Can't preload " + s, e);
		}
	}

	public Image get(String s) throws IOException {
		File f = db.get(s);
		if (f == null) {
			f = File.createTempFile("fool", "");
			FileUtils.copyURLToFile(new URL(s), f);
			db.put(s, f);
			logger.debug("Added " + s);
		}
		return ImageIO.read(f);
	}

}
