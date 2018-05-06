package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import pacman.Main.Codes;

public class DrawPanel extends JPanel implements ActionListener {

	boolean paused;
	private Timer timer;
	private Codes[][] map = Main.map;
	private int padding = Main.padding;
	private int tilePadWidth = Main.tilePadWidth;
	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;

	public DrawPanel(boolean paused, int framerate) {
		this.paused = paused;
		timer = new Timer(1000 / framerate, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer && !paused) {
			repaint();
		}
	}

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
					g.setColor(getColor(Codes.path));
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
		g.setColor(Color.green);
		g.fillRect(0, 0, mapWidth, padding);
		for (int row = 1; row <= map.length; row++) {
			int y = row * tilePadWidth;
			g.fillRect(0, y, mapWidth, padding);
		}
		g.fillRect(0, 0, padding, mapHeight);
		for (int col = 1; col <= map[0].length; col++) {
			int x = col * tilePadWidth;
			g.fillRect(x, 0, padding, mapHeight);
		}
	}

	/*
	 * Reset all values in map array to 0 (path)
	 */
	void blankMap() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = Codes.path;
			}
		}
	}
	
}