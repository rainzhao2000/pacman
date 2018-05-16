package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Ghost extends Character {

	public Ghost(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		super(canvas, dir, row, col, color, isFixed);
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
		ArrayList<Direction> availableDir = new ArrayList<>();
		switch (dir) {
		case left:
			right = false;
			break;
		case right:
			left = false;
			break;
		case up:
			down = false;
			break;
		case down:
			up = false;
			break;
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
		} else {
			int randomIndex = (int) (Math.random() * availableDir.size());
			dir = availableDir.get(randomIndex);
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
				if (canvas.inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (Main.pacman.getRow() == row && Main.pacman.getCol() == col) {
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
				if (canvas.inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (Main.pacman.getRow() == row && Main.pacman.getCol() == col) {
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
				if (canvas.inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (Main.pacman.getRow() == row && Main.pacman.getCol() == col) {
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
				if (canvas.inBounds(tempR, tempC)) {
					if (map[tempR][tempC] == Code.wall) {
						return false;
					} else if (Main.pacman.getRow() == row && Main.pacman.getCol() == col) {
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
