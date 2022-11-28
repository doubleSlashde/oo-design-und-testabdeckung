package de.doubleslash.workshops.oodesign.atm;

import de.doubleslash.workshops.oodesign.log.AuditLog;

/**
 * Klasse mit main-Methode zum Starten des Geldautomaten-Programms.
 */
public class Main {

    public static void main(String[] args) {
        CardReader cardReader = new CardReader();
        AccountingService accountingService = new AccountingRESTServiceClient(new AuditLog());
        final MoneyDispenser moneyDispenser = new MoneyDispenser();

        new ATM(cardReader, accountingService, moneyDispenser).withdrawMoney(1234, 100.0);
    }
}
