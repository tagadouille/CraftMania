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

    private Factory(int price, long capacity, long production_duration) {
        super(price, capacity, production_duration);
    }

    private Factory(int price) {
        this(price, 100,5000);
    }

    private Factory(int price, long capacity){
        this(price, capacity, 5000);
    }

    /**
     * Set the recipe of the factory for the config
     * @param recipeEnum the recipe
     */
    public void setRecipe(RecipeEnum recipeEnum) {
        if(recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
        this.recipe = recipeEnum.getRecipe();
        this.setProduct(recipe.getResult());
    }

    /**
     * Factory method to create a SimpleFactory.
     * @return a new SimpleFactory instance
     */
    public static SimpleFactory createSimpleFactory() {
        return new SimpleFactory();
    }

    /**
     * Factory method to create a FastFactory.
     * @return a new FastFactory instance
     */
    public static FastFactory createFastFactory() {
        return new FastFactory();
    }

    /**
     * Factory method to create a XLFactory.
     * @return a new XLFactory instance
     */
    public static XLFactory createXLFactory() {
        return new XLFactory();
    }

    /**
     * Factory method to create a WeakFactory.
     * @return a new WeakFactory instance
     */
    public static WeakFactory createWeakFactory() {
        return new WeakFactory();
    }

    /**
     * Factory method to create a PolyFactory.
     * @return a new PolyFactory instance
     */
    public static PolyFactory createPolyFactory() {
        return new PolyFactory();
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

        private SimpleFactory() {
            super(100);
        }
    }

    /**
     * The XLFactory class is a concrete implementation of the Factory class.
     * It represents an extra-large factory type with enhanced production capabilities.
     */
    public static final class XLFactory extends Factory {

        private XLFactory() {
            super(175, 350);
        }
    }

    /**
     * The FastFactory class is a concrete implementation of the Factory class.
     * It represents a fast factory type with accelerated production capabilities.
     */
    public static final class FastFactory extends Factory {

        private FastFactory() {
            super(250, 150, 2500);
        }
    }

    /**
     * The WeakFactory class is a concrete implementation of the Factory class.
     * It represents a weak factory type that can break sometimes.
     */
    public static final class WeakFactory extends Factory {

        private boolean broken = false;

        private WeakFactory() {
            super(50);
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

        private PolyFactory() {
            super(150);
        }
    }

}
