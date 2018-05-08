package pacman;

public class Pacman {
	// For the direction variable:
	// 1 represents left, 2 represents right, 3 represents up, 4 represents down.
	// For the coordinate variables:
	// the top left corner grid of the map has x position of 0 and y position of 0.
	private static int speed, xPos, yPos, direction;
	private static boolean alive;

	// Pacman constructor
	public Pacman(int x, int y, int s, int dir) {
		xPos = x;
		yPos = y;
		speed = s;
		direction = dir;
		alive = true;
	}

	// return speed
	public static int getSpeed() {
		return speed;
	}

	// return xPos
	public static int getxPos() {
		return xPos;
	}

	// return yPos
	public static int getyPos() {
		return yPos;
	}

	// update the position of the Pacman
	// returns true if position moved, return false if stuck or dead
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

	private static boolean check(int x, int y) {

	}

}
