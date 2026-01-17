package com.app.main.models.ressources;

public enum RessourceEnum {

    WOOD(new Ressource(5)),
    QUARTZ(new Ressource(10)),
    CLAY(new Ressource(10)),
    HEAT(new Ressource(5)),
    BRICK(new Ressource(15)),
    CAR(new Ressource(15)),
    CAT(new Ressource(15)),
    TANK(new Ressource(15)),
    HAMMER(new Ressource(15)),
    DOG(new Ressource(15));

    private Ressource ressource;
    
    private RessourceEnum(Ressource ressource){
        this.ressource = ressource;
        ressource.setName(this.toString());
        ressource.setTypeName("ressource");
    }
    public Ressource getRessource() {
        return ressource.clone();
    }
    public static RessourceEnum getRessourceEnum(String ressourceName){
        for (RessourceEnum res : RessourceEnum.values()) {
            if(ressourceName.equals(res.toString())){
                return res;
            }
        }
        return null;
    }
}
