package model;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyAlgorithm {
    private static final int ENCRYPT_BLOCK_SIZE = 53; // Blocchi da 53 byte per criptare
    private static final int DECRYPT_BLOCK_SIZE = 64; // Blocchi da 64 byte per decriptare

    public void encryptFile(String inputFilePath, String outputFilePath, Key publicKey, String algorithmName) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[ENCRYPT_BLOCK_SIZE];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] blockToEncrypt = (bytesRead < ENCRYPT_BLOCK_SIZE)
                        ? trimBytes(buffer, bytesRead)
                        : buffer;

                byte[] encryptedBlock = cipher.doFinal(blockToEncrypt);
                outputStream.write(encryptedBlock);
            }
        }
    }
    //To manage when a block is not full
    private static byte[] trimBytes(byte[] buffer, int length) {
        byte[] trimmed = new byte[length];
        System.arraycopy(buffer, 0, trimmed, 0, length);
        return trimmed;
    }

    public void decryptFile(String inputFilePath, String outputFilePath, Key privateKey, String algorithmName) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[DECRYPT_BLOCK_SIZE];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] blockToDecrypt = (bytesRead < DECRYPT_BLOCK_SIZE)
                        ? trimBytes(buffer, bytesRead)
                        : buffer;

                byte[] decryptedBlock = cipher.doFinal(blockToDecrypt);
                outputStream.write(decryptedBlock);
            }
        }
    }

}
