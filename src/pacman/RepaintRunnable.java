package pacman;

public class RepaintRunnable implements Runnable {

	String name;
	private int framerate;
	private DrawPanel canvas;

	public RepaintRunnable(String name, int framerate, DrawPanel canvas) {
		this.name = name;
		this.framerate = framerate;
		this.canvas = canvas;
		System.out.println(name + " canvas thread created");
	}

	@Override
	public void run() {
		while (running) {
			synchronized (canvas) {
				while (true) {
					if (paused) {
						try {
							canvas.wait();
						} catch (InterruptedException e) {
							System.out.println(name + " canvas.wait() interrupted");
							break;
						}
					}
				}
			}
			try {
				Thread.sleep(1000 / framerate); // milliseconds
			} catch (InterruptedException e) {
				System.out.println(name + " Thread.sleep() interrupted");
				break;
			}
			canvas.repaint();
			System.out.println("painting " + name);
		}
	}

}
