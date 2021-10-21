package course;

import java.util.Arrays;
import java.util.Iterator;

//Note the use of a generic type for the items
//范型
public class Bag_Array<Item> implements Bag<Item> {
    public void add(Item item) {
        if (full())
            grow(items, 2 * capacity());
        items[count++] = item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public Iterator<Item> iterator() {
        return Arrays.asList(Arrays.copyOf(items, count)).iterator();
    }

    public Bag_Array() {
        grow((Item[]) new Object[0], 32);
    }

    private void grow(Item[] source, int size) {
        items = growFrom(source, size);
    }

    private int capacity() {
        return items.length; // items should always be non-null when this method is called
        //
    }

    private boolean full() {
        return size() == capacity();

    }

    private static <T> T[] growFrom(T[] from, int size) {
        T[] result = (T[]) new Object[size];
        System.arraycopy(from, 0, result, 0, from.length);
        return result;
    }

    private Item[] items = null;
    private int count = 0;
}

