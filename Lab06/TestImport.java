import domain.*;
import java.io.File;

public class TestImport {
    public static void main(String[] args) {
        try {
            Forest f = new Forest(true);
            f.importFile(new File("forestOther"));
            System.out.println("Import exitoso");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
