package unlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class DataStructure {


    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        //大顶堆存小的元素，这样堆顶是最大的元素，整个队列是左半边的元素
        PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
        //小顶堆存大的元素，这样堆顶是最小的元素，整个队列是右半边的元素
        PriorityQueue<Integer> right = new PriorityQueue<>();

        for (int i = 0; i < nums.length; i++) {
            //左右半边元素个数保持平衡
            if (left.size() <= right.size()) {
                right.add(nums[i]);
                left.add(right.poll());
            } else {
                left.add(nums[i]);
                right.add(left.poll());
            }


            //总共k个元素，则求median值
            if (left.size() + right.size() == k) {
                double median;
                //偶数个
                if (left.size() == right.size()) {
                    median = (double) ((long) left.peek() + (long) right.peek()) / 2;
                } else {//奇数个
                    median = (double) left.peek();
                }

                int start = i - k + 1;
                result[start] = median;
                //直接移除开头的元素
                if (!left.remove(nums[start])) {
                    right.remove(nums[start]);
                }
            }
        }
        return result;
    }

    class KthLargest {
        PriorityQueue<Integer> queue;
        int k;

        public KthLargest(int k, int[] nums) {
            queue = new PriorityQueue<>(k);
            this.k = k;
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            if (queue.size() < k) {
                queue.offer(val);
            } else if (queue.peek() < val) {
                queue.poll();
                queue.offer(val);
            }
            return queue.peek();
        }
    }
}
