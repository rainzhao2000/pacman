package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Pacman extends Character {

	private Direction intendedDir;

	private ArrayList<Ghost> ghosts;

	private int eatCounter, lives;

	private boolean left, right, up, down;

	// Pacman constructor
	public Pacman(DrawPanel canvas, Direction dir, int row, int col) {
		this.canvas = canvas;
		ghosts = canvas.getGhosts();
		this.dir = dir;
		this.intendedDir = dir;
		this.rowSpawn = row;
		this.colSpawn = col;
		this.row = row;
		this.col = col;
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		speed = 5;
		color = Color.yellow;
		lives = 3;
		timer = new Timer((int) (1000 / speed), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkSurrounding();
		checkCurrent();
	}

	@Override
	void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, Main.tilePadWidth, Main.tilePadWidth);
		double displacement = speed * Main.tilePadWidth / canvas.getFramerate();
		if (dir != null) {
			switch (dir) {
			case left:
				x -= displacement;
				break;
			case right:
				x += displacement;
				break;
			case up:
				y -= displacement;
				break;
			case down:
				y += displacement;
				break;
			}
		}
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
		} else {
			dir = null;
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
