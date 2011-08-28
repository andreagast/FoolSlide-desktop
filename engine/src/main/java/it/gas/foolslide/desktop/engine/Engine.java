package it.gas.foolslide.desktop.engine;

public class Engine {
	private static Engine INSTANCE;
	
	public static Engine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Engine();
		return INSTANCE;
	}
	
}
