package pacman;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

public class Main {

	static enum Codes {

		path("0"), wall("1"), pacdot("2"), powerPellet("3"), fruit("4"), pacman("5"), blinky("6"), pinky("7"), inky(
				"8"), clyde("9");

		private final String code;
		private static final Map<String, Codes> codeIndex = new HashMap<String, Codes>();

		static {
			for (Codes code : Codes.values()) {
				codeIndex.put(code.getCode(), code);
			}
		}

		Codes(String code) {
			this.code = code;
		}

		String getCode() {
			return code;
		}

		static Codes lookupByName(String name) {
			return codeIndex.get(name);
		}

	}

	static GameManager gameManager;
	static final int framerate = 60;
	static Codes[][] map = new Codes[31][28];
	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameManager = new GameManager();
					gameManager.frmPacman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}