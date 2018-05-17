package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {
	private Direction lastDir;
	private int lastRow, lastCol;
	private double probability;

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed, double probability) {
		super(canvas, dir, row, col, color, isFixed);
		lastDir = dir;
		lastRow = -1;
		lastCol = -1;
		this.probability = probability;
	}

	@Override
	void draw(Graphics g) {
		if (canvas.inBounds(row, col) && !isFixed) {
			checkSurrounding();
		}
		animate(g);
	}

	@Override
	protected void selectDir() {

		lastDir = dir;
		ArrayList<Direction> availableDir = new ArrayList<>();
		int dirCounter = 0;
		if (lastRow == row && lastCol == col) {
			return;
		} else {
			lastRow = row;
			lastCol = col;
		}
		double randVal = Math.random();

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

		if (availableDir.contains(reverseDir(lastDir)))
			availableDir.remove(availableDir.indexOf(reverseDir(lastDir)));

		boolean seesPacman = false;
		Direction temp = null;

		for (Direction d : availableDir) {
			if (rayTrace(d)) {
				if (edible) {
					temp = reverseDir(d);
					availableDir.remove(availableDir.indexOf(d));
				} else {
					temp = d;
				}
				seesPacman = true;
				break;
			}
		}

		if (dirCounter == 0) {
			// dont move
			move = false;
		} else if (availableDir.size() == 0) {
			// turn around at dead end
			switch (dir) {
			case left:
				dir = Direction.right;
				break;
			case right:
				dir = Direction.left;
				break;
			case up:
				dir = Direction.down;
				break;
			case down:
				dir = Direction.up;
				break;
			}
		} else if (seesPacman && !edible) {
			if (probability >= randVal) {
				dir = temp;
			} else {
				int randomIndex = (int) (Math.random() * availableDir.size());
				dir = availableDir.get(randomIndex);
			}
		} else if (seesPacman && edible) {
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

			if (escapeAvailable) {
				if (probability >= randVal) {
					dir = temp;
				} else {
					int randomIndex = (int) (Math.random() * availableDir.size());
					dir = availableDir.get(randomIndex);
				}
			} else {
				int randomIndex = (int) (Math.random() * availableDir.size());
				dir = availableDir.get(randomIndex);
			}
		} else {
			int randomIndex = (int) (Math.random() * availableDir.size());
			dir = availableDir.get(randomIndex);
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

	// return true if pacman is in sight
	// otherwise return false
	boolean rayTrace(Direction d) {
		int tempR = row;
		int tempC = col;
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

	double getProb() {
		return this.probability;
	}

	@Override
	void respawn() {
		this.setEdible(false);
		super.respawn();
	}

}
