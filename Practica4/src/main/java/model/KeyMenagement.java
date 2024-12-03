package model;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

public class KeyMenagement {

    private String keyStoragePath;
    private String password;
    private int lenght = 512;
    private KeyPair keyPair;



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
        KeySerializable keySerializable = new KeySerializable(pku,pkr);
        File encryptedFile = new File(this.keyStoragePath);
        FileOutputStream fileOut = new FileOutputStream(encryptedFile);
        ObjectOutputStream os = new ObjectOutputStream (fileOut);
        keySerializable.writeObject(os);
        System.out.println("ciao");
        os.close();
    } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void keyLoad(String keyStoragePath, String password){
        try {
            byte[] fileBytes = Files.readAllBytes(Path.of(keyStoragePath));
            ByteArrayInputStream bs= new ByteArrayInputStream(fileBytes); // bytes es el byte[]
            ObjectInputStream is = new ObjectInputStream(bs);
            KeySerializable keySerializable = (KeySerializable) is.readObject();
            is.close();
            System.out.println(keySerializable.pkr.toString()+ "\n"+ keySerializable.pku.toString());

        }catch (IOException e ){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        }
}
