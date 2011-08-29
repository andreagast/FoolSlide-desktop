package it.gas.foolslide.desktop.engine.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Chapter {
	@Id
	private int id;
	private int team_id;
	private int joint_id;
	private int chapter;
	private int subchapter;
	private int volume;
	private String language;
	private String name;
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

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

}
