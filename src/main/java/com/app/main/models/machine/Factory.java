package com.app.main.models.machine;

import java.util.Random;

import com.app.main.models.resources.Recipe;
import com.app.main.models.resources.RecipeEnum;

/**
 * Factory class representing a production factory in the game.
 * Extends the Machine class and includes recipe-based production capabilities.
 * 
 * @see Machine
 * @author Dai Elias
 */
public sealed abstract class Factory extends Machine permits com.app.main.models.machine.Factory.SimpleFactory, com.app.main.models.machine.Factory.XLFactory, com.app.main.models.machine.Factory.FastFactory, com.app.main.models.machine.Factory.WeakFactory, com.app.main.models.machine.Factory.PolyFactory {

    private Recipe recipe;

    private Factory(int price, Recipe recipe, long capacity, long production_duration) {
        super(price, recipe.getResult(), capacity, production_duration);
    }

    private Factory(int price, Recipe recipe) {
        this(price, recipe, 100,5000);
    }

    private Factory(int price, Recipe recipe, long capacity){
        this(price, recipe, capacity, 5000);
    }

    /**
     * Factory method to create a SimpleFactory.
     * @param product the resource produced by the factory
     * @return a new SimpleFactory instance
     */
    public static SimpleFactory createSimpleFactory(RecipeEnum recipe) {
        return new SimpleFactory(recipe.getRecipe());
    }

    /**
     * Factory method to create a FastFactory.
     * @param recipe the resource produced by the factory
     * @return a new FastFactory instance
     */
    public static FastFactory createFastFactory(RecipeEnum recipe) {
        return new FastFactory(recipe.getRecipe());
    }

    /**
     * Factory method to create a XLFactory.
     * @param recipe the resource produced by the factory
     * @return a new XLFactory instance
     */
    public static XLFactory createXLFactory(RecipeEnum recipe) {
        return new XLFactory(recipe.getRecipe());
    }

    /**
     * Factory method to create a WeakFactory.
     * @param recipe the resource produced by the factory
     * @return a new WeakFactory instance
     */
    public static WeakFactory createWeakFactory(RecipeEnum recipe) {
        return new WeakFactory(recipe.getRecipe());
    }

    /**
     * Factory method to create a PolyFactory.
     * @param recipe the resource produced by the factory
     * @return a new PolyFactory instance
     */
    public static PolyFactory createPolyFactory(RecipeEnum recipe) {
        return new PolyFactory(recipe.getRecipe());
    }

    /**
     * Processes the factory's production by checking for required ingredients
     * and producing the resulting resource if conditions are met.
     */
    @Override
    public void process() {

        if(inventory.countResource(recipe.getIngredient1()) > 0 && inventory.countResource(recipe.getIngredient2()) > 0) {
            inventory.removeResource(recipe.getIngredient1());
            inventory.removeResource(recipe.getIngredient2());
            super.product();
        }
    }

    /**
     * The SimpleFactory class is a concrete implementation of the Factory class.
     * It represents a basic factory type with standard production capabilities.
     */
    public static final class SimpleFactory extends Factory {

        private SimpleFactory(Recipe recipe) {
            super(100, recipe);
        }
    }

    /**
     * The XLFactory class is a concrete implementation of the Factory class.
     * It represents an extra-large factory type with enhanced production capabilities.
     */
    public static final class XLFactory extends Factory {

        private XLFactory(Recipe recipe) {
            super(175, recipe, 350);
        }
    }

    /**
     * The FastFactory class is a concrete implementation of the Factory class.
     * It represents a fast factory type with accelerated production capabilities.
     */
    public static final class FastFactory extends Factory {

        private FastFactory(Recipe recipe) {
            super(250, recipe, 150, 2500);
        }
    }

    /**
     * The WeakFactory class is a concrete implementation of the Factory class.
     * It represents a weak factory type that can break sometimes.
     */
    public static final class WeakFactory extends Factory {

        private boolean broken = false;

        private WeakFactory(Recipe recipe) {
            super(50, recipe);
        }

        @Override
        /**
         * Processes the factory, with a chance to break down.
         */
        public void process() {

            if(new Random().nextInt(4) == 0) {
                broken = true;
            }
            if(!broken) super.process();
        }

        /**
         * Repair the factory to make it operational again.
         */
        public void repair() {
            broken = false;
        }
    }

    /**
     * The PolyFactory class is a concrete implementation of the Factory class.
     * It represents a factory type that can be configured multiple times.
     */
    public static final class PolyFactory extends Factory {

        private PolyFactory(Recipe recipe) {
            super(150, recipe);
        }
    }

}
