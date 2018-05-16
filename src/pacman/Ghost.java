package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {
	private Direction lastDir;
	private int lastR, lastC;

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		super(canvas, dir, row, col, color, isFixed);
		lastDir = dir;
		lastR = -1;
		lastC = -1;
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
		if (lastR == row && lastC == col) {
			return;
		} else {
			lastR = row;
			lastC = col;
		}

		if (left) {
			availableDir.add(Direction.left);
		}
		if (right) {
			availableDir.add(Direction.right);
		}
		if (up) {
			availableDir.add(Direction.up);
		}
		if (down) {
			availableDir.add(Direction.down);
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

		if (availableDir.size() == 0) {
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
			dir = temp;
		} else if (seesPacman && edible) {
			boolean escapeAvaliable = false;
			if (temp == Direction.left) {
				escapeAvaliable = left;
			} else if (temp == Direction.right) {
				escapeAvaliable = right;
			} else if (temp == Direction.up) {
				escapeAvaliable = up;
			} else {
				escapeAvaliable = down;
			}

			if (escapeAvaliable) {
				dir = temp;
			} else {
				int randomIndex = (int) (Math.random() * availableDir.size());
				dir = availableDir.get(randomIndex);
			}
		} else {
			int randomIndex = (int) (Math.random() * availableDir.size());
			dir = availableDir.get(randomIndex);
		}

		System.out.println(dir);

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

}
