package pacman;

import pacman.Main.Codes;

public class Ghost {
	private int row, col, direction;
	private double updatePeriod;
	private Codes[][] map = Main.map;

	public Ghost() {

	}

	// return speed
	public double getupdatePeriod() {
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

	public void respawn() {
		// reset row and col
		// reset to ghost house
	}

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
			// ghosts can overlap
			if (map[r][c] == Codes.blinky || map[r][c] == Codes.inky || map[r][c] == Codes.pinky
					|| map[r][c] == Codes.clyde) {
				// ghost
				alive = false;
				return true;
			}
		}
	}
}
