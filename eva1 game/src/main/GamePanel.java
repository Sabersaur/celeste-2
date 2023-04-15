package main;

import java.awt.Color;
//import java.awt.BasicStroke; //For debugging...
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//world settings
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
	Thread gameThread; /*subclass Thread gameThread; : bliver ved med at kører indtil det bliver stoppet. 
	For at kører tiden i programmet skal man implimenter "Runnable"*/
	public Player player = new Player(this,KeyH);
	
	long Timer = System.nanoTime();

	public GamePanel () {
		
		this.setPreferredSize(new Dimension((int) screenWidth,(int) screenHeight)); //sætter størelse på JPanal klassen
		this.setBackground(Color.black); //sætter bagrunden af vinduet til farven sort
		this.setDoubleBuffered(true); //alle tegninger i komponetet bliver tegnet offscreen i en buffer, det vil derfor gøre rendering bedre i JPanal
		this.addKeyListener(KeyH); // Det gør at Gamepanel klassen kan se KeyInput fra KeyHandler
		this.setFocusable(true);	//uptimization for KeyInputs
	}

	public void startGameThread() {
		
		gameThread = new Thread(this); //"this" betyder GamePanel klassen (passing to GamePanel klassen)
		gameThread.start(); //starter update tiden i programmet
	}

	@Override 
	/*(methode): når et object (GamePanel) implimentere "Runnable" bliver "void run" brugt til at starte en Thread. 
	så når Thread'en starter kalder den Run metoden. */
	public void run() {
		
		double drawInterval = 1000000000/FPS; //0.01666 sec = 60 times på et sek
		double nextDrawTime = System.nanoTime() + drawInterval; 
		
 		//while Loop repeater det inde i loopet mens og så længde gameThread kører
		while(gameThread !=null) {
			
			update(); //updater player position, som bliver til movement i spillet
			
			repaint(); //repaint er måden man kalder paintcomponet metoden i java
			
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
	//kalder update metoden
	public void update() {
		
		player.update();
		
	}
	//paintComponent()  (metode) : standard build in metode i java til at tegne i JPanel
	//(Graphics g) En klasse der her mange funktioner til at tegne objekter i vinduet
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; /*Graphics2D : er en klasse der extender funktionaliteten af Graphics klassen, 
		til at give mere kontrol over movement cordinater i 2D og farver og tekst layout.*/
		//Graphics2D g2 = (Graphics2D)g; : betyder at vi laver klassen om til Graphics2D klassen.

		tileM.draw(g2);
		
		player.draw(g2);
		g2.setColor(Color.black); //sætter 2D farven sort
		g.fillRect (60, 57, 100, 30); //rectangle med given størrelse
		g2.setColor(Color.white); //sætter rect til hvid
		g2.drawString(String.valueOf((System.nanoTime()-Timer)/1000000000),75,75); //selve timeren
	
	
		g2.dispose();//optimisering
		
	}
}

