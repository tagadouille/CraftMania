package com.app.main.models.save;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.app.main.models.Player;
import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Harvester;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile.TileType;
import com.app.main.models.resources.Resource;


public class LoadSave {

    private static final String PATH = Saving.getPath();
    private static final String DECRYPTIONFILEPATH = "src/fichier/sauvegarde/dechiffr.txt";
    private static boolean isSaveCorrupted;


    public static boolean isSaveCorrupted() {
        return isSaveCorrupted;
    }

    public static void setSaveCorrupted(boolean saveCorrupted) {
        LoadSave.isSaveCorrupted = saveCorrupted;
    }

    public static boolean recuperation(Player player, GameMap map){

        File file = new File(PATH);

        if(file.exists()){

            try{
                CaesarEncrypt.decryptFile(file, Saving.getKey(), DECRYPTIONFILEPATH);
            }
            //TODO err
            catch(FileNotFoundException e){
                System.out.println("Unexistant file");
                return false;
            }
            catch(IllegalArgumentException e){
                System.out.println("Corrupted save");
                return false;
            }

            try{

                Scanner reader = new Scanner(new File(DECRYPTIONFILEPATH));
                
                if(!recupPlayer(player, reader)){
                    reader.close();
                    System.out.println("Corrupted save");
                    return false;
                }

                if(!recupMap(map, reader)){
                    
                    // Delete of map data :
                    for (int i = 0; i < map.getHeight(); i++) {
                        for (int j = 0; j < map.getWidth(); j++) {
                            map.setCase(j, i, new Case(j*(GamePannel.getSpriteSize()), i*(GamePannel.getSpriteSize())));
                        }
                    }
                    System.out.println("Corrupted save");
                    reader.close();
                    return false;
                }
                
                reader.close();
                System.out.println("finish");
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
            System.out.println("Unexitant file");
            return true;
        }
        return false;
    }

    private static boolean recupPlayer(Player player, Scanner reader){

        if(!reader.hasNext()){
            return false;
        }
        String[] line = reader.nextLine().split(" ");

        // Verification of the number of data and the first word of the line
        if(line.length != 2 + player.getNameRes().length){
            return false;
        }

        if(!line[0].equals("player")){
            return false;
        }

        int i = 2; //2 is the index of the first resource quantity in the line

        try{
            player.setMoney(Integer.parseInt(line[1]));// Money recovery
            
            // Resources recovery
            for (; i < line.length; i++) {
                for (int j = 0; j < Integer.parseInt(line[i]) ; j++) {
                    player.addResource(null);
                    //! player.ajouterRessourceInventaire(obtenirRessource(player.getNameRes()[i - 2]));
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
        String[] ligne = reader.nextLine().split(" ");

        if(ligne.length != 3){
            return false;
        }

        if(!ligne[0].equals("Market")) {
            return false;
        }
        try{
            int x = Integer.parseInt(ligne[2]);
            int y = Integer.parseInt(ligne[1]);

            try{
                map.setMarketPos(new Point(x, y));
            }
            catch(IllegalArgumentException e){
                return false;
            }

            if(!recupResourceTMP(map, reader) || !recupResource(map, reader)){
                System.out.println("ff");
                return false;
            }
            if(!recupMachine(map, reader)){
                System.out.println("machine");
                return false;
            }
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    private static boolean recupResourceTMP(GameMap map, Scanner reader){

        String[] line;
        map.getCaseRessourceListe().clear();

        // Recovery of the position of the temporary resources:
        for (int i = 0; i < 6; i++) {
            
            if(!reader.hasNext()){
                return false;
            }
            line = reader.nextLine().split(" ");

            if(line.length != 2){
                return false;
            }
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);

            if(!coordValide(x, y)){
                return false;
            }
            map.getMap()[x][y].setType(TileType.RESOURCETMP);
            map.getCaseRessourceListe().add(new Integer[]{y, x});
        }
        return true;
    }

    private static boolean recupResource(GameMap map, Scanner reader){

        String[] line;
        // Resources recovery:
        for (int i = 0; i < 12; i++) {
            
            //Verifications:
            if(!reader.hasNext()){
                return false;
            }

            line = reader.nextLine().split(" ");
            
            if(line.length != 3){
                return false;
            }

            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);
            
            if(!coordValide(x,y)){
                return false;
            }

            Resource res = obtenirRessource(line[0]);

            if(res == null){
                return false;
            }
            //On vérifie si les ressources temporaires sont là ou elles devraient être:
            boolean flag = false;
            RessourceTMP res2 = null;

            for (RessourceTMP ressource : RessourceEnum.getLesRessourceTMP()) {

                if(res.getNom().equals(ressource.getNom())){
                    res2 = ressource;

                    for (Integer[] position : map.getCaseRessourceListe()) {
                        
                        if(x == position[1] && y == position[0]){
                            
                            //2 ressources superposées
                            if(flag){
                                return false;
                            }
                            flag = true;
                        }
                    }
                    if(!flag){
                        return false;
                    }
                    break;
                }    
            }
            if(res2 == null){

               // Resource positioning:
                res.setPosX(x*GamePannel.getSpriteSize());
                res.setPosY(y*GamePannel.getSpriteSize());

                // Test if there is already a resource on the case :
                if(map.getMap()[x][y].getRessource() == null ){
                    map.getMap()[x][y].setRessource(res);
                }
                else{
                    return false;
                }
            }
            else{
                // Resource positioning:
                res2.setPosX(x*GamePannel.getSpriteSize());
                res2.setPosY(y*GamePannel.getSpriteSize());

                // Test if there is already a resource on the case :
                if(map.getMap()[x][y].getRessource() == null ){
                    map.getMap()[x][y].setRessource(res2);
                }
                else{
                    return false;
                }
            }
            
        }
        return true;
    }

    private static boolean recupMachine(GameMap map, Scanner reader){

        while (reader.hasNext()) {

            String[] ligne = reader.nextLine().split(" ");

            //Verification
            if(ligne[0].equals("Factory") &&  ligne.length == 8){
                
                if(!recupFactory(map, ligne)){
                    return false;
                }
                continue;
            }

            if(ligne[0].equals("Harvester") && ligne.length == 6){

                if(!recupharvester(map, ligne)){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean recupFactory(GameMap map, String[] line){

        Factory factory;
        int x = Integer.parseInt(line[2]);
        int y = Integer.parseInt(line[3]);

        if(!coordValide(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize())){
            return false;
        }

        // TODO method for add a factory in the map with the type of the factory as parameter
        switch (line[1]) {
            case "factory":
                factory = Factory.createSimpleFactory();
                break;
            case "factoryWeak":
                factory = Factory.createWeakFactory();
                break;
            case "factoryPL":
                factory = Factory.createPolyFactory();
                break;
            case "factoryXL":
                factory = Factory.createXLFactory();
                break;
            case "factoryFast":
                factory = Factory.createFastFactory();
                break;
            default:
                return false;
        }
        
        if(line[4].equals("null")){
            if(Integer.parseInt(line[5]) == 0 && Integer.parseInt(line[6]) == 0 && Integer.parseInt(line[7]) == 0){
                return map.placerfactory(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), factory);
            }
            return false;
        }
        boolean flag = false;
        for (Craft craft : Craft.values()) {

            //Vérification du nom du craft et paramétrage
            if(craft.toString().equals(line[4])){
                factory.setRecette(craft.recette);
                flag = true;
                break;
            }
        }
        if(!flag){
            return false;
        }
        //Remplissage de l'factory:
        for (int i = 0; i < Integer.parseInt(line[5]); i++) {
            factory.getInventaire().ajouterRessource(obtenirRessource(factory.getRessource1Nom()));
        }
        for (int i = 0; i < Integer.parseInt(line[6]); i++) {
            factory.getInventaire().ajouterRessource(obtenirRessource(factory.getRessource2Nom()));
        }
        for (int i = 0; i < Integer.parseInt(line[7]); i++) {
            factory.getInventaire().ajouterRessource(obtenirRessource(factory.getResultatNom()));
        }
        return map.placerfactory(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), factory);
    }

    private static boolean recupharvester(GameMap map, String[] ligne){

        Harvester harvester;
        int x = Integer.parseInt(ligne[2]);
        int y = Integer.parseInt(ligne[3]);

        if(!map.inBound(x, y)) {
            return false;
        }

        switch (ligne[1]) {
            case "harvesterWeak":
                harvester = Harvester.createWeakHarvester();
                break;
            case "harvester":
                harvester = Harvester.createSimpleHarvester();
                break;
            case "harvesterPoly":
                harvester = Harvester.createPolyHarvester();
                break;
            case "harvesterXL":
                harvester = Harvester.createXLHarvester();
                break;
            case "harvesterFast":
                harvester = Harvester.createFastHarvester();
                break;
            default:
                return false;
        }

        //TODO PLACER
        if(ligne[4].equals("null")){
            if(ligne[5].equals("0")){
                return map.placerharvester(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), harvester);
            }
        }

        if(obtenirRessource(ligne[4]) != null){
            harvester.setRessourceCible(obtenirRessource(ligne[4]));

            //Remplissage de l'inventaire de la récolteuse
            for (int i = 0; i < Integer.parseInt(ligne[5]); i++) {
                harvester.getInventaire().ajouterRessource(obtenirRessource(ligne[4]));
            }
            return map.placerharvester(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), harvester);
        }
        return false;
    }
}
