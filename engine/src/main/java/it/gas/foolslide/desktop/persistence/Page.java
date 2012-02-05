package it.gas.foolslide.desktop.persistence;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page implements Comparable<Page> {
	private int id;
	private int chapter_id;
	private String filename;
	// image of the page
	private String url;
	private long size;
	private int height;
	private int width;
	// thumbnail of the page
	private String thumb_url;
	private long thumbsize;
	private int thumbheight;
	private int thumbwidth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public long getThumbsize() {
		return thumbsize;
	}

	public void setThumbsize(long thumbsize) {
		this.thumbsize = thumbsize;
	}

	public int getThumbheight() {
		return thumbheight;
	}

	public void setThumbheight(int thumbheight) {
		this.thumbheight = thumbheight;
	}

	public int getThumbwidth() {
		return thumbwidth;
	}

	public void setThumbwidth(int thumbwidth) {
		this.thumbwidth = thumbwidth;
	}

	@Override
	public int compareTo(Page o) {
		return this.filename.compareTo(o.filename);
	}

}
