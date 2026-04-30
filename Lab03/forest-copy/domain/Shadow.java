package domain;
import java.awt.Color;

/**
 * Representa una sombra en el bosque. Es un punto negro que esparce
 * su sombra en toda la fila en la que se encuentra y se desplaza
 * de sur a norte circularmente en cada ciclo.
 * No es un ser vivo — implementa Thing directamente.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Shadow implements Thing {

    private Forest forest;
    private int row;
    private int column;

    /**
     * Crea una nueva sombra en la posición (row, column) del bosque.
     * @param forest bosque al que pertenece
     * @param row    fila inicial
     * @param column columna inicial
     */
    public Shadow(Forest forest, int row, int column) {
        this.forest = forest;
        this.row    = row;
        this.column = column;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color de la sombra — siempre negro.
     * @return Color.BLACK
     */
    public Color getColor() {
        return Color.BLACK;
    }

    /**
     * Retorna la forma de la sombra — cuadrada.
     * @return Thing.SQUARE
     */
    public int shape() {
        return Thing.SQUARE;
    }

    /**
     * Indica que la sombra no es un ser vivo.
     * @return false
     */
    public boolean isLivingThing() {
        return false;
    }

    /**
     * Ejecuta el comportamiento de la sombra en cada ciclo:
     * 1. Esparce la sombra ocupando todas las celdas vacías de su fila.
     * 2. Se desplaza una fila hacia el norte de forma circular.
     *    Al llegar a la fila 0 reaparece en la fila SIZE-1.
     */
    public void ticTac() {
        for (int c = 0; c < forest.getSize(); c++) {
            if (forest.isEmpty(row, c)) {
                forest.setThing(row, c, this);
            }
        }
        int newRow = (row - 1 + forest.getSize()) % forest.getSize();
        for (int c = 0; c < forest.getSize(); c++) {
            if (forest.getThing(row, c) == this) {
                forest.setThing(row, c, null);
            }
        }
        row = newRow;
        forest.setThing(row, column, this);
    }

    /**
     * Elimina la sombra del bosque liberando su celda actual.
     */
    public void die() {
        forest.setThing(row, column, null);
    }
}