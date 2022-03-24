package lab03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class FooTests {

    @Test
    @Order(0)
    public void returnNumber_returnsFive_true() {
        Foo foo = new Foo();
        Assertions.assertEquals(5, foo.returnNumber());
    }

    @Test @Disabled
    public void returnNumber_returnsFive_failure() {
        Foo foo = new Foo();
        Assertions.assertEquals(4, foo.returnNumber());
    }

    @Test
    @Order(1)
    public void getNum_returnsZero_passed() {
        Foo foo = new Foo();
        Assertions.assertEquals(0, foo.getNum());
    }

    @Test
    public void increment_returnsIncrementedNumber_passed() {
        Foo foo = new Foo();
        Assertions.assertEquals(0, foo.getNum());
        foo.increment();
        Assertions.assertEquals(1, foo.getNum());
    }

    @Test
    public void increment_returnsIncrementedNumber_passed2() {
        Foo foo = new Foo();
        int number = foo.getNum();
        foo.increment();
        Assertions.assertEquals(++number, foo.getNum());
    }

    @Test
    public void getNum_afterIncrement_increments() {
        Foo foo = new Foo();
        int first = foo.getNum();
        foo.increment();
        int next = foo.getNum();
        Assertions.assertTrue(next > first);
    }

    @Test
    public void exceptionThrown_throwsException_OcekavanaVyjimka() {
        Foo foo = new Foo();
        Assertions.assertThrows(Exception.class, foo::exceptionThrown, "Ocekavana vyjimka");
    }

    @Test
    public void exceptionThrown_returnsException_throwedException() {
        Foo foo = new Foo();
        Assertions.assertThrows(Exception.class, foo::exceptionThrown);
    }

    @Test @Disabled
    public void exceptionThrown_returnsException_throwedRuntimeException() {
        Foo foo = new Foo();
        Assertions.assertThrows(RuntimeException.class, foo::exceptionThrown);
    }
}
