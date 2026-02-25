package com.app.main.models.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.app.main.models.Player;
import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Harvester;
import com.app.main.models.machine.Machine;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile.TileType;
import com.app.main.models.resources.Resource;
import com.app.main.models.resources.ResourceEnum;


public class Saving {

    private static final String PATH = "src/fichier/sauvegarde/sauvegarde.txt";
    private static final int KEY = 11904; //La clé de chiffrement
    private static boolean noPath; //Informe si le chemin vers un fichier est introuvable
    private static boolean success; //Informe si la sauvegarde a réussie


    public static String getPath() {
        return PATH;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static boolean isNoPath() {
        return noPath;
    }

    public static int getKey() {
        return KEY;
    }

    public static void setSuccess(boolean success) {
        Saving.success = success;
    }

    public static void setNoPath(boolean noPath) {
        Saving.noPath = noPath;
    }

    /**
     * This method saves the current state of the player and the game map to a file. 
     * It first checks if a file already exists at the specified path and deletes it 
     * to avoid appending to an old save. Then, it creates a new file and writes 
     * the encrypted data of the player and the map to it.
     * @param player the player whose state is to be saved
     * @param map the game map whose state is to be saved
     * @return true if the save was successful, false otherwise
     */
    public static boolean save(Player player, GameMap map){
        
        File file = new File(PATH);

        // Deleting the file if it already exists to avoid appending to an old save
        if(file.exists()){
            file.delete();
        }

        //Create the file if it doesn't exist
        try{
            file.createNewFile();
        }
        catch(IOException e){
            noPath = true;
            return false;
        }
        noPath = false;

        // Writing of the file with the encrypted data of the player and the map
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
            
            if(!savePlayer(writer, player) || !saveMap(writer, map)){
                noPath = true;
                return false;
            }
            success = true;
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Saving of the player or the map failed");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean savePlayer(BufferedWriter writer, Player player){

        try{
            CaesarEncrypt.writeEncrypt("player ", writer, KEY);

            // Save money:
            CaesarEncrypt.writeEncrypt(player.getMoney() + " ", writer, KEY);

            // Save of the player's inventory:
            for (ResourceEnum res : ResourceEnum.values()) {
                CaesarEncrypt.writeEncrypt(player.getInventory().countResource(res) + " ", writer, KEY);
            }
            CaesarEncrypt.writeEncrypt("\n", writer, KEY);
        }
        catch(IOException e){
            System.out.println("Error while saving the player");
            return false;
        }
        return true;
        
    }
    
    private static boolean saveMap(BufferedWriter writer, GameMap map){

        //Save of the position of the market on the map:
        try{
            CaesarEncrypt.writeEncrypt(
                "Marche " + (int) map.getMarketPos().getY() 
                + " " + (int) map.getMarketPos().getX() + "\n",
                writer, KEY
            );
        }
        catch(IOException e ){
            return false;
        }
        
        // Save of the resources and machines on the map:
        if(!saveResources(writer, map) || !saveMachine(writer, map)){
            return false;
        }return true;
    }

    private static boolean saveResources(BufferedWriter writer, GameMap map){

        try{
            // Start with position of the temporary resources :
            for (Integer[] position : map.getCaseRessourceListe()) {
                CaesarEncrypt.writeEncrypt(position[1] + " " + position[0] + "\n", writer, KEY);
            }

            //For the others resources, we save their name and position :
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {

                    Resource res = map.getMap()[i][j].getItem() instanceof Resource ? (Resource) map.getMap()[i][j].getItem() : null;

                    if(res != null){
                        CaesarEncrypt.writeEncrypt(res.getName() + " " + i + " " + j + "\n", writer, KEY);
                    }

                    // The case where there is a temporary resource that has disappeared :
                    if(map.getMap()[i][j].getType() == TileType.RESOURCETMP && res == null){
                        
                        Random rand = new Random();
                        String nom = "";
                        
                        // Like a resource respawned
                        if(rand.nextInt(2) == 0){
                            nom = ResourceEnum.HEAT.toString();
                        }
                        else{
                            nom = ResourceEnum.WOOD.toString();
                        }
                        CaesarEncrypt.writeEncrypt(nom + " " + i + " " + j + "\n", writer, KEY);
                        
                    }
                }
            }
            
        }
        catch(IOException e){
            System.out.println("Error while saving resources");
            return false;
        }
        return true;
    }

    private static boolean saveMachine(BufferedWriter writer, GameMap map){

        try{
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {

                    if(map.getMap()[i][j].getMachine() != null){

                        Machine machine = map.getMap()[i][j].getMachine();
                        if(machine instanceof Harvester){
                            
                            if(!saveHarvester(writer, (Harvester) machine, j, i)){
                                return false;
                            }
                        }
                        if(machine instanceof Factory){
                            
                            if(!saveFactory(writer, (Factory) machine, j, i)){
                                return false;
                            }
                        }
                    }
                }
            }
            writer.write("");
            return true;
        }
        catch(IOException e){
            return false;
        }
    }

    private static boolean saveHarvester(BufferedWriter writer, Harvester harvester, int x, int y){

        try{
            String ligne = "harvester " + harvester.getClass().getSimpleName() + " " + x + " " + y + " ";

            if(harvester.getProduct() == null){
                ligne += "null 0";
            }
            else{
                ligne += harvester.getProduct().toString() + " "
                + harvester.getInventory().countResource(harvester.getProduct());
            }
            CaesarEncrypt.writeEncrypt(ligne + "\n", writer, KEY);
            return true;
        }
        catch(IOException e){
            return false;
        }
    }

    private static boolean saveFactory(BufferedWriter writer, Factory factory, int x, int y){
        
        try{
            String ligne = "factory " + factory.getClass().getSimpleName() + " " + x + " " + y + " ";
            
            if(factory.getRecipe() == null){
                ligne += "null 0 0 0";
            }
            else{
                ligne += factory.getRecipe().getResult().toString() + " "
                + factory.getInventory().getQuantite(factory.getRessource1Nom()) + " " + factory.getInventory().getQuantite(factory.getRessource2Nom())
                + " " + factory.getInventory().getQuantite(factory.getResultatNom());
            }
            CaesarEncrypt.writeEncrypt(ligne + "\n", writer, KEY);
            return true;
        }
        catch(IOException e){
            return false;
        }
    }
}
