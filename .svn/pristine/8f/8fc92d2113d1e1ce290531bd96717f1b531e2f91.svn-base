package ld27.map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage img;
	private int x,y,layer, gid;
	private Rectangle bounds;
	
	public Tile(BufferedImage img, int x, int y, int layer, int gid){
		this.img = img;
		this.x = x;
		this.y = y;
		this.layer = layer;
		this.gid = gid;
		bounds = gid == 0 ? new Rectangle(0,0,0,0) : new Rectangle(x*img.getWidth(), y*img.getHeight(), img.getWidth(), img.getHeight());
	}
	
	public void paint(Graphics g, int offx, int offy){
		if(gid==0) return;
		g.drawImage(img, x*img.getWidth()+offx, y*img.getHeight()+offy, null);
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
}