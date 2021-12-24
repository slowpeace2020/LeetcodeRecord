package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import unlock.Codec.TreeNode;

public class BFSII {

  public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    List<List<String>> res = new ArrayList();

    Map<String, List<String>> nextWordMap = new HashMap<>();
    Map<String, Integer> distance = new HashMap<>();
    //bfs搜start--end的最短路径
    bfs(beginWord, endWord, nextWordMap, distance, wordList);

    //dfs输出距离最短的路径\
    List<String> tmp = new ArrayList<>();
    tmp.add(beginWord);
    //dfs输出距离最短的路径
    dfs(endWord, nextWordMap, distance, res, 0, tmp);

    return res;
  }


  private void bfs(String start, String end, Map<String, List<String>> nextWordMap,
      Map<String, Integer> distance, List<String> wordList) {
    for (String s : wordList) {
      nextWordMap.put(s, new ArrayList<>());
    }

    nextWordMap.put(start, new ArrayList<>());
    Queue<String> queue = new LinkedList<>();
    queue.offer(start);
    distance.put(start, 0);
    boolean flag = false;
    int step = 0;
    while (!queue.isEmpty()) {
      step++;
      int n = queue.size();
      //搜索下一跳
      for (int i = 0; i < n; i++) {
        String word = queue.poll();
        for (String next : getNext(word, wordList)) {
          nextWordMap.getOrDefault(word, new ArrayList<>()).add(next);
          if (next.equals(end)) {
            //当下一跳是end时，就可以结束搜索
            flag = true;
          }

          //如果没被添加过，则进行添加
          if (!distance.containsKey(next)) {
            distance.put(next, step);
            queue.offer(next);
          }
        }
      }

      if (flag) {
        break;
      }
    }
  }


  private void dfs(String end, Map<String, List<String>> nextWordMap, Map<String, Integer> distance,
      List<List<String>> res, int step, List<String> tmp) {
    if (tmp.size() >= 1 && tmp.get(tmp.size() - 1).equals(end)) {
      res.add(new ArrayList<>(tmp));
    }

    for (String word : nextWordMap.get(tmp.get(tmp.size() - 1))) {
      tmp.add(word);
      if (distance.get(word) == step + 1) {
        dfs(end, nextWordMap, distance, res, step + 1, tmp);
      }
      tmp.remove(word);
    }
  }

  public List<String> getNext(String source, List<String> wordList) {
    List<String> res = new ArrayList<>();
    for (int i = 0; i < source.length(); i++) {
      char[] chars = source.toCharArray();
      for (char c = 'a'; c <= 'z'; c++) {
        chars[i] = c;
        String tmp = new String(chars);
        if (tmp.equals(source) || !wordList.contains(tmp)) {
          continue;
        }
        res.add(tmp);
      }
    }

    return res;
  }

  public void dfsFindLadders(String beginWord, String endWord, List<String> wordList,Set<String> visited,List<List<String>> res,List<String> list){
    if(beginWord.equals(endWord)){
      res.add(new ArrayList<>(list));
      return;
    }

    visited.add(beginWord);
    list.add(beginWord);
    List<String> nextList = getNext(beginWord,wordList);
    for(String next:nextList){
      if(visited.contains(next)){
        continue;
      }
      dfsFindLadders(next,endWord,wordList,visited,res,list);
    }

  }




  public boolean isNext(String source, String target) {
    int n = source.length();
    int count = 0;
    if (source.equals(target)) {
      return false;
    }
    for (int i = 0; i < n; i++) {
      if (source.charAt(i) == target.charAt(i)) {
        continue;
      }
      count++;
      if (count > 1) {
        return false;
      }
    }

    return true;
  }

  public boolean validTree(int n, int[][] edges) {
    if (n == 0) {
      return false;
    }

    if (edges.length != n - 1) {
      return false;
    }

    Map<Integer, List<Integer>> graph = new HashMap<>();

    for (int[] edge : edges) {
      List<Integer> list1 = graph.getOrDefault(edge[0], new ArrayList<>());
      list1.add(edge[1]);

      List<Integer> list2 = graph.getOrDefault(edge[1], new ArrayList<>());
      list2.add(edge[0]);
    }

    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    queue.offer(0);
    visited.add(0);

    while (!queue.isEmpty()) {
      int cur = queue.poll();
      for (Integer next : graph.get(cur)) {
        if (visited.contains(next)) {
          return false;
        }
        queue.add(next);
      }
    }

    return visited.size() == n;

  }

  public int minLength(String s, Set<String> dict) {
    // write your code here

    int ans = Integer.MAX_VALUE;
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    queue.offer(s);
    visited.add(s);
    while (!queue.isEmpty()) {
      String cur = queue.poll();
      for (String rep : dict) {
        int pos = cur.indexOf(rep);
        while (pos != -1) {
          // next为删除下标pos开始的word之后的字符串
          String next = cur.substring(0, pos) + cur.substring(pos + rep.length());
          if (next.equals("")) {
            return 0;
          }

          // 如果这个字符串没有出现过，压入队列，更新答案
          if (visited.add(next)) {
            // 更新剩下的字符串的最短长度
            ans = Math.min(ans, next.length());
            queue.add(next);
          }

          // 寻找s中下标从pos+1开始到末尾第一次出现的word的首下标
          pos = cur.indexOf(rep, pos + 1);
        }

      }
    }

    return ans;
  }


  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode cur = root;

    while (!stack.isEmpty() || cur != null) {
      while (cur != null) {
        stack.push(cur);
        cur = cur.left;
      }

      if (cur == null) {
        cur = stack.pop();
        res.add(cur.val);
        cur = cur.right;
      }

    }

    return res;

  }

  public List<Integer> postorderTraversal(TreeNode root) {
    LinkedList<Integer> res = new LinkedList<>();
    if (root == null) {
      return res;
    }

    Stack<TreeNode> stack = new Stack();
    TreeNode cur = root;
    while (!stack.isEmpty() || cur != null) {
      while (cur != null) {
        stack.push(cur);
        res.addFirst(cur.val);
        cur = cur.right;
      }

      if (cur == null) {
        cur = stack.pop();
        cur = cur.left;
      }
    }
    return res;
  }


    public static void main(String[] args) {
    BFSII test = new BFSII();
    Set<String> dict = new HashSet<>();
    dict.add("ab");
    dict.add("abcd");
//    test.minLength("abcabd",dict);
    String[] strings = "abcabd".split("ab");
      System.out.println(strings);
  }



  }
