package de.doubleslash.workshops.oodesign.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountingRESTServiceClientTest {

    private TestLog testLog;

    private AccountingService testee;

    @BeforeEach
    public void setUp() {
        testLog = new TestLog();
        testee = new AccountingRESTServiceClient(testLog);
    }

    @Test
    void accountingServiceShouldLogTransaction() {
        // arrange (nothing to do)

        // act
        testee.withdrawAmount(100.00, 543_210);

        // assert
        String expectedLogMessage = "AccountingRESTServiceClient: Verbuche Auszahlung von Betrag 100.0 auf Kontonummer 543210.";
        // prüfen dass die Transaktion geloggt wurde
        List<String> loggedMessages = testLog.getInfoMessages();
        assertThat(loggedMessages).contains(expectedLogMessage);
    }

}
