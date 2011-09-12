package it.gas.foolslide.desktop.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.MainControllerListener;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.controller.PagesControllerListener;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class PanelPages extends JPanel implements MainControllerListener, PagesControllerListener {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblImage;
	private JScrollPane scroll;
	private JButton btnChapters, btnPrev, btnNext;
	
	private MainController mController;
	private PagesController pController;

	public PanelPages(MainController mController, PagesController pController) {
		this.mController = mController;
		this.pController = pController;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		lblImage = new JLabel("", JLabel.CENTER);
		lblImage.setBackground(Color.WHITE);
		lblImage.setOpaque(true);
		scroll = new JScrollPane(lblImage);
		add(scroll);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(new FlowLayout());
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnChapters = new JButton("Chapter's list");
		pnlBottom.add(btnChapters);
		btnChapters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.showChapters(null);
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnPrev = new JButton("<");
		pnlBottom.add(btnPrev);
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pController.requestPrevPage();
			}
		});
		
		btnNext = new JButton(">");
		pnlBottom.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pController.requestNextPage();
			}
		});
	}

	@Override
	public void showComicsPane() {}

	@Override
	public void showChaptersPane() {}

	@Override
	public void showPagesPane() {}

	@Override
	public void setComicsList(List<Comic> l) {}

	@Override
	public void setChaptersList(List<Chapter> l) {}

	@Override
	public void setPagesList(List<Page> l) {
		pController.setPagesList(l);
	}

	@Override
	public void showLoadingPane() {}

	@Override
	public void exitApp() {}

	@Override
	public void setNextButtonEnabled(boolean b) {
		btnNext.setEnabled(b);
	}

	@Override
	public void setPrevButtonEnabled(boolean b) {
		btnPrev.setEnabled(b);
	}

	@Override
	public void setCurrentPageNumber(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPageCountNumber(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentPageImage(Image i) {
		lblImage.setText("");
		lblImage.setIcon(new ImageIcon(i));
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}

	@Override
	public void setMessage(String str) {
		lblImage.setText(str);
		lblImage.setIcon(null);
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}
	
}
