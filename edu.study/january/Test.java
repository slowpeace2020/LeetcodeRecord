package january;

import java.util.*;

public class Test {
    int res = Integer.MAX_VALUE;
    int[][] countMap;

    public int minStickers(String[] stickers, String target) {
        if (target == null) {
            return -1;
        }

        if (target.length() == 0) {
            return -1;
        }

        if (stickers == null || stickers.length == 0) {
            return -1;
        }

        int m = stickers.length;
        countMap = new int[m][26];

        for (int i = 0; i < m; i++) {
            for (char c : stickers[i].toCharArray()) {
                countMap[i][c - 'a']++;
            }
        }


        dfsMinStickers(0, 0, new int[26], target, stickers);

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * @param times         当前已经用过的单词个数
     * @param pos           target字符串中的下一个匹配位置
     * @param charAvaliable 用过的单词中还没用完的字符个数数组
     * @param target        目标字符串
     * @param stickers      提供的字符串列表
     */
    private void dfsMinStickers(int times, int pos, int[] charAvaliable, String target, String[] stickers) {
        if (times > res) {
            return;
        }

        int m = stickers.length;
        int n = target.length();
        //匹配到末尾，全部匹配成功
        if (pos == n) {
            res = Math.min(res, times);
            return;
        }

        //当前待匹配位置的字符
        char c = target.charAt(pos);
        //先前用过的单词中还有可供匹配的字符
        if (charAvaliable[c - 'a'] > 0) {
            charAvaliable[c - 'a']--;
            dfsMinStickers(times, pos + 1, charAvaliable, target, stickers);
        } else {
            //没有了，则需要加入新的单词供匹配
            for (int i = 0; i < m; i++) {
                //当前字符串不包含需要匹配的字符，跳过
                if (countMap[i][c - 'a'] == 0) {
                    continue;
                }

                //当前字符串包含需要匹配的字符，把它加入到用过的单词中，同时将它能提供的字符加入可供使用的字符数组
                for (int j = 0; j < 26; j++) {
                    charAvaliable[j] += countMap[i][j];
                }

                dfsMinStickers(times + 1, pos, charAvaliable, target, stickers);
                //还原到开始状态
                for (int j = 0; j < 26; j++) {
                    charAvaliable[j] -= countMap[i][j];
                }
            }
        }
    }


    public List<Integer> diffWaysToComputeI(String input) {
        List<Integer> ret = new LinkedList<Integer>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-' ||
                    input.charAt(i) == '*' ||
                    input.charAt(i) == '+') {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                List<Integer> part1Ret = diffWaysToComputeI(part1);
                List<Integer> part2Ret = diffWaysToComputeI(part2);
                for (Integer p1 : part1Ret) {
                    for (Integer p2 : part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+':
                                c = p1 + p2;
                                break;
                            case '-':
                                c = p1 - p2;
                                break;
                            case '*':
                                c = p1 * p2;
                                break;
                        }
                        if (c <= 1000) {
                            ret.add(c);
                        }
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        return ret;
    }

    public int scoreOfStudentsI(String s, int[] answers) {
        List<Integer> list = diffWaysToComputeI(s);

        Stack<Integer> stack = new Stack<>();
        stack.push(s.charAt(0) - '0');
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                stack.push(s.charAt(i + 1) - '0');
            } else if (s.charAt(i) == '*') {
                stack.push(stack.pop() * (s.charAt(i + 1) - '0'));
            }
        }
        int five = 0;
        while (!stack.isEmpty()) {
            five += stack.pop();
        }

        int ans = 0;
        for (int num : answers) {
            if (num == five) {
                ans += 5;
            } else if (list.contains(num)) {
                ans += 2;
            }
        }

        return res;

    }

    Map<String, Set<Integer>> mem;

    // 计算表达式正确答案
    public int calc(String s) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                int n = s.charAt(++i) - '0';
                stack.push(stack.pop() * n);
            } else if (c == '+') {

            } else {
                stack.push(c - '0');
            }
        }

        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }

        return res;
    }

    // 分治 + 记忆化 获取所有错误答案
    public Set<Integer> getErrorAnswers(String s) {
        Set<Integer> answers = new HashSet<>();
        if (s.length() == 1) {
            answers.add(s.charAt(0) - '0');
            return answers;
        }

        if (mem.containsKey(s)) {
            return mem.get(s);
        }

        for (int i = 1; i < s.length(); i += 2) {
            char c = s.charAt(i);

            Set<Integer> leftSet = getErrorAnswers(s.substring(0, i));
            Set<Integer> rightSet = getErrorAnswers(s.substring(i + 1));

            for (int left : leftSet) {
                for (int right : rightSet) {
                    int val = (c == '*') ? left * right : left + right;
                    if (val <= 1000) { // 答案外的值不保存
                        answers.add(val);
                    }
                }
            }
        }

        mem.put(s, answers);
        return answers;
    }

    public int scoreOfStudentsII(String s, int[] answers) {
        mem = new HashMap<>();
        Set<Integer> errAnsSet = getErrorAnswers(s);
        int validAns = calc(s);
        //System.out.println("validAns: " + validAns);

        int res = 0;
        for (int ans : answers) {
            if (ans == validAns) {
                res += 5;
            } else if (errAnsSet.contains(ans)) {
                res += 2;
            }
        }

        return res;
    }


    public Set<Integer> diffWaysToCompute(String input, Map<String, Set<Integer>> mem) {
        if (mem.containsKey(input)) {
            return mem.get(input);
        }
        Set<Integer> ret = new HashSet<Integer>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-' ||
                    input.charAt(i) == '*' ||
                    input.charAt(i) == '+') {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                Set<Integer> part1Ret = diffWaysToCompute(part1, mem);
                Set<Integer> part2Ret = diffWaysToCompute(part2, mem);
                for (Integer p1 : part1Ret) {
                    for (Integer p2 : part2Ret) {
                        int c = 0;
                        switch (input.charAt(i)) {
                            case '+':
                                c = p1 + p2;
                                break;
                            case '-':
                                c = p1 - p2;
                                break;
                            case '*':
                                c = p1 * p2;
                                break;
                        }
                        if (c <= 1000) {
                            ret.add(c);
                        }
                    }
                }
            }
        }
        if (ret.size() == 0) {
            ret.add(Integer.valueOf(input));
        }
        mem.put(input, null);
        return ret;
    }

    public int scoreOfStudents(String s, int[] answers) {
        Set<Integer> list = diffWaysToCompute(s, new HashMap<>());

        Stack<Integer> stack = new Stack<>();
        stack.push(s.charAt(0) - '0');
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                stack.push(s.charAt(i + 1) - '0');
            } else if (s.charAt(i) == '*') {
                stack.push(stack.pop() * (s.charAt(i + 1) - '0'));
            }
        }
        int five = 0;
        while (!stack.isEmpty()) {
            five += stack.pop();
        }

        int ans = 0;
        for (int num : answers) {
            if (num == five) {
                ans += 5;
            } else if (list.contains(num)) {
                ans += 2;
            }
        }

        return ans;

    }


    public int expressiveWords(String s, String[] words) {
        int count = 0;
        for (String word : words) {
            if (canMatch(s, word)) {
                count++;
            }
        }

        return count;
    }

    private boolean canMatch(String s, String target) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < target.length()) {
            if (s.charAt(i) == target.charAt(j)) {
                int len1 = getRepeateLenth(s, i);
                int len2 = getRepeateLenth(target, j);
                if ((len1 < 3 && len1 != len2) || (len1 >= 3 && len1 < len2)) {
                    return false;
                }

                i += len1;
                j += len2;
            } else {
                return false;
            }
        }


        return j == target.length() && i == s.length();

    }

    private int getRepeateLenth(String s, int i) {
        int j = i;
        while (j < s.length() && s.charAt(j) == s.charAt(i)) {
            j++;
        }

        return j - i;
    }


    public List<String> ambiguousCoordinates(String S) {
        S = S.substring(1, S.length() - 1);
        List<String> result = new LinkedList<>();
        for (int i = 1; i < S.length(); i++) {
            List<String> left = allowed(S.substring(0, i));
            List<String> right = allowed(S.substring(i));
            for (String ls : left) {
                for (String rs : right) {
                    result.add("(" + ls + ", " + rs + ")");
                }
            }
        }
        return result;
    }

    private List<String> allowed(String s) {
        int l = s.length();
        char[] cs = s.toCharArray();
        List<String> result = new LinkedList<>();
        if (cs[0] == '0' && cs[l - 1] == '0') { // "0xxxx0" Invalid unless a single "0"
            if (l == 1) {
                result.add("0");
            }
            return result;
        }
        if (cs[0] == '0') { // "0xxxxx" The only valid result is "0.xxxxx"
            result.add("0." + s.substring(1));
            return result;
        }
        if (cs[l - 1] == '0') { // "xxxxx0" The only valid result is itself
            result.add(s);
            return result;
        }
        result.add(s); // Add itself
        for (int i = 1; i < l; i++) { // "xxxx" -> "x.xxx", "xx.xx", "xxx.x"
            result.add(s.substring(0, i) + '.' + s.substring(i));
        }
        return result;
    }

    int ans = 0;

    public int uniqueWeightedPaths(int[][] grid) {
        // write your codes here
        int m = grid.length;
        int n = grid[0].length;

        int[] source = new int[2];
        int zeros = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    source[0] = i;
                    source[1] = j;
                } else if (grid[i][j] == 0) {
                    zeros++;
                }
            }
        }

        boolean[][] visited = new boolean[m][n];
        visited[source[0]][source[1]] = true;
        dfsFind(grid, source[0], source[1], visited, zeros);
        return ans;
    }


    public void dfsFind(int[][] grid, int x, int y, boolean[][] visited, int zeroCount) {

        if (grid[x][y] == 2) {
            if (zeroCount == 0) {
                ans++;
            }
            return;
        }

        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int m = grid.length;
        int n = grid[0].length;

        for (int[] dir : dirs) {
            int i = x + dir[0];
            int j = y + dir[1];
            if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] || grid[i][j] == -1) {
                continue;
            }
            visited[i][j] = true;
            dfsFind(grid, i, j, visited, zeroCount - 1);
            visited[i][j] = false;
        }

    }

    int count = 0;

    public int numSquarefulPerms(int[] nums) {
        Arrays.sort(nums);
        backtrack(nums, new boolean[nums.length], 0, -1);
        return count;
    }

    /**
     * @param nums
     * @param visited 标记某个数字是否被使用过
     * @param index   已经选择的数的个数
     * @param preNum  打乱数组的前一个数字
     */
    private void backtrack(int[] nums, boolean[] visited, int index, int preNum) {
        //如果数字都访问完了，说明这个打乱的数组是一个正方形数组
        if (index == nums.length) {
            count++;
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            //如果当前数字被访问过，直接跳过
            if (visited[i])
                continue;
            //如果当前数字和前一个数字不能构成完全平方数，直接跳过
            if (preNum > 0 && !isSquare(preNum + nums[i]))
                continue;
            //这里是为了过滤掉重复的，比如数组[2,2,7,9]，我们选择了
            //[2,7(第一个2)]和[2,7(第二个2)]，剩下的数字都是一样的，
            //所以他们的组合也是一样的，会出现重复，我们需要过滤掉
            if ((i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]))
                continue;
            //选择当前数字，标记为已经被使用过
            visited[i] = true;
            //递归，进行到下一层
            backtrack(nums, visited, index + 1, nums[i]);
            //回到上一层，撤销选择
            visited[i] = false;
        }
    }

    //判断数字num是否是完全平方数
    private boolean isSquare(int num) {
        int sqr = (int) Math.sqrt(num);
        return sqr * sqr == num;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if(key<root.val){
            root.left = deleteNode(root.left,key);
            return root;
        }else if(key>root.val){
            root.right = deleteNode(root.right,key);
            return root;
        }else {
            if(root.left==null){
                return root.right;
            }else if(root.right==null){
                return root.left;
            }else {
                TreeNode min = root.right;
                while (min.left!=null){
                    min = min.left;
                }

                //调用右边子树删除新值
                root.val = min.val;
                root.right = deleteNode(root.right,min.val);
                return root;
            }
        }
    }

    public TreeNode constructTreeNode(){
        TreeNode root = new TreeNode(5);
         root.left = new TreeNode(3);
         root.left.left = new TreeNode(2);
         root.left.right = new TreeNode(4);
         root.right = new TreeNode(6);
         root.right.right = new TreeNode(7);
         return root;
    }

    public int largestIslandI(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] size = new int[m*n+1];
        int[] root = new int[m*n+1];

        return 0;
    }

    public void dfsLargestIsland(int[][] grid,int x,int y,boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        for(int[] dir:dirs){
            int i = x+dir[0];
            int j = y+dir[1];
            if(i<0||j<0||i>=m||j>=n||visited[i][j]||grid[i][j]==0){
                continue;
            }
        }
    }

    public int largestIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] directions = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

        int res = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    continue;
                }
                boolean[][] visited = new boolean[m][n];
                Queue<int[]> queue=new LinkedList<>();
                queue.add(new int[]{i,j});
                visited[i][j] = true;
                int count = 0;
                while (!queue.isEmpty()){
                    int[] cur = queue.poll();
                    count++;
                    for(int[] direct:directions){
                        int x=cur[0]+direct[0];
                        int y=cur[1]+direct[1];
                        if(x<0||y<0||x>=m||y>=n||grid[x][y]==0||visited[x][y]){
                            continue;
                        }
                        visited[x][y] = true;
                        queue.add(new int[]{x,y});
                    }


                }

                res = Math.max(res,count);
            }
        }

        return res;
    }

    public int find(int pos,int[] parent){
        while(pos!=parent[pos]){
            parent[pos] = parent[parent[pos]];
            pos = parent[pos];
        }

        return pos;
    }


    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m * n];
        int k = 0;
        boolean up = true;
        int i=0;
        int j=0;
        while (k<m*n){

            if(up){
                while (k<m*n&&i>=0&&i<m&&j>=0&&j<n){
                    res[k++]=mat[i][j];
                    i--;
                    j++;
                }
            }


            if(j>=n){
                i+=2;
                j=n-1;
            }else{
                i=0;
            }
            up = false;
            if(!up){
                while (k<m*n&&i>=0&&i<m&&j>=0&&j<n){
                    res[k++]=mat[i][j];
                    i++;
                    j--;
                }
            }

            up= true;
            if(i>=m){
                i=m-1;
                j+=2;
            }else{
                j=0;
            }


        }
        return res;
    }

    public int divide(int dividend, int divisor) {
        //溢出情况
        if(dividend==Integer.MIN_VALUE&&divisor==-1){
            return Integer.MAX_VALUE;
        }

        int sign = (dividend<0)==(divisor<0) ? 1:-1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        int res =0;
        while (dividend>=divisor){
            int temp = divisor;
            int count =1;
            while (temp<=dividend-temp){
                temp+=temp;
                count+=count;
            }
            dividend-=temp;
            res+=count;
        }
        return sign==-1?-res:res;
    }

    public String getLargestString(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int[] count = new int[26];
        for(char c:s.toCharArray()){
            count[c-'a']++;
        }

        for(int i=25;i>=0;i--){
            if(count[i]>k){
                int temp = k;
                while (temp>0){
                    sb.append((char)(i+'a'));
                    temp--;
                }
                count[i]-=k;
                int j=i-1;
                while (count[j]<=0&&j>=0){
                    j--;
                }
                if(j>=0&&count[j]>0){
                    sb.append((char)(j+'a'));
                    count[j]--;
                }else {
                    break;
                }
            }else if(count[i]>0){
                while (count[i]>0){
                    sb.append((char)(i+'a'));
                    count[i]--;
                }

            }else {
                i--;
            }
        }

     return sb.toString();
    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer,Integer> frequency = new HashMap<>();
        for(int num:arr){
            frequency.put(num,frequency.getOrDefault(num,0)+1);
        }

        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<Map.Entry<Integer,Integer>>((a,b)->(a.getValue()-b.getValue()));
        queue.addAll(frequency.entrySet());
        while(k>0&&!queue.isEmpty()){
            Map.Entry<Integer,Integer> entry = queue.peek();
            if(k-entry.getValue()>=0){
                k-=entry.getValue();
                queue.poll();
            }
        }

        return queue.size();
    }

    public void wallsAndGates(int[][] rooms) {
        // write your code here

        int m = rooms.length;
        int n = rooms[0].length;
        int[][] dirs=new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(rooms[i][j]==0){
                    boolean[][] visited = new boolean[m][n];
                    visited[i][j] = true;
                    queue.add(new int[]{i,j});
                }
            }
        }

        while (!queue.isEmpty()){
                int[] cur = queue.poll();
                for(int[] dir:dirs){
                    int x = cur[0]+dir[0];
                    int y = cur[1]+dir[1];
                    if(x>=m||x<0||y>=n||y<0||rooms[x][y]!=Integer.MAX_VALUE){
                        continue;
                    }
                    rooms[x][y]=rooms[cur[0]][cur[1]]+1;
                    queue.add(new int[]{x,y});
                }
        }

    }



        public void wallsAndGatesI(int[][] rooms) {
            // Write your code here
            int n = rooms.length;
            if (n == 0) {
                return;
            }
            int m = rooms[0].length;

            int dx[] = {0, 1, 0, -1};
            int dy[] = {1, 0, -1, 0};

            Queue<Integer> qx = new LinkedList<>();
            Queue<Integer> qy = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (rooms[i][j] == 0) {
                        qx.offer(i);
                        qy.offer(j);
                    }
                }
            }

            while (!qx.isEmpty()) {
                int cx = qx.poll();
                int cy = qy.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = cx + dx[i];
                    int ny = cy + dy[i];
                    if (0 <= nx && nx < n && 0 <= ny && ny < m
                            && rooms[nx][ny] == Integer.MAX_VALUE) {
                        qx.offer(nx);
                        qy.offer(ny);
                        rooms[nx][ny] = rooms[cx][cy] + 1;
                    }
                }
            }

        }

    public String simplifyPath(String path) {
        String[] ps = path.split("/");
        StringBuilder sb = new StringBuilder();
        for(String str:ps){
            if(str.equals("")||str.equals(".")){
                continue;
            }
            if(str.equals("..")){
                if(sb.lastIndexOf("/")>=0){
                    sb.delete(sb.lastIndexOf("/"),sb.length());
                }
            }else {
                sb.append("/"+str);
            }
        }
        String res = sb.toString();
        if(res.length()==0){
            return "/";
        }

        return res;
    }
    public String minRemoveToMakeValid(String s) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){

            if(s.charAt(i)=='('){
                sb.append("_");
                stack.push(i);
            }else if(s.charAt(i)==')'){
                if(!stack.isEmpty()){
                    int pos = stack.pop();
                    sb.replace(pos,pos+1,"(");
                    sb.append(')');
                }else {
                    sb.append("_");
                }

            }else{
                sb.append(s.charAt(i));
            }
        }

        return sb.toString().replace("_","");
    }

    public int test(int a,int b){
        if(a==b){
            return a;
        }else if(a>b){
            return test(a-b,b);
        }else {
            return test(a,b-a);
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        //sort words so they will be added in a sorted order to nodes
        Arrays.sort(products);

        Trie root = new Trie();
        //构建Trie
        for (String prod : products) {
            Trie n = root;
            for (char ch : prod.toCharArray()) {
                int i = ch - 'a';
                if (n.next[i] == null) {
                    n.next[i] = new Trie();
                }
                n = n.next[i];
                if (n.words.size() < 3)
                    n.words.add(prod);
            }
        }

        List<List<String>> res = new ArrayList();
        Trie n = root;
        //start going over the search word char by char
        for (int i = 0; i < searchWord.length(); i++) {
            n = n.next[searchWord.charAt(i) - 'a'];
            //if we met null - means no more matches possible, the result of result can be filled by empty lists
            if (n == null) {
                for (int j = i; j < searchWord.length(); j++)
                    res.add(Collections.EMPTY_LIST);
                break;
            }
            res.add(n.words);
        }
        return res;
    }
    //trie node
    class Trie {
        Trie[] next;
        List<String> words;

        Trie() {
            words = new ArrayList();
            next = new Trie[26];
        }
    }


    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        int[] parent = new int[n];
        for(int i=0;i<n;i++){
            parent[i]=i;
        }

        for(List<Integer> pair:pairs){
            int p = pair.get(0);
            int q = pair.get(1);
            int pRoot = findRoot(p,parent);
            int qRoot = findRoot(q,parent);
            if(pRoot<qRoot){
                parent[q]=pRoot;
                parent[qRoot]=pRoot;
            }else if(pRoot>qRoot){
                parent[p]=qRoot;
                parent[pRoot]=qRoot;
            }
        }

        Map<Integer,PriorityQueue<Character>> map = new HashMap<>();
        for(int i=0;i<n;i++){
            int root = findRoot(i,parent);
            map.putIfAbsent(root,new PriorityQueue<>());
            map.get(root).offer(s.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            sb.append(map.get(findRoot(i,parent)).poll());
        }


        return sb.toString();
    }



    private int findRoot(int p,int[] parent){
        while(p!=parent[p]){
            p = parent[p];
        }

        return p;
    }

    public int equalSubstring(String s, String t, int maxCost) {
        int cost = 0;
        int len =0;
        int start=0;
        int n = s.length();
        for(int i=0;i<n;i++){
            cost+=Math.abs(s.charAt(i)-t.charAt(i));
            while(cost>maxCost){
                cost-=Math.abs(s.charAt(start)-t.charAt(start));
                start++;
            }

            len = Math.max(len,i-start+1);
        }
        return len;
    }

    public int openLock(String[] deadends, String target) {
        int max = 10000;
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add("0000");
        visited.add("0000");
        for(String end:deadends){
            visited.add(end);
        }
        int step = 0;
        while(!queue.isEmpty()&&step<max){
            step++;
            int size = queue.size();
            while(size>0){
                size--;
                String cur = queue.poll();
                if(cur.equals(target)){
                    return step-1;
                }
                    for(int i=0;i<4;i++){
                        char[] arr1 = cur.toCharArray();
                        arr1[i] = arr1[i]=='9'? '0':(char)(arr1[i]+1);
                        char[] arr2 = cur.toCharArray();
                        arr2[i] = arr2[i]=='0'? '9':(char)(arr2[i]-1);
                        String next1 = new String(arr1);
                        String next2=new String(arr2);
                        if(visited.add(next1)){
                            queue.offer(next1);
                        }
                        if(visited.add(next2)){
                            queue.offer(next2);
                        }
                    }

            }
        }

        return -1;

    }

    public int reachNumber(int target) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
//        Set<Integer> visited = new HashSet<>();
//        visited.add(0);
        int step =0;
        while(!queue.isEmpty()){
            step++;
            int size = queue.size();
            while(size>0){
                size--;
                int cur = queue.poll();
                if(cur==target){
                    return step-1;
                }
                if(cur+step<1e9){
                    queue.offer(cur+step);
                }
                if(cur-step>-1e9){
                    queue.offer(cur-step);
                }
            }
        }

        return step;
    }


    public String pushDominoes(String dominoes) {
        int n = dominoes.length();
        int[] left = new int[n];
        int[] right=new int[n];
        left[n-1] = dominoes.charAt(n-1)=='L' ? 1:0;
        for(int i=n-2;i>=0;i--){
            char c = dominoes.charAt(i);
            left[i] = left[i+1];
            if(c=='L'){
                left[i]++;
            }
        }

        right[0] = dominoes.charAt(0)=='R'?1:0;
        for(int i=1;i<n;i++){
            char c = dominoes.charAt(i);
            right[i] = right[i-1];
            if(c=='R'){
                right[i]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            if(left[i]==right[i]){
                sb.append(".");
            }else if(left[i]>right[i]){
                sb.append('L');
            }else{
                sb.append('R');
            }
        }

        return sb.toString();
    }

    public boolean halvesAreAlike(String s) {
        int count = 0;
        int n = s.length();
        List<Character> list = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        for(int i=0;i<n/2;i++){
            if(list.contains(s.charAt(i))){
                count++;
            }
        }

        for(int i=n/2;i<n;i++){
            if(list.contains(s.charAt(i))){
                count--;
            }
        }

        return count==0;
    }

        public static void main(String[] args) {
        Test test = new Test();
       List<Character> list = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
//         test.findDiagonalOrder(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
//            test.test(2437,875);
//            System.out.println(test.openLock(new String[]{"0201","0101","0102","1212","2002"},"0202"));
            System.out.println(test.reachNumber(2));
    }
}
