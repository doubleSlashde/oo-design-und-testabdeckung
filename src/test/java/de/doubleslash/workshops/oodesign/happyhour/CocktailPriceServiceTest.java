package de.doubleslash.workshops.oodesign.happyhour;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testet den {@link CocktailPriceService}.
 * <p>
 * Problem: abhängig von der aktuellen Uhrzeit können jeweils nur zwei der Tests erfolgreich sein, die anderen beiden schlagen fehl.
 */
@ExtendWith(MockitoExtension.class)
class CocktailPriceServiceTest {

    @Test
    void testPricesOutsideHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(11, 42);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha")).isEqualTo(7.2d);
        assertThat(prices.get("Planter's Punch")).isEqualTo(7.4d);
    }

    @Test
    void testPricesOneMinuteBeforeHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(17, 59);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha")).isEqualTo(7.2d);
        assertThat(prices.get("Planter's Punch")).isEqualTo(7.4d);
    }

    @Test
    void testPricesAtBeginOfHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(18, 0);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha")).isEqualTo(5.0d);
        assertThat(prices.get("Planter's Punch")).isEqualTo(5.5d);
    }

    @Test
    void testPricesWithinHappyHour() {
        // arrange
        CocktailPriceService cocktailPriceService = priceServiceAtTime(19, 25);

        // act
        Map<String, Double> prices = cocktailPriceService.getPrices();

        // assert
        assertThat(prices.get("Caipirinha")).isEqualTo(5.0d);
        assertThat(prices.get("Planter's Punch")).isEqualTo(5.0d);
    }

    private CocktailPriceService priceServiceAtTime(int hour, int minute) {
        // ??? CocktailPriceService benutzt LocalTime.now() um die aktuelle Zeit festzustellen.
        // ??? Wie können wir die Abhängigkeit "Zeit" kontrollieren und dem CocktailPriceService eine bestimmte Zeit vorgaukeln?
        return new CocktailPriceService();
    }

}
