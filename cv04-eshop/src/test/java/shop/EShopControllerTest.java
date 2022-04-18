package shop;

import org.junit.jupiter.api.*;
import storage.ItemStock;
import storage.NoItemInStorage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

public class EShopControllerTest {

    /*
     * Process tests according to EShopController.prj
     * Test Depth Level = 2 (created manually to reduce number of tests and make them more plausible)
     *
     *       Test case
     * Edge  A. B. C.
     * 1-2   x  x  x
     * 2-3      x  x
     * 2-9   x
     * 3-4      xx xxxxx
     * 4-5      xx xxx
     * 4-7         xx
     * 5-6      xx xxx
     * 6-3      x  xxx
     * 6-9      x
     * 7-8         xx
     * 8-3         x
     * 8-9         x
     * 9-10  x
     * 9-11     x  x
     * 11-12    x
     * 11-13       x
     *
     * Test cases:
     * A. 1-2-9-10
     * B. 1-2-3-4-5-6-3-4-5-6-9-11-12
     * C. 1-2-3-4-5-6-3-4-7-8-3-4-5-6-3-4-5-6-3-4-7-8-9-11-13
     */

    private static final Item toy = new StandardItem(123, "Toy", 1234.5f, "Toys", 1);
    private static final Item ball = new StandardItem(456, "Ball", 200f, "Sport", 2);

    private final PrintStream originalOutput = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void captureOutput() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreOutput() {
        System.setOut(originalOutput);
    }

    private String[] capturedOutput() {
        System.out.flush();
        String result = outContent.toString();
        outContent.reset();
        return result.lines().toArray(String[]::new);
    }

    @Test
    public void processTestA() {
        EShopController controller = new EShopController();
        // 1
        ShoppingCart cart = controller.newCart();
        Assertions.assertTrue(cart.getCartItems().isEmpty(),
                "New cart is not empty");
        // 2,9,10
        Assertions.assertDoesNotThrow(
                () -> controller.purchaseShoppingCart(cart, "Adam Albertovsky", "Albertovska 1, Albertov"),
                "Unexpected exception");
        Assertions.assertArrayEquals(new String[] { "Error: shopping cart is empty" }, capturedOutput(),
                "Bad error message");
    }

    @Test
    public void processTestB() {
        EShopController controller = new EShopController();
        controller.getStorage().insertItems(toy, 1);
        Object[] items = { toy };
        String[] storage = {
                "STORAGE IS CURRENTLY CONTAINING:",
                "STOCK OF ITEM:  Item   ID 123   NAME Toy   CATEGORY Toys   PRICE 1234.5   LOYALTY POINTS 1    PIECES IN STORAGE: 1"
        };
        // 1
        ShoppingCart cart = controller.newCart();
        Assertions.assertEquals(0, cart.getItemsCount(),
                "New cart is not empty");
        // 2,3
        controller.getStorage().printListOfStoredItems();
        Assertions.assertArrayEquals(storage, capturedOutput(),
                "Bad listing of all items");
        // 4,5
        cart.addItem(toy);
        Assertions.assertEquals(1234.5f, cart.getTotalPrice(), "Bad total cost of shopping cart after adding item");
        // 6,3 - some alternative read operation used
        Assertions.assertArrayEquals(items, controller.getStorage().getItemsOfCategorySortedByPrice("Toys").toArray(),
                "Bad listing of Toys");
        // 4,5
        cart.addItem(toy);
        Assertions.assertEquals(2, cart.getItemsCount(),
                "Bad total amount of items is cart");
        // 6,9,11,12
        Assertions.assertThrows(NoItemInStorage.class,
                () -> controller.purchaseShoppingCart(cart, "Bela Bydzovska", "Kostelni 1, Bydzov"),
                "Insufficient storage not detected");
        Assertions.assertEquals(1, controller.getStorage().getItemCount(toy),
                "Storage changed despite no purchase");
        Assertions.assertEquals(0, controller.getArchive().getHowManyTimesHasBeenItemSold(toy),
                "Item sold unexpectedly");
    }

    @Test
    public void processTestC() {
        EShopController controller = new EShopController();
        controller.getStorage().insertItems(ball, 10);
        Object[] items = { ball };
        String[] sportItems = {
                "STOCK OF ITEM:  Item   ID 456   NAME Ball   CATEGORY Sport   PRICE 200.0   LOYALTY POINTS 2    PIECES IN STORAGE: 10"
        };
        String[] storage = Stream.concat(
                Stream.of("STORAGE IS CURRENTLY CONTAINING:"),
                Stream.of(sportItems)
        ).toArray(String[]::new);
        // 1
        ShoppingCart cart = controller.newCart();
        Assertions.assertEquals(0, cart.getTotalPrice(),
                "New cart has non-zero price");
        // 2,3
        controller.getStorage().printListOfStoredItems();
        Assertions.assertArrayEquals(storage, capturedOutput(),
                "Bad listing of all items");
        // 4,5
        cart.addItem(ball);
        Assertions.assertArrayEquals(items, cart.getCartItems().toArray(),
                "Bad cart content after adding first item");
        // 6,3 - some alternative read operation used
        Assertions.assertArrayEquals(items, controller.getStorage().getItemsOfCategorySortedByPrice("Sport").toArray(),
                "Bad listing of Sport goods");
        // 4,7
        cart.removeItem(ball.getID());
        Assertions.assertArrayEquals(new Object[0], cart.getCartItems().toArray(),
                "Cart not empty after removing items");
        // 8,3 - yet another read operation used
        Assertions.assertArrayEquals(sportItems,
                controller.getStorage().getStockEntries().stream().map(ItemStock::toString).toArray(String[]::new),
                "Bad collection of stock entries");
        // 4,5
        cart.addItem(ball);
        Assertions.assertEquals(1, cart.getItemsCount(),
                "Cart does not contain 1 item after adding it into empty cart");
        // 6,3 - very simple read operation
        Assertions.assertEquals(1, controller.getStorage().getStockEntries().size());
        // 4,5
        cart.addItem(toy);
        Assertions.assertTrue(cart.getCartItems().contains(toy),
                "Toy not in cart after adding it");
        // 6,3 - another read operation
        Assertions.assertEquals(200, controller.getStorage().getPriceOfWholeStock(),
                "Unexpected cost of whole stock");
        // 4,7
        cart.removeItem(toy.getID());
        Assertions.assertFalse(cart.getCartItems().contains(toy),
                "Toy in cart after removing it");
        // 8,9,11,13
        Assertions.assertDoesNotThrow(
                () -> controller.purchaseShoppingCart(cart, "Cecilie Cindlinska", "Zamecka 1, Cidlina"),
                "Unexpected exception");
        Assertions.assertEquals(9, controller.getStorage().getItemCount(ball.getID()),
                "Bad remaining stock");
        Assertions.assertEquals(1, controller.getArchive().getHowManyTimesHasBeenItemSold(ball),
                "Bad sold count");
    }
}
