package lock;

import java.util.Arrays;
import java.util.Comparator;

public class Solution_1799 {
    long[][] dp;

    int[][] pairs;
    public int maxScore(int[] nums) {
        pairs = new int[(nums.length * (nums.length - 1)) / 2][];
        int pairs_i = 0;
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                int[] a = new int[] {i, j, gcd(nums[i], nums[j])};
                pairs[pairs_i++] = a;
            }
        }

        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] p, int[] q) {
                return p[2] - q[2];
            }
        });

        dp = new long[pairs.length][1 << nums.length];

        long ans = 0, len = pairs.length - (nums.length / 2) + 1;
        for(int i = 0; i < len; i++) {
            ans = Math.max(ans, recur(i, 1, 0, nums.length / 2));
        }

        return (int) ans;
    }

    long recur(int idx, int order, int taken, int till) {
        int[] cur = pairs[idx];
        if(((1 << cur[0]) & taken) != 0) return Integer.MIN_VALUE;
        if(((1 << cur[1]) & taken) != 0) return Integer.MIN_VALUE;

        if(order == till) {
            return order * cur[2];
        } else {
            if(dp[idx][taken] != 0) {
                return dp[idx][taken];
            }

            taken |= (1 << cur[0]);
            taken |= (1 << cur[1]);

            long max = 0, here = order * cur[2];
            for(int i = idx + 1; i < pairs.length; i++) {
                max = Math.max(max, here + recur(i, order + 1, taken, till));
            }

            dp[idx][taken] = max;

            return max;
        }
    }

    int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
}
