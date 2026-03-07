package com.app.main.controllers.view_controller.props_ui;

import com.app.main.models.Market;
import com.app.main.models.Player;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.views.props_ui.MarketDialog;
import com.app.main.views.props_ui.MarketDialog.BuyPanel;
import com.app.main.views.props_ui.MarketDialog.SellPanel;

/**
 * MarketDialogController is a class for controlling the market dialog
 * 
 * @author Dai Elias
 */
public final class MarketDialogController {
    
    private MarketDialog marketDialog;
    private BuyPanel buyPanel;
    private SellPanel sellPanel;

    private Market market;

    private Player player;

    private MarketDialogController(MarketDialog marketDialog, Market market, Player player){
        this.marketDialog = marketDialog;
        this.market = market;
        this.player = player;
        buttonBehavior();
    }

    /**
     * Factory method for creating a MarketDialogController
     * @param marketDialog the market dialog
     * @param market the market
     * @return a new instance of MarketDialogController
     */
    public static MarketDialogController create(MarketDialog marketDialog, Player player) {

        if(marketDialog == null) {
            throw new IllegalArgumentException("The market dialog can't be null");
        }
        if(player == null) {
            throw new IllegalArgumentException("The player can't be null");
        }
        return new MarketDialogController(marketDialog, Market.createMarket(player), player);
    }

    /* Setting the behavior of the components : */

    private void buttonBehavior() {

        marketDialog.updateMoney(player.getMoney());

        marketDialog.getBuy().setOnAction((e) -> {
            buyPanel = BuyPanel.create(market);
            marketDialog.setScene(buyPanel);
            buyPannelBehavior();
        });

        marketDialog.getSell().setOnAction((e) -> {
            sellPanel = SellPanel.createSellPanel(market);
            marketDialog.setScene(sellPanel);
            sellPanelBehavior();
        });
    }

    private void buyPannelBehavior(){

        buyPanel.updateMoney(player.getMoney());

        buyPanel.getBack().setOnAction((e) -> {
            marketDialog.setScene(marketDialog.getMainScene());
            marketDialog.updateMoney(player.getMoney());
        });
    }

    private void sellPanelBehavior(){

        sellPanel.updateMoney(player.getMoney());

        sellPanel.getBack().setOnAction((e) -> {
            marketDialog.setScene(marketDialog.getMainScene());
            marketDialog.updateMoney(player.getMoney());
        });

        for (int i = 0; i < sellPanel.getSellButtons().length; i++) {
            int index = i;
            sellPanel.getSellButtons()[i].setOnAction((e) -> {
                market.sellResource(ResourceEnum.values()[index]);
                sellPanel.updateMoney(player.getMoney());
                sellPanel.updateView();
                sellPanelBehavior();
            });
        }
    }
}
