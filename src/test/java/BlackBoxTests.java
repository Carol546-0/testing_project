import banking.Account;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlackBoxTests {

    @Test
    void depositNegativeAmountFails() {
        Account acc = new Account(500, "Verified");
        assertFalse(acc.deposit(-100));
    }

    @Test
    void validDepositSucceeds() {
        Account acc = new Account(500, "Verified");
        assertTrue(acc.deposit(100));
    }

    @Test
    void withdrawMoreThanBalanceFails() {
        Account acc = new Account(200, "Verified");
        assertFalse(acc.withdraw(300));
    }
}
