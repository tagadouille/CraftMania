package com.app.main.views.props_ui;

import java.io.IOException;

import com.app.main.models.Market;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.util.image.ImageUtil;
import com.app.main.views.utilities.ItemImageEnum;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The MarketDialog class represents a dialog window for the market interface in the game.
 * It allows players to buy and sell resources.
 * Extends the Stage class from JavaFX.
 * @author Dai Elias
 */
public final class MarketDialog extends Stage{

    private Scene mainScene;

    private Button buy = new Button("Buy");
    private Button sell = new Button("Sell");
    private Text money;

    /**
     * The constructor initializes the MarketDialog with its properties and UI components.
     */
    public MarketDialog(){
        
        this.setAlwaysOnTop(true);
        this.setTitle("Market Screen");
        this.setResizable(false);
        this.setWidth(250);
        this.setHeight(250);
        this.requestFocus();
        this.initModality(Modality.APPLICATION_MODAL);

        HBox root = new HBox(5);
        this.mainScene = new Scene(root);

        this.setScene(mainScene);

        root.getChildren().add(buy);
        root.getChildren().add(sell);
        //root.getChildren().add(money);
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
     * The BuyPanel class represents the panel where the user can buy items
     */
    public static class BuyPanel extends Scene{

        private GridPane root;
        private Button back = new Button("Back");

        /**
         * Constructor for BuyPanel
         */
        public BuyPanel(){
            super(new GridPane(2, 1));
            root = (GridPane) this.getRoot();

            Text title = new Text("What do you want to buy ?");
            root.getChildren().add(title);
            
            root.getChildren().add(back);
        }

        /* Getters : */
        public Button getBack() {
            return back;
        }
    }

    /**
     * The SellPanel class represents the panel where the user can sell items.
     * Extends Scene class from JavaFX.
     * @author Dai Elias
     */
    public static class SellPanel extends Scene{

        private String imagePath;

        private VBox root;

        private Button back = new Button("Back");

        private Market market;

        private SellPanel(Market market){
            super(new VBox());
            this.root = (VBox) this.getRoot();

            this.market = market;

            Text title = new Text("What do you want to sell ?");
            root.getChildren().add(title);

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

        /* Getters */

        public Button getBack() {
            return back;
        }

        private void displayResources(){
            ScrollPane scrollPane = new ScrollPane();
            
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
                //TODO MVC
                sellRes.setOnAction((e) -> {
                    market.sellResource(res.getResource());
                });
                
                line.getChildren().add(sellRes);

                resourcePanel.getChildren().add(line);
            }
            scrollPane.setContent(resourcePanel);
            root.getChildren().add(scrollPane);
        }
    }
}
