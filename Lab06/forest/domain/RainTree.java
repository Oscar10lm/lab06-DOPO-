package domain;
import java.awt.Color;

/**
 * Representa un árbol de lluvia en el bosque. Es un tipo especial
 * de árbol que tiene color azul que se intensifica con el tiempo,
 * vive el doble que un árbol normal (energía 200), riega sus vecinos
 * cada dos ciclos generando nuevos árboles en celdas vacías adyacentes,
 * y se pinta como un cuadrado para diferenciarse visualmente del Tree normal.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public class RainTree extends Tree {

    /** Contador interno de ciclos propio de RainTree. */
    private int tictac;

    /**
     * Crea un nuevo RainTree en la posición (row, column) del bosque.
     * Inicia con color azul claro, energía 100 y tictac en 0.
     * Se registra automáticamente en el bosque al crearse.
     * @param forest bosque al que pertenece
     * @param row    fila de la posición inicial
     * @param column columna de la posición inicial
     */
    public RainTree(Forest forest, int row, int column) {
        super(forest, row, column);
        this.tictac = 0;
        this.color  = new Color(173, 216, 230);
    }

    /**
     * Retorna la forma del RainTree — cuadrada, a diferencia
     * del Tree normal que es redondo.
     * @return Thing.SQUARE
     */
    @Override
    public final int shape() {
        return Thing.SQUARE;
    }

    /**
     * Retorna el color actual del RainTree — azul que se intensifica
     * progresivamente con cada par de ciclos.
     * @return color azul actual
     */
    @Override
    public final Color getColor() {
        return color;
    }

    /**
     * Actualiza el color del RainTree intensificando el azul cada
     * dos ciclos. Los componentes R y G disminuyen progresivamente
     * mientras B se mantiene constante en 230.
     * El máximo de intensidad se alcanza en 20 pasos.
     */
    private void updateColor() {
        int intensity = Math.min(tictac / 2, 20);
        int r = Math.max(173 - intensity * 8, 13);
        int g = Math.max(216 - intensity * 8, 56);
        int b = 230;
        color = new Color(r, g, b);
    }

    /**
     * Intenta regar un vecino generando un nuevo Tree básico en la
     * primera celda vacía adyacente encontrada en orden norte, este,
     * sur, oeste. Solo nace un árbol por llamada.
     */
    private void water() {
        int[] drs = {-1,  0, 1,  0};
        int[] dcs = { 0,  1, 0, -1};
        for (int i = 0; i < drs.length; i++) {
            int nr = row + drs[i];
            int nc = column + dcs[i];
            if (forest.isEmpty(nr, nc)) {
                new Tree(forest, nr, nc);
                return;
            }
        }
    }

    /**
     * Ejecuta el comportamiento del RainTree en cada ciclo:
     * 1. Incrementa tictac y actualiza el color azul.
     * 2. Consume energía via step() — muere si se agota.
     * 3. Cada 2 ciclos riega una celda vacía adyacente.
     * 4. Cada 4 ciclos (tictac%4==1) incrementa los años.
     */
    @Override
    public void ticTac() {
        tictac++;
        updateColor();
        boolean ok = step();
        if (!ok) {
            die();
            return;
        }
        if (tictac % 2 == 0) {
            water();
        }
        if (tictac % 4 == 1) {
            years += 1;
        }
    }
}