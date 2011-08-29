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
import javax.persistence.TypedQuery;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Engine {
	private static Engine INSTANCE;
	
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
	
	private void fillDB(JSONObject o) { 
		JSONArray array = (JSONArray) o.get("comics");
		for (int i = 0; i < array.size(); i++) {
			JSONObject com = (JSONObject) array.get(i);
			Comic c = new Comic();
			c.setId(Integer.parseInt((String) com.get("id")));
			c.setName((String) com.get("name"));
			c.setDescription((String) com.get("description"));
			c.setThumb_url((String) com.get("thumb_url"));
			em.persist(c);
		}
	}
	
	public void refresh() {
		//retrieve the comics
		URL url;
		try {
			url = new URL("http://foolrulez.org/slide/api/reader/comics");
			URLConnection conn = url.openConnection();
			conn.connect();
			Reader r = new InputStreamReader(conn.getInputStream());
			Object obj = JSONValue.parse(r);
			tx.begin();
			fillDB((JSONObject) obj);
			tx.commit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Comic> getComics() {
		TypedQuery<Comic> query = em.createNamedQuery("getAll", Comic.class);
		return query.getResultList();
	}
	
}
