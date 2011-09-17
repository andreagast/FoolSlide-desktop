package it.gas.foolslide.desktop.controller;

import java.awt.Image;

public interface PagesControllerListener {
	
	public void setNextButtonEnabled(boolean b);
	public void setPrevButtonEnabled(boolean b);
	
	public void setCurrentPageNumber(int n);
	public void setPageCountNumber(int n);
	
	public void setCurrentPageImage(Image i);
	public void setMessage(String str);
	public void setLoading(boolean b);

}
