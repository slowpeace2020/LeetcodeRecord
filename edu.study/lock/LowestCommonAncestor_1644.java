package lock;

public class LowestCommonAncestor_1644 {
    class TreeNode{
        int val;
        TreeNode left,right;
        public TreeNode(int val){
            this.val = val;
        }
    }

    private int count = 0;
    public TreeNode lowestCommonAncester(TreeNode root, TreeNode p , TreeNode q){
        TreeNode res = findByPostOrder(root,p,q);
        return count==2?res:null;
    }

    public TreeNode findByPostOrder(TreeNode root, TreeNode p, TreeNode q){
        if(root==null){
            return null;
        }

        TreeNode left = findByPostOrder(root.left,p,q);
        TreeNode right = findByPostOrder(root.right,p,q);
        if(root==p||root==q){
            count++;
            return root;
        }

        if(left==null){
            return right;
        }else if(right==null){
            return left;
        }else {
            return root;
        }
    }
}
