
package de.doubleslash.workshops.oodesign.atm;

import de.doubleslash.workshops.oodesign.log.Log;

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
        Log.info("Bargeld-Ausgabe in Höhe von %.2f.", amount);
    }

}
