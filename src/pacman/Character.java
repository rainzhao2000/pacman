package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Character implements ActionListener {

	protected Code[][] map = Main.map;

	protected Direction dir;

	protected Timer edibleTimer;

	protected DrawPanel canvas;
	protected Color color;

	protected int rowSpawn, colSpawn, row, col, x0, y0, edibleTime;
	protected final double stdSpeed = 5;

	protected double x, y, speed, displacement; // speed in tiles/sec
	protected double centerOffset = Main.tilePadWidth / 2.0;

	protected boolean isFixed;
	protected boolean doAnimate;
	protected boolean move;
	protected boolean doShowPos;
	protected boolean left, right, up, down;
	protected boolean edible, firstEdible;

	protected Character(DrawPanel canvas, Direction dir, int row, int col, Color color, boolean isFixed) {
		this.canvas = canvas;
		this.dir = dir;
		this.rowSpawn = row;
		this.colSpawn = col;
		this.row = row;
		this.col = col;
		x0 = col * Main.tilePadWidth;
		y0 = row * Main.tilePadWidth;
		x = x0 + centerOffset;
		y = y0 + centerOffset;
		speed = 5;
		this.color = color;
		this.isFixed = isFixed;
		this.doAnimate = !isFixed;
		doShowPos = false;
		this.edible = false;
		this.firstEdible = false;
		this.edibleTime = 10000;
		move = true;
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

	void respawn() {
		row = rowSpawn;
		col = colSpawn;
		x0 = col * Main.tilePadWidth;
		y0 = row * Main.tilePadWidth;
		x = x0 + centerOffset;
		y = y0 + centerOffset;
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
		// this.edible = state;
		if (state) {
			if (!this.edible) {
				this.firstEdible = true;
			}
			edibleTimer = new Timer(edibleTime, this);
			edibleTimer.start();
		} else {
			edibleTimer.stop();
			this.speed = stdSpeed;
		}
		this.edible = state;
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
			this.setEdible(false);
		}

	}

}
