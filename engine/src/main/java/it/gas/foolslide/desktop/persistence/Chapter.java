package it.gas.foolslide.desktop.persistence;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chapter implements Comparable<Chapter> {
	private int id;
	private int comic_id;
	private int team_id;
	private int joint_id;
	private int chapter;
	private int subchapter;
	private int volume;
	private String language;
	private String name;

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

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Vol. ");
		build.append(volume);
		build.append(" Chapter ");
		build.append(chapter);
		if (subchapter != 0) {
			build.append('.');
			build.append(getSubchapter());
		}
		build.append(": ");
		build.append(name);
		return build.toString();
	}

	@Override
	public int compareTo(Chapter o) {
		if (this.volume != o.volume)
			return this.volume - o.volume;
		if (this.chapter != o.chapter)
			return this.chapter - o.chapter;
		return this.subchapter - o.subchapter;
	}

}
