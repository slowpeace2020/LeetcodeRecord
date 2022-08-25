package companyOA.amazon;

public class TemperatureChange {
//    Find the maximum aggregate temperature changed evaluated among all the days.
//    ex - [-1,2,3] - 5
//    explanation -
//            [-1],[-1,2,3] - max(-1,4) = 4
//            [-1,2],[2,3] - max(1,5) = 5
//            [-1,2,3][3] - max(4,3) = 4


   public int maxTemperatureChange(int[] nums){
       int suffixSum = 0;
       int prefixSum = 0;
       for(int i=0;i<nums.length;i++){
           suffixSum+=nums[i];
       }

       int res = Integer.MIN_VALUE;
       for(int i=0;i< nums.length;i++){
           suffixSum-=nums[i];
           prefixSum+=nums[i];
           res = Math.max(res,Math.max(prefixSum,suffixSum));
       }


       return res;
   }


}
