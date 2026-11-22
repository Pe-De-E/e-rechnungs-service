package de.pde.erechnung.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;

class InvoiceTest {

    private static UUID id() {
        return UUID.randomUUID();
    }

    private static final String INVOICE_NUMBER = "RE-2026-001";

    private static Party seller() {
        return new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "DE", "DE818282828128128");
    }

    private static Party buyer() {
        return new Party("Dobidush Mahabo", "Strasse", "84456", "Homecity", "DE", "DE818282828128128");
    }

    private static InvoiceLine line(String quantity, String unitPrice) {
        return new InvoiceLine("Beratung", new BigDecimal(quantity), new BigDecimal(unitPrice), 19);
    }

    private static Invoice invoice(UUID id, String invoiceNumber, Party seller, List<InvoiceLine> lines) {
        return new Invoice(id, invoiceNumber, LocalDate.of(2026, 11, 20), seller, buyer(), lines);
    }

    private static List<InvoiceLine> lines() {
        return List.of(line("2", "19.99"), line("1.5", "10.00"));
    }

    @Test
    void correctSum() {
        var invoice = invoice(id(), INVOICE_NUMBER, seller(), lines());

        assertThat(invoice.netTotal()).isEqualByComparingTo("54.98");
    }

    @Test
    void noPositions() {
        assertThatThrownBy(() -> invoice(id(), INVOICE_NUMBER, seller(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noInvoiceNumber() {
        assertThatThrownBy(
                () -> invoice(id(), "    ", seller(), lines())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noSeller() {
        assertThatThrownBy(
                () -> invoice(id(), INVOICE_NUMBER, null, lines())).isInstanceOf(NullPointerException.class);
    }

    @Test
    void changeOrginalList() {
        var lines = new ArrayList<>(lines());
        var invoice = invoice(id(), INVOICE_NUMBER, seller(), lines);

        lines.add(line("3", "20.00"));
        assertThat(invoice.getLines())
                .hasSize(2);
    }

    @Test
    void unsupportedAction() {
        var lines = new ArrayList<>(lines());
        var invoice = invoice(id(), INVOICE_NUMBER, seller(), lines);

        assertThatThrownBy(() -> invoice.getLines().add(line("2", "1.99")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void sameId() {
        var sharedId = UUID.randomUUID();

        var invoiceOne = invoice(sharedId, INVOICE_NUMBER, seller(), lines());
        var invoiceTwo = invoice(sharedId, "RE-2026-002", seller(), lines());

        assertThat(invoiceOne).isEqualTo(invoiceTwo);
    }

    @Test
    void sameNumbersButNotId() {
        var invoiceOne = invoice(id(), INVOICE_NUMBER, seller(), lines());
        var invoiceTwo = invoice(id(), INVOICE_NUMBER, seller(), lines());

        assertThat(invoiceOne).isNotEqualTo(invoiceTwo);
    }
}
