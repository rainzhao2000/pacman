/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Date: June 4, 2018
 * Description: This class is the pacman in the game pacman. It extends 
 * 				Character class and override some of its methods.
 */

package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Pacman extends Character {

	// declaring and/or initializing variables
	private Direction intendedDir;
	private int eatCounter, lives;

	// constructor
	public Pacman(DrawPanel canvas, Direction dir, int row, int col, boolean isFixed, int initialLives) {
		// calls the super constructor
		super(canvas, dir, row, col, Color.yellow, isFixed);
		// initialize the last direction to the given direction
		this.intendedDir = dir;
		// set the lives to the initial lives
		this.lives = initialLives;
	}

	// override the draw method in the super class
	@Override
	void draw(Graphics g) {
		if (canvas.inBounds(row, col) && !isFixed) {
			checkSurrounding();
			// if (x0 == col * Main.tilePadWidth && y0 == row *
			// Main.tilePadWidth) {
			checkCurrent();
			// }
		}
		animate(g);
	}

	// render the image of the ghost
	@Override
	protected void drawShape(Graphics g) {
		g.fillOval(x0, y0, Main.tilePadWidth, Main.tilePadWidth);
	}

	// This method handles turning at intersections to the last key stroke memorized
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

	// This method checks the current position of the pacman
	private void checkCurrent() {
		// for each ghost
		for (Ghost ghost : Main.ghosts) {
			// if the pacman overlaps the ghost
			if ((ghost.getRow() == row && ghost.getCol() == col) || (ghost.getx0() == x0 && ghost.gety0() == y0)) {
				// if the ghost is edible
				if (ghost.getEdible()) {
					// eat and respawn the ghost and increment the score
					canvas.setScore(canvas.getScore() + (int) (Math.pow(2, eatCounter) * 200));
					ghost.setEdible(false);
					ghost.setSpeed(speed);
					ghost.respawn();
					eatCounter++;
				} // if the ghost is inedible
				else {
					// respawn all characters
					Main.game.getCanvas().respawnCharacters(true);
					// decrease pacman lives
					lives--;
					// break out of the loop
					break;
				}
			}
		}
		// check other cases
		switch (map[row][col]) {
		// if the current tile is a pacdot
		case pacdot:
			// the pacdot is eaten
			map[row][col] = Code.path;
			// the score is incremented
			canvas.setScore(canvas.getScore() + 10);
			// increase eaten edible object counter
			canvas.incrementEdibleObjectEaten();
			break;
		// if the current tile is a power pellet
		case powerPellet:
			// the pwoer pellet is eaten
			map[row][col] = Code.path;
			// the score is incremented
			canvas.setScore(canvas.getScore() + 50);
			// set all ghosts to edible
			canvas.setGhostsEdible();
			// increase eaten edible object counter
			canvas.incrementEdibleObjectEaten();
			// reset the number of ghost eaten within 1 power pellet
			eatCounter = 0;
			break;
		// if the current tile is a fruit
		case fruit:
			// the fruit is eaten
			map[row][col] = Code.path;
			// the score is incremented
			canvas.setScore(canvas.getScore() + 100);
			// increase eaten edible object counter
			canvas.incrementEdibleObjectEaten();
			break;
		default:
			break;
		}
	}

	// accessor method
	int getLives() {
		return lives;
	}

	// modifier method
	void setLives(int lives) {
		this.lives = lives;
	}

	// log the last unresolved direction
	void logDir(Direction intendedDir) {
		this.intendedDir = intendedDir;
	}

}
