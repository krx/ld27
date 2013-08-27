package ld27.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import ld27.util.FileIOHelper;

public class Animation {
	private ArrayList<Image> frames = new ArrayList<Image>();
	private int currentFrame = 0;
	private int tick = 0;
	private int rate = 12;
	private boolean flipped = false;
	public Animation(Image... aniFrames) {
		addFrames(aniFrames);
	}
	public Animation(String... paths) {
		addFrames(paths);
	}
	public void addFrames(Image... aniFrames) {
		for (Image i:aniFrames)
			frames.add(i);
	}
	public void addFrames(String... paths) {
		for (String s:paths)
			try {
				frames.add(ImageIO.read(FileIOHelper.loadResource(s)));
			} catch (IOException e) {
		}
	}
	public boolean getFlipped() {
		return flipped;
	}
	public int getRate() {
		return rate;
	}
	public void insertFrame(int index, Image frame) {
		frames.add(index, frame);
	}
	public void insertFrame(int index, String path) {
		try {
			frames.add(index, ImageIO.read(Animation.class.getResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void removeFrame(int index) {
		frames.remove(index);
	}
	public void setFlipped(boolean f) {
		flipped = f;
	}
	public void setRate(int r) {
		rate = r;
	}
	public void paint(Graphics g, int x, int y) {
		if (frames.isEmpty())
			return;
		if (!flipped)
			g.drawImage(frames.get(currentFrame), x, y, null);
		else {
			Image i = frames.get(currentFrame);
			g.drawImage(i, x, y, x+i.getWidth(null), y+i.getHeight(null),
					i.getWidth(null),0,0,i.getHeight(null),null);
		}
		if (++tick >= rate) {
			tick = 0;
			currentFrame++;
			currentFrame %= frames.size();
		}
	}
	public void paint(Graphics g, int x, int y, HashMap<Color, Color> replace) {
		if (frames.isEmpty())
			return;
		Image i = frames.get(currentFrame);
		BufferedImage re = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics rg = re.getGraphics();
		rg.drawImage(i, 0, 0, null);
		for (Entry<Color, Color> p:replace.entrySet()) {
			for (int c=0; c<re.getWidth(); c++) {
				for (int r=0; r<re.getHeight(); r++) {
					if (re.getRGB(c, r) == p.getKey().hashCode()) {
						re.setRGB(c, r, p.getValue().hashCode());
					}
				}
			}
		}
		if (!flipped)
			g.drawImage(re, x, y, null);
		else
			g.drawImage(re, x, y, x+i.getWidth(null), y+i.getHeight(null),
					i.getWidth(null),0,0,i.getHeight(null),null);
		if (++tick >= rate) {
			tick = 0;
			currentFrame++;
			currentFrame %= frames.size();
		}
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public int getLength(){
		return frames.size();
	}
}
