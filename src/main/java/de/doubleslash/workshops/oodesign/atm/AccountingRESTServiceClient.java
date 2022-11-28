package de.doubleslash.workshops.oodesign.atm;


import de.doubleslash.workshops.oodesign.log.Log;

/**
 * Konto-Service zum Verbuchen der Abhebungen.
 */
public class AccountingRESTServiceClient implements AccountingService {

    private final Log log;

    /**
     * Konstruktor. Erzeugt die {@link AccountingRESTServiceClient}-Instanz.
     */
    public AccountingRESTServiceClient(Log log) {
        this.log = log;
        log.info("AccountingRESTServiceClient-Instanz wird erzeugt.");
    }

    /**
     * Verbucht eine Geldabhebung auf dem Kundenkonto.
     *
     * @param amount der abgehobene Betrag.
     * @param bankAccountNumber die Kontonummer des Kunden.
     *
     * @return {@code true} wenn die Verbuchung erfolgreich war, andernfalls {@code false}.
     */
    @Override
    public boolean withdrawAmount(double amount, int bankAccountNumber) {
        log.info("AccountingRESTServiceClient: Verbuche Auszahlung von Betrag %s auf Kontonummer %s.", amount, bankAccountNumber);
        return true;
    }

}
