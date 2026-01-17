/*package main.java.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.java.com.controllers.KeyHandler;
import main.java.com.controllers.MouseController;
import main.java.com.controllers.MouseHandler;
import main.java.com.controllers.PlayerController;
import main.java.com.models.Player;
import main.java.com.models.map.Map;
import main.java.com.views.HarvestBar;
import main.java.com.views.MapDisplay;
import main.java.com.views.PlayerSprite;

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

    private KeyHandler kh;
    private MouseHandler mh;

    private PlayerController playerController;

    private PlayerSprite playerSprite;

    private HarvestBar harvestBar = new HarvestBar();

    private JPanel pan;

    public Screen(){
        this.setSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        kh = new KeyHandler();
        this.addKeyListener(kh);

        map = new Map(15, 15);
        dis = new MapDisplay(map.getMap());
        player = new Player(15/2, 15/2);

        this.playerController = new PlayerController(player, this);
        this.playerSprite = new PlayerSprite(player);

        mh =  new MouseHandler(new MouseController(map, player, playerController));
        this.addMouseListener(mh);

        this.pan = new JPanel();
        this.add(pan);
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
    public HarvestBar getHarvestBar() {
        return harvestBar;
    }
    public PlayerSprite getPlayerSprite() {
        return playerSprite;
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
                revalidate();
                repaint();
                delta--;
            }

        }
    }
    public void update(){
        playerController.process(kh, map);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        dis.displayMap(g2);
        playerSprite.display(g2);
        paintChildren(g2);
        g2.dispose();
    }
    public void despawnHarvestBar(){
        this.harvestBar.despawn(this);
    }
    public void spawnHarvestBar(){
        this.harvestBar.spawn((playerSprite.getPosX()*multiplicator) - harvestBar.getWidth()/4, (playerSprite.getPosY()*multiplicator) - getSpriteSize()/2, this);
        this.setComponentZOrder(harvestBar, 0);
        revalidate();
    }
}
*/