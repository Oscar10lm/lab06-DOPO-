package domain;

import java.util.*;
import java.io.File;

/**
 * Clase principal del modelo Forest.
 * 
 * @author Oscar Lasso - Juan Diego Gaitan
 * @version 1.0
 */
public class Forest {
    static private int SIZE = 25;
    private Thing[][] places;

    /**
     * Constructor principal que inicializa el bosque y le agrega algunos elementos.
     */
    public Forest() {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someThings();
    }

    /**
     * Constructor alternativo
     */

    public Forest(boolean empty) {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                places[r][c] = null;
        if (!empty)
            someThings();
    }

    /**
     * Obtiene el tamaño del bosque.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene el elemento ubicado en una fila y columna específica.
     */
    public Thing getThing(int r, int c) {
        return places[r][c];
    }

    /**
     * Coloca un elemento en una posición específica del bosque.
     */
    public void setThing(int r, int c, Thing e) {
        places[r][c] = e;
    }

    /**
     * Pobla el bosque con elementos iniciales aleatorios (árboles, tierra, fuego,
     * agua).
     */
    public void someThings() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                double p = Math.random();
                if (p < 0.50)
                    new FireTree(this, r, c);
                else if (p < 0.70)
                    new Ground(this, r, c);
                else if (p < 0.85)
                    new Fire(this, r, c);
                else
                    new Water(this, r, c);
            }
        }

    }

    /**
     * Cuenta la cantidad de vecinos del mismo tipo alrededor de una posición.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inForest(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inForest(r + dr, c + dc) &&
                            (places[r + dr][c + dc] != null)
                            && (places[r][c].getClass() == places[r + dr][c + dc].getClass()))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Verifica si una posición dada dentro del bosque está vacía.
     */
    public boolean isEmpty(int r, int c) {
        return (inForest(r, c) && places[r][c] == null);
    }

    /**
     * Comprueba si las coordenadas dadas están dentro de los límites del bosque.
     */
    private boolean inForest(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Avanza el estado del bosque un instante de tiempo, actualizando todos sus
     * elementos.
     */
    public void ticTac() {
        Thing[][] snapshot = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                snapshot[r][c] = places[r][c];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (snapshot[r][c] != null) {
                    snapshot[r][c].ticTac();
                }
            }
        }

    }

    /**
     * Abre un bosque desde un archivo.
     */
    public void open00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_OPEN_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Abre un bosque desde un archivo.
     */
    public void open(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_OPEN_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Guarda el bosque actual en el archivo especificado (versión inicial).
     */
    public void saveAs00(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_SAVE_AS_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Guarda el bosque actual en el archivo especificado.
     */
    public void saveAs(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_SAVE_AS_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Importa la configuración de un bosque.
     */
    public void importFile(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_IMPORT_EN_CONSTRUCCION + file.getName());
    }

    /**
     * Exporta el estado del bosque a un formato específico.
     */
    public void exportAs(File file) throws ForestException {
        throw new ForestException(ForestException.OPCION_EXPORT_AS_EN_CONSTRUCCION + file.getName());
    }
}
