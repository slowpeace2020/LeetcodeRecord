package june;

import java.util.*;

public class MyMemory {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if(desiredTotal<=0){
            return false;
        }

        if(maxChoosableInteger*(maxChoosableInteger+1)/2<desiredTotal){
            return false;
        }

        return canIWin(desiredTotal,new int[maxChoosableInteger],new HashMap<>());

    }

    private boolean canIWin(int total, int[] state, HashMap<String,Boolean> memory){
            String cur = Arrays.toString(state);
            if(memory.containsKey(cur)){
                return memory.get(cur);
            }
            for(int i=0;i<state.length;i++){
                if(state[i]==0){
                    state[i] = 1;
                    if(total<i+1||!canIWin(total-i-1,state,memory)){
                        memory.put(cur,true);
                        state[i] = 0;
                        return true;
                    }
                    state[i] = 0;
                }
            }

            memory.put(cur,false);
            return false;
    }

    public int longestSubarray(int[] nums, int limit) {
        int res =1;
        int start =0;
        TreeMap<Integer,Integer> mapCount = new TreeMap<>();
        mapCount.put(nums[0],1);
        for(int i=1;i<nums.length;i++){
            int key = nums[i];
            Integer low = mapCount.floorKey(key-limit-1);
            Integer high = mapCount.ceilingKey(key+limit+1);
            while (low!=null||high!=null){
                if(mapCount.get(nums[start])==1){
                    mapCount.remove(nums[start]);
                }else {
                    mapCount.put(nums[start],mapCount.get(nums[start])-1);
                }
                start++;
                low = mapCount.floorKey(key-limit-1);
                high = mapCount.ceilingKey(key+limit+1);
            }

            mapCount.put(key,mapCount.getOrDefault(key,0)+1);

            res = Math.max(res,i-start+1);
        }

        return res;
    }

    public int partitionArray(int[] nums, int k) {
        int res =0;
        Arrays.sort(nums);
        TreeMap<Integer,Integer> map = new TreeMap<>();
        for(int i=0;i<nums.length;i++){
            int key = nums[i];
            Integer low = map.floorKey(key-k-1);
            Integer high = map.ceilingKey(key+k+1);
            if(low!=null||high!=null){
                res++;
                map = new TreeMap<>();
            }
            map.put(key,map.getOrDefault(key,0)+1);
        }

        return res+1;
    }


    public int countHomogenous(String s) {
        int res = 1;
        int len=1;
        char pre = s.charAt(0);
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)==pre){
                len++;
            }else{
                len=1;
                pre = s.charAt(i);
            }
            res+=len;
        }

        return res;
    }

    public int backPackVI(int[] nums, int target) {
        int[] dp = new int[target+1];
        dp[0] = 1;
        for(int i=1;i<=target;i++){
            for(int num:nums){
                if(i>=num){
                    dp[i]+=dp[i-num];
                }
            }
        }

        return dp[target];
    }

    public int maxSumAfterPartitioning(int[] arr, int k) {
        Arrays.sort(arr);
        int n = arr.length;
        int res =0;
        int index =n-1;
        while(n>k){
            res+=arr[index--]*k;
            n=n-k;
        }

        res+=arr[index--]*n;
        return res;
    }

    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        int[] leftSum = new int[n+1];
        int[] rightSum = new int[n+1];
        int res = n+1;
        for(int i=0;i<n;i++){
            leftSum[i+1]=leftSum[i] + nums[i];
            if(leftSum[i+1]==x){
                res = Math.min(res,i+1);
            }
        }

        TreeMap<Integer,Integer> treeMap = new TreeMap<>();

        for(int j=n-1;j>=0;j--){
            rightSum[n-j] = rightSum[n-j-1]+nums[j];
            if(rightSum[n-j]==x){
                res = Math.min(res,n-j);
            }
            treeMap.put(rightSum[n-j],n-j);
        }

        if(leftSum[n]<x||(nums[0]>x&&nums[n-1]>x)){
            return -1;
        }


        for(int i=0;i<=n;i++){
            // for(int j=n-i;j>=0;j--){
            //     if(leftSum[i]+rightSum[j]==x){
            //         res=Math.min(res,i+j);
            //     }
            // }

            if(x-leftSum[i]<1){
                break;
            }

            Integer key = treeMap.ceilingKey(x-leftSum[i]);
            if(key+leftSum[i]==x){
                res=Math.min(res,treeMap.get(key)+i);
            }

        }

        return res==n+1?-1:res;
    }

    public int maximum69Number (int num) {
        char[] array = (num+"").toCharArray();
        for(int i=0;i<array.length;i++){
            if(array[i]-'0'!=9){
                array[i]='9';
                return Integer.valueOf(new String(array));
            }
        }

        return num;
    }

    public String solveEquation(String equation) {
        int[] left = count(equation.split("=")[0]);
        int[] right = count(equation.split("=")[1]);
        if(left[1]==right[1]){
            return "Infinite solutions";
        }

        int value = (right[0]-left[0])/(left[1]-right[1]);
        return "x="+value;

    }
    private int[] count(String str){
        int value = 0;
        int x = 0;
        int n = str.length();
        for(int i=0;i<n;i++){
            if(str.charAt(i)=='x'){
                x++;
            }else if(str.charAt(i)=='+'){
                if(str.charAt(i+1)=='x'){
                    x++;
                    i++;
                }else{
                    int sum = 0;
                    while(i+1<n&&str.charAt(i+1)>='0'&&str.charAt(i+1)<='9'){
                        sum=sum*10+str.charAt(i+1)-'0';
                        i++;
                    }
                    value+=sum;
                }

            }else if(str.charAt(i)=='-'){
                if(str.charAt(i+1)=='x'){
                    x--;
                    i++;
                }else{
                    int sum = 0;
                    while(i+1<n&&str.charAt(i+1)>='0'&&str.charAt(i+1)<='9'){
                        sum=sum*10+str.charAt(i+1)-'0';
                        i++;
                    }
                    value-=sum;
                }
            }else{
                int sum = str.charAt(i)-'0';
                while(i+1<n&&str.charAt(i+1)>='0'&&str.charAt(i+1)<='9'){
                    sum=sum*10+str.charAt(i+1)-'0';
                    i++;
                }
                if(i+1<n&&str.charAt(i+1)=='x'){
                    x+=sum;
                }else{
                    value+=sum;
                }
            }
        }

        return new int[]{value,x};
    }

    public int longestValidParentheses(String s) {
        int n = s.length();
        if(n<=1){
            return 0;
        }
        int res = 0;
        int[] dp = new int[n];
        if(s.charAt(0)=='('&&s.charAt(1)==')'){
            dp[1]=1;
            res = 1;
        }

        for(int i=2;i<n;i++){
            if(s.charAt(i)==')'){
                for(int j=i-1;j>=0;j--){
                    if(dp[j]!=0 &&s.charAt(j-dp[j]*2)=='('){
                        dp[i]=dp[j]+1+dp[j-dp[j]*2];
                        res = Math.max(res,dp[i]);
                    }else if(s.charAt(j)=='('){
                        dp[i]=dp[j-1]+1;
                        res = Math.max(res,dp[i]);
                    }
                    break;
                }
            }
        }

        return res*2;

    }

    public int reductionOperations(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int res = 0;
        for(int i=n-1;i>=0;i--){
            if(nums[i]==nums[0]){
                break;
            }
            while(i>0&&nums[i]==nums[i-1]){
                i--;
            }
            res+=(n-i);
        }

        return res;
    }


    public int findMaxLength(int[] nums) {
        int n = nums.length;
        int res = 0;
        int[] ones = new int[n+1];
        int[] zeros = new int[n+1];
        for(int i=1;i<=n;i++){
            if(nums[i-1]==0){
                zeros[i]=zeros[i-1]+1;
                ones[i]=ones[i-1];
            }else{
                ones[i]=ones[i-1]+1;
                zeros[i]=zeros[i-1];
            }
        }

        for(int i=1;i<=n;i++){
            if(zeros[i]==ones[i]){
                res = Math.max(res,zeros[i]*2);
            }else if(zeros[i]>ones[i]){
                int diff = zeros[i]-ones[i];
                if(zeros[i]-zeros[diff]==ones[i]-ones[diff]){
                    res = Math.max(res,(zeros[i]-diff)*2);
                }
            }else{
                int diff = ones[i] -zeros[i];
                if(zeros[i]-zeros[diff]==ones[i]-ones[diff]){
                    res = Math.max(res,(ones[i]-diff)*2);
                }
            }
        }


        return res;

    }

    public int minCostConnectPoints(int[][] points) {
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        int n = points.length;
        int[] distance = new int[n];
        for(int i=1;i<n;i++){
            distance[i]=getDistance(points,0,i);
        }
        int res = 0;
        while (visited.size()!=n){
            // Find the node that has shortest distance
            int next = -1;
            for(int i=0;i<n;i++){
                if(visited.contains(i)){
                    continue;
                }

                if(next==-1||distance[i]<distance[next]){
                    next = i;
                }
            }
           // Put the node into the Minning Spanning Tree
            visited.add(next);
            res+=distance[next];
            // Update distance array
            for(int i=0;i<n;i++){
                if(!visited.contains(i)){
                    distance[i] = getDistance(points,next,i);
                }
            }

        }

        return res;
    }

    private int getDistance(int[][] points,int a,int b){
        return Math.abs(points[a][0]-points[b][0])+Math.abs(points[a][1]-points[b][1]);
    }


    int res=Integer.MAX_VALUE;
    public int minStickers(String[] stickers, String target) {
        int n = target.length();
        int[] count = new int[26];
        int[] kind = new int[26];

        for(int i=0;i<n;i++){
            count[target.charAt(i)-'a']++;
            kind[target.charAt(i)-'a']++;
        }

        int[][] candidates = new int[stickers.length][26];
        int i=0;
        for(String stick:stickers){
            for(char c:stick.toCharArray()){
                candidates[i][c-'a']++;
                kind[c-'a'] = 0;
            }
            i++;
        }

        for(int value:kind){
            if(value!=0){
                return -1;
            }
        }


        dfsSearch(candidates,0,count,0);

        return res;
    }


    private void dfsSearch(int[][] candidates,int start,int[] nums,int count){

        boolean over = true;

        for(int value:nums){
            if(value!=0){
                over = false;
                break;
            }
        }

        if(over){
            res = Math.min(res,count);
            return;
        }
        if(count>=res){
            return;
        }

        for(int i=start;i<candidates.length;i++){
            boolean use = false;
            int[] candidate = candidates[i];
            int[] next = Arrays.copyOf(nums,26);
            for(int j=0;j<26;j++){
                if(next[j]>0&&candidate[j]>0){
                    use = true;
                    next[j]=next[j]>=candidate[j]?next[j]-candidate[j]:0;
                }
            }

            if(use){
                dfsSearch(candidates,i,next,count+1);
            }
        }

    }

        public static void main(String[] args) {
        MyMemory memory = new MyMemory();
        memory.minStickers(new String[]{"indicate","why","finger","star","unit","board","sister","danger","deal","bit","phrase","caught","if","other","water","huge","general","read","gold","shall","master","men","lay","party","grow","view","if","pull","together","head","thank","street","natural","pull","raise","cost","spoke","race","new","race","liquid","me","please","clear","could","reply","often","huge","old","nor"},"fhhfiyfdcwbycma");
    }

}
