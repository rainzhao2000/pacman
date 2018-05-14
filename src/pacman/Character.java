package pacman;

import java.awt.Graphics;

import pacman.Main.Codes;
import pacman.Main.Directions;

public class Character {

	protected Codes[][] map = Main.map;

	protected Directions dir;

	// speed in tiles/sec
	protected int rowSpawn, colSpawn, row, col, x, y, width, height, speed;

	void draw(Graphics g) {
		switch (dir) {
		case left:
			if (checkTile(row, col - 1)) {
				col--;
			}
			break;
		case right:
			if (checkTile(row, col + 1)) {
				col++;
			}
			break;
		case up:
			if (checkTile(row - 1, col)) {
				row--;
			}
			break;
		case down:
			if (checkTile(row + 1, col)) {
				row++;
			}
			break;
		}
	}

	protected boolean checkTile(int row, int col) {
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
			// exceed boundary
			return false;
		} else if (map[row][col] == Codes.wall) {
			// wall
			return false;
		} else {
			// path
			return true;
		}
	}

	void respawn() {
		row = rowSpawn;
		col = colSpawn;
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

	Directions getDir() {
		return dir;
	}

	void setDir(Directions dir) {
		this.dir = dir;
	}

}
