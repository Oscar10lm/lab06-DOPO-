package domain;
import java.awt.Color;

/**
 * Representa una ardilla en el bosque. Es un ser vivo que se mueve
 * aleatoriamente, envejece cambiando de color café a amarillo, se
 * reproduce cuando hay dos ardillas con una celda vacía entre ellas,
 * y muere a los 10 años o al agotar su energía.
 * Se pinta como un cuadrado en la GUI.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class Squirrel extends LivingThing implements Thing {

    /** Bosque al que pertenece la ardilla. */
    private Forest forest;

    /** Posición actual de la ardilla en la cuadrícula. */
    protected int row, column;

    /** Color actual de la ardilla — interpola de café a amarillo. */
    protected Color color;

    /**
     * Crea una nueva ardilla en la posición (row, column) del bosque.
     * Inicia con color café, energía 100 y años 0.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public Squirrel(Forest forest, int row, int column) {
        this.forest = forest;
        this.row    = row;
        this.column = column;
        this.color  = new Color(139, 90, 43);
        this.forest.setThing(row, column, this);
    }

    /**
     * Retorna la fila actual de la ardilla.
     * @return fila
     */
    public final int getRow() { return row; }

    /**
     * Retorna la columna actual de la ardilla.
     * @return columna
     */
    public final int getColumn() { return column; }

    /**
     * Retorna el color actual de la ardilla según sus años.
     * @return color interpolado entre café y amarillo
     */
    public final Color getColor() { return color; }

    /**
     * Retorna la forma de la ardilla — cuadrada.
     * @return Thing.SQUARE
     */
    public final int shape() { return Thing.SQUARE; }

    /**
     * Actualiza el color de la ardilla interpolando linealmente
     * entre café (139,90,43) y amarillo (255,200,0) según los años
     * transcurridos. A los 10 años el color es completamente amarillo.
     */
    private void updateColor() {
        int maxYears = 10;
        float t = Math.min((float) years / maxYears, 1.0f);
        int r = (int)(139 + (255 - 139) * t);
        int g = (int)(90  + (200 - 90)  * t);
        int b = (int)(43  + (0   - 43)  * t);
        color = new Color(r, g, b);
    }

    /**
     * Intenta mover la ardilla a una celda adyacente libre elegida
     * aleatoriamente del vecindario de Moore (8 direcciones).
     * Si no hay celda libre disponible la ardilla permanece en su lugar.
     */
    private void move() {
        int[] drs = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dcs = {-1,  0,  1, -1, 1, -1, 0, 1};
        for (int i = drs.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            int tmp = drs[i]; drs[i] = drs[j]; drs[j] = tmp;
                tmp = dcs[i]; dcs[i] = dcs[j]; dcs[j] = tmp;
        }
        for (int i = 0; i < drs.length; i++) {
            int nr = row + drs[i];
            int nc = column + dcs[i];
            if (forest.isEmpty(nr, nc)) {
                forest.setThing(row, column, null);
                row    = nr;
                column = nc;
                forest.setThing(row, column, this);
                return;
            }
        }
    }

    /**
     * Verifica si existe una ardilla vecina con una celda vacía
     * entre ellas. Si se cumple la condición nace una nueva ardilla
     * en la celda vacía. Revisa pares opuestos en las cuatro
     * direcciones principales y diagonales.
     */
    private void reproduce() {
        int[][] pairs = {
            {-1, 0,  1, 0},
            { 0,-1,  0, 1},
            {-1,-1,  1, 1},
            {-1, 1,  1,-1}
        };
        for (int[] p : pairs) {
            int r1 = row + p[0], c1 = column + p[1];
            int r2 = row + p[2], c2 = column + p[3];
            if (forest.getThing(r1, c1) instanceof Squirrel
                    && forest.isEmpty(r2, c2)) {
                new Squirrel(forest, r2, c2);
                return;
            }
            if (forest.getThing(r2, c2) instanceof Squirrel
                    && forest.isEmpty(r1, c1)) {
                new Squirrel(forest, r1, c1);
                return;
            }
        }
    }

    /**
     * Elimina la ardilla del bosque liberando su celda actual.
     */
    public void die() {
        forest.setThing(row, column, null);
    }

    /**
     * Ejecuta el comportamiento de la ardilla en cada ciclo:
     * 1. Consume energía y envejece actualizando el color.
     * 2. Si llegó a 10 años o sin energía muere.
     * 3. Intenta reproducirse si hay condición.
     * 4. Se mueve aleatoriamente a una celda libre.
     */
    public void ticTac() {
        boolean ok = step();
        years++;
        updateColor();
        if (years >= 10 || !ok) {
            die();
            return;
        }
        reproduce();
        move();
    }
}