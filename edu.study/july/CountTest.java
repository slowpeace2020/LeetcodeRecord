package july;

import java.util.*;

public class CountTest {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i])-1;
            if (nums[index] < 0)
                res.add(Math.abs(index+1));
            nums[index] = -nums[index];
        }
        return res;
    }

    public int minMutation(String start, String end, String[] bank) {
        List<String> bankList = new ArrayList<>();
        for(String str:bank){
            bankList.add(str);
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        Set<String> visited = new HashSet<>();
        visited.add(start);
        int step =0;

        while (!queue.isEmpty()){
            int size = queue.size();

            for(int i=0;i<size;i++){
                String cur = queue.poll();
                if(cur.equals(end)){
                    return step;
                }
                List<String> next = getNext(cur,bankList);
                for(String str:next){
                    if(visited.add(str)){
                        queue.add(str);
                    }
                }

            }
            step++;
        }

        return -1;
    }


    private List<String> getNext(String start,List<String> bankList){
        List<String> res = new ArrayList<>();
        for(String str:bankList){
            if(str.equals(start)){
                continue;
            }
            if(isNext(start,str)){
                res.add(str);
            }
        }

        return res;
    }

    private boolean isNext(String start, String end){
        int diff = 0;
        for(int i=0;i<start.length();i++){
            if(start.charAt(i)!=end.charAt(i)){
                diff++;
            }
            if(diff==2){
                return false;
            }
        }

        return true;
    }

    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int[][] dp = new int[n][n];
        for(int i=0;i<n;i++){
            Arrays.fill(dp[i],Integer.MAX_VALUE);
            dp[i][i] = stones[i];
        }

        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                for(int k=i;k<j;k++){
                    dp[i][j]=Math.min(dp[i][j],Math.abs(dp[i][k]-dp[k+1][j]));
                }
                dp[i][j]=Math.min(dp[i][j],Math.abs(dp[i][j-1]-dp[j][j]));
            }
        }

        return dp[0][n-1];
    }


        public static void main(String[] args) {
        CountTest test = new CountTest();
        test.lastStoneWeightII(new int[]{2,7,4,1,8,1});
    }
}
