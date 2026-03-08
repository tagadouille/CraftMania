package com.app.main.controllers.view_controller.props_ui;

import java.util.List;

import com.app.main.models.Player;
import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Factory.PolyFactory;
import com.app.main.models.resources.RecipeEnum;
import com.app.main.views.props_ui.FactoryView;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * FactoryViewController is responsible for managing the view of a Factory object in the UI. 
 * It provides methods to create and update the view based on the state of the Factory.
 * 
 * @see FactoryView
 */
public final class FactoryViewController {
    
    private Factory factory;
    private Player player;

    private FactoryView factoryView;

    private FactoryViewController(Factory factory, FactoryView factoryView, Player player) {
        this.factory = factory;
        this.factoryView = factoryView;
        this.player = player;
        buttonBehavior();
    }

    /**
     * Creates a new instance of FactoryViewController with the given Factory and FactoryView.
     * @param factory the Factory object to be managed by this controller
     * @param factoryView the FactoryView object that will be updated based on the state of the Factory
     * @return a new instance of FactoryViewController
     */
    public static FactoryViewController create(Factory factory, FactoryView factoryView, Player player) {

        if(factory == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        if(factoryView == null) {
            throw new IllegalArgumentException("FactoryView cannot be null");
        }
        if(player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return new FactoryViewController(factory, factoryView, player);
    }

    private void buttonBehavior() {

        // Configuration of the factory
        if(!factory.isAlreadySetted() || factory instanceof PolyFactory) {
            factoryView.displayCraft();


            List<VBox> lines = factoryView.getCraftPane().getLines();

            int i = 0;

            for(VBox line : lines) {
                
                for(Node box : line.getChildren()) {

                    int finalI = i;

                    box.setOnMouseClicked(e -> {

                        if(factory instanceof PolyFactory && factory.getRecipe() != null) {
                            factory.cleanInventory(player);

                            for(int j = 0; j < factory.getInventory().countResource(factory.getRecipe().getIngredient1()); j++) {
                                player.getInventory().addResource(factory.getRecipe().getIngredient1());
                            }

                            for(int j = 0; j < factory.getInventory().countResource(factory.getRecipe().getIngredient2()); j++) {
                                player.getInventory().addResource(factory.getRecipe().getIngredient2());
                            }
                        }
                        RecipeEnum recipe = RecipeEnum.values()[finalI];

                        factory.setRecipe(recipe);
                        factoryView.hideCraft();
                        buttonBehavior();
                        return;
                    });
                    
                    i++;
                }
            }
            return;
        }

        factoryView.changeFillIg1Text(factory.getRecipe().getIngredient1());
        factoryView.changeFillIg2Text(factory.getRecipe().getIngredient2());
        update();

        // Configuration of the buttons
        factoryView.getGetButton().setOnAction(e -> {
            factory.cleanInventory(player);
            update();
        });

        factoryView.getFillIg1().setOnAction(e -> {

            for (int index = 0; index < factoryView.getNbIg1().getValue(); index++) {
                factory.getInventory().addResource(factory.getRecipe().getIngredient1());
                player.getInventory().removeResource(factory.getRecipe().getIngredient1());
            }
            update();
        });

        factoryView.getFillIg2().setOnAction(e -> {

            for (int index = 0; index < factoryView.getNbIg2().getValue(); index++) {
                factory.getInventory().addResource(factory.getRecipe().getIngredient2());
                player.getInventory().removeResource(factory.getRecipe().getIngredient2());
            }
            update();
        });
    }

    private void update() {
        factoryView.updateResText(factory.getInventory().countResource(factory.getProduct().get()));
        factoryView.updateIg1Text(factory.getInventory().countResource(factory.getRecipe().getIngredient1()));
        factoryView.updateIg2Text(factory.getInventory().countResource(factory.getRecipe().getIngredient2()));
    }
}
