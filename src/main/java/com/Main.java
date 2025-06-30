package main.java.com;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.com.views.MainMenu;

import java.awt.CardLayout;
import java.awt.Dimension;

public class Main{
    public static CardLayout card = new CardLayout();
    public static JPanel mainPanel = new JPanel(card);
    public static Screen screen = new Screen();
    public static void main(String[] args) {
        
        JFrame window = new JFrame("CraftMania 3000");

        MainMenu mainMenu = new MainMenu();

        mainPanel.add(mainMenu, "mainMenu");
        mainPanel.add(screen, "screen");

        card.show(mainPanel, "mainMenu");

        window.add(mainPanel);
        window.setSize(new Dimension(screen.getWidth(), screen.getWidth()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}