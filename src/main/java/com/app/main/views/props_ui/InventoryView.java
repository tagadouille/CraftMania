package com.app.main.views.props_ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.main.models.resources.RecipeEnum;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.util.image.ImageUtil;
import com.app.main.views.utilities.RecipeImageEnum;
import com.app.main.views.utilities.ItemImageEnum;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The InventoryView class represents the inventory user interface in the application.
 * It extends the PropUI class to inherit common UI properties and behaviors.
 * 
 * @see PropUI
 * 
 * @author Dai Elias
 */
public class InventoryView extends PropUI {

    private Scene mainScene;

    private CraftPane craftPane;
    private ItemPane itemPane;
    
    public InventoryView() {
        super("Inventory", 300, 300);

        VBox root = new VBox(5);
        this.mainScene = new Scene(root);

        this.setScene(mainScene);

        this.itemPane = new ItemPane();
        this.craftPane = new CraftPane();

        root.setFillWidth(true);

        root.getChildren().add(this.itemPane);
        root.getChildren().add(this.craftPane);
    }

    public CraftPane getCraftPane() {
        return craftPane;
    }

    public Text[] getItemNb() {
        return itemPane.getItemNb();
    }


    /**
     * The ItemPane class represents a pane that displays items in the inventory.
     * @author Dai Elias
     */
    private static class ItemPane extends HBox {

        private Text[] itemNb = new Text[ResourceEnum.values().length];
        
        private ItemPane() {
            super(5);
            this.setMaxWidth(Double.MAX_VALUE);
            displayItem();
        }

        public Text[] getItemNb() {
            return itemNb;
        }

        /**
         * Displays the items in the inventory pane.
         */
        private void displayItem() {

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            HBox.setHgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
            
            VBox resourcePanel = new VBox(10);
            resourcePanel.setAlignment(Pos.CENTER);

            resourcePanel.setFillWidth(true);
            HBox currentRow = null;

            for (int i = 0; i < ResourceEnum.values().length; i++) {
                ResourceEnum res = ResourceEnum.values()[i];

                // Create a new row every 2 items
                if (i % 2 == 0) {
                    currentRow = new HBox(10);
                    currentRow.setMaxWidth(Double.MAX_VALUE);
                    resourcePanel.getChildren().add(currentRow);
                }

                HBox itemBox = new HBox();

                // Resource image display :
                Image img = null;

                for(ItemImageEnum type : ItemImageEnum.values()){
                    if(res.getResource().getName().equals(type.toString())){
                        img = type.getImage();
                        break;
                    }
                }

                try {
                    ImageView imageView = new ImageView(ImageUtil.resizeImage(img, 50, 50));

                    itemBox.getChildren().add(imageView);
                }
                catch(IOException e){

                }

                VBox infoBox = new VBox(0);
                HBox title = new HBox(1);

                // Informations display :
                Text resName = new Text(res.toString().toLowerCase());
                title.getChildren().add(resName);

                itemNb[res.ordinal()] = new Text("0");

                infoBox.getChildren().add(title);
                infoBox.getChildren().add(itemNb[res.ordinal()]);

                itemBox.getChildren().add(infoBox);

                currentRow.getChildren().add(itemBox);
            }
            scrollPane.setContent(resourcePanel);
            this.getChildren().add(scrollPane);
        }
    }

    /**
     * The CraftPane class represents a pane that displays crafting options in the inventory.
     * 
     * @author Dai Elias
     */
    public class CraftPane extends VBox {

        private List<VBox> lines = new ArrayList<>();

        private CraftPane() {
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
}
