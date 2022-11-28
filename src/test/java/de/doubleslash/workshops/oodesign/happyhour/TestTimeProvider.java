package de.doubleslash.workshops.oodesign.happyhour;

import java.time.LocalTime;

/**
 * {@link TimeProvider}-Implementierung für Testzwecke. Liefert immer eine fixe Zeit zurück.
 */
public class TestTimeProvider implements TimeProvider {

    private final LocalTime time;

    /**
     * Konstruktor.
     * @param time die fixe Zeit, die zurückgegeben werden soll.
     */
    public TestTimeProvider(LocalTime time) {
        this.time = time;
    }

    /**
     * Gibt eine fixe, über den Konstruktor bestimmte Zeit zurück.
     *
     * @return die im Konstruktor übergebene Zeit.
     */
    @Override
    public LocalTime getCurrentTime() {
        return time;
    }

}
