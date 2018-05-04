package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MapCreator {

	JFrame frmMapCreator;
	JFrame frmPacman = GameManager.window.frmPacman;
	int[][] map = GameManager.map;

	/**
	 * Create the application.
	 */
	public MapCreator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = GameManager.pathCode;
			}
		}

		frmMapCreator = new JFrame();
		frmMapCreator.setResizable(false);
		frmMapCreator.setTitle("Pac-Man Map Creator");
		frmMapCreator.setBounds(frmPacman.getX() + frmPacman.getWidth(), frmPacman.getY(), 450, 450);
		frmMapCreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMapCreator.getContentPane().setLayout(null);

		DrawPanel canvas = new DrawPanel();
		canvas.setBounds(0, 0, 309, 342);
		frmMapCreator.getContentPane().add(canvas);

		JButton btnPath = new JButton("path");
		btnPath.setBounds(315, 6, 117, 29);
		frmMapCreator.getContentPane().add(btnPath);

		JButton btnWall = new JButton("wall");
		btnWall.setBounds(315, 47, 117, 29);
		frmMapCreator.getContentPane().add(btnWall);

		JButton btnPacdot = new JButton("pacdot");
		btnPacdot.setBounds(315, 88, 117, 29);
		frmMapCreator.getContentPane().add(btnPacdot);

		JButton btnPowerPellet = new JButton("power pellet");
		btnPowerPellet.setBounds(315, 129, 117, 29);
		frmMapCreator.getContentPane().add(btnPowerPellet);

		JButton btnFruit = new JButton("fruit");
		btnFruit.setBounds(315, 170, 117, 29);
		frmMapCreator.getContentPane().add(btnFruit);

		JButton btnPacman = new JButton("pacman");
		btnPacman.setBounds(315, 211, 117, 29);
		frmMapCreator.getContentPane().add(btnPacman);

		JButton btnBlinky = new JButton("blinky");
		btnBlinky.setBounds(315, 252, 117, 29);
		frmMapCreator.getContentPane().add(btnBlinky);

		JButton btnPinky = new JButton("pinky");
		btnPinky.setBounds(315, 293, 117, 29);
		frmMapCreator.getContentPane().add(btnPinky);

		JButton btnInky = new JButton("inky");
		btnInky.setBounds(315, 334, 117, 29);
		frmMapCreator.getContentPane().add(btnInky);

		JButton btnClyde = new JButton("clyde");
		btnClyde.setBounds(315, 375, 117, 29);
		frmMapCreator.getContentPane().add(btnClyde);

		Timer t = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.repaint();
			}
		});
		t.start();
		Executors.newSingleThreadExecutor().execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					canvas.paintImmediately(canvas.getBounds());
				}
			}
		});
	}
}
