package main.java.com;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class Main{
    static CardLayout card = new CardLayout();
    static JPanel mainPanel = new JPanel(card);
    static Screen screen = new Screen();
    public static void main(String[] args) {
        
        JFrame window = new JFrame("CraftMania 3000 sigma gyatt");
        window.add(mainPanel);

        MainMenu mainMenu = new MainMenu();

        mainPanel.add(mainMenu, "mainMenu");
        mainPanel.add(screen, "screen");

        card.show(mainPanel, "mainMenu");
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}