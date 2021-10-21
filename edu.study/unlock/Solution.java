package unlock;

public class Solution {
    public int minMoves2(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        int sum = 0;

        for(int num:nums){
            sum+=num;
        }

        int average1 = sum/nums.length;
        int average2 = (sum/nums.length)+1;
        System.out.println(average1);
        System.out.println(average2);
        int res1 = 0 ;
        int res2 = 0 ;
        for(int num:nums){
            res1+=Math.abs(num-average1);
            res2+=Math.abs(num-average2);
        }

        System.out.println(res1);
        System.out.println(res2);

        return Math.min(res1,res2);
    }

    public boolean stoneGame(int[] piles) {
        int res = getStone(piles,0,piles.length-1,1);
        return res>0;
    }

    public int getStone(int[] nums, int start,int end,int turn) {
        if (start < 0 || end >= nums.length) {
            return 0;
        }
        if (start == end) {
            return nums[start] * turn;
        }


        return Math.max(nums[start] * turn + getStone(nums, start + 1, end, -turn), nums[end] * turn + getStone(nums, start, end - 1, -turn));
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.minMoves2(new int[]{1,0,0,8,6});
    }
}
