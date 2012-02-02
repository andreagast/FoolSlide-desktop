package it.gas.foolslide.desktop.engine;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class ImageCache implements Runnable {
	private static ImageCache INSTANCE;
	
	private Map<String, File> db;
	private Logger logger;
	private Queue<String> workQueue;
	private Thread t;

	public static ImageCache getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ImageCache();
		return INSTANCE;
	}

	private ImageCache() {
		db = Collections.synchronizedMap(new HashMap<String, File>());
		//db = new HashMap<String, File>();
		logger = Logger.getLogger(ImageCache.class.getName());
		workQueue = new LinkedBlockingQueue<String>();
		//logger
		logger.fine("ImageCache initialized");
	}

	/** Add the URL to the preload queue. */
	public void preload(String s) {
		workQueue.add(s);
		if (t == null || ! t.isAlive()) {
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
		logger.fine("Request preload for: " + s);
	}
	
	/** Remove any pending file in the preload queue. */
	public void clear() {
		workQueue.clear();
		logger.fine("Preload queue cleared.");
	}
	
	/*public void preload(String s) {
		if (db.containsKey(s))
			return;
		try {
			File f = File.createTempFile("fool", "");
			FileUtils.copyURLToFile(new URL(s), f);
			db.put(s, f);
			logger.debug("Added " + s);
		} catch (IOException e) {
			logger.warn("Can't preload " + s, e);
		}
	}*/

	public Image get(String s) throws IOException {
		File f = db.get(s);
		if (f == null) {
			f = File.createTempFile("fool", "");
			FileUtils.copyURLToFile(new URL(s), f);
			db.put(s, f);
			logger.fine("Added " + s);
		}
		return ImageIO.read(f);
	}

	public void run() {
		logger.fine("ImageCache preloader started.");
		String tmpString;
		while (true) {
			//worker
			while (! workQueue.isEmpty()) {
				tmpString = workQueue.poll();
				if (! db.containsKey(tmpString)) {
					try {
						File f = File.createTempFile("fool", "");
						FileUtils.copyURLToFile(new URL(tmpString), f);
						db.put(tmpString, f);
						logger.fine("Preloaded " + tmpString);
					} catch (IOException e) {
						logger.warning("Can't preload " + tmpString);
					}
				}
			}
			//sleeping
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.warning("Problems with sleep()");
			}
		}
	}

}
