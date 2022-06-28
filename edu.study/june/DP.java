package june;

import java.util.Arrays;

public class DP {

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        Node[] dp = new Node[n];

        dp[0]=new Node(1,1);
        for(int i=1;i<n;i++){
            dp[i] = new Node(1,1);
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    if(dp[i].length==dp[j].length+1){
                        dp[i].kind+=dp[j].kind;
                    }else if(dp[i].length<dp[j].length+1){
                        dp[i].kind = dp[j].kind;
                        dp[i].length = dp[j].length+1;
                    }
                }
            }
        }

        Arrays.sort(dp,(a,b)->(b.length-a.length));
        int res =0;
        int max = dp[0].length;
        for(Node node : dp){
            if(node.length==max){
                res+=node.kind;
            }else {
                break;
            }
        }


        return res;

    }

    class Node{
        int length;
        int kind;

        public Node(int len,int kind){
            this.length = len;
            this.kind = kind;
        }
    }

    public static void main(String[] args) {
        DP test = new DP();
        test.findNumberOfLIS(new int[]{1,3,5,4,7});
    }
}
