/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: 1 Jun 2018
 * Description:
 */

package pacman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import pacman.Main.Direction;
import javax.swing.SwingConstants;

public class Game implements KeyListener {

	private JFrame frmGame;
	private JLabel lblScore, lblCurrentLives;
	private JSpinner speedMultiplierSpinner;
	private JPanel infoPanel;
	private DrawPanel canvas;
	private MapCreator mapCreatorWindow = null;

	private final Action mapCreatorAction = new MapCreatorAction();
	private final Action togglePosAction = new TogglePosAction();
	private final Action toggleGridAction = new ToggleGridAction();
	private final Action resetAction = new ResetAction();

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
		frmGame.setBounds(400, 100, 355, 558);
		frmGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGame.getContentPane().setLayout(null);
		frmGame.addKeyListener(this);
		frmGame.setFocusable(true);
		frmGame.requestFocusInWindow();

		JLabel lblHighScore = new JLabel("High Score:");
		lblHighScore.setBounds(20, 11, 92, 16);
		frmGame.getContentPane().add(lblHighScore);

		lblScore = new JLabel("0");
		lblScore.setBounds(20, 23, 238, 16);
		frmGame.getContentPane().add(lblScore);

		canvas = new DrawPanel(frmGame, false, false, Main.framerate);
		canvas.setBounds(20, 50, Main.mapWidth, Main.mapHeight);
		frmGame.getContentPane().add(canvas);

		infoPanel = new JPanel();
		infoPanel.setBounds(24, 392, 300, 40);
		frmGame.getContentPane().add(infoPanel);

		// JLabel lblDebugControls = new JLabel("Debug Controls");
		// lblDebugControls.setBounds(125, 434, 99, 16);
		// frmGame.getContentPane().add(lblDebugControls);

		JPanel debugPanel = new JPanel();
		debugPanel.setBounds(0, 450, 349, 78);
		debugPanel.setLayout(new BorderLayout(0, 0));
		debugPanel.setBackground(Color.lightGray);
		frmGame.getContentPane().add(debugPanel);

		JButton btnMapCreator = new JButton("Map Creator");
		debugPanel.add(btnMapCreator, BorderLayout.WEST);
		btnMapCreator.setAction(mapCreatorAction);

		JButton btnTogglePos = new JButton("Toggle Pos");
		debugPanel.add(btnTogglePos, BorderLayout.CENTER);
		btnTogglePos.setAction(togglePosAction);

		JButton btnToggleGrid = new JButton("Toggle Grid");
		btnToggleGrid.setAction(toggleGridAction);
		debugPanel.add(btnToggleGrid, BorderLayout.EAST);

		JPanel debugPanel2 = new JPanel();
		debugPanel2.setBackground(Color.LIGHT_GRAY);
		debugPanel.add(debugPanel2, BorderLayout.SOUTH);

		JButton btnReset = new JButton("Reset Game");
		debugPanel2.add(btnReset);
		btnReset.setAction(resetAction);

		JLabel lblSpeedMultiplier = new JLabel("Speed multiplier:");
		debugPanel2.add(lblSpeedMultiplier);

		speedMultiplierSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 0.1));
		Component[] comps = speedMultiplierSpinner.getEditor().getComponents();
		for (Component component : comps) {
			component.setFocusable(false);
		}
		debugPanel2.add(speedMultiplierSpinner);

		JLabel lblLives = new JLabel("Lives");
		lblLives.setBounds(283, 12, 46, 14);
		frmGame.getContentPane().add(lblLives);

		lblCurrentLives = new JLabel(Integer.toString(Main.pacman.getLives()));
		lblCurrentLives.setBounds(283, 24, 46, 14);
		frmGame.getContentPane().add(lblCurrentLives);
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

	JLabel getCurrentLivesLabel() {
		return lblCurrentLives;
	}

	JSpinner getSpeedMultiplierSpinner() {
		return speedMultiplierSpinner;
	}

	JPanel getInfoPanel() {
		return infoPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT) {
			Main.pacman.logDir(Direction.left);
		}
		if (code == KeyEvent.VK_RIGHT) {
			Main.pacman.logDir(Direction.right);
		}
		if (code == KeyEvent.VK_UP) {
			Main.pacman.logDir(Direction.up);
		}
		if (code == KeyEvent.VK_DOWN) {
			Main.pacman.logDir(Direction.down);
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
		 * When the component this action is attached to is triggered, pause the current
		 * canvas rendering (so that resources can be allocated to the MapCreator
		 * canvas), then try to close any existing open MapCreator window and
		 * instantiate a new MapCreator window
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					canvas.setPaused(true);
					System.out.println("Game Paused");
					if (mapCreatorWindow != null) {
						mapCreatorWindow.getCanvas().close();
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
			Main.pacman.setDoShowPos(!Main.pacman.getDoShowPos());
			for (Ghost ghost : Main.ghosts) {
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

	private class ResetAction extends AbstractAction {
		public ResetAction() {
			putValue(NAME, "Reset Game");
		}

		public void actionPerformed(ActionEvent e) {
			canvas.reset(false);
			frmGame.requestFocusInWindow();
		}
	}
}