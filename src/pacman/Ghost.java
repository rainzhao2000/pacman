package pacman;

import pacman.Main.Directions;

public class Ghost extends Character {

	private boolean edible;

	// edible timer
	// refresh timer
	// etc

	public Ghost(Directions dir, int row, int col, int speed) {
		this.dir = dir;
		this.row = row;
		this.col = col;
		this.speed = speed;
		this.edible = false;
	}

	boolean getEdible() {
		return edible;
	}

	// modifier method for the edible variable of the ghost
	void setEdible(boolean state) {
		this.edible = state;
	}

}
