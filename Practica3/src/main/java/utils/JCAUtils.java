package utils;

import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class JCAUtils {
    public static byte[] computeDigest(String digestName, byte[] data)
            throws NoSuchProviderException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(digestName, "BC");

        digest.update(data);

        return digest.digest();
    }

    /**
     * Return a DigestCalculator for the passed in algorithm digestName.
     *
     * @param digestName the name of the digest algorithm.
     * @return a DigestCalculator for the digestName.
     */
    public static DigestCalculator createDigestCalculator(String digestName)
            throws OperatorCreationException {
        DigestAlgorithmIdentifierFinder algFinder =
                new DefaultDigestAlgorithmIdentifierFinder();
        DigestCalculatorProvider digCalcProv =
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();

        return digCalcProv.get(algFinder.find(digestName));
    }
}
