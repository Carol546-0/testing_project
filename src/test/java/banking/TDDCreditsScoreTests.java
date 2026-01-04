package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TDDCreditsScoreTests {

    @Test
    void creditScoreAboveThresholdIsEligible() {
        Account acc = new Account(200, "Verified");
        acc.setCreditScore(750);

        assertTrue(acc.isCreditEligible());
    }

    @Test
    void creditScoreAtThresholdIsEligible() {
        Account acc = new Account(200, "Verified");
        acc.setCreditScore(650);

        assertTrue(acc.isCreditEligible());
    }

    @Test
    void creditScoreBelowThresholdIsNotEligible() {
        Account acc = new Account(200, "Verified");
        acc.setCreditScore(600);

        assertFalse(acc.isCreditEligible());
    }

    @Test
    void negativeCreditScoreThrowsException() {
        Account acc = new Account(200, "Verified");

        assertThrows(IllegalArgumentException.class,
                () -> acc.setCreditScore(-1));
    }
}
