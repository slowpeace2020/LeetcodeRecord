package november;

public class NumArray {
    class SegmentTree{
        int start,end,sum;
        SegmentTree left,right;
        public SegmentTree(int start,int end){
            this.start = start;
            this.end = end;
        }
    }

    SegmentTree root;
    int[] array;

    private SegmentTree build(int start,int end){
        if(start>end){
            return null;
        }
        SegmentTree node = new SegmentTree(start,end);
        if(start!=end){
            int mid = start+(end-start)/2;
            node.left = build(start,mid);
            node.right = build(mid+1,end);
        }
        return node;
    }

    private void update(SegmentTree root,int index,int val){
       if(root==null||root.end<index||root.start>index){
           return;
       }

       if(root.start==index&&root.end==index){
           root.sum = val;
           return;
       }

       int mid = root.start+(root.end-root.start)/2;
       if(index<=mid){
           update(root.left,index,val);
       }else {
           update(root.right,index,val);
       }
       root.sum=root.left.sum+root.right.sum;
    }

    private int query(SegmentTree root,int start,int end){
        if(start>end||root==null||root.start>end||root.end<start){
            return 0;
        }
        if(start<=root.start&&end>=root.end){
            return root.sum;
        }

        int mid = root.start+(root.end-root.start)/2;
        int leftSum = 0;
        int rightSum = 0;
        if(start<=mid){
            if(end>=mid){
                leftSum = query(root.left,start,mid);
            }else {
                leftSum = query(root.left,start,end);
            }
        }

        if(end>mid){
            if(start<=mid){
                rightSum = query(root.right,mid+1,end);
            }else {
                rightSum = query(root.right,start,end);
            }
        }

        return leftSum+rightSum;
    }

    public NumArray(int[] nums) {
        this.array = nums;
        root = build(0,nums.length-1);
        for(int i=0;i<nums.length;i++){
            update(i,nums[i]);
        }
    }

    public void update(int index, int val) {
        array[index] = val;
        update(root,index,val);
    }

    public int sumRange(int left, int right) {
        return query(root,left,right);
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{1,3,5});
        System.out.println(numArray.sumRange(0,2));
    }
}
