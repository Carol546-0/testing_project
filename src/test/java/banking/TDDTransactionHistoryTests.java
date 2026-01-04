package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TDDTransactionHistoryTests {

    @Test
    void verifiedAccountCanViewTransactionHistory() {
        Account acc = new Account(200, "Verified");
        acc.deposit(50);

        assertDoesNotThrow(acc::getSecureTransactionHistory);
        assertFalse(acc.getSecureTransactionHistory().isEmpty());
    }

    @Test
    void suspendedAccountCanViewTransactionHistory() {
        Account acc = new Account(200, "Suspended");

        assertDoesNotThrow(acc::getSecureTransactionHistory);
    }

    @Test
    void closedAccountCannotViewTransactionHistory() {
        Account acc = new Account(200, "Closed");

        assertThrows(IllegalStateException.class,
                acc::getSecureTransactionHistory);
    }
}
