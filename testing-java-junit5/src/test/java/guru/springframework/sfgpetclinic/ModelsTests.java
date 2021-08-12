package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.*;

@Tag("Models")
public interface ModelsTests {

    @BeforeEach
    default void beforeEach(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }
}
