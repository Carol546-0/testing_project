package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateBasedTests {

    @Test
    void unverifiedAccountCannotDeposit() {
        Account acc = new Account(300); // default = Unverified

        assertFalse(acc.deposit(50));
        assertEquals(300, acc.getBalance());
    }

    @Test
    void verifiedAccountAllowsDepositAndWithdraw() {
        Account acc = new Account(300, "Verified");

        assertTrue(acc.deposit(100));
        assertTrue(acc.withdraw(50));
        assertEquals(350, acc.getBalance());
    }

    @Test
    void suspendedAccountBlocksWithdraw() {
        Account acc = new Account(300, "Suspended");

        assertFalse(acc.withdraw(50));
        assertEquals(300, acc.getBalance());
    }

    @Test
    void suspendedAccountCanBeReactivated() {
        Account acc = new Account(500, "Suspended");

        acc.setStatus("Verified"); // Appeal transition

        assertTrue(acc.deposit(100));
        assertEquals(600, acc.getBalance());
    }

    @Test
    void closedAccountBlocksAllOperations() {
        Account acc = new Account(1000, "Closed");

        assertFalse(acc.deposit(100));
        assertFalse(acc.withdraw(50));
        assertEquals(1000, acc.getBalance());
    }
}
