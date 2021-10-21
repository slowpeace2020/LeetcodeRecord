package course;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PQ_UnorderedArray<Key extends Comparable<Key>> {
    private final Key[] pq; //pq[i]=ithelementonpq
    private int M = 0; // number of elements on pq

    public PQ_UnorderedArray(int capacity) {
        pq = (Key[]) new Comparable[capacity];
    }

    public boolean isEmpty() {
        return M == 0;
    }

    public void insert(Key x) {
        pq[M++] = x;
    }

//    public Key remove() {
//
//        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(5, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return 0;
//            }
//        });
//
//
//        new PriorityQueue<String>(5, (a,b) -> a.length() - b.length());
//        int max = 0;
//        for (int i = 1; i < M; i++)
//            if (Key[max) max = i;
//        swap(max, M - 1);
//        return pq[—M];
//    }
}
