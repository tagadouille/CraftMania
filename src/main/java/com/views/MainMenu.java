package main.java.com.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.Main;

public class MainMenu extends JPanel{
    
    public MainMenu(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton newGame = new JButton("New Game");
        JButton continu = new JButton("Continue");
        JButton settings = new JButton("Settings");
        JButton quit = new JButton("Quit");

        quit.addActionListener((e) -> {System.exit(0);});
        newGame.addActionListener((e) -> {
            Main.card.show(Main.mainPanel, "screen");
            Main.screen.launchGameThread();
        });

        constraints.gridx = 0;
        this.add(newGame, constraints);
        constraints.gridy++;
        this.add(continu, constraints);
        constraints.gridy++;
        this.add(settings, constraints);
        constraints.gridy++;
        this.add(quit, constraints);
        constraints.gridy++;
    }
}
