package de.doubleslash.workshops.oodesign.atm;

/**
 * Klasse mit main-Methode zum Starten des Geldautomaten-Programms.
 */
public class Main {

    public static void main(String[] args) {
       new ATM().withdrawMoney(1234, 100.0);
    }
}
