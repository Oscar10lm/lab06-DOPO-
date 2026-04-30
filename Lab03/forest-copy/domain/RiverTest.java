package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class RiverTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest(true);  // bosque vacío
    }

    // --- estado inicial ---

    @Test
    public void testRiverExisteEnPosicion() {
        new River(forest, 8, 0);
        assertNotNull(forest.getThing(8, 0));
    }

    @Test
    public void testRiverNoEsLivingThing() {
        new River(forest, 8, 0);
        assertFalse(forest.getThing(8, 0).isLivingThing());
    }

    @Test
    public void testRiverFormaEsSQUARE() {
        new River(forest, 8, 0);
        assertEquals(Thing.SQUARE, forest.getThing(8, 0).shape());
    }

    @Test
    public void testColorInicialEsAzul() {
        new River(forest, 8, 0);
        Color c = forest.getThing(8, 0).getColor();
        // azul claro inicial: R=173, G=216, B=230
        assertEquals(173, c.getRed());
        assertEquals(216, c.getGreen());
        assertEquals(230, c.getBlue());
    }

    // --- movimiento ---

    @Test
    public void testSeMoveoALaDerecha() {
        new River(forest, 8, 0);
        forest.ticTac();
        // debe haberse movido a columna 1
        assertNotNull("debe estar en (8,1)", forest.getThing(8, 1));
        assertNull("(8,0) debe estar vacía", forest.getThing(8, 0));
    }

    @Test
    public void testMovimientoCircular() {
        // colocar en última columna
        new River(forest, 8, 24);
        forest.ticTac();
        // debe aparecer en columna 0
        assertNotNull("debe aparecer en (8,0) circular",
            forest.getThing(8, 0));
        assertNull("(8,24) debe quedar vacía",
            forest.getThing(8, 24));
    }

    // --- color cambia ---

    @Test
    public void testColorCambiaConTics() {
        new River(forest, 8, 0);
        Color c0 = forest.getThing(8, 0).getColor();
        forest.ticTac();
        Color c1 = forest.getThing(8, 1).getColor();
        assertNotEquals("el color debe cambiar tras el tic", c0, c1);
    }
    
    @Test
    public void testOscarYJuanDiegoNuncaSeSolapan() {
        River oscar     = new River(forest, 3, 0);
        River juanDiego = new River(forest, 3, 12);
    
        for (int i = 0; i < 20; i++) {
            forest.ticTac();
    
            // contar cuántos ríos hay en la fila 3
            int count = 0;
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(3, c) instanceof River) count++;
            }
            assertEquals("debe haber exactamente 2 ríos en fila 3"
                + " en tic " + (i + 1), 2, count);
        }
    }
}