package it.gas.foolslide.desktop.engine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.json.JSONTokener;

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
	
	public void refresh() {
		//retrieve the comics
		URL url;
		try {
			url = new URL("http://foolrulez.org/slide/api/reader/comics");
			URLConnection conn = url.openConnection();
			conn.connect();
			JSONTokener tokener = new JSONTokener(conn.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
