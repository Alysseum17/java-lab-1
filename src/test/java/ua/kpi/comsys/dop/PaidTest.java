package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaidTest {

    @Test
    void createsValidPaid() {
        Paid paid = new Paid("PAY-1", new BigDecimal("10.00"));
        assertEquals("PAY-1", paid.paymentId());
        assertEquals(new BigDecimal("10.00"), paid.amount());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsInvalidPaymentId(String paymentId) {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid(paymentId, new BigDecimal("10.00")));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"0", "0.00", "-0.01", "-100"})
    void rejectsInvalidAmount(String amount) {
        BigDecimal value = amount == null ? null : new BigDecimal(amount);
        assertThrows(IllegalArgumentException.class, () -> new Paid("PAY-1", value));
    }
}