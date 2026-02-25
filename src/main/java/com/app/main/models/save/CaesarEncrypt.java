package com.app.main.models.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The CaesarEncrypt class provides methods for encrypting and decrypting strings using the Caesar cipher technique.
 * It also includes methods for encrypting and decrypting files.
 */
public class CaesarEncrypt {
    
    /**
     * This method takes a string and a key, and returns the encrypted string using the Caesar cipher technique.
     * Each character in the string is shifted by the value of the key. 
     * If the resulting character goes beyond the valid character range, an IllegalArgumentException is thrown.
     * @param s the string to be encrypted
     * @param key the key used for encryption, which determines how many positions each character in the string will be shifted
     * @return the encrypted string
     * @throws IllegalArgumentException if the key is too big or too small for the string
     */
    public static String encrypt(String s, int key){
        char[] tab = s.toCharArray();
        String ret = "";

        for (char c : tab) {
            if(c + key < 0 || c + key > 1000000){
                throw new IllegalArgumentException("The key is too big or too small for the string");
            }
            ret += Character.toString(c + key);
        }
        return ret;
    }

    /**
     * This method takes an encrypted string and a key, and returns the decrypted string by applying the Caesar cipher decryption technique.
     * It calls the encrypt method with the negative of the key to reverse the encryption process.
     * @param s the encrypted string to be decrypted
     * @param key the key used for decryption, which should be the same as the key used for encryption but with the opposite sign
     * @return the decrypted string
     */
    public static String decrypt(String s, int key){
        return encrypt(s, -key);
    }

    /**
     * This method takes a file, a key, and a path, and decrypts the contents of the file using the Caesar cipher decryption technique.
     * The decrypted content is then written to a new file at the specified path.
     * @param file the file to be decrypted
     * @param key the key used for decryption, which should be the same as the key used for encryption but with the opposite sign
     * @param path the path where the decrypted file will be saved
     * @throws FileNotFoundException
     */
    public static void decryptFile(File file, int key, String path) throws FileNotFoundException{

        if(file.exists()){
            try{
                Scanner reader = new Scanner(file);
                File fic = new File(path);

                if(fic.exists()){
                    fic.delete();
                }

                try{
                    fic.createNewFile(); // Create the file if it doesn't exist
                }catch(IOException e){
                    e.getStackTrace();
                }
                // Write in the new file the dicypherment of fic
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(fic));) {
                    
                    while(reader.hasNext()){
                        writer.write(decrypt(reader.nextLine(), key));
                    }
                    writer.close();
                }
                catch (IOException e) {
                    System.out.println("The decipherment of the file failed");
                    e.printStackTrace();
                }
                finally{
                    reader.close();
                } 
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else{
            throw new FileNotFoundException("The file to decipher doesn't exist");
        }
    }

    /**
     * The writeEncrypt method takes a string, a BufferedWriter, and a key, and writes 
     * the encrypted version of the string to the BufferedWriter using the Caesar cipher technique.
     * @param s the string to be encrypted and written to the BufferedWriter
     * @param writer the BufferedWriter to which the encrypted string will be written
     * @param key the key used for encryption, which determines how many positions each character in the string will be shifted
     * @throws IOException
     */
    public static void writeEncrypt(String s, BufferedWriter writer, int key) throws IOException{
        writer.write(encrypt(s, key));
    }

    /**
     * The encryptFile method takes a key, a file, and a path, and encrypts the contents of the file using the Caesar cipher technique.
     * @param key the key used for encryption, which determines how many positions each character in the file will be shifted
     * @param file the file to be encrypted
     * @param path the path where the encrypted file will be saved
     * @throws FileNotFoundException
     */
    public static void encryptFile(int key, File file, String path) throws FileNotFoundException{
        decryptFile(file, -key, path);
    }
}
