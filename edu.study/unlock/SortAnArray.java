package unlock;

public class SortAnArray {
  public int[] sortArray(int[] nums) {
    // Arrays.sort(nums);
    quickSort(nums,0,nums.length-1);
    return nums;
  }

  public void quickSort(int[] nums,int lo,int hi){
    if(hi<=lo)return;
    int j = partition(nums,lo,hi);
    quickSort(nums,lo,j-1);
    quickSort(nums,j+1,hi);
  }

  public int partition(int[] nums,int lo,int hi){
    int pivot = nums[lo], i = lo + 1, j = hi;
    while (i <= j) {
      if (nums[i] > pivot && nums[j] < pivot) {
        swap(nums,i++, j--);
      }
      if (nums[i] <= pivot) ++i;
      if (nums[j] >= pivot) --j;
    }

    swap(nums,lo,j);
    return j;
  }

  public void swap(int[] nums,int i,int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }




  public static void main(String[] args) {
    SortAnArray test = new SortAnArray();
    test.sortArray(new int[]{5,1,1,2,0,0});
  }
}
