package pacman;

import pacman.Main.Codes;

public class Pacman {
	// For the direction variable:
	// 1 represents left, 2 represents right, 3 represents up, 4 represents down.
	// For the coordinate variables:
	// the first row is row 0 and the first column is col 0
	private int row, col, direction;
	private double updatePeriod;
	private boolean alive;
	private Codes[][] map = Main.map;

	// Pacman constructor
	public Pacman(int row, int col, double updatePeriod, int direction) {
		this.row = row;
		this.col = col;
		this.updatePeriod = updatePeriod;
		this.direction = direction;
		this.alive = true;
	}

	// return speed
	public double getUpdatePeriod() {
		return updatePeriod;
	}

	// return row
	public int getrow() {
		return row;
	}

	// return col
	public int getcol() {
		return col;
	}

	// update the position of the Pacman
	public void update() {
		if (direction == 1) {
			// left
			if (check(row, col - 1)) {
				col--;
			}
		} else if (direction == 2) {
			// right
			if (check(row, col + 1)) {
				col++;
			}
		} else if (direction == 3) {
			// up
			if (check(row - 1, col)) {
				row--;
			}
		} else {
			// down
			if (check(row + 1, col)) {
				row++;
			}
		}
	}

	// return true if the block at r and c is not a wall
	// return false if the block at r and c is a wall
	// set alive to false if the block at r and c is a ghost
	private boolean check(int r, int c) {
		if (r < 0 || r >= map.length || c < 0 || c >= map[0].length) {
			// exceed boundary
			return false;
		} else if (map[r][c] == Codes.wall) {
			// wall
			return false;
		} else if (map[r][c] == Codes.pacdot) {
			// pacdot
			map[r][c] = Codes.path;
			Main.gameManager.canvas.score += 10;
			return true;
		} else if (map[r][c] == Codes.powerPellet) {
			// power pellet
			map[r][c] = Codes.path;
			Main.gameManager.canvas.edible = true;
			Main.gameManager.canvas.score += 50;
			return true;
		} else if (map[r][c] == Codes.fruit) {
			// fruit
			map[r][c] = Codes.path;
			Main.gameManager.canvas.score += 100;
			return true;
		} else if (map[r][c] == Codes.path) {
			// path
			return true;
		} else {
			// instead of checking ghost in array, we have to check ghost's position
			// edible & ghost, points++ and re-spawn ghost
			// in-edible & ghost, alive = false & restart game
			if (map[r][c] == Codes.blinky || map[r][c] == Codes.inky || map[r][c] == Codes.pinky
					|| map[r][c] == Codes.clyde) {
				// ghost
				alive = false;
				return true;
			}
		}
	}

}
