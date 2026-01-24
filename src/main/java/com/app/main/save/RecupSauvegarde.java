package com.app.main.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * La classe RecupSauvegarde permet de lire les fichiers de sauvegarde
 * et d'initialiser les objets selon les données
 * 
 * <h4>Voici les principales méthodes de la classe: </h4>
 * <ul>
 * <li><strong>boolean recuperation(Joueur joueur, Carte carte)</strong> : Méthode permettant de récupérer toutes les données de l'utilisateur</li>
 * </ul>
 * 
 * @author Elias Dai
 * @version 1.6
 */
public class RecupSauvegarde {
    /*private static final String CHEMIN = Sauvegarde.getChemin();
    private static final String DECHIFFR = "src/fichier/sauvegarde/dechiffr.txt";
    private static boolean sauvCorromp; //Informe si la sauvegarde est corrompue ou non


    public static boolean isSauvCorromp() {
        return sauvCorromp;
    }

    public static void setSauvCorromp(boolean sauvCorromp) {
        RecupSauvegarde.sauvCorromp = sauvCorromp;
    }

    public static boolean recuperation(Joueur joueur, Carte carte){
        File fichier = new File(CHEMIN);

        if(fichier.exists()){
            try{
                Crypto.decrypterFichier(fichier, Sauvegarde.getCle(), DECHIFFR);
            }catch(FileNotFoundException e){
                System.out.println("Fichier inexistant");
                return false;
            }catch(IllegalArgumentException e){
                System.out.println("Sauvegarde corrompue");
                return false;
            }
            try{
                Scanner lecteur = new Scanner(new File(DECHIFFR));
                if(!recupJoueur(joueur, lecteur)){
                    lecteur.close();
                    System.out.println("Sauvegarde corrompue");
                    return false;
                }if(!recupCarte(carte, lecteur)){
                    //Effacement des données de la carte
                    for (int i = 0; i < Carte.getHauteur(); i++) {
                        for (int j = 0; j < Carte.getLargeur(); j++) {
                            carte.setCase(j, i, new Case(j*(GamePannel.getSpriteSize()), i*(GamePannel.getSpriteSize())));
                        }
                    }
                    System.out.println("Sauvegarde corrompue");
                    lecteur.close();
                    return false;
                }
                
                lecteur.close();
                System.out.println("fini");
                return true;
                
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }finally{
                new File(DECHIFFR).delete(); //Suppression du fichier déchiffrer
            }
        }else{
            System.out.println("Fichier inexistant");
            return true;
        }
        return false;
    }

    private static boolean recupJoueur(Joueur joueur, Scanner lecteur){
        if(!lecteur.hasNext()){
            return false;
        }
        String[] ligne = lecteur.nextLine().split(" ");

        //Vérification de nombres des données:
        if(ligne.length != 2 + joueur.getNameRes().length){
            return false;
        }

        if(!ligne[0].equals("Joueur")){
            return false;
        }
        int i = 2; //2 correspond à l'index de la première ressource
        try{
            joueur.setMoney(Integer.parseInt(ligne[1]));//On récupère l'argent du joueur
            //Récupération de ses ressources
            for (; i < ligne.length; i++) {
                for (int j = 0; j < Integer.parseInt(ligne[i]) ; j++) {
                    joueur.ajouterRessourceInventaire(obtenirRessource(joueur.getNameRes()[i - 2]));
                }
            }
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    private static boolean recupCarte(Carte carte, Scanner lecteur){
        if(!lecteur.hasNext()){
            return false;
        }
        //Récupération du marché:
        String[] ligne = lecteur.nextLine().split(" ");
        if(ligne.length != 3){
            return false;
        }
        if(!ligne[0].equals("Marche")){
            return false;
        }
        try{
            int x = Integer.parseInt(ligne[2]);
            int y = Integer.parseInt(ligne[1]);

            if(!coordValide(x, y)){
                return false;
            }
            carte.setPositionMarche(new Point(x, y));
            carte.getCase(x, y).setType(Case.Type.MARCHE);

            if(!recupRessourceTMP(carte, lecteur) || !recupRessource(carte, lecteur)){
                System.out.println("ff");
                return false;
            }if(!recupMachine(carte, lecteur)){
                System.out.println("machine");
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    private static boolean recupRessourceTMP(Carte carte, Scanner lecteur){
        String[] ligne;
        carte.getCaseRessourceListe().clear();
        //Récupération des cases temporaires
        for (int i = 0; i < 6; i++) {
            if(!lecteur.hasNext()){
                return false;
            }
            ligne = lecteur.nextLine().split(" ");

            if(ligne.length != 2){
                return false;
            }
            int x = Integer.parseInt(ligne[0]);
            int y = Integer.parseInt(ligne[1]);

            if(!coordValide(x, y)){
                return false;
            }
            carte.getCase(x, y).setType(Case.Type.RESSOURCETMP);
            carte.getCaseRessourceListe().add(new Integer[]{y, x});
        }
        return true;
    }

    private static boolean recupRessource(Carte carte, Scanner lecteur){
        String[] ligne;
        //Récupération des ressources
        for (int i = 0; i < 12; i++) {
            //Vérifications:
            if(!lecteur.hasNext()){
                return false;
            }
            ligne = lecteur.nextLine().split(" ");
            if(ligne.length != 3){
                return false;
            }
            int x = Integer.parseInt(ligne[1]);
            int y = Integer.parseInt(ligne[2]);
            
            if(!coordValide(x,y)){
                return false;
            }
            Ressource res = obtenirRessource(ligne[0]);
            if(res == null){
                return false;
            }
            //On vérifie si les ressources temporaires sont là ou elles devraient être:
            boolean flag = false;
            RessourceTMP res2 = null;
            for (RessourceTMP ressource : RessourceEnum.getLesRessourceTMP()) {
                if(res.getNom().equals(ressource.getNom())){
                    res2 = ressource;

                    for (Integer[] position : carte.getCaseRessourceListe()) {
                        if(x == position[1] && y == position[0]){
                            //2 ressources superposées
                            if(flag){
                                return false;
                            }
                            flag = true;
                        }
                    }if(!flag){
                        return false;
                    }break;
                }    
            }
            if(res2 == null){
               //Placement de la ressources
                res.setPosX(x*GamePannel.getSpriteSize());
                res.setPosY(y*GamePannel.getSpriteSize());

                //On teste si la case ou on va placer la ressource est libre
                if(carte.getCase(x, y).getRessource() == null ){
                    carte.getCase(x, y).setRessource(res);
                }else{
                    return false;
                }
            }else{
                //Placement de la ressources
                res2.setPosX(x*GamePannel.getSpriteSize());
                res2.setPosY(y*GamePannel.getSpriteSize());

                //On teste si la case ou on va placer la ressource est libre
                if(carte.getCase(x, y).getRessource() == null ){
                    carte.getCase(x, y).setRessource(res2);
                }else{
                    return false;
                }
            }
            
        }
        return true;
    }

    private static boolean recupMachine(Carte carte, Scanner lecteur){
        while (lecteur.hasNext()) {
            String[] ligne = lecteur.nextLine().split(" ");
            //Vérification
            if(ligne[0].equals("Usine") &&  ligne.length == 8){
                if(!recupUsine(carte, ligne)){
                    return false;
                }continue;
            }
            if(ligne[0].equals("Recolteuse") && ligne.length == 6){
                if(!recupRecolteuse(carte, ligne)){
                    return false;
                }
            }
        }return true;
    }

    private static boolean recupUsine(Carte carte, String[] ligne){
        Usine usine;
        int x = Integer.parseInt(ligne[2]);
        int y = Integer.parseInt(ligne[3]);

        if(!coordValide(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize())){
            return false;
        }
        switch (ligne[1]) {
            case "Usine":
                usine = new Usine(x, y);
                break;
            case "UsineFragile":
                usine = new UsineFragile(x, y);
                break;
            case "UsinePL":
                usine = new UsinePL(x, y);
                break;
            case "UsineXL":
                usine = new UsineXL(x, y);
                break;
            case "UsineRapide":
                usine = new UsineRapide(x, y);
                break;
            default:
                return false;
        }
        if(ligne[4].equals("null")){
            if(Integer.parseInt(ligne[5]) == 0 && Integer.parseInt(ligne[6]) == 0 && Integer.parseInt(ligne[7]) == 0){
                return carte.placerUsine(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), usine);
            }
            return false;
        }
        boolean flag = false;
        for (RecipeEnum craft : RecipeEnum.values()) {

            //Vérification du nom du craft et paramétrage
            if(craft.toString().equals(ligne[4])){
                usine.setRecette(craft.recette);
                flag = true;
                break;
            }
        }if(!flag){
            return false;
        }
        //Remplissage de l'usine:
        for (int i = 0; i < Integer.parseInt(ligne[5]); i++) {
            usine.getInventaire().ajouterRessource(obtenirRessource(usine.getRessource1Nom()));
        }
        for (int i = 0; i < Integer.parseInt(ligne[6]); i++) {
            usine.getInventaire().ajouterRessource(obtenirRessource(usine.getRessource2Nom()));
        }
        for (int i = 0; i < Integer.parseInt(ligne[7]); i++) {
            usine.getInventaire().ajouterRessource(obtenirRessource(usine.getResultatNom()));
        }
        return carte.placerUsine(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), usine);
    }

    private static boolean recupRecolteuse(Carte carte, String[] ligne){
        Recolteuse recolteuse;
        int x = Integer.parseInt(ligne[2]);
        int y = Integer.parseInt(ligne[3]);

        if(!coordValide(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize())){
            return false;
        }
        switch (ligne[1]) {
            case "RecolteuseFragile":
                recolteuse = new RecolteuseFragile(x, y);
                break;
            case "Recolteuse":
                recolteuse = new Recolteuse(x, y);
                break;
            case "RecolteusePolyvalente":
                recolteuse = new RecolteusePolyvalente(x, y);
                break;
            case "RecolteuseXL":
                recolteuse = new RecolteuseXL(x, y);
                break;
            case "RecolteuseRapide":
                recolteuse = new RecolteuseRapide(x, y);
                break;
            default:
                return false;
        }
        if(ligne[4].equals("null")){
            if(ligne[5].equals("0")){
                return carte.placerRecolteuse(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), recolteuse);
            }
        }
        if(obtenirRessource(ligne[4]) != null){
            recolteuse.setRessourceCible(obtenirRessource(ligne[4]));

            //Remplissage de l'inventaire de la récolteuse
            for (int i = 0; i < Integer.parseInt(ligne[5]); i++) {
                recolteuse.getInventaire().ajouterRessource(obtenirRessource(ligne[4]));
            }
            return carte.placerRecolteuse(x/GamePannel.getSpriteSize(), y/GamePannel.getSpriteSize(), recolteuse);
        }return false;
    }

    private static boolean coordValide(int x, int y){
        return !(x < 0 || x >= Carte.getLargeur() || y < 0 || y >= Carte.getHauteur());
    }

    private static Ressource obtenirRessource(String nom){
        return RessourceEnum.getRessource(nom);
    }*/
}
