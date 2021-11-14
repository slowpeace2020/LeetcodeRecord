package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Pointers {

  public void sortColors(int[] nums) {
    int[] count = new int[3];
    int index = 0;
    for (int num : nums) {
      count[num]++;
    }

    for (int i = 0; i < 3; i++) {
      int num = count[i];
      for (int j = 0; j < num; j++) {
        nums[index++] = i;
      }
    }
  }
  public void sortColorsI(int[] nums) {
    int red = 0;
    int blue = nums.length - 1;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] == 0) {
        swap(nums, i, red++);
      } else if (blue > i && nums[i] == 2) {
        swap(nums, i, blue--);
      }
    }
  }

  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) {
      return nums.length;
    }
    int index = 2;
    for (int i = 2; i < nums.length; i++) {
      if (nums[index - 2] != nums[index - 1]) {
        nums[index] = nums[i];
        index++;
      } else if (nums[index - 1] != nums[i]) {
        nums[index] = nums[i];
        index++;
      }
    }

    return index;
  }

  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int[] res = new int[m + n];
    int i = 0;
    int j = 0;
    int k = 0;
    while (i < m && j < n) {
      res[k++] = nums1[i] < nums2[j] ? nums1[i++] : nums2[j++];
    }
    while (i < m) {
      res[k++] = nums1[i++];
    }
    while (j < n) {
      res[k++] = nums2[j++];
    }
    for (int s = 0; s < m + n; s++) {
      nums1[s] = res[s];
    }
  }

  public void moveZeroes(int[] nums) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[index++] = nums[i];
      }
    }

    for (int i = index; i < nums.length; i++) {
      nums[i] = 0;
    }
  }

  public int findKthLargest(int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    Queue<Integer> heap = new PriorityQueue<>(nums.length, (a, b) -> (b - a));
    for (int i = 0; i < nums.length; i++) {
      heap.add(nums[i]);
    }

    int kth = -1;
    while (k > 0) {
      kth = heap.poll();
      k--;
    }

    return kth;

  }

  public int findKthLargestI(int[] nums, int k) {
    return quickSelect(nums, k, 0, nums.length - 1);
  }

  public int quickSelect(int[] nums, int k, int lo, int hi) {
    if (lo >= hi) {
      return nums[k - 1];
    }
    int j = partition(nums, lo, hi);
    if (j == k - 1) {
      return nums[k - 1];
    } else if (j < k - 1) {
      return quickSelect(nums, k, j + 1, hi);
    } else {
      return quickSelect(nums, k, lo, j - 1);
    }
  }

  public int partition(int[] nums, int lo, int hi) {
    int i = lo + 1;
    int j = hi;
    while (i <= j) {
      if (nums[i] < nums[lo] && nums[j] > nums[lo]) {
        swap(nums, i++, j--);
      }
      if (nums[i] >= nums[lo]) {
        i++;
      }
      if (nums[j] <= nums[lo]) {
        j--;
      }
    }
    swap(nums, lo, j);

    return j;

  }

  private int partitionI(int[] a, int lo, int hi) {
    int i = lo, j = hi;
    while (true) {
      while (a[++i]<=a[lo])
        if (i == hi - 1)
          break;
      while (a[lo]>a[--j])
        if (j == lo)
          break;
      if (i >= j)
        break;
      swap(a, i, j);
    }
    swap(a, lo, j);
    return j;
  }

  public int[] topKFrequent(int[] nums, int k) {
    int[] res = new int[k];
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }
    PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(
        (a, b) -> (b.getValue() - a.getValue()));
    queue.addAll(map.entrySet());
    int index = 0;
    while (k > 0) {
      res[index++] = queue.poll().getKey();
      k--;
    }

    return res;

  }

  public int[] intersection(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0;
    int j = 0;
    List<Integer> res = new ArrayList<>();
    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] == nums2[j]) {
        res.add(nums1[i]);
        i++;
        j++;
      } else if (nums1[i] > nums2[j]) {
        j++;
      } else {
        i++;
      }
    }

    int[] array = new int[res.size()];
    for (i = 0; i < res.size(); i++) {
      array[i] = res.get(i);
    }

    return array;
  }

  public List<String> commonChars(String[] words) {
    int[] count = new int[26];
    Arrays.fill(count, Integer.MAX_VALUE);
    List<String> res = new ArrayList<>();
    for (String word : words) {
      int[] current = new int[26];
      for (char c : word.toCharArray()) {
        current[c - 'a']++;
      }
      for (int i = 0; i < 26; i++) {
        count[i] = Math.min(count[i], current[i]);
      }
    }

    for (int i = 0; i < 26; i++) {
      if (count[i] != 0 && count[i] != Integer.MAX_VALUE) {
        while (count[i] > 0) {
          char r = (char) (i + 'a');
          count[i]--;
          res.add(r + "");
        }
      }
    }

    return res;
  }

  public int longestMountain(int[] arr) {

    int res = 0;
    int up = 0;
    int down = 0;
    for (int i = 1; i < arr.length; i++) {
      if (down > 0 && (arr[i - 1] <= arr[i])) {
        up = 0;
        down = 0;
      }
      if (arr[i - 1] < arr[i]) {
        up++;
      }
      if (arr[i - 1] > arr[i]) {
        down++;
      }

      if (up > 0 && down > 0 && up + down + 1 > res) {
        res = up + down + 1;
      }
    }

    return res;
  }

  public int trap(int[] height) {
    return trap(height,0,height.length-1);
  }

  public int trap(int[] height,int left,int right) {
    int i=left;
    int j=right;
    while (i<right&&height[i]<=height[i+1]){
       i++;
    }
    left = i;
    while (j>left&&height[j]<=height[j-1]){
      j--;
    }

    right = j;
    i++;
    while (i<right){
        if(height[i]>=height[left]){
          return trap(height,left,i)+trap(height,i,right);
        }else {
          i++;
        }
    }
   j--;
    while (j>left){
      if(height[j]>=height[right]){
        return trap(height,left,j)+trap(height,j,right);
      }else {
        j--;
      }
    }


    return getWater(height,left,right);
  }

  public int getWater(int[] height,int left,int right){
    int h = Math.min(height[left],height[right]);
    int res = 0;
    for(int i=left+1;i<right;i++){
      if(height[i]>h){
        continue;
      }
      res+=h-height[i];
    }
    return res;
  }

  public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
    Arrays.sort(arr1);
    Arrays.sort(arr2);

    int res = Math.min(arr1.length,arr2.length);
    for (int num : arr1) {
      int i = 0;
      int j = arr2.length - 1;
      while (i <= arr2.length - 1) {
        if (arr2[i] < num - d) {
          i++;
        } else {
          break;
        }
      }

      while (j >= 0) {
        if (arr2[j] > num + d) {
          j--;
        } else {
          break;
        }
      }

      if (i <= j) {
        res --;
      }


    }
    return res;

  }

  public String multiply(String num1, String num2) {
    int[] res = new int[num1.length() + num2.length()];
    for (int i = num1.length() - 1; i >= 0; i--) {
      for (int j = num2.length() - 1; j >= 0; j--) {
        int current = res[i + j + 1] + (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
        res[i + j + 1] = current % 10;
        res[i + j] += current / 10;
      }
    }

    String result = "";
    for (int i = 0; i < res.length; i++) {
      if (!result.equals("") || res[i] != 0 || i == res.length - 1) {
        result += res[i];
      }
    }

    return result;
  }

  public int removeElement(int[] nums, int val) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != val) {
        nums[index++] = nums[i];
      }
    }

    return index;
  }


  public int maxProduct(String[] words) {
    int res = 0;
    for (int i = 0; i < words.length; i++) {
      for (int j = i + 1; j < words.length; j++) {
        res = Math.max(res, hasCommonWord(words[i], words[j]));

      }
    }
    return res;
  }

  public int hasCommonWord(String word1, String word2) {
    int[] count = new int[26];
    for (char c : word1.toCharArray()) {
      count[c - 'a']++;
    }
    for (char c : word2.toCharArray()) {
      if (count[c - 'a'] != 0) {
        return 0;
      }
    }

    return word1.length() * word2.length();
  }

  public String reverseWords(String s) {
    s = s.trim();
    while(s.contains("  ")){
      s = s.replaceAll("  "," ");
    }
    int start =0;
    int i=0;
    int j=s.length()-1;
    int end = s.length();
    while(i<j){
      while(s.charAt(i)!=' '){
        i++;
      }
      while(s.charAt(j)!=' '){
        j--;
      }
      int lenLeft = i-start;
      int lenRight = end-j-1;
      if(i>j){
        return s;
      }
      if(start>0){
        s = s.substring(0,start)+s.substring(j+1,end)+s.substring(i,j+1)+s.substring(start,i)+s.substring(end);
      }else {
                              s =s.substring(j+1,end)+s.substring(i,j+1)+s.substring(start,i);

      }
      i = i+lenRight-lenLeft+1;
      j = j+(lenRight-lenLeft)-1;
      start = i;
      end = j+1;
    }

    return s;
  }

  public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
    int i=0;
    int j=0;
    int k=0;
    List<Integer> res = new ArrayList<>();
    while (i<arr1.length&&j<arr2.length&&k<arr3.length){
      if(arr1[i]==arr2[j]&&arr1[i]==arr3[k]){
        res.add(arr1[i]);
        i++;
        j++;
        k++;
      }else {
        if(arr1[i]<arr2[j]||arr1[i]<arr3[k]){
          i++;
        }else if(arr2[j]<arr1[i]||arr2[j]<arr3[k]){
          j++;
        }else if(arr3[k]<arr1[i]||arr3[k]<arr2[j]){
          k++;
        }
      }

    }

    return res;
  }

  public List<Integer> pancakeSort(int[] arr) {
    int left = 0;
    int right = arr.length-1;
    List<Integer> res = new ArrayList<>();
    while(true){
      int j = right;

      while(arr[j]>arr[j-1]){
        j--;
        if(j<=left){
          break;
        }
      }

      if(j==0){
        break;
      }
      reverse(arr,0,j);
      res.add(j+1);
    }

    return res;
  }

  public void reverse(int[] arr, int start,int end){
    while(start<end){
      int temp = arr[start];
      arr[start] = arr[end];
      arr[end] = temp;
      start++;
      end--;
    }
  }

  public int partitionArray(int[] nums, int k) {
    // write your code here
    int i = 0;
    int j = nums.length - 1;
    while (i < j) {
      while (nums[i] < k) {
        i++;
        if (i > j) {
          break;
        }
      }
      while (nums[j] >= k) {
        j--;
        if (j <= i) {
          break;
        }
      }
      if (j <= i) {
        break;
      }
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }

    return i;
  }

  public void partition2(int[] nums, int low, int high) {
    // write your code here
    int i=0;
    int j=nums.length-1;
    while(i<j){
      while(nums[i]<low){
        i++;
        if(i>j)break;
      }

      while(nums[j]>=low){
        j--;
        if(j<i)break;
      }
      if(i>=j)break;
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }

    j=nums.length-1;
    while(i<j){
      while(nums[i]<=high){
        i++;
        if(i==j)break;
      }

      while(nums[j]>high){
        j--;
        if(j==i)break;
      }
      if(i>=j)break;
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }

  }

  public void sortColors2(int[] colors, int k) {
    sortColors2(colors,0,colors.length-1);
  }

  public void  sortColors2(int[] colors,int low,int high){
    if(high<=low)return;
    int j = partitionColor(colors,low,high);
    sortColors2(colors,low,j-1);
    sortColors2(colors,j+1,high);

  }

  public int partitionColor(int[] colors,int low,int high){
    int i=low+1;
    int j=high;
    while (true){
      while (colors[i]<colors[low]){
        i++;
        if(i>j)break;
      }

      while (colors[j]>=colors[low]){
        j--;
        if(j<i)break;
      }
      if(j<=i)break;
      swapColor(colors,i,j);
    }
    swapColor(colors,low,j);
    return j;
  }

  public void swapColor(int[] colors,int i,int j){
    int temp = colors[i];
    colors[i] = colors[j];
    colors[j] = temp;
  }

  public void rainbowSort(int[] colors, int start,int end,int from, int to){
    if(start>=end||from==to){
      return;
    }

    int pivot = (from+to)/2;
    int i=start;
    for(int j=start;i<=end;j++){
      if(colors[j]<=pivot){
        swapColor(colors,i,j);
        i++;
      }
    }

    rainbowSort(colors,start,i-1,from,pivot);
    rainbowSort(colors,i+1,end,pivot+1,to);
  }

  public void sortColors2ByCountingSort(int[] colors, int k) {
    int len = colors.length;
    if (len <= 0) {
      return;
    }
    int index = 0;
    while (index < len) {
      int temp = colors[index] - 1;
      if (colors[index] <= 0) {
        index++;
      } else {
        if (colors[temp] <= 0) {
          colors[temp]--;
          colors[index] = 0;
          index++;
        } else {
          swapColor(colors, index, temp);
          colors[temp] = -1;
        }
      }

    }
  }




    public static void main(String[] args) {
    Pointers pointers = new Pointers();
      int[] arr1 = {1,2,3,4,5}, arr2 = {1,2,5,7,9}, arr3 = {1,3,4,5,8};
//    pointers.pancakeSort(new int[]{3,2,4,1});
//    pointers.partitionArray(new int[]{3,2,1},2);
//    pointers.partitionArray(new int[]{7,7,9,8,6,6,8,7,9,8,6,6},10);
    pointers.sortColors2(new int[]{3,2,2,1,4},4);
//    pointers.partitionArray(new int[]{3,2,2,1},2);
//    pointers.findTheDistanceValue(new int[]{4,5,8},
//new int[]{10,9,1,8},2);
//    pointers.reverseWords("the sky is blue");
//    pointers.reverseWords("a good   example");
//    pointers.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});

  }

}
