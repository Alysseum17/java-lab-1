package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CancelledTest {

    @Test
    void createsValidCancelled() {
        Cancelled cancelled = new Cancelled("Out of stock");
        assertEquals("Out of stock", cancelled.reason());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsInvalidReason(String reason) {
        assertThrows(IllegalArgumentException.class, () -> new Cancelled(reason));
    }
}