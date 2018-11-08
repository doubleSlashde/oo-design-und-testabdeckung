package de.doubleslash.workshops.oodesign.happyhour;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

/**
 * Testet den {@link CocktailPriceService}.
 *
 * Problem: abhängig von der aktuellen Uhrzeit können jeweils nur zwei der Tests erfolgreich sein, die anderen beiden schlagen fehl.
 */
public class CocktailPriceServiceTest {

    @Test
    public void testPricesOutsideHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(11, 42);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertEquals(prices.get("Caipirinha"), (Double) 7.2d);
        assertEquals(prices.get("Planter's Punch"), (Double) 7.4d);
    }

    @Test
    public void testPricesOneMinuteBeforeHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(17, 59);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertEquals(prices.get("Caipirinha"), (Double) 7.2d);
        assertEquals(prices.get("Planter's Punch"), (Double) 7.4d);
    }

    @Test
    public void testPricesAtBeginOfHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(18, 0);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha"), is(5.0d));
        assertThat(prices.get("Planter's Punch"), is(5.5d));
    }

    @Test
    public void testPricesWithinHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(19, 25);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha"), is(5.0d));
        assertThat(prices.get("Planter's Punch"), is(5.5d));
    }


    private CocktailPriceService priceServiceAtTime(int hour, int minute) {
        // ??? CocktailPriceService benutzt LocalTime.now() um die aktuelle Zeit festzustellen.
        // ??? Wie können wir die Abhängigkeit "Zeit" kontrollieren und dem CocktailPriceService eine bestimmte Zeit vorgaukeln?
        return new CocktailPriceService();
    }

}
