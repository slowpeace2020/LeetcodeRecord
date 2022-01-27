package winter;

import java.util.*;

public class UniqueCopy {
    class TreeNode{
        int val;
        TreeNode left,right;

        public TreeNode(int val){
            this.val = val;
        }

    }
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode>[] dp = new List[n+1];
        dp[0]= new ArrayList<>();
        dp[0].add(null);
        for(int i=1;i<=n;i++){
            dp[i] = new ArrayList<>();
            for(int root=0;root<i;root++){
                TreeNode cur = new TreeNode(root);
                List<TreeNode> leftList = dp[root];
                List<TreeNode> rightlist =new ArrayList<>();
                for(TreeNode node:dp[i-root-1]){
                    rightlist.add(clone(node,root+1));
                }

                for(TreeNode left:leftList){
                    for(TreeNode right:rightlist){
                        cur.left = left;
                        cur.right = right;
                        dp[i].add(cur);
                    }
                }

            }
        }

        return dp[n];


    }

    public TreeNode clone(TreeNode root, int diff){
        if(root==null){
            return null;
        }

        if(root.left==null&&root.right==null){
            return new TreeNode(root.val+diff);
        }

        TreeNode copy = new TreeNode(root.val+diff);
        copy.left = clone(root.left,diff);
        copy.right = clone(root.right,diff);
        return copy;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();

        Map<String,Integer> distance = new HashMap<>();
        bfsLadders(beginWord,endWord,wordList,distance);
        if(!distance.containsKey(endWord)){
            return res;
        }
        path.add(beginWord);
        dfsTrack(beginWord,endWord,wordList,distance,res,path);
        return res;
    }


    private void bfsLadders(String beginWord, String endWord, List<String> wordList, Map<String,Integer> distance){
        distance.put(beginWord,0);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        boolean find = false;
        int step=0;
        while (!queue.isEmpty()){
            int size = queue.size();
            step++;
            while (size>0){
                size--;
                String cur = queue.poll();
                if(cur.equals(endWord)){
                    find = true;
                }
                List<String> nextList = getNextWord(cur,wordList);
                for(String next:nextList){
                    if(distance.containsKey(next)){
                        continue;
                    }
                    distance.put(next,step);
                    queue.offer(next);
                }
            }

            if(find){
                break;
            }

        }
    }

    public void dfsTrack(String beginWord, String endWord, List<String> wordList,Map<String,Integer> distance,List<List<String>> res,List<String> path){

        if(beginWord.equals(endWord)){

            if(res.size()==0||res.get(0).size()==path.size()){
                res.add(new ArrayList<>(path));
            }else if(res.get(0).size()>path.size()){
                res.clear();
                res.add(new ArrayList<>(path));
            }
            return;
        }

        if(res.size()!=0) {
            if (res.get(0).size() < path.size()) {
                return;
            }
        }

        List<String> nextList = getNextWord(beginWord,wordList);
        for(String next:nextList){
            if(path.contains(next)){
                continue;
            }

            if(path.size()==distance.getOrDefault(next,0)){
                path.add(next);
                dfsTrack(next,endWord,wordList,distance,res,path);
                path.remove(next);
            }

        }
    }


    public List<String> getNextWord(String word,List<String> wordList){
        List<String> res = new ArrayList<>();
        for(int i=0;i<word.length();i++){
            char[] chars = word.toCharArray();
            for(int j=0;j<26;j++){
                if((char)(j+'a')==word.charAt(i)){
                    continue;
                }
                chars[i] = (char)(j+'a');
                if(wordList.contains(new String(chars))&&!res.contains(new String(chars))){
                    res.add(new String(chars));
                }
            }
        }

        return res;
    }

    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length;
        int n = board[0].length;
        List<String> res = new ArrayList<>();
        for(String word:words){
            for(int i=0;i<m;i++){
                boolean find = false;
                for(int j=0;j<n;j++){
                    if(board[i][j]!=word.charAt(0)){
                        continue;
                    }
                    boolean[][] visited = new boolean[m][n];
                    visited[i][j] = true;
                    if(findWord(board,word.substring(1),visited,i,j)){
                        res.add(word);
                        find = true;
                        break;
                    }

                }

                if(find){
                    break;
                }
            }
        }

        return res;

    }

    private boolean findWord(char[][] board,String target,boolean[][] visited, int x,int y){

        if(target.equals("")){
            return true;
        }

        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

        int m = board.length;
        int n = board[0].length;

        for(int[] dir:dirs){
            int i = x+dir[0];
            int j = y+dir[1];
            if(i<0||j<0||i>=m||j>=n||visited[i][j]||target.charAt(0)!=board[i][j]){
                continue;
            }

            visited[i][j] = true;
            if(findWord(board,target.substring(1),visited,i,j)){
                return true;
            }

            visited[i][j] = false;
        }

        return false;

    }

    public static void main(String[] args) {
        UniqueCopy te = new UniqueCopy();
        te.findLadders("hit","cog", Arrays.asList(new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
    }
}
