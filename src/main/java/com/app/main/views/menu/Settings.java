package com.app.main.views.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.ButtonType;

/**
 * The Settings class represents the settings menu scene of the application.
 * It provides options for the user to customize their experience, 
 * such as choosing the window size.
 * 
 * @author Dai Elias
 */
public final class Settings extends Scene {

    private ChoiceBox<String> chooseWindowSize = new ChoiceBox<>();

    private Button back = new Button("Back");
    private Button save = new Button("Save Settings");

    private Settings(int size) {
        super(new HBox(0));
        HBox root = (HBox) this.getRoot();

        root.setPrefHeight(size);
        root.setPrefWidth(size);
        root.setAlignment(Pos.CENTER);

        root.getStyleClass().add("menu");

        VBox menuBox = new VBox(10);

        menuBox.setAlignment(Pos.CENTER);

        Font font = Font.font(size / 40);

        save.setFont(font);
        back.setFont(font);

        this.chooseWindowSize.getItems().addAll("400x400", "800x800", "1200x1200", "1600x1600", "2000x2000");
        this.chooseWindowSize.setValue(this.chooseWindowSize.getItems().get(0));

        this.chooseWindowSize.setPrefWidth(size / 6);
        this.chooseWindowSize.setPrefHeight(size / 15);

        menuBox.getChildren().addAll(chooseWindowSize, save, back);
        root.getChildren().add(menuBox);
    }

    /**
     * Creates a Settings instance with the specified width and height.
     * @param size the width and the height of the settings menu
     * @return a new Settings instance
     */
    public static Settings create(int size) {

        if(size <= 0) {
            throw new IllegalArgumentException("The size can't be null");
        }
        return new Settings(size);
    }

    public ChoiceBox<String> getChooseWindowSize() {
        return chooseWindowSize;
    }

    public Button getSave() {
        return save;
    }

    public Button getBack() {
        return back;
    }

    /**
     * Displays an error alert indicating that an error occurred while saving the file.
     */
    public static void displayError(String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("Error Setting");
        alert.showAndWait();
    }

    /**
     * Displays a success alert indicating that the setting was saved successfully.
     */
    public static void displaySuccess() {
        Alert alert = new Alert(AlertType.INFORMATION, "The setting was saved successfuly", ButtonType.OK);
        alert.setHeaderText("Settings saved");
        alert.showAndWait();
    }
    
}
