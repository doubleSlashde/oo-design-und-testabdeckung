package de.doubleslash.workshops.oodesign.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Testet die Klasse ATM.
 */
@ExtendWith(MockitoExtension.class)
class ATMTest {

    // Instanz der zu testenden Klasse
    private ATM testee;

    @BeforeEach
    void setUp() {
        testee = new ATM();
    }

    @Test
    void accountingServiceShouldBeCalledWithCorrectAmountAndAccountNumberWhenPinIsCorrect() {
        // arrange
        // ??? wie können wir die Kontonummer kennen bzw. bestimmen, die vom CardReader gelesen wird?
        // ??? wie können wir simulieren dass die PIN vom Kunden korrekt eingegeben wurde?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wurde der AccountingService mit korrektem Betrag u. Kontonummer aufgerufen? Wie können wir das prüfen?
        fail("Cannot test this!");
    }

    @Test
    void moneyShouldBeDispensedWhenPinIsCorrect() {
        // arrange
        // ??? wie können wir simulieren dass die PIN vom Kunden korrekt eingegeben wurde?
        // ??? wie können wir simulieren dass die Verbuchung via account service erfolgreich war?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wie können wir prüfen dass der die dispenseCash-Methode vom MoneyDispenser aufgerufen wurde?
        fail("Cannot test this!");
    }

    @Test
    void accountingServiceShouldNotBeCalledWhenCardCannotBeRead() {
        // arrange
        // ??? wie können wir simulieren dass die PIN vom Kunden korrekt eingegeben wurde?
        // ??? wie simulieren wir eine CardReaderException beim Aufruf von readAccountNumber() ?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wie prüfen wir dass der AccountingService NICHT aufgerufen wurde?
        fail("Cannot test this!");
    }

    @Test
    void accountingServiceShouldNotBeCalledWhenPinIsIncorrect() {
        // arrange
        // ??? wie simulieren wir die Eingabe einer falschen PIN?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wie prüfen wir dass der AccountingService NICHT aufgerufen wurde?
        fail("Cannot test this!");
    }

    @Test
    void moneyShouldNotBeDispensedWhenPinIsIncorrect() {
        // arrange
        // ??? wie simulieren wir die Eingabe einer falschen PIN?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wie prüfen wir dass der MoneyDispenser NICHT aufgerufen wurde?
        fail("Cannot test this!");
    }

    @Test
    void moneyShouldNotBeDispensedWhenTransactionWasNotBooked() {
        // arrange
        // ??? wie simulieren wir dass die Verbuchung via AccountingService nicht erfolgreich war?

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // ??? wie prüfen wir dass der MoneyDispenser NICHT aufgerufen wurde?
        fail("Cannot test this!");
    }

}
