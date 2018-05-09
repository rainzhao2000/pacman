package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
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
			repaint();
		}
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
					g.setColor(getColor(map[row][col]));
				} catch (NullPointerException e) {
					blankMap();
					return;
				}
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
		case pacman:
			return Color.yellow;
		case blinky:
			return Color.red;
		case pinky:
			return Color.pink;
		case inky:
			return Color.cyan;
		case clyde:
			return Color.orange;
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
					map[row][col] = Codes.lookupByName(Integer.parseInt(line[col]));
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