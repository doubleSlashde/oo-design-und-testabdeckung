package de.doubleslash.workshops.oodesign.log;

/**
 * Einfache AuditLog-Implementierung, die die Lognachrichten auf die Konsole schreibt (den Loglevel vorangestellt).
 */
public class AuditLog implements Log {

    /**
     * Loggt eine Nachricht mit LogLevel INFO.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */
    @Override
    public void info(final String message, final Object... messageArgs) {
        log(LogLevel.INFO, message, messageArgs);
    }

    /**
     * Loggt eine Nachricht mit LogLevel WARN.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */

    @Override
    public void warn(final String message, final Object... messageArgs) {
        log(LogLevel.WARN, message, messageArgs);
    }

    /**
     * Loggt eine Nachricht mit LogLevel ERROR.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */

    @Override
    public void error(final String message, final Object... messageArgs) {
        log(LogLevel.ERROR, message, messageArgs);
    }

    private void log(final LogLevel logLevel, final String message, Object... messageArgs) {
        String formattedMessage = String.format(message, messageArgs);
        System.out.printf("%7s %s\n", logLevel, formattedMessage);
    }

}
