/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: wallCode Jun 2pathCodewallCode8
 * Description:
 */

package pacman;

import java.awt.Canvas;
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
	private static final int pathCode = 0;
	private static final int wallCode = 1;
	private static final int pacdotCode = 2;
	private static final int powerPelletCode = 3;
	private static final int fruitCode = 4;
	private static final int pacmanCode = 5;
	private static final int blinkyCode = 6;
	private static final int pinkyCode = 7;
	private static final int inkyCode = 8;
	private static final int clydeCode = 9;
	static int[][] map/* = {
			{ wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, pathCode },
			{ wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, wallCode, }
			}*/;
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
		frmPacman.setBounds(100, 100, 309, 500);
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPacman.getContentPane().setLayout(null);
		
		Canvas canvas = new Canvas();
		canvas.setBounds(0, 0, 309, 397);
		frmPacman.getContentPane().add(canvas);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 397, 309, 81);
		frmPacman.getContentPane().add(panel);
		
		JLabel lblDebugControls = new JLabel("Debug Controls");
		
		JButton btnMapCreator = new JButton("Map Creator");
		btnMapCreator.setAction(mapCreatorAction);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(105)
					.addComponent(lblDebugControls, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(105))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(94)
					.addComponent(btnMapCreator, GroupLayout.PREFERRED_SIZE, 42, Short.MAX_VALUE)
					.addGap(95))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(7)
					.addComponent(lblDebugControls)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnMapCreator)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
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
