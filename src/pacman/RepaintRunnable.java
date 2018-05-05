package pacman;

public class RepaintRunnable implements Runnable {

	String name;
	private int framerate;
	private DrawPanel canvas;
	
	public RepaintRunnable(String name, int framerate, DrawPanel canvas) {
		this.name = name;
		this.framerate = framerate;
		this.canvas = canvas;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / framerate); // milliseconds
			} catch (InterruptedException e) {
				break;
			}
			canvas.repaint();
			System.out.println("painting " + name);
		}
	}

}
