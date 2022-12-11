package november;

public class LUPrefix {

    class SegmentTree{
        int start,end;
        boolean cover;
        SegmentTree left,right;
        public SegmentTree(int start,int end){
            this.start = start;
            this.end = end;
        }
    }

    private void update(SegmentTree root,int val){
        if(root==null||root.start>val||root.end<val){
            return;
        }

        if(root.start==root.end&&root.start==val){
            root.cover = true;
            return;
        }

        int mid = (root.start+root.end)>>1;
        if(root.left==null){
            root.left = new SegmentTree(root.start,mid);
            root.right = new SegmentTree(mid+1,root.end);
        }


        if(val<=mid){
            update(root.left,val);
        }else{
            update(root.right,val);
        }

        root.cover = root.left.cover&&root.right.cover;
    }


    private int query(SegmentTree root,int start,int end){
        if(root==null||root.start>end||root.end<start){
            return 0;
        }

        if(root.cover){
            start = Math.max(start,root.start);
            end = Math.min(end,root.end);
            return Math.max(end-start+1,0);
        }

        if(root.left==null){
            return 0;
        }

        int mid = (root.start+root.end)>>1;
        if(end<=mid||!root.left.cover){
            return query(root.left,start,end);
        }

        return root.left.end-root.left.start+1+query(root.right,start,end);
    }



    SegmentTree root;
    int length;
    public LUPrefix(int n) {
        root = new SegmentTree(1,n);
        length = n;
    }

    public void upload(int video) {
        update(root,video);
    }

    public int longest() {
       return query(root,1,length);
    }

    public static void main(String[] args) {
        LUPrefix prefix = new LUPrefix(4);
        prefix.upload(3);
        prefix.upload(1);
        System.out.println(prefix.longest());
    }
}
