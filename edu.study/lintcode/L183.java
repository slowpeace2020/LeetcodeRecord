package lintcode;

public class L183 {

  public int woodCut(int[] L, int k) {
    if (L == null || L.length == 0) {
      return 0;
    }
    int right = L[0];
    for (int num : L) {
      right = Math.max(right, num);
    }

    int left = 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      int count = 0;
      for (int num : L) {
        count += num / mid;
      }

      if (count >= k) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return right;
  }

  /**
   * @param pages: an array of integers
   * @param k: An integer
   * @return: an integer
   */
  public int copyBooks(int[] pages, int k) {
    if (pages == null || pages.length == 0) {
      return 0;
    }

    int left = 0;

    for (int page : pages) {
      left = Math.max(left, page);
    }
    int right = Integer.MAX_VALUE;
    while (left < right) {
      int mid = left + (right - left) / 2;
      int count = 1;
      int remainWork = mid;
      for (int page : pages) {
        if (remainWork >= page) {
          remainWork -= page;
        } else {
          count += (page + mid - 1) / mid;
          remainWork = mid - page % mid;
        }
      }

      if (count > k) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;

  }

  public int copyBooksII(int n, int[] times) {
    if (n == 0) {
      return 0;
    }
    int left = 0;
    int right = times[0] * n;

    while (left < right) {
      int mid = left + (right - left) / 2;
      int count = 0;
      for (int time : times) {
        count += mid / time;
      }
      if (count >= n) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return right;

  }

  public int copyBooksIII(int n, int[] times) {
    if (n == 0) {
      return 0;
    }
    int left = 0, right = times[0] * n;
    while (left < right) {
      int mid = left + (right - left) / 2;

      int count = 0;
      for (int i : times) {
        count += mid / i;
      }
      if (count >= n) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  private boolean check(int n, int[] times, int limit) {
    int count = 0;
    for (int i : times) {
      count += limit / i;
    }
    return count >= n;
  }



  public int copyBooksIX(int n, int[] times) {
    int left = 0, right = n * times[0];
    while (left + 1 < right) {
      int mid = left + (right - left) / 2;
      if (check( n,times, mid)) {
        right = mid;
      }
      else {
        left = mid;
      }
    }
    if (check( n,times, left))
      return left;
    return right;
  }

  public static void main(String[] args) {
    L183 test = new L183();
//    test.copyBooks(new int[]{3,2,4},2);
//    test.copyBooks(new int[]{1,2},5);
//    test.copyBooks(new int[]{1,9,7,3,4,7},3);
//    test.copyBooksII(4,new int[]{3,2,4});
//    test.copyBooksII(4,new int[]{3, 2, 4, 5});
    test.copyBooksIX(6212,new int[]{230,462,841,55,228,936,265,690,475,503,587,270,906,461,334,762,636,257,644,469,808,373,304,25,892,626,485,775,199,192,590,304,773,668,749,622,88,986,414,311,315,325,394,643,683,18,127,942,976,849,664,141,564,216,599,349,620,954,296,841,172,196,265,808,261,151,293,788,162,823,740,192,91,640,151,197,667,91,995,99,328,636,100,612,818,178,929,604,922,320,805,574,425,529,645,168,19,693,421,610,355,870,674,352,982,904,353,691,567,282,740,501,830,250,605,893,850,719,577,142,158,995,972,91,492,98,653,363,615,381,975,597,150,960,755,43,553,957,726,345,763,798,890,529,301,268,264,570,781,887,611,512,684,905,984,612,386,451,704,239,962,998,867,953,190,54,863,514,746,424,684,101,89,598,414,110,963,349,52,396,621,545,333,40,36,683,695,445,173,759,773,330,149,394,461,27,614,280,940,860,534,811,117,580,283,51,243,159,359,273,245,237,973,415,492,554,636,94,350,228,362,321,873,302,228,379,610,738,894,387,604,833,472,116,103,841,737,70,317,949,778,692,592,540,40,849,212,576,693,385,147,65,929,304,815,235,897,429,592,114});
//    test.copyBooksII(53,new int[]{274,233,513,981,372,328,251,926,363,542,573,966,846,940,848,22,804,881,492,151,583,371,403,135,571,37,647,517,929,915,500,372,763,94,167,100,53,679,878,463,310,568,689,631,817,820,967,40,371,589,787,891,304,496,419,64,791,768,330,678,15,2,924,958,583,919,750,63,435,858,478,221,752,238,410,510,56,475,749,329,633,844,111,978,282,561,91,327,298,301,367,402,805,50,962,91,155,84,955,280});
  }

}
