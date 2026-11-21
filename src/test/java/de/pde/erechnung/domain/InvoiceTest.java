package de.pde.erechnung.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;

class InvoiceTest {

    private static Party seller() {
        return new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "DE", "DE818282828128128");
    }

    private static Party buyer() {
        return new Party("Dobidush Mahabo", "Strasse", "84456", "Homecity", "DE", "DE818282828128128");
    }

    private static InvoiceLine line(String quantity, String unitPrice) {
        return new InvoiceLine("Beratung", new BigDecimal(quantity), new BigDecimal(unitPrice), 19);
    }

    @Test
    void correctSum() {
        var lines = List.of(line("2", "19.99"), line("1.5", "10.00"));
        var invoice = new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(),
                lines);

        assertThat(invoice.netTotal()).isEqualByComparingTo("54.98");
    }

    @Test
    void noPositions() {
        assertThatThrownBy(() -> new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(),
                buyer(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noInvoiceNumber() {
        var lines = List.of(line("2", "19.99"), line("1.5", "10.00"));
        assertThatThrownBy(
                () -> new Invoice(UUID.randomUUID(), "    ", LocalDate.of(2026, 11, 20), seller(), buyer(), lines))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noSeller() {
        var lines = List.of(line("2", "19.99"), line("1.5", "10.00"));
        assertThatThrownBy(
                () -> new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), null, buyer(), lines))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void changeOrginalList() {
        var lines = new ArrayList<>(List.of(line("2", "19.99"), line("1.5", "10.00")));
        var invoice = new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(),
                lines);

        lines.add(line("3", "20.00"));
        assertThat(invoice.getLines())
                .hasSize(2);
    }

    @Test
    void unsupportedAction() {
        var lines = new ArrayList<>(List.of(line("2", "19.99"), line("1.5", "10.00")));
        var invoice = new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(),
                lines);

        assertThatThrownBy(() -> invoice.getLines().add(line("2", "1.99")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void sameId() {
        var id = UUID.randomUUID();
        var lines = new ArrayList<>(List.of(line("2", "19.99"), line("1.5", "10.00")));
        var invoiceOne = new Invoice(id, "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(), lines);
        var invoiceTwo = new Invoice(id, "RE-2026-002", LocalDate.of(2026, 11, 20), seller(), buyer(), lines);

        assertThat(invoiceOne).isEqualTo(invoiceTwo);
    }

    @Test
    void sameNumbersButNotId() {
        var lines = new ArrayList<>(List.of(line("2", "19.99"), line("1.5", "10.00")));
        var invoiceOne = new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(),
                lines);
        var invoiceTwo = new Invoice(UUID.randomUUID(), "RE-2026-001", LocalDate.of(2026, 11, 20), seller(), buyer(),
                lines);

        assertThat(invoiceOne).isNotEqualTo(invoiceTwo);
    }
}
