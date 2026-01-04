package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UITests {

    @Test
    void uiDepositVerifiedAccount() {
        Account acc = new Account(200, "Verified");

        boolean result = acc.deposit(100);

        assertTrue(result);
        assertEquals(300, acc.getBalance());
    }

    @Test
    void uiDepositUnverifiedBlocked() {
        Account acc = new Account(200); // default = Unverified

        boolean result = acc.deposit(100);

        assertFalse(result);
        assertEquals(200, acc.getBalance());
    }

    @Test
    void uiWithdrawSuspendedBlocked() {
        Account acc = new Account(200, "Suspended");

        boolean result = acc.withdraw(50);

        assertFalse(result);
        assertEquals(200, acc.getBalance());
    }

    @Test
    void uiInvalidInputRejected() {
        Account acc = new Account(200, "Verified");

        boolean result = acc.deposit(-50);

        assertFalse(result);
        assertEquals(200, acc.getBalance());
    }
}
