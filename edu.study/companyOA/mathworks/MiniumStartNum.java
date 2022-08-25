package companyOA.mathworks;

public class MiniumStartNum {

    public int getStartNum(int[] nums){
        int res = 0;
        int n = nums.length;
        int[] prefixSum = new int[n+1];
        for(int i=0;i<n;i++){
            prefixSum[i+1] = prefixSum[i]+nums[i];
            if(prefixSum[i+1]<0){
                res += 1-prefixSum[i+1];
                prefixSum[i+1]+=1-prefixSum[i+1];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        MiniumStartNum startNum = new MiniumStartNum();
//        startNum.getStartNum(new int[]{-2,3,1,-5});
        startNum.getStartNum(new int[]{-5,4,-2,3,1});
    }
}

