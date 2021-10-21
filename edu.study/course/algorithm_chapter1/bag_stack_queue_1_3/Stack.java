package course.algorithm_chapter1.bag_stack_queue_1_3;

import java.util.Iterator;
import java.util.function.Consumer;

public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;
    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    public Stack(){
        first = null;
        n = 0;
    }

    public boolean isEmpty(){
        return first==null;
    }

    public int size(){
        return n;
    }

    public void push(Item item){
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }


    public Item pop(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        Node<Item> node = first;
        first = first.next;
        n--;
        return node.item;
    }

    public Item peek(){
        if(isEmpty()){
            throw new NullPointerException();
        }

        return first.item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }



    @Override
    public Iterator<Item> iterator() {
        return null;
    }


    private class LinkedIterator implements Iterator<Item>{
        private Node<Item> current;

        public LinkedIterator(Node<Item> first){
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NullPointerException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
