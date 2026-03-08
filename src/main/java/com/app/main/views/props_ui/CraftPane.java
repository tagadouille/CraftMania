package com.app.main.views.props_ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.main.models.resources.RecipeEnum;
import com.app.main.util.image.ImageUtil;
import com.app.main.views.utilities.RecipeImageEnum;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * The CraftPane class represents a pane that displays crafting options in the inventory.
 * 
 * @author Dai Elias
 */
public final class CraftPane extends VBox {

    private List<VBox> lines = new ArrayList<>();

    /**
     * Constructor for the CraftPane class. Initializes the pane and displays the crafting options.
     */
    public CraftPane() {
        super(5);
        displayCraft();
    }

    public List<VBox> getLines() {
        return lines;
    }

    private void displayCraft() {
        ScrollPane scrollPane = new ScrollPane();
        
        VBox recipePanel = new VBox(5);
        for (int i = 0; i < RecipeEnum.values().length; i++) {

            if(i == 0 || i % 2 == 0) {
                lines.add(new VBox(5));
            }

            RecipeEnum recipe = RecipeEnum.values()[i];

            // Recipe image display :
            Image img = null;

            for(RecipeImageEnum type : RecipeImageEnum.values()){
                if(recipe.toString().equals(type.toString())){
                    img = type.getImage();
                    break;
                }
            }
            try {
                ImageView imageView = new ImageView(ImageUtil.resizeImage(img, 50, 50));
                lines.getLast().getChildren().add(imageView);
            }
            catch(IOException e){
            }
        }
        recipePanel.getChildren().addAll(lines);
        scrollPane.setContent(recipePanel);
        this.getChildren().add(scrollPane);
    }
}