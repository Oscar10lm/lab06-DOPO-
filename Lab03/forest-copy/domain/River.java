package domain;
import java.awt.Color;

/**
 * Representa un río en el bosque. Es un objeto que no es un ser vivo
 * y se desplaza horizontalmente hacia la derecha de forma circular
 * en cada ciclo. Su color azul varía entre claro y oscuro cíclicamente
 * simulando el flujo del agua. Se pinta como un cuadrado en la GUI.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class River implements Thing {

    /** Bosque al que pertenece el río. */
    private Forest forest;

    /** Posición actual del río en la cuadrícula. */
    private int row;
    private int column;

    /** Contador interno de ciclos para alternar el color. */
    private int tictac;

    /**
     * Paleta de colores azules que el río alterna cíclicamente,
     * simulando el movimiento y profundidad del agua.
     */
    private static final Color[] BLUES = {
        new Color(173, 216, 230),
        new Color(100, 149, 237),
        new Color(55,  100, 180),
        new Color(25,   60, 130),
    };

    /**
     * Crea un nuevo río en la posición (row, column) del bosque.
     * Inicia con el color azul más claro y tictac en 0.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public River(Forest forest, int row, int column) {
        this.forest  = forest;
        this.row     = row;
        this.column  = column;
        this.tictac  = 0;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color actual del río alternando entre los cuatro
     * tonos azules según el ciclo actual.
     * @return color azul correspondiente al tictac actual
     */
    public Color getColor() {
        return BLUES[tictac % BLUES.length];
    }

    /**
     * Retorna la forma del río — cuadrada.
     * @return Thing.SQUARE
     */
    public int shape() {
        return Thing.SQUARE;
    }

    /**
     * Indica que el río no es un ser vivo.
     * @return false
     */
    public boolean isLivingThing() {
        return false;
    }

    /**
     * Ejecuta el comportamiento del río en cada ciclo:
     * 1. Actualiza el color avanzando el contador tictac.
     * 2. Libera su celda actual.
     * 3. Se desplaza una celda hacia la derecha circularmente.
     * 4. Si la celda destino está ocupada busca la siguiente
     *    celda libre en la misma fila. Si no encuentra ninguna
     *    el río desaparece temporalmente.
     */
    public void ticTac() {
        tictac++;
        forest.setThing(row, column, null);
        column = (column + 1) % forest.getSize();
        int intentos = 0;
        while (!forest.isEmpty(row, column)
               && intentos < forest.getSize()) {
            column = (column + 1) % forest.getSize();
            intentos++;
        }
        if (forest.isEmpty(row, column)) {
            forest.setThing(row, column, this);
        }
    }

    /**
     * Elimina el río del bosque liberando su celda actual.
     */
    public void die() {
        forest.setThing(row, column, null);
    }

    /**
     * Retorna la fila actual del río.
     * @return fila
     */
    public int getRow() { return row; }

    /**
     * Retorna la columna actual del río.
     * @return columna
     */
    public int getColumn() { return column; }
}