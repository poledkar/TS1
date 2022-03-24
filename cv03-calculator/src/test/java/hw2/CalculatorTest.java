package hw2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll // Calculator by testy nemělo mít možnost nijak změnit
    public static void prepareCalculator() {
        calculator = new Calculator();
    }

    @Test
    public void add_1plus2_Returns3() {
        Assertions.assertEquals(3, calculator.add(1, 2));
    }

    @Test
    public void subtract_5minus3_Returns2() {
        Assertions.assertEquals(2, calculator.subtract(5, 3));
    }

    @Test
    public void multiply_2times3_Returns6() {
        Assertions.assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    public void divide_6dividedBy3_Returns2() {
        Assertions.assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    public void divide_5dividedBy0_ThrowsArithmeticException() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
    }
}
