package storage;

import shop.*;

import java.util.Objects;


/**
 * Auxiliary class for item storage
 */
public class ItemStock {
    private final Item refItem;
    private int count;

    ItemStock(Item refItem) {
        this.refItem = Objects.requireNonNull(refItem, "refItem must not be null");
        count = 0;
    }

    @Override
    public String toString() {
        return "STOCK OF ITEM:  "+refItem+"    PIECES IN STORAGE: "+count;
    }

    void increaseItemCount(int x) throws IllegalStateException {
        addSafely(x);
    }

    void decreaseItemCount(int x) throws IllegalStateException {
        addSafely(- (long) x);
    }

    private void addSafely(long diff) throws IllegalStateException {
        long result = count + diff;
        int integer = (int) result;
        if (integer != result) {
            throw new IllegalStateException("count would be out of range");
        }
        count = integer;
    }

    int getCount() {
        return count;
    }

    Item getItem() {
        return refItem;
    }
}