package com.app.main.views.props_ui;

import java.io.IOException;

import com.app.main.models.Market;
import com.app.main.models.machine.FactoryEnum;
import com.app.main.models.machine.HarvesterEnum;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.util.image.ImageUtil;
import com.app.main.views.utilities.FactoryImageEnum;
import com.app.main.views.utilities.HarvesterImageEnum;
import com.app.main.views.utilities.ItemImageEnum;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The MarketDialog class represents a dialog window for the market interface in the game.
 * It allows players to buy and sell resources.
 * Extends the Stage class from JavaFX.
 * Extends PropUI for common UI properties.
 * 
 * @see PropUI
 * @author Dai Elias
 */
public final class MarketDialog extends PropUI {

    private Scene mainScene;

    private Button buy = new Button("Buy");
    private Button sell = new Button("Sell");

    private Text money = new Text("Money : 0$");

    private HBox root;

    /**
     * The constructor initializes the MarketDialog with its properties and UI components.
     */
    public MarketDialog(){

        super("Market Screen", 250, 250);

        root = new HBox(5);
        this.mainScene = new Scene(root);

        this.setScene(mainScene);

        root.getChildren().add(buy);
        root.getChildren().add(sell);
        root.getChildren().add(money);
    }

    /* Getters : */
    public Button getBuy() {
        return buy;
    }

    public Button getSell() {
        return sell;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    /**
     * Updates the money display in the market dialog.
     * @param money the new amount of money to display
     */
    public void updateMoney(int money){
        this.money.setText("Money : " + money + "$");
    }

    /**
     * The BuyPanel class represents the panel where the user can buy items
     */
    public final static class BuyPanel extends Scene{

        private VBox root;
        private Button back = new Button("Back");

        private Market market;

        private Text money = new Text("Money : 0$");

        private ScrollPane scrollPane = new ScrollPane();

        private Button[] resBuyButtons = new Button[ResourceEnum.values().length];

        private Button[] factoryBuyButtons = new Button[FactoryEnum.values().length];

        private Button[] harvesterBuyButtons = new Button[HarvesterEnum.values().length];

        /**
         * Constructor for BuyPanel
         */
        private BuyPanel(Market market){
            super(new VBox());

            root = (VBox) this.getRoot();

            this.market = market;

            HBox header = new HBox(5);

            Text title = new Text("What do you want to buy ?");
            header.getChildren().add(title);

            header.getChildren().add(money);

            root.getChildren().add(back);
            root.getChildren().add(header);

            displayResources();
        }

        /**
         * Factory method to create a BuyPanel instance.
         * @param market the market associated with the BuyPanel
         * @return a new BuyPanel instance
         */
        public static BuyPanel create(Market market) {

            if(market == null){
                throw new IllegalArgumentException("Market cannot be null");
            }
            return new BuyPanel(market);
        }

        /* Getters : */
        public Button getBack() {
            return back;
        }

        public Button[] getResBuyButtons() {
            return resBuyButtons;
        }

        public Button[] getFactoryBuyButtons() {
            return factoryBuyButtons;
        }

        public Button[] getHarvesterBuyButtons() {
            return harvesterBuyButtons;
        }

        /**
         * Updates the money display in the buy panel.
         * @param money the new amount of money to display
         */
        public void updateMoney(int money){
            this.money.setText("Money : " + money + "$");
        }

        private void displayResources(){
            
            updateView();
            root.getChildren().add(scrollPane);
        }

        /**
         * Updates the view of the sell panel to reflect the current state of the market and player's inventory.
         */
        public void updateView() {

            VBox mainBox = new VBox();

            resUpdate(mainBox);
            facUpdate(mainBox);
            harvUpdate(mainBox);
            
            
            scrollPane.setContent(null);
            scrollPane.setContent(mainBox);
        }

        private void resUpdate(VBox mainPanel){
            VBox resourcePanel = new VBox();

            for (ResourceEnum res : ResourceEnum.values()) {
                HBox line = new HBox();

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

                    line.getChildren().add(imageView);
                }
                catch(IOException e){

                }

                VBox infoBox = new VBox(0);
                HBox title = new HBox(1);

                // Informations display :
                Text resName = new Text(res.toString().toLowerCase());
                title.getChildren().add(resName);

                Text price = new Text(res.getResource().getPrice() +"$");
                title.getChildren().add(price);

                infoBox.getChildren().add(title);

                Text number = new Text(market.getPlayer().getInventory().countResource(res) + " already in inventory");
                infoBox.getChildren().add(number);

                line.getChildren().add(infoBox);

                Button buyRes = new Button("Buy");

                resBuyButtons[res.ordinal()] = buyRes;
                
                line.getChildren().add(buyRes);

                resourcePanel.getChildren().add(line);
            }
            mainPanel.getChildren().add(resourcePanel);
        }

        private void facUpdate(VBox mainPanel) {

            VBox factoryPanel = new VBox();

            for (FactoryEnum fac : FactoryEnum.values()) {
                HBox line = new HBox();

                // Resource image display :
                Image img = FactoryImageEnum.factoryToImage(fac);

                try {
                    ImageView imageView = new ImageView(ImageUtil.resizeImage(img, 50, 50));

                    line.getChildren().add(imageView);
                }
                catch(IOException e){

                }

                VBox infoBox = new VBox(0);
                HBox title = new HBox(1);

                // Informations display :
                Text resName = new Text(fac.toString().toLowerCase());
                title.getChildren().add(resName);

                Text price = new Text("Factory " + fac.getFactory().getPrice() +"$");
                title.getChildren().add(price);

                infoBox.getChildren().add(title);

                line.getChildren().add(infoBox);

                Button buyRes = new Button("Buy");

                factoryBuyButtons[fac.ordinal()] = buyRes;
                
                line.getChildren().add(buyRes);

                factoryPanel.getChildren().add(line);
            }
            mainPanel.getChildren().add(factoryPanel);
        }

        private void harvUpdate(VBox mainPanel){
            
            VBox harvestPanel = new VBox();

            for (HarvesterEnum fac : HarvesterEnum.values()) {
                HBox line = new HBox();

                // Resource image display :
                Image img = HarvesterImageEnum.harvesterToImage(fac);

                try {
                    ImageView imageView = new ImageView(ImageUtil.resizeImage(img, 50, 50));

                    line.getChildren().add(imageView);
                }
                catch(IOException e){

                }

                VBox infoBox = new VBox(0);
                HBox title = new HBox(1);

                // Informations display :
                Text resName = new Text(fac.toString().toLowerCase());
                title.getChildren().add(resName);

                Text price = new Text("Harvester " + fac.getHarvester().getPrice() +"$");
                title.getChildren().add(price);

                infoBox.getChildren().add(title);

                line.getChildren().add(infoBox);

                Button buyRes = new Button("Buy");

                harvesterBuyButtons[fac.ordinal()] = buyRes;
                
                line.getChildren().add(buyRes);

                harvestPanel.getChildren().add(line);
            }
            mainPanel.getChildren().add(harvestPanel);
        }
    }

    /**
     * The SellPanel class represents the panel where the user can sell items.
     * Extends Scene class from JavaFX.
     * @author Dai Elias
     */
    public final static class SellPanel extends Scene{

        private VBox root;

        private Button back = new Button("Back");

        private Market market;

        private Text money = new Text("Money : 0$");

        private Button[] sellButtons = new Button[ResourceEnum.values().length];

        private ScrollPane scrollPane = new ScrollPane();

        private SellPanel(Market market){
            super(new VBox());
            this.root = (VBox) this.getRoot();

            this.market = market;

            Text title = new Text("What do you want to sell ?");
            root.getChildren().add(title);
            root.getChildren().add(money);

            displayResources();
            root.getChildren().add(back);
        }

        /**
         * The factory method to create a SellPanel instance.
         * @param market the market associated with the SellPanel
         * @return a new SellPanel instance
         */
        public static SellPanel createSellPanel(Market market){
            if(market == null){
                throw new IllegalArgumentException("Market cannot be null");
            }
            return new SellPanel(market);
        }

        /**
         * Updates the money display in the sell panel.
         * @param money the new amount of money to display
         */
        public void updateMoney(int money){
            this.money.setText("Money : " + money + "$");
        }

        /* Getters */

        public Button getBack() {
            return back;
        }

        public Button[] getSellButtons() {
            return sellButtons;
        }

        /**
         * Updates the view of the sell panel to reflect the current state of the market and player's inventory.
         */
        public void updateView() {
            VBox resourcePanel = new VBox();

            for (ResourceEnum res : ResourceEnum.values()) {
                HBox line = new HBox();

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

                    line.getChildren().add(imageView);
                }
                catch(IOException e){

                }

                VBox infoBox = new VBox(0);
                HBox title = new HBox(1);

                // Informations display :
                Text resName = new Text(res.toString().toLowerCase());
                title.getChildren().add(resName);

                Text price = new Text(res.getResource().getPrice() +"$");
                title.getChildren().add(price);

                infoBox.getChildren().add(title);

                Text number = new Text(market.getPlayer().getInventory().countResource(res) + " already in inventory");
                infoBox.getChildren().add(number);

                line.getChildren().add(infoBox);

                Button sellRes = new Button("Sell");

                sellButtons[res.ordinal()] = sellRes;
                
                line.getChildren().add(sellRes);

                resourcePanel.getChildren().add(line);
            }

            scrollPane.setContent(null);
            scrollPane.setContent(resourcePanel);
        }

        /**
         * Displays the resources available for selling in the sell panel.
         */
        private void displayResources(){
            
            updateView();
            root.getChildren().add(scrollPane);
        }
    }
}
