package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Character implements ActionListener {

	protected Code[][] map = Main.map;

	protected Direction dir;

	protected Timer edibleTimer;

	protected DrawPanel canvas;
	protected Color color;

	protected int rowSpawn, colSpawn, row, col, x0, y0, edibleTime, lastRow, lastCol;
	protected final double stdSpeed = 5;

	protected double x, y, speed, displacement; // speed in tiles/sec
	protected double centerOffset = Main.tilePadWidth / 2.0;

	protected boolean isFixed, doAnimate, move, doShowPos, edible, firstEdible, justTeleported;
	protected boolean left, right, up, down;

	protected ArrayList<point> portals;

	protected Character(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		this.canvas = canvas;
		this.dir = dir;
		rowSpawn = row;
		colSpawn = col;
		this.row = row;
		this.col = col;
		x0 = col * Main.tilePadWidth;
		y0 = row * Main.tilePadWidth;
		x = x0 + centerOffset;
		y = y0 + centerOffset;
		speed = stdSpeed;
		this.color = color;
		this.isFixed = isFixed;
		doAnimate = !isFixed;
		doShowPos = false;
		edible = false;
		firstEdible = false;
		edibleTime = 10000;
		move = true;
		portals = new ArrayList<>();
		lastRow = -1;
		lastCol = -1;
		justTeleported = false;
	}

	void draw(Graphics g) {

	}

	protected void animate(Graphics g) {

		Object value = Main.game.getSpeedMultiplierSpinner().getValue();
		try {
			displacement = speed * (double) value * Main.tilePadWidth / canvas.getFramerate();
		} catch (ClassCastException cce) {
			displacement = speed * (int) value * Main.tilePadWidth / canvas.getFramerate();
		}

		// displacement = speed * 2 * Main.tilePadWidth / canvas.getFramerate();

		if (doShowPos) {
			g.setColor(Color.GREEN);
			g.fillRect(col * Main.tilePadWidth, row * Main.tilePadWidth, Main.tilePadWidth, Main.tilePadWidth);
		}
		if (edible) {
			g.setColor(new Color(166, 124, 255));
		} else {
			g.setColor(color);
		}
		g.fillOval(x0, y0, Main.tilePadWidth, Main.tilePadWidth);
		if (move) {
			if (doAnimate) {
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
				x0 = (int) (x - centerOffset);
				y0 = (int) (y - centerOffset);
			}
			row = (int) Math.rint((double) y0 / Main.tilePadWidth);
			col = (int) Math.rint((double) x0 / Main.tilePadWidth);
		}
	}

	protected void checkSurrounding() {
		doAnimate = true;
		left = checkTile(row, col - 1);
		right = checkTile(row, col + 1);
		up = checkTile(row - 1, col);
		down = checkTile(row + 1, col);
		if (x0 == col * Main.tilePadWidth && y0 == row * Main.tilePadWidth) {
			if (firstEdible) {
				firstEdible = false;
				turnAround();
				selectDir();
			} else {
				selectDir();
			}
			checkPortal(row, col);
		}
	}

	protected void selectDir() {

	}

	private boolean checkTile(int row, int col) {
		if (!canvas.inBounds(row, col)) {
			// exceed boundary
			return false;
		}
		if (map[row][col] == Code.wall) {
			return false;
		}

		return true;
	}

	private void checkPortal(int row, int col) {
		if (lastRow == row && lastCol == col) {
			return;
		} else {
			lastRow = row;
			lastCol = col;

			if (map[row][col] == Code.portal && !justTeleported) {
				if (portals.size() >= 2) {
					while (true) {
						int randomIndex = (int) (Math.random() * portals.size());
						if (portals.get(randomIndex).getRow() != row || portals.get(randomIndex).getCol() != col) {
							setPos(portals.get(randomIndex).getRow(), portals.get(randomIndex).getCol());
							justTeleported = true;
							break;
						}

					}
				}
			} else if (map[row][col] != Code.portal && justTeleported) {
				justTeleported = false;
			}
		}
	}

	public void scanPortals() {
		for (int row1 = 0; row1 < map.length; row1++) {
			for (int col1 = 0; col1 < map[row1].length; col1++) {
				if (map[row1][col1] == Code.portal) {
					portals.add(new point(row1, col1));
				}
			}
		}
	}

	private void setPos(int row, int col) {
		this.row = row;
		this.col = col;
		x0 = col * Main.tilePadWidth;
		y0 = row * Main.tilePadWidth;
		x = x0 + centerOffset;
		y = y0 + centerOffset;
	}

	void respawn() {
		setPos(rowSpawn, colSpawn);
	}

	int getRow() {
		return row;
	}

	int getCol() {
		return col;
	}

	double getSpeed() {
		return speed;
	}

	void setSpeed(double speed) {
		this.speed = speed;
	}

	Color getColor() {
		return color;
	}

	boolean getDoShowPos() {
		return doShowPos;
	}

	void setDoShowPos(boolean state) {
		doShowPos = state;
	}

	protected boolean getEdible() {
		return edible;
	}

	protected void setEdible(boolean state) {
		// edible = state;
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

	boolean turnAround() {
		return true;
	}

	double getStdSpeed() {
		return stdSpeed;
	}

	// turn into inedible state when time comes
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == edibleTimer) {
			setEdible(false);
		}

	}

	protected class point {
		private int row, col;

		public point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}
	}

}
