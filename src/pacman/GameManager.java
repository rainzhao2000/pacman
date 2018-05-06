/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: 1 Jun 2018
 * Description:
 */

package pacman;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameManager {

	JFrame frmPacman;
	DrawPanel canvas;
	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;
	private int framerate = Main.framerate;
	private MapCreator mapCreatorWindow = null;
	private final Action mapCreatorAction = new MapCreatorAction();

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

		canvas = new DrawPanel();
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

		// Create new thread if first time
		if (Main.gameManagerCanvasThread == null) {
			Main.gameManagerPaused = false;
			Main.gameManagerCanvasThread = new Thread(new RepaintRunnable("game manager", Main.gameManagerPaused, framerate, canvas));
			Main.gameManagerCanvasThread.start();
		}
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
					Main.gameManagerPaused = true;
					System.out.println("Game Paused");
					if (mapCreatorWindow != null) {
						mapCreatorWindow.frmMapCreator.dispose();
					}
					mapCreatorWindow = new MapCreator();
					mapCreatorWindow.frmMapCreator.setVisible(true);
				}
			});
		}
	}
}