/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Date: June 4, 2018
 * Description: This class is the ghost in the game pacman. It extends 
 * 				Character class and override some of its methods.
 */

package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character implements ActionListener {
	// declaring and/or initializing variables
	private Direction lastDir;
	private int lastRow, lastCol;
	private double probability;
	private Timer edibleTimer;

	int edibleTime;

	// constructor of the class
	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed, double probability) {
		// calls the super constructor
		super(canvas, dir, row, col, color, isFixed);
		// initialize the last direction to the given direction
		lastDir = dir;
		//
		edibleTime = 10000;
		// set the probability of chasing pacman or running away from pacman
		this.probability = probability;
	}

	// override the draw method in the super class
	@Override
	void draw(Graphics g) {
		if (canvas.inBounds(row, col) && !isFixed) {
			checkSurrounding();
		}
		animate(g);
	}

	// render the image of the ghost
	@Override
	protected void drawShape(Graphics g) {
		g.fillOval(x0, y0, Main.tilePadWidth, Main.tilePadWidth);
		g.fillRect(x0, (int) y, Main.tilePadWidth, Main.tilePadWidth / 2);
	}

	// This method handles the selection of the direction when at an intersection.
	// This is the AI part of the program.
	@Override
	protected void selectDir() {
		// set the last direction to the current direction
		lastDir = dir;

		// create an ArrayList of available directions
		ArrayList<Direction> availableDir = new ArrayList<>();
		int dirCounter = 0;
		if (lastRow == row && lastCol == col) {
			return;
		} else {
			lastRow = row;
			lastCol = col;
		}

		if (left) {
			availableDir.add(Direction.left);
			dirCounter++;
		}
		if (right) {
			availableDir.add(Direction.right);
			dirCounter++;
		}
		if (up) {
			availableDir.add(Direction.up);
			dirCounter++;
		}
		if (down) {
			availableDir.add(Direction.down);
			dirCounter++;
		}

		// this is to prevent the ghost from turning back 180 degree
		if (availableDir.contains(reverseDir(lastDir)))
			availableDir.remove(availableDir.indexOf(reverseDir(lastDir)));

		boolean seesPacman = false;
		Direction temp = null;
		// for each available direction, run a check
		for (Direction d : availableDir) {
			// if pacman is in sight
			if (rayTrace(d)) {
				// if the ghost itself if edible
				if (edible) {
					// set the intended direction to the opposite of pacman's direction
					temp = reverseDir(d);
					// the direction of pacman is no longer available
					availableDir.remove(availableDir.indexOf(d));
				} // if the ghost itself if not edible
				else {
					// set the intended direction to the direction of pacman
					temp = d;
				}
				// set sees pacman to true
				seesPacman = true;
				// break out of the loop since pacman is already found
				break;
			}
		}
		// a random value for the probability calculation later on
		double randVal = Math.random();

		// if there is no available direction at all
		if (dirCounter == 0) {
			// can not move
			move = false;
		} // if the size of the available direction is 0
		else if (availableDir.size() == 0) {
			// turn around
			dir = reverseDir(dir);
		} // if pacman is insight and the ghost is not edible
		else if (seesPacman && !edible) {
			// chase pacman by the given probability
			if (probability >= randVal) {
				// if the ghost is chasing pacman
				dir = temp;
			} else {
				// if not, select a random direction
				int randomIndex = (int) (Math.random() * availableDir.size());
				dir = availableDir.get(randomIndex);
			}
		} // if pacman is insight and the ghost is edible
		else if (seesPacman && edible) {
			// check if there is an escape (opposite direction of the pacman)
			boolean escapeAvailable = false;
			if (temp == Direction.left) {
				escapeAvailable = left;
			} else if (temp == Direction.right) {
				escapeAvailable = right;
			} else if (temp == Direction.up) {
				escapeAvailable = up;
			} else {
				escapeAvailable = down;
			}

			// if the escape is available, escape by the probability
			if (escapeAvailable) {
				// if the ghost is running away
				if (probability >= randVal) {
					dir = temp;
				} // if not, choose a random direction
				else {
					int randomIndex = (int) (Math.random() * availableDir.size());
					dir = availableDir.get(randomIndex);
				}
			} // if the escape direction is not available, choose a random direction
			else {
				int randomIndex = (int) (Math.random() * availableDir.size());
				dir = availableDir.get(randomIndex);
			}
		} // if the ghost does not see pacman at all, choose a random direction
		else {
			int randomIndex = (int) (Math.random() * availableDir.size());
			dir = availableDir.get(randomIndex);
		}
	}

	protected void setEdible(boolean state) {
		if (state) {
			if (!edible) {
				firstEdible = true;
			}
			edibleTimer = new Timer(edibleTime, this);
			edibleTimer.start();
		} else {
			try {
				// if the timer exists, stop it
				edibleTimer.stop();
			} catch (NullPointerException e) {
				// if the timer is never started, do nothing
			}
			speed = stdSpeed;
		}
		edible = state;
	}

	// when the character is no longer edible, set to inedible state
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == edibleTimer) {
			setEdible(false);
		}

	}

	// return the reverse direction
	private Direction reverseDir(Direction d) {
		if (d == Direction.left) {
			return Direction.right;
		} else if (d == Direction.right) {
			return Direction.left;
		} else if (d == Direction.up) {
			return Direction.down;
		} else {
			// down
			return Direction.up;
		}
	}

	// This method handles the situation when pacman eats a power pellet, the ghost
	// will turn around if it is available. This method returns true if turn around
	// is successful, return false otherwise
	@Override
	boolean turnAround() {
		if (reverseDir(dir) == Direction.left && left) {
			dir = reverseDir(dir);
			return true;
		} else if (reverseDir(dir) == Direction.right && right) {
			dir = reverseDir(dir);
			return true;
		} else if (reverseDir(dir) == Direction.up && up) {
			dir = reverseDir(dir);
			return true;
		} else if (reverseDir(dir) == Direction.down && down) {
			dir = reverseDir(dir);
			return true;
		}

		return false;

	}

	// This method returns true if pacman is in sight, returns false otherwise
	boolean rayTrace(Direction d) {
		int tempR = row;
		int tempC = col;
		// the vision continues in a linear fasion untill a wall is detected
		while (canvas.inBounds(tempR, tempC)) {
			if (map[tempR][tempC] == Code.wall) {
				return false;
			}
			if (tempR == Main.pacman.row && tempC == Main.pacman.col) {
				return true;
			}
			if (d == Direction.left) {
				tempC--;
			} else if (d == Direction.right) {
				tempC++;
			} else if (d == Direction.up) {
				tempR--;
			} else {
				// down
				tempR++;
			}
		}
		return false;
	}

	// accessor method
	double getProb() {
		return probability;
	}

	// This method is for respawning the ghost. It will restore the edible status of
	// the ghost, then call the super method
	@Override
	void respawn() {
		setEdible(false);
		super.respawn();
	}

}
