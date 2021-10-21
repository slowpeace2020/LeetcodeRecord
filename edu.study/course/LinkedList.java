package course;

public class LinkedList<Item> {
    public void add(Item item) {
        Element tail = head;
        head = new Element(item, tail);
    }

//    public Item remove() throws BQSException {
//        if (head == null) throw new
//                BQSException("collection is empty");
//        Item result = head.item;
//        head = head.next;
//        return result;
//    }

    public Item getHead() {
        return isEmpty() ? null : head.item;
    }

    public boolean isEmpty() {
        return head == null;
    }

    private class Element {
        Element(Item x, Element n) {
            item = x;
            next = n;
        }final Element next;
        final Item item;
    }

    private Element head = null;
}
