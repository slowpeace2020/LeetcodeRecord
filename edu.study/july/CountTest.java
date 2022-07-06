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


    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs,new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                if(a.startsWith("let")&&b.startsWith("let")){
                    return a.compareTo(b);
                }else if(a.startsWith("let")){
                    return 1;
                }else if(b.startsWith("let")){
                    return -1;
                }else{
                    return 1;
                }
            }
        });

        return logs;
    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int n = words.length;
        int i=0;
        while(i<n){
            int len =words[i].length();
            int j=i+1;
            while(j<n&&len+words[j].length()+(j-i-1)<=maxWidth){
                len+=words[j].length();
                j++;
            }

            res.add(sparse(words,i,j-1,len,maxWidth));
            i=j;
        }
        return res;
    }

    private String sparse(String[] words, int i,int j,int len,int maxWidth){
        int spaces = maxWidth-len;
        StringBuilder sb = new StringBuilder();

        if(j>i){

            int every = spaces/(j-i);
            int reminder = spaces%(j-i);
            int n = words.length;
            if(j==n-1){
                every=1;
                reminder = 0;
            }
            String blanks = getBlank(every);
            for(;i<=j;i++){
                sb.append(words[i]);
                if(i<j){
                    sb.append(blanks);

                    if(reminder>0){
                        sb.append(" ");
                        reminder--;
                    }
                }
            }

            if(j==n-1){
                spaces = maxWidth-sb.length();
                sb.append(getBlank(spaces));
            }

        }else{
            sb.append(words[i]);
            sb.append(getBlank(spaces));
        }

        return sb.toString();

    }

    private String getBlank(int spaces){
        StringBuilder sb = new StringBuilder();
        for(int k=0;k<spaces;k++){
            sb.append(" ");
        }

        return sb.toString();
    }

    public String reorderSpaces(String text) {
        String[] s = text.split(" ");
        int len = 0;
        int slen =0;
        for(String str:s){
            if(str.equals("")){
                continue;
            }
            len+=str.length();
            slen++;
        }

        int spaces = text.length()-len;
        int every = spaces/(slen-1);
        int reminder = spaces%(slen-1);

        StringBuilder sb = new StringBuilder();
        String blank = getBlank(every);
        for(int i=0;i<s.length;i++){
            String str= s[i];
            if(str.equals("")){
                continue;
            }
            sb.append(s[i]);
            if(i<s.length-1){
                sb.append(blank);
                if(reminder>0){
                    sb.append(" ");
                    reminder--;
                }
            }

        }

        return sb.toString();

    }

    public int countDistinct(int[] nums, int k, int p) {
        int start = 0;
        int count=0;
        Set<List<Integer>> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            int value = nums[i];
            if(value%p==0){
                count++;
            }

            while(count>k){
                if(nums[start++]%p==0){
                    count--;
                }
            }

            getSet(nums,start,i,set);

        }

        return set.size();
    }

    private void getSet(int[] nums,int i,int j,Set<List<Integer>> set){
        for(;i<=j;i++){
            List<Integer> list = new ArrayList<>();
            for(int k=i;k<=j;k++){
                list.add(nums[i]);
            }
            set.add(list);
        }
    }

    public int distributeCookies(int[] cookies, int k) {
        int sum = 0;
        int n = cookies.length;
        for(int cookie:cookies){
            sum+=cookie;
        }

        int target = sum/k;
        boolean[][][] dp = new boolean[n][n][target+1];
        dp[0][0][0] =true;
        for(int i=0;i<n;i++){
            if(cookies[i]<=target){
                for(int j=i;j<n;j++){
                    dp[i][j][cookies[i]] = true;
                    dp[i][j][0] = true;
                }
            }
        }


        for(int i=0;i<n;i++){
            for(int j=i-1;j>=0;j--){
                for(int w=target;w>=cookies[i];w--){
                    dp[j][i][w] = dp[j][i][w]||dp[j][i-1][w-cookies[i]]||dp[j][i-1][w];
                }
            }
        }


        int res = 0;

        for(int i=target;i>=0;i--){
            if(dp[0][n-1][i]){
                k--;
                res+=i;
            }
            if(k==1){
                break;
            }
        }

        return sum-res;

    }

    int result = Integer.MAX_VALUE;
    public int minimumTimeRequired(int[] jobs, int k) {
        int n = jobs.length;
        Arrays.sort(jobs);


        return result;
    }

    private void backtracking(int[] jobs,int start,int[] workers){
        if(start<0){
            //遍历到头了
            result = Math.min(result,Arrays.stream(workers).max().getAsInt());
            return;
        }

        //最小的已经找到
        if(Arrays.stream(workers).max().getAsInt()>result){
            return;
        }

        for(int i=0;i<workers.length;i++){
            //两个任务量一样大，不用再给它分，重复的，先分给后面不同值的
            if(i>0&&workers[i]==workers[i-1]){
                  continue;
            }

            workers[i]+=jobs[start];
            backtracking(jobs,start+1,workers);
            workers[i]-=jobs[start];
        }



    }


    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (k > nums.length) return false;
        int sum = 0;
        for (int i : nums) sum += i;
        // basic logic test
        if (sum % k != 0) return false;
        Arrays.sort(nums);

        return dfs(nums,new boolean[nums.length],k,sum/k,0,nums.length-1);
    }

    private boolean dfs(int[] nums,boolean[] visited, int k,int target,int sum,int position){
        if(k==1){
            return true;
        }

        if(sum==target){
            return dfs(nums,visited,k-1,target,0,nums.length-1);
        }

        for(int i=position;i>=0;i--){
            if(visited[i]){
                continue;
            }

            if(sum+nums[i]>target){
                continue;
            }

            visited[i] = true;
            if(dfs(nums,visited,k,target,sum+nums[i],i-1)){
                return true;
            }
            visited[i] = false;
        }
        return false;
    }







        public static void main(String[] args) {
        CountTest test = new CountTest();

        test.distributeCookies(new int[]{6,1,3,2,2,4,1,2},3);

    }
}
