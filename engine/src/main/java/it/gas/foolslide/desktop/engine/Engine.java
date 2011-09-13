package it.gas.foolslide.desktop.engine;

import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
		logger.debug("Engine initialized");
	}

	private void initPersistence() {
		emf = Persistence.createEntityManagerFactory("enginePU");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	/**
	 * Completely delete everything inside the DB.
	 */
	public void reset() {
		tx.begin();
		Query query = em.createNamedQuery("delComics");
		query.executeUpdate();
		//int results = query.executeUpdate();
		//logger.debug("Deleted " + results + " comics from the DB.");
		query = em.createNamedQuery("delChapters");
		query.executeUpdate();
		//results = query.executeUpdate();
		//logger.debug("Deleted " + results + " chapters from the DB.");
		query = em.createNamedQuery("delPages");
		query.executeUpdate();
		//results = query.executeUpdate();
		//logger.debug("Deleted " + results + " pages from the DB.");
		tx.commit();
		logger.debug("DB reset complete.");
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
		tx.begin();
		for (int i = 0; i < array.size(); i++) {
			JSONObject com = (JSONObject) array.get(i);
			Comic c = new Comic();
			c.setId(((Long) com.get("id")).intValue());
			c.setName((String) com.get("name"));
			c.setDescription((String) com.get("description"));
			c.setThumb_url((String) com.get("thumb_url"));
			em.persist(c);
		}
		tx.commit();
	}

	private void fillChapters(JSONObject o) {
		JSONArray chapters = (JSONArray) o.get("chapters");
		tx.begin();
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
		tx.commit();
	}

	private void fillPages(JSONObject o) {
		JSONArray pages = (JSONArray) o.get("pages");
		tx.begin();
		for (int i = 0; i < pages.size(); i++) {
			JSONObject pag = (JSONObject) pages.get(i);
			Page p = new Page();
			p.setId(((Long) pag.get("id")).intValue());
			p.setChapter_id(Integer.parseInt((String) pag.get("chapter_id")));
			p.setFilename((String) pag.get("filename"));
			p.setUrl((String) pag.get("url"));
			p.setSize(Integer.parseInt((String) pag.get("size")));
			p.setHeight(Integer.parseInt((String) pag.get("height")));
			p.setWidth(Integer.parseInt((String) pag.get("width")));
			p.setThumb_url((String) pag.get("thumb_url"));
			p.setThumbsize(Long.parseLong((String) pag.get("thumbsize")));
			p.setThumbheight(Integer.parseInt((String) pag.get("thumbheight")));
			p.setThumbwidth(Integer.parseInt((String) pag.get("thumbwidth")));
			em.persist(p);
		}
		tx.commit();
	}

	public List<Comic> getComics() throws IOException, ParseException {
		TypedQuery<Comic> query = em.createNamedQuery("getComics", Comic.class);
		List<Comic> list = query.getResultList();
		if (list.size() == 0) {
			try {
				fillComics(retrieveComics());
				query = em.createNamedQuery("getComics", Comic.class);
				list = query.getResultList();
			} catch (IOException e) {
				logger.warn("Can't download comics list", e);
				throw e;
			} catch (ParseException e) {
				logger.warn("Can't parse comics list", e);
				throw e;
			}
		}
		return list;
	}

	public List<Chapter> getChapters(Comic c) throws IOException, ParseException {
		TypedQuery<Chapter> query = em.createNamedQuery("getChaptersById",
				Chapter.class);
		query.setParameter(1, c.getId());
		List<Chapter> list = query.getResultList();
		if (list.size() == 0) {
			try {
				fillChapters(retrieveChapters(c));
				query = em.createNamedQuery("getChaptersById", Chapter.class);
				query.setParameter(1, c.getId());
				list = query.getResultList();
			} catch (IOException e) {
				logger.warn("Can't download chapters for comic " + c.getId(), e);
				throw e;
			} catch (ParseException e) {
				logger.warn("Can't parse chapters for comic " + c.getId(), e);
				throw e;
			}
		}
		return list;
	}

	public List<Page> getPages(Chapter c) throws IOException, ParseException {
		TypedQuery<Page> query = em
				.createNamedQuery("getPagesById", Page.class);
		query.setParameter(1, c.getId());
		List<Page> l = query.getResultList();
		if (l.size() == 0) {
			// download the list
			try {
				fillPages(retrievePages(c));
				query = em.createNamedQuery("getPagesById", Page.class);
				query.setParameter(1, c.getId());
				l = query.getResultList();
			} catch (IOException e) {
				logger.debug("Can't download pages for chapter " + c.getId(), e);
				throw e;
			} catch (ParseException e) {
				logger.debug("Can't parse pages for chapter " + c.getId(), e);
				throw e;
			}
		}
		// return the list, even empty this time
		return l;
	}

}
