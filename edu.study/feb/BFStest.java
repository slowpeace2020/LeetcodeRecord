package feb;

import java.util.*;

public class BFStest {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] prerequisite:prerequisites){
            int pre = prerequisite[1];
            int next = prerequisite[0];
            inDegree[next]++;
            map.putIfAbsent(pre,new ArrayList<>());
            map.get(pre).add(next);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()){
            int cur = queue.poll();
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(Integer next : list){
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        for(int in:inDegree){
            if(in!=0){
                return false;
            }
        }

        return true;
    }
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] prerequisite:prerequisites){
            int pre = prerequisite[1];
            int next = prerequisite[0];
            inDegree[next]++;
            map.putIfAbsent(pre,new ArrayList<>());
            map.get(pre).add(next);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        List<Integer> schedule = new ArrayList<>();
        while (!queue.isEmpty()){
            int cur = queue.poll();
            schedule.add(cur);
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(Integer next : list){
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        if(schedule.size()!=numCourses){
            return new int[]{};
        }

        int[] res = new int[numCourses];
        int k=0;
        for(int order:schedule){
            res[k++] = order;
        }

        return res;
    }

    public int scheduleCourse(int[][] courses) {
      PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->(b[1]==a[1]?a[0]-b[0]:a[1]-b[1]));
      for(int[] course:courses){
          queue.offer(course);
      }
      int count=0;
      int cur = 0;
      PriorityQueue<Integer> before = new PriorityQueue<>((a,b)->(b-a));
      while (!queue.isEmpty()){
          int[] course = queue.poll();
          int next = cur+course[0];
          if(next<=course[1]){
              count++;
              cur=next;
              before.offer(course[0]);
          }else {
              int max = before.peek();
              if(max>course[0]){
                  before.poll();
                  cur+=course[0]-max;
                  before.offer(course[0]);
              }
          }
      }

      return count;
    }

    public int minimumTime(int n, int[][] relations, int[] time) {
        int[] inDegree = new int[n];
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int[] rel:relations){
            inDegree[rel[1]-1]++;
            map.putIfAbsent(rel[0]-1,new ArrayList<>());
            map.get(rel[0]-1).add(rel[1]-1);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        int[] completionTime = new int[n];
        while(!queue.isEmpty()){
            int cur = queue.poll();
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(int next:list){
                completionTime[next] = Math.max(completionTime[next],completionTime[cur]+time[next]);
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        int res = 0;
        for(int val:completionTime){
            res = Math.max(res,val);
        }


        return res;
    }

    public int lengthOfLongestSubstring(String s) {
        int start =0;
        int res =0;
        int i=0;
        Map<Character,Integer> map = new HashMap<>();
        while(i<s.length()){
            char cur = s.charAt(i);
            if(map.containsKey(cur)){
                res = Math.max(res,i-start);
                start = Math.max(start,map.get(cur)+1);
            }
            map.put(cur,i);
            i++;
        }
        res = Math.max(res,i-start);
        return res;
    }

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        // Write your code here
        Map<Character,Integer> map = new HashMap<>();
        int res =0;
        int start = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
            while(map.size()>2){
                char st = s.charAt(start);
                if(map.get(st)==1){
                    map.remove(st);
                }else{
                    map.put(st,map.get(st)-1);
                }
                start++;
            }

            res =Math.max(res,i-start+1);
        }

        return res;
    }

    public int subarraysWithKDistinct(int[] nums, int k) {
        int res =0;
        Map<Integer,Integer> map = new HashMap<>();
        int start =0;
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            while(map.size()>k){
                if(map.get(nums[start])==1){
                    map.remove(nums[start]);
                }
                if(map.get(nums[start])>1){
                    map.put(nums[start],map.get(nums[start])-1);
                }
                start++;
            }

            if(map.size()==k){
                res+=(i-start-k+1);
            }
        }

        return res;
    }

    public int maximumUniqueSubarray(int[] nums) {
        int res = 0;
        int score=0;
        int start = 0;
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            while (set.contains(cur)){
                int pre = nums[start];
                score-=pre;
                set.remove(pre);
                start++;
            }
            set.add(cur);
            score+=cur;
            res = Math.max(res,score);
        }

        return res;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        }
        else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }


    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        for(int i=0;i<=n/2;i++){
            for(int j=n-1;j>i;j--){
                int target = -nums[i]-nums[j];
                int index = findValue(nums,i+1,j-1,target);
                if(index>=0){
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[index]);
                    list.add(nums[j]);
                    Collections.sort(list);
                    if(!res.contains(list)){
                        res.add(list);
                    }
                }
            }
        }

        return res;
    }

    private int findValue(int[] nums,int left,int right,int target){
        while(left<=right){
            int mid = left+(right-left)/2;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }

        return -1;
    }


    public String convert(String s, int nRows) {
        char[] c = s.toCharArray();
        int len = c.length;
        StringBuffer[] sb = new StringBuffer[nRows];
        for (int i = 0; i < sb.length; i++) sb[i] = new StringBuffer();

        int i = 0;
        while (i < len) {
            for (int idx = 0; idx < nRows && i < len; idx++) // vertically down
                sb[idx].append(c[i++]);
            for (int idx = nRows-2; idx >= 1 && i < len; idx--) // obliquely up
                sb[idx].append(c[i++]);
        }
        for (int idx = 1; idx < sb.length; idx++)
            sb[0].append(sb[idx]);
        return sb[0].toString();
    }


    public int myAtoi(String str) {
        int i=0;
        while (i<str.length()&&str.charAt(i)==' '){
            i++;
        }

        int sign=1;
        int base =0;
        if(i<str.length()&&(str.charAt(i)=='-'||str.charAt(i)=='+')){
            sign= str.charAt(i)=='-'?-1:1;
            i++;
        }

        while (i<str.length()&&str.charAt(i)>='0'&&str.charAt(i)<='9'){
            if(base>Integer.MAX_VALUE/10||(base==Integer.MAX_VALUE/10&&str.charAt(i)-'0'>7)){
                return sign==1?Integer.MAX_VALUE:Integer.MIN_VALUE;
            }
            base = base*10+(str.charAt(i)-'0');
            i++;
        }

        return base*sign;
    }


    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        helper(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    private void helper(List<String> res, StringBuilder sb, int open, int close, int n) {
        if(open == n && close == n) {
            res.add(sb.toString());
            return;
        }

        if(open < n) {
            sb.append("(");
            helper(res, sb, open+1, close, n);
            sb.setLength(sb.length()-1);
        }
        if(close < open) {
            sb.append(")");
            helper(res, sb, open, close+1, n);
            sb.setLength(sb.length()-1);
        }
    }


    public int trap(int[] height) {
        return trap(height,0,height.length-1);
    }

    public int trap(int[] height,int left,int right) {
        int i=left;
        int j=right;
        //桶的最左侧，趋势为递减
        while (i<right&&height[i]<=height[i+1]){
            i++;
        }
        left = i;
        //桶的最右侧，趋势为递增
        while (j>left&&height[j]<=height[j-1]){
            j--;
        }

        right = j;
        i++;


        while (i<right){
            if(height[i]>=height[left]){
                //内部形成更小的桶
                return trap(height,left,i)+trap(height,i,right);
            }else {
                i++;
            }
        }
        j--;
        while (j>left){
            if(height[j]>=height[right]){
                //内部形成更小的桶
                return trap(height,left,j)+trap(height,j,right);
            }else {
                j--;
            }
        }


        return getWater(height,left,right);
    }

    //雨水的积累量
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

    public String minWindow(String s, String t) {
        if(s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) return "";
        Map<Character,Integer> wordCount = new HashMap<>();
        for(char c:t.toCharArray()){
            wordCount.put(c,wordCount.getOrDefault(c,0)+1);
        }

        int start =0;
        int len = Integer.MAX_VALUE;
        int subStart = 0;
        int count = wordCount.size();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(!wordCount.containsKey(c)){
                continue;
            }

            wordCount.put(c,wordCount.get(c)-1);
            if(wordCount.get(c)==0){
                count--;
                while (count==0){
                    if(i-start+1<len){
                        len = i-start+1;
                        subStart = start;
                    }

                    char pre = s.charAt(start++);
                    if(!wordCount.containsKey(pre)){
                        continue;
                    }else {
                        wordCount.put(pre,wordCount.get(pre)+1);
                        if(wordCount.get(pre)==1){
                            count++;
                        }
                    }

                }
            }

        }

        return len==Integer.MAX_VALUE ? "":s.substring(subStart,subStart+len);

    }

    public List<Integer> grayCode(int n) {

        if(n == 1)
        {
            List<Integer> list = new ArrayList<>();
            list.add(0);list.add(1);
            return list;
        }

        List<Integer> pres = grayCode(n-1);
        List<Integer> mres = new ArrayList<>();

        for(int i=0;i<pres.size();i++)
            mres.add(0+2*pres.get(i));

        for(int i=pres.size()-1;i>=0;i--)
            mres.add(1+2*pres.get(i));

        return mres;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Map<String,List<String>> map = new HashMap<>();
        List<String> nextList = getNextList(beginWord,wordList);
        map.put(beginWord,nextList);

        for(String word:wordList){
            map.put(word,getNextList(word,wordList));
        }

        int step=0;
        Queue<String> queue = new LinkedList<>();
        Map<String,Integer> distance = new HashMap<>();
        queue.offer(beginWord);
        distance.put(beginWord,0);
        boolean flag = false;
        while (!queue.isEmpty()){
            int size = queue.size();
            step++;
            while (size!=0){
                size--;
                String cur = queue.poll();
                if(cur.equals(endWord)){
                    step--;
                    flag = true;
                    break;
                }

                for(String next:map.getOrDefault(cur,new ArrayList<>())){
                    if(distance.containsKey(next)){
                        continue;
                    }

                    distance.put(next,step);
                    queue.offer(next);
                }


            }

        }

        if(!flag){
            return res;
        }
        List<String> list = new ArrayList<>();
        list.add(beginWord);
        dfsFindLadders(beginWord,endWord,map,1,distance,res,list);
        return res;
    }


    private void dfsFindLadders(String beginWord, String endWord,Map<String,List<String>> map,int step,Map<String,Integer> distance,List<List<String>> res,List<String> list){
        if(beginWord.equals(endWord)){
            res.add(new ArrayList<>(list));
        }

        for(String next:map.getOrDefault(beginWord,new ArrayList<>())){
            if(distance.getOrDefault(next,0)==step){
                list.add(next);
                dfsFindLadders(next,endWord,map,step+1,distance,res,list);
                list.remove(list.size()-1);
            }
        }

    }


    private List<String> getNextList(String word,List<String> wordList){
        List<String> res = new ArrayList<>();
            for(int i=0;i<word.length();i++){
                char[] words = word.toCharArray();
                for(int j=0;j<26;j++){
                    if(words[i]!=(char)(j+'a')){
                        words[i]=(char)(j+'a');
                        String next = new String(words);
                        if(wordList.contains(next)){
                            res.add(next);
                        }
                    }
                }
            }

            return res;
    }

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // write your code here

        int[][] dirs = new int[][]{{0,1},{1,0},{-1,0},{0,-1}};
        if(maze[start[0]][start[1]]==1){
            return false;
        }
        int m = maze.length;
        int n = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()){
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            if(destination[0]==x&&destination[1]==y){
                return true;
            }

            for(int[] dir:dirs){
                int i = x+dir[0];
                int j = y+dir[1];
                if(i>=0&&j>=0&&i<m&&j<n&&maze[i][j]==0){
                    while (i>=0&&j>=0&&i<m&&j<n&&maze[i][j]==0){
                        i = i+dir[0];
                        j = j+dir[1];
                    }
                    i = i-dir[0];
                    j = j-dir[1];
                    maze[i][j] = 1;
                    queue.offer(new int[]{i,j});
                }
            }
        }

        return false;
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        long size = t+1L;
        int n = nums.length;
        Map<Long,Long> buckt = new HashMap<>();
        for(int i=0;i<n;i++){
            long u = nums[i]*1L;
            long idx = getIdx(u,size);
            if(buckt.containsKey(idx)){
                return true;
            }

            long left = idx-1;
            long right = idx+1;
            if(buckt.containsKey(left)&&u-buckt.get(left)<=t){
                return true;
            }

            if(buckt.containsKey(right)&&buckt.get(right)-u<=t){
                return true;
            }
            if(i>=k){
                // 移除下标范围不在 [max(0, i - k), i) 内的桶
                if (i >= k) buckt.remove(getIdx(nums[i - k] * 1L,size));
            }
        }

        return false;
    }

    private long getIdx(long u,long size){
        return u>=0? u/size:((u+1)/size)-1;

    }

    public boolean containsNearbyAlmostDuplicateI(int[] nums, int k, int t) {
        int n = nums.length;
        TreeSet<Long> ts = new TreeSet<>();
        for(int i=0;i<n;i++){
            Long u = nums[i]*1L;
            Long low = ts.floor(u);
            Long high = ts.ceiling(u);
            if(low!=null&&(u-low)<t){
                return true;
            }

            if(high!=null&&(high-u)<t){
                return true;
            }
            if(i>=k){
                ts.remove(nums[i-k]*1L);
            }
        }

        return false;
    }

        public static void main(String[] args) {
        BFStest test = new BFStest();
        int n = 5;
        int[][] relations = {{1,5},{2,5},{3,5},{3,4},{4,5}};
        int[] time = {1,2,3,4,5};
//        test.scheduleCourse(new int[][]{{100,200},{200,1300},{1000,1250},{2000,3200}});
       // test.minimumTime(3,new int[][]{{1,3},{2,3}}, new int[]{3,2,5});

//        test.containsNearbyAlmostDuplicate(new int[]{1,2,2,3,4,5}, 3 ,0);
        test.containsNearbyAlmostDuplicate(new int[]{4,1,6,3}, 100,0);
    }
}
