package unlock;

import java.util.Arrays;
import java.util.TreeSet;

public class BiSearch {
    public int arrangeCoins(int n) {
        int res =0;
        while(n>0){
            res++;
            n=n-res;
        }

        if(n<0){
            res--;
        }

        return res;
    }

    public int maxSumSubmatrix(int[][] matrix, int k) {
        int row = matrix.length;
        int ret = Integer.MIN_VALUE;
        int col = matrix[0].length;
        for(int i=0;i<col;i++){   //固定住起始列
            int[] sum = new int[row];
            for(int j=i;j<col;j++){//结尾列
                for(int r=0;r<row;r++){//每一行
                    sum[r]+=matrix[r][j];//i~j列每一行的和
                }

                int curSum =0;
                int curMax = Integer.MIN_VALUE;
                TreeSet<Integer> sumSet = new TreeSet<>();
                sumSet.add(0);
                for(int r=0;r<row;r++){
                    curSum+=sum[r];//curSum保存当前i列至j列中前r行的元素和
                    //找到第一个大于等于curSum-k的值，
                    // 那么curSum减去之前小矩形内元素和可以得到小矩形区域，且其元素和不超过k
                    //也就是说对于任意first>=curSum-k; first是一个组成curSum大小矩阵的子矩阵，
                    //那么curSum-first<=k，curSum-first，则是一个组成curSum大小矩阵的另外一个子矩阵，
                    Integer first = sumSet.ceiling(curSum-k);
                    if(first!=null){
                        //但是curSum-first不一定小于k？
                        curMax = Math.max(curMax,curSum-first);
                    }
                    sumSet.add(curSum);
                }

                ret = Math.max(ret,curMax);
            }
        }

        return ret;
    }

    public int maxEnvelopesI(int[][] envelopes) {
        Arrays.sort(envelopes,(a,b)->(b[0]==a[0]?b[1]-a[1]:b[0]-a[0]));
        int res =1;
        int width = envelopes[0][0];
        int height = envelopes[0][1];
        for(int i=1;i<envelopes.length;i++){
            int w = envelopes[i][0];
            int h = envelopes[i][1];
            if(w<width&&h<height){
                res++;
                width = w;
                height = h;
            }
        }

        return res;

    }

    public int maxEnvelopes(int[][] envelopes) {
        //宽度递曾，宽度相同，高度递减
        Arrays.sort(envelopes,(a,b)->(a[0]==b[0]?b[1]-a[1]:a[0]-b[0]));
        int n = envelopes.length;
        TreeSet<Integer> set = new TreeSet<>();

        for(int i=0;i<n;i++){
            Integer cur = envelopes[i][1];
            //能找到比当前元素高度高的，肯定是和它宽度一样的，把高度替换，
            // 使得高度降下来降下来，不影响现在的size，方便容纳更多
            //那现在替换的元素宽度不会比替换位置之后的更宽吗？
            //有可能，但是不影响结果，只是把高度降下来容纳更多可能性
            //对于
//            {3,10},{3,4},{6,6},{7,7},{8,5}}
            if(!set.isEmpty()&&set.ceiling(cur)!=null){
                Integer upper = set.ceiling(cur);
                set.remove(upper);
            }
            set.add(cur);
        }

        return set.size();
    }


    public int triangleNumber(int[] nums) {

        int n = nums.length;
        if(n<=2){
            return 0;
        }
        int res = 0;
        Arrays.sort(nums);

        for(int i=0;i<n-2;i++){
            for(int j=n-1;j>i+1;j--){
                int count = getCount(nums,i+1,j-1,nums[j]-nums[i]);
                res+=count;
            }
        }

        return res;
    }


    public int getCount(int[] nums, int left, int right, int target){
        int lastIndex = right+1;
        right++;
        while(left<right){
            int mid = left+(right-left)/2;
            if(nums[mid]<=target){
                left = mid+1;
            }else{
                right = mid;
            }
        }

        return lastIndex-left;
    }

    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n-1][n-1];
        //当左右均在参考范围，等于的情况
        while (low<=high){
            int mid = low+(high-low)/2;
            int count  = binaryCount(matrix,mid,n);
            //收缩的时候不能等于mid，要有变化，不然死循环
            if(count>=k){
                high = mid-1;
            }else {
                low = mid+1;
            }
        }
        return low;
    }



    private int binaryCount(int[][] matrix, int target, int n) {
        int res = 0;
        for(int[] mx:matrix){
            int low = 0;
            int high = n-1;
            while (low<=high){
                int mid = low+(high-low)/2;
                if(mx[mid]>target){
                    high = mid-1;
                }else {
                    low = mid+1;
                }
            }
            res+=low;
        }
        return res;
    }

    public int splitArray(int[] nums, int m) {
        int max =nums[0];
        int sum=0;
        for(int num:nums){
            if(num>max){
                max = num;
            }

            sum+=num;
        }

        //答案的可能值范围
        //最小就是最大值单独一组，且是最大值
        long left = max;
        //最大就是搜有元素之和累计
        long right = sum;
        while (left<=right){
            long mid = left+(right-left)/2;
            //数出mid为最大值的数组可以划分的子数组数量
            //若mid为最大值，数组可以划分的数量超过m组，说明mid小了
            //反之大了，决定收缩方向
            boolean valid = isValid(nums,mid,m);
            if(valid){
                right = mid-1;
            }else {
                left=mid+1;
            }
        }

        return (int) left;

    }

    //用boolean类型比int类型好，因为前者不用将数组遍历完就知道结果
    public boolean isValid(int[] nums,long target,int m){
        int count = 0;
        int sum =0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(sum>target){
                count++;
                sum = nums[i];
            }
            if(count>m){
                return false;
            }
        }

        return true;
    }


    public int findKthNumber(int m, int n, int k) {
        int left =1;
        int right = m*n;
        //这里left=right就是答案
        while(left<right){
            int mid = left+(right-left)/2;
            int count = 0;
            int i=m;//右下角位置出发累计小于等于的个数
            int j=1;
          while (i>=1&&j<=n){
              //i*j为当前位置的值
              if(i*j<=mid){
                  count+=i;//当前位置及同一列上面的元素都<=mid，累计
                  j++;//往右边一列看看
              }else {
                  //>mid, 当前位置的右下方可以排除
                  i--;//往上面一行看看
              }
          }


            if(count<k){//小于等于mid的个数小于k个，
                // mid绝对不可能是答案，排除不可能的值
                left=mid+1;
            }else {//小于mid的个数大于等于k个，mid有可能是答案，
                // 一直压缩mid的上限，确保找到的元素一定在矩阵中
                right = mid;
            }

        }


        return left;
    }

    public double maxAverage(int[] nums, int k) {
        double left = Integer.MAX_VALUE;
        double right = Integer.MIN_VALUE;
        for(int num:nums){
            if(num<left){
                left = num;
            }else if(num>right){
                right = num;
            }
        }

        while (left+1e-5<right){
            double mid = left+(right-left)/2;
            //判断平均值mid是否可行

            //若可行则说明答案大于等于mid，那么左边界等于mid

            //否则说明答案小于mid，右边界等于mid
            if(check(nums,k,mid)){
                left = mid;
            }else {
                right = mid;
            }
        }

        return left;
    }

    private boolean check(int[] nums, int k, double avg) {
        //rightSum表示当前指向位置的前缀和

        //leftSum表示当前指向位置左侧k个位置的前缀和

        //minLeftSum表示左侧最小的前缀和
        double rightSum=0;
        double leftSum=0;
        double minLeftSum=0;

        for(int i=0;i<k;i++){
            rightSum+=nums[i]-avg;
        }

        for(int i=k;i<=nums.length;i++){
            if(rightSum-minLeftSum>=0){
                return true;
            }

            if(i<nums.length){
                rightSum+=nums[i]-avg;
                leftSum+=nums[i-k]-avg;
                minLeftSum = Math.min(leftSum,minLeftSum);
            }


        }
        return false;
    }





    public static void main(String[] args) {
        BiSearch test = new BiSearch();
//       int res = test.maxEnvelopes(new int[][]{{3,10},{3,4},{6,6},{7,7},{8,5}});
       test.findKthNumber(3,3,5);
//        System.out.println(res);
    }
}
