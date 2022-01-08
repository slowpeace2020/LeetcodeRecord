package unlock;

public class DivideConquer {

    public int reversePairs(int[] nums) {
        int n = nums.length;
       return helperReversePairs(nums,0,n-1);
      }

    private int helperReversePairs(int[] nums, int left, int right) {
        if(left>=right){
            return 0;
        }
        int mid = left+(right-left)/2;
        //递归调用获取左右子区间内部的对数
        int res =  helperReversePairs(nums,left,mid)+helperReversePairs(nums,mid+1,right);

        int i = left;
        int j= mid+1;
        int k = 0;
        int p = mid+1;
        int[] temp = new int[right-left+1];
        //归并排序
        while (i<=mid){
            //顺便获取每个左边元素和右边子区间元素之间的匹配对数
            while (p<=right&&nums[i]>2*nums[p]){
                //因为左边内部有序，所以如果nums[i]>2*nums[p],
                // 那么后续的nums[i+1],i+2,...,mid肯定至少也有nums[i]匹配的对数，
                // 复用前面的结果，直接从不满足之前的元素的p位置开始，不用再重复检验
                p++;
            }

            res+=p-(mid+1);//从mid+1开始匹配，

            //比nums[i]小的元素统统先拷贝进去
            while (j<=right&&nums[i]>=nums[j]){
                temp[k++] = nums[j++];
            }
            temp[k++] = nums[i++];
        }

        //拷贝剩下的右边区间元素
        while (j<=right){
            temp[k++] = nums[j++];
        }

        //使得原数组有序
        System.arraycopy(temp,0,nums,left,temp.length);

        return res;
    }



    public static void main(String[] args) {
       int[] nums = new int[]{1,3,2,3,1};
       DivideConquer conquer = new DivideConquer();
       conquer.reversePairs(nums);
    }


}
