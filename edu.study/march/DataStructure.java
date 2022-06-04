package march;

import feb.CBTInserter;

import java.io.*;
import java.util.*;

public class DataStructure {

    public boolean wordPattern(String pattern, String str) {
        Map<String,Character> map = new HashMap<>();
        boolean res = dfsMatch(pattern,str,0,0,map);
        return res;
    }

    private boolean dfsMatch(String pattern, String str,int pIndex, int start, Map<String,Character> map){
        int m = pattern.length();
        int n = str.length();
        if(pIndex==m&&n==start){
            return true;
        }

        if(pIndex==m||n==start){
            return false;
        }

        for(int i=start+1;i<=n;i++){
            String part = str.substring(start,i);
            if(map.containsKey(part)){
                if(map.get(part)==pattern.charAt(pIndex)){
                    if(dfsMatch(pattern,str,pIndex+1,i,map)){
                        return true;
                    }
                }else {
                    return false;
                }
            }else {
                if(map.values().contains(pattern.charAt(pIndex))){
                    return false;
                }
                map.put(part,pattern.charAt(pIndex));
                if(dfsMatch(pattern,str,pIndex+1,i,map)){
                    return true;
                }
                map.remove(part);
            }

        }

        return false;
    }

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] max = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(max[i][j]==0){
                    maxIncreasingPath(matrix,max,i,j);
                }
            }
        }

        int res =1;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(max[i][j]>res){
                    res = max[i][j];
                }
            }
        }

        return res;
    }

    private int maxIncreasingPath(int[][] matrix,int[][] max,int x,int y){
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        int res =1;
        int m = matrix.length;
        int n = matrix[0].length;
        for(int[] dir:dirs){
            int i = x+dir[0];
            int j = y+dir[1];
            if(i<0||j<0||i>=m||j>=n||matrix[i][j]<=matrix[x][y]){
                continue;
            }

            if(max[i][j]==0){
                maxIncreasingPath(matrix,max,i,j);
            }
            res = Math.max(max[i][j]+1,res);
        }
        max[x][y] = res;
        return res;
    }


      public static class TreeNode {
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


  int sumRoot=0;
    public int sumRootToLeaf(TreeNode root) {
        sumRootToLeaf(root,0);
        return sumRoot;
    }


    public void sumRootToLeaf(TreeNode root,int pre) {
        if(root==null){
            return;
        }

        if(root.left==null&&root.right==null){
            sumRoot+=root.val+2*pre;
            return;
        }
         sumRootToLeaf(root.right,root.val+2*pre);
         sumRootToLeaf(root.right,root.val+2*pre);
    }

    public TreeNode invertTree(TreeNode root) {
        if(root==null){
            return root;
        }
        if(root.left==null&&root.right==null){
            return root;
        }
        TreeNode left = invertTree(root.right);
        TreeNode right = invertTree(root.left);
        root.left = right;
        root.right = left;
        return root;
    }

    public String minWindow(String s, String t) {
        if(t.length()>s.length()){
            return "";
        }
        String res = "";
        int n = t.length();

        Map<Character,Integer> map = new HashMap<>();
        for(char c:t.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }

        int start = 0;
        int count =0;

        Map<Character,Integer> record = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(!map.containsKey(c)){
                continue;
            }else{
                record.put(c,record.getOrDefault(c,0)+1);
                if(record.get(c)<=map.get(c)){
                    count++;
                }

            }

            if(count==t.length()){
                while(count==t.length()){
                    if(res.equals("")||res.length()>i-start+1){
                        res = s.substring(start,i+1);
                    }
                    char pre = s.charAt(start);
                    if(record.containsKey(pre)){
                        if(map.get(pre)>=record.get(pre)){
                            count--;
                        }
                        record.put(pre,record.get(pre)-1);
                    }
                    start++;
                }

            }


        }

        return res;

    }

    public int eraseOverlapIntervals(int[][] intervals) {
        //sort the interval by their start postion and end position
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((a,b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        for(int[] interval:intervals){
            queue.offer(interval);
        }

        int count =0;
        int[] first = queue.poll();
        int end = first[1];
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            if(cur[0]>=end){//not overlap, update the end point
                end = cur[1];
            }else{
                count++;
                //remove the interval with larger end position
                end = Math.min(end,cur[1]);
            }
        }
        return count;
    }


    public String decodeAtIndex(String s, int k) {
        int start =0;
        int len =0;

        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            int j =i;
            int count =0;

            if(Character.isDigit(c)){
                while(Character.isDigit(c)&&(len+(i-start)*count)<k){
                    count = Integer.valueOf(s.substring(i,j+1));
                    c = s.charAt(j+1);
                    j++;
                }

                if(len+(i-start)*count>=k){
                    int num = (k-len)%(i-start);
                    String str = s.substring(start,i+1);
                    if(num==0){
                        return ""+s.charAt(i-start-1);
                    }
                    return ""+str.charAt(num-1);
                }

                len+=(i-start)*count;
                start = j;
                i=j-1;
            }else if(len+i-start+1==k){
                return c+"";
            }

        }

        return null;
    }


    public int totalMoney(int n) {
        int m = n/7;

        int one =0;
        for(int i=1;i<=7;i++){
            one+=i;
        }

        int res =0;

        for(int i=0;i<m;i++){
            res+=one+i*7;
        }

        int lastOne = m+1;
        for(int i=0;i<n%7;i++){
            res+=lastOne+i;
        }

        return res;
    }

    public int threeSumSmaller(int[] nums,int target){
        int res =0;
        Arrays.sort(nums);
        int n = nums.length;
        for(int i=0;i<n-2;i++){
            int left = i+1;
            int right = n-1;
            while (left<right){
                if(nums[i]+nums[left]+nums[right]<target){
                    res+=right-left;
                    left++;
                }else {
                    right--;
                }
            }
        }
        return res;
    }

    public int slidingPuzzle(int[][] board) {
        String source = "";
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                source+=board[i][j];
            }
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(source);
        visited.add(source);

        int step =0;
        while(!queue.isEmpty()){
            int size = queue.size();
            step++;
            while(size!=0){
                size--;
                String cur = queue.poll();
                if(cur.equals("123450")){
                    return step-1;
                }

                int pos = cur.indexOf("0");
                if(pos>0){
                    char[] arr = cur.toCharArray();
                    char temp = arr[pos-1];
                    arr[pos-1] = '0';
                    arr[pos] = temp;
                    String next = new String(arr);
                    if(visited.add(next)){
                        queue.offer(next);
                    }
                }

                if(pos<5){
                    char[] arr = cur.toCharArray();
                    char temp = arr[pos+1];
                    arr[pos+1] = '0';
                    arr[pos] = temp;
                    String next = new String(arr);
                    if(visited.add(next)){
                        queue.offer(next);
                    }
                }

                if(pos<=2){
                    char[] arr = cur.toCharArray();
                    char temp = arr[pos+3];
                    arr[pos+3] = '0';
                    arr[pos] = temp;
                    String next = new String(arr);
                    if(visited.add(next)){
                        queue.offer(next);
                    }
                }

                if(pos>=3){
                    char[] arr = cur.toCharArray();
                    char temp = arr[pos-3];
                    arr[pos-3] = '0';
                    arr[pos] = temp;
                    String next = new String(arr);
                    if(visited.add(next)){
                        queue.offer(next);
                    }
                }


            }
        }

        return -1;

    }


    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        boolean has = false;
        StringBuilder sb = new StringBuilder();
        for(String s:source){
            if(s.trim().startsWith("//")){
                res.add(" ");
                continue;
            }

            if(s.contains("/*")){
                if(s.contains("*/")){
                    String pre = s.substring(0,s.indexOf("/*"));
                    String after = s.substring(s.indexOf("*/")+1);
                    sb.append(pre);
                    sb.append(after);
                    continue;
                }else{
                    String pre = s.substring(0,s.indexOf("/*"));
                    sb.append(pre);
                    has = true;
                    continue;
                }
            }

            if(!has){
                res.add(s);
            }else{
                if(s.contains("*/")){
                    String after = s.substring(s.indexOf("*/")+1);
                    sb.append(after);
                    if(sb.toString().length()>0){
                        res.add(sb.toString());
                    }
                    has = false;
                }

            }

        }

        return res;
    }

    public boolean circularArrayLoop(int[] nums) {
        for(int i=0;i<nums.length;i++){
            if(chackLoop(nums,i)){
                return true;
            }
        }

        return false;
    }

    private boolean chackLoop(int[] nums,int start){
        int n = nums.length;
        boolean[]  visited = new boolean[n];
        int step =1;
        int next=start;
        visited[start] = true;
        boolean positive= nums[start]>0 ? true:false;
        while(step<n){

            next =(next+nums[next]+n)%n;
            if((nums[next]>0)!=positive){
                return false;
            }

            if(visited[next]){
                if(step<=1){
                    return false;
                }else{
                    return true;
                }
            }
            step++;
            visited[next] = true;

        }

        return false;
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        Arrays.fill(left,1);

        for(int i=1;i<n;i++){
            if(ratings[i]>ratings[i-1]){
                left[i]=left[i-1]+1;
            }
        }

        int[] right = left;
        for(int i=n-2;i>=0;i--){
            if(ratings[i]>ratings[i+1]){
                if(right[i]<=right[i+1]){
                    right[i]=right[i+1]+1;
                }
            }
        }

        int res = 0;
        for(int val:right){
            res+=val;
        }

        return res;

    }


    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] grid = new int[n][n];
        for(int i=0;i<n;i++){
            Arrays.fill(grid[i],1);
        }
        for(int[] mine:mines){
            grid[mine[0]][mine[1]] = 0;
        }

        int row = n;
        int col = n;

        int[][] up = new int[row][col];
        int[][] down = new int[row][col];
        int[][] left = new int[row][col];
        int[][] right = new int[row][col];

        for(int i=0;i<col;i++){
            up[0][i] = grid[0][i];
        }

        for(int i=1;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j]==0){
                    up[i][j]=0;
                }else{
                    up[i][j] = 1+up[i-1][j];
                }
            }
        }

        for(int i=0;i<col;i++){
            down[row-1][i] = grid[row-1][i];
        }

        for(int i=row-2;i>=0;i--){
            for(int j=0;j<col;j++){
                if(grid[i][j]==0){
                    down[i][j]=0;
                }else{
                    down[i][j] = 1+down[i+1][j];
                }
            }
        }


        for(int i=0;i<row;i++){
            left[i][0] = grid[i][0];
        }

        for(int i=0;i<row;i++){
            for(int j=1;j<col;j++){
                if(grid[i][j]!=0){
                    left[i][j] = 1+left[i][j-1];
                }
            }
        }

        for(int i=0;i<row;i++){
            right[i][col-1] = grid[i][col-1];
        }

        for(int i=0;i<row;i++){
            for(int j=col-2;j>=0;j--){
                if(grid[i][j]!=0){
                    right[i][j] = 1+right[i][j+1];
                }
            }
        }

        int res =0;
        for(int i=0;i<row;i++){
            for(int j=0;j<row;j++){
                int l = Math.min(up[i][j],down[i][j]);
                int h = Math.min(left[i][j],right[i][j]);
                res = Math.max(res,Math.min(l,h));
            }
        }

        return res;

    }

    public int longestSubstring(String s, int k) {
        Map<Character,Integer> map = new HashMap<>();
        int res = 0;
        int start = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
            PriorityQueue<Map.Entry<Character,Integer>> queue = new PriorityQueue<Map.Entry<Character,Integer>>((a,b)->(a.getValue()-b.getValue()));

            queue.addAll(map.entrySet());

            while(!queue.isEmpty()&&queue.peek().getValue()>k){
                char pre = s.charAt(start);
                start++;
                if(map.get(pre)==1){
                    map.remove(pre);
                }else{
                    map.put(pre,map.get(pre)-1);
                }
                queue = new PriorityQueue<Map.Entry<Character,Integer>>((a,b)->(a.getValue()-b.getValue()));
                queue.addAll(map.entrySet());


            }

            res = Math.max(res,i-start+1);


        }

        return res;

    }

    public String validIPAddress(String queryIP) {
        if(queryIP==null||queryIP.length()==0){
            return "Neither";
        }

        if(queryIP.contains(".")){
            if(queryIP.startsWith(".")||queryIP.endsWith(".")){
                return "Neither";
            }
            String[] ip = queryIP.split("\\.");
            if(ip.length!=4){
                return "Neither";
            }

            for(String num:ip){

                try{
                    int value = Integer.valueOf(num);
                    if(value<0||value>=256){
                        return "Neither";
                    }

                }catch(Exception e){
                    return "Neither";
                }
            }

            return "IPv4";

        }else if(queryIP.contains(":")){
            if(queryIP.startsWith(":")||queryIP.endsWith(":")){
                return "Neither";
            }
            String[] ip = queryIP.split(":");
            if(ip.length!=8){
                return "Neither";
            }

            for(String part:ip){
                if(part.length()>4){
                    return "Neither";
                }
                for(char c:part.toCharArray()){
                    if(!Character.isLetterOrDigit(c)||Character.toUpperCase(c)>='G'){
                        return "Neither";
                    }


                }
            }

            return "IPv6";
        }

        return "Neither";
    }

    public String getAllString(String str){
        Stack<String> stack = new Stack<>();
        Stack<Integer> num = new Stack<>();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='('){
                if(Character.isDigit(str.charAt(i+1))){
                    int count = 0;
                    while (i+1<str.length()&&Character.isDigit(str.charAt(i+1))){
                        count=count*10+(str.charAt(i+1)-'0');
                        i++;
                    }
                    num.push(count);
                }else if(Character.isLetter(str.charAt(i+1))){
                    StringBuilder sb = new StringBuilder();
                    while (i+1<str.length()&&Character.isLetter(str.charAt(i+1))){
                        sb.append(str.charAt(i+1));
                        i++;
                    }
                    stack.push(sb.toString());
                }
            }else if(str.charAt(i)==')'){
                if(!stack.isEmpty()&&!num.isEmpty()){
                    stack.push(repeate(stack.pop(),num.pop()));
                }
            }
        }

        return stack.peek();
    }

    private String repeate(String str,int count){
        String res ="";
        while (count>0){
            res+=str;
            count--;
        }
        return res;
    }


    public boolean makesquare(int[] matchsticks) {
        long sum =0;
        for(int num:matchsticks){
            sum+=num;
        }

        if(sum%4!=0){
            return false;
        }
        Arrays.sort(matchsticks);
        long target = sum/4;
        return canfind(matchsticks,matchsticks.length-1,target,new int[4]);
    }


    private boolean canfind(int[] matchsticks, int index,long target,int[] size){
        if(index==-1){
            if(size[0]==size[1]&&size[1]==size[2]&&size[2]==size[3]){
                return true;
            }

            return false;
        }

        for(int i=0;i<size.length;i++){
            if(size[i]+matchsticks[index]>target||(i>0&&size[i-1]==size[i])){
                continue;
            }
            size[i]+=matchsticks[index];
            if(canfind(matchsticks,index-1,target,size)){
                return true;
            }
            size[i]-=matchsticks[index];
        }

        return false;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root==null){
            return res;
        }

        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while(cur.size()>0){
            List<TreeNode> next = new ArrayList<>();
            List<Integer> list = new ArrayList<>();
            for(TreeNode node:cur){
                list.add(node.val);
                if(node.left!=null){
                    next.add(node.left);
                }
                if(node.right!=null){
                    next.add(node.right);
                }
            }

            res.add(0,list);
            cur = next;
        }
        return res;
    }


        public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
            root.right.left = new TreeNode(15);
            root.right.right = new TreeNode(7);
        DataStructure test = new DataStructure();
//        test.circularArrayLoop(new int[]{-1,2});
//        test.circularArrayLoop(new int[]{2,-1,1,2,2});
//        test.circularArrayLoop(new int[]{2,1,1,2,2});
//        test.circularArrayLoop(new int[]{-1,-2,-3,-4,-5});
        test.levelOrderBottom(root);
    }

}
