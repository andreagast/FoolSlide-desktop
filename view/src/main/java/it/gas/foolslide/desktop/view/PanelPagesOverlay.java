package it.gas.foolslide.desktop.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.controller.PagesControllerListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class PanelPagesOverlay extends JPanel implements PagesControllerListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private MainController mController;
	private PagesController pController;
	
	private JButton btnChapters, btnPrev, btnNext;
	private JProgressBar loadBar;
	
	public PanelPagesOverlay(MainController m, PagesController p) {
		this.mController = m;
		this.pController = p;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(new FlowLayout());
		pnlBottom.setOpaque(true);
		pnlBottom.addMouseListener(this);
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnChapters = new JButton("Chapter's list");
		btnChapters.addMouseListener(this);
		pnlBottom.add(btnChapters);
		btnChapters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mController.showChapters(null);
				mController.showOverlay(false);
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnPrev = new JButton("<");
		btnPrev.addMouseListener(this);
		pnlBottom.add(btnPrev);
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pController.requestPrevPage();
			}
		});
		
		btnNext = new JButton(">");
		btnNext.addMouseListener(this);
		pnlBottom.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pController.requestNextPage();
			}
		});
		
		pnlBottom.add(new JSeparator(SwingConstants.VERTICAL));
		
		loadBar = new JProgressBar();
		loadBar.addMouseListener(this);
		loadBar.setIndeterminate(false);
		pnlBottom.add(loadBar);
		
		
	}

	public void setNextButtonEnabled(boolean b) {
		btnNext.setEnabled(b);
	}

	public void setPrevButtonEnabled(boolean b) {
		btnPrev.setEnabled(b);
	}

	public void setCurrentPageNumber(int n) {}

	public void setPageCountNumber(int n) {}

	public void setCurrentPageImage(Image i) {}

	public void setMessage(String str) {}

	public void setLoading(boolean b) {
		loadBar.setIndeterminate(b);
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent arg0) {
		mController.showOverlay(true);
	}

	public void mouseExited(MouseEvent arg0) {
		mController.showOverlay(false);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}
	
}
