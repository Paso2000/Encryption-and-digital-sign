package model;

import org.apache.commons.io.output.CountingOutputStream;
import org.bouncycastle.util.encoders.Hex;
import utils.Header;
import utils.Options;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.Arrays;


public class DigitalSignAlgorithm {

    public long bytetoDelete;

    public void Sign(File file, String algorithm, PrivateKey pkr) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Path.of(file.getPath()));
        String encryptedFilePath = file.getParent() + File.separator +
                file.getName().replaceFirst("[.][^.]+$", "") + ".SIG";
        File encryptedFile = new File(encryptedFilePath);
        Signature dsa = Signature.getInstance(algorithm);
        dsa.initSign(pkr);
        dsa.update(fileBytes);

        byte[] signature = dsa.sign();
        System.out.println(Hex.toHexString(signature));
        Header header = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algorithm, signature);
        FileOutputStream fOut = new FileOutputStream(encryptedFile);
        CountingOutputStream outputStream = new CountingOutputStream(fOut);
        header.save(outputStream);
        bytetoDelete = outputStream.getByteCount(); // Store header size
        fOut.write(fileBytes);
        fOut.close();
    }

    public void Verify(File file, String algorithm,PublicKey pku) throws Exception {
        //take all the bytes from the file
        byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
        //create empty header
        // decryptedFilePath
        String decryptedFilePath = file.getParent() + File.separator +
                file.getName().replaceFirst("[.][^.]+$", "") + "_verified.VER";
        //create the file
        File decryptdFile = new File(decryptedFilePath);

        try(FileOutputStream fileOut = new FileOutputStream(decryptdFile);
             ByteArrayInputStream cIn = new ByteArrayInputStream(bytes)) {
            Header header= new Header();
            header.load(cIn);
            byte [] fileBytes = Arrays.copyOfRange(bytes, (int) bytetoDelete, bytes.length);
            System.out.println(bytetoDelete);
            byte[] calculatedHash = header.getData();
            System.out.println(Hex.toHexString(calculatedHash));
            Signature dsa = Signature.getInstance(algorithm);
            dsa.initVerify(pku);
            dsa.update(fileBytes);
            boolean verifies = dsa.verify(calculatedHash);
            if(verifies){
                fileOut.write(fileBytes);
                System.out.println("tutto bene");
            }else {
                System.out.println("non bene");
            }


        }
    }

}
