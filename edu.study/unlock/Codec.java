package unlock;


import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import unlock.LinkedListTest.ListNode;

public class Codec {

  static class TreeNode{
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
  }
  // Encodes a tree to a single string.
  public String serializeI(TreeNode root) {
    if(root==null){
      return null;
    }
    List<String> res = new ArrayList<>();
    res.add(root.val+"");
    List<TreeNode> list = new ArrayList<>();

    while (list.size()!=0){
      List<TreeNode> next = new ArrayList<>();
      for(int i=0;i<list.size();i++){
        TreeNode node = list.get(i);

        if(node.left!=null){
          next.add(node.left);
          res.add(node.left+"");
        }else{
          res.add(null);
        }

        if(node.right!=null){
          next.add(node.right);
          res.add(node.right+"");
        }else{
          res.add(null);
        }
      }

      list = next;
    }

    StringBuilder sb = new StringBuilder();
    for(int i=list.size()-1;i>=0;i--){
      if(sb.length()==0&&list.get(i)==null){
        continue;
      }

      if(sb.length()==0){
        sb.insert(0,list.get(i));
      }else {
        sb.insert(0,list.get(i)+",");
      }
    }

    sb.insert(0,"[");
    sb.append("]");

    return sb.toString();
  }

  // Decodes your encoded data to tree.
  public TreeNode deserializeI(String data) {
    if(data==null||data.equals("[]")){
      return null;
    }

    data = data.replace("[","").replace("]","");
    String[] values = data.split(",");
    int n =values.length;
    TreeNode root = new TreeNode(Integer.valueOf(values[0]));
    TreeNode[] nodes = new TreeNode[n];
    for(int i=0;i<n;i++){
      if(values[i].equals("null")){
        continue;
      }

      if(nodes[i]==null){
        nodes[i] = new TreeNode(Integer.valueOf(values[i]));
      }

      if(i*2+1<n){
        nodes[i*2+1] = new TreeNode(Integer.valueOf(values[i*2+1]));
        nodes[i].left =nodes[i*2+1];
      }


      if(i*2+2<n){
        nodes[i*2+2] = new TreeNode(Integer.valueOf(values[i*2+2]));
        nodes[i].right =nodes[i*2+2];
      }

    }

    return root;
  }

  public String serialize(TreeNode root) {
      if(root==null){
        return "[]";
      }

    Queue<TreeNode> queue = new LinkedList<>();
      StringBuilder sb = new StringBuilder();
      queue.offer(root);
      sb.append("[");
      while (!queue.isEmpty()){
        TreeNode cur = queue.poll();
        if(cur==null){
          sb.append("#");
        }else {
          sb.append(cur.val);
          queue.offer(cur.left);
          queue.offer(cur.right);
        }

        if(!queue.isEmpty()){
          sb.append(",");
        }

      }

      sb.append("]");
      return sb.toString();
  }

  public TreeNode deserialize(String data) {
    if(data==null||data.equals("[]")){
      return null;
    }

    String[] val = data.substring(1,data.length()-1).split(",");
    TreeNode root = new TreeNode(Integer.parseInt(val[0]));

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    boolean isLeftChild = true;

    for(int i=1;i<val.length;i++){
      if(!val.equals("#")){
        TreeNode child = new TreeNode(Integer.parseInt(val[i]));
        if(isLeftChild){
          queue.peek().left = child;
        }else {
          queue.peek().right = child;
        }

        queue.add(child);
      }

      if(!isLeftChild){
        queue.poll();
      }

      isLeftChild = !isLeftChild;
    }

    return root;
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
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
        TreeNode cur = queue.poll();
        list.add(cur.val);
        n--;
        if (cur.left != null) {
          queue.offer(cur.left);
        }

        if (cur.right != null) {
          queue.offer(cur.right);
        }
      }

      res.add(list);
    }

    Collections.reverse(res);
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



  public boolean isCousins(TreeNode root, int x, int y) {
    if (root == null || root.val == x || root.val == y) {
      return false;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int n = queue.size();
      boolean findX = false;
      boolean findY = false;
      int parentX;
      while (n > 0) {
        TreeNode cur = queue.poll();
        if (cur.val == x) {
          findX = true;
        } else if (cur.val == y) {
          findY = true;
        }
        if (cur.left != null && cur.right != null) {
          if (cur.left.val == x && cur.right.val == y) {
            return false;
          }

          if (cur.left.val == y && cur.right.val == x) {
            return false;
          }

        }

        if (cur.left != null) {
          queue.add(cur.left);

        }
        if (cur.right != null) {
          queue.add(cur.right);
        }
        n--;
      }

      if (findX && findY) {
        return true;
      } else if (findX || findY) {
        return false;
      }
    }

    return false;
  }


  }
