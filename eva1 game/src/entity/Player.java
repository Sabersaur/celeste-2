package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.management.ValueExp;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		
		this.gp = gp;
		this.KeyH = KeyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX = 100;
		worldY = 450;
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
			left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png/"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png/"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_3.png/"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_4.png/"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png/"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png/"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_3.png/"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_4.png/"));
			

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		velocityX = (float) (velocityX*0.8); //Friction, bc SOMEONE didn't add colision yet
		velocityY = (float) (velocityY*0.8);

		worldY -= velocityY;
		worldX += velocityX;
		velocityY -= gravity;

		//key input controls
		if(KeyH.upPressed || KeyH.downPressed || 
				KeyH.leftPressed || KeyH.rightPressed)  {
			
			if(KeyH.upPressed) {
				direction = "up";
				velocityY++;
				velocityY++;
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

			//Player sprite changer 
			spriteCounter++;
			if(spriteCounter > 12) { // (12 = speed of change relative to FPS)
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
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
	}
	public void draw(Graphics2D g2) {
		// alternative white block as player
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			if (spriteNum == 3) {
				image = up3;
			}
			if (spriteNum == 4) {
				image = up4;
			}
			break;
		case "down":
			if (spriteNum == 1) {
			    image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			if (spriteNum == 3) {
				image = down3;
			}
			if (spriteNum == 4) {
				image = down4;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			if (spriteNum == 3) {
				image = left3;
			}
			if (spriteNum == 4) {
				image = left4;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			if (spriteNum == 3) {
				image = right3;
			}
			if (spriteNum == 4) {
				image = right4;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}
}