package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("Controllers")
public interface ControllerTests {

    @BeforeAll
    default void beforeAll() {
        System.out.println("Lets do something here");
    }

    @BeforeEach
    default void beforeEach(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }
}
