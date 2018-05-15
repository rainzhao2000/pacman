package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Direction;

public class Ghost extends Character {

	private boolean edible;

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		super(canvas, dir, row, col, 5, color, isFixed);
		edible = false;
	}

	@Override
	void draw(Graphics g) {
		animate(g);
		if (row >= 0 && row < map.length && col >= 0 && col < map[0].length && !isFixed) {
			checkSurrounding();
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
