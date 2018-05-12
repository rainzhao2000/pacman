package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import pacman.Main.Codes;

public class DrawPanel extends JPanel implements ActionListener {

	private Codes[][] map = Main.map;

	private Timer timer;

	private int padding = Main.padding;
	private int tilePadWidth = Main.tilePadWidth;
	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;
	private int score;

	boolean paused;

	// private JLabel lblScore = Main.gameManager.lblScore;

	/*
	 * DrawPanel constructor sets paused and timer states
	 */
	public DrawPanel(boolean paused, int framerate) {
		this.paused = paused;
		score = 0;
		timer = new Timer(1000 / framerate, this);
		timer.start();
	}

	/*
	 * Repaints the panel at the conditions of the timer
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer && !paused) {
			Main.game.getScoreLabel().setText(Integer.toString(score));
			if (!Main.pacman.getState()) {
				if (Main.pacman.getLives() == 0) {
					// game over
					end();
				} else {
					respawn();
				}
			}
			repaint();
			// debug
			// print();
			// System.out.println(Main.pacman.getDirection());
		}
	}

	// debug
	void print() {
		Codes[][] map = Main.map;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == Codes.wall) {
					System.out.print("/");
				} else if (map[i][j] == Codes.pacdot) {
					System.out.print(".");
				} else if (map[i][j] == Codes.powerPellet) {
					System.out.print(",");
				} else if (map[i][j] == Codes.path) {
					System.out.print(" ");
				} else {
					if (Main.blinky.getrow() == i && Main.blinky.getcol() == j) {
						System.out.print("B");
					} else if (Main.pinky.getrow() == i && Main.pinky.getcol() == j) {
						System.out.print("P");
					} else if (Main.inky.getrow() == i && Main.inky.getcol() == j) {
						System.out.print("I");
					} else if (Main.clyde.getrow() == i && Main.clyde.getcol() == j) {
						System.out.print("C");
					} else if (Main.pacman.getrow() == i && Main.pacman.getcol() == j) {
						System.out.print("?");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	/*
	 * Handles all painting
	 */
	protected void paintComponent(Graphics g) {
		drawMap(g);
		drawGrid(g);
	}

	/*
	 * Temporary draw map method with simplified colors instead of sprites
	 */
	private void drawMap(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				try {
					if (Main.pacman.getrow() == row && Main.pacman.getcol() == col) {
						g.setColor(Color.YELLOW);
					} else if (Main.blinky.getrow() == row && Main.blinky.getcol() == col) {
						g.setColor(Color.RED);
					} else if (Main.pinky.getrow() == row && Main.pinky.getcol() == col) {
						g.setColor(Color.PINK);
					} else if (Main.inky.getrow() == row && Main.inky.getcol() == col) {
						g.setColor(Color.CYAN);
					} else if (Main.clyde.getrow() == row && Main.clyde.getcol() == col) {
						g.setColor(Color.ORANGE);
					} else {
						g.setColor(getColor(map[row][col]));
					}
				} catch (NullPointerException e) {
					blankMap();
					return;
				}
				int x = col * tilePadWidth;
				int y = row * tilePadWidth;
				g.fillRect(x, y, tilePadWidth, tilePadWidth);
			}
		}
	}

	/*
	 * Temporary color getter
	 */
	static Color getColor(Codes code) {
		switch (code) {
		case path:
			return Color.black;
		case wall:
			return Color.blue;
		case pacdot:
			return Color.lightGray;
		case powerPellet:
			return Color.white;
		case fruit:
			return Color.magenta;
		default:
			return new Color(150, 100, 100);
		}
	}

	/*
	 * Draw padding guides at every row and column (forming a visual grid)
	 */
	private void drawGrid(Graphics g) {
		// Draw horizontal guides
		g.setColor(Color.green);
		g.fillRect(0, 0, mapWidth, padding);
		for (int row = 1; row <= map.length; row++) {
			int y = row * tilePadWidth;
			g.fillRect(0, y, mapWidth, padding);
		}

		// Draw vertical guides
		g.fillRect(0, 0, padding, mapHeight);
		for (int col = 1; col <= map[0].length; col++) {
			int x = col * tilePadWidth;
			g.fillRect(x, 0, padding, mapHeight);
		}
	}

	/*
	 * Finds the element in map array corresponding to position of mouse cursor
	 * and sets the element to a specified code
	 */
	void setTile(MouseEvent e, Codes code) {
		int col = e.getX() / tilePadWidth;
		int row = e.getY() / tilePadWidth;
		map[row][col] = code;
	}

	/*
	 * Looks up an ArrayList of strings and adds the Codes objects obtained from
	 * the code values to the map array
	 */
	boolean uploadMap(ArrayList<String> lines) {
		try {
			for (int row = 0; row < map.length; row++) {
				String[] line = lines.get(row).split(" ");
				for (int col = 0; col < map[row].length; col++) {
					int tile = Integer.parseInt(line[col]);
					if (tile == Codes.pacman.getCode()) {
						// pacman
						Main.pacman.setRow(row);
						Main.pacman.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (tile == Codes.blinky.getCode()) {
						// blinky
						Main.blinky.setRow(row);
						Main.blinky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (tile == Codes.pinky.getCode()) {
						// pinky
						Main.pinky.setRow(row);
						Main.pinky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (tile == Codes.inky.getCode()) {
						// inky
						Main.inky.setRow(row);
						Main.inky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (tile == Codes.clyde.getCode()) {
						// clyde
						Main.clyde.setRow(row);
						Main.clyde.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else {
						map[row][col] = Codes.lookupByName(tile);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * Reset all elements in map array to path
	 */
	void blankMap() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = Codes.path;
			}
		}
	}

	void respawn() {
		// respawn all ghosts
		// respawn pacman
		// DO NOT respawn pacdots and power pellets
		Main.pacman.respawn();
		Main.blinky.respawn();
		Main.pinky.respawn();
		Main.inky.respawn();
		Main.clyde.respawn();
	}

	void end() {
		// display ending
		// go back to beginning
	}

	/*
	 * 
	 */
	void setGhostsEdible() {
		Main.blinky.changeState(true);
		Main.pinky.changeState(true);
		Main.inky.changeState(true);
		Main.clyde.changeState(true);
		// reset edible timer of each ghost
	}

	int getScore() {
		return score;
	}

	void setScore(int score) {
		this.score = score;
	}

	boolean getPaused() {
		return paused;
	}

}