package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pacman.Main.Codes;

public class Ghost implements ActionListener {
	private int row, col, direction;
	private double updatePeriod;
	private Codes[][] map = Main.map;
	private boolean edible;
	private Timer refreshTimer;

	// edible timer
	// refresh timer
	// etc

	public Ghost(int row, int col, int updatePeriod) {
		this.edible = false;
		this.row = row;
		this.col = col;
		refreshTimer = new Timer((int) (1000 / updatePeriod), this);
		refreshTimer.start();
	}

	// Repaints the panel at the conditions of the timer

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshTimer) {
			update();
		}
	}

	// return speed
	public double getupdatePeriod() {
		return updatePeriod;
	}

	// return row
	public int getrow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	// return col
	public int getcol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean getState() {
		return edible;
	}

	public void respawn() {
		// reset row and col
		// reset to ghost house
	}

	// modifier method for the edible variable of the ghost
	public void changeState(boolean newState) {
		this.edible = newState;
	}

	public void update() {
		if (direction == 1) {
			// left
			if (check(row, col - 1)) {
				col--;
			} else {
				direction = (int) (Math.random() * 4 + 1);
			}
		} else if (direction == 2) {
			// right
			if (check(row, col + 1)) {
				col++;
			} else {
				direction = (int) (Math.random() * 4 + 1);
			}
		} else if (direction == 3) {
			// up
			if (check(row - 1, col)) {
				row--;
			} else {
				direction = (int) (Math.random() * 4 + 1);
			}
		} else {
			// down
			if (check(row + 1, col)) {
				row++;
			} else {
				direction = (int) (Math.random() * 4 + 1);
			}
		}
	}

	private boolean check(int r, int c) {
		if (r < 0 || r >= map.length || c < 0 || c >= map[0].length) {
			// exceed boundary
			return false;
		} else if (map[r][c] == Codes.wall) {
			// wall
			return false;
		} else {
			return true;
		}
	}
//	
//	private boolean intersection() {
//		
//	}
}
