package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pacman.Main.Codes;

public class Pacman implements ActionListener {
	// For the direction variable:
	// 1 represents left, 2 represents right, 3 represents up, 4 represents down.
	// For the coordinate variables:
	// the first row is row 0 and the first column is col 0
	private int row, col, direction;
	private double updatePeriod;
	private boolean alive;
	private Codes[][] map = Main.map;
	private int eatCounter;
	private int lives;
	private Timer refreshTimer;
	// refresh timer

	// Pacman constructor
	public Pacman(int row, int col, double updatePeriod, int direction) {
		this.row = row;
		this.col = col;
		this.updatePeriod = updatePeriod;
		this.direction = direction;
		this.alive = true;
		this.lives = 3;
		refreshTimer = new Timer(1000, this);
		refreshTimer.start();
	}

	int getDirection() {
		return direction;
	}

	void up() {
		direction = 3;
	}

	void down() {
		direction = 4;
	}

	void left() {
		direction = 1;
	}

	void right() {
		direction = 2;
	}

	// return alive
	public boolean getState() {
		return alive;
	}

	// return lives
	public int getLives() {
		return lives;
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

	// Repaints the panel at the conditions of the timer

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshTimer) {
			update();
		}
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

	// reassign pacdot and powerpellet when refreshing?

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
			Main.gameManager.canvas.setGhostsEdible();
			Main.gameManager.canvas.score += 50;
			eatCounter = 0;
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

			if (Main.blinky.getrow() == r && Main.blinky.getcol() == c) {
				if (Main.blinky.getState()) {
					// edible
					// add score
					// respawn ghost
					Main.gameManager.canvas.score += (int) (Math.pow(2, eatCounter) * 200);
					Main.blinky.respawn();
					eatCounter++;
					return true;
				} else {
					// lose life
					lives--;
					alive = false;
					return false;
				}
			}
			if (Main.inky.getrow() == r && Main.inky.getcol() == c) {

				if (Main.inky.getState()) {
					// edible
					// add score
					Main.gameManager.canvas.score += (int) (Math.pow(2, eatCounter) * 200);
					Main.inky.respawn();
					eatCounter++;
					return true;
				} else {
					// lose life
					lives--;
					alive = false;
					return false;
				}
			}
			if (Main.pinky.getrow() == r && Main.pinky.getcol() == c) {
				if (Main.pinky.getState()) {
					// edible
					// add score
					Main.gameManager.canvas.score += (int) (Math.pow(2, eatCounter) * 200);
					Main.pinky.respawn();
					eatCounter++;
					return true;
				} else {
					// lose life
					lives--;
					alive = false;
					return false;
				}
			}
			if (Main.clyde.getrow() == r && Main.clyde.getcol() == c) {
				if (Main.clyde.getState()) {
					// edible
					// add score
					Main.gameManager.canvas.score += (int) (Math.pow(2, eatCounter) * 200);
					Main.clyde.respawn();
					eatCounter++;
					return true;
				} else {
					// lose life
					lives--;
					alive = false;
					return false;
				}
			}
			return true;
		}
	}

	void respawn() {
		row = 23;
		col = 14;
		alive = true;
	}
}
