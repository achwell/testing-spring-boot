package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelsRepeatedTests;
import guru.springframework.sfgpetclinic.ModelsTests;
import org.junit.jupiter.api.*;

public class PersonRepeatedTest implements ModelsRepeatedTests {

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} of {totalRepetitions}")
    @DisplayName("My Repeated Test")
    void myRepeatedTest() {
        System.out.println("Repeated");
    }

    @RepeatedTest(value = 5)
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println("myRepeatedTestWithDI");
        System.out.println(testInfo);
        System.out.println(repetitionInfo);
    }

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} of {totalRepetitions}")
    @DisplayName("My Repeated Test Assignment")
    void myassignmentRepeated(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println("myRepeatedTestWithDI");
        System.out.println(testInfo);
        System.out.println(repetitionInfo);
    }
}
