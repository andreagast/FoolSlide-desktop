package it.gas.foolslide.desktop.engine.persistence;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comics {
	@Id
	private int id;
	private String name;
	private String stub;
	private String uniqid;
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
	private String thumb_url;

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

	public String getStub() {
		return stub;
	}

	public void setStub(String stub) {
		this.stub = stub;
	}

	public String getUniqid() {
		return uniqid;
	}

	public void setUniqid(String uniqid) {
		this.uniqid = uniqid;
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

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

}
