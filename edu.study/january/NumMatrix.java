package january;

import java.util.*;

public class NumMatrix {
    int[][] matrix;
    int[][] prefixSum;
    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
        int m = matrix.length;
        int n = matrix[0].length;
        this.prefixSum = new int[m+1][n+1];

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                prefixSum[i+1][j+1] = prefixSum[i+1][j]+prefixSum[i][j+1]+matrix[i][j]-prefixSum[i][j];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = prefixSum[row2+1][col2+1]-prefixSum[row1][col2+1]-prefixSum[row2+1][col1]+prefixSum[row1][col1];
        return res;
    }

    public boolean splitArray(List<Integer> nums) {
        int n = nums.size();
        int[] prefixSum = new int[n];
        prefixSum[0] = nums.get(0);
        for(int i=1;i<n;i++){
            prefixSum[i]=prefixSum[i-1]+nums.get(i);
        }

        for(int j=3;j<n-2;j++){
            Set<Integer> set = new HashSet<>();
            for(int i=1;i+1<j;i++){
                if(prefixSum[i-1]==prefixSum[j-1]-prefixSum[i]){
                    set.add(prefixSum[i-1]);
                }
            }

            for(int k=j+1;k<n-1;k++){
                if(prefixSum[k-1]-prefixSum[j]==prefixSum[n-1]-prefixSum[k]){
                    if(set.contains(prefixSum[k-1]-prefixSum[j])){
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public int pivotIndex(int[] nums) {

        int n =nums.length;
        int[] prefixSum = new int[n+1];
        for(int i=1;i<=n;i++){
            prefixSum[i]=prefixSum[i-1]+nums[i-1];
        }

        for(int i=0;i<n;i++){
            if(prefixSum[i]==prefixSum[n]-prefixSum[i+1]){
                return i;
           }
        }

        return -1;

    }
    public int bestRotation(int[] nums) {
        int n = nums.length;
        int r1, r2;
        int[] diff = new int[n];
        for(int i = 0; i<n; i++) {
            r1 = Math.min(i, i - nums[i]);
            if(r1 >= 0) { // [0, min(i, i-nums[i])]
                diff[0]++;
                if(r1 < n-1) {
                    diff[r1 + 1]--;
                }
            }
            r2 = Math.min(n - 1, n + i - nums[i]);
            if(r2 < n && r2 > i) {
                diff[i+1]++;
                if(r2 < n-1) {
                    diff[r2 + 1]--;
                }
            }
        }


        int ans = 0;
        int maxScore = 0;
        int acc = 0;
        for(int i = 0; i < n; i++) {
            acc += diff[i];
            if(acc > maxScore) {
                maxScore = acc;
                ans = i;
            }
        }
        return ans;
    }


    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int[][] sum = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        int res =0;
        for(int top=1;top<=n;top++){//矩形上界
            for(int bottom=top;bottom<=n;bottom++){//矩形下界
                int cur = 0;
                Map<Integer,Integer> map = new HashMap<>();
                for(int k=1;k<=m;k++){//矩形左右边
                    cur = sum[bottom][k]-sum[top-1][k];
                    if(cur==target){
                        res++;
                    }
                    if(map.containsKey(cur-target)){
                        res = map.get(cur-target);
                    }

                    map.put(cur,map.getOrDefault(cur,0)+1);
                }
            }
        }


        return res;
    }

    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n+1];
        for(int[] book:bookings){
            diff[book[0]]+=book[2];
            if(book[1]!=n){
                diff[book[1]+1]-=book[2];
            }
        }

        int[] res = new int[n];
        res[0]=diff[1];
        for(int i=1;i<n;i++){
            res[i]=res[i-1]+diff[i+1];
        }
        return res;
    }

    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        //0-n范围内每个字母出现次数
        int[][] count = new int[n+1][26];
        for(int i=0;i<n;i++){
            count[i+1] =count[i].clone();
            count[i+1][s.charAt(i)-'a']++;
        }

        List<Boolean> res = new ArrayList<>();
        for(int[] query:queries){
            int i=query[0];
            int j=query[1];
            int k = query[2];

            int sum=0;
            for(int t=0;t<26;t++){
                sum+=(count[j+1][t]-count[i][t])%2;
            }

            if(sum/2<=k){
                res.add(true);
            }else {
                res.add(false);
            }

        }

        return res;

    }

        public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {3,0,1,4,2},
                {5,6,3,2,1},
                {1,2,0,1,5},
                {4,1,0,1,7},
                {1,0,3,0,5}};
        NumMatrix test = new NumMatrix(matrix);
//        test.sumRegion(1,1,2,2);

            String str = "abcda";
            int[][] queries = new int[][]{{3,3,0},{1,2,0},{0,3,1},{0,3,2},{0,4,1}};

        test.canMakePaliQueries(str,queries);
    }


}
