package pacman;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pacman.Main.Code;

public class MapCreator {

	private Code[][] map = Main.map;
	private Code currentObject = Code.path;

	private JFrame frmMapCreator;
	private JFrame frmPacman = Main.game.getFrame();
	private DrawPanel canvas;
	private DrawPanel gameCanvas = Main.game.getCanvas();
	private JTextField txtFieldFileName;

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
	private final Action defaultMapSelect = new DefaultMapSelect();
	private final Action blankMapSelect = new BlankMapSelect();
	private final Action loadMapSelect = new LoadMapSelect();
	private final Action saveMapSelect = new SaveMapSelect();

	/**
	 * Create the application.
	 */
	public MapCreator() {
		initialize();
		// Unpause the canvas rendering in Game on close of MapCreator
		frmMapCreator.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				gameCanvas.setPaused(false);
				System.out.println("Game Unpaused");
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Set up the window (frame), and its elements
		frmMapCreator = new JFrame();
		frmMapCreator.setResizable(false);
		frmMapCreator.setTitle("Pac-Man Map Creator");
		frmMapCreator.setBounds(frmPacman.getX() + frmPacman.getWidth(), frmPacman.getY(), 450, 498);
		frmMapCreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMapCreator.getContentPane().setLayout(null);

		canvas = new DrawPanel(frmMapCreator, true, Main.framerate);
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				canvas.setTile(e, currentObject);
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				canvas.setTile(e, currentObject);
			}
		});
		canvas.setBounds(0, 0, Main.mapWidth, Main.mapHeight);
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

		JButton btnDefaultMap = new JButton("default map");
		btnDefaultMap.setAction(defaultMapSelect);
		btnDefaultMap.setBounds(0, 354, 117, 29);
		frmMapCreator.getContentPane().add(btnDefaultMap);

		JButton btnBlankMap = new JButton("blank map");
		btnBlankMap.setAction(blankMapSelect);
		btnBlankMap.setBounds(129, 354, 117, 29);
		frmMapCreator.getContentPane().add(btnBlankMap);

		JButton btnLoadMap = new JButton("load map");
		btnLoadMap.setAction(loadMapSelect);
		btnLoadMap.setBounds(0, 393, 117, 29);
		frmMapCreator.getContentPane().add(btnLoadMap);

		JButton btnSaveMap = new JButton("save map");
		btnSaveMap.setAction(saveMapSelect);
		btnSaveMap.setBounds(129, 393, 117, 29);
		frmMapCreator.getContentPane().add(btnSaveMap);

		txtFieldFileName = new JTextField();
		txtFieldFileName.setBounds(129, 434, 134, 28);
		frmMapCreator.getContentPane().add(txtFieldFileName);
		txtFieldFileName.setColumns(10);

		JLabel lblSaveAsFile = new JLabel("Save As File Name:");
		lblSaveAsFile.setBounds(10, 440, 123, 16);
		frmMapCreator.getContentPane().add(lblSaveAsFile);
	}

	JFrame getFrame() {
		return frmMapCreator;
	}

	/*
	 * The following classes are Actions attributed to button components in the
	 * frame and sets the currentObject
	 */
	private class PathSelect extends AbstractAction {
		public PathSelect() {
			putValue(NAME, "path");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.path;
			System.out.println(currentObject + " selected");
		}
	}

	private class WallSelect extends AbstractAction {
		public WallSelect() {
			putValue(NAME, "wall");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.wall;
			System.out.println(currentObject + " selected");
		}
	}

	private class PacdotSelect extends AbstractAction {
		public PacdotSelect() {
			putValue(NAME, "pacdot");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.pacdot;
			System.out.println(currentObject + " selected");
		}
	}

	private class PowerPelletSelect extends AbstractAction {
		public PowerPelletSelect() {
			putValue(NAME, "power pellet");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.powerPellet;
			System.out.println(currentObject + " selected");
		}
	}

	private class FruitSelect extends AbstractAction {
		public FruitSelect() {
			putValue(NAME, "fruit");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.fruit;
			System.out.println(currentObject + " selected");
		}
	}

	private class PacmanSelect extends AbstractAction {
		public PacmanSelect() {
			putValue(NAME, "pacman");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.pacman;
			System.out.println(currentObject + " selected");
		}
	}

	private class BlinkySelect extends AbstractAction {
		public BlinkySelect() {
			putValue(NAME, "blinky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.blinky;
			System.out.println(currentObject + " selected");
		}
	}

	private class PinkySelect extends AbstractAction {
		public PinkySelect() {
			putValue(NAME, "pinky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.pinky;
			System.out.println(currentObject + " selected");
		}
	}

	private class InkySelect extends AbstractAction {
		public InkySelect() {
			putValue(NAME, "inky");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.inky;
			System.out.println(currentObject + " selected");
		}
	}

	private class ClydeSelect extends AbstractAction {
		public ClydeSelect() {
			putValue(NAME, "clyde");
		}

		public void actionPerformed(ActionEvent e) {
			currentObject = Code.clyde;
			System.out.println(currentObject + " selected");
		}
	}

	/*
	 * The following classes are actions attributed to the buttons that handle
	 * map selection, saving and loading
	 */
	private class DefaultMapSelect extends AbstractAction {
		public DefaultMapSelect() {
			putValue(NAME, "default map");
		}

		public void actionPerformed(ActionEvent e) {
			canvas.defaultMap();
		}
	}

	private class BlankMapSelect extends AbstractAction {
		public BlankMapSelect() {
			putValue(NAME, "blank map");
		}

		public void actionPerformed(ActionEvent e) {
			canvas.blankMap();
		}
	}

	private class LoadMapSelect extends AbstractAction {
		public LoadMapSelect() {
			putValue(NAME, "load map");
		}

		/*
		 * Looks for a chosen file in current directory and uploads it to map
		 */
		public void actionPerformed(ActionEvent e) {
			// Open JFileChooser dialog to import map file
			ArrayList<String> lines = new ArrayList<String>();
			File file = new File("default map.txt");
			BufferedReader reader = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int result = fileChooser.showOpenDialog(frmMapCreator);
			if (result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}

			// Read each line as string and add to ArrayList of strings
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = null;

				while ((line = reader.readLine()) != null) {
					lines.add(line);
				}
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(frmMapCreator, "File not found.", "File not found",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e1) {
				}
			}

			// upload ArrayList of strings to map array
			if (!canvas.uploadMap(lines)) {
				JOptionPane.showMessageDialog(frmMapCreator, "Invalid text file structure and/or codes", "Invalid Map",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class SaveMapSelect extends AbstractAction {
		public SaveMapSelect() {
			putValue(NAME, "save map");
		}

		/*
		 * Writes the current map code values to a text file of a specified path
		 */
		public void actionPerformed(ActionEvent e) {
			if (txtFieldFileName.getText().equals("")) {
				JOptionPane.showMessageDialog(frmMapCreator, "You need to enter a file name.", "File name error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// Get the code values of each element in map array and add to
				// ArrayList of strings
				ArrayList<String> lines = new ArrayList<String>();
				for (int row = 0; row < map.length; row++) {
					StringBuilder line = new StringBuilder();
					for (int col = 0; col < map[row].length; col++) {
						System.out.println(map[row][col].getCode());
						line.append(map[row][col].getCode() + " ");
					}
					lines.add(line.toString());
				}

				// Write ArrayList of strings to file
				Path file = Paths.get(txtFieldFileName.getText() + ".txt");
				try {
					Files.write(file, lines, Charset.forName("UTF-8"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}