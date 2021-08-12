package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.MockitoAnnotations.openMocks;

public class AnnotationMocksTest {

    @Mock
    private Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void name() {
        mapMock.put("keyvalue", "value");
    }
}
