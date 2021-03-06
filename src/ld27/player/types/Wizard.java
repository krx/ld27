package ld27.player.types;

import java.awt.Color;

import ld27.GamePanel;
import ld27.player.Animation;
import ld27.player.Bullet;
import ld27.player.HealBall;
import ld27.player.MageBall;
import ld27.player.Player;
import ld27.player.SuperBullet;
import ld27.player.Player.State;

public class Wizard extends Player {
	private static final String root = "/graphics/Wizard/";
	
	public Wizard(Color col) {
		super(250, col);
		loadAnims();
		keyController.attackRate = 20;
	}

	public Wizard(int x, int y, Color col) {
		this(col);
		playerX = x;
		playerY = y;
	}

	public void attack() {
		if(!keyController.isLastMovingLeft()){
			MageBall b = new MageBall(false, playerX+64, playerY+4);
			GamePanel.bullets.add(b);
		} else {
			MageBall b = new MageBall(true, playerX-32, playerY+4);
			GamePanel.bullets.add(b);
		}
	}

	public void ability() {
		if(!keyController.isLastMovingLeft()){
			HealBall b = new HealBall(false, playerX+64, playerY+4);
			GamePanel.bullets.add(b);
		} else {
			HealBall b = new HealBall(true, playerX-32, playerY+4);
			GamePanel.bullets.add(b);
		}
	}

	public void loadAnims() {
		String[] idlePaths = {root + "Idle/WizardPassive0.png",
							  root + "Idle/WizardPassive1.png",
							  root + "Idle/WizardPassive2.png",
							  root + "Idle/WizardPassive3.png",};
		String[] attackPaths = {root + "Attack/WizardAttack0.png",
								root + "Attack/WizardAttack1.png"};
		Animation idle = new Animation(idlePaths);
		Animation attack = new Animation(attackPaths);
		attack.setRate(10);
		anims.put(State.Idle, idle);
		anims.put(State.Attacking, attack);
		activeAnim = anims.get(State.Idle);
	}
}