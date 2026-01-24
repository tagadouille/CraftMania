package com.app.main.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * La classe sauvegarde permet d'écrire dans les fichiers de sauvegarde les
 * données des objets du jeu
 * 
 * <h4>Voici les principales méthodes de la classe: </h4>
 * <ul>
 * <li><strong>boolean sauvegarder(Joueur joueur, Carte carte)</strong> : Méhode qui permet de sauvegarder toutes les données du jeu</li>
 * </ul>
 * 
 * @author Elias Dai
 * @version 1.4
 */
public class Sauvegarde {
    /*private static final String CHEMIN = "src/fichier/sauvegarde/sauvegarde.txt";
    private static final int CLE = 11904; //La clé de chiffrement
    private static boolean pasChemin; //Informe si le chemin vers un fichier est introuvable
    private static boolean reussi; //Informe si la sauvegarde a réussie


    public static String getChemin() {
        return CHEMIN;
    }

    public static boolean isReussi() {
        return reussi;
    }

    public static boolean isPasChemin() {
        return pasChemin;
    }

    public static int getCle() {
        return CLE;
    }

    public static void setReussi(boolean reussi) {
        Sauvegarde.reussi = reussi;
    }

    public static void setPasChemin(boolean pasChemin) {
        Sauvegarde.pasChemin = pasChemin;
    }

    public static boolean sauvegarder(Joueur joueur, Carte carte){
        
        File fichier = new File(CHEMIN);
        if(fichier.exists()){
            fichier.delete();
        }
        try{
            fichier.createNewFile(); //Créer le fichier si il existe pas
        }catch(IOException e){
            pasChemin = true;
            return false;
        }pasChemin = false;

        //Ecriture des données du joueur dans le fichier
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));) {
            
            if(!sauvegarderJoueur(writer, joueur) || !sauvegarderCarte(writer, carte)){
                pasChemin = true;
                return false;
            }
            reussi = true;
            writer.close();
        } catch (IOException e) {
            System.out.println("L'enregistrement des données du joueur n'a pas pû être fait");
            e.printStackTrace();
            return false;
        }return true;
    }

    private static boolean sauvegarderJoueur(BufferedWriter writer, Joueur joueur){
        try{
            Crypto.ecrireCrypter("Joueur ", writer, CLE);
            Crypto.ecrireCrypter(joueur.getMoney() + " ", writer, CLE);//Sauvegarde de l'argent du joueur

            //Sauvegarde des ressources du joueur : 
            for (String resNom : joueur.getNameRes()) {
                Crypto.ecrireCrypter(joueur.getInventory().getQuantite(resNom) + " ", writer, CLE);
            }Crypto.ecrireCrypter("\n", writer, CLE);
        }catch(IOException e){
            System.out.println("Erreur lors de la sauvegarde du joueur");
            return false;
        }return true;
        
    }
    
    private static boolean sauvegarderCarte(BufferedWriter writer, Carte carte){
        //Sauvegarde de la position du marché:
        try{
            Crypto.ecrireCrypter("Marche " + (int) carte.getPositionMarche().getY() + " " + (int) carte.getPositionMarche().getX() + "\n", writer, CLE);
        }catch(IOException e ){
            return false;
        }
        //Sauvegarde du reste des données concernant la carte
        if(!sauvegarderRessource(writer, carte) || !sauvegarderMachine(writer, carte)){
            return false;
        }return true;
    }

    private static boolean sauvegarderRessource(BufferedWriter writer, Carte carte){
        try{
            //On commence par la position des ressources temporaires:
            for (Integer[] position : carte.getCaseRessourceListe()) {
                Crypto.ecrireCrypter(position[1] + " " + position[0] + "\n", writer, CLE);
            }
            //Pour le reste les ressources:
            for (int i = 0; i < Carte.getHauteur(); i++) {
                for (int j = 0; j < Carte.getLargeur(); j++) {
                    Ressource res = carte.getCase(i, j).getRessource();
                    if(res != null){
                        Crypto.ecrireCrypter(res.getNom() + " " + i + " " + j + "\n", writer, CLE);
                    }
                    //Le cas ou la ressource tempoaraire a été ramassé par le joueur:
                    if(carte.getCase(i, j).getType() == Case.Type.RESSOURCETMP && res == null){
                        Random rand = new Random();
                        String nom = "";
                        //On fait comme si une ressource a réapparu
                        if(rand.nextInt(2) == 0){
                            nom = "Chaleur";
                        }else{
                            nom = "Baton";
                        }
                        Crypto.ecrireCrypter(nom + " " + i + " " + j + "\n", writer, CLE);
                        
                    }
                }
            }
            
        }catch(IOException e){
            System.out.println("Erreur lors de la sauvegarde des ressources");
            return false;
        }return true;
    }

    private static boolean sauvegarderMachine(BufferedWriter writer, Carte carte){
        try{
            for (int i = 0; i < Carte.getHauteur(); i++) {
                for (int j = 0; j < Carte.getLargeur(); j++) {
                    if(carte.getCase(i, j).getMachine() != null){
                        Machine machine = carte.getCase(i, j).getMachine();
                        if(machine instanceof Recolteuse){
                            if(!sauvegarderRecolteuse(writer, (Recolteuse) machine)){
                                return false;
                            }
                        }if(machine instanceof Usine){
                            if(!sauvegarderUsine(writer, (Usine) machine)){
                                return false;
                            }
                        }
                    }
                }
            }
            writer.write("");
            return true;
        }catch(IOException e){
            return false;
        }
    }

    private static boolean sauvegarderRecolteuse(BufferedWriter writer, Recolteuse recolteuse){
        try{
            String ligne = "Recolteuse " + recolteuse.getClass().getSimpleName() + " " + recolteuse.getPosX() + " " + recolteuse.getPosY() + " ";

            if(recolteuse.getCible() == null){
                ligne += "null 0";
            }else{
                ligne += recolteuse.getCible().getNom() + " "
                + recolteuse.getInventaire().getQuantite(recolteuse.getCible().getNom());
            }
            Crypto.ecrireCrypter(ligne + "\n", writer, CLE);
            return true;
        }catch(IOException e){
            return false;
        }
    }

    private static boolean sauvegarderUsine(BufferedWriter writer, Usine usine){
        try{
            String ligne = "Usine " + usine.getClass().getSimpleName() + " " + usine.getPosX() + " " + usine.getPosY() + " ";
            
            if(usine.getRecette() == null){
                ligne += "null 0 0 0";
            }else{
                ligne += usine.getRecette().getResultat().getNom().toUpperCase() + " "
                + usine.getInventaire().getQuantite(usine.getRessource1Nom()) + " " + usine.getInventaire().getQuantite(usine.getRessource2Nom())
                + " " + usine.getInventaire().getQuantite(usine.getResultatNom());
            }
            Crypto.ecrireCrypter(ligne + "\n", writer, CLE);
            return true;
        }catch(IOException e){
            return false;
        }
    }*/
}
