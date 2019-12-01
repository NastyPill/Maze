import generator.Generator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    @Test
    public void test() {
        assertThrows(IllegalArgumentException.class, this::generatorTest);

        assertNotNull(generatorNNTest());

        Generator g1 = generatorSeedWorksTest();
        g1.generate();
        Generator g2 = generatorSeedWorksTest();
        g2.generate();

        assertEquals(g1.getField().toString(), g2.getField().toString());

        g1 = generatorSeedWorksTest();
        g1.generate();
        g2 = generatorSeedNotWorksTest();
        g2.generate();

        assertNotEquals(g1.getField().toString(), g2.getField().toString());
    }

    private Generator generatorTest() {
        return new Generator(99999999, 99999999, 123l);
    }

    private Generator generatorNNTest() {
        return new Generator(20, 20);
    }

    private Generator generatorSeedWorksTest() {
        return new Generator(50, 50, 50l);
    }

    private Generator generatorSeedNotWorksTest() {
        return new Generator(50, 50, 51l);
    }

}
