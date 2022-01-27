package winter;

public class RangeModule {
    SegmentNode root;
    public RangeModule() {
        root = new SegmentNode(0,(int)1e9,false);
    }

    public void addRange(int left, int right) {
        update(root,left,right,true);
    }

    public boolean update(SegmentNode node,int start,int end,boolean isExist){
        if(node.start>=end||node.end<=start){
            return node.isExist;
        }

        if(node.start>=start&&end>=node.end){
            node.isExist = isExist;
            node.left = null;
            node.right=null;
            return node.isExist;
        }
        int mid = node.start+(node.end-node.start)/2;
        if(node.left==null){
            node.left = new SegmentNode(node.start,mid,node.isExist);
            node.right = new SegmentNode(mid,node.end,node.isExist);
        }

        boolean right = update(node.right,start,end,isExist);
        boolean left = update(node.left,start,end,isExist);


        node.isExist = left&&right;
        return node.isExist;
    }

    public boolean queryRange(SegmentNode node,int start,int end){
        if(start>=node.end||end<=node.start){
            return true;
        }

        if((start<=node.start && end>=node.end) || (node.left==null)) return node.isExist;

        int mid = node.start+(node.end-node.start)/2;
        if(end<=mid){
            return queryRange(node.left,start,end);
        }else if(start>=mid){
            return queryRange(node.right,start,end);
        }

        boolean left = queryRange(node.left,start,end);
        boolean right = queryRange(node.right,start,end);
        return left&&right;
    }

    public boolean queryRange(int left, int right) {
        return queryRange(root,left,right);
    }

    public void removeRange(int left, int right) {
         update(root,left,right,false);
    }

    class SegmentNode{
        int start,end;
        SegmentNode left,right;
        boolean isExist;
        public SegmentNode(int start,int end,boolean isExist){
            this.start = start;
            this.end = end;
            this.isExist = isExist;
        }
    }

    public static void main(String[] args) {
        RangeModule te = new RangeModule();
        te.addRange(10,20);
        te.removeRange(14,16);
//        te.queryRange(10,14);
//        te.queryRange(13,15);
        te.queryRange(16,17);
    }
}
