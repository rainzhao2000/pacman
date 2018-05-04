package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MapCreator {

	JFrame frmMapCreator;
	JFrame frmPacman = GameManager.window.frmPacman;
	int[][] map = GameManager.map;
	int tilePadWidth = GameManager.tilePadWidth;
	int mapWidth = GameManager.mapWidth;
	int mapHeight = GameManager.mapHeight;
	boolean paused = GameManager.paused;
	private int currentObject = 0;
	private final Action pathSelect = new PathSelect();
	private final Action wallSelect = new WallSelect();
	private final Action pacdotSelect = new PacdotSelect();
	private final Action powerPelletSelect = new PowerPelletSelect();
	private final Action fruitSelect = new FruitSelect();
	private final Action pacmanSelect = new PacmanSelect();
	private final Action blinkySelect = new BlinkySelect();
	private final Action pinkySelect = new PinkySelect();
	private final Action inkySelect = new InkySelect();
	private final Action clydeSelect = new ClydeSelect();

	/**
	 * Create the application.
	 */
	public MapCreator() {
		initialize();
		// Unpause the canvas rendering in GameManager on close of MapCreator
		frmMapCreator.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				paused = false;
				System.out.println("Game Unpaused");
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		blankMap();

		// Set up the window (frame), and its elements
		frmMapCreator = new JFrame();
		frmMapCreator.setResizable(false);
		frmMapCreator.setTitle("Pac-Man Map Creator");
		frmMapCreator.setBounds(frmPacman.getX() + frmPacman.getWidth(), frmPacman.getY(), 450, 450);
		frmMapCreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMapCreator.getContentPane().setLayout(null);

		DrawPanel canvas = new DrawPanel();
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setTile(e);
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setTile(e);
			}
		});
		canvas.setBounds(0, 0, mapWidth, mapHeight);
		frmMapCreator.getContentPane().add(canvas);

		JButton btnPath = new JButton("path");
		btnPath.setAction(pathSelect);
		btnPath.setBounds(315, 6, 117, 29);
		frmMapCreator.getContentPane().add(btnPath);

		JButton btnWall = new JButton("wall");
		btnWall.setAction(wallSelect);
		btnWall.setBounds(315, 47, 117, 29);
		frmMapCreator.getContentPane().add(btnWall);

		JButton btnPacdot = new JButton("pacdot");
		btnPacdot.setAction(pacdotSelect);
		btnPacdot.setBounds(315, 88, 117, 29);
		frmMapCreator.getContentPane().add(btnPacdot);

		JButton btnPowerPellet = new JButton("power pellet");
		btnPowerPellet.setAction(powerPelletSelect);
		btnPowerPellet.setBounds(315, 129, 117, 29);
		frmMapCreator.getContentPane().add(btnPowerPellet);

		JButton btnFruit = new JButton("fruit");
		btnFruit.setAction(fruitSelect);
		btnFruit.setBounds(315, 170, 117, 29);
		frmMapCreator.getContentPane().add(btnFruit);

		JButton btnPacman = new JButton("pacman");
		btnPacman.setAction(pacmanSelect);
		btnPacman.setBounds(315, 211, 117, 29);
		frmMapCreator.getContentPane().add(btnPacman);

		JButton btnBlinky = new JButton("blinky");
		btnBlinky.setAction(blinkySelect);
		btnBlinky.setBounds(315, 252, 117, 29);
		frmMapCreator.getContentPane().add(btnBlinky);

		JButton btnPinky = new JButton("pinky");
		btnPinky.setAction(pinkySelect);
		btnPinky.setBounds(315, 293, 117, 29);
		frmMapCreator.getContentPane().add(btnPinky);

		JButton btnInky = new JButton("inky");
		btnInky.setAction(inkySelect);
		btnInky.setBounds(315, 334, 117, 29);
		frmMapCreator.getContentPane().add(btnInky);

		JButton btnClyde = new JButton("clyde");
		btnClyde.setAction(clydeSelect);
		btnClyde.setBounds(315, 375, 117, 29);
		frmMapCreator.getContentPane().add(btnClyde);

		// Repaint the canvas with paintImmediately() for synchronous painting
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

	/*
	 * Reset all values in map array to 0 (path)
	 */
	private void blankMap() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col] = GameManager.pathCode;
			}
		}
	}

	private void setTile(MouseEvent e) {
		int col = e.getX() / tilePadWidth;
		int row = e.getY() / tilePadWidth;
		map[row][col] = currentObject;
	}

	private class PathSelect extends AbstractAction {
		public PathSelect() {
			putValue(NAME, "path");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.pathCode;
			System.out.println("path selected " + currentObject);
		}
	}

	private class WallSelect extends AbstractAction {
		public WallSelect() {
			putValue(NAME, "wall");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.wallCode;
			System.out.println("wall selected " + currentObject);
		}
	}

	private class PacdotSelect extends AbstractAction {
		public PacdotSelect() {
			putValue(NAME, "pacdot");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.pacdotCode;
			System.out.println("pacdot selected " + currentObject);
		}
	}

	private class PowerPelletSelect extends AbstractAction {
		public PowerPelletSelect() {
			putValue(NAME, "power pellet");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.powerPelletCode;
			System.out.println("power pellet selected " + currentObject);
		}
	}

	private class FruitSelect extends AbstractAction {
		public FruitSelect() {
			putValue(NAME, "fruit");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.fruitCode;
			System.out.println("fruit selected " + currentObject);
		}
	}

	private class PacmanSelect extends AbstractAction {
		public PacmanSelect() {
			putValue(NAME, "pacman");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.pacmanCode;
			System.out.println("pacman selected " + currentObject);
		}
	}

	private class BlinkySelect extends AbstractAction {
		public BlinkySelect() {
			putValue(NAME, "blinky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.blinkyCode;
			System.out.println("blinky selected " + currentObject);
		}
	}

	private class PinkySelect extends AbstractAction {
		public PinkySelect() {
			putValue(NAME, "pinky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.pinkyCode;
			System.out.println("pinky selected " + currentObject);
		}
	}

	private class InkySelect extends AbstractAction {
		public InkySelect() {
			putValue(NAME, "inky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.inkyCode;
			System.out.println("inky selected " + currentObject);
		}
	}

	private class ClydeSelect extends AbstractAction {
		public ClydeSelect() {
			putValue(NAME, "clyde");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = GameManager.clydeCode;
			System.out.println("clyde selected " + currentObject);
		}
	}
}
