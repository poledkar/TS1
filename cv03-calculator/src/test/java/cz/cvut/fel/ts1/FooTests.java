package cz.cvut.fel.ts1;

import org.junit.jupiter.api.*;

public class FooTests {

    Foo foo;

    @BeforeEach
    public void setFoo() {
        foo = new Foo();
    }

    @Test
    @Order(0)
    public void returnNumber_returnsFive_true() {
        Assertions.assertEquals(5, foo.returnNumber());
    }

    @Test @Disabled
    public void returnNumber_returnsFive_failure() {
        Assertions.assertEquals(4, foo.returnNumber());
    }

    @Test
    @Order(1)
    public void getNum_returnsZero_passed() {
        Assertions.assertEquals(0, foo.getNum());
    }

    @Test
    public void increment_returnsIncrementedNumber_passed() {
        Assertions.assertEquals(0, foo.getNum());
        foo.increment();
        Assertions.assertEquals(1, foo.getNum());
    }

    @Test
    public void increment_returnsIncrementedNumber_passed2() {
        int number = foo.getNum();
        foo.increment();
        Assertions.assertEquals(++number, foo.getNum());
    }

    @Test
    public void getNum_afterIncrement_increments() {
        int first = foo.getNum();
        foo.increment();
        int next = foo.getNum();
        Assertions.assertTrue(next > first);
    }

    @Test
    public void exceptionThrown_throwsException_OcekavanaVyjimka() {
        Assertions.assertThrows(Exception.class, foo::exceptionThrown, "Ocekavana vyjimka");
    }

    @Test
    public void exceptionThrown_returnsException_throwedException() {
        Assertions.assertThrows(Exception.class, foo::exceptionThrown);
    }

    @Test @Disabled
    public void exceptionThrown_returnsException_throwedRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, foo::exceptionThrown);
    }
}
