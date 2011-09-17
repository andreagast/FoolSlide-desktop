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
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName
		 * ()); } catch (ClassNotFoundException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } catch (InstantiationException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnsupportedLookAndFeelException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } MainController
		 * mController = new MainController(); PagesController pController = new
		 * PagesController(); final MainWindow window = new
		 * MainWindow(mController, pController);
		 * mController.addListener(window); SwingUtilities.invokeLater(new
		 * Runnable() {
		 * 
		 * @Override public void run() { window.setVisible(true); } });
		 * mController.requestReset();
		 */
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
