package pacman;

import java.awt.EventQueue;

public class Main {

	static enum Codes {
		path, wall, pacdot, powerPellet, fruit, pacman, blinky, pinky, inky, clyde
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
