
package de.doubleslash.workshops.oodesign.atm;

/**
 * Hardwaremodul für die Bargeld-Ausgabe.
 */
public class MoneyDispenser {

    /**
     * Veranlasst die Ausgabe des Bargelds.
     *
     * @param amount der abgehobene Betrag der ausgegeben werden soll.
     */
    public void dispenseCash(double amount) {
        System.out.printf("Bargeld-Ausgabe in Höhe von %.2f.\n", amount);
    }

}
