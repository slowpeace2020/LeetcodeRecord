package unlock;

import java.util.*;

public class StockPrice {
    TreeMap<Integer, Integer> tm = new TreeMap<>();
    TreeMap<Integer, Set<Integer>> valueTimeStamp = new TreeMap<>();
    public StockPrice() {
        tm = new TreeMap<>();
        valueTimeStamp = new TreeMap<>();
    }

    public void update(int timestamp, int price) {
        if(tm.containsKey(timestamp)){//update to existing value - correction
            int oldPrice = tm.get(timestamp);
            valueTimeStamp.get(oldPrice).remove(timestamp);
            if(valueTimeStamp.get(oldPrice).size()==0)//if only one timestamp had the value and it later got updated
                valueTimeStamp.remove(oldPrice);
        }
        tm.put(timestamp, price);
        valueTimeStamp.putIfAbsent(price, new HashSet<>());
        valueTimeStamp.get(price).add(timestamp);
    }

    public int current() {
        return tm.get(tm.lastKey());
    }

    public int maximum() {
        return valueTimeStamp.lastKey();
    }

    public int minimum() {
        return valueTimeStamp.firstKey();
    }

    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while(left<=right){
            int mid = left+(right-left)/2;
            if(nums[mid]==target){
                return true;
            }

            if(nums[mid]<nums[right]){
                if(nums[mid]<target&&target<=nums[right]){
                    left = mid+1;
                }else {
                    right = mid-1;
                }
            }else if(nums[mid]>nums[right]){
                  if(nums[left]<=target&&target<nums[mid]){
                      right = mid-1;
                  }else {
                      left = mid+1;
                  }
            }else {
                right--;
            }
        }


        return false;
    }

    public double findMedianSortedArraysI(int[] A, int[] B) {
            int m = A.length;
            int n = B.length;
            if(m>n){
                return findMedianSortedArraysI(B,A);
            }

            //找到中位数
            int imin = 0;
            //中位数的index范围 去较小的数组中去排除范围
            int imax = m;
            while (imin<imax){
                int i=imin+(imax-imin)/2;
                int j = (m+n+1)/2-i;
                //把A分成[0,i-1]和[i,m-1]两部分
                int A_left = i==0?Integer.MIN_VALUE:A[i-1];
                int A_right = i==m?Integer.MAX_VALUE:A[i];
                int B_left = j==0?Integer.MIN_VALUE:B[i-1];
                int B_right = j==n?Integer.MAX_VALUE:B[i];
                if(A_left>B_right){
                    imax= i-1;
                }else if(B_left>A_right){
                    imin = i+1;
                }else {
                    int maxLeft = A_left>B_left ?A_left:B_left;
                    int minRight = A_right>B_right?B_right:A_right;
                    if((m+n)%2==1){
                        return maxLeft;
                    }else {
                        return (maxLeft+minRight)/2.0;
                    }
                }
            }

            return -1;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length, left = (m + n + 1) / 2, right = (m + n + 2) / 2;
        //两个数组中同时找中间的两个数
        return (findKth(nums1, nums2, left) + findKth(nums1, nums2, right)) / 2.0;
    }
    int findKth(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        //另一个数组全部排除，那么相对位置就是当前数组的index
        if (m == 0) return nums2[k - 1];
        if (n == 0) return nums1[k - 1];
        if (k == 1) return Math.min(nums1[0], nums2[0]);
        //取两个数组中的第k/2个数去比较，防止数组越界
        int i = Math.min(m, k / 2), j = Math.min(n, k / 2);
        //  xxxxx k/2  xxxxxxx    nums1[i]
        //  yyyyy k/2  yyyyyyyyy  nums2[j]
        //若nums1[i - 1] > nums2[j - 1]， 那么nums2的前k/2个数不可能是中位数，
        //减去这些数占的数量，就是中位数在新数组中的相对位置
        if (nums1[i - 1] > nums2[j - 1]) {
            return findKth(nums1, Arrays.copyOfRange(nums2, j, n), k - j);
        } else {
            return findKth(Arrays.copyOfRange(nums1, i, m), nums2, k - i);
        }
    }

    public int findPeakElement(int[] nums) {
        int left = 0;
        int right=nums.length-1;
        //只会在有效范围内
        while (left<right){
            int mid = left + (right - left) / 2;
            //只和右边比，上升趋势，说明峰值在右边
            if(nums[mid]<nums[mid+1]){
                left = mid+1;
            }else {
                //否则在左边，或者是本身
                right = mid;
            }
        }

        //左右相等时
        return right;
    }

    public int minArea(char[][] image, int x, int y) {
        if(image==null||image.length==0||image[0].length==0){
            return 0;
        }

        int n = image.length;
        int m = image[0].length;
        int left =0;
        int right =0;
        int top =0;
        int bottom =0;
        //左边界
        int l = 0;
        int r = y;

        while (l+1<r){
            int mid = l+(r-l)/2;
            if(checkColumn(image,mid)){
                r = mid;
            }else {
                l = mid;
            }
        }

        if(checkColumn(image,l)){
            left = l;
        }else {
            left = r;
        }

        l = y;
        r = m-1;
        while (l+1<r){
            int mid = l+(r-l)/2;
            if(checkColumn(image,mid)){
                l = mid;
            }else {
                r = mid;
            }
        }

        if(checkColumn(image,r)){
            right = r;
        }else {
            right = l;
        }

        //二分最上侧黑色像素坐标

        l = 0;

        r = x;

        while (l + 1 < r) {

            int mid = l + (r - l) / 2;

            if (checkRow(image, mid, left, right)) {

                r = mid;

            } else {

                l = mid;

            }

        }



        if (checkRow(image, l, left, right)) {

            top = l;

        }else{

            top = r;

        }

        //二分最下侧黑色像素坐标

        l = x;

        r = n - 1;

        while (l + 1 < r) {

            int mid = l + (r - l) / 2;

            if (checkRow(image, mid, left, right)) {

                l = mid;

            } else {

                r = mid;

            }

        }
        if (checkRow(image, r, left, right)) {

            bottom = r;

        }else{

            bottom = l;

        }

        return (right - left + 1) * (bottom - top + 1);

    }


    private boolean checkColumn(char[][] image,int col){
        for(int i=0;i<image.length;i++){
            if(image[i][col]=='1'){
                return true;
            }
        }

        return false;
    }

    private boolean checkRow(char[][] image,int row,int left ,int right){
        for(int j=left;j<=right;j++){
            if(image[row][j]=='1'){
                return true;
            }
        }

        return false;
    }

    public double sqrt(double x) {
        // write your code here
        double left = 0.0;
        double right = Math.max(x,1/x);
        while(left+(1e-12)<right){
            double mid = left+(right-left)/2.0;
            if(mid*mid==x){
                return mid;
            }else if(mid*mid<x){
                left = mid;
            }else{
                right = mid;
            }
        }
        return right;
    }

    public int splitArray(int[] nums, int m) {
        int left = 0;
        int right =0;

        for(int num:nums){
            if(left<num){
                left = num;
            }
            right+=num;
        }

        while (left<right){
            int mid = left+(right-left)/2;
            if(isValidII(nums,mid,m)){
                right = mid;
            }else {
                //子数组和最大值为mid的分组数量超过m组，说明mid其实比分成m组的最大值小
                left=mid+1;
            }

        }

        return left;

    }

    public boolean isValidII(int[] nums,int target,int m){
        int count =1;
        int sum = 0;
        for(int num:nums){
            //必须大于target才新增一个子数组，等于还是符合条件的
            if(sum+num>target){
                count++;
                sum = num;
            }else {
                sum+=num;
            }

            //最大值为target的分组数量超过m组，说明target其实比分成m组的最大值小
            if(count>m){
                return false;
            }
        }

        return true;
    }

    public int maxSumSubmatrix(int[][] matrix, int target) {
        int row = matrix.length;
        if(row==0){
            return 0;
        }
        int res=0;
        int col = matrix[0].length;
        //从（0，0）当前坐标的和
        int[][] sum = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int t = matrix[i][j];
                if(i>0){
                    t+=sum[i-1][j];
                }
                if(j>0){
                    t+=sum[i][j-1];
                }
                //减去重叠部分
                if(i>0&&j>0){
                    t-=sum[i-1][j-1];
                }
                sum[i][j] = t;

                //从（0，0）到当前坐标范围之内的切割的小矩形和
                //（r,c）和(i,j)为对角线的两个端点
                for(int r=0;r<=i;r++){
                    for(int c=0;c<=j;c++){
                        int d = sum[r][j];
                        if(r>0)d-=sum[r-1][j];
                        if(c>0)d-=sum[i][c-1];
                        if(r>0&&c>0){
                            d+=sum[r-1][c-1];
                        }
                        if(d<=target){
                            res = Math.max(res,d);
                        }
                    }
                }
            }
        }

        return res;

    }

    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = 0;
        for(int i=0;i<houses.length;i++){
            int house = houses[i];
            int left = 0;
            int right = heaters.length;
            while (left<right){
                int mid = left+(right-left)/2;
                if(heaters[mid]<house){
                    left=mid+1;
                }else {
                    right = mid;
                }
            }

            int leftDistance = left==0? Integer.MAX_VALUE:house-heaters[left-1];
            int rightDistance = left==heaters.length? Integer.MAX_VALUE:heaters[left]-house;
            res = Math.max(res,Math.min(leftDistance,rightDistance));

        }

        return res;
    }




    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if(n<m*k){
            return -1;
        }
        int left =1;
        int right = (int)(1e9);
        while(left<right){
            int mid = left+(right-left)/2;
            if(isValidI(bloomDay,mid,m,k)){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return left;
    }

    public boolean isValidI(int[] bloomDay,int target, int m, int k){
        int count =0;
        for(int i=0;i<bloomDay.length;i++){
            if(bloomDay[i]<=target){
                count++;
            }else{
                count=0;
            }
            if(count==k){
                m--;
                count=0;
            }

            if(m==0){
                return true;
            }
        }

        return false;
    }

    public int arrangeCoins(int n) {
        long left =1;
        long right = n;
        while(left<right){
            long mid = left+(right-left)/2;
            long sum = ((mid+1)*mid)/2;
            if(sum==n){
                return (int)mid;
            }else if(sum>n){
                right = mid-1;
            }else{
                if(sum+mid>=n){
                    return (int) mid;
                }
                left = mid+1;
            }
        }

        return (int)left;
    }


    public int numSubseq(int[] nums, int target) {
            Arrays.sort(nums);
            int res =0;
            int mod = (int) 1e9;
            int[] powDP = new int[nums.length];
            powDP[0] = 1;
            //It is impossible to use 2^n in Java when n is very large
            //we need to use DP-method to pre-calculate 2^n % 1e9+7
            for(int i=0;i<nums.length;i++){
                powDP[i] = (powDP[i-1]*2)%mod;
            }

            for(int start =0;start<nums.length;start++){
                int left=start;
                int right = nums.length-1;
                int end =-1;
                while (left<=right){
                    int mid = left+(right-left)/2;
                    if(nums[start]+nums[mid]<=target){
                        left = mid+1;
                        end=mid;
                    }else {
                        right = mid-1;
                    }
                }

                if(end==-1){
                    break;
                }

                res=(res+powDP[end-start])%mod;

            }

            return res;
    }


    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int left =0;
        int right = nums[n-1]-nums[0];
        while(left<right){
            int mid = left+(right-left)/2;
            if(isValid(nums,k,mid)){
                right = mid;
            }else{
                left=mid+1;
            }
        }
        return left;
    }


    public boolean isValid(int[] nums, int k, int target){
        int n = nums.length;
        int count =0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(nums[j]-nums[i]<=target){
                    count++;
                }
                if(count>=k){
                    return true;
                }
            }
        }

        return false;
    }


    public int findKthNumber(int m, int n, int k) {
        int left =1;
        int right = m*n;
        while(left<right){
            int mid = left+(right-left)/2;
            int count = count(mid,m,n);
            if(count<k){
                left=mid+1;
            }else{
                right=mid;
            }

        }

        return left;

    }

    public int count(int target,int m,int n){
        int count =0;
        for(int i=1;i<=m;i++){
            int temp =Math.min(target/i,n);
            count+=temp;
        }

        return count;
    }

    public int nthMagicalNumber(int n, int a, int b) {
        long left =Math.min(a,b);
        long right = (long) Math.pow(left,n);

        int gcd = getGcd(a,b);
        int multip = a*b/gcd;
        while(left<right){
            long mid = left+(right-left)/2;
            int count = (int) (mid/a+(mid/b)-(mid/multip));
            if(count>=n){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return (int) left;
    }

    public int getGcd(int a,int b){
        if(a<b){
            return getGcd(b,a);
        }

        while(b>0){
            int temp = b;
            b = a%b;
            a = temp;
        }

        return a;

    }


    public double minmaxGasDist(int[] stations, int K) {
        int n = stations.length;
        double left = 0;
        double right = stations[n-1]-stations[0];
        while (left+(1e-6)<right){
            double mid = left+(right-left)/2;
            if(isValidGasDist(stations,K,mid)){
                right = mid;
            }else {
                left = mid;
            }

        }

        return left;
    }

    public boolean isValidGasDist(int[] stations, int K,double target){
        for(int i=1;i<stations.length;i++){
            if(stations[i]-stations[i-1]>target){
                K-=Math.ceil((stations[i]-stations[i-1])/target-1);
            }

            if(K<0){
                return false;
            }
        }

        return true;
    }


    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        double left =0;
        double right=1;
        //p-q为范围
        int p=0;
        int q=1;
        int count =0;
        int n =arr.length;
        while (true){
            double mid = left+(right-left)/2.0;
            count=0;
            p = 0;
            for(int i=0,j=0;i<n;i++){
                //跳过数组组合中比mid大的数
                while (j<n&&arr[i]>mid*arr[j]){
                    j++;
                }

                //剩下的组合长度为相除<=mid的组合
                count+=n-j;
                //把p,q更新为数组中的元素
                if(j<n&&p*arr[i]<q*arr[j]){
                    p = arr[i];
                    q=arr[j];
                }
            }

            //找到了
            if(count==k){
                return new int[]{p,q};
            }

            if(count<k){
                left = mid;
            }else {
                right = mid;
            }

        }
    }


    public int kthSmallest(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left =matrix[0][0];
        int right = matrix[m-1][n-1];
        while(left<right){
            int mid = left+(right-left)/2;
            int i=m;
            int j=0;
            int count =0;
            while(i>=1&&j<n){
                if(matrix[i-1][j]<=mid){
                    j++;
                    count+=i;
                }else{
                    i--;
                }
            }

            if(count<k){
                left = mid+1;
            }else{
                right = mid;
            }
        }

        return left;
    }

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int res =1;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                int len = dfs(matrix,new boolean[m][n],i,j,-1);
                res =Math.max(res,len);
            }
        }
        return res;
    }

    public int dfs(int[][] matrix, boolean[][] visited, int x,int y,int pre){
        int m = matrix.length;
        int n = matrix[0].length;
        if(x<0||y<0||x>=m||y>=n||visited[x][y]||matrix[x][y]>=pre){
            return 0;
        }

        int res =1;
        visited[x][y] = true;
        int val = matrix[x][y];
        int up = dfs(matrix,visited,x-1,y,val);
        int down = dfs(matrix,visited,x+1,y,val);
        int left = dfs(matrix,visited,x,y-1,val);
        int right = dfs(matrix,visited,x,y+1,val);
        int max = Math.max(Math.max(up,down),Math.max(left,right));
        res+=max;
        visited[x][y] = false;
        return res;

    }



    public static void main(String[] args) {
        StockPrice stockPrice = new StockPrice();
        stockPrice.kthSmallest(new int[][]{{1,5,9},{10,11,13},{12,13,15}},8);
//        stockPrice.maxAverage(new int[]{1,12,-5,-6,50,3}, 3);
    }
}
