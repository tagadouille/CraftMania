package com.app.main.controllers.view_controller.props_ui;

import java.util.List;

import com.app.main.models.resources.ResourceEnum;
import com.app.main.views.props_ui.PropUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The HarvesterView class represents the user interface for the harvester prop in the application.
 * It extends the PropUI class to inherit common UI properties and behaviors.
 * 
 * @see PropUI
 * 
 * @author Dai Elias
 */
public class HarvesterView extends PropUI {

    private Scene mainScene;
    private VBox content;

    private Button harvestButton = new Button("Get Ressource");
    private Text harvestText = new Text();
    
    public HarvesterView() {
        super("Harvester", 200, 200);

        VBox root = new VBox(5);
        this.mainScene = new Scene(root);

        ScrollPane scrollPane = new ScrollPane();

        content = new VBox(5);

        scrollPane.setContent(content);

        root.getChildren().add(scrollPane);
        root.getChildren().add(harvestText);
        root.getChildren().add(harvestButton);

        this.setScene(mainScene);
    }

    public Button getHarvestButton() {
        return harvestButton;
    }

    public VBox getContent() {
        return content;
    }

    public void updateHarvestText(int nb) {
        this.harvestText.setText("The number of resources \n in the harvester is : " + nb);
    }

    /**
     * The method update the nearby resources in the harvester view.
     * @param nearbyResources the list of the nearby resources to display in the harvester view.
     */
    public void updateNearbyResources(List<ResourceEnum> nearbyResources) {

        Text text = new Text("Choose the resource to produce :");

        content.getChildren().add(text);

        for (ResourceEnum res : nearbyResources) {
            Button button = new Button(res.name());
            content.getChildren().add(button);
        }

    }
    
}
