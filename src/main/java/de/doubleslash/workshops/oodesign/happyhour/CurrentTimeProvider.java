package de.doubleslash.workshops.oodesign.happyhour;

import java.time.LocalTime;

/**
 * {@link TimeProvider}-Implementierung zur Verwendung in der Anwendungslogik. Gibt immer die
 * jeweils aktuelle Zeit zurück.
 */
public class CurrentTimeProvider implements TimeProvider {

    @Override
    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

}
