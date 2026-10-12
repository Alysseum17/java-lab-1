package ua.kpi.comsys.dop;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor();

        System.out.print(processor.process(new Pending()) + "\n");
        System.out.print(processor.process(new Paid("PAY-123", new BigDecimal("100.50"))) + "\n");
        System.out.print(processor.process(new Shipped("TRK-456", LocalDate.of(2026, 10, 10))) + "\n");
        System.out.print(processor.process(new Cancelled("Out of stock")) + "\n");

        try {
            processor.process(null);
        } catch (IllegalArgumentException e) {
            System.out.println("null -> " + e.getMessage() + "\n");
        }

        try {
            new Paid("PAY-123", BigDecimal.ZERO);
        } catch (IllegalArgumentException e) {
            System.out.println("Paid(amount=0) -> " + e.getMessage() + "\n");
        }

        try {
            new Shipped("  ", LocalDate.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Shipped(blank) -> " + e.getMessage() + "\n");
        }

        try {
            new Cancelled(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Cancelled(null) -> " + e.getMessage() + "\n");
        }
    }
}