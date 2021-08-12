package guru.springframework;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    private Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("In BeforeAll");
    }

    @BeforeEach
    void setUp() {
        greeting = new Greeting();
        System.out.println("In BeforeEach");
    }

    @AfterEach
    void tearDown() {
        System.out.println("In AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("In AfterAll");
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        System.out.println(greeting.helloWorld("Axel"));
    }
}