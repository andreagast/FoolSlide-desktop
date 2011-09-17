package it.gas.foolslide.desktop.view;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.MainControllerListener;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

public class MainWindow extends JFrame implements MainControllerListener {
	private static final long serialVersionUID = 1L;
	private static final String LOADING = "loading";
	private static final String COMICS = "comics";
	private static final String CHAPTERS = "chapters";
	private static final String PAGES = "pages";
	
	private MainController mController;
	private PagesController pController;
	
	private CardLayout layout;
	
	public MainWindow(MainController c, PagesController p) {
		this.mController = c;
		this.pController = p;
		setTitle("FoOlSlide Desktop");
		initComponents();
		setSize(400, 300);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mController.requestExit();
			}
		});
	}
	
	private void initComponents() {
		layout = new CardLayout();
		setLayout(layout);
		
		PanelLoading pnlLoading = new PanelLoading();
		getContentPane().add(pnlLoading, LOADING);
		
		PanelComics pnlComics = new PanelComics(mController);
		mController.addListener(pnlComics);
		getContentPane().add(pnlComics, COMICS);
		
		PanelChapters pnlChapters = new PanelChapters(mController);
		mController.addListener(pnlChapters);
		getContentPane().add(pnlChapters, CHAPTERS);
		
		PanelPages pnlPages = new PanelPages(mController, pController);
		mController.addListener(pnlPages);
		pController.addListener(pnlPages);
		getContentPane().add(pnlPages, PAGES);
	}

	@Override
	public void showComicsPane() {
		layout.show(getContentPane(), COMICS);
	}

	@Override
	public void showChaptersPane() {
		layout.show(getContentPane(), CHAPTERS);
	}

	@Override
	public void showPagesPane() {
		layout.show(getContentPane(), PAGES);
	}

	@Override
	public void setComicsList(List<Comic> l) {}

	@Override
	public void setChaptersList(List<Chapter> l) {}

	@Override
	public void setPagesList(List<Page> l) {}

	@Override
	public void showLoadingPane() {
		layout.show(getContentPane(), LOADING);
	}
	
	@Override
	public void showPopupMessage(String str, int type) {
		JOptionPane.showMessageDialog(this, str, getTitle(), type);
	}
	
}
