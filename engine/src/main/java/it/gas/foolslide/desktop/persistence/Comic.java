package it.gas.foolslide.desktop.persistence;

import it.gas.foolslide.desktop.engine.ImageCache;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
		@NamedQuery(name = "getComics", query = "SELECT c FROM Comic c ORDER BY c.name"),
		@NamedQuery(name = "delComics", query = "DELETE FROM Comic c") })
public class Comic {
	@Id
	private int id;
	private String name;
	@Column(length = 5000)
	private String description;
	private String thumb_url;
	@Transient
	private Image thumb;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public Image getThumb() {
		try {
			if (thumb == null && thumb_url.compareTo("") != 0) {
				Image img = ImageCache.getInstance().get(getThumb_url());
				setThumb(img);
			}
		} catch (IOException e) {
			Logger.getLogger(Comic.class.getName()).warning(
					"Can't load the thumbnail for " + thumb_url);
		}
		return thumb;
	}

	public void setThumb(Image thumb) {
		this.thumb = thumb.getScaledInstance(-1, 125, Image.SCALE_FAST);
	}

	@Override
	public String toString() {
		return getName();
	}

}
