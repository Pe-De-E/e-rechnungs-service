package de.pde.erechnung.domain;

import java.util.Objects;

public record Party(
        String name,
        String street,
        String postalCode,
        String city,
        String countryCode,
        String vatId) {
    public Party {
        requireNotBlank(name, "name");
        requireNotBlank(street, "street");
        requireNotBlank(postalCode, "postalCode");
        requireNotBlank(city, "city");
        requireNotBlank(countryCode, "countryCode");
        if (!countryCode.matches("[A-Z]{2}")) {
            throw new IllegalArgumentException(
                    "countryCode shall be two uppercase Letters and consist of two letters only");
        }
        if (vatId != null && vatId.isBlank()) {
            throw new IllegalArgumentException("vatId shall be filled in or null, nothing in between.");
        }
    }

    private static void requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " shall not be null.");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " shall not be blank.");
        }
    }
}
