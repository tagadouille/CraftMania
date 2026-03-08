package com.app.main.views.props_ui;

import com.app.main.models.resources.ResourceEnum;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The FactoryView class represents the user interface for the factory prop in the application.
 * It extends the PropUI class to inherit common UI properties and behaviors.
 * 
 * @see PropUI
 * @author Dai Elias
 */
public final class FactoryView extends PropUI {

    private Scene mainScene;
    private VBox content;

    private Button getButton = new Button("Get Resources");
    private Text resText = new Text("The number of resources \n in the factory is : 0");

    private Spinner<Integer> nbIg1 = new Spinner<>(1, 500, 1);
    private Spinner<Integer> nbIg2 = new Spinner<>(1, 500, 1);

    private Button fillIg1 = new Button("");
    private Button fillIg2 = new Button("");

    private VBox root;

    private CraftPane craftPane =  new CraftPane();
    
    /**
     * Constructor for the FactoryView class. Initializes the UI components and layout for the factory view.
     */
    public FactoryView() {
        super("Factory", 200, 200);

        root = new VBox();
        this.mainScene = new Scene(root);

        ScrollPane scrollPane = new ScrollPane();

        content = new VBox(5);

        scrollPane.setContent(content);

        HBox ig1Box = new HBox(5);
        ig1Box.getChildren().addAll(fillIg1, nbIg1);

        HBox ig2Box = new HBox(5);
        ig2Box.getChildren().addAll(fillIg2, nbIg2);

        root.getChildren().add(scrollPane);
        root.getChildren().add(resText);
        root.getChildren().add(ig1Box);
        root.getChildren().add(ig2Box);
        root.getChildren().add(getButton);

        this.setScene(mainScene);
    }

    /* Getters : */

    public Button getGetButton() {
        return getButton;
    }

    public VBox getContent() {
        return content;
    }

    public CraftPane getCraftPane() {
        return craftPane;
    }

    public Button getFillIg1() {
        return fillIg1;
    }

    public Button getFillIg2() {
        return fillIg2;
    }

    public Spinner<Integer> getNbIg1() {
        return nbIg1;
    }

    public Spinner<Integer> getNbIg2() {
        return nbIg2;
    }

    /**
     * Changes the text of the fillIg1 button to the name of the given resource.
     * @param res the resource whose name will be displayed on the fillIg1 button
     */
    public void changeFillIg1Text(ResourceEnum res) {
        fillIg1.setText("Add " + res.name().toLowerCase());
    }

    /**
     * Changes the text of the fillIg1 button to the name of the given resource.
     * @param res the resource whose name will be displayed on the fillIg1 button
     */
    public void changeFillIg2Text(ResourceEnum res) {
        fillIg2.setText("Add " + res.name().toLowerCase());
    }

    /**
     * Updates the text displaying the number of resources in the factory.
     * @param nb the number of resources currently in the factory
     */
    public void updateResText(int nb) {

        if(nb < 0) {
            throw new IllegalArgumentException("Number of resources cannot be negative");
        }
        this.resText.setText("The number of resources \n in the factory is : " + nb);
    }

    /**
     * Display the recipe choosing menu
     */
    public void displayCraft() {
        root.getChildren().addFirst(this.craftPane);
    }

    /**
     * Hide the recipe choosing menu
     * Use it after calling displayCraft()
     */
    public void hideCraft() {
        root.getChildren().remove(0);
    }
}
