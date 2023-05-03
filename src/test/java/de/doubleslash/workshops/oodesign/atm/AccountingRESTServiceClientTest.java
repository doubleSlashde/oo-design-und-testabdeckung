package de.doubleslash.workshops.oodesign.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountingRESTServiceClientTest {

    private AccountingRESTServiceClient testee;

    @BeforeEach
    public void setUp() {
        testee = AccountingRESTServiceClient.getInstance();
    }

    @Test
    void accountingServiceShouldLogTransaction() {
        // arrange
        // ??? Die Log-Methoden sind statisch. Wie schaffen wir es, den AccountingRESTSerive so aufzusetzen dass wir die
        //     von ihm geloggten Nachrichten im Nachhinein auswerten können?

        // act
        testee.withdrawAmount(100.00, 543_210);

        // assert
        String expectedLogMessage = "AccountingRESTServiceClient: Verbuche Auszahlung von Betrag 100.0 auf Kontonummer 543210.";
        // ??? wie prüfen wir dass die Transaktion geloggt wurde?
        List<String> loggedMessages = Collections.emptyList(); // => woher bekommen wir die vom AccountingRESTSerive geloggten Nachrichten?
        assertThat(loggedMessages).contains(expectedLogMessage);
    }

}
