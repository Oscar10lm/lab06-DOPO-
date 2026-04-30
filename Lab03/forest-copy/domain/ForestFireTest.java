package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ForestFireTest {

    private Forest forest;

    @Before
    public void setUp() {
        forest = new Forest(true);  // bosque vacío
    }

    // --- Fire ---

    @Test
    public void testFireSeExtingueConAgua() {
        new Fire(forest, 5, 5);
        new Water(forest, 5, 6);   // vecino con agua
        forest.ticTac();
        // fire debe haberse convertido en tierra
        assertTrue("fire debe convertirse en Ground",
            forest.getThing(5, 5) instanceof Ground);
    }

    @Test
    public void testFireDura5CiclosRodeadoDeTierra() {
        new Fire(forest, 5, 5);
        // rodear de tierra
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                new Ground(forest, 5+dr, 5+dc);
            }
        // 5 tics
        for (int i = 0; i < 5; i++) forest.ticTac();
        assertTrue("fire debe ser Ground tras 5 ciclos",
            forest.getThing(5, 5) instanceof Ground);
    }

    // --- Water ---

    @Test
    public void testWaterSeMovéAlSurRodeadaDeTierra() {
        new Water(forest, 5, 5);
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                new Ground(forest, 5+dr, 5+dc);
            }
        forest.ticTac();
        assertTrue("agua debe moverse al sur (6,5)",
            forest.getThing(6, 5) instanceof Water);
        assertTrue("origen debe ser Ground",
            forest.getThing(5, 5) instanceof Ground);
    }

    // --- FireTree ---

    @Test
    public void testFireTreePierdeEnergiaConFuegoVecino() {
        new FireTree(forest, 5, 5);
        new Fire(forest, 5, 6);
        int energiaAntes = ((FireTree) forest.getThing(5, 5)).getEnergy();
        forest.ticTac();
        Thing t = forest.getThing(5, 5);
        if (t instanceof FireTree) {
            assertTrue("debe perder energía",
                ((FireTree) t).getEnergy() < energiaAntes);
        } else {
            assertTrue("o haberse convertido en Fire",
                t instanceof Fire);
        }
    }

    @Test
    public void testFireTreeSeConvierteEnFuegoSinEnergia() {
        new FireTree(forest, 5, 5);
        // rodear de fuego para agotar energía rápido
        new Fire(forest, 5, 6);
        new Fire(forest, 5, 4);
        new Fire(forest, 4, 5);
        new Fire(forest, 6, 5);
        // varios tics hasta que se prenda
        for (int i = 0; i < 20; i++) forest.ticTac();
        // debe haberse convertido en Fire o Ground
        Thing t = forest.getThing(5, 5);
        assertFalse("no debe seguir siendo FireTree con energía",
            t instanceof FireTree
            && ((FireTree) t).getEnergy() >= 10);
    }

    // --- Ground ---

    @Test
    public void testGroundPuedeGenerarFire() {
        // sembrar muchos Ground y verificar que eventualmente nace Fire
        boolean aparecioBuego = false;
        for (int intento = 0; intento < 100; intento++) {
            Forest f = new Forest(true);
            new Ground(f, 5, 5);
            f.ticTac();
            if (f.getThing(5, 5) instanceof Fire) {
                aparecioBuego = true;
                break;
            }
        }
        assertTrue("Ground debe poder generar Fire", aparecioBuego);
    }

    @Test
    public void testNoCeldasVacias() {
        // llenar bosque como someThings
        for (int r = 0; r < forest.getSize(); r++)
            for (int c = 0; c < forest.getSize(); c++) {
                double p = Math.random();
                if (p < 0.50)      new FireTree(forest, r, c);
                else if (p < 0.70) new Ground(forest, r, c);
                else if (p < 0.85) new Fire(forest, r, c);
                else               new Water(forest, r, c);
            }
        // tras 5 tics no debe haber celdas vacías
        for (int i = 0; i < 5; i++) forest.ticTac();
        for (int r = 0; r < forest.getSize(); r++)
            for (int c = 0; c < forest.getSize(); c++)
                assertNotNull("celda (" + r + "," + c + ") no debe ser null",
                    forest.getThing(r, c));
    }
}