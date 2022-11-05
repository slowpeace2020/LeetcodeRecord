package septemper;

import java.util.Arrays;
import java.util.Stack;

public class CountIntervals {
    class SegNode{
        int start;
        int end;
        int info;
        SegNode leftNode;
        SegNode rightNode;
        public SegNode(int start,int end){
            this.start = start;
            this.end= end;
            this.info = 0;
        }

        int count(){
            return info;
        }

        int size(){
            return end-start+1;
        }

        int mid(){
            return (start+end)>>1;
        }

        SegNode getLeftNode(){
            if(leftNode==null){
                leftNode = new SegNode(start,mid());
            }
            return leftNode;
        }

        SegNode getRightNode(){
            if(rightNode==null){
                rightNode = new SegNode(mid()+1,end);
            }

            return rightNode;
        }

        public void add(int left,int right){
            if(size()==count()){
                return;
            }

            if(left<=start&&right>=end){
                info = size();
                return;
            }

            if(left<=mid()){
                getLeftNode().add(left,right);
                info +=leftNode.info;
            }

            if(right>mid()){
                getRightNode().add(left,right);
                info +=rightNode.info;
            }



        }

    }



    SegNode root;
    public CountIntervals() {
        root = new SegNode(1,(int)(1e9));
    }

    public void add(int left, int right) {
        root.add(left,right);
    }

    public int count() {
        return root.info;
    }

    public int numSubseq(int[] A, int target) {
        Arrays.sort(A);
        int n = A.length;
        int[] dp = new int[n];
        dp[0]=1;
        int mod = (int) (1e9+7);
        int left = 0;
        int right = n-1;
        for(int i=1;i<n;i++){
            dp[i]=(dp[i-1]*2)%mod;
        }

        int res = 0;
        while (left<=right){
            if(A[left]+A[right]>target){
                right--;
            }else {
                //从左边小的开始，肯定在组合中，后面的每个元素可选可不选，累计组合数量
                res = (res+dp[right-left])%mod;
                left++;
            }
        }

        return res;
    }

    public int maxDistance(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();
        for(int i=n-1;i>=0;i--){
            while(!stack2.isEmpty()&&nums2[stack2.peek()]>nums2[i]){
                stack2.pop();
            }
            stack2.push(i);
        }

        int ans = 0;
        for(int i=0;i<m;i++){
            if(stack2.isEmpty()){
                break;
            }else{
                while (stack2.peek()<i){
                    stack2.pop();
                }
            }
            if(stack.isEmpty()||nums1[stack.peek()]>nums1[i]){

                if(!stack2.isEmpty()&&nums2[stack2.peek()]>=nums1[i]){
                    int j = stack2.peek();
                    while(!stack2.isEmpty()&&nums2[stack2.peek()]>=nums1[i]){
                        j = stack2.pop();
                    }

                    ans = Math.max(ans,j-i);
                    stack.push(i);
                }

            }

        }

        return ans;
    }

    public int bestRotation(int[] nums) {
        int k = 0;
        int score = 0;
        int n = nums.length;
        for(int i=0;i<n;i++){
            int cur=0;
            for(int j=0;j<n;j++){
                if(nums[j]<=(n+j-i)%n){
                    cur++;
                }
            }
            if(cur>score){
                k=i;
                score = cur;
            }
        }

        return k;
    }

    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int n = nums.length;
        int[] count = new int[n];
        for(int[] req:requests){
            int start = req[0];
            int end = req[1]+1;
            count[start]++;
            if(end<n){
                count[end]--;
            }
        }

        for(int i=1;i<n;i++){
            count[i]+=count[i-1];
        }

        Arrays.sort(nums);
        Arrays.sort(count);
        long res = 0;
        long mod = (long)1e9 + 7;
        for(int i=0;i<n;i++){
            if(count[i]>0){
                res += (long)nums[i] * count[i];
            }
        }

        return (int)(res % mod);
    }

        public static void main(String[] args) {
            System.out.printf("Laptop %d%n", 1);
            System.out.printf("Laptop %d%n", 2);
            System.out.printf("Laptop %d%n", 3);
        CountIntervals countIntervals = new CountIntervals();
        countIntervals.maxSumRangeQuery(new int[]{1,2,3,4,5},
            new int[][]{{1,3},{0,1}});
    }
}
