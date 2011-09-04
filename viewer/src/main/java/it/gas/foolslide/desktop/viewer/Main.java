package it.gas.foolslide.desktop.viewer;

import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.ImageCache;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		//system-ui
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		//initialize all the singleton classes
		Engine.getInstance();
		ImageCache.getInstance();
		logger.debug("All singleton initialized!");
		//start the graphics
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}
	
}
