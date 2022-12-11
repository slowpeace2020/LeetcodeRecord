package november;

public class Thanksgiving {
    public int countRangeSumI(int[] nums, int lower, int upper) {
        long s = 0;
        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s += nums[i];
            prefixSum[i + 1] = s;
        }

        return countRangeSumRecursive(prefixSum, lower, upper, 0, n);
    }

    private int countRangeSumRecursive(long[] prefixSum, int lower, int upper, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = (left + right) / 2;
        int countLeft = countRangeSumRecursive(prefixSum, lower, upper, left, mid);
        int countRight = countRangeSumRecursive(prefixSum, lower, upper, mid + 1, right);

        int res = countLeft + countRight;

        //prefixSum[j] - prefixSum[i] in [lower,upper]
        //看成两个升序数组，n2[j]-n1[i] in [lower,upper]
        //固定n1的i值，j不断往右推进，直到n2[j]>=n1[i]+lower，此时j为左边界l
        //同样固定n1的i值，j不断往右推进，直到n2[j]>n1[i]+upper，此时j为右边界r
        //也就是说对于固定的n1[i]，[l,r]之间都是符合条件的
        int i = left;
        int l = mid + 1;
        int r = mid + 1;
        while (i <= mid) {
            while (l <= right && prefixSum[l] - prefixSum[i] < lower) {
                l++;
            }

            while (r <= right && prefixSum[r] - prefixSum[i] <= upper) {
                r++;
            }
            res += r - l;
            i++;
        }

        //归并排序
        long[] sorted = new long[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int p = 0;
        while (p1 <= mid || p2 <= right) {
            if (p1 > mid) {
                sorted[p++] = prefixSum[p2++];
            } else if (p2 > right) {
                sorted[p++] = prefixSum[p1++];
            } else {
                if (prefixSum[p1] < prefixSum[p2]) {
                    sorted[p++] = prefixSum[p1++];
                } else {
                    sorted[p++] = prefixSum[p2++];
                }
            }
        }

        for (int j = 0; j < sorted.length; j++) {
            prefixSum[left + j] = sorted[j];
        }

        return res;
    }


    public int countRangeSum(int[] nums, int lower, int upper) {
        long sum = 0;
        long[] preSum = new long[nums.length + 1];
        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }

        long lbound = Long.MAX_VALUE, rbound = Long.MIN_VALUE;
        for (long x : preSum) {
            lbound = Math.min(Math.min(lbound, x), Math.min(x - lower, x - upper));
            rbound = Math.max(Math.max(rbound, x), Math.max(x - lower, x - upper));
        }

        SegmentTree root = new SegmentTree(lbound,rbound);
        int count = 0;
        for(long num:preSum){
            long start = num-upper;
            long end =   num-lower;
            count+=query(root,start,end);
            update(root,num);
        }
        return count;
    }

    private void update(SegmentTree root,long val){
        root.count++;
        if (root.start == root.end) {
            return;
        }
        long mid = (root.start + root.end) >> 1;
        if (val <= mid) {
            if (root.left == null) {
                root.left = new SegmentTree(root.start, mid);
            }
            update(root.left, val);
        } else {
            if (root.right == null) {
                root.right = new SegmentTree(mid + 1, root.end);
            }
            update(root.right, val);
        }



    }

    private int query(SegmentTree root,long start,long end){
        if (root == null) {
            return 0;
        }
        if (start > root.end || end < root.start) {
            return 0;
        }
        if (start <= root.start && root.end <= end) {
            return root.count;
        }
        return query(root.left, start, end) + query(root.right, start, end);
    }


    class SegmentTree{
        long start,end;
        int count;
        SegmentTree left,right;
        public SegmentTree(long start,long end){
            this.start = start;
            this.end = end;
            this.left=null;
            this.right = null;
            this.count = 0;
        }
    }

    public int subarraysWithMoreZerosThanOnes(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n+1];
        for(int i=0;i<n;i++){
            prefixSum[i+1] = prefixSum[i]+nums[i];
        }

        SegmentTree root = new SegmentTree(0,prefixSum[n]);

        int count = 0;
        for(int i=1;i<=n;i++){
            count+=query(root,(i+1)/2,prefixSum[i]);
            update(root,prefixSum[i]);
        }

        System.out.println(count);
        return count;

    }

    public static void main(String[] args) {
        Thanksgiving test = new Thanksgiving();
//        test.countRangeSum(new int[]{-2,5,-1},-2, 2);
        test.subarraysWithMoreZerosThanOnes(new int[]{0,1,1,0,1});
    }


}




