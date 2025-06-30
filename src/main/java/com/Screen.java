package main.java.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.java.com.models.Player;
import main.java.com.models.map.Map;
import main.java.com.views.MapDisplay;
import main.java.com.views.Sprite;
import main.java.com.views.utilities.ImageLoader;
import main.java.com.views.utilities.KeyHandler;

public class Screen extends JPanel implements Runnable{
    private static int multiplicator = 3;
    private static final int TILE_SIZE = 16;

    private static int screenWidth = multiplicator*TILE_SIZE*15;
    private static int screenHeight = multiplicator*TILE_SIZE*15;
    private static final int FPS = 30;
    Thread gameThread;

    Map map;
    MapDisplay dis;
    private Player player;

    private KeyHandler kh;;

    public Screen(){
        this.setSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        kh = new KeyHandler();
        this.addKeyListener(kh);
        this.setFocusable(true);
        this.requestFocusInWindow();

        map = new Map(15, 15);
        dis = new MapDisplay(map.getMap());
        player = new Player(15/2, 15/2);
    }
    public static int getScreenHeight() {
        return screenHeight;
    }
    public static int getScreenWidth() {
        return screenWidth;
    }
    public static int getTileSize() {
        return TILE_SIZE;
    }
    public static int getMultiplicator() {
        return multiplicator;
    }
    public static int getSpriteSize(){
        return TILE_SIZE*multiplicator;
    }
    public static void setMultiplicator(int multiplicator) {
        Screen.multiplicator = multiplicator;
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
                revalidate();
                delta--;
            }

        }
    }
    public void update(){
        player.globalMove(kh);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        dis.displayMap(g2);
        new Sprite(player.getX()*multiplicator, player.getX()*multiplicator, TILE_SIZE, TILE_SIZE, ImageLoader.loadImage("src/ressources/image/player.png").getImage()).display(g2);
        g2.dispose();
    }
}
