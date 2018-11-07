package de.doubleslash.workshops.oodesign.atm;


import de.doubleslash.workshops.oodesign.log.Log;

/**
 * Konto-Service zum Verbuchen der Abhebungen.
 */
public class AccountingRESTServiceClient {

    /**
     * Die {@link AccountingRESTServiceClient} Singleton-Instanz.
     */
    private static AccountingRESTServiceClient instance;

    /**
     * Konstruktor. Erzeugt die {@link AccountingRESTServiceClient}-Instanz.
     */
    private AccountingRESTServiceClient() {
        Log.info("AccountingRESTServiceClient-Instanz wird erzeugt.");
    }

    /**
     * Liefert den {@link AccountingRESTServiceClient} zurück und stellt sicher, dass davon nur eine
     * einzige Instanz existiert (Singleton).
     *
     * @return die {@link AccountingRESTServiceClient}-Instanz.
     */
    public static synchronized AccountingRESTServiceClient getInstance() {
        Log.info("Aufruf von AccountingRESTServiceClient.getInstance()");
        if (instance == null) {
            instance = new AccountingRESTServiceClient();
        }
        return instance;
    }

    /**
     * Verbucht eine Geldabhebung auf dem Kundenkonto.
     *
     * @param amount der abgehobene Betrag.
     * @param bankAccountNumber die Kontonummer des Kunden.
     *
     * @return {@code true} wenn die Verbuchung erfolgreich war, andernfalls {@code false}.
     */
    public boolean withdrawAmount(double amount, int bankAccountNumber) {
        Log.info("AccountingRESTServiceClient: Verbuche Auszahlung von Betrag %s auf Kontonummer %s.", amount, bankAccountNumber);
        return true;
    }
    
}
