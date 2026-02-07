package com.app.main.views.props_ui;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The PropUI class serves as a base class for various property user interface components in the application.
 * It extends the Stage class from JavaFX and provides common properties and behaviors for UI elements such as dialogs and views.
 */
public abstract class PropUI extends Stage {

    /**
     * Constructor to initialize the PropUI with a specified title.
     * @param title the title of the PropUI window
     */
    protected PropUI(String title, int width, int height) {

        if(title == null) {
            title = "Prop UI";
        }

        if(width <= 0) {
            width = 300;
        }

        if(height <= 0) {
            height = 300;
        }

        this.setTitle(title);

        this.setWidth(width);
        this.setHeight(height);

        this.setAlwaysOnTop(true);
        this.requestFocus();

        this.initModality(Modality.WINDOW_MODAL);
        this.setResizable(false);
    }
    
}
