package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class ShadowTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest();
    }

    // --- estado inicial ---

    @Test
    public void testShadowExisteEnPosicion() {
        new Shadow(forest, 10, 0);
        assertNotNull(forest.getThing(10, 0));
    }

    @Test
    public void testShadowColorEsNegro() {
        new Shadow(forest, 10, 0);
        assertEquals(Color.BLACK, forest.getThing(10, 0).getColor());
    }

    @Test
    public void testShadowFormaEsSQUARE() {
        new Shadow(forest, 10, 0);
        assertEquals(Thing.SQUARE, forest.getThing(10, 0).shape());
    }

    @Test
    public void testShadowNoEsLivingThing() {
        new Shadow(forest, 10, 0);
        assertFalse(forest.getThing(10, 0).isLivingThing());
    }

    // --- movimiento norte ---

    @Test
    public void testMueveDeFilaAlNorte() {
        new Shadow(forest, 10, 0);
        forest.ticTac();
        // debe haberse movido a fila 9
        assertNotNull("debe existir en fila 9",
            forest.getThing(9, 0));
        assertEquals(Color.BLACK,
            forest.getThing(9, 0).getColor());
    }

    @Test
    public void testMovimientoCircular() {
        new Shadow(forest, 0, 0);
        forest.ticTac();
        // fila 0 → debe ir a fila SIZE-1 = 24
        assertNotNull("debe aparecer en fila 24 (circular)",
            forest.getThing(24, 0));
        assertEquals(Color.BLACK,
            forest.getThing(24, 0).getColor());
    }

    // --- sombra en fila completa ---

    @Test
    public void testEsparceSombraEnFilaCompleta() {
        new Shadow(forest, 10, 0);
        // antes del tic solo ocupa (10,0)
        assertNull(forest.getThing(10, 5));
        forest.ticTac();
        // tras el tic la sombra se movio a fila 9
        // la fila 10 queda libre de sombra
        assertNull("fila anterior debe quedar libre",
            forest.getThing(10, 0));
    }

    @Test
    public void testFilaAnteriorLiberada() {
        new Shadow(forest, 10, 12);
        forest.ticTac();
        // la sombra ya no debe estar en fila 10
        for (int c = 0; c < forest.getSize(); c++) {
            Thing t = forest.getThing(10, c);
            if (t != null) {
                assertFalse("fila 10 no debe tener sombra",
                    t.getColor().equals(Color.BLACK)
                    && !t.isLivingThing());
            }
        }
    }
}