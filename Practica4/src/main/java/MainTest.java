import controller.PBEController;
import model.*;
import view.View;

public class MainTest {
    public static void main(String[] args) {
        PBEAlgorithm pbe = new PBEAlgorithm();
        PBEAlgorithmFile pbeFile = new PBEAlgorithmFile();
        HashAlgorithm hashAlgorithm = new HashAlgorithm();
        HashAlgorithmFile hashAlgorithmFile = new HashAlgorithmFile();
        KeyMenagement keyMenagement = new KeyMenagement();
        View view = new View();
        PBEController controller = new PBEController(pbe, pbeFile, view,hashAlgorithm, hashAlgorithmFile, keyMenagement);
    }
}