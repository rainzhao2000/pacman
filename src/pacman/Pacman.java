package pacman;

public class Pacman {
	// For the direction variable:
	// 1 represents left, 2 represents right, 3 represents up, 4 represents down.
	// For the coordinate variables:
	// the first row is row 0 and the first column is col 0
	private static int speed, rPos, cPos, direction;
	private static boolean alive;

	// Pacman constructor
	public Pacman(int r, int c, int s, int dir) {
		rPos = r;
		cPos = c;
		speed = s;
		direction = dir;
		alive = true;
	}

	// return speed
	public static int getSpeed() {
		return speed;
	}

	// return rPos
	public static int getrPos() {
		return rPos;
	}

	// return cPos
	public static int getcPos() {
		return cPos;
	}

	// update the position of the Pacman
	// return false if dead, return true for any other situation
	public static boolean update() {
		if (direction == 1) {
			// left
			// check boundary
			// check restriction
			// check ghost
		} else if (direction == 2) {
			// right

		} else if (direction == 3) {
			// up

		} else {
			// down

		}
	}

	// return true if the block at r and c is not a wall
	// return false if the block at r and c is a wall
	// set alive to false if the block at r and c is a ghost
	private static boolean check(int r, int c) {
		if (Main.map[r][c] == Main.Codes.wall) {
			// wall

		} else if (Main.map[r][c] == Main.Codes.pacdot) {
			// pacdot

		} else if (Main.map[r][c] == Main.Codes.powerPellet) {
			// pwoer pellet

		} else if (Main.map[r][c] == Main.Codes.fruit) {
			// fruit

		} else if (Main.map[r][c] == Main.Codes.blinky || Main.map[r][c] == Main.Codes.inky
				|| Main.map[r][c] == Main.Codes.pinky || Main.map[r][c] == Main.Codes.clyde) {
			// ghost

		} else {
			// path
			
		}
	}

}
