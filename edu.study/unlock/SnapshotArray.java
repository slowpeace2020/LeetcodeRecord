package unlock;

import java.util.ArrayList;
import java.util.List;

public class SnapshotArray {
  List<int[]>[] arr;
  int snapId;

  public SnapshotArray(int length){
    arr =  new List[length];
    snapId = 0;
    for(int i=0;i<length;i++){
      List<int[]> element = new ArrayList<>();
      element.add(new int[2]);
      arr[i] = element;
    }
  }

  public void set(int index, int val) {
    List<int[]> element = arr[index];
    int[] lastSnap = element.get(element.size()-1);
    if(lastSnap[0]<snapId){
      element.add(new int[]{snapId,val});
      return;
    }
    lastSnap[0] = snapId;
    lastSnap[1] = val;
  }

  public int snap(){
    return snapId++;
  }

  public int get(int index, int snap_id) {
    List<int[]> element = arr[index];
    return binarySearch(element,snap_id);
  }

  private int binarySearch(List<int[]> snaps,int target){
    int low = 0;
    int high = snaps.size()-1;
    while (low<high){
      int mid = low+(high-low)/2;
      if(snaps.get(mid)[0]<target){
        low = mid+1;
      }else if(snaps.get(mid)[0]==target){
        return snaps.get(mid)[1];
      }else {
        high = mid-1;
      }
    }

    return low == 0 ? snaps.get(0)[1] : snaps.get(low - 1)[1];
  }

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // Check if num1 is smaller than num2
    // If not, then we will swap num1 with num2
    if (nums1.length > nums2.length) {
      return findMedianSortedArrays(nums2, nums1);
    }
    // Lengths of two arrays
    int m = nums1.length;
    int n = nums2.length;
    // Pointers for binary search
    int start = 0;
    int end = m;
    // Binary search starts from here
    while (start <= end) {
      // Partitions of both the array
      int partitionNums1 = (start + end) / 2;
      int partitionNums2 = (m + n + 1) / 2 - partitionNums1;
      // Edge cases
      // If there are no elements left on the left side after partition
      int maxLeftNums1 = partitionNums1 == 0 ? Integer.MIN_VALUE : nums1[partitionNums1 - 1];
      // If there are no elements left on the right side after partition
      int minRightNums1 = partitionNums1 == m ? Integer.MAX_VALUE : nums1[partitionNums1];
      // Similarly for nums2
      int maxLeftNums2 = partitionNums2 == 0 ? Integer.MIN_VALUE : nums2[partitionNums2 - 1];
      int minRightNums2 = partitionNums2 == n ? Integer.MAX_VALUE : nums2[partitionNums2];
      // Check if we have found the match
      if (maxLeftNums1 <= minRightNums2 && maxLeftNums2 <= minRightNums1) {
        // Check if the combined array is of even/odd length
        if ((m + n) % 2 == 0) {
          return (Math.max(maxLeftNums1, maxLeftNums2) + Math.min(minRightNums1, minRightNums2)) / 2.0;
        } else {
          return Math.max(maxLeftNums1, maxLeftNums2);
        }
      }
      // If we are too far on the right, we need to go to left side
      else if (maxLeftNums1 > minRightNums2) {
        end = partitionNums1 - 1;
      }
      // If we are too far on the left, we need to go to right side
      else {
        start = partitionNums1 + 1;
      }
    }
    // If we reach here, it means the arrays are not sorted
    throw new IllegalArgumentException();
  }

  public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length;
    int n = matrix[0].length;
    int start = 0;
    int end = m*n-1;
    while(start<=end){
      int mid = start+(end-start)/2;
      int x = mid/n;
      int y = mid%n;
      if(matrix[x][y]==target){
        return true;
      }else if(matrix[x][y]>target){
        end = mid-1;
      }else{
        start = mid+1;
      }
    }

    return false;
  }
  public static void main(String args[]) {

  }



}
