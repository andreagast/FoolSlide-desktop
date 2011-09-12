package it.gas.foolslide.desktop;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.view.MainWindow;

public class MainApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainController mController = new MainController();
		PagesController pController = new PagesController();
		final MainWindow window = new MainWindow(mController, pController);
		mController.addListener(window);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.setVisible(true);
			}
		});
		//window.setVisible(true);
		mController.requestReset();
	}
	
}
