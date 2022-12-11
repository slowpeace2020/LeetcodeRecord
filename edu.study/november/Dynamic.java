package november;

import algs4.src.main.java.edu.princeton.cs.algs4.In;

import java.util.*;

public class Dynamic {
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] increase = new int[n];
        int[] decrease = new int[n];

        increase[0] = 1;
        decrease[n-1] = 1;

        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    increase[i] = Math.max(increase[i],increase[j]+1);
                }
            }
        }

        for(int i=n-2;i>0;i--){
            for(int j=i+1;j<n;j++){
                if(nums[i]>nums[j]){
                    decrease[i]=Math.max(decrease[i],decrease[j]+1);
                }
            }
        }

        int mountain = 0;
        for(int i=1;i<n-1;i++){
            if(increase[i]==1||decrease[i]==1){
                continue;
            }

            mountain = Math.max(mountain,increase[i]+decrease[i]-1);
        }


        return n-mountain;
    }

    public int waysToMakeFair(int[] nums) {
        int n = nums.length;
        int[] odd = new int[n];
        int[] even = new int[n];
        even[0] = nums[0];
        for(int i=1;i<n;i++){
            if(i%2==0){
                even[i]=even[i-1]+nums[i];
                odd[i] = odd[i-1];
            }else{
                even[i] = even[i-1];
                odd[i] = odd[i-1]+nums[i];
            }
        }

        int count = even[n-1]-even[0]==odd[n-1] ? 1:0;

        for(int i=1;i<n;i++){
            int curOdd = odd[i-1]+even[n-1]-even[i];
            int curEven = even[i-1]+odd[n-1]-odd[i];
            if(curOdd==curEven){
                count++;
            }
        }

        return count;
    }

    public int minimumDeletions(String s) {
        int n = s.length();
        //dp stores number of chars to remove to make s.substring(0, i) valid
        int[] dp = new int[n+1];
        int bcount = 0;
        for(int i=1;i<=n;i++){
            if(s.charAt(i-1)=='a'){
                dp[i] = Math.min(dp[i-1]+1,bcount);
            }else {
                dp[i] = dp[i-1];
                bcount++;
            }
        }

        return dp[n];
    }

    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int[] left=new int[n];
        int[] right= new int[n];
        int[] prefixSum = new int[n];
        int[] suffixSum = new int[n];
        prefixSum[0] = boxes.charAt(0)-'0';
        for(int i=1;i<n;i++){
            left[i] = left[i-1]+prefixSum[i-1];
            prefixSum[i]=prefixSum[i-1]+boxes.charAt(i)-'0';
        }

        suffixSum[n-1] = boxes.charAt(n-1)-'0';
        for(int i=n-2;i>=0;i--){
            right[i]=right[i+1]+suffixSum[i+1];
            suffixSum[i]=suffixSum[i+1]+boxes.charAt(i)-'0';
        }

        int[] res = new int[n];
        for(int i=0;i<n;i++){
            res[i] = left[i]+right[i];
        }

        return res;

    }

    Map<String,Integer> cache = new HashMap<>();
    public int minFlips(String s) {
        StringBuilder sb = new StringBuilder(s);
        String reverse = sb.reverse().toString();
        if(cache.containsKey(s)||cache.containsKey(reverse)){
            int res = Integer.MAX_VALUE;
            if(cache.containsKey(s)){
                res = Math.min(res,cache.get(s));
            }

            if(cache.containsKey(reverse)){
                res = Math.min(res,cache.get(reverse));
            }
            return res;
        }
        int count=0;
        int pre = s.charAt(0)-'0';
        for(int i=1;i<s.length();i++){
            int cur = s.charAt(i)-'0';
            if(cur==pre){
                count++;
                pre=1-cur;
            }else{
                pre = cur;
            }

        }

        cache.put(s,count);

        int count2=0;
        pre = s.charAt(s.length()-1)-'0';
        for(int i=s.length()-2;i>=0;i--){
            int cur = s.charAt(i)-'0';
            if(cur==pre){
                count2++;
                pre=1-cur;
            }else{
                pre = cur;
            }
        }
        cache.put(reverse,count2);

        String s2 = s.substring(1)+s.charAt(0);
        int res = minFlips(s2);

        return Math.min(Math.min(count,count2),res);
    }


    public String largestNumber(int[] nums) {
        int n = nums.length;
        String[] str = new String[n];
        for(int i=0;i<n;i++){
            str[i] = nums[i]+"";
        }

        Arrays.sort(str, new Comparator<String>() {
            @Override
            public int compare(String s, String t) {
                return (s + t).compareTo(t + s);
            }
        });
        StringBuilder sb = new StringBuilder();

        for(int i=n-1;i>=0;i--){
            sb.append(str[i]);
        }



        return sb.toString();

    }

    class Interval implements Comparable<Interval>{
        int node;
        boolean start;
        public Interval(int node,boolean start){
            this.node = node;
            this.start = start;
        }


        @Override
        public int compareTo(Interval interval) {
            return this.node==interval.node? (this.start? -1:1) : this.node-interval.node;
        }
    }


    private long getSummation(int n) {
        if (n < 0)
            return 0;

        return (long)((long)n * (long)(n + 1)) / 2;
    }

    public long maximumBooks(int[] books) {
        int len = books.length;
        long[] dp = new long[len];

        // dp[i] represents max number of books that can be taken
        // between shelf 0 and shelf i (both inclusive)

        // use monotonic stack to populate dp array; for every index i,
        // find the nearest break point j < i such that books[i - j] <
        // books[i] - i + j

        // this becomes the restraining point for picking books as now
        // instead of picking (books[i] - i + j) books, we can only pick
        // books[i - j] books; so we will pick the maximum dp[j] books +
        // (books[i] + books[i] - 1 + books[i] - 2 + ... + books[i] - (i - j - 1))
        Deque<Integer> stack = new ArrayDeque();
        long maxBooks = 0;

        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && books[stack.peek()] >= books[i] - i + stack.peek())
                stack.pop();

            // pick dp[j] books and (books[i] + books[i] - 1 + ... + books[i] -
            // (i - j - 1)) books, where j is the current stack top; the latter
            // expression can be rewritten as a difference of two n-summations
            dp[i] = (stack.isEmpty() ? 0 : dp[stack.peek()]) + getSummation(books[i]) - getSummation(books[i] - i + (stack.isEmpty() ? -1 : stack.peek()));

            maxBooks = Math.max(maxBooks, dp[i]);
            stack.push(i);
        }

        return maxBooks;
    }

    public int cherryPickup(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] dirs = new int[][]{{0,1},{1,0}};
        if(!canReach(grid,visited,new int[]{0,0},new int[]{m-1,n-1},dirs)){
            return 0;
        }

        int count = dfsMaxCherry(grid,new boolean[m][n],new int[]{0,0},new int[]{m-1,n-1},dirs);


        visited = new boolean[m][n];
        dirs = new int[][]{{0,-1},{-1,0}};
        if(!canReach(grid,visited,new int[]{m-1,n-1},new int[]{0,0},dirs)){
            return 0;
        }

        return count;
    }

    private int dfsMaxCherry(int[][] grid,boolean[][] visited,int[] start,int[] target,int[][] dirs){
        if(visited[start[0]][start[1]]){
            return 0;
        }
        if(start[0]==target[0]&&start[1]==target[1]){
            return grid[start[0]][start[1]];
        }


        int m = grid.length;
        int n = grid[0].length;
        int res = grid[start[0]][start[1]];
        int count = 0;
        visited[start[0]][start[1]] = true;
        for(int[] dir:dirs){
            int x = start[0]+dir[0];
            int y = start[1]+dir[1];
            if(x<0||y<0||x>=m||y>=n||visited[x][y]){
                continue;
            }
            visited[x][y] = true;
            count = Math.max(count,dfsMaxCherry(grid,visited,new int[]{x,y},target,dirs));
        }

        visited[start[0]][start[1]] = false;
        return res+count;
    }

    private boolean canReach(int[][] grid,boolean[][] visited,int[] start,int[] target,int[][] dirs){
        int m = grid.length;
        int n = grid[0].length;

        if(grid[0][0]==-1){
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();
        visited[0][0] = true;
        queue.add(new int[]{0,0});

        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            if(cur[0]==target[0]&&cur[1]==target[1]){
                return true;
            }
            for(int[] dir:dirs){
                int x = cur[0]+dir[0];
                int y = cur[1]+dir[1];
                if(x<0||y<0||x>=m||y>=n||visited[x][y]||grid[x][y]==-1){
                    continue;
                }
                queue.add(new int[]{x,y});
                visited[x][y] = true;
            }
        }

        return false;
    }

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points,(a,b)->a[1]-b[1]);
        int arrowPos = points[0][1];
        int count = 1;
        for(int i=1;i<points.length;i++){
            if(arrowPos>=points[i][0]){
                continue;
            }
            count++;
            arrowPos = points[i][1];
        }

        return count;
    }

    public String boldWords(String[] words, String s) {
        Set<String> set = new HashSet<>();
        for(String word:words){
            set.add(word);
        }
        List<int[]> list = new ArrayList<>();

        for(String word:words){
            int pos = s.indexOf(word);
            while(pos>=0){
                list.add(new int[]{pos,pos+word.length()});
                if(pos+1>=s.length()){
                    break;
                }
                String remind = s.substring(pos+1);
                if(remind.indexOf(word)<0){
                    break;
                }
                pos = pos+1+remind.indexOf(word);
            }
        }

        if(list.size()==0){
            return s;
        }
        list =  mergeInterval(list);
        StringBuilder sb = new StringBuilder();

        int start = list.get(0)[0];
        int end =  list.get(0)[1];
        sb.append(s.substring(0,start)+"<b>");
        sb.append(s.substring(start,end)+"</b>");
        start = end;
        for(int i=1;i<list.size();i++){
            int curStart = list.get(i)[0];
            int curEnd = list.get(i)[1];
            sb.append(s.substring(start,curStart)+"<b>");
            sb.append(s.substring(curStart,curEnd)+"</b>");
            start = curEnd;
        }
        sb.append(s.substring(start));
        return sb.toString();
    }

    private List<int[]> mergeInterval(List<int[]> list){
        Collections.sort(list,(a,b)->(a[0]==b[0]? b[1]-a[1]:a[0]-b[0]));
        List<int[]> res = new ArrayList<>();
        int start = list.get(0)[0];
        int end = list.get(0)[1];

        for(int i=1;i<list.size();i++){
            int curStart = list.get(i)[0];
            int curEnd = list.get(i)[1];

            if(curStart<=end){
                end = Math.max(end,curEnd);
            }else{
                res.add(new int[]{start,end});
                start = curStart;
                end = curEnd;
            }

        }

        res.add(new int[]{start,end});
        return res;
    }

        public static void main(String[] args) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b)->(a[0]==b[0]?(a[1]>b[1]? 1:-1) : (a[0]>b[0] ? 1:-1)));
        Dynamic dp = new Dynamic();
        int[] numbers = new int[]{8,5,2,7,9};

        String[] strs = new String[]{"ccb","b","d","cba","dc"};
        dp.boldWords(strs,"eeaadadadc");
    }
}
