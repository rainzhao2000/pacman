/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: 1 Jun 2018
 * Description:
 */

package pacman;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game implements KeyListener {

	private JFrame frmGame;
	private DrawPanel canvas;
	private JLabel lblScore;
	private MapCreator mapCreatorWindow = null;

	private int mapWidth = Main.mapWidth;
	private int mapHeight = Main.mapHeight;
	private int framerate = Main.framerate;

	private final Action mapCreatorAction = new MapCreatorAction();
	private final Action toggleGridAction = new ToggleGridAction();

	/**
	 * Create the application.
	 */
	public Game() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Set up the window (frame), and its elements
		frmGame = new JFrame();
		frmGame.setResizable(false);
		frmGame.setTitle("Pac-Man");
		frmGame.setBounds(400, 100, 309, 500);
		frmGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGame.getContentPane().setLayout(null);
		frmGame.addKeyListener(this);
		frmGame.setFocusable(true);

		JLabel lblHighScore = new JLabel("High Score:");
		lblHighScore.setBounds(118, 6, 72, 16);
		frmGame.getContentPane().add(lblHighScore);

		lblScore = new JLabel("0");
		lblScore.setBounds(14, 24, 280, 16);
		frmGame.getContentPane().add(lblScore);

		canvas = new DrawPanel(false, framerate);
		canvas.setBounds(0, 50, mapWidth, mapHeight);
		frmGame.getContentPane().add(canvas);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(14, 392, 280, 40);
		frmGame.getContentPane().add(infoPanel);

		JLabel lblDebugControls = new JLabel("Debug Controls");
		lblDebugControls.setBounds(105, 434, 99, 16);
		frmGame.getContentPane().add(lblDebugControls);

		JPanel debugPanel = new JPanel();
		debugPanel.setBounds(0, 450, 309, 28);
		frmGame.getContentPane().add(debugPanel);
		debugPanel.setLayout(new BorderLayout(0, 0));

		JButton btnMapCreator = new JButton("Map Creator");
		debugPanel.add(btnMapCreator, BorderLayout.WEST);
		btnMapCreator.setAction(mapCreatorAction);

		JButton btnToggleGrid = new JButton("Toggle Grid");
		btnToggleGrid.setAction(toggleGridAction);
		debugPanel.add(btnToggleGrid, BorderLayout.EAST);

		Main.defaultMap(frmGame, canvas);
	}

	JFrame getFrame() {
		return frmGame;
	}

	DrawPanel getCanvas() {
		return canvas;
	}

	JLabel getScoreLabel() {
		return lblScore;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_DOWN) {
			Main.pacman.down();
		}
		if (code == KeyEvent.VK_UP) {
			Main.pacman.up();
		}
		if (code == KeyEvent.VK_LEFT) {
			Main.pacman.left();
		}
		if (code == KeyEvent.VK_RIGHT) {
			Main.pacman.right();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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
					canvas.setPaused(true);
					System.out.println("Game Paused");
					if (mapCreatorWindow != null) {
						mapCreatorWindow.getFrame().dispose();
					}
					mapCreatorWindow = new MapCreator();
					mapCreatorWindow.getFrame().setVisible(true);
				}
			});
		}
	}

	private class ToggleGridAction extends AbstractAction {
		public ToggleGridAction() {
			putValue(NAME, "Toggle Grid");
		}

		public void actionPerformed(ActionEvent e) {
			canvas.setDoDrawGrid(!canvas.getDoDrawGrid());
		}
	}
}