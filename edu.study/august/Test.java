package august;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Test {

    public int maximumScore(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];

        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<n;i++){
            while(!stack.isEmpty()&&nums[stack.peek()]>=nums[i]){
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1:stack.peek();
            stack.push(i);
        }

        stack.clear();

        for(int i=n-1;i>=0;i--){
            while(!stack.isEmpty()&&nums[stack.peek()]>nums[i]){
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n:stack.peek();
            stack.push(i);
        }

        int res = 0;
        for(int i=0;i<n;i++){
            if(left[i]<=k&&right[i]>=k){
                int val = (i-left[i]+right[i]-1-i)*nums[i];
                System.out.println(val);
                res = Math.max(res,val);
            }
        }

        return res;

    }

    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));

        for(int[] tile:tiles){
            queue.add(tile);
        }

        int[] pre = queue.poll();
        int res =   Math.min(carpetLen,pre[1]-pre[0]+1);

        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            if(cur[0]>pre[1]+1){
                res = Math.max(res,Math.min(carpetLen,pre[1]-pre[0]+1));
                pre = cur;
            }else{
                pre[1] = Math.max(pre[1],cur[1]);
            }
        }

        res = Math.max(res,Math.min(carpetLen,pre[1]-pre[0]+1));
        return res;
    }

    long solution(String[] queryType, int[][] query) {
        Map<Integer,Long> map = new HashMap<>();
        int keyOffset = 0;
        int valueOffset = 0;
        long ans = 0;
        for(int i=0;i<queryType.length;i++){
            String operate = queryType[i];
            int[] nums = query[i];
            if(operate.equals("insert")){
                int key = nums[0]-keyOffset;
                int value = nums[1]-valueOffset;
                map.put(key, (long)value);
            }
            if(operate.equals("addToKey")){
                keyOffset+=nums[0];
            }
            if(operate.equals("addToValue")){
                valueOffset+=nums[0];
            }
            if(operate.equals("get")){
                long value = map.get(nums[0])+valueOffset;

                ans = value;
            }
        }
        System.out.println(valueOffset);
        System.out.println(ans);
        return ans;

    }


    public static void main(String[] args) {
        int a,b,c;
        int d=3,e=4,f=5;
        Test test = new Test();
        test.solution(new String[]{"insert",
                "addToValue",
                "get",
                "insert",
                "addToKey",
                "addToValue",
                "get"},new int[][]{{1,2},
                {2},
                {1},
                {2,3},
                {1},
                {-1},
                {3}});
        System.out.println(d);

    }
}
