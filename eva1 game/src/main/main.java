package main;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) { //Constant warning "This method has a constructer name". Can we fix this?

		JFrame window = new JFrame(); //skaber en windowbrowser på din computer.
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lader dig lukke vinduet på din comnputer (x) button
		window.setResizable(false); // gør så du ikke kan resize dit window
		window.setTitle("2D Game"); // laver en title til vinduet
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); //adder gamepanel funktionerne til vinduet på pc
		
		window.pack(); //sætter vinduet til den preferred størrelse
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
