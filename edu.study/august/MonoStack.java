package august;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class MonoStack {
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        int[] rightMin = new int[n];

        Stack<Integer> stack = new Stack<>();

        for(int i=0;i<n;i++){
            while(!stack.isEmpty()&&stack.peek()>=nums[i]){
                stack.pop();
            }
            leftMax[i] = stack.size();
            stack.push(nums[i]);
        }


        stack.clear();
        for(int i=n-1;i>=0;i--){
            while(!stack.isEmpty()&&stack.peek()>=nums[i]){
                stack.pop();
            }
            rightMin[i] = stack.size();
            stack.push(nums[i]);
        }

        int res = 3;

        for(int i=1;i<n-1;i++){
            if(leftMax[i]+rightMin[i]+1==n){
                return 0;
            }
            res = Math.max(res,leftMax[i]+rightMin[i]+1);
        }


        return n-res;
    }

    private int getElement(int[] arr,int n,int i){
        if(i==-1||i==n){
            return Integer.MIN_VALUE;
        }

        return arr[i];
    }
    public int sumSubarrayMinsI(int[] arr) {
        if(arr==null||arr.length==0){
            return 0;
        }

        int n = arr.length;
        long ans = 0;
        Deque<Integer> stack = new LinkedList<>();
        for(int i=-1;i<=n;i++){
            while (!stack.isEmpty()&&getElement(arr,n,stack.peek())>getElement(arr,n,i)){
                int cur = stack.poll();
                ans = (long) ((ans+(long)(cur-stack.peek())*(i-cur)*arr[cur])%(1e9+7));
            }
        }

        return (int) ans;
    }
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];
        Stack<Integer> stack = new Stack<>();

        for(int i=0;i<n;i++){
            while(!stack.isEmpty()&&arr[stack.peek()]>=arr[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                leftMin[i] = stack.peek();
            }else{
                leftMin[i] = -1;
            }
            leftMin[i]--;
            stack.push(i);
        }

        stack.clear();

        for(int i=n-1;i>=0;i--){
            while(!stack.isEmpty()&&arr[stack.peek()]>arr[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                rightMin[i] = stack.peek();
            }else{
                rightMin[i] = n;
            }
            rightMin[i]--;
            stack.push(i);
        }

        long sum =0;
        int mod = (int)(1e9+7);
        for(int i=0;i<n;i++){
            sum=(sum+(long)(i-leftMin[i])*(rightMin[i]-rightMin[i])*arr[i])%mod;
        }

        return (int) sum;

    }

    public static void main(String[] args) {
        MonoStack test = new MonoStack();
        test.sumSubarrayMins(new int[]{3,1,2,4});
    }
}
