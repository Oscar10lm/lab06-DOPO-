package domain;
import java.awt.Color;

/**
 * Representa una celda de tierra en el bosque del simulador Forest Fire.
 * Es el elemento base del ecosistema — cuando otros elementos mueren
 * o se transforman dejan tierra en su lugar. Puede generar fuego con
 * una probabilidad del 10% o agua con una probabilidad del 5% en cada
 * ciclo. No es un ser vivo — implementa Thing directamente.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Ground implements Thing {

    /** Bosque al que pertenece la tierra. */
    private Forest forest;

    /** Posición de la tierra en la cuadrícula. */
    private int row, column;

    /**
     * Crea una nueva celda de tierra en la posición (row, column).
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición
     * @param column columna de la posición
     */
    public Ground(Forest forest, int row, int column) {
        this.forest = forest;
        this.row    = row;
        this.column = column;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color de la tierra — café.
     * @return Color(139, 90, 43)
     */
    public Color getColor() { return new Color(139, 90, 43); }

    /**
     * Retorna la forma de la tierra — cuadrada.
     * @return Thing.SQUARE
     */
    public int shape() { return Thing.SQUARE; }

    /**
     * Indica que la tierra no es un ser vivo.
     * @return false
     */
    public boolean isLivingThing() { return false; }

    /**
     * Ejecuta el comportamiento de la tierra en cada ciclo.
     * Con probabilidad del 10% genera fuego en su celda.
     * Con probabilidad del 5% genera agua en su celda.
     * En ambos casos la tierra desaparece y es reemplazada
     * por el nuevo elemento. Con el 85% restante no hace nada.
     */
    public void ticTac() {
        double prob = Math.random();
        if (prob < 0.10) {
            forest.setThing(row, column, null);
            new Fire(forest, row, column);
        } else if (prob < 0.15) {
            forest.setThing(row, column, null);
            new Water(forest, row, column);
        }
    }
}