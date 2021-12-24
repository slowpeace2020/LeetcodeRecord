package unlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class DP {

  public int abackPackIII(int[] A, int[] V, int m) {
    int n = A.length;
    int[] f = new int[m + 1]; // 利用一维数组求解, 计算第i行元素时，只用到第i-1行的元素
    for (int i = 1; i <= n; i++) {
      for (int j = m; j >= 1; j--) { // 从后向前计算, 因为后面元素的更新依赖与前面为更新
        // if(j < A[i - 1])
        //   f[j] = f[j];  // 保持不变

        if (j >= A[i - 1]) {
          f[j] = Math.max(f[j], f[j - A[i - 1]] + V[i - 1]);
        }
      }
    }
    return f[m];
  }

  public int backPackIII(int[] A, int[] V, int m) {
    int[] dp = new int[m + 1];
    for (int i = 1; i < A.length + 1; i++) {
      for (int j = A[i - 1]; j <= m; j++) {
        dp[j] = Math.max(dp[j], dp[j - A[i - 1]] + V[i - 1]);
        ;
      }
    }

    return dp[m];
  }




  public int sbackPackIII(int[] A, int[] V, int m) {
    int n = A.length;

    //1.
    int[][] f = new int[n+1][m+1];

    //3.
    f[0][0] = 0;
    for(int i=1; i<=m; i++){
      f[0][i] = -1;
    }

    //2.
    for(int i=1; i<=n; i++){
      for(int j=0; j<=m; j++){
        // optimize piece
        f[i][j] = f[i-1][j];
        if(j - A[i-1] >= 0){
          f[i][j] = Math.max(f[i][j-A[i-1]] + V[i-1], f[i][j]);
        }
        // optimize piece
      }
    }

    int res = Integer.MIN_VALUE;
    for(int w=0; w<=m; w++){
      if(res < f[n][w]){
        res = f[n][w];
      }
    }

    return res;
  }

  public int wbackPackIII(int[] A, int[] V, int m) {
    // write your code here
    int[][] dp = new int[A.length + 1][m + 1];
    int res = 0;
    for (int i = 1; i < dp.length; i++) {
      for (int j = 0; j <= m; j++) {
        dp[i][j] = dp[i - 1][j];
        if (j >= A[i - 1]) {
          dp[i][j] = Math.max(dp[i][j], dp[i][j - A[i - 1]] + V[i - 1]);
        }
        res = Math.max(dp[i][j], res);

      }
    }

    return res;
  }

  public int backPackIV(int[] nums, int target) {
    int n = nums.length;
    int[][] dp = new int[n + 1][target + 1];
    dp[0][0] = 1;
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= target; j++) {
        int k = 0;
        while (k * nums[i - 1] <= j) {
          dp[i][j] += dp[i - 1][j - k * nums[i]];
          k++;
        }
      }
    }

    return dp[n][target];
  }


  public int backPackV(int[] nums, int target) {
    int n = nums.length;
    int[][] dp = new int[n + 1][target + 1];
    for (int i = 0; i <= n; i++) {
      dp[i][0] = 1;
    }
    for (int i = 1; i <= n; i++) {
      for (int j = target; j >= 1; j--) {
        dp[i][j] = dp[i - 1][j];
        if (j >= nums[i - 1]) {
          dp[i][j] += dp[i - 1][j - nums[i - 1]];
        }
      }
    }

    return dp[n][target];
  }


  public int weightCapacity(int[] weights, int maxCapacity) {
    // Write your code here
    int[] dp = new int[maxCapacity + 1];
    for (int i = 1; i <= weights.length; i++) {
      for (int j = maxCapacity; j >= weights[i - 1]; j--) {
        dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + weights[i - 1]);
      }
    }

    return dp[maxCapacity];
  }

  public int backPackVI(int[] nums, int target) {
    int[] dp = new int[target + 1];
    dp[0] = 1;

    for (int i = 1; i <= target; i++) {
      for (int num : nums) {
        if (i >= num) {
          dp[i] += dp[i - num];
        }
      }
    }

    return dp[target];

  }


  public long getMaxValue(int k1, int k2, int c, int n, int m, int[] a, int[] b) {
    // step1: 对a，b数组分别排序
    Arrays.sort(a);
    Arrays.sort(b);

    // step2: 求a, b数组的前缀和
    long[] prefixSumA = new long[n + 1];
    long[] prefixSumB = new long[m + 1];

    for (int i = 0; i <= n; i++) {
      prefixSumA[i + 1] = prefixSumA[i] + a[i];
    }

    for (int j = 0; j <= m; j++) {
      prefixSumB[j + 1] = prefixSumB[j] + b[j];
    }

    // step3: 动态规划
    long[][] dp = new long[n + 1][m + 1];
    long res = 0;
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= m; j++) {
        long surplusC = c - prefixSumA[i] - prefixSumB[j];
        if (surplusC < 0) {
          break;
        }

        if (i > 0) {
          dp[i][j] = dp[i - 1][j] + surplusC * k1;
        }

        if (j > 0) {
          dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + surplusC * k2);
        }

        res = Math.max(res, dp[i][j]);

      }
    }

    return res;

  }

  public int findMaxForm(String[] strs, int m, int n) {
    int[][] dp = new int[m + 1][n + 1];
    for (String s : strs) {
      int ones = 0;
      int zeros = 0;
      for (Character c : s.toCharArray()) {
        if (c == '1') {
          ones++;
        } else {
          zeros++;
        }
      }

      for (int i = m; i >= zeros; i--) {
        for (int j = n; j >= ones; j--) {
          dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
        }
      }
    }

    return dp[m][n];
  }

  public boolean wordBreak(String s, List<String> wordDict) {
    Map<String, Boolean> map = new HashMap<>();

    return checkWordBreak(s, wordDict, 0, s.length(), map);

  }

  public boolean checkWordBreak(String s, List<String> wordDict, int start, int end,
      Map<String, Boolean> map) {
    String word = s.substring(start, end);

    if (map.containsKey(word)) {
      return map.get(word);
    }

    if (wordDict.contains(word)) {
      map.put(word, true);
      return true;
    }

    for (int i = start + 1; i < end; i++) {
      if (checkWordBreak(s, wordDict, start, i, map) && checkWordBreak(s, wordDict, i, end, map)) {
        map.put(word, true);
        return true;
      }
    }

    map.put(word, false);
    return false;
  }


  public boolean wordBreakI(String s, List<String> wordDict) {
    int n = s.length();
    boolean[][] dp = new boolean[n + 1][n + 1];

    for (int i = 0; i <= n; i++) {
      for (int j = i + 1; j <= n; j++) {
        String part = s.substring(i, j);
        if (wordDict.contains(part)) {
          dp[i][j] = true;
          for (int k = i - 1; k >= 0; k--) {
            if (dp[k][i]) {
              dp[k][j] = true;
            }
          }
        }

      }
    }

    return dp[0][n];
  }


  public int minCost(int[][] costs) {
    int[][] dp = costs;

    for (int i = 1; i < costs.length; i++) {
      for (int j = 0; j < 3; j++) {
        dp[i][0] = costs[i][0] + Math.min(dp[i - 1][1], dp[i - 1][2]);
        dp[i][1] = costs[i][1] + Math.min(dp[i - 1][0], dp[i - 1][2]);
        dp[i][2] = costs[i][2] + Math.min(dp[i - 1][1], dp[i - 1][0]);
      }
    }

    return Math.min(dp[costs.length - 1][0],
        Math.min(dp[costs.length - 1][1], dp[costs.length - 1][2]));
  }


  public int minCostII(int[][] costs) {
    int[][] dp = costs;
    int m = costs.length;
    int n = costs[0].length;
    int min1 = -1;
    int min2 = -1;
    for (int i = 0; i < m; i++) {
      int last1 = min1;
      int last2 = min2;

      min1 = -1;
      min2 = -1;

      for (int j = 0; j < n; j++) {
        if (j != last1) {
          dp[i][j] += last1 < 0 ? 0 : dp[i - 1][last1];
        } else {
          dp[i][j] += last2 < 0 ? 0 : dp[i - 1][last2];
        }

        if (min1 < 0 || dp[i][j] < dp[i][min1]) {
          min1 = j;
          min2 = min1;
        } else if (min2 < 0 || dp[i][j] < dp[i][min2]) {
          min2 = j;
        }

      }
    }

    return dp[m - 1][min1];
  }


  public int flipDigit(int[] nums) {
    // Write your code here
    int n = nums.length;
    int[][] dp = new int[n][2];
    dp[0][0] = nums[0] == 0 ? 0 : 1;
    dp[0][1] = nums[0] == 1 ? 0 : 1;
    for (int i = 1; i < n; i++) {
      if (nums[i] == 0) {
        dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1]);
        dp[i][1] = dp[i - 1][1] + 1;
      } else {
        dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][0]) + 1;
        dp[i][1] = dp[i - 1][1];
      }
    }

    return Math.min(dp[n - 1][0], dp[n - 1][1]);
  }

  public boolean isMatch(String s, String p) {
    int m = s.length();
    int n = p.length();

    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    for (int i = 1; i <= n; i++) {
      if (p.charAt(i - 1) == '*') {
        dp[0][i] = dp[0][i - 1];
      }
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (p.charAt(j - 1) == '*') {
          dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
        } else {
          dp[i][j] =
              dp[i - 1][j - 1] && (p.charAt(j - 1) == '?' || p.charAt(j - 1) == s.charAt(i - 1));
        }
      }
    }

    return dp[m][n];
  }

  public int minDistance(String word1, String word2) {
    int m = word1.length();
    int n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      dp[i][0] = i;
    }

    for (int j = 1; j <= n; j++) {
      dp[0][j] = j;
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          if (dp[i][j] == 0) {
            dp[i][j] = dp[i - 1][j] + 1;
          }

          dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + 1);
          dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
        }
      }

    }

    return dp[m][n];
  }

  public int minimumDeleteSum(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();
    int res = 0;
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; i++) {
      dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
    }

    for (int i = 1; i <= n; i++) {
      dp[0][i] = dp[0][i - 1] + s2.charAt(i - 1);
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          if (dp[i][j] == 0) {
            dp[i][j] = dp[i][j - 1] + s2.charAt(j - 1);
          }

          dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + s1.charAt(i - 1));
        }
      }
    }

    return dp[m][n];

  }


  public int minFlipsMonoIncr(String s) {
    int n = s.length();
    int[][] dp = new int[n][2];
    dp[0][0] = s.charAt(0) == '0' ? 0 : 1;
    dp[0][1] = s.charAt(0) == '1' ? 0 : 1;

    for (int i = 1; i < n; i++) {
      if (s.charAt(i) == '0') {
        dp[i][0] = dp[i - 1][0];
        dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
      } else {
        dp[i][0] = dp[i - 1][0] + 1;
        dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]);
      }
    }

    return Math.min(dp[n - 1][0], dp[n - 1][1]);

  }


  public boolean isInterleave(String s1, String s2, String s3) {
    int m = s1.length();
    int n = s2.length();

    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;

    for (int i = 1; i <= m; i++) {
      if (s1.substring(0, i).equals(s3.substring(0, i))) {
        dp[i][0] = true;
      }
    }

    for (int i = 1; i <= n; i++) {
      if (s2.substring(0, i).equals(s3.substring(0, i))) {
        dp[0][i] = true;
      }
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1]
            && s2.charAt(j - 1) == s3.charAt(i + j - 1));
      }
    }
    return dp[m][n];

  }


  public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }

    int n = prices.length;
    int[] buy = new int[n];
    int[] sell = new int[n];

    buy[0] = -prices[0];
    sell[0] = 0;
    if (n > 1) {
      buy[1] = Math.max(buy[0], -prices[1]);
      sell[1] = Math.max(sell[0], buy[0] + prices[1]);
      if (n > 2) {

        for (int i = 2; i < n; i++) {
          buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
          sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
      }
    }

    return sell[n - 1];
  }


  public int maxProfitI(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }

    int b0 = -prices[0];
    int b1 = b0;
    int s0 = 0;
    int s1 = 0;
    int s2 = 0;
    for (int i = 1; i < prices.length; i++) {
      b0 = Math.max(b1, s2 - prices[i]);
      s0 = Math.max(s1, b1 + prices[i]);
      b1 = b0;
      s2 = s1;
      s1 = s0;
    }

    return s0;
  }


  public int maxProfit(int[] prices, int fee) {
    int sell = 0;
    int buy = -prices[0];

    for (int i = 1; i < prices.length; i++) {
      //int oldsell = sell;
      sell = Math.max(sell, buy + prices[i] - fee);
      //buy = Math.max(buy,oldsell-prices[i]);
      buy = Math.max(buy, sell - prices[i]);

    }

    return sell;
  }


  public int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[] dp = new int[n];
    int res = 1;

    for (int i = n - 1; i >= 0; i--) {
      int len = 0;
      for (int j = i + 1; j < n; j++) {
        int t = dp[j];
        if (s.charAt(i) == s.charAt(j)) {
          dp[j] = 2 + len;
        }
        len = Math.max(len,t);
      }
    }

    for(int num:dp){
      res = Math.max(res,num);
    }

    return res;
  }

  public boolean isScramble(String s1, String s2) {
    if (s1.equals(s2)) {
      return true;
    }
    int n = s1.length();

    boolean[][][] dp = new boolean[n][n][n + 1];
    for (int i = n - 1; i >= 0; i--) {
      for (int j = i + 1; j < n; j++) {
        for (int k = 1; k <= n; k++) {
          if (s1.substring(i, i + k).equals(s2.substring(j, j + k))) {
            dp[i][j][k] = true;
          } else {
            for (int l = 1; l < k; l++) {
              if (dp[i][j][l] && dp[i + l][j + l][k - l] || dp[i][j][k - l] && dp[i + k - l][j + k
                  - l][l]) {
                dp[i][j][k] = true;
                break;
              }
            }
          }
        }
      }
    }

    return false;
  }


  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    for (int i = 0; i < m; i++) {
      dp[i][0] = 1;
    }

    for (int j = 0; j < n; j++) {
      dp[0][j] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
      }
    }

    return dp[m - 1][n - 1];
  }


  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;

    int[][] dp = new int[m][n];

    for (int i = 0; i < m; i++) {
      if (obstacleGrid[i][0] == 1) {
        break;
      } else {
        dp[i][0] = 1;
      }
    }

    for (int j = 0; j < n; j++) {
      if (obstacleGrid[0][j] == 1) {
        break;
      } else {
        dp[0][j] = 1;
      }
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (obstacleGrid[i][j] == 0) {
          dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
        }
      }
    }

    return dp[m - 1][n - 1];
  }


  public int uniquePathsIII(int[][] grid) {
    int x = 0;
    int y = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == 1) {
          x = i;
          y = j;
        } else if (grid[i][j] == 0) {
          zeroes++;
        }
      }
    }
    DFSforPath(x, y, grid, 0);
    return ans;
  }

  int ans;
  int zeroes = 1; // to nullify the starting case. zeroCount is incremented by 1 even when grid[x][y] == 1

  void DFSforPath(int i, int j, int[][] grid, int zeroCount) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == -1) {
      return;
    }

    if (grid[i][j] == 2) {
      if (zeroCount == zeroes) {
        ans++;
      }
      return;
    }

    grid[i][j] = -1;
    DFSforPath(i + 1, j, grid, zeroCount + 1);
    DFSforPath(i - 1, j, grid, zeroCount + 1);
    DFSforPath(i, j + 1, grid, zeroCount + 1);
    DFSforPath(i, j - 1, grid, zeroCount + 1);

  }


  public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[][] dp = new int[m][n];
    dp[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) {
      dp[i][0] = grid[i][0] + dp[i - 1][0];
    }

    for (int i = 1; i < n; i++) {
      dp[0][i] = grid[0][i] + dp[0][i - 1];
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
      }
    }

    return dp[m - 1][n - 1];

  }

  public long maxPointsI(int[][] points) {
    int m = points.length;
    int n = points[0].length;
    long[] dp = new long[n];
    for (int i = 0; i < n; i++) {
      dp[i] = points[0][i];
    }

    for (int i = 1; i < m; i++) {
      long[] pre =new long[n];

    }

    long res = 0;


    return res;

  }

  public long maxPoints(int[][] points) {

    int rows = points.length;
    int cols = points[0].length;

    long[] prev = new long[cols];

    for (int i = 0; i < cols; i++) {
      prev[i] = points[0][i];
    }

    for (int i = 0; i < rows - 1; i++) {
      long[] left = new long[cols];
      long[] right = new long[cols];
      long curr[] = new long[cols];

      left[0] = prev[0];
      right[cols - 1] = prev[cols - 1];

      for (int j = 1; j < cols; j++) {
        left[j] = Math.max(left[j-1] - 1, prev[j]);
      }

      for (int j = cols - 2; j >= 0; j--) {
        right[j] = Math.max(right[j+1] - 1, prev[j]);
      }

      for (int j = 0; j < cols; j++) {
        curr[j] = points[i + 1][j] + Math.max(left[j], right[j]);
      }

      prev = curr;
    }

    long maxPoints = 0;
    for (int j = 0; j < cols; j++) {
      maxPoints = Math.max(maxPoints, prev[j]);
    }
    return maxPoints;
  }

  public int maximalRectangle(boolean[][] matrix) {
    if (matrix == null||matrix.length==0||matrix[0].length==0) {
      return 0;
    }
    int ans=0;
    int n=matrix.length,m=matrix[0].length;

    int[][] dp=new int[n][m+1];
     for(int i=0;i<n;i++){
       for(int j=0;j<m;j++){
         if(i==0 && matrix[i][j]){
           dp[i][j] = 1;
           continue;
         }

         if(matrix[i][j]){
           dp[i][j] = dp[i-1][j]+1;
         }
       }
     }

     for(int i=0;i<n;i++){
       ans = Math.max(ans,largestRectangleArea(dp[i]));
     }

     return ans;
  }

  //todo 单调栈没搞懂
  public int largestRectangleArea(int[] height){
    Stack<Integer> stack = new Stack<>();
    height[height.length] = 0;
    int sum =0;
    for(int i=0;i<height.length;i++){
      if(stack.empty()||height[i]>stack.peek()){
        stack.push(height[i]);
      }else {
        int tmp = stack.pop();
        sum = Math.max(height[tmp]* ((stack.empty()?i:i-stack.peek()-1)),sum);
        i--;
      }
    }

    return sum;

  }


  public int numDecodings(String s) {
    if (s.startsWith("0")) {
      return 0;
    }

    int n = s.length();
    int[] dp = new int[n + 1];
    dp[0] = 1;
    for (int i = 1; i <= n; i++) {
      dp[i] = s.charAt(i - 1) == '0' ? 0 : dp[i - 1];
      if (i > 1 && (s.charAt(i - 2) == '1' || s.charAt(i - 2) == '2') && s.charAt(i - 1) <= '6') {
        dp[i] += dp[i - 2];
      }
    }

    System.out.println(dp[n]);
    return dp[n];
  }


  Map<Integer, Boolean> map = new HashMap<>();


  public boolean firstWillWin(int n) {

    map.put(0, false);
    map.put(1, true);
    map.put(2, true);
    map.put(3, false);

    if (map.containsKey(n)) {
      return map.get(n);
    }
    // write your code here

    boolean res = !firstWillWin(n - 1) || !firstWillWin(n - 2);
    map.put(n, res);
    return res;

  }

  public boolean firstWillWinI(int n) {
    boolean[] f = new boolean[n + 1];
    f[0] = false;
    for (int i = 1; i <= n; i++) {
      if (i == 1 || i == 2) {
        f[i] = true;
      } else {
        f[i] = !f[i - 1] || !f[i - 2];
      }
    }
    return f[n];
  }

  public boolean firstWillWinII(int[] values) {
    int n = values.length;
    if (n == 0) {
      return false;
    }

    if (n < 3) {
      return true;
    }

    int[] dp = new int[n];
    dp[n - 1] = values[n - 1];
    dp[n - 2] = values[n - 1] + values[n - 2];
    for (int i = n - 3; i >= 0; i--) {
      dp[i] = Math.max(values[i] - dp[i + 1], values[i] + values[i + 1] - dp[i + 2]);
    }

    return dp[0] > 0;

  }

  public boolean firstWillWin(int[] values) {
    // write your code here

    int n = values.length;
    int[][] dp = new int[n][n];

    // 初始化
    for(int i=0;i<n;i++){
      dp[i][i] = values[i];
    }

    for(int i=n-2;i>=0;i--){
      for(int j=i+1;j<n;j++){
          dp[i][j] = Math.max(values[j]-dp[i][j-1],values[i]-dp[i+1][j]);
      }
    }

    return dp[0][n-1]>0;

  }

  public int minCut(String s) {
    int n = s.length();
    if (n == 1) {
      return 0;
    }

    int[] dp = new int[n];
    boolean[][] p = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      p[i][i] = true;
    }

    for (int i = 0; i < n; i++) {
      dp[i] = i;
      for (int j = 0; j <= i; j++) {
        if (s.charAt(i) == s.charAt(j) && (i - j < 2 || p[j + 1][i - 1])) {
          p[j][i] = true;
          dp[i] = j == 0 ? 0 : Math.min(dp[i], dp[j - 1] + 1);
        }
      }
    }

    return dp[n - 1];

  }

  public int numSquares(int n) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, n);
    for (int i = 1; i * i <= n; i++) {
      dp[i * i] = 1;
    }

    for (int i = 2; i <= n; i++) {
      for (int j = 1; j * j <= i; j++) {
        dp[i] = Math.min(1 + dp[i - j * j], dp[i]);
      }
    }

    return dp[n];
  }

  public int numDecodingsII(String s) {
    if (s.startsWith("0")) {
      return 0;
    }

    int n = s.length();
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = s.charAt(0) == '*' ? 9 : 1;

    for (int j = 2; j <=n; j++) {
     int i=j-1;
      char cur = s.charAt(i);
      char pre = s.charAt(i - 1);
      if (pre == '0' && cur == '0') {
        return 0;
      }

      if (pre == '*') {
        if (cur == '*') {
          if (i == 1) {
            dp[i] = 21;
          } else {
            dp[i] = dp[i - 1] * 9;
            if (i >= 2) {
              dp[i] += dp[i - 2] * 14;
            }
          }
        } else {
          if (cur - '0' == 0) {
            dp[i] = dp[i - 2] * 2;
          } else if (cur - '0' <= 6) {
            dp[i] = dp[i - 1];
            if (i >= 2) {
              dp[i] += dp[i - 2] * 2;
            }
          } else {
            dp[i] = dp[i - 1];
          }
        }
      } else if (pre == '0') {
        if (cur == '*') {
          dp[i] = dp[i - 1] * 9;
        } else {
          dp[i] = dp[i - 1];
        }
      } else if (pre - '0' < 3) {
        if (cur == '*') {
          dp[i] = dp[i - 1] * 9;
          if (i >= 2) {
            dp[i] += dp[i - 2] * 6;
          }
        } else if (cur - '0' <= 6) {
          dp[i] = dp[i - 2];
          if (cur != '0') {
            dp[i] += dp[i - 1];
          }
        } else {
          dp[i] = dp[i - 1];
        }
      } else {
        if (cur == '0') {
          return 0;
        } else {
          dp[i] = dp[i - 1];
        }
      }
    }

    return dp[n];
  }

  public boolean canJump(int[] nums) {
    int n = nums.length;
    boolean[] dp = new boolean[n];
    dp[0] = true;
    for (int i = 0; i < n; i++) {
      int jump = nums[i];
      if (dp[i]) {
        for (int j = 1; j <= jump && i + j < n; j++) {
          dp[i + j] = true;
          if (i + j == n - 1) {
            break;
          }
        }
      }

      if (dp[n - 1]) {
        break;
      }
    }

    return dp[n - 1];
  }

  public int jump(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, n);
    dp[0] = 0;

    for (int i = 0; i < nums.length; i++) {
      int jump = nums[i];
      for (int j = 1; j <= jump && i + j < n; j++) {
        dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
      }
    }

    return dp[n - 1];
  }

  public boolean canReach(int[] arr, int start) {
    Queue<Integer> queue = new LinkedList<>();
    int n = arr.length;
    boolean[] visited = new boolean[n];
    queue.offer(start);

    while (!queue.isEmpty()) {
      int current = queue.poll();
      int jump = arr[current];
      if (jump == 0) {
        return true;
      }

      if (current - jump >= 0 && !visited[current - jump]) {
        queue.offer(current - jump);
      }

      if (current + jump < n && !visited[current + jump]) {
        queue.offer(current + jump);
      }

      visited[current] = true;

    }

    return false;
  }


  public boolean canReach(String s, int minJump, int maxJump) {
    int n = s.length();
    boolean[] dp = new boolean[n];

    //滑窗cnt初始化，一开始i = 0是可达的，所以cnt初始化为1
    dp[0] = true;
    int count =1;

    for(int i=minJump;i<n;i++){

      //判断当前坐标是否可达
      if(s.charAt(i)==0&&count>0){
        dp[i] = true;
      }

      //滑窗移动后左端坐标离开带来的更新
      if(i>=maxJump&&dp[i-maxJump]){
        count--;
      }

      //滑窗移动后右端坐标加入带来的更新
      if(dp[i-minJump+1]){
        count++;
      }

    }

    return dp[n-1];
  }

  public List<Integer> partitionLabels(String s) {
    List<Integer> res = new ArrayList<>();
    int n = s.length();
    int[] dp = new int[26];
    int index = 0;
    for (char c : s.toCharArray()) {
      dp[c - 'a'] = index++;
    }

    int start = 0;
    int end = 0;

    for (int i = 0; i < s.length(); i++) {
      end = Math.max(end, dp[s.charAt(i) - 'a']);
      if (i == end) {
        int len = end - start + 1;
        res.add(len);
        start = end + 1;
      }
    }

    return res;
  }

    public static void main(String[] args) {
    DP dp = new DP();
    dp.numDecodingsII("1*");

      String tes = "test";
      System.out.println(tes.substring(4,4));

  }
}
