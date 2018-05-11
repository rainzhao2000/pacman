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

	int score;
	boolean paused;
	boolean edible;
	private Timer timer;
	private Codes[][] map = Main.map;
	private int padding = Main.padding;
	private int tilePadWidth = Main.tilePadWidth;
	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;

	// private JLabel lblScore = Main.gameManager.lblScore;

	/*
	 * DrawPanel constructor sets paused and timer states
	 */
	public DrawPanel(boolean paused, int framerate) {
		this.paused = paused;
		score = 0;
		edible = false;
		timer = new Timer(1000 / framerate, this);
		timer.start();
	}

	/*
	 * Repaints the panel at the conditions of the timer
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer && !paused) {
			Main.gameManager.lblScore.setText(Integer.toString(score));
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

	void respawn() {
		// respawn all ghosts
		// respawn pacman
		// DO NOT respawn pacdots and power pellets
		Main.blinky.respawn();
		Main.pinky.respawn();
		Main.inky.respawn();
		Main.clyde.respawn();
		Main.pacman.respawn();
	}

	void end() {
		// display ending
		// go back to beginning
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
				int x = col * tilePadWidth;
				int y = row * tilePadWidth;
				try {
					if (Main.blinky.getrow() == row && Main.blinky.getcol() == col) {
						g.setColor(Color.RED);
					} else if (Main.pinky.getrow() == row && Main.pinky.getcol() == col) {
						g.setColor(Color.PINK);
					} else if (Main.inky.getrow() == row && Main.inky.getcol() == col) {
						g.setColor(Color.CYAN);
					} else if (Main.clyde.getrow() == row && Main.clyde.getcol() == col) {
						g.setColor(Color.ORANGE);
					} else if (Main.pacman.getrow() == row && Main.pacman.getcol() == col) {
						g.setColor(Color.YELLOW);
					} else {
						g.setColor(getColor(map[row][col]));
					}
				} catch (NullPointerException e) {
					blankMap();
					return;
				}
				g.fillRect(x, y, tilePadWidth, tilePadWidth);
			}
		}
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
	 * Finds the element in map array corresponding to position of mouse cursor and
	 * sets the element to a specified code
	 */
	void setTile(MouseEvent e, Codes code) {
		int col = e.getX() / tilePadWidth;
		int row = e.getY() / tilePadWidth;
		map[row][col] = code;
	}

	/*
	 * Looks up an ArrayList of strings and adds the Codes objects obtained from the
	 * code values to the map array
	 */
	boolean uploadMap(ArrayList<String> lines) {
		try {
			for (int row = 0; row < map.length; row++) {
				String[] line = lines.get(row).split(" ");
				for (int col = 0; col < map[row].length; col++) {
					int index = Integer.parseInt(line[col]);
					if (index == 5) {
						// pacman
						Main.pacman.setRow(row);
						Main.pacman.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (index == 6) {
						// blinky
						Main.blinky.setRow(row);
						Main.blinky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (index == 7) {
						// pinky
						Main.pinky.setRow(row);
						Main.pinky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (index == 8) {
						// inky
						Main.inky.setRow(row);
						Main.inky.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else if (index == 9) {
						// clyde
						Main.clyde.setRow(row);
						Main.clyde.setCol(col);
						map[row][col] = Codes.lookupByName(0);
					} else {
						map[row][col] = Codes.lookupByName(index);
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

}