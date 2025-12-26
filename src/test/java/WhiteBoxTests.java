import banking.Account;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhiteBoxTests {

    @Test
    void withdrawFromSuspendedAccountFails() {
        Account acc = new Account(500, "Suspended");
        assertFalse(acc.withdraw(100)); // branch: suspended
    }

    @Test
    void withdrawExactBalanceSucceeds() {
        Account acc = new Account(300, "Verified");
        assertTrue(acc.withdraw(300)); // branch: amount == balance
    }
}
