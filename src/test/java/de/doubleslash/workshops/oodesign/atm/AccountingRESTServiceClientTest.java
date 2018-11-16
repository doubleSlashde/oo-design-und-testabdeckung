package de.doubleslash.workshops.oodesign.atm;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

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
        String expectedLogMessage = "AccountingRESTServiceClient: Verbuche Auszahlung von Betrag 100.0 auf Kontonummer 543210.";
        // ??? wie prüfen wir dass die Transaktion geloggt wurde?
        List<String> loggedMessages = Collections.emptyList(); // => woher bekommen wir die vom AccountingRESTSerive geloggten Nachrichten?
        assertThat(loggedMessages, hasItem(expectedLogMessage));
    }

}
