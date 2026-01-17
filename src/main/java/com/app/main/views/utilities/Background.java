/*package main.java.com.views.utilities;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel{
    
    private String imagePath;
    private Dimension panelDimension;

    public Background(String imagePath, Dimension panelDimension){
        super();
        this.setPreferredSize(panelDimension);
    }
    public String getImagePath() {
        return imagePath;
    }
    public Dimension getPanelDimension() {
        return panelDimension;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setPanelDimension(Dimension panelDimension) {
        this.panelDimension = panelDimension;
    }
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon img = ImageLoader.loadImage(imagePath);
        if(img != null){
            g.drawImage(img.getImage(), 0, 0, (int) panelDimension.getWidth(), (int) panelDimension.getHeight(),null);
        }
    }
}*/
