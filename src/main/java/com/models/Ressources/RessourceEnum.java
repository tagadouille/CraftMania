package main.java.com.models.Ressources;

public enum RessourceEnum {

    WOOD(new Ressource(5)),
    QUARTZ(new Ressource(10)),
    CLAY(new Ressource(10)),
    HEAT(new Ressource(5));

    private Ressource ressource;
    
    private RessourceEnum(Ressource ressource){
        this.ressource = ressource;
        ressource.setTypeName(this.toString());
    }
    public Ressource getRessource() {
        return ressource;
    }
}
