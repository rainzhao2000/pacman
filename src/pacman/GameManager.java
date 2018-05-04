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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

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
	private final Action mapCreatorAction = new MapCreatorAction();

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
		frmPacman = new JFrame();
		frmPacman.setResizable(false);
		frmPacman.setTitle("Pac-Man");
		frmPacman.setBounds(100, 100, 309, 515);
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPacman.getContentPane().setLayout(null);
		 
		 JLabel lblHighScore = new JLabel("High Score:");
		 lblHighScore.setBounds(118, 6, 72, 16);
		 frmPacman.getContentPane().add(lblHighScore);
		 
		 JLabel lblScore = new JLabel("0");
		 lblScore.setBounds(14, 24, 280, 16);
		 frmPacman.getContentPane().add(lblScore);

		 DrawPanel canvas = new DrawPanel();
		 canvas.setBounds(0, 50, 309, 342);
		 frmPacman.getContentPane().add(canvas);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(14, 392, 280, 40);
		frmPacman.getContentPane().add(infoPanel);

		JPanel debugPanel = new JPanel();
		debugPanel.setBounds(0, 432, 309, 60);
		frmPacman.getContentPane().add(debugPanel);

		JLabel lblDebugControls = new JLabel("Debug Controls");

		JButton btnMapCreator = new JButton("Map Creator");
		btnMapCreator.setAction(mapCreatorAction);
		GroupLayout gl_debugPanel = new GroupLayout(debugPanel);
		gl_debugPanel.setHorizontalGroup(gl_debugPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_debugPanel.createSequentialGroup().addGap(105)
						.addComponent(lblDebugControls, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE).addGap(105))
				.addGroup(gl_debugPanel.createSequentialGroup().addGap(94)
						.addComponent(btnMapCreator, GroupLayout.PREFERRED_SIZE, 42, Short.MAX_VALUE).addGap(95)));
		gl_debugPanel.setVerticalGroup(gl_debugPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_debugPanel.createSequentialGroup().addGap(7).addComponent(lblDebugControls)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnMapCreator).addContainerGap(23,
								Short.MAX_VALUE)));
		debugPanel.setLayout(gl_debugPanel);
	}

	private class MapCreatorAction extends AbstractAction {
		public MapCreatorAction() {
			putValue(NAME, "Map Creator");
			putValue(SHORT_DESCRIPTION, "Customize the map");
		}

		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
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
