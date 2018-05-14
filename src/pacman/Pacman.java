package pacman;

import pacman.Main.Codes;
import pacman.Main.Directions;

public class Pacman extends Character {
	// For the direction variable:
	// 1 represents left, 2 represents right, 3 represents up, 4 represents
	// down.
	// For the coordinate variables:
	// the first row is row 0 and the first column is col 0
	private int eatCounter, lives;
	// refresh timer

	// Pacman constructor
	public Pacman(Directions dir, int row, int col, int speed) {
		this.dir = dir;
		this.rowSpawn = row;
		this.colSpawn = col;
		this.row = row;
		this.col = col;
		this.speed = speed;
		this.lives = 3;
	}

	// return lives
	int getLives() {
		return lives;
	}

	// return true if the block at r and c is not a wall
	// return false if the block at r and c is a wall
	// set alive to false if the block at r and c is a ghost

	// reassign pacdot and powerpellet when refreshing?

	@Override
	protected boolean checkTile(int row, int col) {
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
			// exceed boundary
			return false;
		} else if (Main.blinky.getRow() == row && Main.blinky.getCol() == col) {
			// instead of checking ghost in array, we have to check ghost's
			// position
			// edible & ghost, points++ and re-spawn ghost
			// in-edible & ghost, alive = false & restart game

			if (Main.blinky.getEdible()) {
				// edible
				// add score
				// respawn ghost
				Main.game.getCanvas()
						.setScore(Main.game.getCanvas().getScore() + (int) (Math.pow(2, eatCounter) * 200));
				Main.blinky.respawn();
				eatCounter++;
				return true;
			} else {
				// lose life
				lives--;
				return false;
			}
		} else if (Main.inky.getRow() == row && Main.inky.getCol() == col) {

			if (Main.inky.getEdible()) {
				// edible
				// add score
				Main.game.getCanvas()
						.setScore(Main.game.getCanvas().getScore() + (int) (Math.pow(2, eatCounter) * 200));
				Main.inky.respawn();
				eatCounter++;
				return true;
			} else {
				// lose life
				lives--;
				return false;
			}
		} else if (Main.pinky.getRow() == row && Main.pinky.getCol() == col) {
			if (Main.pinky.getEdible()) {
				// edible
				// add score
				Main.game.getCanvas()
						.setScore(Main.game.getCanvas().getScore() + (int) (Math.pow(2, eatCounter) * 200));
				Main.pinky.respawn();
				eatCounter++;
				return true;
			} else {
				// lose life
				lives--;
				return false;
			}
		} else if (Main.clyde.getRow() == row && Main.clyde.getCol() == col) {
			if (Main.clyde.getEdible()) {
				// edible
				// add score
				Main.game.getCanvas()
						.setScore(Main.game.getCanvas().getScore() + (int) (Math.pow(2, eatCounter) * 200));
				Main.clyde.respawn();
				eatCounter++;
				return true;
			} else {
				// lose life
				lives--;
				return false;
			}
		} else if (map[row][col] == Codes.wall) {
			// wall
			return false;
		} else if (map[row][col] == Codes.pacdot) {
			// pacdot
			map[row][col] = Codes.path;
			Main.game.getCanvas().setScore(Main.game.getCanvas().getScore() + 10);
			return true;
		} else if (map[row][col] == Codes.powerPellet) {
			// power pellet
			map[row][col] = Codes.path;
			Main.game.getCanvas().setGhostsEdible();
			Main.game.getCanvas().setScore(Main.game.getCanvas().getScore() + 50);
			eatCounter = 0;
			return true;
		} else if (map[row][col] == Codes.fruit) {
			// fruit
			map[row][col] = Codes.path;
			Main.game.getCanvas().setScore(Main.game.getCanvas().getScore() + 100);
			return true;
		} else {
			// path
			return true;
		}
	}
}
