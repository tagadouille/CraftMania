package com.app.main.controllers.view_controller;

import com.app.main.models.Market;
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

    private MarketDialogController(MarketDialog marketDialog, Market market){
        this.marketDialog = marketDialog;
        this.market = market;
        buttonBehavior();
    }

    /**
     * Factory method for creating a MarketDialogController
     * @param marketDialog the market dialog
     * @param market the market
     * @return a new instance of MarketDialogController
     */
    public static MarketDialogController createMarketDialogController(MarketDialog marketDialog, Market market) {

        if(marketDialog == null) {
            throw new IllegalArgumentException("The market dialog can't be null");
        }
        if(market == null) {
            throw new IllegalArgumentException("The market can't be null");
        }
        return new MarketDialogController(marketDialog, market);
    }

    /* Setting the behavior of the components : */

    private void buttonBehavior() {

        marketDialog.getBuy().setOnAction((e) -> {
            buyPanel = new BuyPanel();
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

        buyPanel.getBack().setOnAction((e) -> {
            marketDialog.setScene(marketDialog.getMainScene());
        });
    }

    private void sellPanelBehavior(){

        sellPanel.getBack().setOnAction((e) -> {
            marketDialog.setScene(marketDialog.getMainScene());
        });
    }
}
