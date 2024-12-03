import controller.PBEController;
import model.HashAlgorithm;
import model.HashAlgorithmFile;
import model.PBEAlgorithm;
import model.PBEAlgorithmFile;
import view.View;

public class MainTest {
    public static void main(String[] args) {
        PBEAlgorithm pbe = new PBEAlgorithm();
        PBEAlgorithmFile pbeFile = new PBEAlgorithmFile();
        HashAlgorithm hashAlgorithm = new HashAlgorithm();
        HashAlgorithmFile hashAlgorithmFile = new HashAlgorithmFile();
        View view = new View();
        PBEController controller = new PBEController(pbe, pbeFile, view,hashAlgorithm, hashAlgorithmFile);
    }
}