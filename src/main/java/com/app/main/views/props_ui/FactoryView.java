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

    private Text fillIg1Text = new Text("Number of ingredient 1 :");
    private Text fillIg2Text = new Text("Number of ingredient 2 :");

    private Button fillIg1 = new Button("");
    private Button fillIg2 = new Button("");

    private VBox box = new VBox(5);

    private CraftPane craftPane =  new CraftPane();
    
    /**
     * Constructor for the FactoryView class. Initializes the UI components and layout for the factory view.
     */
    public FactoryView() {
        super("Factory", 200, 200);

        ScrollPane mainPane = new ScrollPane();
        this.mainScene = new Scene(mainPane);

        ScrollPane scrollPane = new ScrollPane();

        content = new VBox(5);

        scrollPane.setContent(content);

        HBox ig1Box = new HBox(5);
        ig1Box.getChildren().addAll(fillIg1, nbIg1);

        nbIg1.setMaxWidth(200);
        nbIg2.setMaxWidth(200);

        HBox ig2Box = new HBox(5);
        ig2Box.getChildren().addAll(fillIg2, nbIg2);

        box.getChildren().add(scrollPane);
        box.getChildren().add(resText);
        box.getChildren().add(fillIg1Text);
        box.getChildren().add(ig1Box);
        box.getChildren().add(fillIg2Text);
        box.getChildren().add(ig2Box);
        box.getChildren().add(getButton);

        mainPane.setContent(box);

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
     * Updates the text displaying the number of ingredient 1 to be added to the factory.
     * @param nb the number of ingredient 1 to be added to the factory
     */
    public void updateIg1Text(int nb) {

        if(nb < 0) {
            throw new IllegalArgumentException("Number of resources cannot be negative");
        }
        this.fillIg1Text.setText("Number of ingredient 1 : " + nb);
    }

    /**
     * Updates the text displaying the number of ingredient 2 to be added to the factory.
     * @param nb the number of ingredient 2 to be added to the factory
     */
    public void updateIg2Text(int nb) {

        if(nb < 0) {
            throw new IllegalArgumentException("Number of resources cannot be negative");
        }
        this.fillIg2Text.setText("Number of ingredient 2 : " + nb);
    }

    /**
     * Display the recipe choosing menu
     */
    public void displayCraft() {
        box.getChildren().addFirst(this.craftPane);
    }

    /**
     * Hide the recipe choosing menu
     * Use it after calling displayCraft()
     */
    public void hideCraft() {
        box.getChildren().remove(0);
    }
}
