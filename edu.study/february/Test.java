package february;

import algs4.src.main.java.edu.princeton.cs.algs4.Stack;

import java.util.*;

public class Test {
    public int partitionArray(int[] nums, int k) {
        int left = 0;
        int right = nums.length-1;
        while (left<right){
            while (left<right&&nums[left]<k){
                left++;
            }

            while (left<right&&nums[right]>=k){
                right--;
            }
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right]=temp;
            left++;
            right--;
        }

        return left;
    }

    public void partition2(int[] nums, int low, int high) {
            int i=0;
            int n = nums.length;
            int j =n-1;

            while (i<j){
                while (i<j&&nums[i]<low){
                    i++;
                }
                while (i<j&&nums[j]>=low){
                    j--;
                }
                if(i==j){
                    break;
                }

                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j--;
            }

            j=n-1;
        while (i<j){
            while (i<j&&nums[i]<=high){
                i++;
            }
            while (i<j&&nums[j]>high){
                j--;
            }
            if(i==j){
                break;
            }
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }

    }

    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> res = new ArrayList<>();
        int i=0;
        int j=0;
        int m = firstList.length;
        int n = secondList.length;
        while (i<m&&j<n){
            while (i<m&&j<n&&secondList[j][1]<firstList[i][0]){
                j++;
            }
            while (i<m&&j<n&&firstList[i][1]<secondList[j][0]){
                i++;
            }

            if(i<m&&j<n){

                if(Math.max(secondList[j][0],firstList[i][0])<=Math.min(secondList[j][1],firstList[i][1])){
                    res.add(new int[]{Math.max(secondList[j][0],firstList[i][0]), Math.min(secondList[j][1],firstList[i][1])});
                }
                if(secondList[j][1]<firstList[i][1]){
                    j++;
                }else if(secondList[j][1]>firstList[i][1]){
                    i++;
                }else {
                    i++;
                    j++;
                }

            }

        }

        int[][] arr = new int[res.size()][2];
        for(int k=0;k<res.size();k++){
            arr[k] = res.get(k);
        }
        return arr;
    }

    public int minCost(int[][] costs) {
        int n = costs.length;
        int[][] dp = new int[n][3];
        for(int i=0;i<3;i++){
            dp[0][i]=costs[0][i];
        }

        for(int i=1;i<n;i++){
            dp[i][0] =Math.min(dp[i-1][1],dp[i-1][2])+costs[i][0];
            dp[i][1] =Math.min(dp[i-1][0],dp[i-1][2])+costs[i][1];
            dp[i][2] =Math.min(dp[i-1][1],dp[i-1][0])+costs[i][2];
        }

        return Math.min(dp[n-1][0],Math.min(dp[n-1][1],dp[n-1][2]));
    }

    public int minCostII(int[][] costs) {
        int n = costs.length;
        int k = costs[0].length;
        int firstMin = Integer.MAX_VALUE;
        int firstColor = -1;
        int secondColor = -1;
        int secondMin = Integer.MAX_VALUE;
        int[][] dp = new int[n][k];
        for(int i=0;i<k;i++){
            dp[0][i] = costs[0][i];
            if(dp[0][i]<firstMin){
                firstMin = dp[0][i];
                firstColor = i;
            }else if(dp[0][i]<secondMin){
                secondMin = dp[0][i];
                secondColor = i;
            }
        }

        for(int i=1;i<n;i++){
            int nextFirstMin = Integer.MAX_VALUE;
            int nextFirstColor = -1;
            int nextSecondColor = -1;
            int nextSecondMin = Integer.MAX_VALUE;
            for(int j=0;j<k;j++){
                if(j==firstColor){
                    dp[i][j] = dp[i-1][secondColor]+costs[i][j];
                }else {
                    dp[i][j] = dp[i-1][firstColor]+costs[i][j];
                }
                if(dp[i][j]<nextFirstMin){
                    nextFirstMin = dp[i][j];
                    nextFirstColor = j;
                }else if(dp[i][j]<nextSecondMin){
                    nextSecondMin = dp[i][j];
                    nextSecondColor = j;
                }
            }

            firstColor = nextFirstColor;
            firstMin = nextFirstMin;
            secondColor = nextSecondColor;
            secondMin = nextSecondMin;
        }

        return firstMin;
    }

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for(int j=1;j<=n;j++){
            if(p.charAt(j-1)=='*'){
                dp[0][j]=dp[0][j-1];
            }
        }

        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s.charAt(i-1)==p.charAt(j-1)||p.charAt(j-1)=='?'){
                    dp[i][j] = dp[i-1][j-1];
                }else if(p.charAt(j-1)=='*'){
                    dp[i][j]=dp[i-1][j]||dp[i][j-1];

                }
            }
        }

        return dp[m][n];
    }


    public int sumOfBeauties(int[] nums) {
        int n = nums.length;
        int res =0;
        int[] left = new int[n];
        int max = nums[0];

        for(int i=1;i<n-1;i++){
            if(nums[i]>max){
                left[i]=i;
                max = nums[i];
            }
        }

        int  min = nums[n-1];
        boolean[] mark = new boolean[n];
        for(int i=n-2;i>=1;i--){
            if(nums[i]<min){
                if(left[i]!=0){
                    res+=2;
                    mark[i]=true;
                }
                min= nums[i];
            }
        }

        for(int i=1;i<=n-2;i++){
            if(nums[i-1]<nums[i]&&nums[i]<nums[i+1]&&!mark[i]){
                res++;
            }
        }

        return res;
    }
    Map<String,Boolean> memoScramble = new HashMap<>();
    public boolean isScramble(String s1, String s2) {
            if(memoScramble.containsKey(s1+s2)){
                return memoScramble.get(s1+s2);
            }
           if(s1.equals(s2)){
                memoScramble.put(s1+s2,true);
                return true;
           }

           int[] letter = new int[26];
           for(int i=0;i<s1.length();i++){
               letter[s1.charAt(i)-'a']++;
               letter[s2.charAt(i)-'a']--;
           }

           for(int i=0;i<26;i++){
               if(letter[i]!=0){
                   memoScramble.put(s1+s2,false);
                   return false;
               }
           }

           for(int i=1;i<=s1.length();i++){
               if(isScramble(s1.substring(0,i),s2.substring(0,i))&&isScramble(s1.substring(i),s2.substring(i))){
                   memoScramble.put(s1+s2,true);
                   return true;
               }

               if(isScramble(s1.substring(0,i),s2.substring(s2.length()-i))&&isScramble(s1.substring(i),s2.substring(0,s1.length()-i))){
                   memoScramble.put(s1+s2,true);
                   return true;
               }

           }

           memoScramble.put(s1+s2,false);
           return false;
    }


    int uniquePaths = 0;
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int x =0;
        int y=0;
        int steps = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                  x=i;
                  y=j;
                }else if(grid[i][j]!=-1){
                    steps++;
                }
            }
        }
        boolean[][] visited = new boolean[m][n];
        visited[x][y] = true;
        dfsSearch(grid,x,y,steps,visited);
        return uniquePaths;
    }

    private void dfsSearch(int[][] grid,int x,int y,int steps, boolean[][]visited){
        if(steps==0){
            if(grid[x][y]==2){
                uniquePaths++;
            }
            return;
        }

        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int m = grid.length;
        int n = grid[0].length;

        for(int[] dir:dirs){
            int i = x+dir[0];
            int j = y+dir[1];
            if(i<0||j<0||i>=m||j>=n||visited[i][j]||grid[i][j]==-1){
                continue;
            }
            visited[i][j]=true;
            dfsSearch(grid,i,j,steps-1,visited);
            visited[i][j]=false;
        }

    }


    public int maxScoreSightseeingPair(int[] values) {
        int n = values.length;
        int[] left = new int[n];
        left[1] = 0;
        for(int i=2;i<n;i++){
            if(values[i-1]+i-1>values[left[i-1]]+left[i-1]){
                left[i]=i-1;
            }else{
                left[i]=left[i-1];
            }
        }

        int[] right= new int[n];
        right[n-2] = n-1;
        for(int i=n-3;i>=0;i--){
            if(values[i+1]-i-1>values[right[i+1]]-right[i+1]){
                right[i] = i+1;
            }else{
                right[i] = right[i+1];
            }
        }

        int res =Math.max(values[0]+values[right[0]]-right[0],values[left[n-1]]+left[n-1]+values[n-1]-n+1);
        for(int i=1;i<n-1;i++){
            res = Math.max(res,values[left[i]]+left[i]+values[right[i-1]]-right[i-1]);
        }


        return res;
    }


    public long maxPoints(int[][] points) {
        int m = points.length;
        int n = points[0].length;
        long[][] dp = new long[m][n];
        long[][] left = new long[m][n];
        long[][] right = new long[m][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = points[0][i];
        }

        left[0][0] = points[0][0];
        right[0][n-1] = points[0][n-1]-n+1;
        for (int i = 1; i < n; i++) {
            left[0][i] = Math.max(points[0][i]+i,left[0][i-1]);
        }

        for(int i=n-2;i>=0;i--){
            right[0][i] = Math.max(points[0][i]-i,right[0][i+1]);
        }




        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
//                for (int k = 0; k < n; k++) {
//                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + points[i][j] - Math.abs(k - j));
//                }
                //k<=j
                //dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + k + points[i][j] - j
                //k>j
                //dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] - k + points[i][j] + j

                dp[i][j]=Math.max(left[i-1][j]-j,right[i-1][j]+j)+points[i][j];
            }

            left[i][0] = dp[i][0];
            for (int j = 1; j < n; j++) {
                left[i][j] = Math.max(dp[i][j]+j,left[i][j-1]);
            }

            right[i][n-1]=dp[i][n-1]-n+1;
            for(int j=n-2;j>=0;j--){
                right[i][j] = Math.max(dp[i][j]-j,right[i][j+1]);
            }

        }

        long res = 0;

        for (long num : dp[m - 1]) {
            res = Math.max(res, num);
        }

        return res;

    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] heights = new int[m][n];
        for(int j=0;j<n;j++){
            heights[0][j]=matrix[0][j]-'0';
        }


        for(int i=1;i<m;i++){
            for(int j=0;j<n;j++){
                heights[i][j]= matrix[i][j]=='0'?0:heights[i-1][j]+(matrix[i][j]-'0');
            }
        }


        int res = 0;
        for(int i=0;i<m;i++){
            res = Math.max(res,getMaxRectangle(heights[i]));
        }


        return res;
    }

    private int getMaxRectangle(int[] height) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
         int n =height.length;
         for(int i=0;i<=n;i++){
             int cur= i<n?height[i]:-1;
             while (!stack.isEmpty()&&height[stack.peek()]>=cur){
                 int right = stack.pop();
                 int h = height[right];
                 int area = 0;
                 if(!stack.isEmpty()){
                      area = h*(i-stack.peek()-1);
                 }else {
                     area = h * i;
                 }

                 res = Math.max(area,res);
             }

             stack.push(i);
         }

        return res;
    }

    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] heights = new int[m][n];
        for(int j=0;j<n;j++){
            heights[0][j]=matrix[0][j]-'0';
        }


        for(int i=1;i<m;i++){
            for(int j=0;j<n;j++){
                heights[i][j]= matrix[i][j]=='0'?0:heights[i-1][j]+(matrix[i][j]-'0');
            }
        }


        int res = 0;
        for(int i=0;i<m;i++){
            res = Math.max(res,getMaxSquare(heights[i]));
        }


        return res*res;
    }

    private int getMaxSquare(int[] height) {
        int res =0;

        int n = height.length;
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<=n;i++){
            int cur = i==n? -1:height[i];
            while (!stack.isEmpty()&&height[stack.peek()]>=cur){
                int index = stack.pop();
                int h = height[index];
                int width = stack.isEmpty()? i:(i-stack.peek()-1);
                res = Math.max(res,Math.min(h,width));
            }
        }
        return res;
    }

    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int m = grid.length, n = grid[0].length, res = 0;

        int[][] v1 = new int[m][n];
        int[][] v2 = new int[m][n];
        int[][] v3 = new int[m][n];
        int[][] v4 = new int[m][n];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) { // left to right
                int t = (j == 0 || grid[i][j] == 'W') ? 0 : v1[i][j - 1];
                v1[i][j] = grid[i][j] == 'E' ? t + 1 : t; // @note: 没有关系，i-j 是0才可以放，不会是E
            }
            for (int j = n - 1; j >= 0; --j) { // right to left
                int t = (j == n - 1 || grid[i][j] == 'W') ? 0 : v2[i][j + 1];
                v2[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
        }
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) { // up to down
                int t = (i == 0 || grid[i][j] == 'W') ? 0 : v3[i - 1][j];
                v3[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
            for (int i = m - 1; i >= 0; --i) { // down to up
                int t = (i == m - 1 || grid[i][j] == 'W') ? 0 : v4[i + 1][j];
                v4[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
        }

        // final check
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '0') {
                    res = Math.max(res, v1[i][j] + v2[i][j] + v3[i][j] + v4[i][j]);
                }
            }
        }
        return res;

    }

    public boolean firstWillWin(int n) {
        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        if(n<=2){
            return true;
        }
        dp[1]=true;
        dp[2]=true;

       for(int i=3;i<=n;i++){
           if(!dp[i-1]||dp[i-2]){
               dp[i] = true;
           }
       }

       return dp[n];
    }

    public boolean firstWillWin(int[] values) {
            int n = values.length;
            if(n<=2){
                return true;
            }
            //用一个数组dp[i] 表示从i到len-1 能拿到的最大值
            int[] dp =new int[n+1];
           int sum =0;
           dp[n-1]=values[n-1]; // i=len-1时,只有一个可以拿
           dp[n-2]=values[n-1]+values[n-2];// i = len-2,有两个可拿，直接拿走
           dp[n-3]=values[n-3]+values[n-2];// 当i=len-3的时候，剩下最后三个，这时候如果拿一个，对方就会拿走两个，所以这次拿两个
           sum+=values[n-1]+values[n-3]+values[n-2];
        // 当i = len-4以及以后的情况中，显然可以选择拿一个或者拿两个两种情况，我们自然是选择拿最多的那个作为`dp`的值
        for(int i=n-4;i>=0;i++){
               sum+=values[i];
               dp[i]=Math.max(values[i]+Math.min(dp[i+2],dp[i+3]),values[i]+values[i+1]+Math.min(dp[i+3],dp[i+4]));
           }

           return dp[0]>sum-dp[0];
    }

    public int minCut(String s) {
        int n = s.length();
        int[] dp = new int[n];
        boolean[][] palindrome = new boolean[n][n];

        for(int i=0;i<n;i++){
            int min =i;
            for(int j=0;j<=i;j++){
                if(s.charAt(i)==s.charAt(j)&&(i-j<=2||palindrome[i+1][j-1])){
                    palindrome[j][i] = true;
                    min=j==0?0:Math.min(min,dp[j-1]+1);
                }
            }
            dp[i]=min;
        }

        return dp[n-1];
    }

    public int numDecodings(String s) {
        if (s.startsWith("0")) {
            return 0;
        }
        long[] dp = new long[s.length()+1];
        dp[0] = 1;
        if(s.charAt(0) == '0'){
            return 0;
        }
        dp[1] = (s.charAt(0) == '*') ? 9 : 1;

        for(int i = 2; i <= s.length(); i++) {
            char first = s.charAt(i-2);
            char second = s.charAt(i-1);

            // For dp[i-1]
            if(second == '*'){
                dp[i] += 9*dp[i-1];
            }else if(second > '0'){
                dp[i] += dp[i-1];
            }

            // For dp[i-2]
            if(first == '*'){
                if(second == '*'){
                    dp[i] += 15*dp[i-2];
                }else if(second <= '6'){
                    dp[i] += 2*dp[i-2];
                }else{
                    dp[i] += dp[i-2];
                }
            }else if(first == '1' || first == '2'){
                if(second == '*'){
                    if(first == '1'){
                        dp[i] += 9*dp[i-2];
                    }else{ // first == '2'
                        dp[i] += 6*dp[i-2];
                    }
                }else if( ((first-'0')*10 + (second-'0')) <= 26 ){
                    dp[i] += dp[i-2];
                }
            }

            dp[i] %= 1000000007;
        }


        return (int) dp[s.length()];
        }

    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        if(s==null||s.equals("")){
            return res;
        }

        int[] position = new int[26];
        for(int i=0;i<s.length();i++){
            position[s.charAt(i)-'a']=i;
        }

        int last =0;
        int start =0;
        for(int i=0;i<s.length();i++){
            last = Math.max(last,position[s.charAt(i)-'a']);
            if(last==i){
                res.add(last-start+1);
                start=last+1;
            }
        }

        return res;
    }

    public boolean canReach(String s, int minJump, int maxJump) {
        if(s.endsWith("1")){
            return false;
        }

        int n = s.length();
        boolean[] dp = new boolean[n];
        dp[0]=true;
        int checkPos = 0;
        for(int i=0;i<n;i++){
            if(dp[i]){
                // 向前探索是否可达，对于已经探索过的位置[0 ～ checkPos) 则跳过
                int left = Math.max(checkPos,i+minJump);
                int right = Math.min(i+maxJump,n-1);
                for(int j=left;j<=right;j++){
                     dp[j]=s.charAt(j)=='0'?true:false;
                }
                // checkPos 表示坐标 [0 ～ checkPos) 的位置已经判断过是否可到达
                checkPos=right+1;
            }
        }


        return dp[n-1];
    }

        public static void main(String[] args) {
        Test test = new Test();
       char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
//        test.maximalSquare(matrix);
            test.canReach("011010", 2,3);
    }

}
