package it.gas.foolslide.desktop.engine;

import it.gas.foolslide.desktop.engine.persistence.Chapter;
import it.gas.foolslide.desktop.engine.persistence.Comic;
import it.gas.foolslide.desktop.engine.persistence.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Engine {
	private static Engine INSTANCE;
	private static Logger logger = LoggerFactory.getLogger(Engine.class);

	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	public static Engine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Engine();
		return INSTANCE;
	}

	private Engine() {
		initPersistence();
	}

	private void initPersistence() {
		emf = Persistence.createEntityManagerFactory("enginePU");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	/**
	 * Completely delete everything inside the DB.
	 */
	private void purgeDB() {
		Query query = em.createNamedQuery("delComics");
		int results = query.executeUpdate();
		logger.debug("Deleted " + results + " comics from the DB.");
		query = em.createNamedQuery("delChapters");
		results = query.executeUpdate();
		logger.debug("Deleted " + results + " chapters from the DB.");
		query = em.createNamedQuery("delPages");
		results = query.executeUpdate();
		logger.debug("Deleted " + results + " pages from the DB.");
	}

	private JSONObject retrieveComics() throws IOException, ParseException {
		URL url = new URL("http://foolrulez.org/slide/api/reader/comics");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		JSONObject json = (JSONObject) JSONValue.parseWithException(r);
		r.close();
		return json;
	}

	private JSONObject retrieveChapters(Comic c) throws IOException,
			ParseException {
		StringBuilder build = new StringBuilder();
		build.append("http://foolrulez.org/slide/api/reader/comic/id/");
		build.append(c.getId());
		URL url = new URL(build.toString());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		JSONObject json = (JSONObject) JSONValue.parseWithException(r);
		r.close();
		return json;
	}

	private JSONObject retrievePages(Chapter c) throws IOException,
			ParseException {
		StringBuilder build = new StringBuilder();
		build.append("http://foolrulez.org/slide/api/reader/chapter/id/");
		build.append(c.getId());
		URL url = new URL(build.toString());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		JSONObject json = (JSONObject) JSONValue.parseWithException(r);
		r.close();
		return json;
	}

	private void fillComics(JSONObject o) {
		JSONArray array = (JSONArray) o.get("comics");
		for (int i = 0; i < array.size(); i++) {
			JSONObject com = (JSONObject) array.get(i);
			Comic c = new Comic();
			c.setId(((Long) com.get("id")).intValue());
			c.setName((String) com.get("name"));
			c.setDescription((String) com.get("description"));
			c.setThumb_url((String) com.get("thumb_url"));
			em.persist(c);
		}
	}

	private void fillChapters(JSONObject o) {
		JSONArray chapters = (JSONArray) o.get("chapters");
		for (int i = 0; i < chapters.size(); i++) {
			JSONObject desc = (JSONObject) chapters.get(i);
			JSONObject cha = (JSONObject) desc.get("chapter");
			Chapter c = new Chapter();
			c.setId(((Long) cha.get("id")).intValue());
			c.setComic_id(Integer.parseInt((String) cha.get("comic_id")));
			c.setTeam_id(Integer.parseInt((String) cha.get("team_id")));
			c.setJoint_id(Integer.parseInt((String) cha.get("joint_id")));
			c.setChapter(Integer.parseInt((String) cha.get("chapter")));
			c.setSubchapter(Integer.parseInt((String) cha.get("subchapter")));
			c.setVolume(Integer.parseInt((String) cha.get("volume")));
			c.setLanguage((String) cha.get("language"));
			c.setName((String) cha.get("name"));
			em.persist(c);
		}
	}

	private void fillPages(JSONObject o) {
		JSONArray pages = (JSONArray) o.get("pages");
		for (int i = 0; i < pages.size(); i++) {
			JSONObject pag = (JSONObject) pages.get(i);
			Page p = new Page();
			p.setId(((Long) pag.get("id")).intValue());
			p.setChapter_id(((Long) pag.get("chapter_id")).intValue());
			p.setUrl((String) pag.get("url"));
			p.setSize((Long) pag.get("size"));
			p.setHeight(((Long) pag.get("height")).intValue());
			p.setWidth(((Long) pag.get("width")).intValue());
			p.setThumb_url((String) pag.get("thumb_url"));
			p.setThumbsize((Long) pag.get("thumbsize"));
			p.setThumbheight(((Long) pag.get("thumbheight")).intValue());
			p.setThumbwidth(((Long) pag.get("thumbwidth")).intValue());
			em.persist(p);
		}
	}

	/**
	 * Delete everything and download Comics and Chapters again.
	 */
	public void initialize() {
		try {
			// comics
			tx.begin();
			purgeDB();
			JSONObject o = retrieveComics();
			fillComics(o);
			tx.commit();
			// chapters
			List<Comic> l = getComics();
			tx.begin();
			for (int i = 0; i < l.size(); i++) {
				fillChapters(retrieveChapters(l.get(i)));
			}
			tx.commit();
		} catch (MalformedURLException e) {
			System.err.println("malformed");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("io");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("parse");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Comic> getComics() {
		TypedQuery<Comic> query = em.createNamedQuery("getComics", Comic.class);
		return query.getResultList();
	}

	public List<Chapter> getChapters(Comic c) {
		TypedQuery<Chapter> query = em.createNamedQuery("getChaptersById",
				Chapter.class);
		query.setParameter(1, c.getId());
		return query.getResultList();
	}

	public List<Page> getPages(Chapter c) {
		TypedQuery<Page> query = em
				.createNamedQuery("getPagesById", Page.class);
		query.setParameter(1, c.getId());
		List<Page> l = query.getResultList();
		if (l.size() != 0)
			return l;
		// download the list
		try {
			JSONObject o = retrievePages(c);
			fillPages(o);
			query = em.createNamedQuery("getPagesById", Page.class);
			query.setParameter(1, c.getId());
			l = query.getResultList();
		} catch (IOException e) {
			logger.debug("Can't download pages for chapter " + c.getId(), e);
		} catch (ParseException e) {
			logger.debug("Can't parse pages for chapter " + c.getId(), e);
		}
		// return the list, even empty this time
		return l;
	}

}
