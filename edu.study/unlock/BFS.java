package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BFS {

  class DirectedGraphNode{
    int label;
       List<DirectedGraphNode> neighbors;
      DirectedGraphNode(int x) {
          label = x;
          neighbors = new ArrayList<DirectedGraphNode>();
     }
  }

  public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
    ArrayList<DirectedGraphNode> result = new ArrayList<>();
    HashMap<DirectedGraphNode, Integer> map = new HashMap<>();

    for(DirectedGraphNode node:graph){
      for(DirectedGraphNode neighbor:node.neighbors){
        if(!map.containsKey(neighbor)){
          map.put(neighbor,1);
        }else {
          map.put(neighbor,map.get(neighbor)+1);
        }
      }
    }

    int size = graph.size();
    for(DirectedGraphNode node:graph){
      if(!map.containsKey(node) && size!=result.size()){
        dfsTopSort(node,result,map);
      }
    }

    return result;

  }

  private void dfsTopSort(DirectedGraphNode node, ArrayList<DirectedGraphNode> result,
      HashMap<DirectedGraphNode, Integer> map) {
    result.add(node);
    if(node.neighbors.isEmpty())return;

    for (DirectedGraphNode next:node.neighbors){
      int degree = map.get(next)-1;
      map.put(next,degree-1);
      if(degree==0){
        dfsTopSort(next,result,map);
      }
    }
  }


  public ArrayList<DirectedGraphNode> topSortII(ArrayList<DirectedGraphNode> graph) {
    if(graph==null||graph.size()==0)return new ArrayList<>();

    LinkedList<DirectedGraphNode> result = new LinkedList<>();
    Set<DirectedGraphNode> gray = new HashSet<>();
    Set<DirectedGraphNode> dark = new HashSet<>();

    for(DirectedGraphNode node : graph){
      if(!gray.contains(node)&&!dark.contains(node)){
        dfsTopSortI(node,gray,dark,result);
      }
    }

    return new ArrayList<>(result);

  }

  private void dfsTopSortI(DirectedGraphNode node, Set<DirectedGraphNode> gray,
      Set<DirectedGraphNode> dark, LinkedList<DirectedGraphNode> result) {
    gray.add(node);
    for(DirectedGraphNode child:node.neighbors){
      if(!gray.contains(child)&&!dark.contains(child)){
        dfsTopSortI(node,gray,dark,result);
      }
    }

    dark.add(node);
    gray.remove(node);
    result.addFirst(node);
  }


  public ArrayList<DirectedGraphNode> topSortI(ArrayList<DirectedGraphNode> graph) {
    ArrayList<DirectedGraphNode> res = new ArrayList<>();
    Map<DirectedGraphNode, Integer> inDegree = new HashMap<>();
    for (DirectedGraphNode node : graph) {
      for (DirectedGraphNode neighbor : node.neighbors) {
        inDegree.putIfAbsent(node, 0);
        inDegree.put(neighbor, inDegree.get(neighbor) + 1);
      }
    }

    Queue<DirectedGraphNode> queue = new LinkedList<>();
    for (DirectedGraphNode node : inDegree.keySet()) {
      if (inDegree.get(node) == 0) {
        queue.offer(node);
      }
    }

    while (!queue.isEmpty()) {
      DirectedGraphNode node = queue.poll();
      res.add(node);
      for (DirectedGraphNode neighbor : node.neighbors) {
        inDegree.put(neighbor, inDegree.get(neighbor) - 1);
        if (inDegree.get(neighbor) == 0) {
          queue.offer(neighbor);
        }
      }
    }

    return res;
  }

  public boolean canFinishI(int numCourses, int[][] prerequisites) {
    Map<Integer,List<Integer>>  graph = new HashMap<>();
    Map<Integer, Integer>  inDegree = new HashMap<>();
    for(int[] pre:prerequisites){
      int first = pre[1];
      int second = pre[0];
      inDegree.putIfAbsent(second,0);
      inDegree.put(second,inDegree.get(second)+1);
      graph.putIfAbsent(first,new ArrayList<>());
      List<Integer> list = graph.get(first);
      list.add(second);
      graph.put(first,list);
    }

    Queue<Integer> queue = new LinkedList<>();
    List<Integer> res = new ArrayList<>();

    for(int i=0;i<numCourses;i++){
      if(!inDegree.containsKey(i)){
        queue.add(i);
      }
    }

    while (!queue.isEmpty()){
      int course = queue.poll();
      if(res.contains(course))continue;
      res.add(course);

      List<Integer> list = graph.getOrDefault(course,new ArrayList<>());
      for(Integer next:list){
        int degree = inDegree.get(next)-1;
        if(degree==0){
          queue.add(next);
          inDegree.remove(next);
        }else if(degree>0){
          inDegree.put(next,degree);
        }
      }

    }

    return res.size()==numCourses;
  }

  public boolean canFinish(int numCourses, int[][] prerequisites) {
    Map<Integer, List<Integer>> adjList = new HashMap<>();
    Set<Integer> visited = new HashSet<>();
    for (int i = 0; i < numCourses; i++) {
      adjList.put(i, new ArrayList<>());
    }
    for (int[] pre : prerequisites) {
      adjList.get(pre[0]).add(pre[1]);
    }
    for (int course = 0; course < numCourses; course++) {
      if (!dfs(course, adjList, visited)) {
        return false;
      }
    }
    return true;
  }

  public boolean dfs(int course, Map<Integer, List<Integer>> adjList, Set<Integer> visited) {
    if (visited.contains(course)) {
      return false;
    }

    if (adjList.get(course).size() == 0) {
      return true;
    }

    visited.add(course);
    List<Integer> preReqs = adjList.get(course);
    for (int pre : preReqs) {
      if (!dfs(pre, adjList, visited)) {
        return false;
      }
    }
    visited.remove(course);
    adjList.get(course).clear();
    return true;
  }


  public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>(numCourses);
    for (int i = 0; i < numCourses; i++) {
      adj.add(i, new ArrayList<>());
    }

    for (int i = 0; i < prerequisites.length; i++) {
      adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
    }

    boolean[] visited = new boolean[numCourses];
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < numCourses; i++) {
      if (!topologicalSort(adj, i, stack, visited, new boolean[numCourses])) {
        return new int[0];
      }
    }

    int i = 0;
    int[] result = new int[numCourses];
    while (!stack.isEmpty()) {
      result[i++] = stack.pop();
    }

    return result;

  }


  private boolean topologicalSort(List<List<Integer>> adj, int course, Stack<Integer> stack,
      boolean[] visited, boolean[] isLoop) {
    if (visited[course]) {
      return true;
    }
    if (isLoop[course]) {
      return false;
    }

    isLoop[course] = true;
    for (Integer u : adj.get(course)) {
      if (!topologicalSort(adj, u, stack, visited, isLoop)) {
        return false;
      }
    }

    visited[course] = true;
    stack.push(course);
    return true;
  }




  public int[] findOrderI(int numCourses, int[][] prerequisites) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> inDegree = new HashMap<>();
    for (int[] pre : prerequisites) {
      int first = pre[1];
      int second = pre[0];
      inDegree.putIfAbsent(second, 0);
      inDegree.put(second, inDegree.get(second) + 1);
      graph.putIfAbsent(first, new ArrayList<>());
      List<Integer> list = graph.get(first);
      list.add(second);
      graph.put(first, list);
    }

    Queue<Integer> queue = new LinkedList<>();
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i < numCourses; i++) {
      if (!inDegree.containsKey(i)) {
        queue.add(i);
      }
    }

    while (!queue.isEmpty()) {
      int course = queue.poll();
      if (res.contains(course)) {
        continue;
      }
      res.add(course);

      List<Integer> list = graph.getOrDefault(course, new ArrayList<>());
      for (Integer next : list) {
        int degree = inDegree.get(next) - 1;
        if (degree == 0) {
          queue.add(next);
          inDegree.remove(next);
        } else if (degree > 0) {
          inDegree.put(next, degree);
        }
      }

    }

    int[] result = new int[numCourses];

    if (res.size() == numCourses) {
      for (int i = 0; i < res.size(); i++) {
        result[i] = res.get(i);
      }
    } else {
      result = new int[]{};
    }

    return result;
  }

  public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    int[] visited = new int[26];

    buildgraph(words, graph);
    StringBuilder sb = new StringBuilder();
    for (char c : graph.keySet()) {
      if (!dfsAlienOrder(c, sb, graph, visited)) {
        return "";
      }
    }

    return sb.toString();

  }

  public void buildgraph(String[] words, Map<Character, Set<Character>> graph) {
    for (String word : words) {
      for (Character c : word.toCharArray()) {
        graph.put(c, new HashSet<>());
      }
    }

    for (int i = 0; i < words.length - 1; i++) {
      String first = words[i];
      String second = words[i + 1];

      int length = Math.min(first.length(), second.length());
      for (int j = 0; j < length; j++) {
        char parent = first.charAt(j);
        char child = second.charAt(j);
        if (parent != child) {
          if (!graph.get(first).contains(child)) {
            graph.get(parent).add(child);
            break;
          }
        }
      }
    }

  }

  public boolean dfsAlienOrder(Character c, StringBuilder sb, Map<Character, Set<Character>> graph,
      int[] visited) {
    if (visited[c - 'a'] == 1) {
      return true;
    }
    if (visited[c - 'a'] == -1) {
      return false;
    }

    visited[c - 'a'] = -1;

    for (char next : graph.get(c)) {
      if (!dfsAlienOrder(next, sb, graph, visited)) {
        return false;
      }
    }

    visited[c - 'a'] = 1;
    sb.insert(0, c);
    return true;
  }


  public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    Map<Integer, Integer> inDegree = new HashMap<>();

    for (List<Integer> list : seqs) {
      for (Integer element : list) {
        map.putIfAbsent(element, new HashSet<>());
        inDegree.putIfAbsent(element, 0);
      }
    }

    for (List<Integer> seq : seqs) {
      if (seq.size() < 2) {
        continue;
      }

      for (int i = 0; i < seq.size() - 1; i++) {
        if (map.get(seq.get(i)).add(seq.get(i + 1))) {
          inDegree.put(seq.get(i + 1), inDegree.get(seq.get(i + 1)) + 1);
        }
      }
    }

    if (org.length != inDegree.size()) {
      return false;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int num : inDegree.keySet()) {
      if (inDegree.get(num) == 0) {
        queue.offer(num);
      }
    }

    int count = 0;
    while (!queue.isEmpty()) {
      if (queue.size() > 1) {
        return false;
      }

      int num = queue.poll();
      count++;
      for (int next : map.get(num)) {
        inDegree.put(next, inDegree.get(next) - 1);
        if (inDegree.get(next) == 0) {
          queue.add(next);
        }
      }
    }

    return count == org.length;

  }

  public int numIslands(char[][] grid) {
    int res = 0;
    int m = grid.length;
    int n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(visited[i][j]||grid[i][j]=='0'){
          continue;
        }

        res++;
        helper(grid,visited,i,j);
      }
    }

    return res;

  }

  public void helper(char[][] grid, boolean[][] visited , int x,int y){
    int m = grid.length;
    int n = grid[0].length;
    if(x<0||y<0||x>=m||y>=n){
      return;
    }
    if(grid[x][y]=='0'||visited[x][y]){
      return;
    }

    visited[x][y] = true;

    helper(grid,visited,x+1,y);
    helper(grid,visited,x-1,y);
    helper(grid,visited,x,y+1);
    helper(grid,visited,x,y-1);

  }


  public boolean dfsHasPath(int[][] maze, int[] start, int[] destination) {
    int m = maze.length;
    int n = maze[0].length;
    int x = start[0];
    int y = start[1];
    if (x == destination[0] && y == destination[1]) {
      return true;
    }

    if (x + 1 < m && maze[x + 1][y] == 0) {
      maze[x + 1][y] = 1;
      if (dfsHasPath(maze, new int[]{x + 1, y}, destination)) {
        return true;
      }
    }
    if (x - 1 >= 0 && maze[x - 1][y] == 0) {
      maze[x - 1][y] = 1;
      if (dfsHasPath(maze, new int[]{x - 1, y}, destination)) {
        return true;
      }
    }

    if (y + 1 < n && maze[x][y + 1] == 0) {
      maze[x][y + 1] = 1;
      if (dfsHasPath(maze, new int[]{x, y + 1}, destination)) {
        return true;
      }
    }
    if (y - 1 >= 0 && maze[x][y - 1] == 0) {
      maze[x][y - 1] = 1;
      if (dfsHasPath(maze, new int[]{x, y - 1}, destination)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasPath(int[][] maze, int[] start, int[] destination) {
    Queue<Integer> queue = new LinkedList<>();
    int m = maze.length;
    int n = maze[0].length;
    queue.add(start[0]*n+start[1]);
    maze[start[0]][start[1]] = 1;
    while (!queue.isEmpty()){
        int value = queue.poll();
        int x  = value/n;
        int y = value%n;
        if(x==destination[0]&&y==destination[1]){
          return true;
        }
        if(x+1<m&&maze[x+1][y]==0){
          maze[x+1][y]=1;
          queue.add((x+1)*n+y);
        }
      if(x-1>=0&&maze[x-1][y]==0){
        maze[x-1][y]=1;
        queue.add((x-1)*n+y);
      }

      if(y+1<n&&maze[x][y+1]==0){
        maze[x][y+1]=1;
        queue.add(x*n+y+1);
      }

      if(y-1>=0&&maze[x][y-1]==0){
        maze[x][y-1]=1;
        queue.add(x*n+y-1);
      }
    }

    return false;
  }


  public int shortestDistance(int[][] maze, int[] start, int[] destination) {
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> (a[2] - b[2]));

    queue.add(new int[]{start[0], start[1], 0});
    int m = maze.length;
    int n = maze[0].length;
    while (!queue.isEmpty()) {
      int[] cur = queue.poll();
      int x = cur[0];
      int y = cur[1];
      int count = cur[2];
      maze[x][y] = -1;
      if (x == destination[0] && y == destination[1]) {
        return cur[2];
      }

      for (int[] dir : dirs) {
        int nextX = x;
        int nextY = y;
        int nextCount = count;
        while (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && maze[x][y] == 0) {
          nextX += dir[0];
          nextY += dir[1];
          nextCount++;
        }
        nextX -= dir[0];
        nextY -= dir[1];
        nextCount--;
        if (maze[nextX][nextY] != -1) {
          queue.add(new int[]{nextX, nextY, nextCount});
        }
      }
    }

    return -1;
  }


  public int[][] updateMatrix(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;

    int[][] res = new int[m][n];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (mat[i][j] == 0) {
          res[i][j] = 0;
          queue.add(new int[]{i, j});
        } else {
          res[i][j] = -1;
        }
      }
    }

    while (!queue.isEmpty()) {
      int[] cur = queue.poll();
      int x = cur[0];
      int y = cur[1];

      if (x + 1 < m && res[x + 1][y] == -1) {
        res[x + 1][y] = res[x][y] + 1;
        queue.add(new int[]{x + 1, y});
      }

      if (x - 1 >= 0 && res[x - 1][y] == -1) {
        res[x - 1][y] = res[x][y] + 1;
        queue.add(new int[]{x - 1, y});
      }

      if (y + 1 < n && res[x][y + 1] == -1) {
        res[x][y + 1] = res[x][y] + 1;
        queue.add(new int[]{x, y + 1});
      }

      if (y - 1 >= 0 && res[x][y - 1] == -1) {
        res[x][y - 1] = res[x][y] + 1;
        queue.add(new int[]{x, y - 1});
      }

    }

    return res;
  }


  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    int m = image.length;
    int n = image[0].length;
    boolean[][] visited = new boolean[m][n];
    int pixl = image[sr][sc];
    dfsFillColor(image, sr, sc, pixl, newColor, visited);
    return image;

  }

  public void dfsFillColor(int[][] image, int sr, int sc, int pixl, int newColor,
      boolean[][] visited) {
    int m = image.length;
    int n = image[0].length;
    if (sr < 0 || sc < 0 || sr >= m || sc >= n || visited[sr][sc]) {
      return;
    }

    visited[sr][sc] = true;
    if (image[sr][sc] == pixl) {
      image[sr][sc] = newColor;
      dfsFillColor(image, sr + 1, sc, pixl, newColor, visited);
      dfsFillColor(image, sr - 1, sc, pixl, newColor, visited);
      dfsFillColor(image, sr, sc - 1, pixl, newColor, visited);
      dfsFillColor(image, sr, sc + 1, pixl, newColor, visited);
    }


  }


  public int orangesRotting(int[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }
    int rows = grid.length;
    int cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int count_fresh = 0;
    //Put the position of all rotten oranges in queue
    //count the number of fresh oranges
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid[i][j] == 2) {
          queue.offer(new int[]{i, j});
        } else if (grid[i][j] == 1) {
          count_fresh++;
        }
      }
    }
    //if count of fresh oranges is zero --> return 0
    if (count_fresh == 0) {
      return 0;
    }
    int count = 0;
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    //bfs starting from initially rotten oranges
    while (!queue.isEmpty()) {
      ++count;
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        int[] point = queue.poll();
        for (int dir[] : dirs) {
          int x = point[0] + dir[0];
          int y = point[1] + dir[1];
          //if x or y is out of bound
          //or the orange at (x , y) is already rotten
          //or the cell at (x , y) is empty
          //we do nothing
          if (x < 0 || y < 0 || x >= rows || y >= cols || grid[x][y] == 0 || grid[x][y] == 2) {
            continue;
          }
          //mark the orange at (x , y) as rotten
          grid[x][y] = 2;
          //put the new rotten orange at (x , y) in queue
          queue.offer(new int[]{x, y});
          //decrease the count of fresh oranges by 1
          count_fresh--;
        }
      }
    }
    return count_fresh == 0 ? count - 1 : -1;
  }


  public List<Integer> numIslands2(int m, int n, int[][] positions) {
    int[][] dirs = new int[][]{{0,1},{-1,0},{1,0},{0,-1}};
    int[] roots = new int[m*n];
    Arrays.fill(roots,-1);

    List<Integer> res = new ArrayList<>();
    int count = 0;
    for(int[] pos:positions){
      int x = pos[0];
      int y = pos[1];
      count++;
      pos[x*n+y]=x*n+y;
      for(int[] dir:dirs){
        int i = x+dir[0];
        int j= y+dir[1];
        if(i<0||i>=m||j<0||j>=n||roots[i*n+j]==-1){
          continue;
        }

        int root = findRoot(roots,i*n+j);
        if(root!=x*n+y){
          roots[x*n+y] = root;
          count--;
        }
      }

      res.add(count);
    }

    return res;

  }


  public int slidingPuzzle(int[][] board) {
    String source = matrixToString(board);
    String target = "123450";
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();

    queue.offer(source);
    visited.add(source);
    int count = 0;
    while (!queue.isEmpty()) {
      int n = queue.size();
      count++;
      while (n > 0) {
        String cur = queue.poll();
        if (cur.equals(target)) {
          return count;
        }

        List<String> list = getNext(cur);
        for (String next : list) {
          if (visited.contains(next)) {
            continue;
          }

          queue.add(next);
        }
      }
    }

    return -1;
  }

  public String matrixToString(int[][] board) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        sb.append(board[i][j]);
      }
    }

    return sb.toString();
  }

  public List<String> getNext(String cur) {
    List<String> res = new ArrayList<>();
    int zeroIndex = cur.indexOf('0');
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int x = zeroIndex / 3;
    int y = zeroIndex % 3;

    for (int[] dir : dirs) {
      int i = x + dir[0];
      int j = y + dir[1];
      if (i < 0 || i > 1 || j < 0 || j > 2) {
        continue;
      }
      char[] chars = cur.toCharArray();
      chars[zeroIndex] = chars[i * 3 + j];
      chars[i * 3 + j] = '0';
      res.add(new String(chars));
    }

    return res;
  }

    public int findRoot(int[] roots,int node){
    while (node!=roots[node]){
      node=roots[node];
    }
    return node;
  }


  public int zombie(int[][] grid) {
    // write your code here
    int human = 0;
    int m = grid.length;
    int n = grid[0].length;

    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 0) {
          human++;
        } else if (grid[i][j] == 1) {
          queue.add(new int[]{i, j});
        }
      }
    }

    int count = 0;
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    while (!queue.isEmpty()) {
      ++count;
      int size = queue.size();
      while (size > 0) {
        size--;
        int[] cur = queue.poll();
        int x = cur[0];
        int y = cur[1];
        for (int[] dir : dirs) {
          int i = x + dir[0];
          int j = y + dir[1];
          if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != 0) {
            continue;
          }
          grid[i][j] = 1;
          queue.offer(new int[]{i, j});
          human--;
        }
      }
    }

    return human == 0 ? count - 1 : -1;

  }


  public int minMoveStep(int[][] init_state, int[][] final_state) {
    // # write your code here
    String initial = switchToString(init_state);
    String target = switchToString(final_state);

    int m = init_state.length;
    int n = init_state[0].length;
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();
    queue.add(initial);
    visited.add(initial);

    int step = 0;

    while(!queue.isEmpty()){
      step++;
      int size=queue.size();
      while(size>0){
        size--;
        String cur = queue.poll();
        if(cur.equals(target)){
          return step-1;
        }
        List<String> list = getNext(cur,m,n);
        for(String next:list){
          if(visited.add(next)){
            queue.add(next);
          }
        }
      }

    }
    return -1;
  }

  public String switchToString(int[][] state){
    StringBuilder sb = new StringBuilder();
    int m = state.length;
    int n = state[0].length;
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        sb.append(state[i][j]);
      }
    }

    return sb.toString();
  }

  public List<String> getNext(String source,int m,int n){
    int zeroIndex = source.indexOf("0");
    int x = zeroIndex/n;
    int y = zeroIndex%n;
    List<String> res= new ArrayList<>();
    int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    for(int[] dir:dirs){
      int i = x+dir[0];
      int j = y+dir[1];
      if(i<0||i>=m||j<0||j>=n){
        continue;
      }
      int index = i*n+j;
      char[] chars = source.toCharArray();
      chars[zeroIndex] = chars[index];
      chars[index] = '0';
      res.add(new String(chars));
    }

    return res;
  }

    public static void main(String[] args) {
    int num = 2;
     int[][] pres = new int[][]{{0,0,0},{0,1,0},{1,1,1}};
     BFS test = new BFS();

     test.updateMatrix(pres);

  }



}
