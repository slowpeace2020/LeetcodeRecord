package unlock;

import java.util.Arrays;

public class DynamicProgramming {


  int min = Integer.MAX_VALUE;

  public int backPack(int m, int[] A) {
    Arrays.sort(A);
    boolean[] visited = new boolean[m + 1];
    int[] memo = new int[m + 1];
    dfs(m, A, 0, visited, memo);

    return m - min;
  }


  private int dfs(int target, int[] A, int startIndex, boolean[] visited, int[] memo) {
    if (visited[target]) {
      return memo[target];
    }

    int res = target;
    for (int i = startIndex; i < A.length; i++) {
      if (target - A[i] >= 0) {
        res = dfs(target - A[i], A, i + 1, visited, memo);
      } else {
        break;
      }
    }

    visited[target] = true;
    memo[target] = res;
    min = Math.min(min, res);

    return res;
  }


  public int backPackIII(int m, int[] A) {
    int[] dp = new int[m + 1];
    for (int i = 0; i < A.length; i++) {
      for (int j = m; j >= A[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j - A[i]] + A[i]);
      }
    }
    return dp[m];
  }


//  public int backPackII(int m, int[] A) {
//    int dp[][] = new int[A.length + 1][m + 1];
//    for (int i = 1; i < dp.length; i++) {
//      //不超过j的最大重量可能值比较
//      for (int j = 0; j <= m; j++) {
//        dp[i][j] = dp[i - 1][j];
//        if (j >= A[i - 1]) {
//          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - A[i - 1]] + A[i - 1]);
//        }
//      }
//    }
//    return dp[A.length][m];
//  }

  public int backPackI(int m, int[] A) {
    // write your code here
    int n = A.length;
    int res =0;
    int[][] dp = new int[n][2];
    dp[0][1]=A[0]>m?0:A[0];

    for(int i=1;i<n;i++){
      int value = A[i]>m?0:A[i];
      dp[i][1] = value;
      for(int j=i-1;j>=0;j--){
        dp[i][0] = Math.max(dp[j][0],dp[j][1]);
        if(dp[j][0]+value<=m){
          dp[i][1] = Math.max(dp[j][0]+value,dp[i][1]);
        }
        if(dp[j][1]+value<=m){
          dp[i][1] = Math.max(dp[j][1]+value,dp[i][1]);
        }
      }

      res = Math.max(res,Math.max(dp[i][0],dp[i][1]));
    }


    return res;
  }

  public int backPackII(int m, int[] A, int[] V) {
    // write your code here
    int[][] dp=new int[A.length+1][m+1];
    for(int i=1;i<=m;i++){
      dp[0][i] = -1;
    }
    for(int i=1;i<=A.length;i++){
      for(int j=1;j<=m;j++){
        dp[i][j] = dp[i-1][j];
        System.out.println(i);
        if(j>=A[i-1]&&dp[i][j-A[i-1]]!=-1){
          dp[i][j] = Math.max(dp[i][j],dp[i-1][j-A[i-1]]+V[i-1]);
        }
        System.out.println(i+","+j+","+dp[i][j]);
      }
    }

    System.out.println(dp[A.length][m]);

    return dp[A.length][m];
  }

  public int backPackIIA(int m, int[] A, int[] V) {
    // write your code here
    // dp[i][j] represent
    // get first ith items to add into the bag with size j, the maximum value we can get
    int[][] dp = new int[A.length + 1][m + 1];
    // initalization
    // function: dp[i][j] = max(dp[i-1][j] or dp[i-1][j - A[i-1]] + V[i-1])
    dp[0][0] = 0;

    for (int i = 1; i <= A.length; i++) {
      for (int j = 1; j <= m; j++) {
        dp[i][j] = dp[i - 1][j]; // don't select ith item
        if (j >= A[i - 1]) {
          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
        }
      }
    }

    return dp[A.length][m];
  }

  public static void main(String[] args) {
    DynamicProgramming dp = new DynamicProgramming();
    dp.backPackII(10,new int[]{2,3,5,7},new int[]{1,5,2,4});
  }

}
