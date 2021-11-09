package unlock;

/**
 * test
 */
public class Peak {

  /**
   * for tes
   *
   * @param nums
   * @return
   */
  public int findPeakElement(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] < nums[mid + 1]) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return right;
  }

  /**
   * 迭代
   *
   * @param image
   * @param x
   * @param y
   * @return
   */

  public int minAreaII(char[][] image, int x, int y) {
    int left = y;
    int right = y;
    int up = x;
    int down = x;
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        if (image[i][j] == '1') {
          left = Math.min(left, i);
          right = Math.max(right, i);
          up = Math.min(up, j);
          down = Math.max(down, j);
        }
      }
    }

    return (right - left + 1) * (down - up + 1);
  }

  /**
   * 递归
   *
   * @param image
   * @param x
   * @param y
   * @return
   */
  public int minAreaI(char[][] image, int x, int y) {
    Integer left = y;
    Integer right = y;
    Integer up = x;
    Integer down = x;
    dfs(image, x, y, left, right, up, down);
    return (right - left + 1) * (down - up + 1);

  }


  public void dfs(char[][] image, int x, int y, Integer left, Integer right, Integer up,
      Integer down) {
    if (x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] != 0) {
      return;
    }
    left = Math.min(left, x);
    right = Math.max(right, x);
    up = Math.min(up, y);
    down = Math.max(down, y);
    dfs(image, x - 1, y, left, right, up, down);
    dfs(image, x + 1, y, left, right, up, down);
    dfs(image, x, y - 1, left, right, up, down);
    dfs(image, x, y + 1, left, right, up, down);
  }

  public int minArea(char[][] image, int x, int y) {
    int m = image.length;
    int n = image[0].length;
    int up = binarySearch(image, true, true, 0, x, 0, n);
    int down = binarySearch(image, true, false, x + 1, m, 0, n);
    int left = binarySearch(image, false, true, 0, y, up, down);
    int right = binarySearch(image, false, false, y + 1, n, up, down);
    return (right - left) * (down - up);
  }

  /**
   * @param image
   * @param horizontal 搜索行还是列
   * @param opt        最左边界还是最右边界
   * @param i
   * @param j
   * @param low
   * @param high
   * @return
   */
  private int binarySearch(char[][] image, boolean horizontal, boolean opt, int i, int j, int low,
      int high) {

    while (i < j) {
      int k = low, mid = (i + j) / 2;
      while (k < high && (horizontal ? image[mid][k] : image[k][mid]) == '0') {
        ++k;
      }
      if (k < high == opt) {
        j = mid;
      } else {
        i = mid + 1;
      }
    }
    return i;
  }

  public int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = (int) 1E10;
    while (left < right) {
      int mid = left + (right - left) / 2;
      int count = 0;
      for (int pile : piles) {
        //向上取整
        //count+=Math.ceil(pile/mid);
        //也可以这样向上取整,把超出背书背的余数部分补足，保证加1，同时除尽的时候不加1
        count += (pile + mid - 1) / mid;
      }
      if (count > h) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return right;
  }


  public int smallestDivisor(int[] nums, int threshold) {
    int left = 1;
    int right = 1000000;
    while (left < right) {
      int mid = left + (right - left) / 2;
      int sum = 0;
      for (int num : nums) {
        sum += (num + mid - 1) / mid;
      }

      if (sum > threshold) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }
    return right;
  }

  public int mySqrt(int x) {
    int left = 1;
    int right = x;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (x / mid == mid) {
        return mid;
      } else if (x / mid > mid) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return right;
  }

  public double myPow(double x, int n) {
    double res = 1.0;
    for (int i = n; i != 0; i /= 2) {
      if (i % 2 != 0) {
        res = res * x;
      }
      x *= x;
    }
    return n < 0 ? 1 / res : res;
  }

  public double myPowI(double x, int n) {
    if (n == 0) {
      return 1.0;
    }
    double res = 1;
    double half = myPow(x,n/2);
    if(n%2==0)return half*half;
    if(n>0)return half*half*x;
    return half*half/x;
  }

  public boolean isPerfectSquare(int num) {
    int left = 1;
    int right = num;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if ((num / mid == mid) && (num % mid == 0)) {
        return true;
      } else if (num / mid > mid) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return false;
  }

  public static void main(String args[]) {
    String s1 = "abc";
    String s2 = new String("abc");

    if(s1 == s2)
      System.out.println(1);
    else
      System.out.println(2);
    if(s1.equals(s2))
      System.out.println(3);
    else
      System.out.println(4);
  }


}
