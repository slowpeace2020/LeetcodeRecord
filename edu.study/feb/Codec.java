package feb;

import java.util.*;

public class Codec {
   class TreeNode{
        int val;
        TreeNode left,right;
        public TreeNode(){

        }

        public TreeNode(int val){
            this.val = val;
        }
    }
    public String serializeI(TreeNode root) {
       if(root==null){
           return "";
       }

       StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(node==null){
                sb.append("X,");
            }else {
                sb.append(node.val+",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return sb.toString();
    }
    public String serialize(TreeNode root) {
        if(root==null){
            return "X,";
        }

        String leftSerialize = serialize(root.left);
        String rightSerialize = serialize(root.right);
        return root.val+","+leftSerialize+rightSerialize;
      }

    public TreeNode deserializeI(String data) {
       if(data.equals("")){
           return null;
       }
       Queue<String> nodes = new ArrayDeque<>(Arrays.asList(data.split(",")));
       TreeNode root = new TreeNode(Integer.valueOf(nodes.poll()));

       Queue<TreeNode> queue = new LinkedList<>();
       queue.offer(root);
       while (!queue.isEmpty()){
           TreeNode node = queue.poll();
           String left = nodes.poll();
           String right = nodes.poll();
           if(!left.equals("X")){
               node.left = new TreeNode(Integer.valueOf(left));
               queue.offer(node.left);
           }

           if(!right.equals("X")){
               node.right = new TreeNode(Integer.valueOf(right));
               queue.offer(node.right);
           }
       }
        return root;
    }
    public TreeNode deserialize(String data) {
        String[] temp = data.split(",");
        Deque<String> deque = new LinkedList<>(Arrays.asList(temp));
        return buildTree(deque);
    }

    private TreeNode buildTree(Deque<String> deque) {
       String s = deque.poll();
       if(s.equals("X")){
           return null;
       }
       TreeNode root = new TreeNode(Integer.valueOf(s));
       root.left = buildTree(deque);
       root.right=buildTree(deque);
        return root;
    }




}
