package domain;
import java.awt.Color;
import java.io.Serializable;

/**
 * Interfaz que define el contrato de cualquier objeto que pueda
 * existir en el bosque (Forest). Todo Thing puede actuar (ticTac),
 * tiene una forma, un color y puede indicar si es un ser vivo.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public interface Thing extends Serializable {

    public static final int SQUARE = 2;

    public static final int ROUND = 1;

    /**
     * Define el comportamiento del objeto en cada ciclo del simulador.
     * Toda clase concreta que implemente Thing debe proveer su propia
     * implementación de este método.
     */
    public void ticTac();

    /**
     * Retorna la forma del objeto.
     * Por defecto retorna SQUARE.
     * @return SQUARE o ROUND
     */
    public default int shape() {
        return SQUARE;
    }

    /**
     * Retorna el color con el que se pinta el objeto en la GUI.
     * Por defecto retorna Color.black.
     * @return Color del objeto
     */
    public default Color getColor() {
        return Color.black;
    }

    /**
     * Indica si el objeto es el único en su celda.
     * Por defecto retorna true.
     * @return true si es el único Thing en su celda
     */
    public default boolean isOnlyThing() {
        return true;
    }

    /**
     * Indica si el objeto es un ser vivo (LivingThing).
     * Por defecto retorna false.
     * @return true si el objeto extiende LivingThing
     */
    public default boolean isLivingThing() {
        return false;
    }
}