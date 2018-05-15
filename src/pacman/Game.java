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

import pacman.Main.Direction;

public class Game implements KeyListener {

	private JFrame frmGame;
	private DrawPanel canvas;
	private JLabel lblScore;
	private MapCreator mapCreatorWindow = null;

	private final Action mapCreatorAction = new MapCreatorAction();
	private final Action togglePosAction = new TogglePosAction();
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
		frmGame.setBounds(400, 100, 349, 500);
		frmGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGame.getContentPane().setLayout(null);
		frmGame.addKeyListener(this);
		frmGame.setFocusable(true);
		frmGame.requestFocusInWindow();

		JLabel lblHighScore = new JLabel("High Score:");
		lblHighScore.setBounds(128, 6, 92, 16);
		frmGame.getContentPane().add(lblHighScore);

		lblScore = new JLabel("0");
		lblScore.setBounds(24, 24, 300, 16);
		frmGame.getContentPane().add(lblScore);

		canvas = new DrawPanel(frmGame, false, Main.framerate);
		canvas.setBounds(20, 50, Main.mapWidth, Main.mapHeight);
		frmGame.getContentPane().add(canvas);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(24, 392, 300, 40);
		frmGame.getContentPane().add(infoPanel);

		JLabel lblDebugControls = new JLabel("Debug Controls");
		lblDebugControls.setBounds(125, 434, 99, 16);
		frmGame.getContentPane().add(lblDebugControls);

		JPanel debugPanel = new JPanel();
		debugPanel.setBounds(0, 450, 349, 28);
		frmGame.getContentPane().add(debugPanel);
		debugPanel.setLayout(new BorderLayout(0, 0));

		JButton btnMapCreator = new JButton("Map Creator");
		debugPanel.add(btnMapCreator, BorderLayout.WEST);
		btnMapCreator.setAction(mapCreatorAction);

		JButton btnTogglePos = new JButton("Toggle Pos");
		debugPanel.add(btnTogglePos, BorderLayout.CENTER);
		btnTogglePos.setAction(togglePosAction);

		JButton btnToggleGrid = new JButton("Toggle Grid");
		btnToggleGrid.setAction(toggleGridAction);
		debugPanel.add(btnToggleGrid, BorderLayout.EAST);
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
		if (code == KeyEvent.VK_LEFT) {
			canvas.getPacman().logDir(Direction.left);
		}
		if (code == KeyEvent.VK_RIGHT) {
			canvas.getPacman().logDir(Direction.right);
		}
		if (code == KeyEvent.VK_UP) {
			canvas.getPacman().logDir(Direction.up);
		}
		if (code == KeyEvent.VK_DOWN) {
			canvas.getPacman().logDir(Direction.down);
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
		@Override
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				@Override
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

	private class TogglePosAction extends AbstractAction {
		public TogglePosAction() {
			putValue(NAME, "Toggle Pos");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getPacman().setDoShowPos(!canvas.getPacman().getDoShowPos());
			for (Ghost ghost : canvas.getGhosts()) {
				ghost.setDoShowPos(!ghost.getDoShowPos());
			}
			frmGame.requestFocusInWindow();
		}
	}

	private class ToggleGridAction extends AbstractAction {
		public ToggleGridAction() {
			putValue(NAME, "Toggle Grid");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.setDoDrawGrid(!canvas.getDoDrawGrid());
			frmGame.requestFocusInWindow();
		}
	}
}