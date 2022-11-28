package de.doubleslash.workshops.oodesign.atm;

/**
 * Geldautomat (ATM = Automatic Teller Machine).
 */
public class ATM {

    // Konto-Service zum Verbuchen der Abhebungen
    private final AccountingService accountingService;

    // Kartenleser-Modul des Automats
    private final CardReader cardReader;

    // Geldausgabe-Modul des Automats
    private final MoneyDispenser moneyDispenser;

    /**
     * Konstruktor. Erzeugt eine neue {@link ATM}-Instanz.
     */

    public ATM(CardReader cardReader, AccountingService accountingService, MoneyDispenser moneyDispenser) {
        this.cardReader = cardReader;
        this.accountingService = accountingService;
        this.moneyDispenser = moneyDispenser;
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
                    System.out.println("Abbruch wegen Fehler beim Verbuchen der Transaktion");
                    // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
                }
            } catch (CardReaderException e) {
                System.out.println("Fehler beim Lesen der Karte: " + e.getMessage());
                // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
            }
        } else {
            System.out.println("Fehler: PIN ist inkorrekt.");
            // ... Anzeige einer entsprechenden Fehlermeldung für den Kunden ...
        }
    }

}
