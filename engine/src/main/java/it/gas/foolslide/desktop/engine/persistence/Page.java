package it.gas.foolslide.desktop.engine.persistence;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Page {
	@Id
	private int id;
	// chapter_id
	private String filename;
	private boolean hidden;
	private String description;
	private String thumbnail;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp created;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastseen;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp updated;
	private int creator;
	private int editor;
	private int height;
	private int width;
	private String mime;
	private boolean grayscale;
	private int thumbheight;
	private int thumbwidth;
	private long size;
	private long thumbsize;
	private String url;
	private String thumb_url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastseen() {
		return lastseen;
	}

	public void setLastseen(Timestamp lastseen) {
		this.lastseen = lastseen;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getEditor() {
		return editor;
	}

	public void setEditor(int editor) {
		this.editor = editor;
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

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public boolean isGrayscale() {
		return grayscale;
	}

	public void setGrayscale(boolean grayscale) {
		this.grayscale = grayscale;
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

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getThumbsize() {
		return thumbsize;
	}

	public void setThumbsize(long thumbsize) {
		this.thumbsize = thumbsize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

}
