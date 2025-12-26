import banking.Account;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateBasedTests {

    @Test
    void depositInClosedStateFails() {
        Account acc = new Account(1000, "Closed");
        assertFalse(acc.deposit(100));
    }

    @Test
    void suspendedAccountCanBeReactivated() {
        Account acc = new Account(500, "Suspended");
        acc.setStatus("Verified"); // Appeal transition
        assertTrue(acc.deposit(100));
    }
}
