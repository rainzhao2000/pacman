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

	private Pacman pacman = Main.pacman;
	private Ghost blinky = Main.blinky;
	private Ghost pinky = Main.pinky;
	private Ghost inky = Main.inky;
	private Ghost clyde = Main.clyde;
	private Timer timer;

	private int tileWidth = Main.tileWidth;
	private int padding = Main.padding;
	private int tilePadWidth = Main.tilePadWidth;
	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;
	private int score;

	private boolean paused = false;
	private boolean doDrawGrid;

	/*
	 * DrawPanel constructor sets paused and timer states
	 */
	public DrawPanel(boolean doDrawGrid, int framerate) {
		this.doDrawGrid = doDrawGrid;
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
			/*
			 * if (!pacman.getVitality()) { if (pacman.getLives() == 0) { //
			 * game over end(); } else { respawn(); } }
			 */
			repaint();
		}
	}

	/*
	 * Handles all painting
	 */
	protected void paintComponent(Graphics g) {
		drawMap(g);
		if (doDrawGrid) {
			drawGrid(g);
		}
		pacman.draw(g);
		blinky.draw(g);
		pinky.draw(g);
		inky.draw(g);
		clyde.draw(g);
	}

	/*
	 * Temporary draw map method with simplified colors instead of sprites
	 */
	private void drawMap(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				int x = col * tilePadWidth;
				int y = row * tilePadWidth;
				int width = tilePadWidth;
				try {
					/*
					 * if (pacman.getrow() == row && pacman.getcol() == col) {
					 * g.setColor(Color.YELLOW); } else if (blinky.getrow() ==
					 * row && blinky.getcol() == col) { g.setColor(Color.RED); }
					 * else if (pinky.getrow() == row && pinky.getcol() == col)
					 * { g.setColor(Color.PINK); } else if (inky.getrow() == row
					 * && inky.getcol() == col) { g.setColor(Color.CYAN); } else
					 * if (clyde.getrow() == row && clyde.getcol() == col) {
					 * g.setColor(Color.ORANGE); } else { if (map[row][col] ==
					 * Codes.pacdot) { g.setColor(getColor(Codes.path));
					 * g.fillRect(x, y, width, width); width = tileWidth / 4; x
					 * += (tilePadWidth - width) / 2 + padding; y +=
					 * (tilePadWidth - width) / 2 + padding; }
					 * g.setColor(getColor(map[row][col])); }
					 */
					if (map[row][col] == Codes.pacdot) {
						g.setColor(getColor(Codes.path));
						g.fillRect(x, y, width, width);
						width = tileWidth / 4;
						x += (tilePadWidth - width) / 2 + padding;
						y += (tilePadWidth - width) / 2 + padding;
					}
					g.setColor(getColor(map[row][col]));
				} catch (NullPointerException e) {
					blankMap();
					return;
				}
				g.fillRect(x, y, width, width);
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
			return Color.white;
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
						pacman.setRow(row);
						pacman.setCol(col);
						map[row][col] = Codes.path;
					} else if (tile == Codes.blinky.getCode()) {
						// blinky
						blinky.setRow(row);
						blinky.setCol(col);
						map[row][col] = Codes.path;
					} else if (tile == Codes.pinky.getCode()) {
						// pinky
						pinky.setRow(row);
						pinky.setCol(col);
						map[row][col] = Codes.path;
					} else if (tile == Codes.inky.getCode()) {
						// inky
						inky.setRow(row);
						inky.setCol(col);
						map[row][col] = Codes.path;
					} else if (tile == Codes.clyde.getCode()) {
						// clyde
						clyde.setRow(row);
						clyde.setCol(col);
						map[row][col] = Codes.path;
					} else {
						map[row][col] = Codes.lookupByValue(tile);
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
		pacman.respawn();
		blinky.respawn();
		pinky.respawn();
		inky.respawn();
		clyde.respawn();
	}

	void end() {
		// display ending
		// go back to beginning
	}

	/*
	 * 
	 */
	void setGhostsEdible() {
		blinky.setEdible(true);
		pinky.setEdible(true);
		inky.setEdible(true);
		clyde.setEdible(true);
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

	void setPaused(boolean state) {
		paused = state;
	}

	boolean getDoDrawGrid() {
		return doDrawGrid;
	}

	void setDoDrawGrid(boolean state) {
		doDrawGrid = state;
	}

}