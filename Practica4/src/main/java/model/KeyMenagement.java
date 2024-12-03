package model;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class KeyMenagement {

    private String keyStoragePath;
    private String password;
    private int lenght = 512;
    private KeyPair keyPair;
    private KeySerializable keySerializable = new KeySerializable();



    public void keyGenerationAndStorage(String keyStoragePath, String password)   {
        try {
        this.keyStoragePath=keyStoragePath;
        System.out.println(this.keyStoragePath);
        this.password=password;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(lenght);
        keyPair = kpg.generateKeyPair();
        PublicKey pku = keyPair.getPublic();
        System.out.println(pku.toString());
        PrivateKey pkr= keyPair.getPrivate();
        File encryptedFile = new File(this.keyStoragePath);
        FileOutputStream fileOut = new FileOutputStream(encryptedFile);
        ObjectOutputStream os = new ObjectOutputStream (fileOut);
        keySerializable.writeObject(os, pku,pkr);
        System.out.println("ciao");
        os.close();
    } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

        public KeyPair keyLoad(String keyStoragePath, String password){
        try {
            File file = new File(keyStoragePath);
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fIn);
            KeyPair keys= keySerializable.readObject(is);
            is.close();
            return keys;

        }catch (IOException | ClassNotFoundException | InvalidKeySpecException | NoSuchAlgorithmException e ){
            throw new RuntimeException(e);
        }

        }
}
