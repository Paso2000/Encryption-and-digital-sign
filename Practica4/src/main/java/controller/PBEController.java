package controller;

import model.*;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Controller class for managing the interaction between the View and the Model.
 * This class implements the MVC design pattern, serving as the Controller.
 * It handles user actions in the GUI (View) and interacts with the business logic in the Model.
 */
public class PBEController {

    private PBEAlgorithm pbeAlgorithm;
    private PBEAlgorithmFile pbeAlgorithmFile;
    private HashAlgorithmFile hashAlgorithmFile;
    private HashAlgorithm hashAlgorithm;
    private KeyMenagement keyMenagement;
    private View view;
    private String result;

    private String[] emptyArrray= {};

    private String value;

    private PublicKey publicKey;

    private PrivateKey privateKey;



    /**
     * Constructor for PBEController.
     *
     * @param pbeAlgorithm      The algorithm for encrypting and decrypting messages.
     * @param pbeAlgorithmFile  The algorithm for encrypting and decrypting files.
     * @param view              The GUI (View) used to interact with the user.
     * @param hashAlgorithm     The algorithm for hashing and verifying messages.
     * @param hashAlgorithmFile The algorithm for hashing and verifying files.
     */
    public PBEController(PBEAlgorithm pbeAlgorithm, PBEAlgorithmFile pbeAlgorithmFile, View view, HashAlgorithm hashAlgorithm, HashAlgorithmFile hashAlgorithmFile, KeyMenagement keyMenagement) {
        this.pbeAlgorithm = pbeAlgorithm;
        this.pbeAlgorithmFile = pbeAlgorithmFile;
        this.hashAlgorithm = hashAlgorithm;
        this.hashAlgorithmFile = hashAlgorithmFile;
        this.keyMenagement=keyMenagement;
        this.view = view;

        // Connect action listeners to the buttons in the View
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecryptButtonListener(new DecryptButtonListener());
        this.view.addVerifyButtonListener(new VerifyButtonListener());
        this.view.addMessageHashButtonListener(new MessageHashButtonListener());
        this.view.addFileHashButtonListener(new FileHashButtonListener());
        this.view.addVerifyFileHashButtonListener(new VerifyFileHashButtonListener());
        this.view.addGenerateKeyButtonListener(new GenerateKeyButtonListener());
        this.view.addLoadKeyButtonListener(new LoadKeyButtonListener());
        this.view.addShowKeyButtonListener(new ShowKeyButtonListener());
    }

    /**
     * Listener for the "Hash File" button.
     * Handles file hashing operations.
     */
    class ShowKeyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(publicKey!=null && privateKey!=null){
                view.addResult(publicKey.toString());
                view.addResult(privateKey.toString());
            }else view.addResult("No key loaded");
        }
    }
    class GenerateKeyButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyStoragePath = view.getKeyStoragePath();
            value = view.getPasswordValue();
            keyMenagement.keyGenerationAndStorage(keyStoragePath,value);
        }
    }

    class LoadKeyButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyStoragePath = view.getKeyStoragePath();
            value = view.getPasswordValue();
            KeyPair keys = keyMenagement.keyLoad(keyStoragePath,value);
            publicKey = keys.getPublic();
            privateKey = keys.getPrivate();
            view.addResult("Keys loaded");
        }
    }

    class FileHashButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Value control
            value = view.getPasswordValue();
            if(!value.isEmpty()){
            File file = View.getFile();
            //File control
            if (file != null) {
                String hashFunction = view.getHashAlgorithm();
                try {
                    String[] result = hashAlgorithmFile.hashFileEncrypt(file, hashFunction, value);
                    view.addResult("File hash: " + result[0] +
                            "\nWith the Algorithm: " + result[1]
                            + "\nSize: " + file.length());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                view.addResult("No file selected");
            }
        }else {
                view.addResult("Insert a Value");
            }
    }}

    /**
     * Listener for the "Verify File Hash" button.
     * Handles file hash verification operations.
     */
    class VerifyFileHashButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            value = view.getPasswordValue();
            if(!value.isEmpty()){
            File file = View.getFile();
            if (file != null) {
                String hashFunction = view.getHashAlgorithm();
                try {
                    String[] result = hashAlgorithmFile.hashVerifyFile(file, hashFunction, value);
                    if (result[0].equals(result[1])) {
                        view.addResult("File hash: " + result[0] + "\nCalculated hash: " + result[1] +
                                "\nSize: " + file.length() +
                                "\nNot modified file, the hash is the same");
                    } else {
                        view.addResult("File hash: " + result[0] + "\nCalculated hash: " + result[1] +
                                "\nSize: " + file.length() +
                                "\nModified file or different password");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                view.addResult("No file selected");
            }}else {
                view.addResult("Insert a Value");
            }
        }
    }

    /**
     * Listener for the "Hash Message" button.
     * Handles hashing operations for plaintext messages.
     */
    class MessageHashButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            value = view.getPasswordValue();
            if(!value.isEmpty()){
            String plaintext = view.getInputText();
            String hashFunction = view.getHashAlgorithm();
            try {
                result = hashAlgorithm.protectMessageHash(plaintext, hashFunction, value);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            view.setResult(result);
        }else{
            view.setResult("Insert a value");
        }
    }
    }

    /**
     * Listener for the "Verify Message Hash" button.
     * Handles hash verification for messages.
     */
    class VerifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            value = view.getPasswordValue();
            if(!value.isEmpty()){
            String hashedTest = view.getInputText();
            String hashFunction = view.getHashAlgorithm();
            try {
                result = hashAlgorithm.verifyHashMessage(hashedTest, hashFunction, value);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            view.setResult(result);
        }else {
                view.setResult("Insert a value");
            }
    }}

    /**
     * Listener for the "Encrypt File" button.
     * Handles file encryption operations.
     */
    class EncryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                File file = View.getFile();
                String password = view.getPasswordValue();
                String symmetricAlgorithm = view.getSymmetricAlgorithm();
                pbeAlgorithmFile.Encrypt(file, password, symmetricAlgorithm);
                view.addResult("Successfully encrypted file: " + file.getPath() +
                        "\nSize: " + file.length() + " byte");
            } catch (Exception ex) {
                view.showError("Error during encryption: " + ex.getMessage());
            }
        }
    }

    /**
     * Listener for the "Decrypt File" button.
     * Handles file decryption operations.
     */
    class DecryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                File file = View.getFile();
                if (file != null) {
                    String password = view.getPasswordValue();
                    String symmetricAlgorithm = view.getSymmetricAlgorithm();
                    pbeAlgorithmFile.Decrypt(file, password, symmetricAlgorithm);
                    view.addResult("Successfully decrypted file: " + file.getPath() +
                            "\nSize: " + file.length() + " byte");
                } else {
                    view.addResult("No file selected");
                }
            } catch (Exception ex) {
                view.showError("Error during decryption: " + ex.getMessage());
            }
        }
    }
}