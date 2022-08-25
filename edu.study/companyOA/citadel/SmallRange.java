package companyOA.citadel;

import java.util.Arrays;

public class SmallRange {

    //https://www.tutorialcup.com/interview/array/minimize-the-maximum-difference-between-the-heights.htm
    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);
        int res = nums[nums.length-1]-nums[0];
        for(int i=0;i<nums.length-1;i++){
            int a = nums[i];
            int b = nums[i+1];
            int high = Math.max(nums[nums.length-1]-k,a+k);

        }

        return res;
    }

}
