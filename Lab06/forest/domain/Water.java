package domain;
import java.awt.Color;

/**
 * Representa una fuente de agua en el bosque del simulador Forest Fire.
 * Si está rodeada completamente de tierra intenta moverse hacia el sur,
 * convirtiendo su celda origen en tierra. Puede agotar un árbol vecino
 * restaurando su energía al 100%, convirtiéndose ella misma en tierra.
 * No es un ser vivo — implementa Thing directamente.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Water implements Thing {

    /** Bosque al que pertenece el agua. */
    private Forest forest;

    /** Posición actual del agua en la cuadrícula. */
    private int row, column;

    /**
     * Crea una nueva fuente de agua en la posición (row, column).
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public Water(Forest forest, int row, int column) {
        this.forest = forest;
        this.row    = row;
        this.column = column;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color del agua — azul.
     * @return Color(64, 164, 223)
     */
    public Color getColor() { return new Color(64, 164, 223); }

    /**
     * Retorna la forma del agua — cuadrada.
     * @return Thing.SQUARE
     */
    public int shape() { return Thing.SQUARE; }

    /**
     * Indica que el agua no es un ser vivo.
     * @return false
     */
    public boolean isLivingThing() { return false; }

    /**
     * Retorna la fila actual del agua.
     * @return fila
     */
    public int getRow() { return row; }

    /**
     * Retorna la columna actual del agua.
     * @return columna
     */
    public int getColumn() { return column; }

    /**
     * Verifica si el agua está completamente rodeada de tierra
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
     * Ejecuta el comportamiento del agua en cada ciclo:
     * Si está rodeada completamente de tierra intenta moverse
     * una celda hacia el sur. La celda origen se convierte en
     * Ground. Si la celda sur no existe o no es Ground
     * el agua permanece en su lugar.
     */
    public void ticTac() {
        if (surroundedByGround()) {
            int newRow = row + 1;
            if (newRow < forest.getSize()
                    && forest.getThing(newRow, column) instanceof Ground) {
                forest.setThing(row, column, null);
                new Ground(forest, row, column);
                row = newRow;
                forest.setThing(row, column, this);
            }
        }
    }

    /**
     * Elimina el agua del bosque liberando su celda actual.
     */
    public void die() {
        forest.setThing(row, column, null);
    }
}