package unlock;

import java.util.Arrays;
import java.util.Random;

public class PointersII {

  public int kthSmallest(int k, int[] nums) {
    int j = partition(nums,0,nums.length-1);
    while (j!=k-1){
      if(j<k-1){
        j = partition(nums,j+1,nums.length-1);
      }else {
        j = partition(nums,0,j-1);
      }
    }

    return nums[k-1];
  }




  private int partitionI(int[] nums, int low,int high){
    int i=low+1;
    int j= high;
    while (true){
      while (nums[i]<nums[low]){
        i++;
        if(i>j)break;
      }

      while (nums[j]>=nums[low]){
        j--;
        if(j<i)break;
      }
      if(j<=i)break;
      swap(nums,i,j);

    }

    swap(nums,j,low);
    return j;
  }

  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  public int kthSmallestByQuickSelect(int k, int[] nums) {
    int start = 0;
    int end = nums.length - 1;
    while (end > start) {
      int j = quickSelect(nums, start, end);
      if (j == k - 1) {
        return nums[k - 1];
      } else if (j > k - 1) {
        end = j - 1;
      } else {
        start = j + 1;
      }
    }

    return nums[k - 1];
  }


  public int[] topk(int[] nums, int k) {
    // write your code here
    int[] res = new int[k];
    int start = 0;
    int end = nums.length - 1;
    while (end > start) {
      int j = quickSelect(nums, start, end);
      if (j == k - 1) {
        break;
      } else if (j > k - 1) {
        end = j - 1;
      } else {
        start = j + 1;
      }
    }

    res[k - 1] = nums[k - 1];

    quickSort(nums, 0, k - 2);
    for (int i = 0; i < k - 1; i++) {
      res[i] = nums[i];
    }

    return res;
  }

  public int quickSelect(int[] nums, int start, int end) {
    if (start == end) {
      return nums[start];
    }

    int i = start + 1;
    int j = end;
    while (true) {
      while (nums[i] >= nums[start]) {
        i++;
        if (i > j) {
          break;
        }
      }

      while (nums[j] < nums[start]) {
        j--;
        if (j < i) {
          break;
        }
      }
      if (j <= i) {
        break;
      }
      swap(nums, i, j);
    }
    swap(nums, start, j);
    return j;

  }

  private void quickSort(int[] nums, int start, int end) {
    if (start >= end) {
      return;
    }
    int j = partition(nums, start, end);
    quickSort(nums, start, j - 1);
    quickSort(nums, j + 1, end);
  }

  private int partition(int[] nums, int start, int end) {
    if (start >= end) {
      return start;
    }

    int i = start + 1;
    int j = end;
    while (true) {
      while (nums[i] >= nums[start]) {
        i++;
        if (i > j) {
          break;
        }
      }

      while (nums[j] < nums[start]) {
        j--;
        if (j < i) {
          break;
        }
      }
      if (j <= i) {
        break;
      }
      swap(nums, i, j);
    }
    swap(nums, start, j);

    return j;

  }

  public int[] sortArrayByParityII(int[] nums) {
    int i = 0;
    int j = nums.length - 1;
    while (true) {
      while (nums[i] % 2 == 0) {
        i += 2;
        if (i > nums.length - 1) {
          break;
        }
      }

      while (nums[j] % 2 != 0) {
        j -= 2;
        if (0 > j) {
          break;
        }
      }
      if (i > nums.length - 1 || j < 0) {
        break;
      }
      swap(nums, i, j);
    }

    return nums;

  }

  public int findPairs(int[] nums, int k) {
    Arrays.sort(nums);
    int res = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      if (i >= 1 && nums[i] == nums[i - 1]) {
        continue;
      }
      for (int j = i + 1; j < nums.length; j++) {
        if (j >= i + 2 && nums[j] == nums[j - 1]) {
          continue;
        }

        if (nums[j] - nums[i] == k) {
          res++;
        } else if (nums[j] - nums[i] > k) {
          break;
        }
      }
    }

    return res;

  }


  public static void main(String[] args) {
  PointersII pointers = new PointersII();
//  pointers.findPairs(new int[]{1,3,1,5,4},0);
  pointers.findPairs(new int[]{1,1,1,2,2},0);
  }

}
