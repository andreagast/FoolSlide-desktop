package it.gas.foolslide.desktop.engine.persistence;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Chapter {
	@Id
	private int id;
	// comic_id
	private int team_id;
	private int joint_id;
	private int chapter;
	private int subchapter;
	private int volume;
	private String language;
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
	
	//not part of the json fields
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "chapter_id")
	private List<Page> pages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getJoint_id() {
		return joint_id;
	}

	public void setJoint_id(int joint_id) {
		this.joint_id = joint_id;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getSubchapter() {
		return subchapter;
	}

	public void setSubchapter(int subchapter) {
		this.subchapter = subchapter;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

}
