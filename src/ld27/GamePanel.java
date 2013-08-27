package ld27;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ld27.AI.EnemyAI;
import ld27.map.Map;
import ld27.map.Maps;
import ld27.map.XMLParser;
import ld27.menu.Menu;
import ld27.menu.Menu.MenuFunction;
import ld27.menu.Words;
import ld27.player.Animatable;
import ld27.player.Animation;
import ld27.player.Player;
import ld27.player.types.GunDude;
import ld27.player.types.Ninja;
import ld27.player.types.Wizard;
import ld27.util.FileIOHelper;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	public static enum Screen {
		PLAYER_CONFIG, GAMEPLAY, PAUSE, PLAYAGAIN1, PLAYAGAIN2, INSTRUCTIONS, CLASS;
	}
	//Sleep time between draw calls for 60fps
	private static final int FRAMERATE = 1000/60;
	//Data used for FPS calculations
	private double startTime = 0;
	private double deltaTime = 0;
	private double fps = 0;
	private int frames = 0;
	private int selectedCharacter = 0;
	private Color selectedColor = new Color(65, 255, 0);
	private Map map;
	public static Camera cam;
	private static BufferedImage background;
	
	
	public static Screen screen = Screen.PLAYER_CONFIG;
	private static final String gunDudePrefix = "/graphics/GunDude/";
	private Animation gunDude = new Animation(gunDudePrefix + "Idle/DudeWithGunPassive0.png",
			gunDudePrefix + "Idle/DudeWithGunPassive1.png", gunDudePrefix + "Idle/DudeWithGunPassive2.png",
			gunDudePrefix + "Idle/DudeWithGunPassive3.png");
	private static final String NinjaPrefix = "/graphics/Ninja/";
	private Animation ninja = new Animation(NinjaPrefix + "Idle/DudeWithSword0.png",
			NinjaPrefix + "Idle/DudeWithSword1.png", NinjaPrefix + "Idle/DudeWithSword2.png");
	private static final String wizardPrefix = "/graphics/Wizard/";
	private Animation wizard = new Animation(wizardPrefix + "Idle/WizardPassive0.png",
							  wizardPrefix + "Idle/WizardPassive1.png",
							  wizardPrefix + "Idle/WizardPassive2.png",
							  wizardPrefix + "Idle/WizardPassive3.png");
	private Animation[] characters = {gunDude, ninja, wizard};
	private int rows = 0, rc = 0;
	private Menu pauseMenu;
	private Container parent;
	private Menu paMenu1;
	private Menu paMenu2;
	private Container origParent;
	public static ArrayList<Animatable> bullets = new ArrayList<Animatable>();
	public static ArrayList<Player> allies = new ArrayList<Player>();
	public static ArrayList<Player> enemies = new ArrayList<Player>();
	public static Player player;
	public static EnemyAI enemyController;
	
	public GamePanel(final Container pnt){
		origParent = pnt;
		parent = pnt;
		setPreferredSize(new Dimension(800,600));
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		map = XMLParser.loadMap(Maps.MainMap);
		//player = new Ninja(300, randomColor());
		player = null;
		enemies.add(randomEnemy());
		enemyController = new EnemyAI();
		cam = new Camera(800, 600, player);
		//cam.offy = -1000;
		final GamePanel gamePanel = this;
		
		pauseMenu = new Menu("PAUSED");
		pauseMenu.addSelection(new MenuFunction() {
			public void run() {
				screen = Screen.GAMEPLAY;
			}
			public String name() {
				return "Resume";
			}
		});
		pauseMenu.addSelection(new MenuFunction() {
			public void run() {
				parent.remove(gamePanel);
				GameMenu panel = new GameMenu(pnt);
				parent.add(panel);
				panel.setSize(parent.getWidth(), parent.getHeight());
				if (pnt instanceof AppletGame) {
					((AppletGame) pnt).PANEL = panel;
					panel.addKeyListener(((AppletGame) pnt).KEY);
				} else if (pnt instanceof DesktopGame) {
					((DesktopGame) pnt).PANEL = panel;
					if (DesktopGame.DEVICE.getFullScreenWindow() == null)
						((DesktopGame) pnt).pack();
					panel.addKeyListener(((DesktopGame) pnt).KEY);
				}
				panel.requestFocus();
				panel.setParent(parent);
			}
			public String name() {
				return "End Game";
			}
		});
		
		paMenu1 = new Menu("YOU WIN");
		paMenu2 = new Menu("YOU LOSE");
		paMenu1.addSelection(new MenuFunction() {
			public void run() {
				bullets.clear();
				allies.clear();
				enemies.clear();
				screen = Screen.PLAYER_CONFIG;
				enemies.add(randomEnemy());
			}
			public String name() {
				return "Play Again";
			}
		});
		paMenu1.addSelection(new MenuFunction() {
			public void run() {
				parent.remove(gamePanel);
				GameMenu panel = new GameMenu(pnt);
				parent.add(panel);
				panel.setSize(parent.getWidth(), parent.getHeight());
				if (pnt instanceof AppletGame) {
					((AppletGame) pnt).PANEL = panel;
					panel.addKeyListener(((AppletGame) pnt).KEY);
				} else if (pnt instanceof DesktopGame) {
					((DesktopGame) pnt).PANEL = panel;
					if (DesktopGame.DEVICE.getFullScreenWindow() == null)
						((DesktopGame) pnt).pack();
					panel.addKeyListener(((DesktopGame) pnt).KEY);
				}
				panel.requestFocus();
				panel.setParent(parent);
			}
			public String name() {
				return "Quit";
			}
		});
		paMenu2.addSelection(new MenuFunction() {
			public void run() {
				bullets.clear();
				allies.clear();
				enemies.clear();
				screen = Screen.PLAYER_CONFIG;
				enemies.add(randomEnemy());
			}
			public String name() {
				return "Play Again";
			}
		});
		paMenu2.addSelection(new MenuFunction() {
			public void run() {
				parent.remove(gamePanel);
				GameMenu panel = new GameMenu(pnt);
				parent.add(panel);
				panel.setSize(parent.getWidth(), parent.getHeight());
				if (pnt instanceof AppletGame) {
					((AppletGame) pnt).PANEL = panel;
					panel.addKeyListener(((AppletGame) pnt).KEY);
				} else if (pnt instanceof DesktopGame) {
					((DesktopGame) pnt).PANEL = panel;
					if (DesktopGame.DEVICE.getFullScreenWindow() == null)
						((DesktopGame) pnt).pack();
					panel.addKeyListener(((DesktopGame) pnt).KEY);
				}
				panel.requestFocus();
				panel.setParent(parent);
			}
			public String name() {
				return "Quit";
			}
		});
		
		try {
			background = ImageIO.read(FileIOHelper.loadResource("/graphics/Background/MoonBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initTimer();
		
		
	}
	
	private Player randomEnemy(){
		switch((int)(Math.random()*3)){
			case 0: return new GunDude(4900+(int)(Math.random()*50),1300, randomColor());
			case 1: return new Ninja(4900+(int)(Math.random()*50),1300, randomColor());
			case 2: return new Wizard(4900+(int)(Math.random()*50),1300, randomColor());
		}
		return null;
	}
	
	public static void endGame(boolean leftSide){
		if(leftSide){
			screen = Screen.PLAYAGAIN1;
		} else {
			screen = Screen.PLAYAGAIN2;
		}
	}
	
	private Color randomColor(){
		return new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	}
	
	//COMMENTS ARE FOR SCRUBS
	public void tick(){
		if (screen == Screen.GAMEPLAY) {
			cam.updatePosition();
			cam.viewport.x = -cam.offx;
			cam.viewport.y  = -cam.offy;
			//if(cam.viewport.width != getWidth() || cam.viewport.height != getHeight()){
				//cam.offx=0;
				//cam.offy=0;
				//cam.viewport.width = getWidth();
				//cam.viewport.height = getHeight();
				//System.out.println(cam.viewport.width + " " + cam.viewport.height);
			//}
		}
		
		for (int i =0; i < enemies.size(); i++){
			if (enemies.get(i)!=null && enemyController != null && player != null){
				enemyController.tick(enemies.get(i), player);
			}
		}
	}
	
	public void paint(Graphics g){
		BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics bg = img.getGraphics();
		bg.setColor(Color.WHITE);
		bg.fillRect(0, 0, 800, 600);
		switch (screen) {
			case PLAYER_CONFIG:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				bg.setColor(new Color(0xCCCCCC));
				String title = "CHARACTER SELECTION";
				bg.drawImage(Words.menuWord(title, 790/title.length()-5, 80), 10, 10, null);
				String inst = "ASWD CHARACTER - DRAG COLOR - ENTER PLAY";
				bg.drawImage(Words.menuWord(inst, 790/inst.length()-5, 20), 10, 100, null);
				int x = characters.length * 100;
				rc = 0;
				rows = 0;
				if (x < 800) {
					x = (800 - x) / 2;
					rc = characters.length;
					rows = 1;
				} else {
					x =  0;
					rc = 8;
					rows = (int) Math.ceil(characters.length * 1.0 / rc);
				}
				int y = (400 - rows) / 2 + 100;
				int sx = x;
				HashMap<Color, Color> replace = new HashMap<Color, Color>();
				replace.put(new Color(65, 255, 0), selectedColor);
				for (int i = 0; i < characters.length; i++) {
					if (i == selectedCharacter) {
						bg.setColor(new Color(0x111111));
						bg.drawRect(x, y, 100, 100);
						bg.drawRect(x + 15, y + 15, 70, 70);
						bg.setColor(selectedColor);
						bg.fillRect(x + 1, y + 1, 99, 14);
						bg.fillRect(x + 1, y + 1, 14, 99);
						bg.fillRect(x + 1, y + 86, 99, 14);
						bg.fillRect(x + 86, y + 1, 14, 99);
					}
					characters[i].paint(bg, x + 18, y + 18, replace);
					x += 100;
					if ((i + 1) % rc == 0) {
						x = sx;
						y += 100;
					}
					for (int j=0; j<256; j++) {
						bg.setColor(new Color(j, selectedColor.getGreen(), selectedColor.getBlue()));
						bg.fillRect(16+3*j, 500, 3, 20);
						if (selectedColor.getRed() == j) {
							bg.setColor(Color.BLACK);
							bg.drawRect(16+3*j-1, 499, 3, 22);
						}
					}
					for (int j=0; j<256; j++) {
						bg.setColor(new Color(selectedColor.getRed(), j, selectedColor.getBlue()));
						bg.fillRect(16+3*j, 530, 3, 20);
						if (selectedColor.getGreen() == j) {
							bg.setColor(Color.BLACK);
							bg.drawRect(16+3*j-1, 529, 3, 22);
						}
					}
					for (int j=0; j<256; j++) {
						bg.setColor(new Color(selectedColor.getRed(), selectedColor.getGreen(), j));
						bg.fillRect(16+3*j, 560, 3, 20);
						if (selectedColor.getBlue() == j) {
							bg.setColor(Color.BLACK);
							bg.drawRect(16+3*j-1, 559, 3, 22);
						}
					}
				}
				break;
			case GAMEPLAY:
				int bgx = player.playerX*-300/map.getPxWidth();
				int bgy = player.playerY*-200/map.getPxHeight();
				bg.drawImage(background, bgx, bgy, null);
				map.paint(bg, cam.offx, cam.offy, cam.viewport);
				for(int i=0; i<allies.size(); i++){
					allies.get(i).paint(bg);
				}
				for(int i=0; i<enemies.size(); i++){
					enemies.get(i).paint(bg);
				}
				//player.paint(bg);
				for(int i=0; i<bullets.size(); i++){
					bullets.get(i).paint(bg);
				}
				map.cz.paint(bg);
				player.userInterface.paint(bg);
				
				break;
				
			case PAUSE:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				Words.LETTER_COLOR = new Color(0xCCCCCC);
				Words.VOID_COLOR = new Color(0x333333);
				pauseMenu.paintMenu(bg);
				break;
				
			case PLAYAGAIN1:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				Words.LETTER_COLOR = new Color(0xCCCCCC);
				Words.VOID_COLOR = new Color(0x333333);
				paMenu1.paintMenu(bg);
				break;
			case PLAYAGAIN2:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				Words.LETTER_COLOR = new Color(0xCCCCCC);
				Words.VOID_COLOR = new Color(0x333333);
				paMenu2.paintMenu(bg);
				break;
			case INSTRUCTIONS:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				Words.LETTER_COLOR = new Color(0xCCCCCC);
				Words.VOID_COLOR = new Color(0x333333);
				String ititle = "INSTRUCTIONS";
				bg.drawImage(Words.menuWord(ititle, 790/ititle.length()-5, 80), 10, 10, null);
				String[] instructions = new String[]{"You must claim the zone for your team to win!", " ",
						"You can choose between 3 classes, Gunner, Ninja, and Wizard",
						"each with their own unique traits", " ",
						"To claim the zone, you must stand in it for",
						"10 seconds without the enmy interefering", " ",
						"Move with WASD - Space will also jump",
						"Left click - Normal Attacks",
						"Right Click - Ability Attack",
						"P or Escape to Pause", " ", " ",
						"ESCAPE TO GO BACK"};
				int h = 260 / instructions.length;
				int iy = 220;
				int w = 0;
				for (String line:instructions) {
					w = Math.max(line.length(), w);
				}
				w = 790/w-5;
				for (String line:instructions) {
					bg.drawImage(Words.menuWord(line, w, h), (800-(w+5)*line.length())/2, iy, null);
					iy += h;
				}
				break;
			case CLASS:
				bg.setColor(new Color(0x333333));
				bg.fillRect(0, 0, 800, 600);
				Words.LETTER_COLOR = new Color(0xCCCCCC);
				Words.VOID_COLOR = new Color(0x333333);
				String ctitle = "CLASS INFO";
				bg.drawImage(Words.menuWord(ctitle, 790/ctitle.length()-5, 80), 10, 10, null);
				String[] classes = new String[]{"The gunner", " - attacks with bullets", " - ability to shoot grenades", " ",
						"The ninja", " - attacks with sword", " - ability to go invisible", " ",
						"The wizard", " - attacks with magic", " - ability to heal allies that it touches", " ",
						"ESCAPE TO GO BACK"};
				h = 260 / classes.length;
				iy = 220;
				w = 0;
				for (String line:classes) {
					w = Math.max(line.length(), w);
				}
				w = 790/w-5;
				for (String line:classes) {
					bg.drawImage(Words.menuWord(line, w, h), (800-(w+5)*line.length())/2, iy, null);
					iy += h + 5;
				}
				break;
		}
		String framesPerSecond =  "" + (int)Math.round(fps) + "fps";
		int width = bg.getFontMetrics().stringWidth(framesPerSecond);
		bg.setColor(Color.BLACK);
		bg.fillRect(800-width-10, 0, width+10, bg.getFontMetrics().getHeight() + 5);
		bg.setColor(Color.WHITE);
		bg.drawString(framesPerSecond, 800-width-5, bg.getFontMetrics().getHeight());
		g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, 800, 600, null);
		calcFPS(); //Must be at end of method
	}
	
	/*
	 * Calculates game fps
	 * ^^WHAT HE SAID
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

	//Point oldPos = new Point();
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (player == null)
			return;
		if(SwingUtilities.isLeftMouseButton(e)){
			player.keyController.resetAttackRate();
			player.keyController.setAttacking(true);
		}
		if(SwingUtilities.isRightMouseButton(e)){
			player.keyController.setCastingAbility(true);
		}
		//if(SwingUtilities.isMiddleMouseButton(e))
		//	oldPos = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		if (screen == Screen.PLAYER_CONFIG) {
			double xMulti = getWidth() * 1.0 /800;
			double yMulti = getHeight() * 1.0 /600;
			if (new Rectangle((int)(16 * xMulti), (int)(500 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color((int)((e.getPoint().x-16 * xMulti) / (3 * xMulti)), selectedColor.getGreen(), selectedColor.getBlue());
			} else if (new Rectangle((int)(16 * xMulti), (int)(530 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color(selectedColor.getRed(), (int)((e.getPoint().x-16*xMulti) / (3 * xMulti)), selectedColor.getBlue());
			} else if (new Rectangle((int)(16 * xMulti), (int)(560 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color(selectedColor.getRed(), selectedColor.getGreen(), (int)((e.getPoint().x-16*xMulti) / (3 * xMulti)));
			}
		}
		if (player == null)
			return;
		if(SwingUtilities.isLeftMouseButton(e)){
			player.keyController.setAttacking(false);
		}
		if(SwingUtilities.isRightMouseButton(e)){
			player.keyController.setCastingAbility(false);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		//if(SwingUtilities.isMiddleMouseButton(e)){
		//	int dx = e.getX()-oldPos.x;
		//	int dy = e.getY()-oldPos.y;
		//	cam.tryMove(dx, dy);
		//	oldPos = e.getPoint();
		//}
		if (screen == Screen.PLAYER_CONFIG) {
			double xMulti = getWidth() * 1.0 /800;
			double yMulti = getHeight() * 1.0 /600;
			if (new Rectangle((int)(16 * xMulti), (int)(500 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color((int)((e.getPoint().x-16 * xMulti) / (3 * xMulti)), selectedColor.getGreen(), selectedColor.getBlue());
			} else if (new Rectangle((int)(16 * xMulti), (int)(530 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color(selectedColor.getRed(), (int)((e.getPoint().x-16*xMulti) / (3 * xMulti)), selectedColor.getBlue());
			} else if (new Rectangle((int)(16 * xMulti), (int)(560 * yMulti), (int)(768 * xMulti), (int)(20 * yMulti)).contains(e.getPoint())) {
				selectedColor = new Color(selectedColor.getRed(), selectedColor.getGreen(), (int)((e.getPoint().x-16*xMulti) / (3 * xMulti)));
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println((e.getX()-cam.offx) + " " + (e.getY()-cam.offy));
	}

	public void keyPressed(KeyEvent e) {
		//Replace with the actual player object once we get to that point
		switch (screen) {
			case PLAYER_CONFIG:
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					selectedCharacter--;
					selectedCharacter = Math.max(0, selectedCharacter);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					selectedCharacter++;
					selectedCharacter = Math.min(characters.length - 1, selectedCharacter);
				} else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && rows > 1 && selectedCharacter-rc > -1) {
					selectedCharacter -= rc;
				} else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && rows > 1 && selectedCharacter+rc < characters.length) {
					selectedCharacter += rc;
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (selectedCharacter == 0) {
                        player = new GunDude(220, 1300, selectedColor);
	                } else if (selectedCharacter == 1) {
	                        player = new Ninja(220, 1300, selectedColor);
	                } else if (selectedCharacter == 2){
	                		player = new Wizard(220, 1300, selectedColor);
	                } else {
	                        player = new GunDude(220, 1300, selectedColor);
	                }
	                cam = new Camera(800, 600, player);
	                screen = Screen.GAMEPLAY;
	                bullets.clear();
					allies.clear();
					enemies.clear();
	                enemies.add(randomEnemy());
	                allies.add(player);
	                enemyController = new EnemyAI();
				}
				break;
			case GAMEPLAY:
				if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					pauseMenu.resetSelect();
					screen = Screen.PAUSE;
				} else if (player!=null) {
					player.keyController.processKeyPressed(e);
				}
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (screen) {
			case PLAYER_CONFIG:
				break;
			case GAMEPLAY:
				if (player!=null)
					player.keyController.processKeyReleased(e);
				break;
			case PAUSE:
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
					pauseMenu.selectorUp();
				else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
					pauseMenu.selectorDown();
				else if (e.getKeyCode() == KeyEvent.VK_ENTER)
					pauseMenu.select();
				break;
			case PLAYAGAIN1:
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
					paMenu1.selectorUp();
				else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
					paMenu1.selectorDown();
				else if (e.getKeyCode() == KeyEvent.VK_ENTER)
					paMenu1.select();
				break;
			case PLAYAGAIN2:
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
					paMenu2.selectorUp();
				else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
					paMenu2.selectorDown();
				else if (e.getKeyCode() == KeyEvent.VK_ENTER)
					paMenu2.select();
				break;
			case INSTRUCTIONS:
			case CLASS:
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					parent.remove(this);
					GameMenu panel = new GameMenu(origParent);
					parent.add(panel);
					panel.setSize(parent.getWidth(), parent.getHeight());
					if (origParent instanceof AppletGame) {
						((AppletGame) origParent).PANEL = panel;
						panel.addKeyListener(((AppletGame) origParent).KEY);
					} else if (origParent instanceof DesktopGame) {
						((DesktopGame) origParent).PANEL = panel;
						if (DesktopGame.DEVICE.getFullScreenWindow() == null)
							((DesktopGame) origParent).pack();
						panel.addKeyListener(((DesktopGame) origParent).KEY);
					}
					panel.requestFocus();
					panel.setParent(parent);
				}
		}
	}

	public void keyTyped(KeyEvent e) {
		//System.out.println(e.getKeyChar());
	}

	private void initTimer(){
		Timer t1 = new Timer();
		TimerTask tt1 = new TimerTask() {
			public void run() {
				tick();
			}
		};
		t1.schedule(tt1, 0, FRAMERATE);
	}
}