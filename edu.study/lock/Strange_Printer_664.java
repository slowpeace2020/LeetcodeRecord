package lock;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * refer:https://massivealgorithms.blogspot.com/2017/08/leetcode-664-strange-printer.html
 */

public class Strange_Printer_664 {
    /**
     * DP
     * @param s
     * @return
     */
    public int strangePrinter(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[][] dp = new int[101][101];
        for (int i = 0; i < n; i++) dp[i][i] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                dp[j][j + i] = i + 1;
                for (int k = j + 1; k <= j + i; k++) {
                    int temp = dp[j][k - 1] + dp[k][j + i];
                    if (s.charAt(k - 1) == s.charAt(j + i)) temp--;
                    dp[j][j + i] = Math.min(dp[j][j + i], temp);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * Memory
     */

    int[][] memo;

    public int strangePrinterI(String s) {
        int N = s.length();
        memo = new int[N][N];
        return dp(s, 0, N - 1);
    }

    public int dp(String s, int i, int j) {
        if (i > j)
            return 0;
        if (memo[i][j] == 0) {
            int ans = dp(s, i + 1, j) + 1;
            for (int k = i + 1; k <= j; ++k)
                if (s.charAt(k) == s.charAt(i))
                    ans = Math.min(ans, dp(s, i, k - 1) + dp(s, k + 1, j));
            memo[i][j] = ans;
        }
        List<Integer> list = new ArrayList<>();
        return memo[i][j];
    }
}
