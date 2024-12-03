package model;

import java.security.*;

public class KeyMenagement {

    private String keyStoragePath;
    private String password;
    private int lenght = 512;
    private KeyPair keyPair;

    public void keyGenerationAndStorage(String keyStoragePath, String password) throws NoSuchAlgorithmException {
        this.keyStoragePath=keyStoragePath;
        this.password=password;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA/ECB/PKCS1Padding");
        kpg.initialize(lenght);
        keyPair = kpg.generateKeyPair();
        PublicKey pku = keyPair.getPublic();
        PrivateKey pkr= keyPair.getPrivate();
        //inserire nel file

    }

    public void keyLoad(String keyStoragePath, String password){

    }
}
