package course;

public class Queue_Elements<Item> implements Queue<Item> {
    @Override
    public void enqueue(Item item) {

    }

    @Override
    public Item dequeue() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Construct a new (empty) queue.
     */
//    public Queue_Elements() {
//        oldest = null;
//        newest = null;
//    }
//
//    /**
//     * Enqueue the given item into the linked list referenced by oldest * @param item the item to add
//     */
//    public void enqueue(Item item) {
//        Element<Item> element = new Element<>(item);
//        Element<Item> secondNewest = newest;
//        if (isEmpty()) oldest = element;
//        else {
//            assert secondNewest != null; // Redundant Check secondNewest.next = element;
//        }
//        this.newest = element;
//    }
//
//    /**
//     * Dequeue an element from the oldest list and return the item. * @return the value of the oldest element.
//     */
//    public Item dequeue() {
//        if (isEmpty()) return null;
//        else {
//            assert oldest != null; // Redundant assertion Item result = oldest.item;
//            oldest = oldest.next;
//            if (isEmpty()) newest = null;
//            return result;
//        }
//    }
//
//    public boolean isEmpty() {
//        return oldest == null;
//    }


    // This Element essentially begins a LinkedList of Elements which correspond
// to the elements that can be taken from the queue (head points to the oldest element). // However, it is built in manner that requires a pointer to the newest element. privateElement<Item> oldest;
//    Element constructor with single parameter makes next pointer null.
//    We are primarily concerned with newest, but must take care of oldest when list is empty
//    oldest always changes but newest only when empty.
//    oldest essentially implements a linked list while newest points to its last Element
    // This element always points to the newest (tail-most) element in the LinkedList referenced by oldest.
//    private Element<Item> oldest;
//    private Element<Item> newest;
    private class Element {
// same as for LinkedList (except that next must be mutable)
    }
}
