package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelsTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class OwnerTest implements ModelsTests {

    @Test
    void dependentAssertions() {
        Owner owner = new Owner(1L, "Axel Wulff", "Sæther");
        owner.setCity("Drammen");
        owner.setTelephone("900 86 954");

        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Axel Wulff", owner.getFirstName(), "First Name Did not Match"),
                        () -> assertEquals("Sæther", owner.getLastName())),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Drammen", owner.getCity(), "City Did Not Match"),
                        () -> assertEquals("900 86 954", owner.getTelephone())
                ));

        assertThat(owner.getCity(), is("Drammen"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Spring", "FrameWork", "Guru"})
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("Value Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "FrameWork", "Guru"})
    void testValueSource2(String val) {
        System.out.println(val);
    }

    @DisplayName("Enum Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void enumTest(OwnerType ownerType) {
        System.out.println(ownerType);
    }

    @DisplayName("csv Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 3",
    })
    void csvSourceTest(String stateName, int val1, int val2) {
        System.out.println(stateName + val1 + val2);
    }

    @DisplayName("csv File Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFileTest(String stateName, int val1, int val2) {
        System.out.println(stateName + val1 + val2);
    }

    @DisplayName("Method Provider Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @MethodSource("getArgs")
    void fromMethodTest(String stateName, int val1, int val2) {
        System.out.println(stateName + val1 + val2);
    }

    @DisplayName("Custom Provider Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void customProviderTest(String stateName, int val1, int val2) {
        System.out.println(stateName + val1 + val2);
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of("FL", 1, 2),
                Arguments.of("OH", 3, 4),
                Arguments.of("MI", 5, 6)
        );
    }
}