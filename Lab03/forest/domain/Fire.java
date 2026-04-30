package domain;
import java.awt.Color;

/**
 * Representa el fuego en el bosque del simulador Forest Fire.
 * Se extingue al tener agua en su vecindario de Moore convirtiéndose
 * en tierra. Si permanece rodeado únicamente de tierra durante cinco
 * ciclos también se convierte en tierra. Su color alterna entre
 * naranja y rojo en cada ciclo simulando las llamas.
 * No es un ser vivo — implementa Thing directamente.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Fire implements Thing {

    /** Bosque al que pertenece el fuego. */
    private Forest forest;

    /** Posición del fuego en la cuadrícula. */
    private int row, column;

    /** Contador de ciclos que el fuego lleva rodeado de tierra. */
    private int cycles;

    /**
     * Crea un nuevo fuego en la posición (row, column) del bosque.
     * Inicia con cycles en 0 y color naranja.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición
     * @param column columna de la posición
     */
    public Fire(Forest forest, int row, int column) {
        this.forest = forest;
        this.row    = row;
        this.column = column;
        this.cycles = 0;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color del fuego alternando entre naranja y rojo
     * según el número de ciclos transcurridos, simulando las llamas.
     * @return Color.ORANGE si cycles es par, Color.RED si es impar
     */
    public Color getColor() {
        return cycles % 2 == 0 ? Color.ORANGE : Color.RED;
    }

    /**
     * Retorna la forma del fuego — cuadrada.
     * @return Thing.SQUARE
     */
    public int shape() { return Thing.SQUARE; }

    /**
     * Indica que el fuego no es un ser vivo.
     * @return false
     */
    public boolean isLivingThing() { return false; }

    /**
     * Verifica si hay alguna fuente de agua en el vecindario
     * de Moore (8 vecinos) del fuego.
     * @return true si al menos un vecino es Water
     */
    private boolean hasWaterNeighbor() {
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Thing t = forest.getThing(row + dr, column + dc);
                if (t instanceof Water) return true;
            }
        return false;
    }

    /**
     * Verifica si el fuego está completamente rodeado de tierra
     * en su vecindario de Moore (8 vecinos).
     * Retorna false si algún vecino es null o no es Ground.
     * @return true si todos los vecinos son Ground
     */
    private boolean surroundedByGround() {
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Thing t = forest.getThing(row + dr, column + dc);
                if (t == null || !(t instanceof Ground)) return false;
            }
        return true;
    }

    /**
     * Ejecuta el comportamiento del fuego en cada ciclo siguiendo
     * el orden de prioridad de las reglas:
     * 1. Si hay agua en el vecindario se extingue convirtiéndose
     *    en tierra inmediatamente.
     * 2. Si lleva 5 o más ciclos rodeado únicamente de tierra
     *    también se convierte en tierra.
     */
    public void ticTac() {
        if (hasWaterNeighbor()) {
            forest.setThing(row, column, null);
            new Ground(forest, row, column);
            return;
        }
        cycles++;
        if (cycles >= 5 && surroundedByGround()) {
            forest.setThing(row, column, null);
            new Ground(forest, row, column);
        }
    }
}