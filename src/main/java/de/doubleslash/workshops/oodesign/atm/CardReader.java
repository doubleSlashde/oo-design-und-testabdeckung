package de.doubleslash.workshops.oodesign.atm;

import java.util.Random;

/**
 * Hardware-Modul zum Lesen der Bankkarte des Kunden.
 */
public class CardReader {

    private final Random random = new Random();

    /**
     * Prüfung, ob die PIN auf der Karte mit der vom Kunden eingegebenen übereinstimmt.
     * @param pin die vom Kunden eingegebene PIN.
     *
     * @return {@code true} wenn die PIN vom Kunden korrekt eingegeben wurde, andernfalls {@code false}.
     */
    public boolean verifyPin(int pin) {
        // simuliert die Prüfung der PIN
        return verifyPinInternal();
    }

    /**
     * Auslesen der Kontonummer aus der Bankkarte des Kunden.
     *
     * @return die ausgelesene Kontonummer.
     *
     * @throws CardReaderException wenn das Auslesen fehlschlägt.
     */
    public int readAccountNumber() throws CardReaderException {
        // Simuliert die Tatsache, dass für jede Karte eine andere Kontonummer ausgelesen wird
        // (Rückgabe einer Zufallszahl zwischen 0 und 99.999)
        return Math.abs(random.nextInt(100_000));
    }

    private boolean verifyPinInternal() {
        // Simuliert die Prüfung mit korrekter bzw. falscher Eingabe der PIN (80% korrekt, 20% inkorrekt)
        int i = random.nextInt(5);

        return (i != 0);
    }

}
