package unlock;

public class NumArray {
    static class SegmentTree{
        int sum;
        int start;
        int end;
        SegmentTree left;
        SegmentTree right;
        public SegmentTree(int start,int end){
            this.start = start;
            this.end = end;
            left = null;
            right = null;
        }


        public static SegmentTree buildTree(int[] nums,int start,int end){
           SegmentTree root = new SegmentTree(start,end);
            if(start==end){
                root.sum = nums[start];
                return root;
            }
            int mid = start+(end-start)/2;
            if(root.left==null){
                root.left = buildTree(nums,start,mid);
                root.right = buildTree(nums,mid+1,end);
            }

            root.sum = root.left.sum+root.right.sum;
            return root;
        }

        public static void update(SegmentTree root,int index, int val){
            if(index<root.start||index>root.end){
                return;
            }

            if(root.start==root.end&&root.start==index){
                root.sum = val;
                return;
            }
            update(root.left,index,val);
            update(root.right,index,val);
            root.sum = root.left.sum+root.right.sum;
        }

        public static int query(SegmentTree root, int left,int right){
            if(root.end<left||root.start>right){
                return 0;
            }

            if(left<=root.start&&right>=root.end){
                return root.sum;
            }

            return query(root.left,left,right)+query(root.right,left,right);
        }

    }

    SegmentTree root;
    public NumArray(int[] nums) {
        int n = nums.length;
        root = SegmentTree.buildTree(nums,0,n-1);
    }

    public void update(int index, int val) {
        SegmentTree.update(root,index,val);
    }

    public int sumRange(int left, int right) {
        return SegmentTree.query(root,left,right);
    }


}
