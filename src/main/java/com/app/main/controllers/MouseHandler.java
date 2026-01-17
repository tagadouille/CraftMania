package com.app.main.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{

    private MouseController mouseController;

    public MouseHandler(MouseController mouseController){
        this.mouseController = mouseController;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseController.process(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
