package com.app.main.controllers.view_controller.menu;

import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.save.LoadSave;
import com.app.main.views.GameScene;
import com.app.main.views.menu.MainMenu;

/**
 * The MainMenuController class handles the interactions and behaviors of the MainMenu view.
 * It sets up the button actions for starting a new game, loading a save, accessing settings, and quitting the application.
 * 
 * @author Dai Elias
 */
public final class MainMenuController {
    
    private MainMenu mainMenu;

    private MainMenuController(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setButtonBehavior();
    }

    /**
     * Creates a MainMenuController instance for the given MainMenu.
     * @param mainMenu the MainMenu instance to be controlled
     * @return a new MainMenuController instance
     */
    public static MainMenuController create(MainMenu mainMenu) {

        if(mainMenu == null) {
            throw new IllegalArgumentException("The main menu can't be null");
        }
        return new MainMenuController(mainMenu);
    }

    private void setButtonBehavior() {

        mainMenu.getQuit().setOnAction((e) -> System.exit(0));

        mainMenu.getSettings().setOnAction((e) -> {

        });

        mainMenu.getNewGame().setOnAction((e) -> {
            MenuSwitcher.switchScene(GameScene.create());
        });

        mainMenu.getLoad().setOnAction((e) -> {
            Player player = Player.createPlayer(7, 7);
            GameMap gameMap = GameMap.createDefaultMap(false);

            boolean success = LoadSave.load(player, gameMap);

            mainMenu.loadNotif(success);

            if(success) {
                MenuSwitcher.switchScene(GameScene.create(player, gameMap));
            }
        });
    }
}
