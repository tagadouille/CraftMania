package com.app.main.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Crypto {
    

    public static String crypter(String s, int cle){
        char[] tab = s.toCharArray();
        String ret = "";

        for (char c : tab) {
            if(c + cle < 0 || c + cle > 1000000){
                throw new IllegalArgumentException("Le caractère encoder n'est pas dans la norme UNICODE");
            }
            ret += Character.toString(c + cle);
        }
        return ret;
    }

    public static String decrypter(String s, int cle){
        return crypter(s, -cle);
    }

    public static void decrypterFichier(File fichier, int cle, String chemin) throws FileNotFoundException{
        if(fichier.exists()){
            try{
                Scanner lecteur = new Scanner(fichier);
                File fic = new File(chemin);//Fichier ou sera déchiffrer fichier

                if(fic.exists()){
                    fic.delete();
                }
                try{
                    fic.createNewFile(); //Créer le fichier si il existe pas
                }catch(IOException e){
                    e.getStackTrace();
                }
                //Ecriture dans le nouveau fichier du déchiffrement de fic
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(fic));) {
                    
                    while(lecteur.hasNext()){
                        writer.write(decrypter(lecteur.nextLine(), cle));
                    }writer.close();
                } catch (IOException e) {
                    System.out.println("Le déchiffrement n'a pas pû être fait");
                    e.printStackTrace();
                }finally{
                    lecteur.close();
                } 
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }else{
            throw new FileNotFoundException("Le fichier passé en paramètre n'existe pas");
        }
    }

    public static void ecrireCrypter(String s, BufferedWriter writer, int cle) throws IOException{
        writer.write(crypter(s, cle));
    }

    public static void crypterFichier(int cle, File fichier, String chemin) throws FileNotFoundException{
        decrypterFichier(fichier, -cle, chemin);
    }
}
