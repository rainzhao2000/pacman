/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Date: June 4, 2018
 * Description: This class is the super class of Ghost and Pacman. It contains 
 * 				the common characteristics of pacman and ghosts. Methods in 
 * 				this class might be overridden in subclasses.
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

public class Character {

	// declaration and/or initialization of protected objects

	protected Code[][] map = Main.map;

	protected Direction dir;

	protected DrawPanel canvas;
	protected Color color;

	protected int rowSpawn, colSpawn, row, col, x0, y0, lastRow, lastCol;
	protected final double stdSpeed = 5;

	// speed in tiles per second
	protected double x, y, speed, displacement;
	protected double centerOffset = Main.tilePadWidth / 2.0;

	protected boolean isFixed, doAnimate, move, doShowPos, edible, firstEdible, justTeleported;
	protected boolean left, right, up, down;

	// the ArrayList that stores all the cordinate of the portal
	protected ArrayList<point> portals;

	// constructor of the character class
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
		move = true;
		portals = new ArrayList<>();
		lastRow = -1;
		lastCol = -1;
		justTeleported = false;
	}

	// This method is meant to be overidden by the subclasses
	void draw(Graphics g) {

	}

	// This method handles the animation of the characters
	protected void animate(Graphics g) {

		// handle the speed multiplier
		Object value = Main.game.getSpeedMultiplierSpinner().getValue();
		try {
			displacement = speed * (double) value * Main.tilePadWidth / canvas.getFramerate();
		} catch (ClassCastException cce) {
			displacement = speed * (int) value * Main.tilePadWidth / canvas.getFramerate();
		}

		// check if show position is toggled
		if (doShowPos) {
			g.setColor(Color.GREEN);
			g.fillRect(col * Main.tilePadWidth, row * Main.tilePadWidth, Main.tilePadWidth, Main.tilePadWidth);
		}

		// change the color of the character if edible
		if (edible) {
			g.setColor(new Color(166, 124, 255));
		} else {
			g.setColor(color);
		}

		// draw the image of the character
		drawShape(g);

		// if able to move
		if (move) {
			// if can do animation
			if (doAnimate) {
				// move
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
				// update x0 and y0
				x0 = (int) (x - centerOffset);
				y0 = (int) (y - centerOffset);
			}
			// set current row and column based on the x and y coordinates
			row = (int) Math.rint((double) y0 / Main.tilePadWidth);
			col = (int) Math.rint((double) x0 / Main.tilePadWidth);
		}
	}

	// This class renders the image of the character. It is meant to be overridden
	// by subclasses
	protected void drawShape(Graphics g) {

	}

	// This method checks the surrounding of the character and store the
	// availability in corresponding variables
	protected void checkSurrounding() {
		doAnimate = true;
		left = checkTile(row, col - 1);
		right = checkTile(row, col + 1);
		up = checkTile(row - 1, col);
		down = checkTile(row + 1, col);
		// if the character is not in-between tiles
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

	// a method created for subclasses to override
	protected void selectDir() {

	}

	// return if the character can go to the tile at the given location
	private boolean checkTile(int row, int col) {
		// out of bound
		if (!canvas.inBounds(row, col)) {
			return false;
		}
		// if it is a wall, return false
		if (map[row][col] == Code.wall) {
			return false;
		}

		// any other case, the character can move to the tile at this location
		return true;
	}

	// this method handles the teleporting process of the character
	private void checkPortal(int row, int col) {
		if (lastRow == row && lastCol == col) {
			// prevent detecting the same portal multiple times
			return;
		} else {
			// at a new location, update lastRow and lastCol to prevent future recalculation
			lastRow = row;
			lastCol = col;

			// at a portal and not just teleported
			if (map[row][col] == Code.portal && !justTeleported) {
				// if there is more than or equal to 2 portals
				if (portals.size() >= 2) {
					while (true) {
						// teleport to a random exit
						int randomIndex = (int) (Math.random() * portals.size());
						if (portals.get(randomIndex).getRow() != row || portals.get(randomIndex).getCol() != col) {
							setPos(portals.get(randomIndex).getRow(), portals.get(randomIndex).getCol());
							justTeleported = true;
							// re-check surrounding to prevent going through walls
							checkSurrounding();
							break;
						}

					}
				}
			} else {
				// any other case, set the justTeleported to false
				justTeleported = false;
			}
		}
	}

	// scan and store all the location of portals in the portals ArrayList
	public void scanPortals() {
		for (int row1 = 0; row1 < map.length; row1++) {
			for (int col1 = 0; col1 < map[row1].length; col1++) {
				if (map[row1][col1] == Code.portal) {
					portals.add(new point(row1, col1));
				}
			}
		}
	}

	// set the character to the given location
	private void setPos(int row, int col) {
		this.row = row;
		this.col = col;
		x0 = col * Main.tilePadWidth;
		y0 = row * Main.tilePadWidth;
		x = x0 + centerOffset;
		y = y0 + centerOffset;
	}

	// reset the location of the character to its spawning position
	void respawn() {
		setPos(rowSpawn, colSpawn);
	}

	// accessor method
	int getRow() {
		return row;
	}

	// accessor method
	int getCol() {
		return col;
	}

	// accessor method
	int getx0() {
		return x0;
	}

	// accessor method
	int gety0() {
		return y0;
	}

	// accessor method
	double getSpeed() {
		return speed;
	}

	// accessor method
	Color getColor() {
		return color;
	}

	// accessor method
	boolean getDoShowPos() {
		return doShowPos;
	}

	// accessor method
	protected boolean getEdible() {
		return edible;
	}

	// accessor method
	boolean turnAround() {
		return true;
	}

	// accessor method
	double getStdSpeed() {
		return stdSpeed;
	}

	// modifier method
	void setSpeed(double speed) {
		this.speed = speed;
	}

	// modifier method
	void setDoShowPos(boolean state) {
		doShowPos = state;
	}

	// a class that simply stores a row number and a column number
	protected class point {
		// private variables
		private int row, col;

		// constructor
		public point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		// accessor method
		public int getRow() {
			return this.row;
		}

		// accessor method
		public int getCol() {
			return this.col;
		}
	}

}
