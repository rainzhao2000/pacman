package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Character {

	protected Code[][] map = Main.map;

	protected Direction dir;

	protected DrawPanel canvas;
	protected Color color;

	protected int rowSpawn, colSpawn, row, col, x0, y0;
	
	protected double x, y, speed, displacement; // speed in tiles/sec
	protected double centerOffset = Main.tilePadWidth / 2.0;
	
	protected boolean doAnimate;

	void draw(Graphics g) {

	}

	protected void animate(Graphics g) {
		g.setColor(color);
		if (doAnimate) {
			g.fillRect(x0, y0, Main.tilePadWidth, Main.tilePadWidth);
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
		} else {
			g.fillRect(x0, y0, Main.tilePadWidth, Main.tilePadWidth);
		}
		row = y0 / Main.tilePadWidth;
		col = x0 / Main.tilePadWidth;
	}
	
	protected boolean checkTile(int row, int col) {
		return false;
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

	void setRow(int row) {
		this.row = row;
	}

	int getCol() {
		return col;
	}

	void setCol(int col) {
		this.col = col;
	}

	Direction getDir() {
		return dir;
	}

	void setDir(Direction dir) {
		this.dir = dir;
	}

	double getX() {
		return x;
	}
	
	double getY() {
		return y;
	}
	
}
