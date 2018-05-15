package pacman;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {

	private boolean edible;

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color) {
		super(canvas, dir, row, col, 5, color);
		edible = false;
	}

	@Override
	void draw(Graphics g) {
		animate(g);
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
