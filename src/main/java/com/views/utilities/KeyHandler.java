package main.java.com.views.utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public KeyHandler(){
        
    }
    public boolean getUp(){
        return this.up;
    }
    public boolean getLeft(){
        return this.left;
    }
    public boolean getRight(){
        return this.right;
    }
    public boolean getDown(){
        return this.down;
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = false;
        }
    }
    
}
