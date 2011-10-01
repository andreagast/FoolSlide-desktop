package it.gas.foolslide.desktop;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.engine.Engine;
import it.gas.foolslide.desktop.engine.ImageCache;
import it.gas.foolslide.desktop.view.MainWindow;

public class MainApp extends SingleFrameApplication {
	private MainController mController;
	private PagesController pController;

	public static void main(String[] args) {
		Application.launch(MainApp.class, args);
	}

	@Override
	protected void initialize(String[] args) {
		mController = new MainController();
		pController = new PagesController();
		// initializing the engine
		Engine.getInstance();
		ImageCache.getInstance();
	}

	@Override
	protected void startup() {
		MainWindow window = new MainWindow(mController, pController);
		setMainFrame(window);
		mController.addListener(window);
		window.setVisible(true);
	}

	@Override
	protected void ready() {
		mController.requestReset();
	}

	@Override
	protected void shutdown() {
		super.shutdown();
		//System.out.println("Shutdown");
	}

}
