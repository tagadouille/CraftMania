package com.app.main.controllers.view_controller.menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.app.main.views.menu.MainMenu;
import com.app.main.views.menu.Settings;

/**
 * The SettingsController class is responsible for handling 
 * the logic and interactions of the settings menu in the application.
 * It manages the settings menu scene and processes user input 
 * to update the settings accordingly.
 * 
 * @see Settings
 * @author Dai Elias
 */
public final class SettingsController {
    
    private Settings settings;

    private int size;

    private SettingsController(Settings settings) {
        this.settings = settings;
        size = getSettings();
        buttonBehavior();
    }

    /**
     * Creates a SettingsController instance with the specified settings.
     * @param settings the settings menu to be controlled
     * @return a new SettingsController instance
     */
    public static SettingsController create(Settings settings) {

        if(settings == null) {
            throw new IllegalArgumentException("The settings can't be null");
        }
        return new SettingsController(settings);
    }

    private void buttonBehavior() {

        settings.getBack().setOnAction((e) -> {
            MainMenu mainMenu = MainMenu.create(size);
            MainMenuController.create(mainMenu);
            MenuSwitcher.switchScene(mainMenu);
        });

        settings.getSave().setOnAction((e) -> {
            handleWindowSizeChange();
        });
    }

    private void handleWindowSizeChange() {
        String selectedSize = settings.getChooseWindowSize().getValue();
        
        this.size = Integer.parseInt(selectedSize.split("x")[0]);

        // Update the application window size based on the selected size
        settings = Settings.create(size);

        // Save the size in a file
        File file = new File("files/settings.txt");

        File parentDirectory = file.getParentFile();
        if(parentDirectory != null && !parentDirectory.exists() && !parentDirectory.mkdirs()) {
            Settings.displayError("An error occured while saving the file");
            return;
        }

        if(file.exists()) {
            file.delete();
        }
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(Integer.toString(size));
        } 
        catch (IOException e) {
            Settings.displayError("An error occured while saving the file");
            return;
        }
        Settings.displaySuccess();
    }

    /**
     * Retrieves the current settings from the settings file.
     * @return the current settings value, or a default value if the file does not exist or an error occurs
     */
    public static int getSettings() {
        File file = new File("files/settings.txt");

        if(!file.exists()) {
            return 400;
        }

        try(Scanner scanner = new Scanner(file)) {
            if(scanner.hasNextInt()) {
                int loadedSize = scanner.nextInt();
                if(loadedSize > 0) {
                    return loadedSize;
                }
            }
        }
        catch (Exception e) {
            // Keep startup resilient: this method can be called before JavaFX is initialized.
        }
        return 400;
    }
}
