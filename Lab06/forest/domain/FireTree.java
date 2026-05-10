package domain;
import java.awt.Color;

/**
 * Representa un árbol en el simulador Forest Fire. Es un ser vivo
 * que interactúa con el fuego, el agua y la tierra siguiendo reglas
 * de prioridad estrictas. Su color verde se oscurece a medida que
 * pierde energía. Se pinta como un óvalo en la GUI.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class FireTree extends LivingThing implements Thing {

    /** Bosque al que pertenece el árbol. */
    private Forest forest;

    /** Posición del árbol en la cuadrícula. */
    private int row, column;

    /**
     * Crea un nuevo FireTree en la posición (row, column) del bosque.
     * Inicia con energía 100 y color verde brillante.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public FireTree(Forest forest, int row, int column) {
        super(100);
        this.forest = forest;
        this.row    = row;
        this.column = column;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color del árbol — verde que se oscurece
     * progresivamente a medida que pierde energía.
     * Con energía 100 es verde brillante, con energía 0 es
     * verde muy oscuro.
     * @return Color verde proporcional a la energía actual
     */
    public Color getColor() {
        int g = (int)(60 + (getEnergy() / 100.0) * 140);
        return new Color(30, g, 30);
    }

    /**
     * Retorna la forma del árbol — redonda (óvalo en la GUI).
     * @return Thing.ROUND
     */
    public int shape() { return Thing.ROUND; }

    /**
     * Verifica si hay algún fuego en el vecindario de Moore
     * (8 vecinos) del árbol.
     * @return true si al menos un vecino es Fire
     */
    private boolean hasFireNeighbor() {
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                if (forest.getThing(row + dr, column + dc) instanceof Fire)
                    return true;
            }
        return false;
    }

    /**
     * Busca una fuente de agua en el vecindario de Moore
     * (8 vecinos) del árbol.
     * @return el primer Water encontrado o null si no hay ninguno
     */
    private Water findWaterNeighbor() {
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Thing t = forest.getThing(row + dr, column + dc);
                if (t instanceof Water) return (Water) t;
            }
        return null;
    }

    /**
     * Verifica si el árbol está completamente rodeado de tierra
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
     * Genera un nuevo FireTree en la primera celda vecina que
     * sea Ground. La celda de tierra es reemplazada por el
     * nuevo árbol. Solo nace un árbol por llamada.
     */
    private void spawnNeighborTree() {
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = column + dc;
                Thing t = forest.getThing(nr, nc);
                if (t instanceof Ground) {
                    forest.setThing(nr, nc, null);
                    new FireTree(forest, nr, nc);
                    return;
                }
            }
    }

    /**
     * Ejecuta el comportamiento del árbol en cada ciclo siguiendo
     * el orden estricto de prioridad de las reglas:
     * 1. Si hay fuego vecino pierde el 25% de su energía actual.
     * 2. Si su energía cae por debajo del 10% se convierte en Fire.
     * 3. Si hay agua vecina recupera el 100% de energía y el agua
     *    se convierte en tierra.
     * 4. Si está rodeado solo de tierra genera un nuevo árbol
     *    en una celda vecina de tierra.
     */
    public void ticTac() {
        if (hasFireNeighbor()) {
            int loss = (int)(getEnergy() * 0.25);
            for (int i = 0; i < loss; i++) step();
        }
        if (getEnergy() < 10) {
            forest.setThing(row, column, null);
            new Fire(forest, row, column);
            return;
        }
        Water w = findWaterNeighbor();
        if (w != null) {
            restore();
            forest.setThing(w.getRow(), w.getColumn(), null);
            new Ground(forest, w.getRow(), w.getColumn());
            return;
        }
        if (surroundedByGround()) {
            spawnNeighborTree();
        }
    }

    /**
     * Retorna la fila actual del árbol.
     * @return fila
     */
    public int getRow() { return row; }

    /**
     * Retorna la columna actual del árbol.
     * @return columna
     */
    public int getColumn() { return column; }
}