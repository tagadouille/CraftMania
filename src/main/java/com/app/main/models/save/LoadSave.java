package com.app.main.models.save;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.main.models.Player;
import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Harvester;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile.TileType;
import com.app.main.models.resources.RecipeEnum;
import com.app.main.models.resources.ResourceEnum;

/**
 * The LoadSave class is responsible for loading the saved state of 
 * the game from an encrypted file.
 * It reads the player's information and the game map data, 
 * including the positions of resources and machines
 * 
 * @see Saving
 * @see CaesarEncrypt
 * 
 * @author Dai Elias
 */
public final class LoadSave {

    private static final String PATH = Saving.getPath();
    private static final String DECRYPTIONFILEPATH = "files/saves/decrypt.txt";
    private static boolean isSaveCorrupted;

    // Buffer for lines read ahead (used when a line isn't a resource)
    private static List<String> bufferedLines = new ArrayList<>();

    public static boolean isSaveCorrupted() {
        return isSaveCorrupted;
    }

    /**
     * Loads the saved game state from a file, 
     * including the player's information and the game map.
     * It first checks if a save file exists, then decrypts it and reads 
     * the data to restore the player's state and the map's state.
     * @param player the player whose state is to be loaded
     * @param map the game map whose state is to be loaded
     * @return true if the loading process is successful, 
     * false if there are errors (e.g., corrupted save)
     */
    public static boolean load(Player player, GameMap map){

        File file = new File(PATH);
        bufferedLines.clear();

        if(file.exists()){

            try{
                CaesarEncrypt.decryptFile(file, Saving.KEY, DECRYPTIONFILEPATH);
            }
            catch(FileNotFoundException e){
                System.err.println("Unexistant file");
                return false;
            }
            catch(IllegalArgumentException e){
                System.err.println("Corrupted save");
                return false;
            }

            try{
                Scanner reader = new Scanner(new File(DECRYPTIONFILEPATH));
                
                if(!recupPlayer(player, reader)){
                    reader.close();
                    System.err.println("Corrupted save - player data");
                    isSaveCorrupted = true;
                    return false;
                }

                if(!recupMap(map, reader)){
                    System.err.println("Corrupted save - map data");
                    reader.close();
                    isSaveCorrupted = true;
                    return false;
                }
                
                reader.close();
                System.out.println("Load finished successfully");
                isSaveCorrupted = false;
                return true;
                
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            finally{
                new File(DECRYPTIONFILEPATH).delete(); // Delete the decrypted file
            }
        }
        else{
            System.err.println("Unexistant file");
            return true; // No save file exists, this is not an error
        }
        return false;
    }

    private static boolean recupPlayer(Player player, Scanner reader){

        if(!reader.hasNext()){
            return false;
        }
        String[] line = reader.nextLine().split(" ");

        // Verification of the number of data and the first word of the line
        // Format: player <money> <res1count> <res2count> ... <resNcount>
        if(line.length != 2 + ResourceEnum.values().length){
            return false;
        }

        if(!line[0].equals("player")){
            return false;
        }

        try{
            player.setMoney(Integer.parseInt(line[1])); // Money recovery
            
            // Resources recovery
            for (int i = 2; i < line.length; i++) {
                int count = Integer.parseInt(line[i]);
                for (int j = 0; j < count; j++) {
                    player.addResource(ResourceEnum.values()[i - 2]);
                }
            }
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    private static boolean recupMap(GameMap map, Scanner reader){

        if(!reader.hasNext()){
            return false;
        }

        // Market position recovery:
        // Format: Marche <y> <x>
        String[] line = reader.nextLine().split(" ");

        if(line.length != 3){
            return false;
        }

        if(!line[0].equals("Market")) {
            return false;
        }

        try{
            int x = Integer.parseInt(line[2]);
            int y = Integer.parseInt(line[1]);

            try{
                map.setMarketPos(new Point(x, y));
            }
            catch(IllegalArgumentException e){
                return false;
            }

            if(!recupResourcesAndMachines(map, reader)){
                return false;
            }
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Read all remaining lines and process resources and machines.
     * Resources have format: <resourceName> <row> <col>
     * Machines have format: harvester/factory <type> <x> <y> ...
     */
    private static boolean recupResourcesAndMachines(GameMap map, Scanner reader){

        while(reader.hasNextLine()){
            String line = reader.nextLine().trim();
            
            if(line.isEmpty()){
                continue;
            }

            String[] parts = line.split(" ");

            if(parts.length == 0){
                continue;
            }

            // Check if this is a machine line
            if(parts[0].equals("harvester") && parts.length == 6){
                if(!recupHarvester(map, parts)){
                    return false;
                }
            }
            else if(parts[0].equals("factory") && parts.length == 8){
                if(!recupFactory(map, parts)){
                    return false;
                }
            }
            // Otherwise, it should be a resource line
            else if(parts.length == 3){
                if(!recupSingleResource(map, parts)){
                    return false;
                }
            }
            else {
                // Unknown format, skip or fail
                System.err.println("Unknown line format: " + line);
            }
        }
        return true;
    }

    /**
     * Process a single resource line.
     * Format: <resourceName> <row> <col>
     */
    private static boolean recupSingleResource(GameMap map, String[] parts){

        try{
            String resourceName = parts[0].toUpperCase();
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            if(!map.inBound(row, col)){
                System.err.println("Resource position out of bounds: " + row + ", " + col);
                return false;
            }

            ResourceEnum res = ResourceEnum.getResourceEnum(resourceName);

            if(res == null){
                System.err.println("Unknown resource: " + resourceName);
                return false;
            }

            // Place the resource on the map
            TileType tileType = res.isTmp() ? TileType.RESOURCETMP : TileType.RESOURCE;
            map.getMap()[row][col].setType(tileType);
            map.getMap()[row][col].setItem(res.getResource());

            return true;
        }
        catch(NumberFormatException e){
            System.err.println("Invalid resource coordinates");
            return false;
        }
    }

    /**
     * Process a factory line.
     * Format: factory <SimpleClassName> <j> <i> <recipe|null> <ing1Count> <ing2Count> <resultCount>
     * Note: Saving writes (j, i) but map uses [i][j], so we swap coordinates
     */
    private static boolean recupFactory(GameMap map, String[] line){

        try{
            Factory factory;
            int col = Integer.parseInt(line[2]);
            int row = Integer.parseInt(line[3]);

            if(!map.inBound(row, col)){
                return false;
            }

            // Match the class simple names from Saving (getClass().getSimpleName())
            switch (line[1]) {
                case "SimpleFactory":
                    factory = Factory.createSimpleFactory();
                    break;
                case "WeakFactory":
                    factory = Factory.createWeakFactory();
                    break;
                case "PolyFactory":
                    factory = Factory.createPolyFactory();
                    break;
                case "XLFactory":
                    factory = Factory.createXLFactory();
                    break;
                case "FastFactory":
                    factory = Factory.createFastFactory();
                    break;
                default:
                    System.err.println("Unknown factory type: " + line[1]);
                    return false;
            }
            
            if(line[4].equals("null")){
                // No recipe set - verify counts are all 0
                if(Integer.parseInt(line[5]) == 0 && 
                   Integer.parseInt(line[6]) == 0 && 
                   Integer.parseInt(line[7]) == 0){
                    map.addMachine(row, col, factory);
                    return true;
                }
                return false;
            }

            // Find and set the recipe
            boolean recipeFound = false;
            for (RecipeEnum recipe : RecipeEnum.values()) {
                if(recipe.getRecipe().getResult().toString().equals(line[4])){
                    factory.setRecipe(recipe);
                    recipeFound = true;
                    break;
                }
            }
            
            if(!recipeFound){
                System.err.println("Unknown recipe: " + line[4]);
                return false;
            }

            // Factory inventory filling
            int ing1Count = Integer.parseInt(line[5]);
            int ing2Count = Integer.parseInt(line[6]);
            int resultCount = Integer.parseInt(line[7]);

            for (int i = 0; i < ing1Count; i++) {
                factory.getInventory().addResource(factory.getRecipe().getIngredient1());
            }
            for (int i = 0; i < ing2Count; i++) {
                factory.getInventory().addResource(factory.getRecipe().getIngredient2());
            }
            for (int i = 0; i < resultCount; i++) {
                factory.getInventory().addResource(factory.getRecipe().getResult());
            }

            map.addMachine(row, col, factory);
            return true;
        }
        catch(NumberFormatException e){
            System.err.println("Invalid factory data format");
            return false;
        }
    }

    /**
     * Process a harvester line.
     * Format: harvester <SimpleClassName> <j> <i> <product|null> <productCount>
     * Note: Saving writes (j, i) but map uses [i][j], so we swap coordinates
     */
    private static boolean recupHarvester(GameMap map, String[] line){

        try{
            Harvester harvester;
            // Saving passes (j, i) where j=col, i=row
            int col = Integer.parseInt(line[2]); // column (j from saving)
            int row = Integer.parseInt(line[3]); // row (i from saving)

            if(!map.inBound(row, col)) {
                return false;
            }

            // Match the class simple names from Saving (getClass().getSimpleName())
            switch (line[1]) {
                case "SimpleHarvester":
                    harvester = Harvester.createSimpleHarvester();
                    break;
                case "WeakHarvester":
                    harvester = Harvester.createWeakHarvester();
                    break;
                case "PolyHarvester":
                    harvester = Harvester.createPolyHarvester();
                    break;
                case "XLHarvester":
                    harvester = Harvester.createXLHarvester();
                    break;
                case "FastHarvester":
                    harvester = Harvester.createFastHarvester();
                    break;
                default:
                    System.err.println("Unknown harvester type: " + line[1]);
                    return false;
            }

            if(line[4].equals("null")){
                // No product set
                if(line[5].equals("0")){
                    map.addMachine(row, col, harvester);
                    return true;
                }
                return false;
            }

            // Find and set the product
            ResourceEnum resource = ResourceEnum.getResourceEnum(line[4].toUpperCase());
            
            if(resource != null){
                harvester.setProduct(resource);

                // Fill harvester inventory
                int count = Integer.parseInt(line[5]);
                for (int i = 0; i < count; i++) {
                    harvester.getInventory().addResource(resource);
                }
                
                map.addMachine(row, col, harvester);
                return true;
            }
            
            System.err.println("Unknown resource for harvester: " + line[4]);
            return false;
        }
        catch(NumberFormatException e){
            System.err.println("Invalid harvester data format");
            return false;
        }
    }
}
