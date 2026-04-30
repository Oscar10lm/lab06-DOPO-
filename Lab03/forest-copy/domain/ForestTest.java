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
}