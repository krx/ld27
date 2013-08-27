package ld27.multiplayer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.TimerTask;

import ld27.player.Player;

/**
 * Class to handle the game server
 * @author RedSoxFan
 */
public class GameServer {
//	public static void main(String[] args) {
//		try {
//			GameServer gs = new GameServer(9542);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	private ArrayList<Player> players = new ArrayList<Player>(); 
//	private int port = 0;
//	private ServerSocket serv = null;
//	public GameServer(int port) throws IOException {
//		serv = new ServerSocket(port, 15);
//		this.port = serv.getLocalPort();
//		new Thread(){ 
//			public void run() {
//				new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
//					public void run() {
//						try {
//							Socket s = serv.accept();
//							BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//							String msg = br.readLine();
//							if (msg!=null && msg.equals("[Ld1bTt]")) {
//								synchronized (players) {
//									Player p = Player.createPlayer(s);
//									p.reader = br;
//									p.writer = new PrintWriter(s.getOutputStream());
//									players.add(p);
//								}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}, 1, 100);
//			}
//		}.start();
//		new Thread(){ 
//			public void run() {
//				new java.util.Timer().schedule(new TimerTask() {
//					public void run() {
//						synchronized (players) {
//							for (Player player:players) {
//								try {
//									String msg = "";
//									if (player.reader.ready()) {
//										msg = player.reader.readLine();
//										if (msg.startsWith("[PlColCh]")) {
//											player.c = new Color(Integer.parseInt(msg.substring(9)));
//											System.out.printf("Player Color Changed To - RGB(%s, %s, %s)\n",
//													player.c.getRed(), player.c.getBlue(), player.c.getGreen());
//										} else {
//											System.out.println("Message: " + msg);
//										}
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							}
//						}
//					}
//				}, 1, 100);
//			}
//		}.start();
//	}
//	public String getHost() {
//		try {
//			return Inet4Address.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e) {
//		}
//		return "127.0.0.1";
//	}
//	public int getPort() {
//		return port;
//	}
}
