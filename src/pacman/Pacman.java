package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Pacman extends Character {

	private Direction intendedDir;

	private int eatCounter, lives;

	// Pacman constructor
	public Pacman(DrawPanel canvas, Direction dir, int row, int col, boolean isFixed) {
		super(canvas, dir, row, col, 5, Color.yellow, isFixed);
		this.intendedDir = dir;
		lives = 3;
	}

	@Override
	void draw(Graphics g) {
		animate(g);
		if (canvas.inBounds(row, col) && !isFixed) {
			checkSurrounding();
			checkCurrent();
		}
	}

	@Override
	protected void selectDir() {
		if (intendedDir == Direction.left && left || intendedDir == Direction.right && right
				|| intendedDir == Direction.up && up || intendedDir == Direction.down && down) {
			dir = intendedDir;
		} else if (!(dir == Direction.left && left) && !(dir == Direction.right && right)
				&& !(dir == Direction.up && up) && !(dir == Direction.down && down)) {
			doAnimate = false;
		}
	}
	
	private void checkCurrent() {
		for (Ghost ghost : Main.ghosts) {
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
