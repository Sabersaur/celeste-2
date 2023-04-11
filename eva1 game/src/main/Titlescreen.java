package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Titlescreen extends JPanel {

    GamePanel gp;
    
    /*Denne kode opretter en ny JFrame og indstiller dens titel, størrelse og lukkefunktion. 
    Det opretter derefter et JPanel med en sort baggrund og tilføjer det til rammen. 
    To JLabel-komponenter oprettes og føjes til panelet, en for titlen og en for meddelelsen 
    "Tryk på en vilkårlig tast for at starte". Disse etiketter er stylet med forskellige skrifttyper og farver. 
    Til sidst gøres rammen synlig.*/

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Title Screen");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Titlescreen panel = new Titlescreen();
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Celeste 2", 200, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press P to start", 280, 300);
    }

    
}