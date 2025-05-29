package main.java.com.models.Ressources;

public enum RessourceEnum {

    WOOD(new Ressource(5)),
    QUARTZ(new Ressource(10)),
    STONE(new Ressource(10));

    private Ressource ressource;
    
    private RessourceEnum(Ressource ressource){
        this.ressource = ressource;
    }
    public Ressource getRessource() {
        return ressource;
    }
}
