package course.algorithm_chapter1.bag_stack_queue_1_3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item>{

    private static final int INIT_CAPACITY = 8;

    private Item[] q;
    private int n;
    private int first;
    private int last;


    public ResizingArrayQueue(){
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty(){
        return n==0;
    }

    public int size(){
        return n;
    }

    private void resize(int capacity){
        assert capacity>=n;
        Item[] copy = (Item[]) new Object[capacity];
        for(int i=0;i<n;i++){
            copy[i] = q[(first+i)%q.length];
        }
        q = copy;
        first = 0;
        last = n;
    }

    public void enqueue(Item item){
        if(n==q.length)resize(2*q.length);
        q[last++] = item;
        if(last==q.length)last=0;//?wrap-around 环绕？
        n++;
    }

    public Item dequeue(){
        if(isEmpty())throw new NullPointerException();

        Item item = q[first];
        q[first] = null;
        n--;
        first++;
        if(first==q.length)first=0;
        if(n>0&&n==q.length/4)resize(q.length/2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[first];
    }



    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    public static class ArrayIterator<Item> implements Iterator<Item>{
        private int i=0;

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;
        }
    }
}
