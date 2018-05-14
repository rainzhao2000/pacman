package pacman;

import java.awt.Color;
import java.util.ArrayList;

import pacman.Main.Codes;
import pacman.Main.Directions;

public class Pacman extends Character {

	private DrawPanel canvas;
	private ArrayList<Ghost> ghosts;

	private int eatCounter, lives;

	// Pacman constructor
	public Pacman(DrawPanel canvas, Directions dir, int row, int col, int speed) {
		this.canvas = canvas;
		ghosts = canvas.getGhosts();
		this.dir = dir;
		this.rowSpawn = row;
		this.colSpawn = col;
		this.row = row;
		this.col = col;
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
		this.speed = speed;
		color = Color.yellow;
		lives = 3;
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
		Codes tile = map[row][col];
		switch (tile) {
		case wall:
			return false;
		case pacdot:
			tile = Codes.path;
			canvas.setScore(canvas.getScore() + 10);
			return true;
		case powerPellet:
			tile = Codes.path;
			canvas.setGhostsEdible();
			canvas.setScore(canvas.getScore() + 50);
			eatCounter = 0;
		case fruit:
			tile = Codes.path;
			canvas.setScore(canvas.getScore() + 100);
			return true;
		default:
			// path
			return true;
		}
	}
	
}
