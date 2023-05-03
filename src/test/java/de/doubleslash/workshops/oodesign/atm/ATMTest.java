package de.doubleslash.workshops.oodesign.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;

/**
 * Testet die Klasse ATM.
 */
@ExtendWith(MockitoExtension.class)
class ATMTest {

    @Mock
    private CardReader cardReaderMock;

    @Mock
    private AccountingService accountingServiceMock;

    @Mock
    private MoneyDispenser moneyDispenserMock;

    // Instanz der zu testenden Klasse
    private ATM testee;

    @BeforeEach
    void setUp() {
        testee = new ATM(cardReaderMock, accountingServiceMock, moneyDispenserMock);
    }

    @Test
    void accountingServiceShouldBeCalledWithCorrectAmountAndAccountNumberWhenPinIsCorrect() throws CardReaderException {
        // arrange
        // Kontonummer bestimmen, die vom CardReader gelesen wird
        Mockito.when(cardReaderMock.readAccountNumber()).thenReturn(4711);

        // simulieren dass die PIN vom Kunden korrekt eingegeben wurde
        Mockito.when(cardReaderMock.verifyPin(1234)).thenReturn(true);

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // prüfen ob der AccountingService mit korrektem Betrag u. Kontonummer aufgerufen wurde
        Mockito.verify(accountingServiceMock).withdrawAmount(100.0, 4711);
    }

    @Test
    void moneyShouldBeDispensedWhenPinIsCorrect() {
        // arrange
        // simulieren dass die PIN vom Kunden korrekt eingegeben wurde
        Mockito.when(cardReaderMock.verifyPin(1234)).thenReturn(true);
        // simulieren dass die Verbuchung via account service erfolgreich war
        Mockito.when(accountingServiceMock.withdrawAmount(anyDouble(), anyInt())).thenReturn(true);

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // prüfen dass der die dispenseCash-Methode vom MoneyDispenser mit korrektem Betrag aufgerufen wurde
        Mockito.verify(moneyDispenserMock).dispenseCash(100.0);
    }

    @Test
    void accountingServiceShouldNotBeCalledWhenCardCannotBeRead() throws CardReaderException {
        // arrange
        // simulieren dass die PIN vom Kunden korrekt eingegeben wurde
        Mockito.when(cardReaderMock.verifyPin(1234)).thenReturn(true);
        // simulieren dass eine CardReaderException beim Aufruf von readAccountNumber() geworfen wird
        Mockito.when(cardReaderMock.readAccountNumber()).thenThrow(new CardReaderException());

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // prüfen dass der AccountingService NICHT aufgerufen wurde
        // withdrawAmount(...) soll überhaupt nicht aufgerufen werden, egal mit welchen Parametern. Daher anyDouble() und anyInt().
        // würden beim Verify konkrete Werte angegeben werden, würde nur geprüft werden,
        // dass die Methode nicht mit diesen Werten aufgerufen wurde
        Mockito.verify(accountingServiceMock, never()).withdrawAmount(anyDouble(), anyInt());
    }

    @Test
    void accountingServiceShouldNotBeCalledWhenPinIsIncorrect() {
        // arrange
        // simulieren der Eingabe einer falschen PIN
        Mockito.when(cardReaderMock.verifyPin(anyInt())).thenReturn(false);

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // prüfen dass der AccountingService NICHT aufgerufen wurde (egal mit welchen Parametern)
        Mockito.verify(accountingServiceMock, never()).withdrawAmount(anyDouble(), anyInt());
    }

    @Test
    void moneyShouldNotBeDispensedWhenPinIsIncorrect() {
        // arrange
        // simulieren der Eingabe einer falschen PIN
        Mockito.when(cardReaderMock.verifyPin(anyInt())).thenReturn(false);

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        // prüfen dass der MoneyDispenser NICHT aufgerufen wurde (egal mit welchem Parameter)
        Mockito.verify(moneyDispenserMock, never()).dispenseCash(anyDouble());
    }

    @Test
    void moneyShouldNotBeDispensedWhenTransactionWasNotBooked() {
        // arrange
        // simulieren dass die PIN vom Kunden korrekt eingegeben wurde
        Mockito.when(cardReaderMock.verifyPin(1234)).thenReturn(true);

        // simulieren dass die Verbuchung via AccountingService nicht erfolgreich war
        Mockito.when(accountingServiceMock.withdrawAmount(anyDouble(), anyInt())).thenReturn(false);

        // act
        testee.withdrawMoney(1234, 100.0);

        // assert
        //prüfen dass der MoneyDispenser NICHT aufgerufen wurde (egal mit welchem Parameter)
        Mockito.verify(moneyDispenserMock, never()).dispenseCash(anyDouble());
    }

}
