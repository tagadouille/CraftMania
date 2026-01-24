package com.app.main.models.resources;

/**
 * Enum representing different types of resources in the application.
 * 
 * @author Dai Elias
 */
public enum ResourceEnum {

    WOOD(new Resource(5)),
    QUARTZ(new Resource(10)),
    CLAY(new Resource(10)),
    HEAT(new Resource(5)),
    BRICK(new Resource(15)),
    CAR(new Resource(15)),
    CAT(new Resource(15)),
    TANK(new Resource(15)),
    HAMMER(new Resource(15)),
    DOG(new Resource(15));

    private Resource ressource;
    
    private ResourceEnum(Resource ressource){
        this.ressource = ressource;
        ressource.setName(this.toString());
        ressource.setTypeName("ressource");
    }

    /**
     * Gets a clone of the resource associated with this enum constant.
     * @return A clone of the resource.
     */
    public Resource getResource() {
        return ressource.clone();
    }

    /**
     * Retrieves the RessourceEnum constant corresponding to the given resource name.
     * 
     * @param ressourceName The name of the resource.
     * @return The matching RessourceEnum constant, or null if not found.
     */
    public static ResourceEnum getResourceEnum(String ressourceName){
        for (ResourceEnum res : ResourceEnum.values()) {
            if(ressourceName.equals(res.toString())){
                return res;
            }
        }
        return null;
    }
}
