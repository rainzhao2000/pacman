package pacman;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Main {

	/*
	 * Enumerator handles codes of elements that will be referenced from map
	 * array
	 */
	static enum Codes {

		path(0), wall(1), pacdot(2), powerPellet(3), fruit(4), pacman(5), blinky(6), pinky(7), inky(8), clyde(9);

		private final int code;
		private static final Map<Integer, Codes> codeIndex = new HashMap<Integer, Codes>();

		/*
		 * Make a HashMap to hold references to Codes objects by reference of
		 * their values
		 */
		static {
			for (Codes code : Codes.values()) {
				codeIndex.put(code.getCode(), code);
			}
		}

		/*
		 * Enum constructor
		 */
		Codes(int code) {
			this.code = code;
		}

		/*
		 * Returns the code value of a Codes object
		 */
		int getCode() {
			return code;
		}

		/*
		 * Returns the Codes object of a code value
		 */
		static Codes lookupByValue(int code) {
			return codeIndex.get(code);
		}

	}

	static enum Directions {

		left(1), right(2), up(3), down(4);

		private final int dir;
		private static final Map<Integer, Directions> dirIndex = new HashMap<Integer, Directions>();

		static {
			for (Directions dir : Directions.values()) {
				dirIndex.put(dir.getDir(), dir);
			}
		}

		Directions(int dir) {
			this.dir = dir;
		}

		int getDir() {
			return dir;
		}

		static Directions lookupByValue(int dir) {
			return dirIndex.get(dir);
		}

	}

	static Codes[][] map = new Codes[31][28];

	static Game game;

	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;
	static final int framerate = 60;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					game = new Game();
					game.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}