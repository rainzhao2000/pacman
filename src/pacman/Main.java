/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Date: June 4, 2018
 * Description: This class is the Main class that stores the main components of the game, it contains an instance 
 * 				of game, pacman, ghosts, and other useful things. Also, the enum that is used throughout the 
 * 				program is also written in this class.
 */

package pacman;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	/*
	 * Enumerator handles codes of elements that will be referenced from map array
	 */
	static enum Code {

		path(0), wall(1), portal(2), pacdot(3), powerPellet(4), fruit(5), pacman(6), blinky(7), pinky(8), inky(
				9), clyde(10);

		private final int code;
		private static final Map<Integer, Code> codeIndex = new HashMap<Integer, Code>();

		/*
		 * Make a HashMap to hold references to Codes objects by reference of their
		 * values
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

	/*
	 * Direction enum handles all directions
	 */
	static enum Direction {

		left(1), right(2), up(3), down(4);

		private final int dir;
		private static final Map<Integer, Direction> dirIndex = new HashMap<Integer, Direction>();

		/*
		 * Make a HashMap to hold references to Codes objects by reference of their
		 * values
		 */
		static {
			for (Direction dir : Direction.values()) {
				dirIndex.put(dir.getDir(), dir);
			}
		}

		/*
		 * constructor
		 */
		Direction(int dir) {
			this.dir = dir;
		}

		/*
		 * Returns the code value of a Codes object
		 */
		int getDir() {
			return dir;
		}

		/*
		 * Returns the Codes object of a code value
		 */
		static Direction lookupByValue(int dir) {
			return dirIndex.get(dir);
		}

	}

	// declaration and/or initialization of static elements

	static Code[][] map = new Code[31][28];

	static Game game;
	static Pacman pacman;
	static Pacman tempPacman;
	static ArrayList<Ghost> ghosts;
	static ArrayList<Ghost> tempGhosts;
	static int pacmanLives = 3;

	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;
	static final int framerate = 240;

	// main method created with WindowBuilder
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// creates instance of Game
					game = new Game();
					game.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}