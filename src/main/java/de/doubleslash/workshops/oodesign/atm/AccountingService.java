package de.doubleslash.workshops.oodesign.atm;

public interface AccountingService {
    boolean withdrawAmount(double amount, int accountNumber);
}
