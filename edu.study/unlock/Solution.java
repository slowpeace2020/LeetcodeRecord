package unlock;

import java.util.*;

public class Solution {

  public int minMoves2(int[] nums) {
    if (nums.length == 0) {
      return 0;
    }
    int sum = 0;

    for (int num : nums) {
      sum += num;
    }

    int average1 = sum / nums.length;
    int average2 = (sum / nums.length) + 1;
    System.out.println(average1);
    System.out.println(average2);
    int res1 = 0;
    int res2 = 0;
    for (int num : nums) {
      res1 += Math.abs(num - average1);
      res2 += Math.abs(num - average2);
    }

    System.out.println(res1);
    System.out.println(res2);

    return Math.min(res1, res2);
  }

  public boolean stoneGame(int[] piles) {
    int res = getStone(piles, 0, piles.length - 1, 1);
    return res > 0;
  }

  public int getStone(int[] nums, int start, int end, int turn) {
    if (start < 0 || end >= nums.length) {
      return 0;
    }
    if (start == end) {
      return nums[start] * turn;
    }

    return Math.max(nums[start] * turn + getStone(nums, start + 1, end, -turn),
        nums[end] * turn + getStone(nums, start, end - 1, -turn));
  }


  public String longestDiverseString(int a, int b, int c) {
    String res = "";
    PriorityQueue<Pair> queue = new PriorityQueue<Pair>((a1, b1) -> (b1.count - a1.count));
    queue.add(new Pair('a', a));
    queue.add(new Pair('b', b));
    queue.add(new Pair('c', c));

    char pre = '0';
    Pair toAdd = null;
    while (!queue.isEmpty()) {
      Pair current = queue.poll();
      char c1 = current.c;
      int count = current.count;
      if (count == 0) {
        continue;
      }
      if (toAdd != null) {
        queue.add(toAdd);
        toAdd = null;
      }
      if (c1 != pre) {
        if (count >= 1) {
          res += current.c;
          count--;
        }
        pre = current.c;
        if (count > 0) {
          current.count = count;
          queue.add(current);
        }
      } else {
        if (queue.isEmpty()) {
          return res;
        } else {
          toAdd = current;
        }
      }
    }

    return res;


  }

  class Pair {

    char c;
    int count;

    Pair(char c, int count) {
      this.c = c;
      this.count = count;
    }
  }

  public String longestDiverseString1(int a, int b, int c) {
    int freq[] = new int[3];
    freq[0] = a;
    freq[1] = b;
    freq[2] = c;
    Queue<Integer> q = new PriorityQueue<>((e1, e2) -> freq[e2] - freq[e1]);
      if (a != 0) {
          q.add(0);
      }
      if (b != 0) {
          q.add(1);
      }
      if (c != 0) {
          q.add(2);
      }
    StringBuilder sb = new StringBuilder();
    while (!q.isEmpty()) {
      int x = q.poll();
      if (sb.length() > 1 && sb.charAt(sb.length() - 1) - 97 == x
          && sb.charAt(sb.length() - 2) - 97 == x) {
          if (q.isEmpty()) {
              break;
          }
        int t = q.poll();
        q.add(x);
        x = t;
      }
      sb.append((char) (x + 97));
        if (--freq[x] != 0) {
            q.add(x);
        }
    }
    return sb.toString();
  }


  public int maxRepeating(String sequence, String word) {
    int num = sequence.length();
    int length = word.length();
    int max = num / length;
    if (!sequence.contains(word)) {
      return 0;
    }

    StringBuilder sb = new StringBuilder();
    int n = max;
    while (n != 0) {
      sb.append(word);
      n--;
    }

    String last = sb.toString();

    int left = 1;
    int right = max;
    while (left < right) {
      int mid = left + (right - left) / 2;
      String current = last.substring(0, word.length() * mid + 1);
      if (sequence.contains(current)) {
        left = mid;
      } else {
        right = mid - 1;
      }
    }

    return right;
  }


  public List<Integer> minSubsequence(int[] nums) {
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
    }

    List<Integer> list = new ArrayList<>();
    int half = sum / 2 + 1;
//        Arrays.sort(nums,Collections.reverseOrder());
    int cur = 0;
    int i = 0;
    while (cur < half) {
      cur += nums[i];
      list.add(nums[i]++);
    }

    return list;


  }


  public boolean validPalindrome(String s) {
    int i = 0, j = s.length() - 1;
    while (i < j) {
      if (s.charAt(i) == s.charAt(j)) {
        i++;
        j--;
      } else {
        int iStart = i;
        i++;
        int jEnd = j;
        while (i < j) {
          if (s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
          } else {
            break;
          }

        }
        if (s.charAt(i) == s.charAt(j)) {
          return true;
        }
        i = iStart;
        j = jEnd;
        j--;
        while (i < j) {
          if (s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
          } else {
            return false;
          }
        }
      }
    }

    return true;
  }


  public List<Integer> countSmaller(int[] nums) {
    List<Integer> res = new ArrayList<>();
    res.add(0);

    List<Integer> list = new ArrayList<>();
    int n = nums.length;
    list.add(nums[n - 1]);
    for (int i = n - 2; i >= 0; i--) {
      int index = getIndex(list, nums[i]);
      res.add(0, index);
    }

    return res;


  }

  public int getIndex(List<Integer> list, int target) {
    int left = 0;
    int right = list.size() - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (list.get(mid) >= target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    list.add(left, target);
    return left;
  }

  public int findTargetSumWays(int[] nums, int target) {
    int res = helpFindTarget(nums, 0, target, 0);
    return res;
  }

  public int helpFindTarget(int[] nums, int start, int target, int sum) {
    if (start > nums.length) {
      return 0;
    }

    if (target == sum && start == nums.length) {
      return 1;
    }

    int res = helpFindTarget(nums, start + 1, target, sum - nums[start]);
    res += helpFindTarget(nums, start + 1, target, sum + nums[start]);

    return res;

  }

  public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
    int res = 0;
    for (int i = 0; i < group.length; i++) {
      res += helpScheme(n - group[i], minProfit - profit[i], group, i, profit);
    }
    return res;
  }

  public int helpScheme(int n, int minProfit, int[] group, int start, int[] profit) {
    if (n < 0) {
      return 0;
    }

    int res = 0;
    if (minProfit <= 0) {
      res++;
    }
    if (start == profit.length) {
      return res;
    }
    for (int i = start + 1; i < profit.length; i++) {
      res += helpScheme(n - group[i], minProfit - profit[i], group, i, profit);
    }

    return res % (10 ^ 9 + 7);
  }


  public String largestNumber(int[] cost, int target) {
    int dp[] = new int[target + 1];
    //make-dp
    for (int i = 1; i <= target; i++) {
      dp[i] = -1000_000;
        for (int j = 0; j < 9; j++) {
            if (cost[j] <= i) {
                dp[i] = Math.max(dp[i], 1 + dp[i - cost[j]]);
            }
        }
    }
      if (dp[target] < 0) {
          return "0";
      }
    StringBuilder res = new StringBuilder();
    //retrieve string from dp
    for (int i = 8; i >= 0; i--) {
      while (target >= cost[i] && dp[target] == dp[target - cost[i]] + 1) {
        res.append(i + 1);
        target -= cost[i];
      }
    }
    return res.toString();
  }


  public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] res = new int[n - k];
    PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> (nums[b] - nums[a]));
    for (int i = 0; i < k - 1; i++) {
      queue.offer(i);
    }
    int index = 0;
    for (int i = k - 1; i < n; i++) {
      queue.offer(i);
      int max = queue.peek();
      if (queue.size() > k && max == i - k) {
        queue.poll();
        max = queue.peek();
      }
      res[index++] = max;
    }

    return res;

  }

  public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
  }


  public int[] searchRange(int[] nums, int target) {
    int n = nums.length;
    int[] res = new int[2];
    Arrays.fill(res, -1);
    if (n == 0) {
      return res;
    }

    int left = 0;
    int right = n - 1;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] >= target) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    if (right > n || left >= n || nums[right] != target) {
      return res;
    }

    res[0] = right;
    left = 0;
    right = n - 1;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] <= target) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    res[1] = nums[left] == target ? left : left - 1;
    return res;

  }

  public int[] searchRangeI(int[] nums, int target) {
    int[] res = new int[2];
    Arrays.fill(res, -1);
    if (nums.length == 0) {
      return res;
    }

    int index = -1;
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        index = mid;
        break;
      } else if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    if (index == -1) {
      return res;
    }

    //看边界怎么处理，最后一个值要不要，先确定left的值可取再减
    left = index;
    while (left > 0 && nums[index] == nums[left - 1]) {
      left--;
    }
    right = index;
    while (right < nums.length - 1 && nums[index] == nums[right + 1]) {
      right++;
    }

    res[0] = left;
    res[1] = right;

    return res;
  }

  public int[] finalPrices(int[] prices) {
    int[] res = new int[prices.length];
    res[prices.length - 1] = prices[prices.length - 1];
    Stack<Integer> stack = new Stack<>();
    stack.push(prices[prices.length - 1]);
    for (int i = prices.length - 2; i >= 0; i--) {
      int price = prices[i];
      int current = stack.peek();
      while (current > price && !stack.isEmpty()) {
        current = stack.pop();
      }

      if (current <= price) {
        res[i] = price - current;
        stack.push(current);
      } else {
        res[i] = price;
      }
      stack.push(price);
    }

    return res;

  }

  public int findMin(int[] nums) {
    return divideAndConquer(nums, 0, nums.length - 1);
  }

  public int divideAndConquer(int[] nums, int start, int end) {
    if (nums[start] <= nums[end]) {
      return nums[start];
    }
    int mid = start + (end - start) / 2;
    return Math.min(divideAndConquer(nums, start, mid), divideAndConquer(nums, mid + 1, end));
  }
  public int findMinI(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] < nums[right]) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return nums[right];
  }


  public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        return mid;
      }
      if (nums[mid] < nums[right]) {
        if (nums[mid] < target && nums[right] >= target) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      } else {
        if (nums[left] <= target && nums[mid] > target) {
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      }
    }
    return -1;

  }
  public int searchII(int[] nums, int target) {

    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if(nums[mid]==target){
        return mid;
      }
      if(nums[mid]<nums[right]){
        if(nums[mid]<target&&target<=nums[right]){
          left = mid+1;
        }else {
          right = mid-1;
        }
      }else {
        if(nums[left]<=target&&target<nums[mid]){
          right = mid-1;
        }else {
          left = mid+1;
        }
      }
    }
    return -1;
  }

  public int searchI(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[right] > nums[mid]) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    int index = right;

    left = 0;
    right = index - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }

    }
    left = index;
    right = nums.length - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }

    }

    return -1;

  }


  public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int left = 0;
    int right = arr.length - 1;
    boolean find = false;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (arr[mid] == x) {
        left = mid;
        find = true;
        break;
      } else if (arr[mid] > x) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    List<Integer> res = new ArrayList<>();
    int i = left - 1;
    int j = left + 1;
    if (find) {
      res.add(arr[left]);
      k--;
    } else {
      j = left;
    }
    while (k > 0 && i >= 0 && j < arr.length) {
      if (Math.abs(x - arr[i]) <= Math.abs(x - arr[j])) {
        res.add(0, arr[i--]);
      } else {
        res.add(arr[j++]);
      }
      k--;
    }

    while (k > 0 && i >= 0) {
      res.add(0, arr[i--]);
    }

    while (k > 0 && j < arr.length) {
      res.add(arr[j++]);
    }

    return res;
  }

  public int largestPerimeter(int[] nums) {
    Arrays.sort(nums);
    for (int i = nums.length - 1; i >= 2; i--) {
      if (nums[i] < nums[i - 1] + nums[i - 2]) {
        return nums[i] + nums[i - 1] + nums[i - 2];
      }
    }
    return 0;
  }

  public static void main(String[] args) {
    Solution solution = new Solution();
    String my = "weer";
//    solution.findMinII(new int[]{1,3,5});

//        solution.longestDiverseString1(0,8,11);
//        solution.longestDiverseString1(1,1,7);
//        solution.profitableSchemes(5,3,new int[]{2,2},new int[]{2,3});
//    solution.findClosestElements( new int[]{1,2,3,4,5}, 4,3);
    solution.findClosestElements( new int[]{1,3}, 1,2);
  }
}
