package ld27;

import javax.swing.JFrame;

public class DesktopGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePanel gp = new GamePanel();
		frame.add(gp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}	
}