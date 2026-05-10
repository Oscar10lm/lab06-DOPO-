package domain;
import java.awt.Color;

/**
 * Clase abstracta que representa un ser vivo en el bosque.
 * Define el modelo de energía y envejecimiento compartido por
 * todos los seres vivos. Sus métodos finales garantizan que
 * ninguna subclase pueda alterar las reglas de vida y muerte.
 *
 * @author Lasso - Gaitan
 * @version 1.0
 */
public abstract class LivingThing {

    /** Años de vida del ser vivo. Accesible por subclases. */
    protected int years;

    /** Energía del ser vivo. Solo accesible mediante métodos. */
    private int energy;

    /**
     * Crea un nuevo LivingThing con energía inicial de 100
     * y años en 0.
     */
    public LivingThing() {
        this.energy = 100;
        this.years  = 0;
    }

    /**
     * Crea un nuevo LivingThing con energía inicial personalizada
     * y años en 0. Útil para seres vivos con ciclo de vida distinto
     * al estándar, como RainTree que inicia con 200 de energía.
     * @param initialEnergy energía inicial del ser vivo
     */
    public LivingThing(int initialEnergy) {
        this.energy = initialEnergy;
        this.years  = 0;
    }

    /**
     * Descuenta una unidad de energía si el ser vivo aún tiene.
     * Representa el costo de existir un ciclo más.
     * No puede ser sobreescrito — todas las subclases comparten
     * el mismo modelo de consumo de energía.
     * @return true si tenía energía y pudo dar el paso,
     *         false si la energía era 0 y debe morir
     */
    final boolean step() {
        boolean ok = false;
        if (energy >= 1) {
            energy -= 1;
            ok = true;
        }
        return ok;
    }

    /**
     * Restaura la energía del ser vivo al 100%.
     * Se usa cuando un árbol es alimentado por una fuente de agua.
     * No puede ser sobreescrito para garantizar consistencia.
     */
    public final void restore() {
        energy = 100;
    }

    /**
     * Retorna la energía actual del ser vivo.
     * No puede ser sobreescrito para evitar que subclases
     * falseen su estado de energía.
     * @return valor actual de energy
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * Indica que este objeto es un ser vivo.
     * No puede ser sobreescrito — todo LivingThing es siempre
     * un ser vivo sin excepción.
     * @return true siempre
     */
    public final boolean isLivingThing() {
        return true;
    }
}