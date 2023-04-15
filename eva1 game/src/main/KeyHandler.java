package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	//Keylitsener er et interface for Keyboard inputs
	public boolean upPressed, downPressed, leftPressed, rightPressed, xKey, xKeyBad, spacePressed;

	@Override //keyTyped er ikke så vigtig for vores program
	public void keyTyped(KeyEvent e) {
		
	}

	@Override //KeyPressed : når du trykker på en key på dit Keyboard
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); //int code = e.getKeycode : retunere integeren af Keycoden som et nummer af en pressed key
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true; /*if(code == KeyEvent.VK_W)...osv : if statement om en user har trykket på "W"
			then set upPressed = true; : Programmet ser du har trykket på knappen*/
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_L) {
			if (xKeyBad) {
				xKey = false;
			} else {
				xKey = true;
				xKeyBad = true;
			}
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_L) {
			xKeyBad = false;
		}

		
	}


	

}
