package de.pde.erechnung.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private final UUID id;
    private final String invoiceNumber;
    private final LocalDate date;
    private final Party seller;
    private final Party buyer;
    private final List<InvoiceLine> lines;

    public Invoice(UUID id, String invoiceNumber, LocalDate date, Party seller, Party buyer, List<InvoiceLine> lines) {
        Objects.requireNonNull(id);
        requireNotBlank(invoiceNumber, "invoiceNumber");
        Objects.requireNonNull(date);
        Objects.requireNonNull(seller);
        Objects.requireNonNull(buyer);

        Objects.requireNonNull(lines);
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("lines shall not be empty.");
        }

        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.seller = seller;
        this.buyer = buyer;
        this.lines = List.copyOf(lines);
    }

    public UUID getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public Party getSeller() {
        return seller;
    }

    public Party getBuyer() {
        return buyer;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public BigDecimal netTotal() {
        var net = BigDecimal.ZERO;

        for (InvoiceLine line : lines) {
            net = net.add(line.netAmount());
        }

        return net;
    }

    @Override
    public boolean equals(Object otherInvoice) {
        if (!(otherInvoice instanceof Invoice other)) return false;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    private static void requireNotBlank(String value, String fieldName) {
        // TODO move to helper when needed third time
        Objects.requireNonNull(value, fieldName + " shall not be null.");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " shall not be blank.");
        }
    }
}
