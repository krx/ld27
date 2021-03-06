package ld27;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import kuusisto.tinysound.TinySound;

@SuppressWarnings("serial")
public class DesktopGame extends JFrame {
	public static final GraphicsDevice DEVICE = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int HEIGHT = AppletGame.HEIGHT;
	public static final int WIDTH = AppletGame.WIDTH;
	public Component PANEL;
	public KeyListener KEY = new KeyAdapter() {
		public void keyReleased(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_F) {
				setFullscreen(DEVICE.getFullScreenWindow() == null);
			}
		}
	};

	public static void main(String[] args) {
		new DesktopGame();
	}
	public DesktopGame(){
		TinySound.init();
		PANEL = new GameMenu(this);
		add(PANEL);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		PANEL.addKeyListener(KEY);
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
			public void run() {
				repaint();
			}
		}, 1, 1000/60);
	}
	
	public void setFullscreen(boolean fulls) {
		if (fulls) {
			PANEL.setPreferredSize(new Dimension(1, 1));
			setVisible(false);
			dispose();
			setUndecorated(true);
			setVisible(true);
			DEVICE.setFullScreenWindow(this);
			PANEL.requestFocus();
		} else {
			PANEL.setPreferredSize(new Dimension(1, 1));
			setVisible(false);
			dispose();
			setUndecorated(false);
			setVisible(true);
			DEVICE.setFullScreenWindow(null);
			PANEL.requestFocus();
			getContentPane().setSize(800, 600);
		}
	}

}