package pacman;

import java.awt.Color;
import java.awt.Graphics;

import pacman.Main.Code;
import pacman.Main.Direction;

public class Character {

	protected Code[][] map = Main.map;

	protected Direction dir;

	protected Color color;

	// speed in tiles/sec
	protected int rowSpawn, colSpawn, row, col, x, y, speed;

	void draw(Graphics g) {
		
	}
	
	protected boolean checkTile(int row, int col) {
		return false;
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

	Direction getDir() {
		return dir;
	}

	void setDir(Direction dir) {
		this.dir = dir;
	}

}
