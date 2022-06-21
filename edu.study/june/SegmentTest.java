package june;

public class SegmentTest {
    class SegmentTreeNode{
        public SegmentTreeNode left;
        public SegmentTreeNode right;
        int start;
        int end;
        int info;
        int delta;
        boolean lazy;
        SegmentTreeNode(int a,int b,int val){
            lazy = false;
            delta = 0;
            start = a;
            end = b;
            if(a==b){
                info = val;
            }
            int mid = (a+b)/2;
            if(left==null){
                left = new SegmentTreeNode(a,mid,val);
                right = new SegmentTreeNode(mid+1,b,val);
                info = left.info+right.info;
            }
        }

        public void pushDown(){
            if(lazy&&left!=null){
                left.delta+=delta;
                right.delta+=delta;
                left.lazy=true;
                right.lazy = true;
                lazy = false;
                delta = 0;
            }
        }


        public void updateRangeBy(int a,int b, int val){
            if(b<start||a>end){
                return;
            }

            if(a<=start&&end<=b){
                delta+=val;
                lazy=true;
                return;
            }

            if(left!=null){
                pushDown();
                left.updateRangeBy(a,b,val);
                right.updateRangeBy(a,b,val);
                info = left.info+right.info;
            }
        }


        public int queryRange(int a,int b){
            if(b<start||a>end){
                return 0;
            }

            if(a<=start&&end<=b){
                return info+delta*(end-start+1);
            }

            if(left!=null){
                pushDown();
                int ret = left.queryRange(a,b)+right.queryRange(a,b);
                info = left.info+right.info;
                return ret;
            }
            return info;
        }
    }
}
