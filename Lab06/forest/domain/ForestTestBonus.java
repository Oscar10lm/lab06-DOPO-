package domain;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

/**
 * Pruebas unitarias para la Parte IV (Bono) - Perfeccionando importar.
 */
public class ForestTestBonus {

    @Test
    public void testImport02DetailedException() {
        Forest forest = new Forest(true);
        File errFile = new File("../forestErr.txt");
        
        try {
            forest.import02(errFile);
            fail("Debería haber lanzado una excepción detallada ForestParseException");
        } catch (ForestParseException e) {
            // Evaluamos que la primera línea con error en forestErr.txt ("Dragon 12 15") sea reportada.
            assertEquals("Debe identificar el error en la línea 2", 2, e.getLineNumber());
            assertEquals("Debe identificar 'Dragon' como el causante", "Dragon", e.getErroneousWord());
            assertTrue("El mensaje debe indicar tipo desconocido", e.getMessage().contains("Palabra reservada o tipo"));
        } catch (ForestException e) {
            fail("La excepción debería ser de tipo ForestParseException");
        }
    }

    @Test
    public void testImport03FlexibleReflection() {
        Forest forest = new Forest(true);
        File reflectionFile = new File("../forestReflection.txt");
        
        try {
            Forest loaded = forest.import03(reflectionFile);
            assertNotNull("El bosque debería cargarse exitosamente usando Reflection", loaded);
            
            // Verificamos que los objetos fueron instanciados (por ejemplo, Tree en 1,1)
            assertTrue(loaded.getThing(1, 1) instanceof Tree);
            assertTrue(loaded.getThing(2, 2) instanceof FireTree);
            assertTrue(loaded.getThing(7, 7) instanceof Ground);
            
        } catch (ForestException e) {
            fail("No debería lanzar excepción para el archivo forestReflection.txt válido. " + e.getMessage());
        }
    }
}
