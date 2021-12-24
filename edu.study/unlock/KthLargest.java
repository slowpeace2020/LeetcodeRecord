package unlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KthLargest {
    List<Integer> list;
    int k;
    public KthLargest(int k, int[] nums) {
        list = new ArrayList<>();
        this.k = k;
        for(int num:nums){
            list.add(num);
        }
    }

    public int add(int val) {
        Collections.sort(list);
        int res =list.get(k-1);
        list.add(val);

        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,5,8,2};
        KthLargest test = new KthLargest(3,nums);
        test.add(3);
        test.add(5);
        test.add(10);
        test.add(9);
        test.add(4);
    }
}
