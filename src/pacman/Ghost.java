package pacman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {

	private boolean edible;

	public Ghost(Direction dir, int row, int col, Color color) {
		this.dir = dir;
		this.row = row;
		this.col = col;
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		speed = 5;
		this.color = color;
		edible = false;
		timer = new Timer((int) (1 / speed), this);
		timer.start();
	}

	@Override
	void draw(Graphics g) {
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		g.setColor(color);
		g.fillRect((int) x, (int) y, Main.tilePadWidth, Main.tilePadWidth);
	}

	@Override
	protected boolean checkTile(int row, int col) {
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
			// exceed boundary
			return false;
		} else if (map[row][col] == Code.wall) {
			// wall
			return false;
		} else {
			return true;
		}
	}

	boolean getEdible() {
		return edible;
	}

	// modifier method for the edible variable of the ghost
	void setEdible(boolean state) {
		this.edible = state;
	}

}
