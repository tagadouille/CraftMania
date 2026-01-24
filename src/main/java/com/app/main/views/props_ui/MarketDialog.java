package com.app.main.views.props_ui;

import java.io.IOException;

import com.app.main.models.Market;
import com.app.main.models.ressources.RessourceEnum;
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

public class MarketDialog extends Stage{

    private Scene mainScene;

    private Button buy = new Button("Buy");
    private Button sell = new Button("Sell");
    private Text money;

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

    public Button getBuy() {
        return buy;
    }

    public Button getSell() {
        return sell;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public static class BuyPanel extends Scene{

        private GridPane root;
        private Button back = new Button("Back");

        public BuyPanel(){
            super(new GridPane(2, 1));
            root = (GridPane) this.getRoot();

            Text title = new Text("What do you want to buy ?");
            root.getChildren().add(title);
            
            root.getChildren().add(back);
        }

        public Button getBack() {
            return back;
        }
    }

    public static class SellPanel extends Scene{

        private String imagePath;

        private VBox root;

        private Button back = new Button("Back");

        private Market market;

        public SellPanel(Market market){
            super(new VBox());
            this.root = (VBox) this.getRoot();

            this.market = market;

            Text title = new Text("What do you want to sell ?");
            root.getChildren().add(title);

            displayRessources();
            root.getChildren().add(back);
        }

        public Button getBack() {
            return back;
        }

        private void displayRessources(){
            ScrollPane scrollPane = new ScrollPane();
            
            VBox ressourcePanel = new VBox();

            for (RessourceEnum res : RessourceEnum.values()) {
                HBox line = new HBox();

                // Ressource image display :
                Image img = null;

                for(ItemImageEnum type : ItemImageEnum.values()){
                    if(res.getRessource().getName().equals(type.toString())){
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

                Text price = new Text(res.getRessource().getPrice() +"$");
                title.getChildren().add(price);

                infoBox.getChildren().add(title);

                Text number = new Text(market.getPlayer().numberRessourceInInventory(res) + " already in inventory");
                infoBox.getChildren().add(number);

                line.getChildren().add(infoBox);

                Button sellRes = new Button("Sell");
                //TODO MVC
                sellRes.setOnAction((e) -> {
                    market.sellRessource(res.getRessource());
                });
                
                line.getChildren().add(sellRes);

                ressourcePanel.getChildren().add(line);
            }
            scrollPane.setContent(ressourcePanel);
            root.getChildren().add(scrollPane);
        }
    }
}
