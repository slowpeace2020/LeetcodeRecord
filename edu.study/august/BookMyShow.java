package august;

import java.util.Arrays;

public class BookMyShow {
    static class SegTree{
        long sum[];
        long segTree[];
        int m,n;
        public SegTree(int n,int m){
            this.m = m;
            this.n = n;
            segTree = new long[4*n];
            sum = new long[4*n];
            build(0,0,n-1,n);
        }

        private void build(int index,int low,int high,long val){
            if(low==high){
                segTree[index] = val;
                sum[index] = val;
                return;
            }

            int mid = (low+high)/2;
            build(2*index+1,low,mid,val);
            build(2*index+2,mid+1,high,val);
            segTree[index] = sum[2*index+1] + sum[2*index+2];
        }

        private void update(int index, int low,int high,int pos,int val){
            if(low==high){
                segTree[index] = val;
                sum[index] = val;
                return;
            }

            int mid = (low + high)/2;
            if(pos <= mid){
                update(2*index+1,low,mid,pos,val);
            }else{
                update(2*index+2,mid+1,high,pos,val);
            }

            segTree[index] = Math.max(segTree[2*index+1],segTree[2*index+2]);
            sum[index] = sum[2*index+1] + sum[2*index+2];
        }

        public void update(int pos,int val){
            update(0,0,n-1,pos,val);
        }

        public int gatherQuery(int k, int maxRaw){
            return gatherQuery(0,0,n-1,k,maxRaw);
        }

        public int gatherQuery(int index,int low,int high,int k, int maxRaw){
            if(segTree[index]<k||low>maxRaw){
                return -1;
            }

            if(low==high){
                return low;
            }

            int mid = (low+high)/2;
            int c = gatherQuery(2*index+1,low,mid,k,maxRaw);

            if(c==-1){
                c = gatherQuery(2*index+2,mid+1,high,k,maxRaw);
            }

            return c;
        }

        public long sumQuery(int k, int maxRaw){
            return sumQuery(0,0,n-1,k,maxRaw);
        }

        private long sumQuery(int index, int low, int high, int left,int right){
            if(low>right||high<left){
                return 0;
            }

            if(low>=left&&high<=right){
                return sum[index];
            }

            int mid = (low+high)/2;
            return sumQuery(2*index+1,low,mid,left,right) + sumQuery(2*index+2,mid+1,high,left,right);
        }

    }

    SegTree segTree;

    int[] rowSeats;

    public BookMyShow(int n, int m){
        segTree = new SegTree(n,m);
        rowSeats = new int[n];
        Arrays.fill(rowSeats,n);
    }

    public int[] gather(int k, int maxRaw){//某一行>=k的最小值，二分查找，rangeMAx, 更新rangeMax
        int row = segTree.gatherQuery(k,maxRaw);
        if(row==-1){
            return new int[]{};
        }
        int col = segTree.m - rowSeats[row];
        rowSeats[row] -= k;
        segTree.update(row,rowSeats[row]);

        return new int[]{row,col};
    }

    public boolean scatter(int k, int maxRaw){//rangeSum 和更新rangesum
        long sum = segTree.sumQuery(0,maxRaw);
        if(sum<k){
            return false;
        }

        for(int i=0;i<maxRaw&&k!=0;i++){
            if(rowSeats[i]>0){
                long t = Math.min(rowSeats[i],k);
                rowSeats[i]-=t;
                k-=t;
                segTree.update(i,rowSeats[i]);
            }
        }

        return true;
    }


}
