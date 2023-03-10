package entity;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.management.ValueExp;

import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler KeyH;
	TileManager tileM;
	
	public final float screenX;
	public final float screenY;
	
	public Player(GamePanel gp, KeyHandler KeyH, TileManager tileM) {
		
		this.gp = gp;
		this.KeyH = KeyH;
		this.tileM = tileM;
		
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
		gravity = 0;
		direction = "down";
	}
	public void getPlayerImage() {
		
		try {
			//video #3 (11:24) or pixelart.com scale 30x30 510 print scale
			up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png/"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_3.png/"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_4.png/"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png/"));

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
	public void collide(Graphics2D g3) {
		int[] line2P1 = new int[2];
		int[] line2P2 = new int[2];
		int countx;
		int county;
		float[] col = new float[2];
		
		g3.draw(new Line2D.Float(playerX, playerY, playerX+velocityX, playerY-velocityY));

		if (velocityX >= 0) { //velX is positive
			if (velocityY >= 0) { //velY is positive
				countx = (int) playerX / gp.tileSize;
				county = ((int) playerY / gp.tileSize);

				for (int ix = countx, iy = county; ix <= gp.maxWorldCol-1 && iy >= 0; ix++) {
					System.out.println("ix: "+ix+", iy: "+iy);
					
					if (tileM.mapTileNum[ix][iy] == 1) { //Search for hitboxes
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = iy*gp.tileSize;
						line2P2[0] = line2P1[0];
						line2P2[1] = line2P1[1]+gp.tileSize-10;
						col = colisionAid(line2P1, line2P2, g3);
						if (col[0] != 0 && col[1] != 0) {
							playerX = col[0] - (gp.tileSize/2);
							velocityX = 0;
						}
						line2P1[0] = ix*gp.tileSize;
						line2P1[1] = (iy+1)*gp.tileSize;
						line2P2[0] = line2P1[0] + gp.tileSize-10;
						line2P2[1] = line2P1[1];
						col = colisionAid(line2P1, line2P2, g3);
						if (col[0] != 0 && col[1] != 0) {
							playerY = col[1] + (gp.tileSize/2);
							velocityY = 0;
						}	
					}
					if (ix >= gp.maxWorldCol-1) {
						ix = countx-1;
						iy--;
					}
				}
			} else { //velY is negative

			}

		} else { //velX is negative
			if (velocityY >= 0) { //velY is positive

			} else { //velY is negative

			}
		}
		playerY -= velocityY;
		playerX += velocityX;
	}
	public float[] colisionAid(int[] line2P1, int[] line2P2, Graphics2D g3) {
		float[] result = new float[2];
		float[] line1P1 = new float[2];
		float[] line1P2 = new float[2];

		line1P1[0] = playerX;
		line1P1[1] = playerY;
		line1P2[0] = playerX + velocityX;
		line1P2[1] = playerY - velocityY;
	   
		g3.draw(new Line2D.Float(line2P1[0], line2P1[1], line2P2[0], line2P2[1]));
		
		double s1_x = line1P2[0] - line1P1[0]; 
		double s1_y = line1P2[1] - line1P1[1];
	  
		double s2_x = line2P2[0] - line2P1[0]; 
		double s2_y = line2P2[1] - line2P1[1]; 
	  
		double s = (-s1_y * (line1P1[0] - line2P1[0]) + s1_x * (line1P1[1] - line2P1[1])) / (-s2_x * s1_y + s1_x * s2_y);
		double t = ( s2_x * (line1P1[1] - line2P1[1]) - s2_y * (line1P1[0] - line2P1[0])) / (-s2_x * s1_y + s1_x * s2_y);
	  
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) { // Collision detected
		  result[0] = (float) (line1P1[0] + (t * s1_x)); 
		  result[1] = (float) (line1P1[1] + (t * s1_y));
		}
	  
		return result;
	  }
	public void update() {
		velocityX = (float) (velocityX*0.8); //Friction, bc SOMEONE didn't add colision yet
		velocityY = (float) (velocityY*0.8);

		
		
		velocityY -= gravity;

		//key input controls
		if(KeyH.upPressed || KeyH.downPressed || 
				KeyH.leftPressed || KeyH.rightPressed || KeyH.spacePressed)  {
			
			if(KeyH.upPressed) {
				direction = "up";
			}
			 
			if(KeyH.downPressed) {
				direction = "down";
				velocityY--;
				velocityY--;
			}
			if(KeyH.leftPressed) {
				direction = "left";
				velocityX--;
				velocityX--;
			}
			if (KeyH.rightPressed) {
				direction = "right";
				velocityX++;
				velocityX++;
			}
			if (KeyH.xKey) {
				if (direction == "right") {
					velocityX = velocityX + 50;
				}
				if (direction == "left") {
					velocityX = velocityX - 50;
				}
				if (direction == "up") {
					velocityY = velocityY + 50;
				}
				if (direction == "down") {
					velocityY = velocityY - 50;
				}
				KeyH.xKey = false;
			}
			if (KeyH.spacePressed) {
				velocityY =+ 30;
				KeyH.spacePressed = false;
				
			}
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
				image = right1;
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
			    image = right1;
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
		g2.drawImage(image, (int) playerX - gp.tileSize/2, (int) playerY - gp.tileSize/2, (int) (gp.tileSize*1.2), (int) (gp.tileSize*1.2), null);
		// alternative white block as player
		//g2.setColor(Color.white);
		//g2.fillRect((int) playerX - gp.tileSize/2,(int) playerY - gp.tileSize/2, gp.tileSize, gp.tileSize);
		g2.setColor(Color.RED);
		collide(g2);
	}
}