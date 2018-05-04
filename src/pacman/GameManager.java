/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: 1 Jun 2018
 * Description:
 */

package pacman;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameManager {

	static GameManager window;
	private MapCreator mapCreatorWindow;
	JFrame frmPacman;
	static final int pathCode = 0;
	static final int wallCode = 1;
	static final int pacdotCode = 2;
	static final int powerPelletCode = 3;
	static final int fruitCode = 4;
	static final int pacmanCode = 5;
	static final int blinkyCode = 6;
	static final int pinkyCode = 7;
	static final int inkyCode = 8;
	static final int clydeCode = 9;
	static int[][] map = new int[31][28];
	static final int tileWidth = 10;
	static final int padding = 1;
	static final int tilePadWidth = tileWidth + padding;
	static final int mapWidth = map[0].length * tileWidth + (map[0].length + 1) * padding;
	static final int mapHeight = map.length * tileWidth + (map.length + 1) * padding;
	private final Action mapCreatorAction = new MapCreatorAction();
	static boolean paused = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GameManager();
					window.frmPacman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Set up the window (frame), and its elements
		frmPacman = new JFrame();
		frmPacman.setResizable(false);
		frmPacman.setTitle("Pac-Man");
		frmPacman.setBounds(400, 100, 309, 500);
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPacman.getContentPane().setLayout(null);

		JLabel lblHighScore = new JLabel("High Score:");
		lblHighScore.setBounds(118, 6, 72, 16);
		frmPacman.getContentPane().add(lblHighScore);

		JLabel lblScore = new JLabel("0");
		lblScore.setBounds(14, 24, 280, 16);
		frmPacman.getContentPane().add(lblScore);

		DrawPanel canvas = new DrawPanel();
		canvas.setBounds(0, 50, mapWidth, mapHeight);
		frmPacman.getContentPane().add(canvas);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(14, 392, 280, 40);
		frmPacman.getContentPane().add(infoPanel);

		JLabel lblDebugControls = new JLabel("Debug Controls");
		lblDebugControls.setBounds(105, 434, 99, 16);
		frmPacman.getContentPane().add(lblDebugControls);

		JButton btnMapCreator = new JButton("Map Creator");
		btnMapCreator.setBounds(94, 449, 120, 29);
		frmPacman.getContentPane().add(btnMapCreator);
		btnMapCreator.setAction(mapCreatorAction);

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
				while (!paused) {
					canvas.paintImmediately(canvas.getBounds());
				}
			}
		});
	}

	// Action that opens the MapCreator window
	private class MapCreatorAction extends AbstractAction {
		public MapCreatorAction() {
			putValue(NAME, "Map Creator");
			putValue(SHORT_DESCRIPTION, "Customize the map");
		}

		/*
		 * When the component this action is attached to is triggered, pause the
		 * current canvas rendering (so that resources can be allocated to the
		 * MapCreator canvas), then try to close any existing open MapCreator
		 * window and instantiate a new MapCreator window
		 */
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					paused = true;
					System.out.println("Game Paused");
					try {
						mapCreatorWindow.frmMapCreator.dispose();
						mapCreatorWindow = new MapCreator();
						mapCreatorWindow.frmMapCreator.setVisible(true);
					} catch (Exception e) {
						mapCreatorWindow = new MapCreator();
						mapCreatorWindow.frmMapCreator.setVisible(true);
					}
				}
			});
		}
	}
}
