package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class ForestTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest();
        // someThings() tiene:
        // new Tree(this, 10, 10) → beard
        // new Tree(this, 15, 15) → soul
    }

    @Test
    public void testTicTacNullSafe() {
        try {
            forest.ticTac();
        } catch (NullPointerException e) {
            fail("ticTac() no debe fallar en celdas null");
        }
    }

    @Test
    public void testUnTicCambiaAGreen() {
        forest.ticTac();
        assertEquals(Color.GREEN, forest.getThing(10, 10).getColor());
        assertEquals(Color.GREEN, forest.getThing(15, 15).getColor());
    }

    @Test
    public void testDosTicsCambiaAOrange() {
        forest.ticTac();
        forest.ticTac();
        assertEquals(Color.ORANGE, forest.getThing(10, 10).getColor());
        assertEquals(Color.ORANGE, forest.getThing(15, 15).getColor());
    }

    @Test
    public void testTresTicsCambiaAGrayYConsumeEnergia() {
        forest.ticTac();
        forest.ticTac();
        forest.ticTac();
        assertEquals(Color.GRAY, forest.getThing(10, 10).getColor());
        assertEquals(99, ((LivingThing) forest.getThing(10, 10)).getEnergy());
    }

    @Test
    public void testCuatroTicsVuelveAPinkYSumaYear() {
        forest.ticTac();
        forest.ticTac();
        forest.ticTac();
        forest.ticTac();
        assertEquals(Color.PINK, forest.getThing(10, 10).getColor());
        assertEquals(1, ((Tree) forest.getThing(10, 10)).years);
    }

    @Test
    public void testArbolesMuerenAl400Tic() {
        for (int i = 0; i < 400; i++) forest.ticTac();
        assertNull(forest.getThing(10, 10));
        assertNull(forest.getThing(15, 15));
    }
    
    @Test
    public void testDiagnostico() {
        forest = new Forest(true);
        new RainTree(forest, 12, 12);
        new RainTree(forest, 12, 14);
        
        // verificar que (12,13) está vacía ANTES del ticTac
        assertTrue("(12,13) debe ser isEmpty antes", forest.isEmpty(12, 13));
        assertTrue("(11,12) debe ser isEmpty antes", forest.isEmpty(11, 12));
        assertTrue("(13,12) debe ser isEmpty antes", forest.isEmpty(13, 12));
        
        forest.ticTac(); // tic 1
        forest.ticTac(); // tic 2
        
        // ver qué hay alrededor de lasso
        System.out.println("(11,12): " + forest.getThing(11, 12));
        System.out.println("(12,13): " + forest.getThing(12, 13));
        System.out.println("(13,12): " + forest.getThing(13, 12));
        System.out.println("(12,11): " + forest.getThing(12, 11));
    }

    @Test
    public void testSaveAs() {
        try {
            Forest testForest = new Forest(true);
            java.io.File tempFile = java.io.File.createTempFile("testForestSave", ".dat");
            testForest.saveAs(tempFile);
            assertTrue("El archivo debería haber sido creado", tempFile.exists());
            assertTrue("El archivo no debería estar vacío", tempFile.length() > 0);
            tempFile.delete(); // Limpiar el archivo después de la prueba
        } catch (Exception e) {
            fail("No debería haber lanzado excepción: " + e.getMessage());
        }
    }

    @Test
    public void testOpen() {
        try {
            Forest original = new Forest(true); 
            new Fire(original, 10, 10); 
            java.io.File tempFile = java.io.File.createTempFile("testOpen", ".dat");
            original.saveAs(tempFile);
            
            Forest cargado = new Forest(true); 
            cargado = cargado.open(tempFile); 
            
            assertNotNull("El fuego debe haber sido cargado desde el archivo", cargado.getThing(10, 10));
            assertTrue("Debe ser instancia de Fire", cargado.getThing(10, 10) instanceof Fire);
            
            tempFile.delete();
        } catch (Exception e) {
            fail("Falló testOpen: " + e.getMessage());
        }
    }

    @Test
    public void testExportAs() {
        try {
            Forest f = new Forest(true); 
            new Fire(f, 10, 10);
            new Squirrel(f, 15, 15);
            java.io.File tempFile = java.io.File.createTempFile("testExport", ".txt");
            f.exportAs(tempFile);
            
            java.util.Scanner scanner = new java.util.Scanner(tempFile);
            String contenido = "";
            while(scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();
            
            assertTrue("Debe contener Fire 10, 10", contenido.contains("Fire 10, 10"));
            assertTrue("Debe contener Squirrel 15, 15", contenido.contains("Squirrel 15, 15"));
            
            tempFile.delete();
        } catch (Exception e) {
            fail("Falló testExportAs: " + e.getMessage());
        }
    }

    @Test
    public void testImportFile() {
        try {
            java.io.File tempFile = java.io.File.createTempFile("testImport", ".txt");
            java.io.PrintWriter out = new java.io.PrintWriter(tempFile);
            out.println("Water 5, 5");
            out.println("Tree 12, 12");
            out.close();
            
            Forest f = new Forest(true);
            f = f.importFile(tempFile);
            
            assertNotNull("Debe haber Water", f.getThing(5, 5));
            assertTrue("Debe ser instancia de Water", f.getThing(5, 5) instanceof Water);
            assertNotNull("Debe haber Tree", f.getThing(12, 12));
            assertTrue("Debe ser instancia de Tree", f.getThing(12, 12) instanceof Tree);
            
            tempFile.delete();
        } catch (Exception e) {
            fail("Falló testImportFile: " + e.getMessage());
        }
    @Test
    public void testOpenFileNotFound() {
        try {
            Forest f = new Forest(true);
            f.open(new java.io.File("archivo_no_existente_test_xyz.dat"));
            fail("Se esperaba ForestException por archivo no encontrado.");
        } catch (ForestException e) {
            assertEquals(ForestException.ARCHIVO_NO_ENCONTRADO, e.getMessage());
        }
    }

    @Test
    public void testSaveAsInvalidExtension() {
        try {
            Forest f = new Forest(true);
            f.saveAs(new java.io.File("bosque.txt")); // Not .dat
            fail("Se esperaba ForestException por extensión incorrecta.");
        } catch (ForestException e) {
            assertEquals(ForestException.EXTENSION_INVALIDA_DAT, e.getMessage());
        }
    }
    @Test
    public void testExportInvalidTextExt() {
        try {
            Forest f = new Forest(true);
            f.exportAs(new java.io.File("bosque.dat")); // Wants .txt
            fail("Esperaba error de extensión.");
        } catch (ForestException e) {
            assertEquals(ForestException.EXTENSION_INVALIDA_TXT, e.getMessage());
        }
    }

    @Test
    public void testImportBadType() {
        try {
            java.io.File temp = java.io.File.createTempFile("bad", ".txt");
            java.io.PrintWriter pw = new java.io.PrintWriter(temp);
            pw.println("Marciano 10, 10");
            pw.close();
            
            Forest f = new Forest(true);
            f.importFile(temp);
            fail("Debió tronar por Marciano.");
        } catch (ForestException e) {
            assertTrue(e.getMessage().contains(ForestException.ERROR_TIPO_DESCONOCIDO));
        }
    }
}