/*
 * Collaborators: Jacky Zhao and Rain Zhao
 * Due Date: 1 Jun 2018
 * Description:
 */

package pacman;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GameManager {

	private JFrame frmPacman;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameManager window = new GameManager();
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
		frmPacman.setSize(600, 800);
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
