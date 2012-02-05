package it.gas.foolslide.desktop.engine;

import it.gas.foolslide.desktop.persistence.Comic;
import it.gas.foolslide.desktop.persistence.Chapter;
import it.gas.foolslide.desktop.persistence.Page;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class Engine {
	private static String BASE_URL = "http://foolrulez.org/slide";
	private static Engine INSTANCE;
	private static Logger logger = Logger.getLogger(Engine.class.getName());
	private List<Comic> comics;

	public static Engine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Engine();
		return INSTANCE;
	}

	private Engine() {
		reset();
		logger.fine("Engine initialized");
	}

	/**
	 * Completely delete everything inside the DB.
	 */
	public void reset() {
		comics = new ArrayList<Comic>();
		logger.fine("DB reset complete.");
	}

	private JsonNode retrieveComics() throws IOException {
		//System.out.println("Put that ass-crap!");
		URL url = new URL(BASE_URL + "/api/reader/comics/orderby/asc_name");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		/*Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));*/
		ObjectMapper mapper = new ObjectMapper();
		//JsonNode root = mapper.readValue(r, JsonNode.class);
		InputStream in = conn.getInputStream();
		JsonNode root = mapper.readValue(in, JsonNode.class);
		in.close();
		Iterator<JsonNode> iter = root.get("comics").getElements();
		while (iter.hasNext()) {
			comics.add(mapper.readValue(iter.next(), Comic.class));
		}
		//r.close();
		return root;
	}

	public List<Comic> getComics() throws IOException {
		if (comics.size() == 0) {
			try {
				retrieveComics();
			} catch (IOException e) {
				logger.warning("Can't download comics list\n" + e.getMessage());
				throw e;
			}
		}
		return comics;
	}

	public List<Chapter> getChapters(Comic c) throws IOException {
		if (c == null)
			throw new NullPointerException();
		List<Chapter> cList = new ArrayList<Chapter>();
		StringBuilder build = new StringBuilder();
		build.append(BASE_URL);
		build.append("/api/reader/comic/id/");
		build.append(c.getId());
		
		URL url = new URL(build.toString());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		/*Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));*/
		ObjectMapper mapper = new ObjectMapper();
		//JsonNode root = mapper.readValue(r, JsonNode.class);
		InputStream in = conn.getInputStream();
		JsonNode root = mapper.readValue(in, JsonNode.class);
		in.close();
		JsonNode chapters = root.get("chapters");
		Iterator<JsonNode> iter = chapters.getElements();
		while (iter.hasNext()) {
			JsonNode node = iter.next();
			cList.add(mapper.readValue(node.get("chapter"), Chapter.class));
		}
		Collections.sort(cList, Collections.reverseOrder());
		//r.close();
		return cList;
	}

	public List<Page> getPages(Chapter c) throws IOException {
		if (c == null)
			throw new NullPointerException();
		List<Page> pList = new ArrayList<Page>();
		StringBuilder build = new StringBuilder();
		build.append(BASE_URL);
		build.append("/api/reader/chapter/id/");
		build.append(c.getId());
		
		URL url = new URL(build.toString());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "json");
		/*Reader r = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));*/
		ObjectMapper mapper = new ObjectMapper();
		InputStream in = conn.getInputStream();
		//JsonNode root = mapper.readValue(r, JsonNode.class);
		JsonNode root = mapper.readValue(in, JsonNode.class);
		in.close();
		JsonNode pages = root.get("pages");
		Iterator<JsonNode> iter = pages.getElements();
		while (iter.hasNext()) {
			pList.add(mapper.readValue(iter.next(), Page.class));
		}
		//r.close();
		Collections.sort(pList);
		return pList;
	}

}
