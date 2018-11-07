package de.doubleslash.workshops.oodesign.atm;

import de.doubleslash.workshops.oodesign.log.Log;

/**
 * Geldautomat (ATM = Automatic Teller Machine).
 */
public class ATM {

    // Konto-Service zum Verbuchen der Abhebungen
    private final AccountingRESTServiceClient accountingService;

    // Kartenleser-Modul des Automats
    private final CardReader cardReader;

    // Geldausgabe-Modul des Automats
    private final MoneyDispenser moneyDispenser;

    /**
     * Konstruktor. Erzeugt eine neue {@link ATM}-Instanz.
     */
    public ATM() {
        this.cardReader = new CardReader();
        this.accountingService = AccountingRESTServiceClient.getInstance();
        this.moneyDispenser = new MoneyDispenser();
    }

    /**
     * Funktion zum Abheben von Bargeld.
     *
     * @param pin die PIN, die vom Kunden eingegeben wurde.
     * @param amount der vom Kunden gewünschte Betrag.
     */
    public void withdrawMoney(int pin, double amount) {

        boolean pinCorrect = cardReader.verifyPin(pin);

        if (pinCorrect) {
            try {
                // Kontonummer aus der Bankkarte auslesen
                int accountNumber = cardReader.readAccountNumber();
                // Verbuchen der Abhebung
                boolean success = accountingService.withdrawAmount(amount, accountNumber);

                if (success) {
                    // Ausgabe des Geldes
                    moneyDispenser.dispenseCash(amount);
                } else {
                    Log.error("Abbruch wegen Fehler beim Verbuchen der Transaktion");
                    // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
                }
            } catch (CardReaderException e) {
                Log.error("Fehler beim Lesen der Karte: " + e.getMessage());
                // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
            }
        } else {
            Log.error("PIN ist inkorrekt.");
            // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
        }
    }

}
