package de.doubleslash.workshops.oodesign.log;

/**
 * Interface für Logging. Entkoppelt die nutzenden Klassen von der konkreten {@link AuditLog}-Implementierung.
 */
public interface Log {

    enum LogLevel {
        INFO, WARN, ERROR;

        @Override
        public String toString() {
            return "[" + name() + "]";
        }
    }

    void info(String message, Object... messageArgs);

    void warn(String message, Object... messageArgs);

    void error(String message, Object... messageArgs);

}
