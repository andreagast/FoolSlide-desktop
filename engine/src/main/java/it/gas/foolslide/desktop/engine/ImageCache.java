package it.gas.foolslide.desktop.engine;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		//db = Collections.synchronizedMap(new HashMap<String, File>());
		db = new HashMap<String, File>();
		logger = LoggerFactory.getLogger(ImageCache.class);
		workQueue = new LinkedBlockingQueue<String>();
		//logger
		logger.debug("ImageCache initialized");
	}

	/** Add the URL to the preload queue. */
	public void preload(String s) {
		workQueue.add(s);
		if (t == null || ! t.isAlive()) {
			t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
		logger.debug("Request preload for: " + s);
	}
	
	/** Remove any pending file in the preload queue. */
	public void clear() {
		workQueue.clear();
		logger.debug("Preload queue cleared.");
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
			logger.debug("Added " + s);
		}
		return ImageIO.read(f);
	}

	@Override
	public void run() {
		logger.debug("ImageCache preloader started.");
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
						logger.debug("Preloaded " + tmpString);
					} catch (IOException e) {
						logger.warn("Can't preload " + tmpString, e);
					}
				}
			}
			//sleeping
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.warn("Problems with sleep()", e);
			}
		}
	}

}
