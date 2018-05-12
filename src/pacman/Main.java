package pacman;

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
		static Codes lookupByName(int code) {
			return codeIndex.get(code);
		}

	}

	static Game game;

	static Codes[][] map = new Codes[31][28];

	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;
	static final int framerate = 60;

	static Pacman pacman = new Pacman(23, 14, 5, 1);
	static Ghost blinky = new Ghost(11, 14, 5);
	static Ghost pinky = new Ghost(14, 14, 5);
	static Ghost inky = new Ghost(14, 12, 5);
	static Ghost clyde = new Ghost(14, 16, 5);

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

	/*
	 * Looks for 'default map.txt' in current directory and uploads it to map
	 */
	static void defaultMap(Component parent, DrawPanel canvas) {
		// Generate file from path 'default map.txt'
		ArrayList<String> lines = new ArrayList<String>();
		File file = new File("default map.txt");
		BufferedReader reader = null;

		// Read each line as string and add to ArrayList of strings
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(parent, "'default map.txt' not found.", "File not found",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e1) {

			}
		}

		// upload ArrayList of strings to map array
		if (!canvas.uploadMap(lines)) {
			JOptionPane.showMessageDialog(parent, "Invalid text file structure and/or codes", "Invalid Map",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}