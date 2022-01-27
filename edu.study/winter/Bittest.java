package winter;

import unlock.Trie;

import java.util.*;

public class Bittest {
    class Trie {
        Trie[] child;

        public Trie() {
            child = new Trie[2];
        }
    }




    public int minOperations(int[] target, int[] A) {
        Map<Integer, Integer> h = new HashMap<>();
        for (int i = 0; i < target.length; i++) {
            h.put(target[i], i);
        }
        TreeSet<Integer> stack = new TreeSet<>();
        for (int a : A) {
            if (!h.containsKey(a)) {
                continue;
            }
            //比最大值还大，直接加入
            if (stack.isEmpty() || stack.last() < h.get(a)) {
                stack.add(h.get(a));
                continue;
            }
            //找到比当前值大的第一个元素，替换它，降低高度，方便增长
            int key = stack.ceiling(h.get(a));
            stack.remove(key);
            stack.add(h.get(a));
        }
        return target.length - stack.size();
    }


    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j <= i + k && j < nums.length; j++) {
                long a = nums[i];
                long b = nums[j];
                if (Math.abs(a - b) <= t) {
                    return true;
                }

            }
        }

        return false;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            if (!set.add(nums[i])) {
                return true;
            }

        }

        return false;
    }

    public int longestSubstring(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int logest = 0;
        for (int i = 0; i < s.length(); i++) {
            //遇到频率不符合要求的字符，排除它，左右递归查找
            if (map.get(s.charAt(i)) < k) {
                int subLongest = longestSubstring(s.substring(left, i), k);
                logest = Math.max(subLongest, logest);
                //下一个可能符合要求的子区间起点位置
                left = i + 1;
            }
        }

        if (left == 0) {
            return s.length();
        }

        int subLongest = longestSubstring(s.substring(left), k);
        logest = Math.max(subLongest, logest);
        return logest;
    }


    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) {
            return 0;
        }
        if (s.length() <= k) {
            return s.length();
        }

        int res = 0;
        int left = 0;
        int right = 0;
        int count = 0;
        int[] charSet = new int[256];//统计每个字符出现的次数

        while (right < s.length()) {
            if (charSet[s.charAt(right)] == 0) {//第一次出现，计数
                count++;
            }
            charSet[s.charAt(right)]++;
            right++;//右边界向右移动
            while (count > k) {
                charSet[s.charAt(left)]--;
                if (charSet[s.charAt(left)] == 0) {//窗口内的字符种类减少
                    count--;
                }
                left++;//左边界右移
            }

            res = Math.max(res, right - left);
        }

        return res;

    }


    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    public int characterReplacementI(String s, int k) {
        int[] count = new int[26];
        int start = 0;
        int maxCharCount = 0;
        int n = s.length();
        int res = 0;
        for (int end = 0; end < n; end++) {
            //统计每个字母出现的频率
            count[s.charAt(end) - 'A']++;
            //保存出现最多的次数
            if (maxCharCount < count[s.charAt(end) - 'A']) {
                maxCharCount = count[s.charAt(end) - 'A'];
            }

            //相当于有一个窗口，把其他字母统统转换为出现次数最多的字母
            //转换次数 = 窗口长度-出现的最多次数，转换次数不能超过k
            if (end - start - maxCharCount + 1 > k) {
                //窗口左侧收缩，向右滑动
                count[s.charAt(start) - 'A']--;
                start++;
            }

            res = Math.max(res, end - start + 1);
        }

        return res;
    }


    public List<Integer> findAnagrams(String s, String p) {
        int[] count = new int[26];
        int n = p.length();
        int[] cur = new int[26];
        for (char c : p.toCharArray()) {
            count[c - 'a']++;
        }
        List<Integer> res = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            cur[s.charAt(i) - 'a']++;
            if (i >= n - 1) {
                int j = 0;
                for (; j < 26; j++) {
                    if (count[j] < cur[j]) {
                        break;
                    }
                }
                while (j < 26 && cur[j] > count[j]) {
                    cur[s.charAt(start) - 'a']--;
                    start++;
                }
                if (i - start + 1 == n) {
                    res.add(start);
                }
            }
        }

        return res;
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] res = new double[n - k + 1];
        Queue<Integer> right = new PriorityQueue<>(Collections.reverseOrder());
        Queue<Integer> left = new PriorityQueue<>();
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (left.size() > right.size()) {
                left.add(nums[i]);
                right.add(left.poll());
            } else {
                right.add(nums[i]);
                left.add(right.poll());
            }

            if (i >= k - 1) {
                if (k % 2 == 0) {
                    double val = left.peek();
                    val += right.peek();
                    res[index++] = val / 2.0;
                } else {
                    res[index++] = left.peek();
                }
                if (left.contains(nums[i - k + 1])) {
                    left.remove(nums[i - k + 1]);
                } else {
                    right.remove(nums[i - k + 1]);
                }
            }

        }
        return res;

    }

    public int findMaxConsecutiveOnes(int[] nums) {
        // write your code here
        int start = 0;
        int zero = 0;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zero++;
            }
            while (i < nums.length && zero > 1) {
                if (nums[start] == 0) {
                    zero--;
                }
                start++;
            }
            res = Math.max(res, i - start + 1);
        }

        return res;
    }


    public boolean checkInclusion(String s1, String s2) {
        int[] count = new int[26];
        int n = s1.length();
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        int start = 0;
        int[] cur = new int[26];
        for (int i = 0; i < s2.length(); i++) {
            cur[s2.charAt(i) - 'a']++;
            int j = 0;
            for (; j < 26; j++) {
                if (count[j] < cur[j]) {
                    break;
                }
            }

            while (j < 26 && count[j] < cur[j]) {
                cur[s2.charAt(start) - 'a']--;
                start++;
            }

            if (i - start + 1 == n) {
                return true;
            }
        }

        return false;
    }



    public String minWindowII(String s, String t) {
        if(t.length()>s.length()){
            return "";
        }
        String res = "";
        int n = t.length();
        int[] count = new int[256];
        for (char c : t.toCharArray()) {
            count[c]++;
        }

        int[] window = new int[256];
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            window[s.charAt(i)]++;
            if (i >= n - 1) {
                int j = 0;
                for (; j < 256; j++) {
                    if (count[j] != 0 && count[j] < window[j]) {
                        break;
                    }
                }

                while (j<256&&(count[j] < window[j])&&(count[s.charAt(start)]<=window[s.charAt(start)])) {
                    window[s.charAt(start)]--;
                    start++;
                }

                while (start<s.length()&&count[s.charAt(start)]==0) {
                    window[s.charAt(start)]--;
                    start++;
                }


                if ((res.equals("")||res.length() > i - start + 1)&&i-start+1>=n) {
                    boolean valid = true;
                    for (j=0; j < 256; j++) {
                        if (count[j] != 0 && count[j] != window[j]) {
                            valid = false;
                            break;
                        }
                    }
                    if(valid){
                        res = s.substring(start, i + 1);
                    }
                }

            }
        }

        return res;

    }


    public int findLHS(int[] nums) {
        Map<Long,Integer> map = new HashMap<>();
        for(long num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }

        int res = 0;
        for(long num:nums){
            res = Math.max(map.getOrDefault(num-1,0)+map.get(num),res);
            res = Math.max(map.getOrDefault(num+1,0)+map.get(num),res);
        }

        return res;
    }


    public int[] smallestRange(List<List<Integer>> nums) {
        List<int[]> points = new ArrayList<>();
        //每个元素所属的组处理成对的方式
        for (int i = 0; i < nums.size(); i++) {
            for (int j : nums.get(i)) points.add(new int[]{j, i});
        }
        //按顺序排列
        Collections.sort(points, (p1, p2) -> p1[0] - p2[0]);
        //每个组的元素数量
        int[] counts = new int[nums.size()];
        int countUnique = 0, minStart = -1, minLength = Integer.MAX_VALUE;
        for (int i = 0, j = 0; j < points.size(); j++) {
            //累计属于不同组的元素
            if (counts[points.get(j)[1]]++ == 0) countUnique++;
            //覆盖了所有组的元素
            while (countUnique == counts.length) {
                //跨度小于结果，更新结果
                if (points.get(j)[0] - points.get(i)[0] + 1 < minLength) {
                    minStart = points.get(i)[0];
                    minLength = points.get(j)[0] - points.get(i)[0] + 1;
                }
                int prev = points.get(i)[0];
                //剔除覆盖重复组的元素
                while (i <= j && prev == points.get(i)[0]) {
                    if (--counts[points.get(i++)[1]] == 0) countUnique--;
                }
            }
        }
        return new int[]{minStart, minStart + minLength - 1};
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) return 0;
        int cnt = 0;
        int product = 1;
        for (int i = 0, j = 0; j < nums.length; j++) {
            product = nums[j];
            while (i <= j && product >= k) {
                product /= nums[i++];
            }
            cnt += j - i + 1;
        }
        return cnt;
    }

    public String minWindowI(String s, String t) {
        if(s==null||t==null){
            return "";
        }

        int startIndex =-1;
        int minLength = Integer.MAX_VALUE;
        int lastMatchCount = t.length();
        int[] count = new int[128];
        for(char c:t.toCharArray()){
            count[c]++;
        }

        int right =0;
        int left =0;
        while (right<s.length()){
            while (right<s.length()&&lastMatchCount>0){// 滑动右端,直达满足最小覆盖子串
                if(count[s.charAt(right)]-->0){//count有效字母全部满足
                    lastMatchCount--;
                }
                right++;
            }

            while (lastMatchCount==0){// 左端左移至打破子串全覆盖的前一个字符
                if(count[s.charAt(left)]++==0){
                    lastMatchCount++;
                }
                left++;
            }

            if(right-left+1<minLength){// 更新最小值
                startIndex = left-1;
                minLength = right-left+1;
            }
        }

        return startIndex==-1?"":s.substring(startIndex,startIndex+minLength);

    }
    public String minWindow(String S, String T) {
            int m = T.length();
            int n = S.length();
            //dp[i][j]表示匹配T串的前i个字符，S中前j个字符中匹配的起点，
           // 当前两字符相同时，则有dp[i][j] = dp[i-1][j-1]，
           // 当两字符不相同时，相当于第j个字符被跳过，则有dp[i][j] = dp[i][j-1]。
            int[][] dp = new int[m+1][n+1];

            //初始化两字符串匹配的起点
            for(int j=0;j<=n;j++){
                dp[0][j] = j+1;
            }

            for(int i=1;i<=m;i++){//T字符串
                for(int j=1;j<=n;j++){//S字符串
                    if(T.charAt(i)==S.charAt(j)){//两字符匹配
                        dp[i][j] = dp[i-1][j-1];//dp[i][j]的起点则等于dp[i-1][j-1]的起点
                    }else {
                        dp[i][j] = dp[i][j-1];//dp[i][j]的起点等于dp[i][j-1]的起点
                    }
                }
            }

        int start = 0, len = n + 1;
            for(int j=1;j<=n;j++){
                if(dp[m][j]!=0){//dp[m][j]!=0表示当前T串的m个字符已经匹配成为S串的前j个长度字符串的子序列
                    if(j-dp[m][j]+1<len){//选择字符串长度最小的
                        start =  dp[m][j]-1;
                        len = j-dp[m][j]+1;
                    }
                }
            }
        return len==n+1?"":S.substring(start,start+len);

    }


    public String minWindowIII(String S, String T) {
        int m = S.length(), n = T.length(), start = -1, minLen = Integer.MAX_VALUE, i = 0, j = 0;
        while (i < m) {
            if (S.charAt(i) == T.charAt(j)) {
                //匹配到T的末尾了
                if (++j == n) {
                    //S子序列的结束位置
                    int end = i + 1;
                    //往回找最小的匹配长度
                    while (--j >= 0) {
                        //
                        while (S.charAt(i--) != T.charAt(j));
                    }
                    ++i; ++j;
                    //找到的最小匹配长度比目前的小，更新它
                    if (end - i < minLen) {
                        minLen = end - i;
                        start = i;
                    }
                }
            }
            ++i;
        }
        return (start != -1) ? S.substring(start, minLen) : "";
    }


    public int subarraysWithKDistinct(int[] nums, int k) {
            return atMostKDistinct(nums,k)-atMostKDistinct(nums,k-1);
    }

    private int atMostKDistinct(int[] nums, int k){
        if(k==0){
            return 0;
        }
        int count =0;
        int left =0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            while (map.size()>k&&left<nums.length){
                if(map.get(nums[left])>1){
                    map.put(nums[left],map.get(nums[left])-1);
                }else {
                    map.remove(nums[left]);
                }
                left++;
            }

            count+=i-left+1;
        }

        return count;
    }


    public int longestOnes(int[] nums, int k) {
        int left =0;
        int zeros =0;
        int res = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0){
                zeros++;
            }

            while(zeros>k){
                if(nums[left]==0){
                    zeros--;
                }
                left++;
            }

            res =Math.max(res,i-left+1);
        }

        return res;
    }
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int[] count = new int[2];
        int max = 0;
        int res =0;
        int left =0;
        for(int i=0;i<answerKey.length();i++){
            char c = answerKey.charAt(i);
            if(c=='T'){
                count[0]++;
            }else{
                count[1]++;
            }

            max = Math.max(count[0],count[1]);

            while(k<i-left+1-max){
                c = answerKey.charAt(left);
                if(c=='T'){
                    count[0]--;
                }else{
                    count[1]--;
                }
                left++;
                max = Math.max(count[0],count[1]);
            }

            res =Math.max(res,i-left+1);
        }
        return res;
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        if(goal==0){
            return atMost(nums,0);
        }
        return atMost(nums,goal)-atMost(nums,goal-1);
    }

    public int atMost(int[] nums, int goal){
        int count =0;
        int left =0;
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            while(sum>goal){
                sum-=nums[left];
                left++;
            }
            count+=i-left+1;
        }

        return count;
    }

    public int maxTurbulenceSize(int[] arr) {
        int[] nums= arr;

        int inc =1;//以上一个数作为下降趋势结尾的长度
        int dec =1;//以上一个数作为上升趋势结尾的长度
        boolean less = true;
        int res = 1;
        for(int i=1;i<arr.length;i++){
            if(arr[i]>arr[i-1]){//上升下降趋势交替
                dec=inc+1;//以上一个数为上升趋势结尾的长度，现在改为下降
                inc=1;//现在不能上升了，重新作为上升的起点
            }else if(arr[i]<arr[i-1]){
                inc=dec+1;
                dec=1;
            }else {
                inc = 1;
                dec = 1;
            }

            res = Math.max(res,Math.max(inc,dec));
        }


        return res;

    }


    public int[] sumEvenAfterQueries(int[] nums, int[][] queries) {
        int[] res = new int[queries.length];
        int sum = 0;
        for(int num:nums){
            if(num%2==0){
                sum+=num;
            }
        }

        for(int i=0;i<queries.length;i++){
            int index = queries[i][1];
            int val = queries[i][0];
            if(nums[index]%2==0){
                if(val%2==0){
                    sum+=val;
                }else{
                    sum-=nums[index];
                }
                res[i] = sum;
                nums[index]+=val;
            }else{
                if(val%2!=0){
                    sum+=val+nums[index];
                }
                res[i] = sum;
                nums[index]+=val;
            }

        }
        return res;
    }

    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {

        int count = 0;
        for(int i=0;i<grumpy.length;i++){
            if(grumpy[i]==0){
                count+=customers[i];
            }
        }

        int becomeSatisfied = 0;
        int left =0;
        int max = 0;
        for(int i=0;i<grumpy.length;i++){
            if(grumpy[i]==1){
                becomeSatisfied+=customers[i];
            }

            if(i-left+1>minutes){
                if(grumpy[left]==1){
                    becomeSatisfied-=customers[left];
                }
                left++;
            }

            max = Math.max(becomeSatisfied,max);
        }

        return max+count;
    }


    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int sum = 0;
        //全部选左边
        for (int i = 0; i < k; ++i) sum += cardPoints[i];
        int maxVal = sum;

        int end = n;
        int tail = 0;
        for (int i = k - 1; i >= 0; --i) {
            sum -= cardPoints[i];//第i次选右边
            tail += cardPoints[--end];

            maxVal = Math.max(maxVal, sum + tail);
        }

        return maxVal;

    }

    public int longestSubarray(int[] A, int limit) {
        int i = 0, j;
        int res = 0;
        TreeMap<Integer, Integer> m = new TreeMap<>();
        for (j = 0; j < A.length; j++) {
            m.put(A[j], 1 + m.getOrDefault(A[j], 0));
            System.out.println(m.lastEntry().getKey());
            System.out.println(m.firstEntry().getKey());
            if (m.lastEntry().getKey() - m.firstEntry().getKey() > limit) {
                m.put(A[i], m.get(A[i]) - 1);
                if (m.get(A[i]) == 0)
                    m.remove(A[i]);
                i++;
            }
            res =Math.max(res,j-i+1);
        }

        //为什么这个对？
        return j - i;
    }


    public int jump(int[] nums) {
        int n = nums.length;
        //到达index位置的最少步数
        int[] dp = new int[n];
        //dp[i] = Math.min(dp[j])+1, 0<j<i;
        for(int i=1,j=0;i<n;i++){
            //找到最早能够经过一步到达 i 点的 j 点
            while (j+nums[j]<i){
                j++;
            }

            dp[i] = dp[j]+1;
        }

        return dp[n-1];
    }

    public String largestNumber(int[] nums) {
        int n = nums.length;
        String[] strs = new String[n];
        for(int i=0;i<n;i++){
            strs[i] = nums[i]+"";
        }
        Arrays.sort(strs,new Comparator<String>(){
            @Override
            public int compare(String s,String t){
                String st1 = s+t;
                String st2 = t+s;
                return st2.compareTo(st1);
            }
        });

        //一串0的特殊情况
        if(strs[0].charAt(0)=='0'){
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for(String s:strs){
            sb.append(s);
        }

        return sb.toString();
    }

    public boolean increasingTriplet(int[] nums) {
        //长度为i，结尾元素为dp[i]
        int[] dp = new int[3];
        //初始化均为最大值；
        Arrays.fill(dp,Integer.MAX_VALUE);
        for(int num:nums){
            if(num>dp[2]){
                return true;
            }

            if(num<dp[1]){
                dp[1] = num;
            }else if(dp[1]<num&&num<dp[2]){
                dp[2] = num;
            }
        }

        return false;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        //总共剩下的油量
        int sum =0;
        //开始出发的站点
        int start =0;
        //邮箱现在剩下的油
        int curGas =0;
        for(int i=0;i<cost.length;i++){
            sum+=gas[i]-cost[i];
            curGas+=gas[i]-cost[i];

            if(curGas<0){//之前的油不能到达当前站
                curGas = 0;
                start=i+1;//从下一个站点开始
            }
        }

        if(sum<0){//所有加油站的油不能走完路程
            return -1;
        }

        return start;
    }


    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies,1);
        for(int i=1;i<n;i++){
            if(ratings[i]>ratings[i-1]){
                ratings[i] = ratings[i-1]+1;
            }
        }

        for(int i=n-2;i>=0;i--){
            if(ratings[i]>ratings[i+1]){
                ratings[i] = Math.max(ratings[i],ratings[i+1]+1);
            }
        }

        int sum = 0;
        for(int can:candies){
            sum+=can;
        }

        return sum;
    }

    public String removeDuplicateLetters(String s) {
        if(s==null||s.length()<=1){
            return s;
        }
        Map<Character,Integer> lastPosMap = new HashMap<>();

        for(int i=0;i<s.length();i++){
            lastPosMap.put(s.charAt(i),i);
        }
        char[] res = new char[lastPosMap.size()];
        int begin =0;
        //最后一次出现的最小位置
        int end = findMinLastPos(lastPosMap);
        for(int i=0;i<res.length;i++){
            char minChar ='z'+1;
            for(int k=begin;k<=end;k++){
                //最后一次出现的最小位置前面是重复的字母，
                // 如果这些字母比最后一次出现的字母排序靠前那就先选这些字母
                //从这之中找到最小的字母
                if(lastPosMap.containsKey(s.charAt(k))&&s.charAt(k)<minChar){
                    minChar = s.charAt(k);
                    begin = k+1;
                }
            }

            res[i] = minChar;
            if(i==res.length-1)break;
            lastPosMap.remove(minChar);
            if(s.charAt(end)==minChar){
                end = findMinLastPos(lastPosMap);
            }
        }
        return new String(res);
    }

    private int findMinLastPos(Map<Character, Integer> lastPosMap) {
        if(lastPosMap==null||lastPosMap.isEmpty()){
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for(int lastPos:lastPosMap.values()){
            min = Math.min(min,lastPos);
        }
        return min;
    }

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        int[] res = new int[k];

        for(int i=Math.max(0,k-m);i<=k&&i<=n;i++){
                int[] array1 = findMax(nums1,i);
                int[] array2 = findMax(nums2,k-i);
                int[] candidates = merge(array1,array2,k);
                res = greater(candidates,0,res,0)?candidates:res;
        }
        return res;
    }

    private int[] merge(int[] array1, int[] array2,int k) {
        int[] res = new int[k];
        for(int i=0,j=0,r=0;r<k;r++){
            res[r] = greater(array1,i,array2,j)?array1[i++]:array2[j++];
        }
        return res;
    }

    private boolean greater(int[] array1, int i, int[] array2, int j) {
        while (i<array1.length&&j<array2.length&&array1[i]==array2[j]){
            i++;
            j++;
        }

        return j==array2.length||(i<array1.length&&array1[i]>array2[j]);
    }

    public int[] findMax(int[] nums,int k){
        int n = nums.length;
        int[] res = new int[k];
        int j = 0;

        for(int i=0;i<n;i++){
            //可以挑选的名额n-i+j
            while(n-i+j>k&&j>0&&nums[i]>res[j-1]){
                j--;
            }

            res[j++] = nums[i];

        }

        return res;
    }

    Map<Integer,Integer> replaceMap = new HashMap<>();
    public int integerReplacement(int n) {
        replaceMap.put(1,0);
        replaceMap.put(2,1);


       int res = integerReplacement(2147483647,0);
        System.out.println(res);
        return res;
    }

    public int integerReplacement(int n,int num) {
        if(replaceMap.containsKey(n)){
            return num+replaceMap.get(n);
        }
        int res =0;
        if(n%2==0){

            res=  integerReplacement(n/2,num+1);
        }else{
            res = Math.min(integerReplacement((n+1)/2,1+num),integerReplacement((n-1)/2,1+num));
        }
        replaceMap.put(n,res);
        return res;
    }

    public String removeKdigits(String num, int k) {
        int n = num.length();
        if(k==n){
            return "";
        }
        Stack<Character> stack = new Stack<>();
        for(int i=0;i<n;i++){
            while (k>0&&!stack.isEmpty()&&stack.peek()>num.charAt(i)){
                stack.pop();
                k--;
            }

            stack.push(num.charAt(i));
            i++;
        }
        while (k>0){
            stack.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }
        sb.reverse();
        while (sb.length()>0&&sb.charAt(0)>0){
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }


    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;

        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        for(int i=n-1;i>=0;i--){
            stack.push(nums[i]);
        }

        for(int i=n-1;i>=0;i--){
            res[i] = -1;
            while (!stack.isEmpty()&&stack.peek()<nums[i]){
                stack.pop();
            }

            if(!stack.isEmpty()){
                res[i] = stack.peek();
            }

            stack.push(nums[i]);
        }

        return res;
    }

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Integer> capQueue = new PriorityQueue<>((a,b)->(capital[a]-capital[b]));
        PriorityQueue<Integer> profitQueue = new PriorityQueue<>((a,b)->(profits[b]-profits[a]));

        for(int i=0;i<capital.length;i++){
            capQueue.offer(i);
        }


        while (k>0){
            while (!capQueue.isEmpty()&&capital[capQueue.peek()]<=w){
                profitQueue.offer(capQueue.poll());
            }
            if(!profitQueue.isEmpty()){
                w+=profits[profitQueue.poll()];
                k--;
            }else{
                break;
            }
        }

        return w;
    }

 
      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }
  

    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorder(preorder,0,preorder.length-1);
    }

    public TreeNode bstFromPreorder(int[] preorder, int start,int end) {
        if(start>end){
            return null;
        }

        int val = preorder[start];
        TreeNode root = new TreeNode(val);
        if(start==end){
            return root;
        }

        int leftEnd = start;
        while (leftEnd+1<preorder.length&&preorder[leftEnd+1]<val){
            leftEnd++;
        }

        root.left = bstFromPreorder(preorder,start+1,leftEnd);
        root.right = bstFromPreorder(preorder,leftEnd+1,end);

        return root;

    }

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int i=0;i<n;i++){
            int[] connect = isConnected[i];
            for(int j=0;j<n;j++){
                if(i==j){
                    continue;
                }

                if(connect[j]==1){
                    map.putIfAbsent(i,new ArrayList<>());
                    map.get(i).add(j);
                }
            }
        }

        int count =0;
        boolean[] visited = new boolean[n];
        for(int i=0;i<n;i++){
            if(!visited[i]){
                count++;
                visited[i] = true;
                dfsConnect(map,i,visited);
            }
        }

        return count;


    }

    public void dfsConnect(Map<Integer,List<Integer>> map, int source,boolean[] visited){
        for(int next:map.getOrDefault(source,new ArrayList<>())){
            if(visited[next]){
                continue;
            }
            visited[next] = true;
            dfsConnect(map,next,visited);
        }
    }

    public int findMinMoves(int[] machines) {
        int n = machines.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int sum = 0;
        for(int num:machines){
            sum+=num;
        }

        //每个位置上的最终值，为平均值
        int k = sum/n;
        if(sum%n!=0){
            return -1;
        }

        //最左边的元素不能再往左传递， left[0] = 0
        //剩下的只能往右传递
        right[0] = machines[0] - k;
        for(int i=1;i<n;i++){
            //第i个往左传递的值，等价于第i-1个往右传递的相反数
            left[i] = -right[i-1];
            right[i] = machines[i] - k -left[i];
        }

        int res =0;
        for(int i=0;i<n;i++){
            //每个位置必须移动的步数，左右之和
            res = Math.max(res,left[i]+right[i]);
        }

        return res;
    }

    public String findLongestWord(String s, List<String> dictionary) {
        Collections.sort(dictionary, (a,b)->(b.length()-a.length()==0? a.compareTo(b):b.length()-a.length()));

        for(String str:dictionary){
            if(match(s,str)){
                return str;
            }
        }

        return "";

    }

    public boolean match(String s, String t){
        if(s.equals(t)){
            return true;
        }
        int m = s.length();
        int n = t.length();

        if(m<n){
            return false;
        }


        int i=0;
        int j=0;
        while(i<m&&j<n){
            if(s.charAt(i)!=t.charAt(j)){
                i++;
                if(m-i<n-j){
                    return false;
                }
            }else{
                i++;
                j++;
            }

        }

        return j==n;
    }

    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length-2];
    }

    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses,(a,b)->(a[1]-b[1]));
        //当前日期，每天都在上课
        int days = 0;

        //大顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b)->(b-a));

        for(int[] course:courses){
            int duration = course[0];
            int end = course[1];
            //这些天全在上课，上的每一门持续时间都在队列里
            queue.add(duration);
            days+=end;

            //这门课赶不上了
            if(days<course[1]){
                //把这些天耗时最长的那门课踢掉，给后续课多点时间
                days-=queue.poll();
            }
        }

        return queue.size();
    }


    public int minSwapsCouples(int[] row) {
        int res =0;
        int n = row.length;
        int[] root = new int[n];
        for(int i=0;i<n;i++){
            root[i] = i;
        }

        for(int i=0;i<n;i+=2){
            int x = findRoot(row[i]/2,root);
            int y = findRoot(row[i+1]/2,root);
            if(x!=y){
                root[x] = y;
                res++;
            }
        }

        return res;
    }

    private int findRoot(int p, int[] root){
        while (p!=root[p]){
            p= root[p];
        }

        return p;
    }

    public int numRabbits(int[] answers) {
        int[] bucket = new int[1000];
        int res = 0;
        for(int i=0;i<answers.length;i++){
            if(answers[i]==0){
                res++;
            }else{
                if(bucket[answers[i]]==0){
                    res+=answers[i]+1;
                    bucket[answers[i]] = answers[i];
                }else{
                    bucket[answers[i]]--;
                }
            }

        }

        return res;
    }


    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] row = new int[m];
        int[] col = new int[n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                row[i] =Math.max(row[i],grid[i][j]);
                col[j] =Math.max(col[j],grid[i][j]);
            }
        }

        int res = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                res += Math.min(row[i],col[j])-grid[i][j];
            }
        }

        return res;
    }

    public int largestSumAfterKNegations(int[] A, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        for(int x: A) pq.add(x);
        while( K--  > 0) pq.add(-pq.poll());

        int sum  = 0;
        for(int i = 0; i < A.length; i++){
            sum += pq.poll();
        }
        return sum;
    }

    public int longestSubsequence(int[] arr, int difference) {
        int n = arr.length;
        int res =1;

        Map<Integer,Integer> map =  new HashMap<>();

        for(int i=n-1;i>=0;i--){
            map.put(arr[i],map.getOrDefault(arr[i]+difference,0)+1);
            res = Math.max(res,map.get(arr[i]));
        }

        return res;
    }

    public int balancedStringSplit(String s) {
        int left =0;
        int right =0;
        int res =0;
        int n = s.length();
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='L'){
                left++;
            }else {
                right++;
            }

            if(left==right){
                res++;
            }
        }

        return res;
    }


    public List<int[]> pacificAtlantic(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0;i<m;i++){
            pacific[i][0] = true;
            queue.add(new int[]{i,0});
        }

        for(int i=0;i<n;i++){
            pacific[0][i] = true;
            queue.add(new int[]{0,i});
        }

        int[][] dirs = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        while (!queue.isEmpty()){
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            for(int[] dir:dirs){
                int i = x+dir[0];
                int j = y+dir[1];
                if(i<0||j<0||i>=m||j>=n||pacific[i][j]||matrix[i][j]<matrix[x][y]){
                    continue;
                }
                pacific[i][j] = true;
                queue.offer(new int[]{i,j});
            }
        }

        for(int i=0;i<m;i++){
            atlantic[i][n-1] = true;
            queue.add(new int[]{i,n-1});
        }

        for(int i=0;i<n;i++){
            atlantic[m-1][i] = true;
            queue.add(new int[]{m-1,i});
        }

        while (!queue.isEmpty()){
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            for(int[] dir:dirs){
                int i = x+dir[0];
                int j = y+dir[1];
                if(i<0||j<0||i>=m||j>=n||atlantic[i][j]||matrix[i][j]<matrix[x][y]){
                    continue;
                }
                atlantic[i][j] = true;
                queue.offer(new int[]{i,j});
            }
        }

        List<int[]> res = new ArrayList<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(pacific[i][j]&&atlantic[i][j]){
                    res.add(new int[]{i,j});
                }
            }
        }

        return res;
    }



    public int[] maximizeXor(int[] nums, int[][] queries) {
        int n = queries.length;
        int[] res = new int[n];

        Map<int[],Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            map.put(queries[i],i);
        }

        Arrays.sort(nums);
        Arrays.sort(queries,(a,b)->(a[1]-b[1]));
        Trie root = new Trie();
        int index =0;
        for(int[] query:queries){
            //添加新元素进Trie
            while(index<nums.length&&nums[index]<=query[1]){
                Trie cur = root;
                for(int i=31;i>=0;i--){
                    if(cur.child[(nums[index]>>i)&1]==null){
                        cur.child[(nums[index]>>i)&1] = new Trie();
                    }
                    cur = cur.child[(nums[index]>>i)&1];
                }
                index ++;
            }


            //如果小于最小值，直接赋值
            if(index==0){
                res[map.get(query)] = -1;
                continue;
            }

            //查询计算异或的可能最大值
            int ans =0;
            Trie cur = root;
            for(int i=31;i>=0;i--){
                if (cur.child[1-((query[0]>>i)&1)] != null)
                {
                    cur = cur.child[1-((query[0]>>i)&1)];
                    ans = ans*2+1;
                }
                else
                {

                    cur = cur.child[((query[0]>>i)&1)];
                    ans = ans*2+0;
                }
            }

            res[map.get(query)] = ans;
        }

        return res;
    }

    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        Arrays.sort(arr);
        int i= 0;
        int num = 1;
        while(i<arr.length){
            if(arr[i]>=num){
                num++;
            }
            i++;
        }


        return num-1;
    }

    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int res =nums[0]+nums[nums.length-1];
        for(int i=1;i<nums.length/2;i++){
            res = Math.max(res,nums[i]+nums[nums.length-1-i]);
        }

        return res;
    }

    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        for(int i=1;i<10;i++){
            dfsLexicalOrder(n,i,res);
        }

        return res;
    }

    public void dfsLexicalOrder(int n,int cur,List<Integer> res){
        if(cur>n){
            return;
        }
        res.add(cur);
        for(int i=0;i<10;i++){
            int next = cur*10+i;
            if(next>n){
                return;
            }
            dfsLexicalOrder(n,next,res);
        }
    }

    public int findKthNumber(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for(int i=1;i<10;i++){
            dfsFindKthNumber(n,k,i,list);
            if(list.size()>=k){
                break;
            }
        }

        return list.get(k-1);
    }

    private void dfsFindKthNumber(int n, int k, int cur, List<Integer> res) {
        if (cur > n) {
            return;
        }

        res.add(cur);
        if (res.size() >= k) {
            return;
        }
        for (int i = 0; i < 10; i++) {
            int next = cur * 10 + i;
            if (next > n) {
                return;
            }
            dfsFindKthNumber(n, k, next, res);
            if (res.size() >= k) {
                return;
            }
        }
    }


        public static void main(String[] args) {
        Bittest bittest = new Bittest();
//        bittest.nextGreaterElements(new int[]{1,2,3,4,3});
           String s = "abpcplea";
           String[] dictionary = new String[]{"ale","apple","monkey","plea"};
//        bittest.largestSumAfterKNegations(new int[]{-2,5,9,9,8,4},5);
        bittest.findKthNumber(9885387, 8786251);
//        bittest.eatenApples(new int[]{2,1,1,4,5}, new int[]{10,10,6,4,2});

    }


}
