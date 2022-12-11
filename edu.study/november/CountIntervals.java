package november;

public class CountIntervals {
    class SegmentTree{
        int start,end;
        int count;
        boolean cover;
        SegmentTree left,right;
        public SegmentTree(int start,int end){
            this.start = start;
            this.end = end;
            count = 0;
            cover = false;
            left = null;
            right = null;
        }
    }

    private void update(SegmentTree root,int start,int end){
        if(root==null||root.start>end||root.end<start||root.cover){
            return;
        }
        if(root.start>=start&&root.end<=end){
            root.count = root.end-root.start+1;
            root.cover = true;
            root.left = null;
            root.right = null;
            return;
        }

        int mid = (root.start+root.end)>>1;
        if(end<=mid){
            if(root.left==null){
                root.left = new SegmentTree(root.start,mid);
            }
            update(root.left,start,end);
        }else if(start>mid){
            if(root.right==null){
                root.right = new SegmentTree(mid+1,root.end);
            }
            update(root.right,start,end);
        }else{
            if(root.left==null){
                root.left = new SegmentTree(root.start,mid);
            }
            update(root.left,start,end);
            if(root.right==null){
                root.right = new SegmentTree(mid+1,root.end);
            }
            update(root.right,start,end);
        }

        if(root.left!=null&&root.right!=null&&root.left.cover&&root.right.cover){
            root.count = root.end-root.start+1;
            root.cover = true;
            root.left = null;
            root.right = null;
            return;
        }

        int count = 0;
        if(root.left!=null){
            count +=  root.left.count;
        }

        if(root.right!=null){
            count +=  root.right.count;
        }
        root.count = count;
    }


    SegmentTree root = new SegmentTree(1, (int) 1e9);

    public void add(int left,int right){
        update(root,left,right);
    }

    public int count() {
        return root.count;
    }

    public static void main(String[] args) {
//        ["CountIntervals","count","add","add","add","add","add","count","add","add"]
//[[],[],[8,43],[13,16],[26,33],[28,36],[29,37],[],[34,46],[10,23]]
        CountIntervals test = new CountIntervals();
        test.add(8,43);
        test.add(13,16);
        test.add(26,33);
        test.add(28,36);
        test.add(29,37);
        System.out.println(test.count());
        test.add(34,46);
        test.add(10,23);

    }
}
