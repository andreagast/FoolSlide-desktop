package it.gas.foolslide.desktop.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
		@NamedQuery(name = "getChaptersById", query = "SELECT c FROM Chapter c WHERE c.comic_id = ?1 ORDER BY c.chapter DESC, c.subchapter DESC"),
		@NamedQuery(name = "delChapters", query = "DELETE FROM Chapter c") })
public class Chapter {
	@Id
	private int id;
	private int comic_id;
	private int team_id;
	private int joint_id;
	private int chapter;
	private int subchapter;
	private int volume;
	private String language;
	private String name;
	@Transient
	private boolean pagesGot;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComic_id() {
		return comic_id;
	}

	public void setComic_id(int comic_id) {
		this.comic_id = comic_id;
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

	public boolean isPagesGot() {
		return pagesGot;
	}

	public void setPagesGot(boolean pagesGot) {
		this.pagesGot = pagesGot;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(getChapter());
		if (getSubchapter() != 0) {
			build.append('.');
			build.append(getSubchapter());
		}
		build.append(": ");
		build.append(getName());
		return build.toString();
	}

}
