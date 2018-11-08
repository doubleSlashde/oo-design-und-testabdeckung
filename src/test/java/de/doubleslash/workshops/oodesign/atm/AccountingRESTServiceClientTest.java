package de.doubleslash.workshops.oodesign.atm;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class AccountingRESTServiceClientTest {

    private AccountingRESTServiceClient testee;

    @Before
    public void setUp() {
        testee = AccountingRESTServiceClient.getInstance();
    }

    @Test
    public void accountingServiceShouldLogTransaction() {
        // arrange
        // ??? Die Log-Methoden sind statisch. Wie schaffen wir es, den AccountingRESTSerive so aufzusetzen dass wir die
        //     von ihm geloggten Nachrichten im Nachhinein auswerten können?

        // act
        testee.withdrawAmount(100.00, 543_210);

        // assert
        // ??? wie prüfen wir dass die Transaktion geloggt wurde?
        fail("Cannot test this!");
    }

}
