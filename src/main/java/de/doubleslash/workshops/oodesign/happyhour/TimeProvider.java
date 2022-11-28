package de.doubleslash.workshops.oodesign.happyhour;

import java.time.LocalTime;

/**
 * Interface zur Bereitstellung der aktuellen Zeit. Entkoppelt den {@link CocktailPriceService}
 * von der Abhängigkeit "Zeit", so dass dessen Verhalten zu unterschiedlichen Uhrzeiten getestet
 * werden kann.
 */
public interface TimeProvider {

    /**
     * Gibt die aktuelle Zeit zurück.
     *
     * @return die aktuelle Uhrzeit.
     */
    LocalTime getCurrentTime();

}
