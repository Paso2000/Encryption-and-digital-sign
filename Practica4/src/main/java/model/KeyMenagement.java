package model;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class KeyMenagement {

    private String keyStoragePath;
    private int lenght = 512;
    private KeyPair keyPair;
    private KeySerializable keySerializable = new KeySerializable();

    public KeyPair keyGeneration() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(lenght);
        keyPair = kpg.generateKeyPair();
        return keyPair;
    }


    public void keyStorage(String keyStoragePath, PublicKey publicKey, PrivateKey privateKey)   {
        try {
        this.keyStoragePath=keyStoragePath;
        PublicKey pku = publicKey;
        PrivateKey pkr= privateKey;
        File encryptedFile = new File(this.keyStoragePath);
        FileOutputStream fileOut = new FileOutputStream(encryptedFile);
        ObjectOutputStream os = new ObjectOutputStream (fileOut);
        keySerializable.writeObject(os, pku,pkr);
        os.close();
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public KeyPair keyLoad(String keyStoragePath, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, ClassNotFoundException {

            File file = new File(keyStoragePath);
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fIn);
            KeyPair keys= keySerializable.readObject(is);
            is.close();
            return keys;

        }
}
