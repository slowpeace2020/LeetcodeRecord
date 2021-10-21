package lock;

import java.util.ArrayList;
import java.util.List;

public class Create_Sorted_Array_Through_Instructions_1648 {
    public int createSortedArrayI(int[] instructions) {
        int cost = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(int value:instructions){
            cost+=getcost(value,list);
        }
        System.out.println(cost);
        return cost;
    }

    public int getcost(int value,ArrayList<Integer> list){
        int cost = 0;
        int left = 0;
        int right = list.size();
        while(left<right){
            int mid = left+(right-left)/2;
            if(list.get(mid)<=value){
                left=mid+1;
            }else{
                right = mid;
            }
        }

        int end = left;

         left = 0;
         right = list.size();
         while(left<right){
             int mid = left+(right-left)/2;
             if(list.get(mid)>=value){
                 right = mid;
             }else {
                 left = mid+1;
             }

         }

         int start = right;


         cost = Math.min(Math.min(start,list.size()-start),Math.min(end,list.size()-end));


         list.add(right,value);

        return cost;

    }

    public int createSortedArray(int[] instructions) {
            int res = 0;
            TreeNode root = new TreeNode(instructions[0]);
            for(int i=1;i<instructions.length;i++){
                  root = root.add(root,instructions[i]);
                  TreeNode node = root.query(root,instructions[i]);
                  res += Math.min(node.minSum,node.maxSum);
            }

            return res;
    }

        class TreeNode{
        int val;
        int length;
        int minSum;
        int maxSum;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val){
            this.val = val;
            this.length = 1;
            this.minSum = 0;
            this.maxSum = 0;
            this.left = this.right = null;
        }


        public TreeNode add(TreeNode root, int val){
            if(root==null){
                return new TreeNode(val);
            }
            if(root.val==val){
                update(root,val);
            }else if(root.val>val){
                root.minSum++;
                if(root.left==null){
                    root.left = new TreeNode(val);
                    root.left.maxSum = root.maxSum+ root.length;
                }else {
                    root.left = add(root.left,val);
                }
            }else {
                root.maxSum++;
                root.right = add(root.right,val);

                if(root.right==null){
                    root.right = new TreeNode(val);
                    root.right.minSum = root.minSum+ root.length;
                }else {
                    root.right = add(root.right,val);
                }
            }

            return root;
        }

        public void update(TreeNode root, int val){
            if(root==null){
                return;
            }

            if(root.val==val){
                root.length++;
                update(root.right,val);
            }else if(root.val>val){
                root.minSum++;
                update(root.right,val);
            }else {
                root.maxSum++;
                update(root.left,val);
            }
        }

        public TreeNode query(TreeNode root, int val){
            if(root.val==val){
                return root;
            }else if(root.val>val){
                return query(root.left,val);
            }else {
                return query(root.right,val);
            }

        }


    }

    public static void main(String[] args) {
        Create_Sorted_Array_Through_Instructions_1648 test = new Create_Sorted_Array_Through_Instructions_1648();
//        test.createSortedArray(new int[]{1,5,6,2});
//        test.createSortedArray(new int[]{1,2,3,6,5,4});
        test.createSortedArray(new int[]{1,3,3,3,2,4,2,1,2});
    }
}
