package pacman;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class DrawPanel extends JPanel implements ActionListener {

	private Code[][] map = Main.map;

	private Component parent;
	private Timer timer;

	private int framerate;
	private int score;

	private boolean paused;
	private boolean doDrawGrid;
	private boolean isFixed;

	/*
	 * DrawPanel constructor sets paused and timer states
	 */
	public DrawPanel(Component parent, boolean doDrawGrid, boolean isFixed, int framerate) {
		this.parent = parent;
		paused = false;
		this.doDrawGrid = doDrawGrid;
		this.isFixed = isFixed;
		this.framerate = framerate;
		reset(false);
		timer = new Timer(1000 / framerate, this);
		timer.start();
	}

	void reset(boolean newMap) {
		if (!isFixed) {
			// Game.canvas
			if (!newMap) {
				defaultMap();
			}
			initCharacters();
		} else {
			// MapCreator.canvas
			defaultMap();
		}
		score = 0;
		setPaused(false);
	}

	/*
	 * Repaints the panel at the conditions of the timer
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer && !paused) {
			Main.game.getScoreLabel().setText(Integer.toString(score));
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
		if (isFixed) {
			Main.tempPacman.draw(g);
			for (Ghost tempGhost : Main.tempGhosts) {
				tempGhost.draw(g);
			}
		} else {
			Main.pacman.draw(g);
			for (Ghost ghost : Main.ghosts) {
				ghost.draw(g);
			}
		}
	}

	/*
	 * Temporary draw map method with simplified colors instead of sprites
	 */
	private void drawMap(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				int x = col * Main.tilePadWidth;
				int y = row * Main.tilePadWidth;
				int width = Main.tilePadWidth;
				try {
					if (map[row][col] == Code.pacdot || map[row][col] == Code.powerPellet
							|| map[row][col] == Code.fruit) {
						g.setColor(getColor(Code.path));
						g.fillRect(x, y, width, width);
						if (map[row][col] == Code.pacdot) {
							width = Main.tileWidth / 4;
						} else {
							width = Main.tilePadWidth / 2;
						}
						x += (Main.tilePadWidth - width) / 2 + Main.padding;
						y += (Main.tilePadWidth - width) / 2 + Main.padding;
						g.setColor(getColor(map[row][col]));
						g.fillOval(x, y, width, width);
					} else {
						g.setColor(getColor(map[row][col]));
						g.fillRect(x, y, width, width);
					}
				} catch (NullPointerException e) {
					g.setColor(getColor(Code.path));
					g.fillRect(x, y, width, width);
				}
			}
		}
	}

	/*
	 * Temporary color getter
	 */
	private Color getColor(Code code) {
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
		case pacman:
			return Color.black;
		case blinky:
			return Color.black;
		case pinky:
			return Color.black;
		case inky:
			return Color.black;
		case clyde:
			return Color.black;
		default:
			return new Color(150, 100, 100);
		}
	}

	/*
	 * Draw Main.padding guides at every row and column (forming a visual grid)
	 */
	private void drawGrid(Graphics g) {
		// Draw horizontal guides
		g.setColor(Color.green);
		g.fillRect(0, 0, Main.mapWidth, Main.padding);
		for (int row = 1; row <= map.length; row++) {
			int y = row * Main.tilePadWidth;
			g.fillRect(0, y, Main.mapWidth, Main.padding);
		}

		// Draw vertical guides
		g.fillRect(0, 0, Main.padding, Main.mapHeight);
		for (int col = 1; col <= map[0].length; col++) {
			int x = col * Main.tilePadWidth;
			g.fillRect(x, 0, Main.padding, Main.mapHeight);
		}
	}

	/*
	 * Reset all elements in map array to path
	 */
	void blankMap() {
		Main.tempGhosts = new ArrayList<Ghost>();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = Code.path;
			}
		}
	}

	/*
	 * Looks for 'default map.txt' in current directory and uploads it to map
	 */
	void defaultMap() {
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
		if (!uploadMap(lines)) {
			JOptionPane.showMessageDialog(parent, "Invalid text file structure and/or codes", "Invalid Map",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * Looks up an ArrayList of strings and adds the Codes objects obtained from the
	 * code values to the map array
	 */
	boolean uploadMap(ArrayList<String> lines) {
		try {
			Main.tempGhosts = new ArrayList<Ghost>();
			for (int row = 0; row < map.length; row++) {
				String[] line = lines.get(row).split(" ");
				for (int col = 0; col < map[row].length; col++) {
					uploadTile(Code.lookupByValue(Integer.parseInt(line[col])), row, col);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void uploadTile(Code code, int row, int col) {
		removeOccupiedGhostSpawn(row, col);
		if (code == Code.pacman) {
			try {
				map[Main.tempPacman.getRow()][Main.tempPacman.getCol()] = Code.path;
			} catch (NullPointerException e) {
				System.out.println("tempPacman first init");
			}
			Main.tempPacman = new Pacman(this, Direction.left, row, col, true);
		} else if (code == Code.blinky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.red, true));
		} else if (code == Code.pinky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.pink, true));
		} else if (code == Code.inky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.cyan, true));
		} else if (code == Code.clyde) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.orange, true));
		}
		map[row][col] = code;
	}

	private void removeOccupiedGhostSpawn(int row, int col) {
		Iterator<Ghost> iter = Main.tempGhosts.iterator();
		while (iter.hasNext()) {
			Ghost tempGhost = iter.next();
			if (tempGhost.getRow() == row && tempGhost.getCol() == col)
				iter.remove();
		}
	}

	/*
	 * Finds the element in map array corresponding to position of mouse cursor and
	 * sets the element to a specified code
	 */
	void setTile(MouseEvent e, Code code) {
		int row = e.getY() / Main.tilePadWidth;
		int col = e.getX() / Main.tilePadWidth;
		if (inBounds(row, col)) {
			uploadTile(code, row, col);
		}
	}

	private void initCharacters() {
		Main.ghosts = new ArrayList<Ghost>();
		for (Ghost tempGhost : Main.tempGhosts) {
			Main.ghosts.add(new Ghost(this, Direction.left, tempGhost.getRow(), tempGhost.getCol(),
					tempGhost.getColor(), false));
		}
		Main.pacman = new Pacman(this, Direction.left, Main.tempPacman.getRow(), Main.tempPacman.getCol(), false);
	}

	void close() {
		timer.stop();
	}

	/*
	 * 
	 */
	void setGhostsEdible() {
		for (Ghost ghost : Main.ghosts) {
			ghost.setEdible(true);
			ghost.setSpeed(ghost.getSpeed() * 0.6);
		}
		// reset edible timer of each ghost
	}

	boolean inBounds(int row, int col) {
		return row >= 0 && row < map.length && col >= 0 && col < map[0].length;
	}

	int getFramerate() {
		return framerate;
	}

	int getScore() {
		return score;
	}

	void setScore(int score) {
		this.score = score;
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