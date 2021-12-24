package unlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BinaryTree {

  class TreeNode {

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

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return buildTree(preorder, 0, inorder.length - 1, inorder, 0, inorder.length - 1);
  }

  public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
    return buildTree(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
  }

  public TreeNode buildTree(int[] preorder, int prestart, int preend, int[] postorder,
      int poststart, int postend) {
    if (prestart > preend || poststart > postend) {
      return null;
    }

    TreeNode root = new TreeNode(preorder[prestart]);
    if (prestart < preend) {
      int left = preorder[prestart + 1];
      int index = poststart;
      for (int i = poststart; i <= postend; i++) {
        if (left == postorder[i]) {
          index = i;
          break;
        }
      }

      int leftLen = index - poststart + 1;
      int rightLen = postend - index - 1;
      if (leftLen > 0) {
        root.left = buildTree(preorder, prestart + 1, prestart + leftLen, postorder, poststart,
            index);
        root.right = buildTree(preorder, prestart + leftLen + 1, preend, postorder, index + 1,
            postend - 1);
      }


    }

    return root;
  }


  public TreeNode buildTreeII(int[] inorder, int instart, int inend, int[] postorder, int poststart,
      int postend) {
    if (instart > inend || poststart > postend) {
      return null;
    }
    if (instart < 0 || instart >= inorder.length || poststart < 0
        || poststart >= postorder.length) {
      return null;
    }

    TreeNode root = new TreeNode(postorder[postend]);
    int index = instart;
    for (int i = instart; i <= inend; i++) {
      if (inorder[i] == root.val) {
        index = i;
        break;
      }
    }

    int leftLen = index - instart;
    int rightLen = inend - index;
    if (leftLen > 0) {
      root.left = buildTree(inorder, instart, index - 1, postorder, poststart,
          poststart + leftLen - 1);
    }
    if (rightLen > 0) {
      root.right = buildTree(inorder, index + 1, inend, postorder, poststart + leftLen,
          postend - 1);
    }
    return root;

  }

  public TreeNode buildTreeI(int[] preorder, int prestart, int preend, int[] inorder, int instart,
      int inend) {
    if (prestart < 0 || prestart >= inorder.length) {
      return null;
    }

    if (prestart > preend || instart > inend) {
      return null;
    }
    TreeNode root = new TreeNode(preorder[prestart]);
    int index = instart;
    for (int i = instart; i <= inend; i++) {
      if (inorder[i] == preorder[prestart]) {
        index = i;
        break;
      }
    }

    int leftLength = index - instart;

    root.left = buildTree(preorder, prestart + 1, prestart + leftLength, inorder, instart,
        index - 1);
    root.right = buildTree(preorder, prestart + leftLength + 1, preend, inorder, index + 1, inend);
    return root;
  }

  public int kthSmallest(TreeNode root, int k) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode cur = root;
    while (!stack.isEmpty() || cur != null) {
      while (cur != null) {
        stack.push(cur);
        cur = cur.left;
      }
      if (cur == null) {
        k--;
        cur = stack.pop();
        if (k == 0) {
          return cur.val;
        }
        cur = cur.right;
      }
    }

    return -1;
  }

  public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    if (root == null || p == null) {
      return null;
    }

    //如果根节点小于或等于要查找的节点, 直接进入右子树递归
    if (root.val < p.val) {
      return inorderSuccessor(root.right, p);
    } else {
      //如果根节点大于要查找的节点, 则暂存左子树递归查找的结果,
      //如果是 null, 则直接返回当前根节点; 反之返回左子树递归查找的结果.
      TreeNode left = inorderSuccessor(root.left, p);
      //暂存左子树递归查找的结果就相当于循环实现中维护的祖先节点.
      return left == null ? root : left;
    }
  }


  public int closestValue(TreeNode root, double target) {
    TreeNode child = root.val > target ? root.left : root.right;
    if (child == null) {
      return root.val;
    }

    int childValue = closestValue(child, target);
    return Math.abs(root.val - target) > Math.abs(childValue - target) ? childValue : root.val;
  }

  public List<Integer> closestKValues(TreeNode root, double target, int k) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }

    dfsClosestKValues(root, target, k, res);
    return res;
  }

  public void dfsClosestKValues(TreeNode root, double target, int k, List<Integer> res) {
    if (root == null) {
      return;
    }

    dfsClosestKValues(root.left, target, k, res);
    if (res.size() < k) {
      res.add(root.val);
    } else if (Math.abs(res.get(0) - target) > Math.abs(root.val - target)) {
      res.remove(0);
      res.add(root.val);
    }

    dfsClosestKValues(root.right, target, k, res);
  }



  class Node{
    public int val;
    public Node left;
    public Node right;
    public Node parent;
  }

  public Node inorderSuccessor(Node x) {
    if (x.right != null) {
      Node successor = x.right;
      while (successor.left != null) {
        successor = successor.left;
      }
      return successor;
    } else {

      while (x != null) {
        if (x.parent == null) {
          return null;
        }
        if (x == x.parent.left) {
          return x.parent;
        }
        x = x.parent;
      }

    }

    return null;
  }

  public TreeNode inorderPredecessor(TreeNode root, TreeNode p) {
    // write your code here
    if (root == null) {
      return null;
    }
    TreeNode predecessor = null;
    while (root != null) {
      if (root.val >= p.val) {
        root = root.left;//roo》=p不可能右答案，放心往左走

      } else {
        //一旦找到比p大的值开始了，那肯定是第一个左子节点，所以一直往右走
        if (predecessor == null || predecessor.val < root.val) {
          predecessor = root;
        }

        root = root.right;
      }

    }

    return predecessor;
  }


  public boolean isValidBST(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    TreeNode pre = null;
    while (current != null || !stack.isEmpty()) {
      if (current != null) {
        stack.add(current);
        current = current.left;
      } else {
        TreeNode node = stack.pop();
        if (pre == null) {
          pre = node;
        } else if (pre.val >= node.val) {
          return false;
        } else {
          pre = node;
        }
        current = node.right;

      }
    }

    return true;
  }

  public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
      return true;
    }

    if (p == null) {
      return false;
    }

    if (q == null) {
      return false;
    }

    if (p.val != q.val) {
      return false;
    }

    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);

  }


  public boolean isSymmetric(TreeNode root) {
    if (root == null) {
      return true;
    }
    return isSymmetric(root.left, root.right);
  }

  public boolean isSymmetric(TreeNode left, TreeNode right) {
    if (left == null && right == null) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }
    System.out.println();
    if (left.val != right.val) {
      return false;
    }

    return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
  }

  boolean isBalanced = true;
  public boolean isBalanced(TreeNode root) {
    if (root == null) {
      return true;
    }
    int left = getHeightByDFS(root);

    return isBalanced;
  }

  public int getHeightByDFS(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int left = getHeightByDFS(root.left);
    int right = getHeightByDFS(root.right);
    if(Math.abs(left-right)>=2){
      isBalanced = false;
    }

    return 1 + Math.max(left, right);
  }


  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (root.left == null && root.right == null) {
      return 1;
    }
    if (root.left == null) {
      return minHeight(root.right,1);
    }

    if (root.right == null) {
      return minHeight(root.left,1);
    }

    return Math.min(minHeight(root.left,1),minHeight(root.right,1));
  }

  public int minHeight(TreeNode root,int depth) {
    if(root==null){
      return depth;
    }

    if(root.left==null){
      return minHeight(root.right,depth+1);
    }else if(root.right==null){
      return minHeight(root.left,depth+1);
    }

    return Math.min(minHeight(root.left,depth+1),minHeight(root.right,depth+1));
  }


  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }

    if (root.left == null && root.right == null) {
      return 1;
    }

    if (root.left == null) {
      return 1 + maxDepth(root.right);
    }

    if (root.right == null) {
      return 1 + maxDepth(root.left);
    }

    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
  }

  public int largestBSTSubtree(TreeNode root) {
    if (root == null) {
      return 0;
    }

    if (isValidBalanceTree(root, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
      return countNum(root);
    }

    return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
  }

  private boolean isValidBalanceTree(TreeNode root, int min, int max) {
    if (root == null) {
      return true;
    }
    if (root.val <= min || root.val >= max) {
      return false;
    }

    return isValidBalanceTree(root.left, min, root.val) && isValidBalanceTree(root.right, root.val,
        max);
  }


  private int countNum(TreeNode root) {
    if (root == null) {
      return 0;
    }

    return 1 + countNum(root.left) + countNum(root.right);
  }


  public TreeNode findSubtree(TreeNode root) {
    // write your code here
    if (root == null) {
      return null;
    }
    getSum(root);
    return res;
  }

  double min = Integer.MAX_VALUE;
  TreeNode res = null;

  public int getSum(TreeNode root) {
    int sum = root.val;
    if (root.left == null && root.right == null) {

    } else if (root.left == null) {
      sum += getSum(root.right);
    } else if (root.right == null) {
      sum += getSum(root.left);
    } else {
      sum += getSum(root.right) + getSum(root.left);
    }

    int count = countNum(root);
   double av = sum/count;
    if (av < min) {
      min = av;
      res = root;
    }

    return sum;
  }

  public TreeNode findSubtree2(TreeNode root) {
    dfs(root);
    return maxAverage;
  }

  class ResultData {

    int sum;
    int count;

    public ResultData(int sum, int count) {
      this.sum = sum;
      this.count = count;
    }
  }

  private TreeNode maxAverage = null;
  private ResultData maxData = null;

  public ResultData dfs(TreeNode root) {
    if (root == null) {
      return new ResultData(0, 0);
    }
    ResultData left = dfs(root.left);
    ResultData right = dfs(root.right);
    ResultData cur = new ResultData(root.val + left.sum + right.sum, 1 + left.count + right.count);

    if (maxAverage == null || maxData.count * cur.sum > maxData.sum * cur.count) {
      maxData = cur;
      maxAverage = root;
    }

    return cur;
  }

  public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) {
      return false;
    }
    if (root.left == null && root.right == null) {
      if (root.val == targetSum) {
        return true;
      }
      return false;
    }
    if (root.left == null) {
      return hasPathSum(root.right, targetSum - root.val);
    }
    if (root.right == null) {
      return hasPathSum(root.left, targetSum - root.val);
    }

    return hasPathSum(root.right, targetSum - root.val) || hasPathSum(root.left,
        targetSum - root.val);

  }

  public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    getPath(root, targetSum, res, new ArrayList<>());
    return res;
  }

  public void getPath(TreeNode root, int targetSum, List<List<Integer>> res, List<Integer> list) {
    list.add(root.val);
    if (root.left == null && root.right == null) {
      if (root.val == targetSum) {
        res.add(new ArrayList<>(list));
      }
    } else if (root.left == null) {
      getPath(root.right, targetSum - root.val, res, list);
    } else if (root.right == null) {
      getPath(root.left, targetSum - root.val, res, list);
    } else {
      getPath(root.left, targetSum - root.val, res, list);
      getPath(root.right, targetSum - root.val, res, list);
    }
    list.remove(list.size() - 1);
  }


  public int max = Integer.MIN_VALUE;

  public int maxPathSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    getPathSum(root);
    return max;
  }

  public int getPathSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int res = root.val;
    ;
    int left = Math.max(getPathSum(root.left), 0);
    int right = Math.max(getPathSum(root.right), 0);
    max = Math.max(max, res + left + right);
    res += Math.max(left, right);
    return res;
  }


  public int maxPathSum2(TreeNode root) {
    // write your code here
    if (root == null) {
      return 0;
    }
    return getPath(root);
  }

  public int getPath(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int res = root.val;
    int left = Math.max(getPath(root.left), 0);
    int right = Math.max(getPath(root.right), 0);
    res = res + Math.max(left, right);
    return res;
  }

  int longest = 0;
  public int longestConsecutiveI(TreeNode root) {
    if(root==null){
      return longest;
    }
    getLongestDFS(root,root.val-1,0);
    return longest;
  }

  public void getLongestDFS(TreeNode root,int pre,int count){
    if(root==null){
      longest =Math.max(longest,count);
      return;
    }

    if(root.val==pre+1){
      count++;
    }else {
      count=1;
    }

    longest = Math.max(longest,count);
    getLongestDFS(root.left,root.val,count);
    getLongestDFS(root.right,root.val,count);
  }

  public int longestConsecutive(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int up = helperLongestConsecutive(root, 1);
    int down = helperLongestConsecutive(root, -1);
    return Math.max(up, down) + 1;
  }

  public int helperLongestConsecutive(TreeNode root, int diff) {
    if (root == null) {
      return 0;
    }

    int left = 0;
    int rigt = 0;
    if (root.left != null && root.val + diff == root.left.val) {
      left = 1 + helperLongestConsecutive(root.left, diff);
    }

    if (root.right != null && root.val + diff == root.right.val) {
      rigt = 1 + helperLongestConsecutive(root.right, diff);
    }

    return Math.max(left, rigt);
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    // write your code here
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int n = queue.size();
      List<Integer> list = new ArrayList<>();
      while (n > 0) {
        n--;
        TreeNode cur = queue.poll();
        list.add(cur.val);
        if (cur.left != null) {
          queue.add(cur.left);
        }
        if (cur.right != null) {
          queue.add(cur.right);
        }
      }
      res.add(list);
    }

    return res;
  }


  public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
      return null;
    }
    if (root == p) {
      return p;
    }

    if (root == q) {
      return q;
    }

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) {
      return root;
    }

    return left == null ? right : left;

  }


  boolean findA = false;
  boolean findB = false;
  public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
    // write your code here
    TreeNode res = lowestCommonAncestor(root,A,B);
    return findA&&findB? res:null;
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
    // write your code here
    if(root==null){
      return null;
    }
    if(root==A){
      findA = true;
      return A;
    }

    if(root==B){
      findB = true;
      return B;
    }
    TreeNode left = lowestCommonAncestor(root.left,A,B);
    TreeNode right = lowestCommonAncestor(root.right,A,B);
    if(left!=null&&right!=null){
      return root;
    }

    return left==null ? right:left;
  }


  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.peek();
      res.add(node.val);
      int n = queue.size();
      while (n > 0) {
        n--;
        TreeNode cur = queue.poll();
        if (cur.right != null) {
          queue.add(cur.right);
        }
        if (cur.left != null) {
          queue.add(cur.left);
        }
      }
    }

    return res;
  }

  public int findBottomLeftValue(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int res = root.val;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int n = queue.size();
      res = queue.peek().val;
      while (n > 0) {
        TreeNode cur = queue.poll();
        n--;
        if (cur.left != null) {
          queue.offer(cur.left);
        }

        if (cur.right != null) {
          queue.offer(cur.right);
        }
      }
    }

    return res;
  }


  public int solution(int[] A) {
    // write your code in Java SE 8
    if(A.length==0){
      return 1;
    }
    Arrays.sort(A);
    int pre = 0;
    for(int i=0;i<A.length;i++){
      if(A[i]<0){
        continue;
      }
      if(A[i]==pre+1){
        pre = A[i];
      }else{
        return pre+1;
      }
    }

    return pre+1;
  }


  public boolean solution(String[] B) {
    // write your code in Java SE 8
    int m = B.length;
    int n = B[0].length();
    char[][] grid = new char[m][n];
    for(int i=0;i<m;i++){
      grid[i] = B[i].toCharArray();
    }
    sign(grid);
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[m][n];
    boolean flag  = false;
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(grid[i][j]=='A'){
          queue.add(new int[]{i,j});
          visited[i][j] = true;
          flag = true;
          break;
        }
      }
      if(flag){
        break;
      }
    }

    int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
    while(!queue.isEmpty()){
      int[] cor = queue.poll();
      if(cor[0]==m-1&&cor[1]==n-1){
        return true;
      }
      for(int[] dir:dirs){
        int x = cor[0]+dir[0];
        int y = cor[1]+dir[1];
        if(x<0||x>=m||y<0||y>=n||grid[x][y]=='X'||visited[x][y]){
          continue;
        }
        visited[x][y] = true;
        queue.add(new int[]{x,y});
      }


    }


    return false;

  }

  public void sign(char[][] grid){
    int m = grid.length;
    int n = grid[0].length;

    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(grid[i][j]=='>'){
          int start = j;
          while(start<n&&grid[i][start]!='X'){
            grid[i][start++]= 'X';
          }
        }else if(grid[i][j]=='<'){
          int start = j;
          while(start>=0&&grid[i][start]!='X'){
            grid[i][start--]= 'X';
          }
        }else if(grid[i][j]=='^'){
          int start = i;
          while(start>=0&&grid[start][j]!='X'){
            grid[start--][j]= 'X';
          }
        }else if(grid[i][j]=='v'){
          int start = i;
          while(start<m&&grid[start][j]!='X'){
            grid[start++][j]= 'X';
          }
        }
      }
    }


  }

  public void flatten(TreeNode root) {
    if (root == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      TreeNode tmp = stack.pop();
      if (tmp.left != null) {
        TreeNode r = tmp.left;
        while (r.right != null) {
          r = r.right;
        }
        r.right = tmp.right;
        tmp.right = tmp.left;
        tmp.left = null;
      }
      if (tmp.right != null) {
        stack.push(tmp.right);
      }
    }

  }

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> res = new ArrayList<>();
    getSum(candidates, target, res, new ArrayList<>(), 0);
    return res;

  }

  public void getSum(int[] candidates, int target, List<List<Integer>> res, List<Integer> list,
      int start) {
    if (target == 0) {
      res.add(new ArrayList<>(list));
      return;
    }

    for (int i = start; i < candidates.length; i++) {

      if (candidates[i] > target) {
        break;
      }
      if (i > start && candidates[i] == candidates[i - 1]) {
        continue;
      }
      list.add(candidates[i]);
      getSum(candidates, target - candidates[i], res, list, i + 1);
      list.remove(list.size() - 1);
    }
  }

  public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> res = new ArrayList<>();
    dfs(res, new ArrayList<>(), n, k, new boolean[n + 1], 1);
    return res;
  }

  public void dfs(List<List<Integer>> res, List<Integer> list, int n, int k, boolean[] visited,
      int start) {
    if (list.size() == k) {
      res.add(new ArrayList<>(list));
      return;
    }

    for (int i = start; i <= n; i++) {
      if (visited[i]) {
        continue;
      }
      list.add(i);
      visited[i] = true;
      dfs(res, list, n, k, visited, i + 1);
      visited[i] = false;
      list.remove(list.size() - 1);
    }

  }

  public List<List<Integer>> subsetsWithDup(int[] nums) {

    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(nums);
    dfs(res, new ArrayList<>(), nums, 0);
    return res;
  }

  public void dfs(List<List<Integer>> res, List<Integer> list, int[] nums, int start) {
    res.add(new ArrayList<>(list));
    for (int i = start; i < nums.length; i++) {
      if (i > start && nums[i - 1] == nums[i]) {
        continue;
      }
      list.add(nums[i]);
      dfs(res, list, nums, i + 1);
      list.remove(list.size() - 1);
    }
  }


  public TreeNode construct(){
    TreeNode root = new TreeNode(1);
    TreeNode left = new TreeNode(2);
    TreeNode right = new TreeNode(3);
    root.left = left;
    root.right = right;

    TreeNode left1 = new TreeNode(3);
    TreeNode left2 = new TreeNode(4);
    TreeNode right2 = new TreeNode(6);


    left.left = left1;
    left.right = left2;
    right2.right = right2;
//
//    lowestCommonAncestor3(root,left1,left2);




    return root;

  }



  public static void main(String[] args) {
    BinaryTree te = new BinaryTree();
    te.flatten(te.construct());
  }



  }
