package main.java.com;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
    static int multiplicator = 3;
    static final int TILE_SIZE = 16;

    static int screenWidth = multiplicator*TILE_SIZE*15;
    static int screenHeight = multiplicator*TILE_SIZE*15;
    private static final int FPS = 30;
    Thread gameThread;

    public Screen(){
        this.setSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
    public void launchGameThread(){
        this.gameThread = new Thread(this);
        gameThread.start();
    }
    public void run(){
         // Intervalle de mise à jour (en nanosecondes)
        double intervalMaj = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long actualTime;

        while (true){
            actualTime = System.nanoTime();
            delta += (actualTime - lastTime) / intervalMaj;
            lastTime = actualTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }
    }
    public void update(){
        System.out.println("salut");
    }
}
