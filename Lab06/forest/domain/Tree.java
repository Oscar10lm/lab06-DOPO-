package domain;
import java.awt.Color;

/**
 * Representa un árbol en el bosque. Es un ser vivo que cicla
 * por cuatro estaciones (primavera, verano, otoño, invierno)
 * cambiando de color en cada ciclo. En invierno consume energía
 * y muere si se agota. Se pinta como un óvalo en la GUI.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Tree extends LivingThing implements Thing {

    /** Bosque al que pertenece el árbol. */
    protected Forest forest;

    /** Posición del árbol en la cuadrícula. */
    protected int row, column;

    /** Color actual del árbol según la estación. */
    protected Color color;

    /** Estación actual: 0=primavera, 1=verano, 2=otoño, 3=invierno. */
    private int season;

    /** Contador interno de ciclos para determinar la estación. */
    private int tictac;

    /**
     * Crea un nuevo árbol en la posición (row, column) del bosque.
     * Inicia en primavera con color PINK, energía 100 y años 0.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public Tree(Forest forest, int row, int column) {
        this.forest  = forest;
        this.row     = row;
        this.column  = column;
        this.color   = Color.PINK;
        this.season  = 0;
        this.tictac  = 0;
        this.forest.setThing(row, column, (Thing) this);
    }

    /**
     * Retorna la fila actual del árbol.
     * @return fila
     */
    public final int getRow() {
        return row;
    }

    /**
     * Retorna la columna actual del árbol.
     * @return columna
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Retorna el color actual del árbol según su estación:
     * PINK=primavera, GREEN=verano, ORANGE=otoño, GRAY=invierno.
     * @return color actual
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retorna la forma del árbol — redonda (óvalo en la GUI).
     * @return Thing.ROUND
     */
    public int shape() {
        return Thing.ROUND;
    }

    /**
     * Ejecuta el comportamiento del árbol en cada ciclo:
     * Avanza una estación actualizando el color.
     * En verano (tictac%4==1) incrementa los años.
     * En invierno (tictac%4==3) consume energía via step().
     * Si step() retorna false la energía se agotó y muere.
     */
    public void ticTac() {
        tictac++;
        color = (tictac % 4 == 0 ? Color.PINK   :
                 tictac % 4 == 1 ? Color.GREEN   :
                 tictac % 4 == 2 ? Color.ORANGE  :
                                   Color.GRAY);
        if (tictac % 4 == 1) {
            years += 1;
        }
        if (tictac % 4 == 3) {
            boolean ok = step();
            if (!ok) {
                die();
            }
        }
    }

    /**
     * Elimina el árbol del bosque liberando su celda.
     * La celda queda null tras la muerte.
     */
    public void die() {
        forest.setThing(row, column, null);
    }
}