package com.app.main.controllers;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    public final Set<KeyCode> keylist = new HashSet<>();

    /**
     * Attache des handlers clavier à la scène pour maintenir la liste
     * {@link #keylist} des touches pressées.
     */
    public void setupInput(Scene scene) {

        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            keylist.add(e.getCode());
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            keylist.remove(e.getCode());
        });
    }
}
