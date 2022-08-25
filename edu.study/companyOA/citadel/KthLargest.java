package companyOA.citadel;

import java.util.PriorityQueue;

public class KthLargest {
//    int k;
//    PriorityQueue<Integer> left = new PriorityQueue<>((a,b)->(b-a));
//    public KthLargest(int k, int[] nums) {
//        this.k = k;
//        for(int num:nums){
//            add(num);
//        }
//    }
//
//    public int add(int val) {
//        if(left.size()<k){
//            left.add(val);
//        }else{
//            if(left.peek()<val){
//                left.poll();
//                left.add(val);
//            }
//        }
//
//        return left.peek();
//    }

    PriorityQueue<Integer> pq;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        pq = new PriorityQueue<>();
        for (int x : nums) {
            add(x);
        }
    }

    public int add(int val) {
        pq.offer(val);
        if (pq.size() > k) {
            pq.poll();
        }
        return pq.peek();
    }

    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        kthLargest.add(3);   // return 4
        kthLargest.add(5);   // return 5
        kthLargest.add(10);  // return 5
        kthLargest.add(9);   // return 8
        kthLargest.add(4);   // return 8
    }
}
