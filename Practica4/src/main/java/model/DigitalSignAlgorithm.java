package model;

import utils.Header;
import utils.Options;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

public class DigitalSignAlgorithm {

    public void Sign(File file, String algorithm, PrivateKey pkr) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Path.of(file.getPath()));
        String encryptedFilePath = file.getParent() + File.separator +
                file.getName().replaceFirst("[.][^.]+$", "") + ".SIG";
        File encryptedFile = new File(encryptedFilePath);
        Signature dsa = Signature.getInstance(algorithm);
        dsa.initSign(pkr);
        dsa.update(fileBytes);
        byte[] signature = dsa.sign();
        System.out.println(signature);
        Header header = new Header(Options.OP_SIGNED,algorithm, Options.OP_NONE_ALGORITHM, signature);
        FileOutputStream fOut = new FileOutputStream(encryptedFile);
        header.save(fOut);
        fOut.write(fileBytes);
        fOut.close();
    }

    public void Verify(File file, String algorithm,PublicKey pku) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Path.of(file.getPath()));
        Header header= new Header();
        String decryptedFilePath = file.getParent() + File.separator +
                file.getName().replaceFirst("[.][^.]+$", "") + "_verified.VER";
        File decryptdFile = new File(decryptedFilePath);
        try (FileOutputStream fileOut = new FileOutputStream(decryptdFile);
             ByteArrayInputStream cIn = new ByteArrayInputStream(fileBytes)) {
            header.load(cIn);
            byte[] calculatedHash = header.getData();
            System.out.println(calculatedHash);
            Signature dsa = Signature.getInstance(algorithm);
            dsa.initVerify(pku);
            dsa.update(fileBytes);
            byte[] sign = dsa.sign();
            System.out.println(sign);
            boolean verifies = dsa.verify(calculatedHash);
            System.out.println(verifies);

        }



    }
}
