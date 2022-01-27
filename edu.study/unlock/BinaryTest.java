package unlock;

public class BinaryTest {

    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int left =0;
        int right=n-1;
        int[] res = new int[2];

        while(left<=right){
            int mid = left+(right-left)/2;
            if(nums[mid]<target){
                left=mid+1;
            }else {
                right =mid-1;
            }
        }

        if(left==n||nums[left]!=target){
            return new int[]{-1,-1};
        }

        res[0] = left;

        right=n-1;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(nums[mid]<=target){
                left=mid+1;
            }else {
                right =mid-1;
            }
        }


        res[1] = right;
        return res;

    }


    public int search(ArrayReader reader, int target) {
        int left =0;
        int right =10000;
        while (left<right){
            int mid = left+(right-left)/2;
            if(reader.get(mid)==target){
                return mid;
            }else if(reader.get(mid)<target){
                left = mid+1;
            }else {
                right = mid-1;
            }
        }

        return -1;
    }

    public int findMin(int[] nums) {
        int left =0;
        int right = nums.length-1;
        while (left<right){
            //最小值在left
            if(nums[left]<nums[right]){
                return nums[left];
            }

            int mid = left+(right-left)/2;
            //最小值在[left,mid]区间
            if(nums[left]>nums[mid]){
                right=mid;
                //最小值在[mid+1,right]
            }else if(nums[left]<nums[mid]){
                left=mid+1;
            }else {
                //两端值相等，left往右挪一位
                left++;
            }
        }

        return nums[left];
    }



        public static void main(String[] args) {
        BinaryTest test = new BinaryTest();
//        test.searchRange(new int[]{5,7,7,8,8,10},6);
//        test.searchRange(new int[]{5,7,7,8,8,10},8);
        test.findMin(new int[]{3,1,1});
//        test.searchRange(new int[]{1,4},4);
    }
}
