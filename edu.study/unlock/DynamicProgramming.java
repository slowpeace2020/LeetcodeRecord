package unlock;

import java.util.*;
import java.util.stream.Stream;

public class DynamicProgramming {


  int min = Integer.MAX_VALUE;

  public int backPack(int m, int[] A) {
    Arrays.sort(A);
    boolean[] visited = new boolean[m + 1];
    int[] memo = new int[m + 1];
    dfs(m, A, 0, visited, memo);

    return m - min;
  }


  private int dfs(int target, int[] A, int startIndex, boolean[] visited, int[] memo) {
    if (visited[target]) {
      return memo[target];
    }

    int res = target;
    for (int i = startIndex; i < A.length; i++) {
      if (target - A[i] >= 0) {
        res = dfs(target - A[i], A, i + 1, visited, memo);
      } else {
        break;
      }
    }

    visited[target] = true;
    memo[target] = res;
    min = Math.min(min, res);

    return res;
  }


  public int backPackIII(int m, int[] A) {
    int[] dp = new int[m + 1];
    for (int i = 0; i < A.length; i++) {
      for (int j = m; j >= A[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j - A[i]] + A[i]);
      }
    }
    return dp[m];
  }


//  public int backPackII(int m, int[] A) {
//    int dp[][] = new int[A.length + 1][m + 1];
//    for (int i = 1; i < dp.length; i++) {
//      //不超过j的最大重量可能值比较
//      for (int j = 0; j <= m; j++) {
//        dp[i][j] = dp[i - 1][j];
//        if (j >= A[i - 1]) {
//          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - A[i - 1]] + A[i - 1]);
//        }
//      }
//    }
//    return dp[A.length][m];
//  }

  public int backPackI(int m, int[] A) {
    // write your code here
    int n = A.length;
    int res =0;
    int[][] dp = new int[n][2];
    dp[0][1]=A[0]>m?0:A[0];

    for(int i=1;i<n;i++){
      int value = A[i]>m?0:A[i];
      dp[i][1] = value;
      for(int j=i-1;j>=0;j--){
        dp[i][0] = Math.max(dp[j][0],dp[j][1]);
        if(dp[j][0]+value<=m){
          dp[i][1] = Math.max(dp[j][0]+value,dp[i][1]);
        }
        if(dp[j][1]+value<=m){
          dp[i][1] = Math.max(dp[j][1]+value,dp[i][1]);
        }
      }

      res = Math.max(res,Math.max(dp[i][0],dp[i][1]));
    }


    return res;
  }

  public int backPackII(int m, int[] A, int[] V) {
    // write your code here
    int[][] dp=new int[A.length+1][m+1];
    for(int i=1;i<=m;i++){
      dp[0][i] = -1;
    }
    for(int i=1;i<=A.length;i++){
      for(int j=1;j<=m;j++){
        dp[i][j] = dp[i-1][j];
        System.out.println(i);
        if(j>=A[i-1]&&dp[i][j-A[i-1]]!=-1){
          dp[i][j] = Math.max(dp[i][j],dp[i-1][j-A[i-1]]+V[i-1]);
        }
        System.out.println(i+","+j+","+dp[i][j]);
      }
    }

    System.out.println(dp[A.length][m]);

    return dp[A.length][m];
  }

  public int backPackIIA(int m, int[] A, int[] V) {
    // write your code here
    // dp[i][j] represent
    // get first ith items to add into the bag with size j, the maximum value we can get
    int[][] dp = new int[A.length + 1][m + 1];
    // initalization
    // function: dp[i][j] = max(dp[i-1][j] or dp[i-1][j - A[i-1]] + V[i-1])
    dp[0][0] = 0;

    for (int i = 1; i <= A.length; i++) {
      for (int j = 1; j <= m; j++) {
        dp[i][j] = dp[i - 1][j]; // don't select ith item
        if (j >= A[i - 1]) {
          dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
        }
      }
    }

    return dp[A.length][m];
  }

  public int maxValueI(int[][] events, int k) {
    Arrays.sort(events,(a,b)->(a[1]-b[1]==0?a[0]-b[0]:a[1]-b[1]));
    Map<String,Integer> cache = new HashMap<>();
    return dfsMaxValue(events,0,0,k,0,cache);
  }

  /**
   *
   * @param events      事件数组
   * @param startIndex  剩余待选择的起始位置
   * @param count     已经选择的事件数量
   * @param k         可选事件数量的最大值
   * @param endDay    前一个已选事件的结束事件
   * @param cache     已经找到的最优结果。以事件数量_结束事件时间为key
   * @return
   */
  private int dfsMaxValue(int[][] events, int startIndex, int count, int k,int endDay, Map<String, Integer> cache) {
    if(count==k||startIndex==events.length){
      return 0;
    }

    String key = count+"_"+endDay;
    //这是一个递归栈
    //看是否已经搜索过了，结果存在map中
    Integer val = cache.get(key);
    //有就直接返回
    if(val!=null){
      return val;
    }

    //不选当前事件的最优结果
    int max = dfsMaxValue(events,startIndex+1,count,k,endDay,cache);

    //若前一个事件的结束时间比当前事件的开始时间小
    //则可以选当前事件的最优结果
    if(events[startIndex][0]>endDay){
      max = Math.max(max,dfsMaxValue(events,startIndex+1,count+1,k,events[startIndex][1],cache))+events[startIndex][2];
    }

    cache.put(key,max);
    return max;
  }


  public int maxValue(int[][] events, int k) {
    Arrays.sort(events,(a,b)->(a[1]-b[1]));
    int n = events.length;
    int[] prev = new int[n];
    for(int i=0;i<n;i++){
      prev[i] = binarySearchIndex(events,events[i][0]);
    }

    int[][] dp = new int[n+1][k+1];
    for(int i=1;i<=n;i++){
      for(int j=1;j<=k;j++){
        dp[i][j] = Math.max(dp[i][j],dp[i][j-1]);
        dp[i][j] = Math.max(dp[i][j],dp[i-1][j]);
        dp[i][j] = Math.max(dp[i][j],dp[prev[i]][j-1]+events[i][2]);
      }
    }

    return dp[n][k];
  }

  private int binarySearchIndex(int[][] events, int val) {
    int left =0;
    int right = events.length;
    while (left<right){
      int mid = left+(right-left)/2;
      if(events[mid][1]<val){
        left=mid;
      }else {
        right=mid-1;
      }
    }
    return right;
  }

  public int numWays(int n, int[][] relation, int k) {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    for(int[] rel:relation){
      int from = rel[0];
      int to = rel[1];
      map.putIfAbsent(from,new HashSet<>()).add(to);
    }

    Queue<Integer> queue = new LinkedList<>();
    queue.add(0);

    //k先比较再减
    while (!queue.isEmpty()&&k-->0){
      int size = queue.size();
      while (size-->0){

        int cur = queue.poll();
        queue.addAll(map.getOrDefault(cur,new HashSet<>()));
      }
    }

    int res = 0;
    while (!queue.isEmpty()){
      if(queue.poll()==n-1){
        res++;
      }
    }

    return res;
  }
  int ways = 0;
  public int numWaysI(int n, int[][] relation, int k) {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    for(int[] rel:relation){
      int from = rel[0];
      int to = rel[1];
      map.putIfAbsent(from,new HashSet<>()).add(to);
    }

    dfsWays(0,0,n,k,map);
    return ways;
  }

  private void dfsWays(int start, int step,int n, int k, Map<Integer, Set<Integer>> map) {
      if(step==k){
        if(start==n-1){
          ways++;
        }
        return;
      }

      for(int next:map.getOrDefault(start,new HashSet<>())){
        dfsWays(next,step+1,n,k,map);
      }
  }

  public int rob(int[] nums) {
    int n=nums.length;
    if(n==1){
      return nums[0];
    }
    int[] dp = new int[n-1];
    dp[0]=nums[0];
    dp[1]= Math.max(nums[0],nums[1]);
    if(n<=2){
      return dp[n-1];
    }
    for(int i=2;i<n-1;i++){
      dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i]);
    }

    int res = dp[n-2];

    dp = new int[n-1];
    dp[0]=nums[1];
    dp[1]=Math.max(nums[1],nums[2]);
    for(int i=3;i<n;i++){
      dp[i-1]=Math.max(dp[i-2],dp[i-3]+nums[i]);
    }

    return Math.max(res,dp[n-2]);
  }

  public int minDistance(String word1, String word2) {
    int m = word1.length();
    int n = word2.length();
    int[][] dp = new int[m+1][n+1];

    for(int i=0;i<=m;i++){
      dp[i][0]=i;
    }

    for(int i=0;i<=n;i++){
      dp[0][i]=i;
    }

    for(int i=1;i<=m;i++){
      char one = word1.charAt(i-1);
      for(int j=1;j<=n;j++){
        char two = word2.charAt(j-1);
        if(one==two){
          dp[i][j]=dp[i-1][j-1];
        }else{
          dp[i][j]=Math.min(dp[i-1][j],dp[i-1][j-1])+1;
        }
      }
    }

    return  dp[m][n];
  }

  public int numDistinct(String s, String t) {
    int m = s.length();
    int n = t.length();
    int[][] dp = new int[m+1][n+1];
    for(int i=0;i<=m;i++){
      dp[i][0]=1;
    }

    for(int i=1;i<=m;i++){
      for(int j=1;j<=n;j++){
        dp[i][j]+=dp[i-1][j];
        if(s.charAt(i-1)==t.charAt(j-1)){
          dp[i][j]+=dp[i-1][j-1];
        }
      }
    }

    return dp[m][n];
  }

  public boolean isInterleave(String s1, String s2, String s3) {
    int m=s1.length();
    int n= s2.length();
    boolean[][] dp = new boolean[m+1][n+1];
    dp[0][0]=true;
    for(int i=1;i<=m;i++){
      if(dp[i-1][0]&&s1.charAt(i-1)==s3.charAt(i-1)){
        dp[i][0] = true;
      }else {
        break;
      }
    }

    for(int i=1;i<=n;i++){
      if(dp[i-1][0]&&s2.charAt(i-1)==s3.charAt(i-1)){
        dp[0][i] = true;
      }else {
        break;
      }
    }

    for(int i=1;i<=m;i++){
      for(int j=1;j<=n;j++){
        dp[i][j]|=dp[i-1][j]&&(s1.charAt(i-1)==s3.charAt(i+j-1));
        dp[i][j]|=dp[i][j-1]&&(s2.charAt(j-1)==s3.charAt(i+j-1));
      }
    }


    return dp[m][n];
  }


  public int findRotateSteps(String ring, String key) {
    int m = ring.length();
    int n = key.length();
    //每一轮的当前位置字母转到12：00位置所需要的最小步数
    int[][] dp= new int[n][m];
    Arrays.fill(dp,Integer.MAX_VALUE);
    Map<Character,List<Integer>> map = new HashMap<>();

    //将每个字母在字符串中出现的位置存起来
    for( int i=0;i<m;i++){
      map.putIfAbsent(ring.charAt(i),new ArrayList<>()).add(i);
    }

    //初始化，第一个字母出现的每个位置转到12：00的最小步数
    for(int pos:map.get(key.charAt(0))){
      dp[0][pos] = Math.min(dp[0][pos],Math.min(pos,m-pos));
    }

    for(int i=1;i<n;i++){
      for(int curPos:map.get(key.charAt(i))){
        for(int prePos:map.get(key.charAt(i-1))){
          //当前字母和上一个字母的相对位置，转到上一个字母的位置也就是12：00的位置所需要的最小步数
          dp[i][curPos] = Math.min(dp[i][curPos],dp[i-1][prePos]+Math.min(Math.abs(prePos-curPos),m-Math.abs(prePos-curPos)));
        }
      }
    }

    int res = Integer.MAX_VALUE;
    //最后一个字母出现的所有位置转到12：00位置所需要的最小步数，从中挑选最终结果
    for(int pos:map.get(key.charAt(n-1))){
      res = Math.min(res,dp[n-1][pos]);
    }

    //每个字母转到12：00都需要按下确认键，统一加上
    return res+n;
  }


  int[] visited = new int[1<<21];

  public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    // Write your code here

    return dfs(0,0,maxChoosableInteger,desiredTotal);
  }

  private boolean dfs(int state,int sum,int maxChoosableInteger, int desiredTotal){
    if(visited[state]==2){
      return true;
    }

    if(visited[state]==1){
      return false;
    }

    for(int i=1;i<=maxChoosableInteger;i++){
      if(((state>>i)&1)==1){
        continue;
      }
      if(sum+i>=desiredTotal){
        visited[state] = 2;
        return true;
      }

      if(!dfs(state+(1<<i), sum+i, maxChoosableInteger, desiredTotal)){
        visited[state] = 2;
        return true;
      }


    }

    visited[state] = 1;
    return false;
  }

  public boolean canIWinI(int choose, int total) {


        //# 这里用一个Boolean的数组来做的memory，比map要快很多，但空间也用的多了


    int memo[] = new int[1<<(choose+1)];


    return dp(0, 0, choose, total, memo);


  }


  private boolean dp(int cur, int used, int choose, int total, int[] memo) {


    if (memo[used]==2){
      return true;
    }

    if(memo[used]==1){
      return false;
    }


    for (int i=choose; i>0; i--) {
      if ((used&(1<<i))==0) {
        if (cur+i>=total) {
          memo[used] = 2;
          return true;
        }


        if (!dp(cur+i, used|(1<<i), choose, total, memo)) {
          memo[used] = 2;
          return true;
        }


      }


    }

    memo[used] = 1;
    return false;
  }

  public int maxSumAfterPartitioning(int[] arr, int k) {
    int n = arr.length;
    int[] dp = new int[n+1];
    dp[n-1] = arr[n-1];
    for(int i=n-2;i>=0;i--){
      int max = -1;
      for(int j=i;j<=i+k&&j<n;j++){
        max = Math.max(max,arr[j]);
        dp[i]=Math.max(dp[i],max*(j-i+1)+dp[j+1]);
      }
    }

    return dp[0];
  }

  public int wiggleMaxLength(int[] nums) {
    int n = nums.length;

    int[] up = new int[n];
    int[] down = new int[n];

    up[0]=1;
    down[0]=1;

    for(int i=1;i<n;i++){
      if(nums[i]>nums[i-1]){
        up[i]=down[i-1]+1;
        down[i]=down[i-1];
      }else if(nums[i]<nums[i-1]){
        up[i] = up[i-1];
        down[i-1] = up[i-1]+1;
      }else {
        down[i] = down[i-1];
        up[i] = up[i-1];
      }
    }
    return Math.max(down[n-1],up[n-1]);
  }

  public int combinationSum4(int[] nums, int target) {
    int[] dp = new int[target+1];
    Arrays.fill(dp,-1);
    dp[0] = 1;

    return dfsCombinationSum4(nums,target,dp);
  }

  private int dfsCombinationSum4(int[] nums, int target, int[] dp) {
    //已经算过的结果直接返回
    if(dp[target]!=-1){
      return dp[target];
    }

    int res = 0;
    for(int i=0;i<nums.length;i++){
      if(target>=nums[i]){
        res+=dfsCombinationSum4(nums,target-nums[i],dp);
      }
    }

    dp[target] = res;
    return res;
  }

  public int splitArray(int[] nums, int m) {
    int n = nums.length;
    int[] sum = new  int[n+1];

    for(int i=0;i<n;i++){
      sum[i+1]=sum[i]+nums[i];
    }

    int[] dp = new int[n];
    //从i往后切成一个子数组的和
    for(int i=0;i<n;i++){
      dp[i]=sum[n]-sum[i];
    }

    //现在计算划分成k+1个子数组的最优结果,
    // dp存的是划分成k个子数组的最优结果
    for(int k=1;k<m;k++){
      //从i开始，从前往后更新，不影响上一轮i之后的划分结果
      for(int i=0;i<n-k;i++){
        dp[i] = Integer.MAX_VALUE;
        for(int j=i+1;j<=n-k;j++){
          int t = Math.max(dp[j],sum[j]-sum[i]);
          if(t<=dp[i]){
            dp[i]=t;
          }else {
            break;
          }
        }
      }
    }

    return dp[0];
  }

  public int numberOfArithmeticSlices(int[] A) {
      int n = A.length;
      if(n<3){
        return 0;
      }
      int[] dp = new int[n];
      dp[0] = 0;
      dp[1] = 0;
      int sum = 0;
      for(int i=2;i<n;i++){
        if(A[i]-A[i-1]==A[i-1]-A[i-2]){
          dp[i]=dp[i-1]+1;
        }else {
          dp[i] = 0;
        }
        sum+=dp[i];
      }
      return sum;
  }

  public boolean canPartition(int[] nums) {
    int n = nums.length;
    int sum =0;
    for(int num:nums){
      sum+=num;
    }

    if(sum%2!=0||n<=1){
      return false;
    }

    int target = sum/2;

    //是否存在和为i的方案
   boolean[] dp = new boolean[target+1];
   dp[0] = true;
   //每考虑一个新元素更新组合结果
   for(int num:nums){
     //从大往小更新，排除重复
     for(int i=target;i>=num;i++){
       dp[i]=dp[i]||dp[i-num];
     }
   }

   return dp[target];
  }


  public int numDecodings(String s) {
    if (s.startsWith("0")) {
      return 0;
    }

    int n = s.length();
    int[] dp = new int[n+1];
    dp[0]=1;
    dp[1]=1;
    for(int i=2;i<=s.length();i++){
      int pre = s.charAt(i-2)-'0';
      int cur = s.charAt(i-1)-'0';
      if(pre==0){
        if(cur==0){
          return 0;
        }else{
          dp[i] = dp[i-1];
        }
      }else{
        if(cur==0){
          if(pre>2){
            return 0;
          }else{
            dp[i]=dp[i-2];
          }
        }else{
          if(pre==1||(pre==2&&cur<=6)){
            dp[i]=dp[i-1];
            if(i>1){
              dp[i]+=dp[i-2];
            }
          }else{
            dp[i]=dp[i-1];
          }

        }
      }
    }

    return dp[n - 1];

  }

  public int numTrees(int n) {
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = 1;
    for(int level = 2;level<=n;level++){
      for(int root=1;root<=level;root++){
        dp[level]+=dp[level-root]*dp[root-1];
      }
    }

    return dp[n];
  }

  public int[] countBits(int num) {
    int[] dp = new int[num+1];
    dp[0]=0;
    for(int i=1;i<=num;i++){
      if((i&1)==0){
        dp[i]=dp[i>>1];
      }else {
        dp[i]=dp[i>>1]+1;
      }
    }

    return dp;
  }

  public int integerBreak(int n) {

    int[] dp = new int[n+1];
    dp[0]=1;
    for(int i=1;i<=n;i++){
      for(int j=i;j<=n;j++){
        dp[j]=Math.max(dp[i],dp[i-j]*j);
      }
    }

    return dp[n];
  }

  public int countNumbersWithUniqueDigits(int n) {
      int[] dp = new int[n+1];
      int sum = 1;
      dp[0] = 0;
      int k=9;
      for(int i=1;i<=n;i++){
        if(i==1){
          dp[1] = 9;
        }else {
          dp[i]=dp[i-1]*k;
          k--;
        }
        sum+=dp[i];
      }

      return sum;
  }

  public int findLongestChain(int[][] pairs) {
    //按区间结束位置从小到大排
      Arrays.sort(pairs,(a,b)->(a[1]-b[1]));
      int count =0;
      int i=0;
      int n = pairs.length;
      while (i<n){
        count++;
        int curEnd = pairs[i][1];
        //跳过重叠区间
        while (i<n&&pairs[i][0]<=curEnd){
          i++;
        }
      }

      return count;
  }


  public boolean divisorGame(int n) {

    boolean[] dp = new boolean[n];
    dp[0]=false;
    dp[1]=false;
    //bottom up 从小往大，竭尽全力使得dp[i]为TRUE
    for(int i=2;i<=n;i++){
      for(int j=1;j<i;j++){
          if(i%j==0){
            if(dp[i-j]==false){
              dp[i] = true;
            }
          }
      }
    }

    return dp[n];
  }
  public int mincostTickets(int[] days, int[] costs) {
      //日期最终时间
      int n = days[days.length-1];
      int[] dp = new int[n+1];
      boolean[] isTravelDay = new boolean[n+1];
      //标记旅行的天数
      for(int day:days){
        isTravelDay[day] = true;
      }

      for(int i=0;i<=n;i++){
        int day = days[i];
        //不出去旅行，和前值保持一致
        if(!isTravelDay[day]){
          dp[day]= dp[day-1];
          continue;
        }

        //买日票
        dp[i]=dp[i-1]+costs[0];
        //7天前买周票
        dp[i]=Math.min(dp[i],dp[Math.max(i-7,0)]+costs[1]);
        //30天前买月票
        dp[i]=Math.min(dp[i],dp[Math.max(i-30,0)]+costs[2]);
      }

      return dp[n];
  }

  public int deleteAndEarn(int[] nums) {
    int n = nums.length;
    int[] dp = new int[10001];
    int[] counts = new int[10001];
    for(int num:nums){
      counts[num]++;
    }

    dp[1]=counts[1];
    for(int i=2;i<=10000;i++){
      dp[i]=Math.max(dp[i-1],dp[i-2]+i*counts[i]);
    }

    return dp[10000];
  }

  private static final int mod = (int) (1e9+7);

  public int knightDialer(int n) {
      if(n==1){
        return 10;
      }

      //跳转位置关系
      int[][] jumpMap = new int[][]{{4,6},{6,8},{7,9},{4,8},{3,9,0},{},{1,7,0},{2,6},{1,3},{2,4}};

      //从每个点出发到长度为i的点的方案数量
      int[] dp = new int[10];
      //初始化长度为1.也就是不动
      Arrays.fill(dp,1);
      //top down,
      for(int i=n;i>1;i--){
        int[] temp = new int[10];
        for(int j=0;j<jumpMap.length;j++){
          for(int k=0;k<jumpMap[j].length;k++){
            int position = jumpMap[j][k];
            temp[position]=(temp[position]+dp[j]%mod)%mod;
          }
        }
        dp = temp;
      }

      int res =0;
      for(int num:dp){
        res =(res+num%mod)%mod;
      }

      return res;
  }

  public static List<List<String>> partition(String s) {
    int len = s.length();
    //前i个字符组成的回文结果
    List<List<String>>[] dp = new ArrayList[len+1];
    dp[0] = new ArrayList<>();
    dp[0].add(new ArrayList<>());
    boolean[][] isPanlindrome = new boolean[len][len];
    for(int right =0;right<s.length();right++){
      for(int left =0;left<=right;left++){
          if((s.charAt(left)==s.charAt(right)&&(right-left<=2))||isPanlindrome[left+1][right-1]){
            isPanlindrome[left][right]=true;
            String newPalindrome = s.substring(left,right+1);
            //左端[0,left-1],组成的回文字符串结果，
            // 追加上[left,right]的回文串即为新的组合
            for(List<String> preRes:dp[left]){
              List<String> curRes= new ArrayList<>(preRes);
              curRes.add(newPalindrome);
              dp[right+1].add(curRes);
            }
          }

      }
    }

    return dp[len];
  }

  public int minCut(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];

    for(int i=0;i<n;i++){
      for(int j=i;j<n;j++){
        dp[i][j] = j-i;
      }
    }

    for(int i=0;i<n;i++){
      dp[i][i]=0;
      if(i+1<n){
        if(s.charAt(i)==s.charAt(i+1)){
          dp[i][i+1]=0;
        }else{
          dp[i][i+1]=1;
        }
      }
    }

    for(int i=n-3;i>=0;i--){
      for(int j=n-1;j>i+1;j--){
        for(int k=i+1;k<=j;k++){

          if(s.charAt(i)==s.charAt(k)){
            dp[i][k]=Math.min(dp[i+1][k-1],dp[i][k]);
          }
          dp[i][k] = Math.min(dp[i][k],dp[i][k-1]+1);
          dp[i][k] = Math.min(dp[i][k],dp[i+1][k]+1);

          if(k<j){
            dp[i][j]=Math.min(dp[i][j],dp[i][k]+dp[k+1][j]);
          }



        }
      }
    }

    return dp[0][n-1];
  }


  public boolean wordBreakI(String s, List<String> wordDict) {
    int n = s.length();
    boolean[] dp = new boolean[n+1];
    dp[0]=true;
    for(int i=1;i<=n;i++){
      for(int j=i-1;j>=1;j--){
        if(dp[j]){
          String cut = s.substring(j,i);
          if(wordDict.contains(cut)){
            dp[i]=true;
            break;
          }
        }
      }
    }

    return dp[n];
  }


  public List<String> wordBreak(String s, List<String> wordDict) {
      int n = s.length();
      List<String>[] dp = new ArrayList[n+1];
      dp[0]= new ArrayList<>();
      dp[0].add("");
      for(int i=1;i<=n;i++){

        for(int j=0;j<i;j++){
          String cur = s.substring(j,i);
          if(wordDict.contains(cur)&&dp[j]!=null){

            if(dp[i]==null){
              dp[i] =new ArrayList<>();
            }

            List<String> curList =dp[i];

            List<String> preList = dp[j];

            for(String pre:preList){
              if(!pre.equals("")){
                pre+=" ";
              }
              curList.add(pre+cur);
            }

          }
        }
      }

      return dp[n];

  }

  public boolean isSubsequence(String s, String t) {
  int m = t.length();
  int n = s.length();
  boolean[][] dp= new boolean[m+1][n+1];


  for(int i=0;i<=m;i++){
    dp[i][0]=true;
  }

  for(int i=1;i<=m;i++){
    for(int j=1;j<=n;j++){
      dp[i][j]=dp[i][j]||dp[i-1][j];
      if(t.charAt(i-1)==s.charAt(j-1)){
        dp[i][j]=dp[i][j]||dp[i-1][j-1];
      }
    }
  }

    return dp[m][n];
  }

  public List<String> findAllConcatenatedWordsInADict(String[] words) {
    List<String> res = new ArrayList<>();
    Set<String> preWords = new HashSet<>();
    Arrays.sort(words,(a,b)->(a.length()-b.length()));
    for(int i=0;i<words.length;i++){
      if(dfsFindWords(words[i],preWords)){
        res.add(words[i]);
      }
      preWords.add(words[i]);
    }

    return res;
  }

  private boolean dfsFindWords(String word,Set<String> dict){
    if(dict.isEmpty()){
      return false;
    }

    boolean[] dp = new boolean[word.length()+1];
    dp[0] =true;
    for(int i=1;i<=word.length();i++){
      for(int j=0;j<i;j++){
        if(!dp[j]){
          continue;
        }
        if(dict.contains(word.substring(j,i))){
          dp[i]=true;
          break;
        }
      }
    }

    return dp[word.length()];

  }


  public int longestValidParentheses(String s) {
    int n = s.length();
    int[] dp = new int[n];
    int res =0;
    for(int i=1;i<s.length();i++){
      if(s.charAt(i)==')'){
        if(s.charAt(i-1)=='('){
          dp[i]=(i-2)>=0? dp[i-2]+2:2;
        }else {
          //左边括号在dp[i-1]的有效左括号之前一位，这一位是左括号，生效
          //加上这一位之前的有效括号数量，注意数组越界的问题
          if(i-dp[i-1]-1>=0&&s.charAt(i-dp[i-1]-1)=='('){
            dp[i]=dp[i-1]+2+((i-dp[i-1]-2>=0?dp[i-dp[i-1]-2]:0));
          }
        }
        if(dp[i]>res){
          res = dp[i];
        }
      }
    }

    return res;
  }


  public int longestStrChain(String[] words) {
    Arrays.sort(words,(a,b)->(a.length()-b.length()));
    Map<String, Integer> map = new HashMap<>();
    int n = words.length;
    for(String word:words){
      map.putIfAbsent(word,1);
    }

    int res =1;
    for(int i=1;i<n;i++){
      String word = words[i];
      for(int j=0;j<word.length();j++){
        String pre = word.substring(0,j)+word.substring(j+1);
        if(map.containsKey(pre)){
          Integer val =map.get(word);
          if(val<map.get(pre)+1){
            map.put(word,map.get(pre)+1);
          }
        }
      }
      res =Math.max(res,map.get(word));
    }
    return res;
  }

  int longestDecomp=0;
  public int longestDecomposition(String text) {
    int n = text.length();
    backtrack(text,0,n-1,0);
    return longestDecomp;
  }

  private void backtrack(String text,int start,int end,int chunks){
    if(start+1>end){
      return;
    }

    if(start+1==end){
      longestDecomp = Math.max(longestDecomp,chunks);
      return;
    }

    int len = end-start;
    for(int i=1;i<=len;i++){
      if(text.substring(start,start+i).equals(text.substring(end+1-i,end+1))){
        backtrack(text,start+i,end-i,chunks+2);
        //因为长度是从小划分的，所以遇到了肯定是最小的
        break;
      }
    }

    longestDecomp = Math.max(longestDecomp,chunks+1);
  }

  static public String longestDiverseString(int a, int b, int c) {
    PriorityQueue<Pair> queue = new PriorityQueue<>((one,two)->(two.count-one.count));
    if(a>0){
      queue.add(new Pair('a',a));
    }
    if(b>0){
      queue.add(new Pair('b',b));
    }
    if(c>0){
      queue.add(new Pair('c',c));
    }

    StringBuilder sb = new StringBuilder();
    while (queue.size()>1){
      //出现次数最多的字母最先弹出
      Pair pairOne = queue.poll();
      if(pairOne.count>=2){
        sb.append(pairOne.ch);
        sb.append(pairOne.ch);
        pairOne.count-=2;
      }else {
        sb.append(pairOne.ch);
        pairOne.count-=1;
      }

      //下一个字母要不同，接着弹出，错开
      Pair pairTwo = queue.poll();
      //若两者次数差距太大，则第二个出现次数最多的字母要省着用，
      // 以便把出现最多次数的字母尽可能塞进去
      //这里巧妙的比较次数，可以判断能否尽可能把它们都用完
      if(pairTwo.count>=2&&pairOne.count<pairTwo.count){
        sb.append(pairTwo.ch);
        sb.append(pairTwo.ch);
        pairTwo.count-=2;
      }else {
        sb.append(pairTwo.ch);
        pairTwo.count-=1;
      }
      if(pairOne.count>0){
        queue.add(pairOne);
      }

      if(pairTwo.count>0){
        queue.add(pairTwo);
      }
    }

    //最后剩下的字母，看能不能追加到最后
    if(!queue.isEmpty()){
      if(sb.charAt(sb.length()-1)!=queue.peek().ch){
        if(queue.peek().count>=2){
          sb.append(queue.peek().ch);
          sb.append(queue.peek().ch);
        }else {
          sb.append(queue.peek().ch);
        }
      }
    }

    return sb.toString();
  }

  static class Pair{
    char ch;
    int count;
    public Pair(char ch,int count){
      this.ch = ch;
      this.count = count;
    }
  }

    public static void main(String[] args) {
    DynamicProgramming dp = new DynamicProgramming();
    String[] words =new String[]{"xbc","pcxbcf","xb","cxbc","pcxbc"};
//    dp.longestDecomposition("merchant");
      System.out.println(dp.longestDecomposition("ghiabcdefhelloadamhelloabcdefghi"));
  }

}
