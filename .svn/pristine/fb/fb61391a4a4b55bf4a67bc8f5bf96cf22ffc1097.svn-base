package ld27.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu {
	
	public interface MenuFunction {
		/**
		 * Generates name image for MenuFunction f to be displayed in the Menu
		 * @return String
		 */
		public String name();
		/**
		 * Code to execute when the MenuFunction is selected
		 */
		public void run();
	}
	private class Selection {
		private BufferedImage img=null;
		private MenuFunction f=null;
		private Selection(BufferedImage img, MenuFunction f){
			this.img=img;
			this.f=f;
		}
	}
	public static int
		SCREEN_WIDTH = Scale.SCREEN_WIDTH,
		SCREEN_HEIGHT = Scale.SCREEN_HEIGHT,
		LETTER_IMG_WIDTH = Scale.LETTER_IMG_WIDTH, 
		LETTER_IMG_HEIGHT = Scale.LETTER_IMG_HEIGHT,
		MENU_SPACE = Scale.MENU_SPACE;
	private boolean isVisible=false;
	private int SELECTOR_POSITION=0;
	private BufferedImage title=null;
	private ArrayList<Selection> selections = new ArrayList<Selection>();
	/**
	 * Title is displayed as twice the height and width of the MenuFunction letters
	 * @param title
	 */
	public Menu(String title){
		this.title=Words.menuWord(title,LETTER_IMG_WIDTH*2,LETTER_IMG_HEIGHT*2);
	}
	/**
	 * Paints Menu into passed Graphics
	 * @param g
	 */
	public void paintMenu(Graphics g){
		int y=SCREEN_HEIGHT/2-((LETTER_IMG_HEIGHT+MENU_SPACE)*(selections.size()/2));
		int x=SCREEN_WIDTH/2;
		g.drawImage(title,x-title.getWidth()/2,y-(title.getHeight()+MENU_SPACE),title.getWidth(),title.getHeight(),null);
		for(int i=0; i<selections.size(); i++){
			g.drawImage(i==SELECTOR_POSITION?paintSelector(selections.get(i).img):selections.get(i).img,x-selections.get(i).img.getWidth()/2,y,selections.get(i).img.getWidth(),selections.get(i).img.getHeight(),null);
			y+=LETTER_IMG_HEIGHT+MENU_SPACE;
		}
	}
	private BufferedImage paintSelector(BufferedImage img){
		BufferedImage i = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
		for(int y=0; y<i.getHeight(); y++){
			for(int x=0; x<i.getWidth(); x++){
				i.setRGB(x,y,img.getRGB(x,y)==Words.LETTER_COLOR.hashCode()?Color.GREEN.hashCode():Words.VOID_COLOR.hashCode());
			}
		}
		return i;
	}
	/**
	 * Sets the visible state of the Menu
	 * @param b
	 */
	public void setVisible(boolean b){
		isVisible=b;
	}
	/**
	 * Resets the selector to the zero position of the Menu (top selection)
	 */
	public void resetSelect(){
		SELECTOR_POSITION=0;
	}
	/**
	 * Calls the run method of whichever selection is currently selected
	 */
	public void select(){
		selections.get(SELECTOR_POSITION).f.run();
	}
	/**
	 * Moves the selector one position up in the Menu
	 */
	public void selectorUp(){
		if(SELECTOR_POSITION-1>=0){
			SELECTOR_POSITION--;
		}
	}
	/**
	 * Moves the selector one position down in the Menu
	 */
	public void selectorDown(){
		if(SELECTOR_POSITION+1<selections.size()){
			SELECTOR_POSITION++;
		}
	}
	/**
	 * Returns the visible state of the menu
	 * @return boolean
	 */
	public boolean isVisible(){
		return isVisible;
	}
	/**
	 * Adds the new MenuFunction to an array of functions in the Menu.
	 * The function is automatically displayed when paintMenu(Graphics g) is called
	 * @param f
	 */
	public void addSelection(MenuFunction f){
		selections.add(new Selection(Words.menuWord(f.name(),LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT),f));
	}
}