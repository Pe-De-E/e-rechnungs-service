package de.pde.erechnung.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record InvoiceLine(
        String name,
        BigDecimal quantity,
        BigDecimal unitPrice,
        int vatRate) {
    public InvoiceLine {
        Objects.requireNonNull(quantity, "quantity shall not be null");
        Objects.requireNonNull(unitPrice, "unitPrice shall not be null");
        Objects.requireNonNull(name, "name shall not be null");


        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantity shall not be below zero.");
        }
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("unitPrice shall be zero or above");
        }
        if (vatRate < 0) {
            throw new IllegalArgumentException("vatRate shall not be negative.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("name shall not be blank.");
        }
    }

    public BigDecimal netAmount() {
        return quantity
                .multiply(unitPrice)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
