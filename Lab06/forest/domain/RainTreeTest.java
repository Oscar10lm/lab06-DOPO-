package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class RainTreeTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest(true);
    }

    // --- estado inicial ---

    @Test
    public void testRainTreeExisteEnPosicion() {
        new RainTree(forest, 5, 5);
        assertNotNull(forest.getThing(5, 5));
    }

    @Test
    public void testRainTreeEsLivingThing() {
        new RainTree(forest, 5, 5);
        assertTrue(forest.getThing(5, 5).isLivingThing());
    }

    @Test
    public void testFormaEsSQUARE() {
        new RainTree(forest, 5, 5);
        assertEquals(Thing.SQUARE, forest.getThing(5, 5).shape());
    }

    @Test
    public void testColorInicialEsAzulClaro() {
        new RainTree(forest, 5, 5);
        Color c = forest.getThing(5, 5).getColor();
        // azul inicial: R=173, G=216, B=230
        assertEquals(173, c.getRed());
        assertEquals(216, c.getGreen());
        assertEquals(230, c.getBlue());
    }

    // --- color se intensifica ---

    @Test
    public void testColorSeIntensificaConTics() {
        new RainTree(forest, 5, 5);
        Color inicial = forest.getThing(5, 5).getColor();
        for (int i = 0; i < 4; i++) forest.ticTac();
        Color despues = forest.getThing(5, 5).getColor();
        // R y G deben haber bajado (más azul intenso)
        assertTrue(despues.getRed()   < inicial.getRed());
        assertTrue(despues.getGreen() < inicial.getGreen());
        assertEquals(230, despues.getBlue());  // B constante
    }

    // --- riega vecinos ---

    @Test
    public void testRiegaVecinoAlSegundoTic() {
        new RainTree(forest, 12, 12);
        // (11,12),(12,13),(13,12),(12,11) están vacías
        forest.ticTac();  // tic 1 — aún no riega
        forest.ticTac();  // tic 2 — riega primer vecino
        // al menos una celda adyacente debe tener un Tree nuevo
        boolean hayVecino =
            forest.getThing(11, 12) instanceof Tree ||
            forest.getThing(12, 13) instanceof Tree ||
            forest.getThing(13, 12) instanceof Tree ||
            forest.getThing(12, 11) instanceof Tree;
        assertTrue("debe haber nacido un árbol vecino en tic 2",
            hayVecino);
    }

    @Test
    public void testNoRiegaEnTicImpar() {
        new RainTree(forest, 12, 12);
        forest.ticTac();  // tic 1 — no riega
        boolean hayVecino =
            forest.getThing(11, 12) instanceof Tree ||
            forest.getThing(12, 13) instanceof Tree ||
            forest.getThing(13, 12) instanceof Tree ||
            forest.getThing(12, 11) instanceof Tree;
        assertFalse("no debe regar en tic impar", hayVecino);
    }
    
    @Test
    public void testLassoYGaitanRieganSinConflicto() {
        new RainTree(forest, 12, 12);  // lasso
        new RainTree(forest, 12, 14);  // gaitan
    
        forest.ticTac(); // tic 1
        assertNull("(12,13) debe estar vacía en tic 1",
            forest.getThing(12, 13));
    
        forest.ticTac(); // tic 2
    
        // lasso riega al primer vecino libre (norte: 11,12)
        // gaitan riega al primer vecino libre (norte: 11,14)
        boolean lassoRiego =
            forest.getThing(11, 12) instanceof Tree ||
            forest.getThing(12, 13) instanceof Tree ||
            forest.getThing(13, 12) instanceof Tree ||
            forest.getThing(12, 11) instanceof Tree;
    
        assertTrue("lasso debe haber regado un vecino", lassoRiego);
    
        // lasso y gaitan siguen vivos
        assertNotNull("lasso debe seguir vivo", forest.getThing(12, 12));
        assertNotNull("gaitan debe seguir vivo", forest.getThing(12, 14));
}
}