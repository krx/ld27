package ld27;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, MouseListener{
	//Sleep time between draw calls for 60fps
	private static final int TICK = 1000/60;
	//Data used for FPS calculations
	private double startTime = 0;
	private double deltaTime = 0;
	private double fps = 0;
	private int frames = 0;
	
	
	public GamePanel(){
		setPreferredSize(new Dimension(1024,768));
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		initTimers();
	}
	
	/*
	 * Initializes timers for game
	 * 
	 * Brian: Would Timers or Threads be better to use for this?
	 */
	private void initTimers(){
		Timer t1 = new Timer();
		TimerTask tt1 = new TimerTask() {
			public void run() {
				repaint();
			}
		};
		t1.schedule(tt1, 0, TICK);
	}
	
	public void paint(Graphics g){
		//System.out.println(fps);
		
		calcFPS(); //Must be at end of method
	}
	
	/*
	 * Calculates game fps
	 */
	public void calcFPS() {
		frames++;
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		} else {
			double time = System.currentTimeMillis();
			deltaTime = time - startTime;
			if (deltaTime > 1000) {
				fps = (frames * 1000) / deltaTime;
				frames = 0;
				startTime = time;
			}
		}
	}
		

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {
		//System.out.println(e.getKeyChar());
	}
}