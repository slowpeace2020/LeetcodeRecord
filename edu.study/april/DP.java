package april;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class DP {
    int res =0;
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k==0){
            return 0;
        }
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();

        dfs(nums,k,1,0,re,new ArrayList<>());
        return res;
    }

    public void dfs(int[] nums, int k, int cur, int start,List<List<Integer>> res,List<Integer> list){
        if(cur>=k){
            return;
        }
        if(list.size()!=0){
            res.add(new ArrayList<>(list));
        }
        for(int i=start;i<nums.length;i++){
            if(cur*nums[i]>=k){
                break;
            }
            if(i>start&&nums[i]==nums[i-1]){
                continue;
            }
            list.add(nums[i]);
           dfs(nums,k,cur*nums[i],i+1,res,list);
           list.remove(list.size()-1);
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String>  res = new ArrayList<>();
        generateParenthesis(n,n,res,"");
        return res;
    }

    private void generateParenthesis(int left, int right,List<String>  res, String pre){
        if(left==0&&right==0){
            res.add(new String(pre));
            return;
        }

        if(right<left){
            return;
        }
        if(left>0){
            generateParenthesis(left-1,right,res,pre+"(" );
        }
        if(right>=left){
            generateParenthesis(left,right-1,res,pre+")" );
        }
    }

    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] array = new int[n+2];
        for(int i=0;i<n;i++){
            array[i+1]=nums[i];
        }
        array[0]=1;
        array[n+1]=1;
        int[][] dp = new int[n+2][n+2];

        for(int k=2;k<n;k++){
            for(int left = 0;left<n+2-k;k++){//固定左端
                int right = left+k;//右端index
                for(int i=left+1;i<right;i++){//两端之间区间的组合遍历保留最大值
                    dp[left][right] = Math.max(dp[left][right],nums[left]*nums[i]*nums[right]+dp[left][i]+dp[i][right]);
                }
            }
        }

        return dp[0][n+1];
    }


    private int burst(int[][] memo,int[] nums,int left,int right){
        //左右相邻，不用消去中间
        if(left+1==right){
            return 0;
        }
        //此区间最大值已经求出来了，不必再算
        if(memo[left][right]>0){
            return memo[left][right];
        }

        int ans =0;
        //遍历中间的值组合，看留下谁最后消去的结果最大
        for(int i=left+1;i<right;i++){
            //中间的区间也是同样的方案，分而治之
            ans = Math.max(ans,nums[left]*nums[i]*nums[right]+burst(memo,nums,left,i)+burst(memo,nums,i,right));
        }
        memo[left][right] = ans;
        return ans;
    }

    public static void main(String[] args) {
        DP dp = new DP();
        dp.generateParenthesis(3);
    }
}
