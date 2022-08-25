package companyOA.mathworks;

import java.util.Arrays;

public class SumGo {

    public static void main(String[] args) {
        getMaxSum(new int[]{10,-20,-5},2);
    }

    private static int getMaxSum(int[] nums,int maxLength){
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp,Integer.MIN_VALUE);
        dp[0] = nums[0];
        for(int i=0;i<n;i++){
            for(int j=1;j<=maxLength&&i+j<n;j++){
                dp[i+j] = Math.max(dp[i+j],dp[i]+nums[i+j]);
                dp[i+j] = Math.max(dp[i+j],nums[i+j]);
            }
        }

        return dp[n-1];
    }
}
