package august;

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


    public static void main(String[] args) {
        int a,b,c;
        int d=3,e=4,f=5;
        Test test = new Test();
        test.maximumScore(new int[]{1,4,3,7,4,5},3);
        System.out.println(d);

    }
}
