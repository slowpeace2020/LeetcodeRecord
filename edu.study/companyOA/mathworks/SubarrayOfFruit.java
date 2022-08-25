package companyOA.mathworks;

public class SubarrayOfFruit {

    public static void main(String[] args) {
        SubarrayOfFruit fruit = new SubarrayOfFruit();
       int res = fruit.getSubArrays(new int[]{1,2,3,0},3);
        System.out.println(res);
    }

    public int getSubArrays(int[] nums,int target){
        int n = nums.length;
        int[] prefixSum = new int[n+1];
        for(int i=0;i<n;i++){
            prefixSum[i+1] = prefixSum[i]+nums[i];
        }
        int count = 0;
        for(int i=1;i<=n;i++){
            for(int j=i-1;j>=0;j--){
                if(prefixSum[i]-prefixSum[j]==target){
                    count++;
                }
            }
        }

        return count;
    }


}
