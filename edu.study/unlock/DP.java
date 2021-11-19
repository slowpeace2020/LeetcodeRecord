package unlock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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





    public static void main(String[] args) {
    DP dp = new DP();
//    dp.backPackV(new int[]{1,2,3,3,7},7);

      String tes = "test";
      System.out.println(tes.substring(4,4));

  }
}
