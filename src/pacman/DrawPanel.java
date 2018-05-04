package pacman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

	int framerate = 30;
	int[][] map = GameManager.map;
	int tileWidth;
	int padding;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		try {
			Thread.sleep(1000 / framerate); // milliseconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tileWidth = (getHeight() - map.length - 1) / map.length;
		padding = tileWidth / 10;
		drawGrid(g);
	}

	public void drawGrid(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), padding);
		for (int row = 1; row <= map.length; row++) {
			int y = row * (tileWidth + padding);
			g.fillRect(0, y, getWidth(), padding);
		}
		g.fillRect(0, 0, padding, getHeight());
		for (int col = 1; col <= map[0].length; col++) {
			int x = col * (tileWidth + padding);
			g.fillRect(x, 0, padding, getHeight());
		}
	}

}
