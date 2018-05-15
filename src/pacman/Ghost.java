package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {

	private boolean edible;

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		super(canvas, dir, row, col, 5, color, isFixed);
		edible = false;
	}

	@Override
	void draw(Graphics g) {
		animate(g);
		if (inBounds(row, col) && !isFixed) {
			checkSurrounding();
		}
	}

	boolean getEdible() {
		return edible;
	}

	// modifier method for the edible variable of the ghost
	void setEdible(boolean state) {
		this.edible = state;
	}

	@Override
	protected void checkSurrounding() {
		left = checkTile(row, col - 1);
		right = checkTile(row, col + 1);
		up = checkTile(row - 1, col);
		down = checkTile(row + 1, col);
		ArrayList<Direction> avaliableDir = new ArrayList<>();
		if (dir == Direction.up) {
			// discard down
			down = false;
		} else if (dir == Direction.down) {
			// discard up
			up = false;
		} else if (dir == Direction.left) {
			// discard right
			right = false;
		} else if (dir == Direction.right) {
			// discard left
			left = false;
		}

		if (left) {
			avaliableDir.add(Direction.left);
		}
		if (right) {
			avaliableDir.add(Direction.right);
		}
		if (up) {
			avaliableDir.add(Direction.up);
		}
		if (down) {
			avaliableDir.add(Direction.down);
		}

		if (avaliableDir.size() == 0) {
			// turn around at dead end
			if (dir == Direction.left) {
				dir = Direction.right;
			} else if (dir == Direction.right) {
				dir = Direction.left;
			} else if (dir == Direction.up) {
				dir = Direction.down;
			} else if (dir == Direction.down) {
				dir = Direction.up;
			}
		} else {
			int randomIndex = (int) (Math.random() * avaliableDir.size());
			dir = avaliableDir.get(randomIndex);
		}

	}

	// return true if pacman is in sight
	// otherwise return false
	boolean rayTrace() {
		switch (dir) {
		case left:
			int tempR = row;
			int tempC = col;
			while (true) {
				if (inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (canvas.getPacman().row == row && canvas.getPacman().col == col) {
						return true;
					} else {
						tempC--;
					}
				} else {
					return false;
				}
			}
		case right:
			tempR = row;
			tempC = col;
			while (true) {
				if (inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (canvas.getPacman().row == row && canvas.getPacman().col == col) {
						return true;
					} else {
						tempC++;
					}
				} else {
					return false;
				}
			}
		case up:
			tempR = row;
			tempC = col;
			while (true) {
				if (inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (canvas.getPacman().row == row && canvas.getPacman().col == col) {
						return true;
					} else {
						tempR--;
					}
				} else {
					return false;
				}
			}
		case down:
			tempR = row;
			tempC = col;
			while (true) {
				if (inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (canvas.getPacman().row == row && canvas.getPacman().col == col) {
						return true;
					} else {
						tempR++;
					}
				} else {
					return false;
				}
			}
		}

		return false;
	}

}
