package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void processesPending() {
        String expected = """
                Order Status: PENDING
                Details: Order is currently pending processing.
                """;
        assertEquals(expected, processor.process(new Pending()));
    }

    @Test
    void processesPaid() {
        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY-123, Amount: $100.50
                """;
        assertEquals(expected,
                processor.process(new Paid("PAY-123", new BigDecimal("100.50"))));
    }

    @Test
    void processesShipped() {
        String expected = """
                Order Status: SHIPPED
                Details: Tracking Code: TRK-456, Dispatch Date: 2026-10-10
                """;
        assertEquals(expected,
                processor.process(new Shipped("TRK-456", LocalDate.of(2026, 10, 10))));
    }

    @Test
    void processesCancelled() {
        String expected = """
                Order Status: CANCELLED
                Details: Reason: Out of stock
                """;
        assertEquals(expected, processor.process(new Cancelled("Out of stock")));
    }

    @Test
    void throwsOnNullStatus() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));
        assertEquals("Status cannot be null", ex.getMessage());
    }
}