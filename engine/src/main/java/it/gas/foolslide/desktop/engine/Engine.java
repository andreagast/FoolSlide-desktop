package it.gas.foolslide.desktop.engine;

import it.gas.foolslide.desktop.engine.persistence.Comic;

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
	
	private void purgeDB() {
		Query query = em.createNamedQuery("deleteAll");
		int results = query.executeUpdate();
		logger.debug("Deleted " + results + " from the DB.");
	}
	
	private void fillDB(JSONObject o) {
		//System.out.println("fillDB()");
		JSONArray array = (JSONArray) o.get("comics");
		for (int i = 0; i < array.size(); i++) {
			JSONObject com = (JSONObject) array.get(i);
			//System.out.println("lol" + com);
			Comic c = new Comic();
			c.setId(((Long)com.get("id")).intValue());
			c.setName((String) com.get("name"));
			//System.out.println(com.containsKey("name"));
			//System.out.println(com.get("name"));
			c.setDescription((String) com.get("description"));
			c.setThumb_url((String) com.get("thumb_url"));
			em.persist(c);
		}
	}
	
	public void refresh() {
		//System.out.println("Start refreshing...");
		URL url;
		try {
			url = new URL("http://foolrulez.org/slide/api/reader/comics");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("accept", "json");
			//System.out.println("Connected.");
			Reader r = new InputStreamReader(conn.getInputStream());
			//System.out.println("Started parsing...");
			Object obj = JSONValue.parseWithException(r);
			//System.out.println("Parsed.");
			r.close();
			tx.begin();
			purgeDB();
			fillDB((JSONObject) obj);
			tx.commit();
			//System.out.println("Saved.");
			r.close();
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
		TypedQuery<Comic> query = em.createNamedQuery("getAll", Comic.class);
		return query.getResultList();
	}
	
}
