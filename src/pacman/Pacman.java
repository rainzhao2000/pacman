package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Pacman extends Character {

	private Direction intendedDir;

	private DrawPanel canvas;
	private ArrayList<Ghost> ghosts;

	private int eatCounter, lives;

	private boolean left, right, up, down;

	// Pacman constructor
	public Pacman(DrawPanel canvas, Direction dir, int row, int col, int speed) {
		this.canvas = canvas;
		ghosts = canvas.getGhosts();
		this.dir = dir;
		this.intendedDir = dir;
		this.rowSpawn = row;
		this.colSpawn = col;
		this.row = row;
		this.col = col;
		this.speed = speed;
		color = Color.yellow;
		lives = 3;
	}

	@Override
	void draw(Graphics g) {
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		g.setColor(color);
		g.fillRect(x, y, Main.tilePadWidth, Main.tilePadWidth);
		checkSurrounding();
	}

	private void checkSurrounding() {
		left = checkTile(row, col - 1);
		right = checkTile(row, col + 1);
		up = checkTile(row - 1, col);
		down = checkTile(row + 1, col);
		if (intendedDir == Direction.left && left) {
			dir = intendedDir;
			col--;
		} else if (intendedDir == Direction.right && right) {
			dir = intendedDir;
			col++;
		} else if (intendedDir == Direction.up && up) {
			dir = intendedDir;
			row--;
		} else if (intendedDir == Direction.down && down) {
			dir = intendedDir;
			row++;
		} else if (dir == Direction.left && left) {
			col--;
		} else if (dir == Direction.right && right) {
			col++;
		} else if (dir == Direction.up && up) {
			row--;
		} else if (dir == Direction.down && down) {
			row++;
		}
	}

	@Override
	protected boolean checkTile(int row, int col) {
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
			// exceed boundary
			return false;
		}
		for (Ghost ghost : ghosts) {
			if (ghost.getRow() == row && ghost.getCol() == col) {
				if (ghost.getEdible()) {
					canvas.setScore(canvas.getScore() + (int) (Math.pow(2, eatCounter) * 200));
					ghost.respawn();
					eatCounter++;
					return true;
				} else {
					lives--;
					return false;
				}
			}
		}
		switch (map[row][col]) {
		case wall:
			return false;
		case pacdot:
			map[row][col] = Code.path;
			canvas.setScore(canvas.getScore() + 10);
			return true;
		case powerPellet:
			map[row][col] = Code.path;
			canvas.setGhostsEdible();
			canvas.setScore(canvas.getScore() + 50);
			eatCounter = 0;
		case fruit:
			map[row][col] = Code.path;
			canvas.setScore(canvas.getScore() + 100);
			return true;
		default:
			// path
			return true;
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
