package com.app.main.controllers;

import com.app.main.models.Market;
import com.app.main.views.MarketDialog;
import com.app.main.views.MarketDialog.BuyPanel;
import com.app.main.views.MarketDialog.SellPanel;

public final class MarketDialogController {
    
    private MarketDialog marketDialog;
    private BuyPanel buyPanel;
    private SellPanel sellPanel;

    private Market market;

    public MarketDialogController(MarketDialog marketDialog, Market market){
        this.marketDialog = marketDialog;
        this.market = market;
        buttonBehavior();
    }

    private void buttonBehavior() {

        marketDialog.getBuy().setOnAction((e) -> {
            buyPanel = new BuyPanel();
            marketDialog.setScene(buyPanel);
            buyPannelBehavior();
        });

        marketDialog.getSell().setOnAction((e) -> {
            sellPanel = new SellPanel(market);
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
