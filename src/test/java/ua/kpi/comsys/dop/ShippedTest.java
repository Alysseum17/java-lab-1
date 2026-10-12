package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShippedTest {

    @Test
    void createsValidShipped() {
        LocalDate date = LocalDate.of(2026, 10, 10);
        Shipped shipped = new Shipped("TRK-1", date);
        assertEquals("TRK-1", shipped.trackingCode());
        assertEquals(date, shipped.dispatchDate());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void rejectsInvalidTrackingCode(String trackingCode) {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped(trackingCode, LocalDate.of(2026, 10, 10)));
    }

    @Test
    void rejectsNullDispatchDate() {
        assertThrows(IllegalArgumentException.class, () -> new Shipped("TRK-1", null));
    }
}