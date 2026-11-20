package de.pde.erechnung.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PartyTest {

    @Test
    void correctParty() {
        var party = new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "DE", "DE818282828128128");

        assertThat(party.countryCode()).isEqualTo("DE");
        assertThat(party.vatId()).isNotBlank();
    }

    @Test
    void correctPartyWithoutVat() {
        var party = new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "DE", null);

        assertThat(party.countryCode()).isEqualTo("DE");
        assertThat(party.vatId()).isNull();
    }

    @Test
    void incorrectPartyWithoutSpacesAsVat() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "DE", "    "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withIncorrectCountryCode() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "Germany", "DE12312312"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withIncorrectCasingInCountryCode() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "De", "DE1231323"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withNumberInCountryCode() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "Strasse", "84456", "Homecity", "D1", "BlaBla"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cityIsNull() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "Strasse", "84456", null, "DE", "    "))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void withoutStreet() {
        assertThatThrownBy(() -> new Party("Inigo Montoya", "    ", "84456", "Homecity", "DE", "    "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
