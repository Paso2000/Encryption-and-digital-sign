package model;

import java.io.*;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class KeySerializable implements Serializable {
    public PublicKey pku;
    public PrivateKey pkr;


    @Serial
    public KeyPair readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {

            // Leggi i byte delle chiavi dal stream
            byte[] publicKeyBytes = (byte[]) stream.readObject();
            byte[] privateKeyBytes = (byte[]) stream.readObject();

            // Usa KeyFactory per ricostruire le chiavi (esempio con RSA)
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");

            // Ricostruisci la PublicKey
            java.security.spec.X509EncodedKeySpec pubKeySpec = new java.security.spec.X509EncodedKeySpec(publicKeyBytes);
            pku = keyFactory.generatePublic(pubKeySpec);

            // Ricostruisci la PrivateKey
            java.security.spec.PKCS8EncodedKeySpec privKeySpec = new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes);
            pkr = keyFactory.generatePrivate(privKeySpec);

            return new KeyPair(pku,pkr);


    }

    @Serial
    public void writeObject(ObjectOutputStream stream, PublicKey pku, PrivateKey pkr)
            throws IOException
    {
        stream.writeObject(pku.getEncoded());
        stream.writeObject(pkr.getEncoded());
    }
}
