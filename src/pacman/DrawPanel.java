/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Date: June 4, 2018
 * Description: This class is the actual pacman game.
 */

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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class DrawPanel extends JPanel implements ActionListener {

	// declaring and/or initializing variables
	private Code[][] map = Main.map;

	private Component parent;
	private Timer frameTimer, respawnTimer;

	private int framerate;
	private int score;
	private int edibleOjectCounter, edibleObjectEaten;

	private boolean paused;
	private boolean doDrawGrid;
	private boolean isFixed;
	private boolean firstTime = true;

	// constructor
	public DrawPanel(Component parent, boolean paused, boolean doDrawGrid, boolean isFixed, int framerate) {
		// assigning values from parameter to local variables
		this.parent = parent;
		this.doDrawGrid = doDrawGrid;
		this.isFixed = isFixed;
		this.framerate = framerate;
		if (!isFixed) {
			fixMaps();
		}

		reset(false);
		this.paused = paused;
		this.edibleOjectCounter = 0;
		this.edibleObjectEaten = 0;

		frameTimer = new Timer(1000 / framerate, this);
		frameTimer.start();
	}

	// This method resets the entire pacman game
	void reset(boolean newMap) {
		resetMap(newMap);
		score = 0;
		try {
			Main.game.getSpeedMultiplierSpinner().setValue(1.0);
		} catch (NullPointerException e) {

		}
	}

	// This method resets the map and the characters of the pacman game
	private void resetMap(boolean newMap) {
		if (!isFixed) {
			initCharacters();
		}
		if (!newMap) {
			currentMap();
			if (!firstTime) {
				respawnCharacters(false);
			}
		}
		// set pacman lives
		Main.pacman.setLives(Main.pacmanLives);
		// scan portals
		Main.pacman.scanPortals();

		// scan portals
		for (Ghost g : Main.ghosts) {
			g.scanPortals();
		}

		// reset counter
		edibleOjectCounter = 0;
		edibleObjectEaten = 0;
		// rescan map
		scanMap();

		// resume the game
		paused = false;
	}

	// Repaints the panel at the conditions of the timer
	public void actionPerformed(ActionEvent e) {
		// if this is the first game, show pop up window for instructions
		if (!isFixed && firstTime) {
			int input = JOptionPane.showConfirmDialog(parent,
					"Instructions:\n\tarrow keys to move,\n\tpress P to pause/unpause\nStart game?",
					"Welcome to Pacman", JOptionPane.YES_NO_OPTION);
			if (input == JOptionPane.YES_OPTION) {
				paused = false;
				firstTime = false;
				scanMap();
			} else {
				System.exit(0);
			}
		}

		// if the game is paused
		if (paused) {
			informPaused();
		} else {
			clearLblPaused();
		}

		// if it is time to refresh and the game is not paused
		if (e.getSource() == frameTimer && !paused) {
			Main.game.getScoreLabel().setText(Integer.toString(score));
			Main.game.getCurrentLivesLabel().setText(Integer.toString(Main.pacman.getLives()));
			if (checkStagePass() && !isFixed) {
				nextStage(false);
			}
			repaint();
		} // if it the end of the game or the pacman died
		else if (e.getSource() == respawnTimer && !paused) {
			// if it is the end of the game
			if (Main.pacman.getLives() == 0) {
				// pause the game
				paused = true;
				// show the pop up message
				int input = JOptionPane.showConfirmDialog(parent, "Game over\nPlay again?", "Game Over",
						JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.YES_OPTION) {
					reset(false);
				} else {
					System.exit(0);
				}
			}

			// freeze the game for 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException exeption) {
				exeption.printStackTrace();
			}

			// restore the timer and the label
			respawnTimer.stop();
			clearLblDeath();

		}
	}

	// This method restores the map to the default map
	private void fixMaps() {
		defaultMap();
		updateCurrentMap(map);
	}

	// This method detects whether a stage is completed
	private boolean checkStagePass() {
		if (this.edibleOjectCounter == 0) {
			return false;
		} else if (this.edibleObjectEaten >= this.edibleOjectCounter) {
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					if (map[row][col] == Code.pacdot || map[row][col] == Code.powerPellet
							|| map[row][col] == Code.fruit) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	protected void incrementEdibleObjectEaten() {
		this.edibleObjectEaten++;
	}

	private void scanMap() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				if (map[row][col] == Code.pacdot || map[row][col] == Code.powerPellet || map[row][col] == Code.fruit) {
					this.edibleOjectCounter++;
				}
			}
		}
	}

	private boolean currentMapIsDefault() throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader("./default map.txt"));
		BufferedReader reader2 = new BufferedReader(new FileReader("./current map.txt"));
		String line1 = reader1.readLine();
		String line2 = reader2.readLine();
		boolean areEqual = true;
		while (line1 != null || line2 != null) {
			if (line1 == null || line2 == null) {
				areEqual = false;
				break;
			} else if (!line1.equalsIgnoreCase(line2)) {
				areEqual = false;
				break;
			}
			line1 = reader1.readLine();
			line2 = reader2.readLine();
		}
		reader1.close();
		reader2.close();
		if (areEqual) {
			return true;
		} else {
			return false;
		}
	}

	private void nextStage(boolean newMap) {
		int tempPacmanLives = Main.pacman.getLives();
		resetMap(newMap);
		Main.pacman.setLives(tempPacmanLives);
		Object value = Main.game.getSpeedMultiplierSpinner().getValue();
		Main.game.getSpeedMultiplierSpinner().setValue((double) value * 1.1);
		for (Ghost g : Main.ghosts) {
			g.edibleTime *= 0.9;
		}
	}

	public void respawnCharacters(boolean died) {
		Main.pacman.respawn();
		for (Ghost g : Main.ghosts) {
			g.respawn();
		}
		Main.pacman.doAnimate = false;
		if (died) {
			informDeath();
		}
		respawnTimer = new Timer(1 * 1000 / framerate, this);
		respawnTimer.start();
	}

	private void informDeath() {
		Main.game.getDeathLabel().setVisible(true);
	}

	private void clearLblDeath() {
		Main.game.getDeathLabel().setVisible(false);
	}

	private void informPaused() {
		Main.game.getPausedLabel().setVisible(true);
	}

	private void clearLblPaused() {
		Main.game.getPausedLabel().setVisible(false);
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
		case portal:
			return new Color(255, 100, 220);
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

	private void currentMap() {
		// Generate file from path 'current map.txt'
		ArrayList<String> lines = new ArrayList<String>();
		File file = new File("current map.txt");
		BufferedReader reader = null;

		// Read each line as string and add to ArrayList of strings
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e1) {
			fixMaps();
			currentMap();
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

	void updateCurrentMap(Code[][] map) {
		// Get the code values of each element in map array and add to
		// ArrayList of strings
		ArrayList<String> lines = new ArrayList<String>();
		for (int row = 0; row < map.length; row++) {
			StringBuilder line = new StringBuilder();
			for (int col = 0; col < map[row].length; col++) {
				line.append(map[row][col].getCode() + " ");
			}
			lines.add(line.toString());
		}

		// Write ArrayList of strings to file
		Path file = Paths.get("current map.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
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
			Main.tempPacman = new Pacman(this, Direction.down, row, col, true, 3);
		} else if (code == Code.blinky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.red, true, 1));
		} else if (code == Code.pinky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.pink, true, 0.75));
		} else if (code == Code.inky) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.cyan, true, 0.50));
		} else if (code == Code.clyde) {
			Main.tempGhosts.add(new Ghost(this, Direction.left, row, col, Color.orange, true, 0.25));
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
					tempGhost.getColor(), false, tempGhost.getProb()));
		}
		Main.pacman = new Pacman(this, Direction.down, Main.tempPacman.getRow(), Main.tempPacman.getCol(), false,
				Main.pacmanLives);
	}

	void close() {
		frameTimer.stop();
	}

	/*
	 * 
	 */
	void setGhostsEdible() {
		for (Ghost ghost : Main.ghosts) {
			ghost.setEdible(true);
			ghost.setSpeed(ghost.getStdSpeed() * 0.6);
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