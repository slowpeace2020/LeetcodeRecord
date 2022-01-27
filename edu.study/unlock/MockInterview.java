package unlock;

import java.util.*;

public class MockInterview {

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        PriorityQueue<String> queue = new PriorityQueue<>(3,(a,b)->a.compareTo(b));
        List<List<String>> res = new ArrayList<>();

        for(int i=1;i<=searchWord.length();i++){
            String sub = searchWord.substring(0,i);
            for(String s:products){
                if(s.startsWith(sub)){
                    queue.offer(s);
                }
            }
            List<String> tempList = new ArrayList<>();
            for(int j=0;j<3;j++){
                if(queue.peek()!=null){
                    tempList.add(queue.poll());
                }
            }
            queue.clear();
            res.add(tempList);
        }
        return res;
    }

    public List<List<String>> suggestedProductsI(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        Arrays.sort(products);
        for(int i=1;i<=searchWord.length();i++){
            String sub = searchWord.substring(0,i);
            int k = Arrays.binarySearch(products,sub);
            while (k>0&&sub.equals(products[k-1])){
                --k;
            }
            if(k<0){
                k=~k;
            }
            List<String> tempList = new ArrayList<>();
            for(int j=k+3;k<products.length&&k<j&&products[k].startsWith(sub);k++){
                tempList.add(products[k]);
            }
            res.add(tempList);
        }

        return res;
    }

    public List<List<String>> suggestedProductsII(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        Arrays.sort(products);
        for(int i=1;i<=searchWord.length();i++){
            String sub = searchWord.substring(0,i);
            List<String> tempList = new ArrayList<>();
            for(String word:products){
                if(word.startsWith(sub)){
                    tempList.add(word);
                }
                if (tempList.size()==3){
                    break;
                }
            }

            res.add(tempList);
        }

        return res;
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum=0;
        for(int num:nums){
            sum+=num;
        }

        if(k<=0||sum%k!=0){
            return false;
        }
        int n = nums.length;
        int target = sum/k;

        //dp[i]代表状态为i的和，如果和=target， 将值置为0，若小于target，则保留
        int[] dp = new int[(1<<n)+1];
        Arrays.fill(dp,-1);
        dp[0]=0;
        for(int i=0;i<(1<<n);i++){
            //此状态还没计算过，不参与更新
            if(dp[i]==-1)continue;
            for(int j=0;j<n;j++){
                //检测第j个元素是否已经参与运算，被分配过
                //bitmask实现校验
                int isNumNotInSubSet = ((i&(1<<j)))==0 ? 1:0;
                //还没有参与过
                if(isNumNotInSubSet==1){
                    //加入到被计算过的dp[i]，i状态下，
                    // 只有当i状态下的和加上当前元素的结果小于等于target才有意义
                    if(dp[i]+nums[j]<=target){
                        //当i状态下的和加上当前元素，构成了一个新状态
                        int subset = i|1<<j;
                        //更新新状态的和
                        dp[subset]=(dp[i]+nums[j])==target?0:(dp[i]+nums[j]);
                    }
                }
            }
        }

        return dp[(1<<n)-1]==0;
    }

    public int shortestPathLength(int[][] graph) {
        //dp代表终点为i,j状态下的最小步数
        int[][] dp = new int[graph.length][1<<graph.length];
        Queue<State> queue = new LinkedList<>();
        //初始化，每个点都可以作为起点
        for(int i=0;i<graph.length;i++){
            Arrays.fill(dp[i],Integer.MAX_VALUE);
            dp[i][1<<i]=0;
            queue.offer(new State(1<<i,i));
        }

        while (!queue.isEmpty()){
            State state = queue.poll();
            for(int next:graph[state.target]){
                //转移到下一个有效状态
               int nextMask = state.mask|(1<<next);
               //仅仅当下一个状态的步数变小时才继续
               if(dp[next][nextMask]>dp[state.target][state.mask]+1){
                   dp[next][nextMask] = dp[state.target][state.mask]+1;
                   queue.offer(new State(nextMask,next));
               }
            }
        }

        int res = Integer.MAX_VALUE;
        for(int i=0;i<graph.length;i++){
            res = Math.min(res,dp[i][1<<graph.length-1]);
        }

        return res==Integer.MAX_VALUE?-1:res;
    }

    class State{
        int mask;
        int target;
        public State(int mask,int target){
            this.mask = mask;
            this.target = target;
        }
    }

    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
            int ns = req_skills.length;
            int np =  people.size();
            //map存储每个技能对应的比特位
            Map<String,Integer> map = new HashMap<>();
            for(int i=0;i<req_skills.length;i++){
                map.put(req_skills[i],i);
            }

            //dp表示每个技能状态下所需要的人的组合方案
            List<Integer>[] suff = new List[1<<ns];
            suff[0] = new ArrayList<>();
            for(int i=0;i<np;i++){
                int skill = 0;
                for(String s:people.get(i)){
                    skill|=(1<<map.get(s));
                }

                //每个有效状态下，加上当前这个人，可以更新为一个新的技能状态
                for(int prev=0;prev<suff.length;prev++){
                    //当前状态并非有效状态，不参与运算
                    if(suff[prev]==null){
                        continue;
                    }

                    int comnination = prev|skill;
                    //新的技能状态方案集为空或者人数比当前状态集加上新人多，就更新
                    if(suff[comnination]==null||suff[comnination].size()>suff[prev].size()+1){
                        suff[comnination] = new ArrayList<>(suff[prev]);
                        suff[comnination].add(i);
                    }
                }
            }

            List<Integer>  res = suff[(1<<ns)-1];
            int[] arr = new int[res.size()];
            for(int i=0;i<arr.length;i++){
                arr[i] = res.get(i);
            }

            return arr;
    }

    private boolean[] calculateDuplicate(int[] peopleBinary, int len) {
        boolean[] isDuplicateArr = new boolean[len];
        for(int i=0;i<peopleBinary.length;i++){
            for(int j=i+1;j<peopleBinary.length;j++){
                if(peopleBinary[i]==peopleBinary[j]){
                    isDuplicateArr[j] = true;
                } else if (peopleBinary[i] > peopleBinary[j] && isPeopleContainsAll(peopleBinary[i], peopleBinary[j],len)) {
                    isDuplicateArr[j] = true;
                } else if (peopleBinary[j] > peopleBinary[i] && isPeopleContainsAll(peopleBinary[j], peopleBinary[i],len)) {
                    isDuplicateArr[i] = true;
                }
            }
        }

        return isDuplicateArr;
    }

    private boolean isPeopleContainsAll(int skill1, int skill2,int len) {
        int mask =1;
        for(int i=0;i<len;i++){
            if((skill1&mask)==0&&(skill2&mask)==1){
                return false;
            }
            mask<<=1;
        }

        return true;
    }


    public String longestPalindrome(String s) {
        int n = s.length();
        String res = null;
        boolean[][] dp = new boolean[n][n];
        for(int i=n-1;i>=0;i--){
            for(int j=i;j<n;j++){
                //区间长度小于3，也就是 a, aa, aaa,这3种情况，不用判断区间[i+1,j-1]
                dp[i][j] = s.charAt(i)==s.charAt(j)&&((j-i)<3||dp[i+1][j-1]);
                if(dp[i][j]&&(res==null||j-i+1>res.length())){
                    res = s.substring(i,j+1);
                }
            }
        }

        return res;
    }

    public String shortestPalindrome(String s) {
        int n = s.length();
        int start=0;
        for(int i=n;i>=0;i--){
            if(isPalindrome(s.substring(0,i))){
                start=i;
                break;
            }
        }

        String left = s.substring(start);
        StringBuilder sb = new StringBuilder(left);
        sb.reverse();

        return sb+s;
    }


    public boolean isPalindrome(String s){
        int i=0;
        int j=s.length()-1;
        while(i<j){
            if(s.charAt(i)!=s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public int countSubstrings(String s) {
        int n = s.length();
        int res = 0;
        for(int i=0;i<n;i++){
            res+=countPalindrome(i,i,s);
            res+=countPalindrome(i,i+1,s);
        }

        return res;
    }

    public int countPalindrome(int left,int right,String s){
        int count = 0;
        while (left>=0&&right<s.length()&&s.charAt(left)==s.charAt(right)){
            count++;
            left--;
            right++;
        }
        return count;
    }


    public int maxAbsoluteSum(int[] nums) {
        int n=nums.length;
        int[][] dp = new int[n][2];
        int res = Math.abs(nums[0]);
        if(nums[0]>0){
            dp[0][0] = nums[0];
            dp[0][1] = 0;
        }else{
            dp[0][1] = nums[0];
            dp[0][0] = 0;
        }

        for(int i=1;i<n;i++){
            if(nums[i]>=0){
                dp[i][0]=dp[i-1][0]+nums[i];
                dp[i][1]=dp[i-1][1]+nums[i]>0?0:dp[i-1][1]+nums[i];
            }else{
                dp[i][0]=dp[i-1][0]+nums[i]<0?0:dp[i-1][0]+nums[i];
                dp[i][1]=dp[i-1][1]+nums[i];
            }
            res =Math.max(res,dp[i][0]);
            res =Math.max(res,-dp[i][1]);
        }

        return res;
    }


    public int maxProduct(int[] nums) {
        int n = nums.length;
        int max = nums[0];
        int min = nums[0];
        int res = nums[0];


        for(int i=1;i<n;i++){
           int temp= max;
           max = Math.max(Math.max(max*nums[i],min*nums[i]),nums[i]);
           min = Math.min(Math.min(temp*nums[i],min*nums[i]),nums[i]);
           if(max>res){
               res = max;
           }

        }

        return res;

    }

    public int[] productExceptSelf(int[] nums) {
        int multiple =1;
        int hasZero = 0;
        boolean other = false;
        for(int num:nums){
            if(num==0){
                hasZero++;
                if(hasZero>1){
                    multiple=0;
                    break;
                }
                continue;
            }

            multiple*=num;
        }

        int n =nums.length;
        int[] res = new int[n];
        for(int i=0;i<n;i++){
            if(hasZero>0){
                if(nums[i]!=0){
                    res[i]=0;
                }else{
                    res[i]=multiple;
                }
            }else{
                res[i]=multiple/nums[i];
            }
        }

        return res;
    }

    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int pre = nums[0]*nums[1]*nums[n-1];
        int suff = nums[n-1]*nums[n-2]*nums[n-3];
        return Math.max(pre,suff);
    }


    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<Integer> ans = new ArrayList<>();
        List<Integer>[] res =new List[n];
        res[0] = new ArrayList<>();
        for(int i=1;i<n;i++){
            for(int j=i-1;j>=0;j--){
                if(res[j]==null){
                    res[j] = new ArrayList<>();
                }
                if(res[i]==null){
                    res[i] = new ArrayList<>(res[j]);
                }
                if(nums[i]%nums[j]==0&&res[j].size()>=res[i].size()){

                    if(res[j].size()==0){
                        res[i].add(nums[j]);
                    }
                    res[i].add(nums[i]);
                    if(res[i].size()>ans.size()){
                        ans=res[i];
                    }
                }



            }
        }

        return ans;
    }


    public int wordCount(String[] startWords, String[] targetWords) {
        Arrays.sort(startWords,(a,b)->(a.length()-b.length()));
        Map<Integer,List<Integer>> map = new HashMap<>();
        int m = startWords.length;
        int n = targetWords.length;
        int[][] words = new int[m][26];

        int res =0;
        for(int i=0;i<m;i++){
            for(char c:startWords[i].toCharArray()){
                words[i][c-'a']++;
            }
            map.putIfAbsent(startWords[i].length(),new ArrayList<>());
            map.get(startWords[i].length()).add(i);
        }


        Set<Integer> visited = new HashSet<>();
        for(int i=0;i<n;i++){
            int[] target = new int[26];
            for(char c:targetWords[i].toCharArray()){
                target[c-'a']++;
            }
            List<Integer> list = map.getOrDefault(targetWords[i].length(),new ArrayList<>());
            List<Integer> list1 = map.getOrDefault(targetWords[i].length()-1,new ArrayList<>());
            list.addAll(list1);
            if(list.size()!=0){
                for(int j=0;j<list.size();j++){
                    if(!visited.contains(list.get(j))&&canGet(target,words[list.get(j)])){
                        visited.add(list.get(j));
                        res++;
                        break;
                    }
                }

            }

        }

        return res;






    }

    public boolean canGet(int[] target,int[] word){
        int diff=0;
        for(int i=0;i<26;i++){
            if(target[i]==word[i]){
                continue;
            }
            if(target[i]>word[i]){
                diff+=target[i]-word[i];
            }else{
                return false;
            }
            if(diff>1){
                return false;
            }
        }
        return diff<=1;
    }


    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] cache = new int[m][n];
        int max = 1;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int len = dfs(matrix, i, j, m, n, cache);
                max = Math.max(max, len);
            }
        }
        return max;
    }

    private int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
        if(cache[i][j]!=0){
            return cache[i][j];
        }
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int max = 1;
        for(int[] dir:dirs){
            int x =i+dir[0];
            int y = j+dir[1];
            if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) continue;
            int len = 1+dfs(matrix,i,j,m,n,cache);
            max = Math.max(max,len);
        }

        cache[i][j] = max;
        return max;
    }

    public boolean increasingTriplet(int[] nums) {
        int n=nums.length;
        Stack<Integer> stack = new Stack<>();
        for(int num:nums){
            while(!stack.isEmpty()&&num<stack.peek()){
                stack.pop();
            }

            if(stack.isEmpty()||stack.peek()<num){
                stack.push(num);
            }
            if(stack.size()>=3){
                return true;
            }

        }

        return false;
    }

    public int findNthDigit(int n) {
        int len =1;
        long count =9;
        int start=1;
        while (n>len*count){
            n-=len*count;
            len+=1;
            count*=10;
            start*=10;
        }
        start+=(n-1)/len;
        String s = start+"";
        return Character.getNumericValue(s.charAt((n-1)%len));
    }

    int sumLeaf= 0;
    public int sumRootToLeaf(BinaryTree.TreeNode root) {
         dfsRootToLeaf(root, 0);
         return sumLeaf;
    }

    private void dfsRootToLeaf(BinaryTree.TreeNode root, int val) {
        if(root==null){
            return;
        }

        val=val*2+root.val;
        if(root.left==null&&root.right==null){
            sumLeaf+=val;
            return;
        }

        dfsRootToLeaf(root.left,val);
        dfsRootToLeaf(root.right,val);
    }

    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        for(int i=0;i<m;i++){
            if(grid[i][0]==1){
                visited[i][0] = true;
                dfsCanBoundry(grid,visited,i,0);
            }
        }


        for(int i=0;i<m;i++){
            if(grid[i][n-1]==1){
                visited[i][n-1] = true;
                dfsCanBoundry(grid,visited,i,n-1);
            }
        }

        for(int j=0;j<n;j++){
            if(grid[0][j]==1){
                visited[0][j] = true;
                dfsCanBoundry(grid,visited,0,j);
            }
        }

        for(int j=0;j<n;j++){
            if(grid[m-1][j]==1){
                visited[m-1][j] = true;
                dfsCanBoundry(grid,visited,m-1,j);
            }
        }


        int res = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1&&!visited[i][j]){
                    res++;
                }
            }
        }

        return res;
    }

    public void dfsCanBoundry(int[][] grid, boolean[][] visited,int x,int y){
        int m = grid.length;
        int n = grid[0].length;
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        for(int[] dir:dirs){
            int i=x+dir[0];
            int j=y+dir[1];
            if(i<0||j<0||i>=m||j>=n||visited[i][j]||grid[i][j]==0){
                continue;
            }
            visited[i][j]=true;
            dfsCanBoundry(grid,visited,i,j);
        }
    }

    public int shipWithinDays(int[] weights, int days) {
        int left =1;
        int right = 50000;
        for(int wei:weights){
            if(wei>left){
                left = wei;
            }
        }

        while(left<right){
            int mid = left+(right-left)/2;
            if(check(weights,days,mid)){
                right = mid;
            }else{
                left =mid+1;
            }
        }

        return left;
    }

    public boolean check(int[] weights, int days,int mid){
        int count =1;
        int sum =0;
        for(int weight:weights){
            if(sum+weight<=mid){
                sum+=weight;
            }else{
                sum=weight;
                count++;
            }
            if(count>days){
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        Random random = new Random();

       String[] products = new String[]{"mobile","mouse","moneypot","monitor","mousepad"};
       String searchWord = "mouse";
       MockInterview test = new MockInterview();
       test.shipWithinDays(new int[]{1,2,3,1,1},4);
//       test.wordCount(new String[]{"ant","act","tack"}, new String[]{"tack","act","acti"});
    }
}
