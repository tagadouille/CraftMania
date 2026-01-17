package com.app.main.models;

import com.app.main.models.ressources.RessourceEnum;

public class Market {
    
    private Player player;

    public Market(Player player){
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public void buy(Item item){
        this.player.buy(item.price);

        if(item.getTypeName().equals("ressource")){
            this.player.addRessource(RessourceEnum.getRessourceEnum(item.getName()));
        }else{
            //TODO Placer l'usine et déterminer si on peut la placer
        }
    }
    public void sellRessource(Item item){
        this.player.gain(item.price);
        this.player.removeRessource(RessourceEnum.getRessourceEnum(item.getName()));
    }

}
