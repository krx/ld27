package ld27.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.EnumMap;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import ld27.GamePanel;
import ld27.map.Map;
import ld27.map.Tile;
import ld27.player.Bullet.State;

public class MageBall extends Animatable{

	private static final String root = "/graphics/Wizard/Bullet/Normal/";
	protected EnumMap<State, Animation> anims = new EnumMap<State, Animation>(State.class);
	protected boolean flipped;
	protected Rectangle bounds;
	protected final int MAX_VELOCITY = 15;
	protected int x,y;
	private final static int DAMAGE = 15;
	private final static Sound laser = TinySound.loadSound(Bullet.class.getResource("/sounds/LaserShoot.wav"));
	private final static Sound laserHit = TinySound.loadSound(Bullet.class.getResource("/sounds/HitWithLaser.wav"));
	
	public MageBall(boolean flipped, int x, int y){
		loadAnims();
		this.flipped = flipped;
		activeAnim.setFlipped(flipped);
		this.x=x;
		this.y=y;
		bounds = new Rectangle(0,0,32,32);
		laser.play();
	}

	public void loadAnims() {
		String[] paths = {root+"WizardBulletNormal.png"};
		Animation flying = new Animation(paths);
		anims.put(State.Flying, flying);
		activeAnim = anims.get(State.Flying);
	}
	
	public void paint(Graphics g){
		activeAnim.paint(g, x+GamePanel.cam.offx, y+GamePanel.cam.offy);
		x+= flipped ? -MAX_VELOCITY : MAX_VELOCITY;
		checkFutureLocation(x, y);
	}
	
	public void checkFutureLocation(int x, int y){
		Tile[][] map = Map.getMapData()[0];
		Rectangle futureBox = new Rectangle(x, y, bounds.width, bounds.height);
		for (int i = 0; i < Map.getWidth(); i++ ){
			for (int j = 0; j < Map.getHeight(); j++){
				if (map[j][i].getBounds().intersects(futureBox)){
					GamePanel.bullets.remove(this);
					return;
				}
			}
		}
		for(int i=0; i<GamePanel.enemies.size(); i++){
			int px = GamePanel.enemies.get(i).playerX;
			int py = GamePanel.enemies.get(i).playerY;
			Rectangle pBounds = new Rectangle(px, py, 64, 64);
			if(pBounds.intersects(futureBox)){
				GamePanel.enemies.get(i).damage(DAMAGE);
				laserHit.play();
				GamePanel.bullets.remove(this);
				return;
			}
		}
		Rectangle you = new Rectangle(GamePanel.player.playerX, GamePanel.player.playerY,64,64);
		if(futureBox.intersects(you)){
			GamePanel.player.damage(DAMAGE);
			laserHit.play();
			GamePanel.bullets.remove(this);
			return;
		}
	}
}