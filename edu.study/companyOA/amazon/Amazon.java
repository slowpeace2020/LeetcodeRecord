package companyOA.amazon;

import java.util.*;

public class Amazon {
    public int minSubArrayLen(int target, int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,-1);
        int sum = 0;
        int res = nums.length+1;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(map.containsKey(sum-target)&&(i-map.get(sum-target)<res)){
                res = i-map.get(sum-target);
            }
            map.put(sum,i);
        }

        return res==nums.length+1 ? 0:res;
    }

    public int numberofDistinctIslands(int[][] grid) {
        // write your code here
        int count = 0;
        int m = grid.length;
        int n = grid[0].length;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    count++;
                    dfsMark(grid,i,j);
                }
            }
        }

        return count;

    }


    private void dfsMark(int[][] grid, int i,int j){
        int[][] dirs = new int[][]{{0,1},{0,-1},{-1,0},{1,0}};
        grid[i][j] = -1;
        int m = grid.length;
        int n = grid[0].length;
        for(int[] dir:dirs){
            int x= i+dir[0];
            int y = j+dir[1];
            if(x<0||y<0||x>=m||y>=n||grid[x][y]==-1||grid[x][y]==0){
                continue;
            }
            dfsMark(grid,x,y);
        }
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
        Stack<Integer> stack = new Stack<>();
        //-1 最左边界，n最右边界
        for(int i=-1;i<=n;i++){
                while (!stack.isEmpty()&&getElement(arr,n,stack.peek())>getElement(arr,n,i)){
                   int cur = stack.pop();
                   ans= (long) ((ans+(long)(cur-stack.peek())*(i-cur)*arr[cur])%(1e9+7));
                }
                stack.push(i);
        }

        return (int) ans;
    }
    public int sumSubarrayMins(int[] arr) {
        if(arr==null||arr.length==0){
            return 0;
        }

        int n = arr.length;

        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new LinkedList<>();

        for(int i=0;i<n;i++){
            while (!stack.isEmpty()&&arr[stack.peek()]>arr[i]){
                stack.pop();
            }

            if(stack.isEmpty()){
                left[i] = -1;
            }else {
                left[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();
        for(int i=n-1;i>=0;i--){
            while (!stack.isEmpty()&&arr[stack.peek()]>arr[i]){
                stack.pop();
            }

            if(stack.isEmpty()){
                right[i] = -1;
            }else {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        int ans = 0;
        for(int i=0;i<n;i++){
            ans= (int) ((ans+(long)(i-left[i])*(right[i]-i)*arr[i])%(1e9+7));
        }

        return ans;

    }

    public int minSwaps(int[] data) {
        int k=0;
        int n = data.length;
        for(int i=0;i<n;i++){
            if(data[i]==1){
                k++;
            }
        }

        int res = 0;
        int count = 0;
        for(int i=0;i<n;i++){
            if(data[i]==1){
                count++;
            }
            if(i>=k&&data[i-k]==1){
                count--;
            }
            res = Math.max(res,count);
        }
        return k-res;
    }


    public int minDifficulty(int[] jobDifficulty, int day) {
       int n = jobDifficulty.length;
       if(n<day){
           return -1;
       }

       int[][] memo = new int[n][day+1];
       for(int[] nums:memo){
           Arrays.fill(nums,-1);
       }
        return memo[n-1][day];
    }

    private int dfs(int d,int len,int[] jobDifficulty,int[][] memo){
        int n = jobDifficulty.length;
        if(d==0&&len==n){
            return 0;
        }
        if(d==0||len==n){
            return Integer.MAX_VALUE;
        }
        if(memo[len][d]!=-1){
            return memo[len][d];
        }

        int curMax = jobDifficulty[len];
        int min = Integer.MAX_VALUE;
        for(int i=len;i<n;i++){
            curMax = Math.max(curMax,jobDifficulty[i]);
            int temp = dfs(d-1,len+1,jobDifficulty,memo);
            if(temp!=Integer.MAX_VALUE){
                min = Math.min(temp+curMax,min);
            }
        }

        memo[len][d] = min;
        return min;
    }

    public int numTeams(int[] rating) {
        int n = rating.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        Stack<Integer> stackMin = new Stack<>();

        for(int i=0;i<n;i++){
            while(!stackMin.isEmpty()&&stackMin.peek()>rating[i]){
                stackMin.pop();
            }

            leftMin[i] = stackMin.size();
            leftMax[i] = i-leftMin[i];
            stackMin.push(rating[i]);
        }
        stackMin.clear();
        for(int i=n-1;i>=0;i--){
            while(!stackMin.isEmpty()&&stackMin.peek()>rating[i]){
                stackMin.pop();
            }



            rightMin[i] = stackMin.size();
            rightMax[i] = i-leftMin[i];
            stackMin.push(rating[i]);
        }

        int count = 0;
        for(int i=1;i<n-1;i++){
            count+=leftMin[i]*rightMax[i]+leftMax[i]*rightMin[i];
        }
        return count;

    }


    int mod = (int)(1e7+9);
    public int maxSum(int[] nums1, int[] nums2) {
        int n = Math.max(nums1.length,nums2.length);
        int[][] memo = new int[n][2];
        for(int i=0;i<n;i++){
            Arrays.fill(memo[i],-1);
        }

        getMaxSum(nums1,nums2,memo,0,true,new HashSet<>());
        getMaxSum(nums1,nums2,memo,0,false,new HashSet<>());
        return Math.max(memo[0][0],memo[0][1]);
    }

    private int getMaxSum(int[] nums1, int[] nums2,int[][] memo,int start, boolean inNum1,Set<Integer> set){
        if(inNum1){
            if(start>=nums1.length){
                return 0;
            }

            if(memo[start][0]!=-1){
                return memo[start][0];
            }
            boolean isAdd = false;
            if(set.add(nums1[start])){
                isAdd = true;
            }
            int otherStart = getIndex(nums2,nums1[start]);
            if(otherStart!=-1){
                memo[start][0] = (int) Math.max(memo[start][0],(getMaxSum(nums1,nums2,memo,otherStart+1,false,set))%mod);
            }

            memo[start][0] = (int) Math.max(memo[start][0],(nums1[start]+getMaxSum(nums1,nums2,memo,start+1,true,set))%mod);

            if(isAdd){
                set.remove(nums1[start]);
            }
            return memo[start][0];

        }else{
            if(start>=nums2.length){
                return 0;
            }

            if(memo[start][1]!=-1){
                return memo[start][1];
            }
            boolean isAdd = false;
            if(set.add(nums2[start])){
                isAdd = true;
            }

            int otherStart = getIndex(nums1,nums2[start]);
            if(otherStart!=-1){
                memo[start][1] = (int) Math.max(memo[start][1],(getMaxSum(nums1,nums2,memo,otherStart+1,true,set))%mod);
            }
            memo[start][1] = (int) Math.max(memo[start][1],(nums2[start]+getMaxSum(nums1,nums2,memo,start+1,false,set))%mod);
            if(isAdd){
                set.remove(nums2[start]);
            }
            return memo[start][1];
        }
    }

    private int getIndex(int[] nums, int target){
        int n = nums.length;
        int left =0;
        int right = n-1;
        while(left<=right){
            int mid =left+ (right-left)/2;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]<target){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return -1;
    }

    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        long sum = 0;
        Stack<Integer> stack = new Stack<>();
        int[] minPrev = new int[n];
        int[] maxPrev = new int[n];
        int[] minNext = new int[n];
        int[] maxNext = new int[n];

        Arrays.fill(minPrev,-1);
        Arrays.fill(maxPrev,-1);
        Arrays.fill(minNext,-1);
        Arrays.fill(maxNext,-1);

        for(int i=0;i<n;i++){
            while (!stack.isEmpty()&&nums[stack.peek()]>=nums[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                minPrev[i] = stack.peek();
            }
        }

        stack.clear();
        for(int i=0;i<n;i++){
            while (!stack.isEmpty()&&nums[stack.peek()]<=nums[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                maxPrev[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();
        for(int i=n-1;i>=0;i--){
            while (!stack.isEmpty()&&nums[stack.peek()]>nums[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                maxNext[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();
        for(int i=n-1;i>=0;i--){
            while (!stack.isEmpty()&&nums[stack.peek()]<nums[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                maxNext[i] = stack.peek();
            }
            stack.push(i);
        }

        for(int i=0;i<n;i++){
            long leftMin = i-minPrev[i];
            long rightMin = minNext[i]-i;
            long leftMax = i-maxPrev[i];
            long rightMax = maxNext[i] - i;
            sum+=(leftMax*rightMax-leftMin*rightMin)*nums[i];
        }

        return sum;
    }

    public int minimumAverageDifference(int[] nums) {
        long prefixSum = 0;
        long suffixSum = 0;
        for(int num:nums){
            suffixSum+=num;
        }

        long res = Integer.MAX_VALUE;
        int index = 0;
        for(int i=0;i<nums.length;i++){
            prefixSum+=nums[i];
            suffixSum-=nums[i];
            long preAverage = prefixSum/(i+1);
            long suffAverage = i==nums.length-1? suffixSum:suffixSum/(nums.length-i-1);
            if(Math.abs(preAverage-suffAverage)<res){
                index = i;
                res = Math.abs(preAverage-suffAverage);
            }
            if(res==0){
                return index;
            }
        }

        return index;

    }

    public int miniumGroups(int[] nums,int k){
        Arrays.sort(nums);
        int count = 1;
        int left = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]-nums[left]>k){
                count++;
                left=i;
            }
        }

        return count;
    }

    public int totalStrengthI(int[] A) {
        int res = 0, ac = 0, mod = (int)1e9 + 7, n = A.length;
        Stack<Integer> stack = new Stack<>();
        int[] acc = new int[n + 2];
        for (int r = 0; r <= n; ++r) {
            int a = r < n ? A[r] : 0;
            ac = (ac + a) % mod;
            acc[r + 1] = (ac + acc[r]) % mod;
            while (!stack.isEmpty() && A[stack.peek()] > a) {
                int i = stack.pop();
                int l = stack.isEmpty() ? -1 : stack.peek();
                long lacc = l < 0 ? acc[i] : acc[i] - acc[l], racc = acc[r] - acc[i];
                int ln = i - l, rn = r - i;
                res = (int)(res + (racc * ln - lacc * rn) % mod * A[i] % mod) % mod;
            }
            stack.push(r);
        }
        return (res + mod) % mod;
    }


    public int totalStrength(int[] strength){
        int n = strength.length;
        int[] minNext = new int[n];
        int[] minPrev = new int[n];



        Arrays.fill(minPrev,-1);
        Arrays.fill(minNext,n);

        long[] prefixSum = new long[n+1];
        for(int i=0;i<n;i++){
            prefixSum[i+1] = prefixSum[i]+strength[i];
        }


        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<n;i++){
            while(!stack.isEmpty()&&strength[stack.peek()]>=strength[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                minPrev[i] = stack.peek();
            }
            stack.push(i);

        }
        stack.clear();

        for(int i=n-1;i>=0;i--){
            while(!stack.isEmpty()&&strength[stack.peek()]>=strength[i]){
                stack.pop();
            }
            if(!stack.isEmpty()){
                minNext[i] = stack.peek();
            }
            stack.push(i);
        }

        long sum = 0;
        for(int i=0;i<n;i++){
            int left = minPrev[i];
            int right = minNext[i];
            sum+=(prefixSum[right+1]-prefixSum[left+1])*strength[i];
            sum= (long) (sum%(1e9+7));
        }


        return (int)sum;
    }
        public static void main(String[] args) {
        Amazon test = new Amazon();
//        test.maxSum(new int[]{2,4,5,8,10},new int[]{4,6,8,9});
//        test.minimumAverageDifference(new int[]{2,5,3,9,5,3});
        test.totalStrength(new int[]{1,3,1,2});
//        test.getIndex(new int[]{2,4,5,8,10},8);
//        test.numTeams(new int[]{3,6,7,5,1});

            if("a".equals("b")){

            }
    }


}
