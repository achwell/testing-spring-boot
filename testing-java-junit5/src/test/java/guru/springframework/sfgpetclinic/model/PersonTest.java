package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelsTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements ModelsTests {

    @Test
    void groupedAssertions() {
        // given
        Person person = new Person(1L, "Axel Wulff", "Sæther");
        //then
        assertAll("Test Props set",
                () -> assertEquals("Axel Wulff", person.getFirstName()),
                () -> assertEquals("Sæther", person.getLastName())
        );
    }

    @Test
    void groupedAssertions2() {
        // given
        Person person = new Person(1L, "Axel Wulff", "Sæther");
        //then
        assertAll("Test Props set",
                () -> assertEquals("Axel Wulff", person.getFirstName(), "First name failed"),
                () -> assertEquals("Sæther", person.getLastName(), "Last name failed")
        );
    }
}