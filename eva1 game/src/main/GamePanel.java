package main;

import java.awt.Color;
//import java.awt.BasicStroke; // For debugging... or, it was. Somehow, it is no longer needed. I dare not remove it though
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// world settings
	public final int maxWorldCol = 40;
	public final int maxWorldRow = 23;
	public final float scaleWindow = (float) 1;
	
	public final float screenWidth = 1920*2/3*scaleWindow;//Most Windows systems upscale programs by 150%
	//To solve this we "simply" have to downscale our program by 33%, so that 66% * 150% = 100%
	public final float screenHeight = 1080*2/3*scaleWindow;
	public final int tileSize = (int) screenWidth/maxWorldCol; //16 is the original tile size

	//FPS
	int FPS = 60;
	
	public TileManager tileM = new TileManager(this);
	KeyHandler KeyH = new KeyHandler();
	Thread gameThread; 
	public Player player = new Player(this,KeyH);
	
	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension((int) screenWidth,(int) screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);	
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //0.01666 sec
		double nextDrawTime = System.nanoTime() + drawInterval;
		
 		
		while(gameThread !=null) {
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				//Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
}

