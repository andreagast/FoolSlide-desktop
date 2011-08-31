package it.gas.foolslide.desktop.engine.persistence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "getAll", query = "SELECT c FROM Comic c"),
		@NamedQuery(name = "deleteAll", query = "DELETE FROM Comic c")
})
public class Comic {
	@Id
	private int id;
	private String name;
	@Column(length = 5000)
	private String description;
	private String thumb_url;
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "comic_id")
	private List<Chapter> chapters;

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

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
