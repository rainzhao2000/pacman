package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Pacman extends Character {

	private Direction intendedDir;

	private ArrayList<Ghost> ghosts;

	private int eatCounter, lives;

	private boolean left, right, up, down;

	// Pacman constructor
	public Pacman(DrawPanel canvas, Direction dir, int row, int col) {
		super(canvas, dir, row, col, 5, Color.yellow);
		ghosts = canvas.getGhosts();
		this.intendedDir = dir;
		lives = 3;
	}

	@Override
	void draw(Graphics g) {
		animate(g);
		if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
			checkSurrounding();
			checkCurrent();
		}
	}

	private void checkSurrounding() {
		doAnimate = true;
		left = checkTile(row, col - 1);
		right = checkTile(row, col + 1);
		up = checkTile(row - 1, col);
		down = checkTile(row + 1, col);
		if (x0 == col * Main.tilePadWidth && y0 == row * Main.tilePadWidth) {
			if (intendedDir == Direction.left && left) {
				dir = intendedDir;
			} else if (intendedDir == Direction.right && right) {
				dir = intendedDir;
			} else if (intendedDir == Direction.up && up) {
				dir = intendedDir;
			} else if (intendedDir == Direction.down && down) {
				dir = intendedDir;
			} else {
				doAnimate = false;
			}
		}
	}

	@Override
	protected boolean checkTile(int row, int col) {
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
			// exceed boundary
			return false;
		}
		if (map[row][col] == Code.wall) {
			return false;
		}
		return true;
	}

	private void checkCurrent() {
		for (Ghost ghost : ghosts) {
			if (ghost.getRow() == row && ghost.getCol() == col) {
				if (ghost.getEdible()) {
					canvas.setScore(canvas.getScore() + (int) (Math.pow(2, eatCounter) * 200));
					ghost.respawn();
					eatCounter++;
				} else {
					lives--;
					respawn();
				}
			}
		}
		switch (map[row][col]) {
		case pacdot:
			map[row][col] = Code.path;
			canvas.setScore(canvas.getScore() + 10);
			break;
		case powerPellet:
			map[row][col] = Code.path;
			canvas.setGhostsEdible();
			canvas.setScore(canvas.getScore() + 50);
			eatCounter = 0;
			break;
		case fruit:
			map[row][col] = Code.path;
			canvas.setScore(canvas.getScore() + 100);
			break;
		default:
			break;
		}
	}

	// return lives
	int getLives() {
		return lives;
	}

	void logDir(Direction intendedDir) {
		this.intendedDir = intendedDir;
	}

}
