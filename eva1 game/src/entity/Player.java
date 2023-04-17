package entity;

import java.awt.Graphics2D;
import java.awt.BasicStroke; // For debugging
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;

import javax.imageio.ImageIO;
//import javax.management.ValueExp; // Why is this here? What is it used for?

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler KeyH;
	boolean debugging = false; // blev brugt da vi lavede colissions. Gjorde egentlig bare karakteren til en firkant med størrelse = tilesize.
	boolean velXplus = true; //I'm sorry, but I need this variable, no matter how stupid it sounds
	int grounded = 0;
	int dash = 0;
	int directioncount = 0;
		
	public final float screenX;
	public final float screenY;
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		
		this.gp = gp;
		this.KeyH = KeyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX = gp.screenWidth/2 - (gp.tileSize/2);
		worldY = gp.screenHeight/2 - (gp.tileSize/2);
		playerX = 100;
		playerY = 450;
		velocityX = 0;
		velocityY = 0;
		gravity = 1;
		direction = "down";
		grounded = 0;
		dash = 0;
	}
	public void getPlayerImage() {
		
		try {

			//vi ændrede i spillets grundstruktur, så spilleren kun går til højre / venstre, og derfor ikke behøver op/ned sprites.

			//up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			//up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png/"));
			//up3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_3.png/"));
			//up4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_4.png/"));

			//down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			//down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			//down3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			//down4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_1.png/"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_2.png/"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_3.png/"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_4.png/"));
			left5 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_5.png/"));
			left6 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_6.png/"));
			left7 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_7.png/"));
			left8 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_Left_8.png/"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png/"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png/"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_3.png/"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_4.png/"));
			right5 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_5.png/"));
			right6 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_6.png/"));
			right7 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_7.png/"));
			right8 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_8.png/"));


		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void collide(Graphics2D g2) {
		float[] line2P1 = new float[2];
		float[] line2P2 = new float[2];
		int countx;
		int county;
		float[] col = new float[2];
		int[] line1P1 = new int[2];
		
		if (velocityX >= 0) { //velX is positive
			if (velocityY >= 0) { //velY is positive
				countx = (int) playerX / gp.tileSize;
				county = ((int) playerY / gp.tileSize);

				line1P1[0] = (int) playerX + gp.tileSize/2;
		    	line1P1[1] = (int) playerY - gp.tileSize/2;

				if (debugging) {
					g2.drawLine((int) line1P1[0],(int) line1P1[1],(int) (line1P1[0] + velocityX),(int) (line1P1[1] - velocityY));
				}
				
				for (int ix = countx, iy = county; ix <= gp.maxWorldCol-1 && iy >= 0; ix++) {

					if (gp.tileM.mapTileNum[ix][iy] >= 1 && gp.tileM.mapTileNum[ix][iy] <= 3) { //Search for hitboxes
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;
						
						col = colisionAid(line1P1, line2P1, line2P2, g2, !velXplus);
						if (col[0] != 0 && col[1] != 0) { // Collision point found
							playerX = col[0] - (gp.tileSize)/2 -1; // Don't go through wall
							velocityX = 0; // Be stopped by wall
							line1P1[0] = (int) col[0]; // Update player position for next calculation
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						col = colisionAid(line1P1, line2P1, line2P2, g2, !velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerY = col[1] + (gp.tileSize)/2 +1; //+1 for IDK it worked when walking
							velocityY = 0;
							line1P1[1] = (int) col[1];

						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 4) { //Search for ouchies
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 5) { //Search for screen transition
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
					}
					if (ix >= gp.maxWorldCol-1 || ix >= countx+5) {
						ix = countx-1;
						iy--;
					}
					if (iy <= county-5) {
						break;
					}
				}
			} else { //velY is negative
				countx = (int) playerX / gp.tileSize;
				county = ((int) playerY / gp.tileSize);

				line1P1[0] = (int) playerX + gp.tileSize/2;
				line1P1[1] = (int) playerY + gp.tileSize/2;

				if (debugging) {
					g2.drawLine((int) line1P1[0],(int) line1P1[1],(int) (line1P1[0] + velocityX),(int) (line1P1[1] - velocityY));
				}

				for (int ix = countx, iy = county; ix <= gp.maxWorldCol-1 && iy <= gp.maxWorldRow-1; ix++) {
					

					if (gp.tileM.mapTileNum[ix][iy] >= 1 && gp.tileM.mapTileNum[ix][iy] <= 3) { //Search for hitboxes
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;
						
						int[] helpme = new int[2]; //This wasn't supposed to work.
						helpme[0] = line1P1[0] +0; //Since it does though, I'm not changing it
						helpme[1] = line1P1[1] -1;
						col = colisionAid(helpme, line2P1, line2P2, g2, !velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerX = col[0] - (gp.tileSize)/2 -1; //Consider it a good luck charm
							velocityX = 0; // I'm trying my hardest with these funny comments
							line1P1[0] = (int) col[0];
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];

						col = colisionAid(line1P1, line2P1, line2P2, g2, !velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerY = col[1] - (gp.tileSize)/2 -1; //-1 for smoother walking
							velocityY = 0;
							line1P1[1] = (int) col[1];
							grounded = 6;
							dash = 1;
						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 4) { //Search for ouchies
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 5) { //Search for screen transition
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
					}
					if (ix >= gp.maxWorldCol-1 || ix >= countx+5) {
						ix = countx-1;
						iy++;
					}
					if (iy >= county+5) {
						break;
					}
				}
			}
			velXplus = true;
		} else { //velX is negative
			if (velocityY >= 0) { //velY is positive
				countx = (int) playerX / gp.tileSize;
				county = ((int) playerY / gp.tileSize);

				line1P1[0] = (int) playerX - gp.tileSize/2;
				line1P1[1] = (int) playerY - gp.tileSize/2;

				if (debugging) {
					g2.drawLine((int) line1P1[0],(int) line1P1[1],(int) (line1P1[0] + velocityX),(int) (line1P1[1] - velocityY));
				}

				for (int ix = countx +1, iy = county; ix >= 0 && iy >= 0; ix--) {

					if (gp.tileM.mapTileNum[ix][iy] >= 1 && gp.tileM.mapTileNum[ix][iy] <= 3) { //Search for hitboxes
						line2P1[0] = (ix+1)*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;
						col = colisionAid(line1P1, line2P1, line2P2, g2, velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerX = col[0] + (gp.tileSize)/2;
							velocityX = 0;
							line1P1[0] = (int) col[0];
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						col = colisionAid(line1P1, line2P1, line2P2, g2, velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerY = col[1] + (gp.tileSize)/2 +1;
							velocityY = 0;
							line1P1[1] = (int) col[1];
						}	
					}
					if (gp.tileM.mapTileNum[ix][iy] == 4) { //Search for ouchies
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 5) { //Search for screen transition
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
					}
					if (ix <= 0 || ix <= countx-5) {
						ix = countx +1; //look, it just works, okay?
						iy--;
					}
					if (iy <= county-5) {
						break;
					}
				}
			} else { //velY is negative
				countx = (int) playerX / gp.tileSize;
				county = ((int) playerY / gp.tileSize);

				line1P1[0] = (int) playerX - gp.tileSize/2;
				line1P1[1] = (int) playerY + gp.tileSize/2;

				if (debugging) {
					g2.drawLine((int) line1P1[0],(int) line1P1[1],(int) (line1P1[0] + velocityX),(int) (line1P1[1] - velocityY));
				}

				for (int ix = countx +1, iy = county; ix >= 0 && iy <= gp.maxWorldRow-1; ix--) {

					if (gp.tileM.mapTileNum[ix][iy] >= 1 && gp.tileM.mapTileNum[ix][iy] <= 3) { //Search for hitboxes
						line2P1[0] = (ix+1)*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;
						col = colisionAid(line1P1, line2P1, line2P2, g2, velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerX = col[0] + (gp.tileSize)/2;
							velocityX = 0;
							line1P1[0] = (int) col[0];
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] =  iy*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						col = colisionAid(line1P1, line2P1, line2P2, g2, velXplus);
						if (col[0] != 0 && col[1] != 0) {
							playerY = col[1] - (gp.tileSize)/2 -1; //Smoother walking, again
							velocityY = 0;
							line1P1[1] = (int) col[1];
							grounded = 6;
							dash = 1;
						}	
					}
					if (gp.tileM.mapTileNum[ix][iy] == 4) { //Search for ouchies
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							setDefaultValues();
						}
					}
					if (gp.tileM.mapTileNum[ix][iy] == 5) { //Search for screen transition
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1] + gp.tileSize;

						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize;
						line2P2[1] = line2P1[1];
						if (colBin(line1P1, line2P1, line2P2, g2)) {
							gp.tileM.level++;
							gp.tileM.loadMap();
							setDefaultValues();
						}
					}
					if (ix <= 0 || ix <= countx-5) {
						ix = countx+1;
						iy++;
					}
					if (iy >= county+5) {
						break;
					}
				}

			}
			velXplus = false;
		}
		playerY -= velocityY;
		playerX += velocityX;
	}
	public float[] colisionAid(int[] line1P1, float[] line2P1, float[] line2P2, Graphics2D g2, boolean inblock) {
		float[] result = new float[2];
		float[] line1P2 = new float[2];


		line1P2[0] = line1P1[0] + velocityX;
		line1P2[1] = line1P1[1] - velocityY;

		if (debugging) {
			g2.drawLine((int) line2P1[0], (int) line2P1[1], (int) line2P2[0], (int) line2P2[1]);
		}
	   
		float s1_x = line1P2[0] - line1P1[0]; 
		float s1_y = line1P2[1] - line1P1[1];
	  
		float s2_x = line2P2[0] - line2P1[0]; 
		float s2_y = line2P2[1] - line2P1[1]; 


	  
		float s = (-s1_y * (line1P1[0] - line2P1[0]) + s1_x * (line1P1[1] - line2P1[1])) / (-s2_x * s1_y + s1_x * s2_y);
		float t = ( s2_x * (line1P1[1] - line2P1[1]) - s2_y * (line1P1[0] - line2P1[0])) / (-s2_x * s1_y + s1_x * s2_y);
	  
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) { // Collision detected
		  result[0] = line1P1[0] + (t * s1_x);
		  result[1] = line1P1[1] + (t * s1_y);
		}

		if (inblock) { //I don't know WHY this breaks collision, but it does, so uhhhhh.... +1!
			line1P2[1] = line1P1[1]; // I never fixed this issue. Somehow, it's gone though. Hurray?

			if (velXplus) {
				line1P2[0] = line1P1[0] + gp.tileSize;
			} else {
				line1P2[0] = line1P1[0] - gp.tileSize;
			}
	
			if (debugging) {
				g2.drawLine((int) line2P1[0], (int) line2P1[1], (int) line2P2[0], (int) line2P2[1]);
			}
		   
			s1_x = line1P2[0] - line1P1[0]; 
			s1_y = line1P2[1] - line1P1[1];
		  
			s2_x = line2P2[0] - line2P1[0]; 
			s2_y = line2P2[1] - line2P1[1]; 
	
	
		  
			s = (-s1_y * (line1P1[0] - line2P1[0]) + s1_x * (line1P1[1] - line2P1[1])) / (-s2_x * s1_y + s1_x * s2_y);
			t = ( s2_x * (line1P1[1] - line2P1[1]) - s2_y * (line1P1[0] - line2P1[0])) / (-s2_x * s1_y + s1_x * s2_y);
	  
			if (s >= 0 && s <= 1 && t >= 0 && t <= 1) { // Collision detected
			  result[0] = line1P1[0] + (t * s1_x);
			  result[1] = line1P1[1] + (t * s1_y);
			}
		}
		return result;
	  }
	  public boolean colBin(int[] line1P1, float[] line2P1, float[] line2P2, Graphics2D g2) {
		float[] line1P2 = new float[2];

		line1P2[0] = line1P1[0] + velocityX;
		line1P2[1] = line1P1[1] - velocityY;

		if (debugging) {
			g2.drawLine((int) line2P1[0], (int) line2P1[1], (int) line2P2[0], (int) line2P2[1]);
		}
	   
		float s1_x = line1P2[0] - line1P1[0]; 
		float s1_y = line1P2[1] - line1P1[1];
	  
		float s2_x = line2P2[0] - line2P1[0]; 
		float s2_y = line2P2[1] - line2P1[1]; 
	  
		float s = (-s1_y * (line1P1[0] - line2P1[0]) + s1_x * (line1P1[1] - line2P1[1])) / (-s2_x * s1_y + s1_x * s2_y);
		float t = ( s2_x * (line1P1[1] - line2P1[1]) - s2_y * (line1P1[0] - line2P1[0])) / (-s2_x * s1_y + s1_x * s2_y);
	  
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) { // Collision detected
		  return true;
		}
		return false;
	  }
	
	public void update() {
		if (grounded > 0) {
			velocityX = (float) (velocityX*0.8); //Friction, while on ground
		}
		
		velocityX = (float) (velocityX*0.95);
		velocityY = (float) (velocityY*0.95);
		velocityY -= gravity;
		
		
		if (playerX <= 0 || playerY <= 0 || playerX >= gp.screenWidth || playerY >= gp.screenHeight) {
			setDefaultValues();
		}


		//key input controls
		if(KeyH.upPressed || KeyH.downPressed || KeyH.xKey ||
				KeyH.leftPressed || KeyH.rightPressed || KeyH.spacePressed)  {
			
			if(KeyH.upPressed) {
				direction = "up";
				directioncount++;
			}
			 
			if(KeyH.downPressed) {
				direction = "down";
				velocityY--;
				directioncount++;
			}
			if(KeyH.leftPressed) {
				direction = "left";
				velocityX--;
				directioncount++;
			}
			if (KeyH.rightPressed) {
				direction = "right";
				velocityX++;
				directioncount++;
			}
			if (KeyH.xKey) {
				if (dash > 0) {
					if (directioncount > 1) { //Dashes should have equal strength, no matter the direction
						if (KeyH.rightPressed) {
							velocityX = velocityX + 20*0.707f; //sin(45) = 0.707
						}
						if (KeyH.leftPressed) {
							velocityX = velocityX - 20*0.707f;
						}
						if (KeyH.upPressed) {
							velocityY = velocityY + 30*0.707f;
						}
						if (KeyH.downPressed) {
							velocityY = velocityY - 30*0.707f;
						}
						KeyH.xKey = false;
						dash--;
					} else {
						if (KeyH.rightPressed) {
							velocityX = velocityX + 15;
						}
						if (KeyH.leftPressed) {
							velocityX = velocityX - 15;
						} // The difference in horizontal and vertical momentum comes from the practical feel in-game
						if (KeyH.upPressed) { // Vertical movement is heavily limited by gravity, whereas
							velocityY = velocityY + 30; // Horizontal movement is basically free
						}
						if (KeyH.downPressed) {
							velocityY = velocityY - 30;
						}
						KeyH.xKey = false;
						dash--;
					}
					
				}
			}
			if (KeyH.spacePressed) {
				if (grounded > 0) {
					velocityY =+ 15;
					KeyH.spacePressed = false;
					grounded = 0;
				}
			}
			directioncount = 0;
			//Player sprite changer 
			spriteCounter++;
			if(spriteCounter > 4) { // (12 = speed of change relative to FPS)
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 3;
				}
				else if(spriteNum == 3) {
					spriteNum = 4;
				}
				else if(spriteNum == 4) {
					spriteNum = 5;
				}
				else if(spriteNum == 5) {
					spriteNum = 6;
				}
				else if(spriteNum == 6) {
					spriteNum = 7;
				}
				else if(spriteNum == 7) {
					spriteNum = 8;
				}
				else if(spriteNum == 8) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
	}
	public void draw(Graphics2D g2) {
		
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteNum >= 1) {
				image = right1; //ser mærkeligt ud, men vi har beholdt orienteringerne fra vores oprindelige system, men visuelt er der nu kun behov for højre/venstre.
			}/* else if (spriteNum == 2) {
				image = up2;
			} else if (spriteNum == 3) {
				image = up3;
			} else if (spriteNum >= 4) {
				image = up4;
			}*/
			break;
		case "down":
			if (spriteNum >= 1) {
			    image = right1; // samme sker her
			}/* else if (spriteNum == 2) {
				image = down2;
			} else if (spriteNum == 3) {
				image = down3;
			} else if (spriteNum >= 4) {
				image = down4;
			}*/
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			} else if (spriteNum == 2) {
				image = left2;
			} else if (spriteNum == 3) {
				image = left3;
			} else if (spriteNum == 4) {
				image = left4;
			} else if (spriteNum == 5) {
				image = left5;
			} else if (spriteNum == 6) {
				image = left6;
			} else if (spriteNum == 7) {
				image = left7;
			} else if (spriteNum == 8) {
				image = left8;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			} else if (spriteNum == 2) {
				image = right2;
			} else if (spriteNum == 3) {
				image = right3;
			} else if (spriteNum == 4) {
				image = right4;
			} else if (spriteNum == 5) {
				image = right5;
			} else if (spriteNum == 6) {
				image = right6;
			} else if (spriteNum == 7) {
				image = right7;
			} else if (spriteNum == 8) {
				image = right8;
			}
			break;
		}
		g2.drawImage(image, (int) playerX - gp.tileSize*2/3, (int) playerY - gp.tileSize*3/4, (int) (gp.tileSize*1.6), (int) (gp.tileSize*1.6), null);

		if (debugging) {// alternative white block as player
			if (dash == 0) {
				g2.setColor(Color.blue);
			} else {
				g2.setColor(Color.white);
				}
			g2.fillRect((int) playerX - gp.tileSize/2,(int) playerY - gp.tileSize/2, gp.tileSize, gp.tileSize);
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(3));
			collide(g2);
			g2.drawLine((int) playerX, (int) playerY, (int) (playerX + velocityX), (int) (playerY - velocityY));
		} else {
			collide(g2);
		}

		if (grounded > 0) {
			grounded--;
		}
	}
}