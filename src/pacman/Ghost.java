package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {

	private boolean edible;

	public Ghost(Direction dir, int row, int col, int speed, Color color) {
		this.dir = dir;
		this.row = row;
		this.col = col;
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		this.speed = speed;
		this.color = color;
		edible = false;
	}

	@Override
	void draw(Graphics g) {
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		g.setColor(color);
		g.fillRect(x, y, Main.tilePadWidth, Main.tilePadWidth);
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
