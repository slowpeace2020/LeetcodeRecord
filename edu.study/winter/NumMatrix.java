package winter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class NumMatrix {

    int[][] matrix;
    int[][] colSum;
    public NumMatrix(){

    }

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        this.colSum = new int[m+1][n];
        this.matrix = matrix;
        for(int i=1;i<=m;i++){
            for(int j=0;j<n;j++){
                colSum[i][j]=colSum[i-1][j]+matrix[i-1][j];
            }
        }
    }



    public void update(int row, int col, int val) {
        for (int i = row + 1; i < colSum.length; ++i) {
            colSum[i][col] += val - matrix[row][col];
        }
        matrix[row][col] = val;
    }


    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = 0;
        for (int j = col1; j <= col2; ++j) {
            res += colSum[row2 + 1][j] - colSum[row1][j];
        }
        return res;
    }


    int pairs = 0;
    public int reversePairs(int[] nums) {
        helperReversePairs(nums,0,nums.length-1);
        return pairs;
    }

    public void helperReversePairs(int[] nums,int start,int end){
        if(start==end){
            return;
        }

        int mid = start+(end-start)/2;
        helperReversePairs(nums,start,mid);
        helperReversePairs(nums,mid+1,end);

        for(int i=mid+1;i<=end;i++){
            int index =findUpper(nums,start,mid, 2*((long)nums[i]));
            if(index==mid&&nums[mid]<=2*((long)nums[i])){
                continue;
            }
            pairs+=mid-index+1;
        }

        merge(nums,start,mid,end);

    }

    private void merge(int[] nums, int start,int mid, int end) {
        int[] temp = new int[end-start+1];
        int i = start;
        int j = mid+1;
        int k = 0;
        while (i<=mid&&j<=end){
            if(nums[i]<nums[j]){
                temp[k++]=nums[i++];
            }else {
                temp[k++]=nums[j++];
            }
        }

        while (i<=mid){
            temp[k++]=nums[i++];
        }

        while (j<=end){
            temp[k++]=nums[j++];
        }

        System.arraycopy(temp,0,nums,start,k);
    }

    public int findUpper(int[] nums,int left,int right,long target){
        while (left<right){
            int mid = left+(right-left)/2;
            if(nums[mid]<=target){
                left=mid+1;
            }else {
                right = mid;
            }
        }

        return left;
    }

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if(n==0){
            return 0;
        }
        int[] dp = new int[n];
        int[] count = new int[n];
        int res =1;
        int len =1;
        dp[0]=1;
        count[0]=1;
        for(int i=1;i<n;i++){
            dp[i] = 1;
            count[i]=1;
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    if(dp[i]==dp[j]+1){
                        count[i]+=count[j];
                    }
                    if(dp[i]<dp[j]+1){
                        dp[i] = dp[j]+1;
                        count[i]=count[j];
                    }

                }
            }

            if(dp[i]==len){
                res+=count[i];
            }else if(dp[i]>len){
                len = dp[i];
                res=count[i];
            }

        }

        return res;

    }



        public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,4,7};
        NumMatrix test = new NumMatrix();
        test.findNumberOfLIS(nums);
    }

}
