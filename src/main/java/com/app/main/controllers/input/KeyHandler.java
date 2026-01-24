package com.app.main.controllers.input;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The KeyHandler class is responsible for handling keyboard input events.
 * It maintains a set of currently pressed keys and provides methods to
 * set up event handlers for key press and release events.
 * @author Dai Elias
 */
public class KeyHandler {

    public final Set<KeyCode> keylist = new HashSet<>();

    /**
     * Sets up the input handlers for the given scene.
     * @param scene the JavaFX scene to attach the input handlers to
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
