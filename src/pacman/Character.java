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

	protected DrawPanel canvas;
	protected Color color;
	protected Timer timer;

	protected int rowSpawn, colSpawn, row, col;
	protected double x, y, speed; // speed in tiles/sec

	public void actionPerformed(ActionEvent e) {
		
	}
	
	void draw(Graphics g) {
		
	}
	
	protected boolean checkTile(int row, int col) {
		return false;
	}
	
	void respawn() {
		row = rowSpawn;
		col = colSpawn;
		x = col * Main.tilePadWidth;
		y = row * Main.tilePadWidth;
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
