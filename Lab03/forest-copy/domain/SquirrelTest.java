package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

public class SquirrelTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest();
    }

    // --- existencia y estado inicial ---

    @Test
    public void testSquirrelExisteEnPosicion() {
        new Squirrel(forest, 5, 5);
        assertNotNull(forest.getThing(5, 5));
    }

    @Test
    public void testSquirrelEsLivingThing() {
        new Squirrel(forest, 5, 5);
        assertTrue(forest.getThing(5, 5).isLivingThing());
    }

    @Test
    public void testColorInicialEsCafe() {
        new Squirrel(forest, 5, 5);
        Color c = forest.getThing(5, 5).getColor();
        assertEquals(139, c.getRed());
        assertEquals(90,  c.getGreen());
        assertEquals(43,  c.getBlue());
    }

    @Test
    public void testFormaEsSQUARE() {
        new Squirrel(forest, 5, 5);
        assertEquals(Thing.SQUARE, forest.getThing(5, 5).shape());
    }

    // --- color cambia con los años ---

    @Test
    public void testColorCambiaConAnios() {
        new Squirrel(forest, 5, 5);
        Color inicial = forest.getThing(5, 5).getColor();
        // 4 tics = 4 años (cada ticTac incrementa years)
        for (int i = 0; i < 4; i++) forest.ticTac();
        Color despues = forest.getThing(5, 5).getColor();
        // debe ser más amarillenta: más rojo y verde, menos azul
        assertTrue(despues.getRed()   > inicial.getRed());
        assertTrue(despues.getGreen() > inicial.getGreen());
        assertTrue(despues.getBlue()  < inicial.getBlue());
    }

    // --- muerte a los 10 años ---

    @Test
    public void testMuereA10Anios() {
        new Squirrel(forest, 5, 5);
        for (int i = 0; i < 10; i++) forest.ticTac();
        assertNull("La ardilla debe morir a los 10 años",
            forest.getThing(5, 5));
    }

    @Test
    public void testVivaAntesDe10Anios() {
        new Squirrel(forest, 5, 5);
        for (int i = 0; i < 9; i++) forest.ticTac();
        assertNotNull("La ardilla debe vivir antes de 10 años",
            forest.getThing(5, 5));
    }

    // --- reproducción ---

    @Test
    public void testReproduccion() {
        // dos ardillas con celda vacía entre medias en fila
        new Squirrel(forest, 5, 5);
        new Squirrel(forest, 5, 7);
        // (5,6) está vacía — condición de reproducción
        assertTrue(forest.isEmpty(5, 6));
        forest.ticTac();
        // tras el tic debe haber nacido una ardilla en (5,6)
        assertNotNull("Debe nacer ardilla entre las dos vecinas",
            forest.getThing(5, 6));
        assertTrue(forest.getThing(5, 6) instanceof Squirrel);
    }
}