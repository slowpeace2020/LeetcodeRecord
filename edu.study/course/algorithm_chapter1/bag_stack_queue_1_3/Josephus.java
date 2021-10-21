package course.algorithm_chapter1.bag_stack_queue_1_3;

import algs4.src.main.java.edu.princeton.cs.algs4.Queue;

/**
 * 1.3.37
 * 用队列模拟约瑟夫环的过程，看最后谁能活下来
 */
public class Josephus {
    public static void main(String[] args) {
        int m = 5;
        int n = 20;
        Queue<Integer> queue = new Queue<>();
        for(int i=1;i<=n;i++){
            queue.enqueue(i);
        }
        while (!queue.isEmpty()){
            for(int i=0;i<m-1;i++){
                queue.enqueue(queue.dequeue());
            }
            System.out.println(queue.dequeue());
        }
    }
}
