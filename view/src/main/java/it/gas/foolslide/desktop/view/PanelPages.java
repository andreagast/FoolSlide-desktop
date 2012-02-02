package it.gas.foolslide.desktop.view;

import it.gas.foolslide.desktop.controller.MainController;
import it.gas.foolslide.desktop.controller.PagesController;
import it.gas.foolslide.desktop.controller.PagesControllerListener;
import it.gas.foolslide.desktop.persistence.Page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class PanelPages extends AbstractMainPanel implements PagesControllerListener {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblImage;
	private JScrollPane scroll;
	
	private MainController mController;
	private PagesController pController;

	public PanelPages(MainController m, PagesController p) {
		this.mController = m;
		this.pController = p;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		lblImage = new JLabel("", JLabel.CENTER);
		lblImage.setBackground(Color.WHITE);
		lblImage.setOpaque(true);
		lblImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2)
					pController.requestNextPage();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				mController.showOverlay(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				mController.showOverlay(false);
			}
			
		});
		
		scroll = new JScrollPane(lblImage);
		//adjust the scroll increment
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		add(scroll);
	}

	public void setPagesList(List<Page> l) {
		pController.setPagesList(l);
	}

	public void setNextButtonEnabled(boolean b) {
		//btnNext.setEnabled(b);
	}

	public void setPrevButtonEnabled(boolean b) {
		//btnPrev.setEnabled(b);
	}

	public void setCurrentPageNumber(int n) {
		// TODO Auto-generated method stub
		
	}

	public void setPageCountNumber(int n) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentPageImage(Image i) {
		lblImage.setText("");
		lblImage.setIcon(new ImageIcon(i));
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}

	public void setMessage(String str) {
		lblImage.setText(str);
		lblImage.setIcon(null);
		scroll.getVerticalScrollBar().setValue(0);
		scroll.getHorizontalScrollBar().setValue(0);
	}

	public void setLoading(boolean b) {}
	
}
