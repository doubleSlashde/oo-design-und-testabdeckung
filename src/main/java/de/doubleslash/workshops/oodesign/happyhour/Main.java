package de.doubleslash.workshops.oodesign.happyhour;

import java.util.Map;

/**
 * Klasse mit main-Methode zum Starten des CocktailPriceService-Programms.
 */
public class Main {

    public static void main(String[] args) {
        CocktailPriceService cocktailPriceService = new CocktailPriceService();

        System.out.println("Aktuelle Cocktail-Preise: ");
        Map<String, Double> cocktailPrices = cocktailPriceService.getPrices();
        for (Map.Entry entry : cocktailPrices.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
