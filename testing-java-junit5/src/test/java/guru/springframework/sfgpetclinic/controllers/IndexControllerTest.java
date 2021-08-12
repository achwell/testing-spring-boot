package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest implements ControllerTests {

    private IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertNotEquals("inddex", controller.index(), "Wrong view returned");
        assertNotEquals("inddex", controller.index(), () -> "Another expensive message " +
                "Make me only if needed");
        assertThat(controller.index()).isEqualTo("index");
    }

    @Test
    void oupsHandler() {
        assertThrows(ValueNotFoundException.class, () -> {
            controller.oupsHandler();
        });
    }

    @Disabled("Demo of timeout")
    @Test
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(5000);
            System.out.println("I got here");
        });
    }

    @Disabled("Demo of timeout")
    @Test
    void testTimeOutPreemptively() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(5000);
            System.out.println("I got here Preemptively");
        });
    }

    @Disabled("Demo of failing Assumption")
    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU_RUNTIME")));
    }

    @Test
    void testAssumptionTrueAssumptionIsTrue() {
        assumeTrue("GURU".equalsIgnoreCase("GURU"));
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testOnMacOS() {
        System.out.println("Running on a Mac");
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testOnWintendo() {
        System.out.println("Running on Wintendo");
    }

    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void testOnJava8() {
        System.out.println("Running on java8");
    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testOnJava11() {
        System.out.println("Running on java11");
    }

    @EnabledOnJre(JRE.OTHER)
    @Test
    void testOnOtherJava() {
        System.out.println("Running on other java");
    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "axelwulffsaether")
    @Test
    void testForUser() {
        System.out.println("Running for user axelwulffsaether");
    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "kallekra")
    @Test
    void testNotRunForUser() {
        System.out.println("Running for user kallekra");
    }
}