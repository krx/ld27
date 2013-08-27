package ld27.player.types;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import ld27.GamePanel;
import ld27.player.Animation;
import ld27.player.Bullet;
import ld27.player.Player;


public class Ninja extends Player {
	private static final String root = "/graphics/Ninja/";
	private boolean stealthed = false;
	
	public boolean isStealthed() {
		return stealthed;
	}

	private long stealthStart = 0;
	private final static int DAMAGE = 30;
	private final static Sound swordHit = TinySound.loadSound(Ninja.class.getResource("/sounds/SwordHit.wav"));
	private final static Sound invisible = TinySound.loadSound(Ninja.class.getResource("/sounds/PossibleGoingInvisible.wav"));
	
	public Ninja(Color col) {
		super(300, col);
		loadAnims();
		keyController.attackRate = 20;
		anims.get(State.Attacking).setRate(10);
	}
	
	public Ninja(int x, int y, Color col) {
		this(col);
		playerX = x;
		playerY = y;
	}
	
	public void damage(int dmg){
		super.damage(dmg);
		if(stealthed)
			endStealth();
	}
	
	public void attack() {
		if(stealthed)
			endStealth();
		Rectangle hitBox = new Rectangle();
		if(!keyController.isLastMovingLeft()){
			hitBox.setBounds(playerX+64, playerY, 32, 64);
		} else {
			hitBox.setBounds(playerX-32, playerY, 32, 64);
		}
		for(int i=0; i<GamePanel.enemies.size(); i++){
			int x = GamePanel.enemies.get(i).playerX;
			int y = GamePanel.enemies.get(i).playerY;
			Rectangle bounds = new Rectangle(x, y, 64, 64);
			if(bounds.intersects(hitBox)){
				swordHit.play();
				GamePanel.enemies.get(i).damage(DAMAGE);
			}
		}
		Rectangle you = new Rectangle(GamePanel.player.playerX, GamePanel.player.playerY,64,64);
		if(hitBox.intersects(you))
			GamePanel.player.damage(DAMAGE);
	}
	
	public void ability() {
		stealthed = true;
		invisible.play();
		stealthStart = System.currentTimeMillis();
		playerXVel += 7;
		GamePanel.cam.MAX_SPEED+=7;
	}
	
	private void endStealth(){
		userInterface.cooldownAbility();
		stealthed = false;
		abilityStartTime = System.currentTimeMillis();
		playerXVel -= 7;
		GamePanel.cam.MAX_SPEED-=7;
	}

	public void loadAnims() {
		String[] idlePaths = {root + "Idle/DudeWithSword0.png",
							  root + "Idle/DudeWithSword1.png",
							  root + "Idle/DudeWithSword2.png",};
		String[] attackPaths = {root + "Attack/DudeWithSwordAttack0.png",
								root + "Attack/DudeWithSwordAttack1.png"};
		Animation idle = new Animation(idlePaths);
		Animation attack = new Animation(attackPaths);
		anims.put(State.Idle, idle);
		anims.put(State.Attacking, attack);
		activeAnim = anims.get(State.Idle);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		if(stealthed){
			if(System.currentTimeMillis()-stealthStart >= TEN_SECONDS){
				endStealth();
			}
			g.setColor(Color.BLACK);
			g.drawString("STEALTHED", playerX+GamePanel.cam.offx, playerY+GamePanel.cam.offy);
		}
		//For now this doesn't change anything, when multiplayer is added,
		//if stealthed==true, the ninja is not drawn to enemy clients
		
		
	}
}