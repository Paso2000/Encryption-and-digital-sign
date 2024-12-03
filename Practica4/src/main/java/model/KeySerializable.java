package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeySerializable implements Serializable {
    public PublicKey pku;
    public PrivateKey pkr;

    public KeySerializable(PublicKey pku, PrivateKey pkr){
        this.pku=pku;
        this.pkr=pkr;
    }

    @Serial
    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        try {
            // Leggi i byte delle chiavi dal stream
            byte[] publicKeyBytes = (byte[]) stream.readObject();
            byte[] privateKeyBytes = (byte[]) stream.readObject();

            // Usa KeyFactory per ricostruire le chiavi (esempio con RSA)
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");

            // Ricostruisci la PublicKey
            java.security.spec.X509EncodedKeySpec pubKeySpec = new java.security.spec.X509EncodedKeySpec(publicKeyBytes);
            this.pku = keyFactory.generatePublic(pubKeySpec);

            // Ricostruisci la PrivateKey
            java.security.spec.PKCS8EncodedKeySpec privKeySpec = new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes);
            this.pkr = keyFactory.generatePrivate(privKeySpec);
        } catch (Exception e) {
            throw new IOException("Errore durante la ricostruzione delle chiavi", e);
        }
    }

    @Serial
    public void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(pku.toString().getBytes());
        stream.writeObject(pkr.toString().getBytes());
    }
}
