package de.pde.erechnung.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class InvoiceLineTest {

    @Test
    void multipliesQuantityByUnitPrice() {
        // Arrange: Ausgangslage herstellen
        var line = new InvoiceLine("Beratung", new BigDecimal("2"), new BigDecimal("19.99"), 19);

        // Act: das Getestete ausführen
        var net = line.netAmount();

        // Assert: Ergebnis prüfen
        assertThat(net).isEqualByComparingTo("39.98");
    }

    @Test
    void rejectsNegativeQuantity() {
        assertThatThrownBy(() -> new InvoiceLine("Beratung", new BigDecimal("-1"), new BigDecimal("10.00"), 19))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void amountHasDecimalPlace() {
        var line = new InvoiceLine("Beratung", new BigDecimal("1.5"), new BigDecimal("10.00"), 19);
        var net = line.netAmount();
        assertThat(net).isEqualByComparingTo("15.00");
    }

    @Test
    void rounding() {
        var line = new InvoiceLine("Beratung", new BigDecimal("3"), new BigDecimal("0.333"), 19);
        var net = line.netAmount();
        assertThat(net).isEqualByComparingTo("1.00");
    }

    @Test
    void amountIsZero() {
        var line = new InvoiceLine("Beratung", new BigDecimal("0"), new BigDecimal("50.00"), 19);
        var net = line.netAmount();
        assertThat(net).isEqualByComparingTo("0.00");
    }

    @Test
    void rejectsNegativePrice() {
        assertThatThrownBy(() -> new InvoiceLine("Beratung", new BigDecimal("1"), new BigDecimal("-10.00"), 19))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNegativeNetRate() {
        assertThatThrownBy(() -> new InvoiceLine("Beratung", new BigDecimal("1"), new BigDecimal("10.00"), -19))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsEmptyName() {
        assertThatThrownBy(() -> new InvoiceLine("    ", new BigDecimal("1"), new BigDecimal("10.00"), 19))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNameNull() {
        assertThatThrownBy(() -> new InvoiceLine(null, new BigDecimal("1"), new BigDecimal("-10.00"), 19))
                .isInstanceOf(NullPointerException.class);
    }
}
