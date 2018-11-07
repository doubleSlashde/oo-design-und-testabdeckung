package de.doubleslash.workshops.oodesign.log;

/**
 * Einfache Log-Implementierung, die die Lognachrichten auf die Konsole schreibt (den Loglevel vorangestellt).
 */
public class Log {

    private enum LogLevel {
        INFO, WARN, ERROR;

        @Override
        public String toString() {
            return "[" + name() + "]";
        }
    }

    /**
     * Loggt eine Nachricht mit LogLevel INFO.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */
    public static void info(final String message, final Object... messageArgs) {
        log(LogLevel.INFO, message, messageArgs);
    }

    /**
     * Loggt eine Nachricht mit LogLevel WARN.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */

    private static void warn(final String message, final Object... messageArgs) {
        log(LogLevel.WARN, message, messageArgs);
    }

    /**
     * Loggt eine Nachricht mit LogLevel ERROR.
     *
     * @param message die Nachricht.
     * @param messageArgs Liste von Objekten, die in die Nachricht hineinformatiert werden.
     */

    public static void error(final String message, final Object... messageArgs) {
        log(LogLevel.ERROR, message, messageArgs);
    }

    private static void log(final LogLevel logLevel, final String message, Object... messageArgs) {
        String formattedMessage = String.format(message, messageArgs);
        System.out.printf("%7s %s\n", logLevel, formattedMessage);
    }

}
