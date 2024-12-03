package utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class JCEUtils {
    public static byte[] computeMac(String algorithm, SecretKey key, byte[] data)
            throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeyException
    {
        Mac mac = Mac.getInstance(algorithm, "BC");

        mac.init(key);

        mac.update(data);

        return mac.doFinal();
    }
}
