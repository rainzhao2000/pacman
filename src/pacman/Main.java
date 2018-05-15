package pacman;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

public class Main {

	/*
	 * Enumerator handles codes of elements that will be referenced from map
	 * array
	 */
	static enum Code {

		path(0), wall(1), pacdot(2), powerPellet(3), fruit(4), pacman(5), blinky(6), pinky(7), inky(8), clyde(9);

		private final int code;
		private static final Map<Integer, Code> codeIndex = new HashMap<Integer, Code>();

		/*
		 * Make a HashMap to hold references to Codes objects by reference of
		 * their values
		 */
		static {
			for (Code code : Code.values()) {
				codeIndex.put(code.getCode(), code);
			}
		}

		/*
		 * Enum constructor
		 */
		Code(int code) {
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
		static Code lookupByValue(int code) {
			return codeIndex.get(code);
		}

	}

	static enum Direction {

		left(1), right(2), up(3), down(4);

		private final int dir;
		private static final Map<Integer, Direction> dirIndex = new HashMap<Integer, Direction>();

		static {
			for (Direction dir : Direction.values()) {
				dirIndex.put(dir.getDir(), dir);
			}
		}

		Direction(int dir) {
			this.dir = dir;
		}

		int getDir() {
			return dir;
		}

		static Direction lookupByValue(int dir) {
			return dirIndex.get(dir);
		}

	}

	static Code[][] map = new Code[31][28];

	static Game game;

	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;
	static final int framerate = 120;

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