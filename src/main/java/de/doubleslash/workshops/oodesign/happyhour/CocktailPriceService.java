package de.doubleslash.workshops.oodesign.happyhour;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Dienst der die Preise für Cocktails liefert.
 * <p>
 * Die zurückgegebenen Preise sind abhängig von der aktuellen Uhrzeit, d.h. ob gerade Happy Hour ist oder nicht!
 */
public class CocktailPriceService {

    private static final LocalTime HAPPY_HOUR_START = LocalTime.of(18, 0);

    private final Map<String, Double> standardPrices;
    private final Map<String, Double> happyHourPrices;
    private final TimeProvider timeProvider;


    /**
     * Konstruktor. Erzeugt eine neue {@link CocktailPriceService}-Instanz.
     */
    public CocktailPriceService(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        this.standardPrices = standardPrices();
        this.happyHourPrices = happyHourPrices();
    }

    /**
     * Liefert die Cocktail-Preise, abhängig davon ob Happy Hour ist oder nicht.
     *
     * @return eine {@link Map} mit den aktuell gültigen Cocktail-Preisen.
     */
    public Map<String, Double> getPrices() {
        LocalTime currentTime = timeProvider.getCurrentTime();
        if (currentTime.isBefore(HAPPY_HOUR_START)) {
            return standardPrices;
        } else {
            return happyHourPrices;
        }
    }

    private Map<String, Double> happyHourPrices() {
        Map<String, Double> map = new HashMap<>();
        map.put("Piña Colada", 5.0);
        map.put("Caipirinha", 5.0);
        map.put("Mockito", 5.0);
        map.put("Mai Tai", 5.5);
        map.put("Planter's Punch", 5.5);

        return Collections.unmodifiableMap(map);
    }

    private Map<String, Double> standardPrices() {
        Map<String, Double> map = new HashMap<>();
        map.put("Piña Colada", 7.2);
        map.put("Caipirinha", 7.2);
        map.put("Mockito", 7.2);
        map.put("Mai Tai", 7.4);
        map.put("Planter's Punch", 7.4);

        return Collections.unmodifiableMap(map);
    }

}
