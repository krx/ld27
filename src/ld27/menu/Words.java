package ld27.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Words {
	
	public static Color LETTER_COLOR = Color.WHITE, VOID_COLOR = new Color(0,0,0,0);
	
	private static final String[] A = {"xxxxx",
		"x...x",
		"xxxxx",
		"x...x",
		"x...x"};
	private static final String[] B = {"xxxxx",
		"x...x",
		"xxxx.",
		"x...x",
		"xxxxx"};
	private static final String[] C = {"xxxxx",
		"x....",
		"x....",
		"x....",
		"xxxxx"};
	private static final String[] D = {"xxxx.",
		"x...x",
		"x...x",
		"x...x",
		"xxxx."};
	private static final String[] E = {"xxxxx",
		"x....",
		"xxxxx",
		"x....",
		"xxxxx"};
	private static final String[] F = {"xxxxx",
		"x....",
		"xxxx.",
		"x....",
		"x...."};
	private static final String[] G = {"xxxx.",
		"x....",
		"x.xxx",
		"x...x",
		"xxxxx"};
	private static final String[] H = {"x...x",
		"x...x",
		"xxxxx",
		"x...x",
		"x...x"};
	private static final String[] I = {"xxxxx",
		"..x..",
		"..x..",
		"..x..",
		"xxxxx"};
	private static final String[] J = {"xxxxx",
		"...x.",
		"...x.",
		"x..x.",
		"xxxx."};
	private static final String[] K = {"x...x",
		"x..x.",
		"xxx..",
		"x..x.",
		"x...x"};
	private static final String[] L = {"x....",
		"x....",
		"x....",
		"x....",
		"xxxxx"};
	private static final String[] M = {"x...x",
		"xx.xx",
		"x.x.x",
		"x...x",
		"x...x"};
	private static final String[] N = {"x...x",
		"xx..x",
		"x.x.x",
		"x..xx",
		"x...x"};
	private static final String[] O = {"xxxxx",
		"x...x",
		"x...x",
		"x...x",
		"xxxxx"};
	private static final String[] P = {"xxxxx",
		"x...x",
		"xxxxx",
		"x....",
		"x...."};
	private static final String[] Q = {"xxxxx",
		"x...x",
		"x.x.x",
		"x..xx",
		"xxxxx"};
	private static final String[] R = {"xxxxx",
		"x...x",
		"xxxx.",
		"x...x",
		"x...x"};
	private static final String[] S = {"xxxxx",
		"x....",
		"xxxxx",
		"....x",
		"xxxxx"};
	private static final String[] T = {"xxxxx",
		"..x..",
		"..x..",
		"..x..",
		"..x.."};
	private static final String[] U = {"x...x",
		"x...x",
		"x...x",
		"x...x",
		"xxxxx"};
	private static final String[] V = {"x...x",
		"x...x",
		"x...x",
		".x.x.",
		"..x.."};
	private static final String[] W = {"x...x",
		"x...x",
		"x.x.x",
		"x.x.x",
		"xxxxx"};
	private static final String[] X = {"x...x",
		".x.x.",
		"..x..",
		".x.x.",
		"x...x"};
	private static final String[] Y = {"x...x",
		".x.x.",
		"..x..",
		"..x..",
		"..x.."};
	private static final String[] Z = {"xxxxx",
		"...x.",
		"..x..",
		".x...",
		"xxxxx"};
	private static final String[] ZERO = {"xxxxx",
		"x...x",
		"x...x",
		"x...x",
		"xxxxx"};
	private static final String[] ONE = {"xxx..",
		"..x..",
		"..x..",
		"..x..",
		"xxxxx"};
	private static final String[] TWO = {"xxxxx",
		"....x",
		"xxxxx",
		"x....",
		"xxxxx"};
	private static final String[] THREE = {"xxxxx",
		"....x",
		"xxxxx",
		"....x",
		"xxxxx"};
	private static final String[] FOUR = {"x..x.",
		"x..x.",
		"xxxxx",
		"...x.",
		"...x."};
	private static final String[] FIVE = {"xxxxx",
		"x....",
		"xxxxx",
		"....x",
		"xxxxx"};
	private static final String[] SIX = {"xxxxx",
		"x....",
		"xxxxx",
		"x...x",
		"xxxxx"};
	private static final String[] SEVEN = {"xxxxx",
		"x...x",
		"...x.",
		"..x..",
		".x..."};
	private static final String[] EIGHT = {"xxxxx",
		"x...x",
		"xxxxx",
		"x...x",
		"xxxxx"};
	private static final String[] NINE = {"xxxxx",
		"x...x",
		"xxxxx",
		"....x",
		"xxxxx"};
	private static final String[] PERIOD = {".....",
		".....",
		".....",
		".xx..",
		".xx.."};
	private static final String[] DASH = {".....",
		".xxx.",
		".xxx.",
		".....",
		"....."};
	public static BufferedImage menuWord(String word, int LETTER_IMG_WIDTH, int LETTER_IMG_HEIGHT){
		word=word.toUpperCase();
		BufferedImage img = new BufferedImage(LETTER_IMG_WIDTH*word.length()+5*word.length(),LETTER_IMG_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		int xLoc=0;
		for(int i=0; i<word.length(); i++){
			g.drawImage(getLetterImage(word.charAt(i),LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT),xLoc,0,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT,null);
			xLoc+=LETTER_IMG_WIDTH+5;
		}
		return img;
	}
	private static BufferedImage getLetterImage(char c, int LETTER_IMG_WIDTH, int LETTER_IMG_HEIGHT){
		switch(c){
		case ' ': return new BufferedImage(LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		case 'A': return makeLetterImage(A,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'B': return makeLetterImage(B,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'C': return makeLetterImage(C,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'D': return makeLetterImage(D,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'E': return makeLetterImage(E,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'F': return makeLetterImage(F,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'G': return makeLetterImage(G,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'H': return makeLetterImage(H,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'I': return makeLetterImage(I,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'J': return makeLetterImage(J,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'K': return makeLetterImage(K,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'L': return makeLetterImage(L,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'M': return makeLetterImage(M,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'N': return makeLetterImage(N,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'O': return makeLetterImage(O,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'P': return makeLetterImage(P,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'Q': return makeLetterImage(Q,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'R': return makeLetterImage(R,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'S': return makeLetterImage(S,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'T': return makeLetterImage(T,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'U': return makeLetterImage(U,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'V': return makeLetterImage(V,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'W': return makeLetterImage(W,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'X': return makeLetterImage(X,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'Y': return makeLetterImage(Y,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case 'Z': return makeLetterImage(Z,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '0': return makeLetterImage(ZERO,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '1': return makeLetterImage(ONE,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '2': return makeLetterImage(TWO,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '3': return makeLetterImage(THREE,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '4': return makeLetterImage(FOUR,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '5': return makeLetterImage(FIVE,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '6': return makeLetterImage(SIX,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '7': return makeLetterImage(SEVEN,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '8': return makeLetterImage(EIGHT,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '9': return makeLetterImage(NINE,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '.': return makeLetterImage(PERIOD,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		case '-': return makeLetterImage(DASH,LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT);
		default: return null;
		}
	}
	private static BufferedImage makeLetterImage(String[] data,int LETTER_IMG_WIDTH, int LETTER_IMG_HEIGHT){
		BufferedImage img = new BufferedImage(LETTER_IMG_WIDTH,LETTER_IMG_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		int xPos=0,yPos=0;
		for(String s : data){
			for(char c : s.toCharArray()){
				fillColor(img,xPos,yPos,LETTER_IMG_WIDTH/5,LETTER_IMG_HEIGHT/5,c=='x'?LETTER_COLOR:VOID_COLOR);
				xPos+=LETTER_IMG_WIDTH/5;
			}
			xPos=0;
			yPos+=LETTER_IMG_HEIGHT/5;
		}
		return img;
	}
	public static void setLetterColor(Color c) {
		LETTER_COLOR = c;
	}
	private static void fillColor(BufferedImage img, int xPos, int yPos, int width, int height, Color c){
		for(int y=yPos; y<yPos+height; y++){
			for(int x=xPos; x<xPos+width; x++){
				img.setRGB(x,y,c.hashCode());
			}
		}
	}
}