package winter;


import java.util.*;

public class StreamChecker {
    class Trie{
        Trie[] child;
        String word;
        int count;
        public Trie(){
            child = new Trie[26];
            count =0;
        }
    }
    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        List<Integer> res = new ArrayList<>();
        Trie root = new Trie();
        for(String word:words){
            char[] cs = word.toCharArray();
            Arrays.sort(cs);
            StringBuffer sb = new StringBuffer();
            sb.append(cs[0]);
            for(int i=1;i<cs.length;i++){
                if(cs[i]!=cs[i-1]){
                    sb.append(cs);
                }
            }

            addWord(sb.toString(),root);
        }

        for(String word:puzzles){
            Trie cur = root;
            res.add(search(word,cur,'a'));
        }

        return res;
    }

    private Integer search(String word, Trie cur, char start) {
        int count =0;
        if(cur.word!=null&&cur.word.indexOf(word.charAt(0))!=-1){
            count+=cur.count;
        }

        for(char c = start;c<='z';c++){
            if(cur.child[c-'a']!=null&&word.charAt(c)!=-1){
                count+=search(word,cur.child[c-'a'],(char)(c+1));
            }
        }

        return count;
    }

    private void addWord(String word, Trie root) {
            Trie cur = root;
            for(int i=0;i<word.length();i++){
                int j=word.charAt(i)-'a';
                if(cur.child[j]==null){
                    cur.child[j] = new Trie();
                }
                cur = cur.child[j];
            }
            cur.word = word;
            cur.count++;
    }

    int high;
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if(n==1){
            res.add(0);
            return res;
        }
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int[] edge:edges){
            int one = edge[0];
            int two = edge[1];
            map.putIfAbsent(one,new ArrayList<>());
            map.putIfAbsent(two,new ArrayList<>());
            map.get(one).add(two);
            map.get(two).add(one);
        }


        int ans =Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            boolean[] visietd = new boolean[n];
            Queue<Integer> queue = new LinkedList<>();
            visietd[i] = true;
            queue.offer(i);
            int steps = 0;
            while (!queue.isEmpty()){
                int size = queue.size();
                steps++;
                while (size>0){
                    size--;
                    int cur = queue.poll();
                    for(Integer next:map.getOrDefault(cur,new ArrayList<>())){
                        if(visietd[next]){
                            continue;
                        }
                        visietd[next] = true;
                        queue.offer(next);
                    }
                }
            }

            if(steps<ans){
                res = new ArrayList<>();
                res.add(i);
                ans = steps;
            }else if(steps==ans){
                res.add(i);
            }
        }


        return res;
    }
    public List<Integer> findMinHeightTreesI(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if(n==1){
            res.add(0);
            return res;
        }
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int[] edge:edges){
            int one = edge[0];
            int two = edge[1];
            map.putIfAbsent(one,new ArrayList<>());
            map.putIfAbsent(two,new ArrayList<>());
            map.get(one).add(two);
            map.get(two).add(one);
        }


        int high = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            boolean[] visited = new boolean[n];
            visited[i] = true;
            int h = dfsHighet(map,i,visited);
            if(h<high){
                res = new ArrayList<>();
                res.add(i);
                high = h;
            }else if(h==high){
                res.add(i);
            }
        }


        return res;

    }


    public int dfsHighet(Map<Integer,List<Integer>> map, int source, boolean[] visited){

        int res = 1;
        for(int next:map.get(source)){
            if(visited[next]){
                continue;
            }
            visited[next] = true;
            int len = 1+dfsHighet(map,next,visited);
            System.out.println(len);
            res=Math.max(res,len);
            if(res>high){
                break;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        StreamChecker sc = new StreamChecker();
       String[] words = new String[]{"aaaa","asas","able","ability","actt","actor","access"};
        String[] puzzles =  new String[]{"aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"};
        sc.findMinHeightTrees(6,new int[][]{{3,0},{3,1},{3,2},{3,4},{5,4}});
    }
}
