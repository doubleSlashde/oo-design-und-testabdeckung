package de.doubleslash.workshops.oodesign.atm;

import de.doubleslash.workshops.oodesign.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Log}-Implementierung für Testzwecke. Speichert geloggte INFO-Nachrichten und stellt diese dem Test zur Auswertung bereit.
 */
public class TestLog implements Log {

    List<String> infoMessages = new ArrayList<>();

    @Override
    public void info(String message, Object... messageArgs) {
        infoMessages.add(String.format(message, messageArgs));
    }

    @Override
    public void warn(String message, Object... messageArgs) {

    }

    @Override
    public void error(String message, Object... messageArgs) {

    }

    public List<String> getInfoMessages() {
        return infoMessages;
    }

}
