package cz.fel.cvut.ts1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PoledkarTest {
    @Test
    public void factorialTest() {
        Poledkar poledkar = new Poledkar();
        assertEquals(6, poledkar.factorial(3));
    }
}
