package pacman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

	int framerate = 30;
	int[][] map = GameManager.map;
	int tileWidth = GameManager.tileWidth;
	int padding = GameManager.padding;
	int mapWidth = GameManager.mapWidth;
	int mapHeight = GameManager.mapHeight;
	
	/*
	 * Framerate dictates the delay between paint refreshes as this method gets
	 * called from repaint().
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		try {
			Thread.sleep(1000 / framerate); // milliseconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		drawGrid(g);
	}

	/*
	 * Draw padding guides at every row and column (forming a visual grid)
	 */
	public void drawGrid(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, mapWidth, padding);
		for (int row = 1; row <= map.length; row++) {
			int y = row * (tileWidth + padding);
			g.fillRect(0, y, mapWidth, padding);
		}
		g.fillRect(0, 0, padding, mapHeight);
		for (int col = 1; col <= map[0].length; col++) {
			int x = col * (tileWidth + padding);
			g.fillRect(x, 0, padding, mapHeight);
		}
	}

}
