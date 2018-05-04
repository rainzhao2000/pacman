package pacman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

	int framerate = 60;
	int[][] map = GameManager.map;
	int tileWidth = GameManager.tileWidth;
	int padding = GameManager.padding;
	int tilePadWidth = GameManager.tilePadWidth;
	int mapWidth = GameManager.mapWidth;
	int mapHeight = GameManager.mapHeight;

	/*
	 * Framerate dictates the delay between paint refreshes as this method gets
	 * called from repaint().
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Thread.sleep(1000 / framerate); // milliseconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		drawMap(g);
		drawGrid(g);
	}

	/*
	 * Temporary draw map method with simplified colors instead of sprites
	 */
	public void drawMap(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				int x = col * tilePadWidth;
				int y = row * tilePadWidth;
				g.setColor(getColor(map[row][col]));
				g.fillRect(x, y, tilePadWidth, tilePadWidth);
			}
		}
	}

	/*
	 * Temporary color getter
	 */
	public Color getColor(int code) {
		switch (code) {
		case GameManager.pathCode:
			return Color.black;
		case GameManager.wallCode:
			return Color.blue;
		case GameManager.pacdotCode:
			return Color.lightGray;
		case GameManager.powerPelletCode:
			return Color.white;
		case GameManager.fruitCode:
			return Color.magenta;
		case GameManager.pacmanCode:
			return Color.yellow;
		case GameManager.blinkyCode:
			return Color.red;
		case GameManager.pinkyCode:
			return Color.pink;
		case GameManager.inkyCode:
			return Color.cyan;
		case GameManager.clydeCode:
			return Color.orange;
		default:
			return new Color(150, 100, 100);
		}
	}

	/*
	 * Draw padding guides at every row and column (forming a visual grid)
	 */
	public void drawGrid(Graphics g) {
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

}
