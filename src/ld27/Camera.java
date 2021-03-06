package ld27;

import java.awt.Rectangle;

import ld27.map.Map;
import ld27.player.Player;
import ld27.util.MoreMath;

public class Camera {
	public Rectangle viewport;
	public int offx=0, offy=0;
	public int centerX, centerY;
	private Player following = null;
	public int MAX_SPEED = 7;
	
	public Camera(int w, int h, Player p){
		viewport = new Rectangle(800,600,w,h);
		following = p;
	}
	
	public boolean canMoveX(int dx){
		if(dx<0)
			return offx + dx >= -Map.getPxWidth()+viewport.width;
		else if(dx>0)
			return offx + dx <= 0;
		return false;
	}
	
	public boolean canMoveY(int dy){
		if(dy<0)
			return offy + dy >= -Map.getPxHeight()+viewport.height;
		else if(dy>0)
			return offy + dy <= 0;
		return false;
	}
	
	public void updatePosition(){
		centerX = viewport.width/2 - offx;
		centerY = viewport.height/2 - offy;
		if(following == null)
			return;
		if(!centeredOnPlayer()){
			int dx = centerX-following.playerX;
			int dy = centerY-following.playerY;
			tryMove(MoreMath.clamp(dx, -MAX_SPEED, MAX_SPEED), MoreMath.clamp(dy, -MAX_SPEED, MAX_SPEED));
		}
	}
	
	public void tryMove(int dx, int dy){
		if(canMoveX(dx)){
			offx += dx;
		}
		if(canMoveY(dy)){
			offy += dy;
		}
	}
		
	
	private boolean centeredOnPlayer(){
		boolean x = following.playerX == centerX;
		boolean y = following.playerY == centerY;
		return x && y;
	}
}