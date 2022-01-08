package unlock;

import java.util.*;

public class BinarySearch {
    public double minmaxGasDist(int[] stations, int k) {
        // Write your code here

        double left = 0;
        double right = stations[stations.length-1]-stations[0];


        while(left+1e-6<right){
            double mid = left + (right-left)/2.0;
            boolean valid = isValid(stations, mid,k);
            if(valid){
                left = mid;
            }else {
                right = mid;
            }
        }

        return right;
    }

    public boolean isValid(int[] stations, double target,int k){
        int count =0;
        for(int i=1;i<stations.length;i++){
            int dist = stations[i]-stations[i-1];
            if(dist>target){
                count+=Math.ceil(dist/target)-1;
                if(count>k){
                    return true;
                }
            }
        }
        return false;
    }


    public int swimInWater(int[][] grid) {
        int n = grid.length;
        //不然起点都站不住
        int left =grid[0][0];
//        int left =0;
        int right =n*n;

        while(left<right){
            int mid = left+(right-left)/2;
            if(isValid(grid,mid)){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return left;
    }

    //BFS判断能否到达终点
    public boolean isValid(int[][] grid,int mid){
        int[][] dirs = new int[][]{{0,1},{0,-1},{-1,0},{1,0}};
        int n = grid.length;

        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        visited[0][0] = true;
        queue.add(new int[]{0,0});
        while(!queue.isEmpty()){
            int[] position = queue.poll();
            int x = position[0];
            int y = position[1];
            if(x==n-1&&y==n-1){
                return true;
            }
            for(int[] dir:dirs){
                int i=x+dir[0];
                int j = y+dir[1];
                if(i<0||j<0||i>=n||j>=n||visited[i][j]||grid[i][j]>mid){
                    continue;
                }
                visited[i][j]=true;
                queue.add(new int[]{i,j});

            }
        }

        return false;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int left =1;
        int right = (int)1e9;
        while(left<right){
            int mid = left+(right-left)/2;
            int count =0;
            for(int num:piles){
                count+=(num+mid-1)/mid;
            }

            if(count<=h){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return left;
    }

    public int nthMagicalNumber(int n, int a, int b) {
        if(a>b){
            int tmp = a;
            a = b;
            b = tmp;
        }
        //最大公约数
        int gcd = getGCD(a,b);
        //最小公倍数
        long lcm = (a*b)/gcd;
        //可能的最小值
        long left = Math.min(a,b);
        //可能的最大值
        long right = n*Math.min(a,b);
        long mod = (long) (Math.pow(10,7)*7);


        while (left<right){
            long mid = left+(right-left)/2;
            //在mid之前的魔法数,mid/a 是除数为a的魔法数数量，mid/b同理
            //但是有的数可能同时是a,b的魔法数，也就是这个数是a,b的最小公倍数的倍数的时候
            //等于这些值数了两遍，所以要减去一遍
            int count = (int) ((mid/a)+(mid/b)-(mid/lcm));
            if(count<n){
                left=mid+1;
            }else {
                right = mid;
            }

        }

        return (int) (left%mod);
    }

    //辗转相除法
    public int getGCD(int a,int b){
        while (b>0){
            int temp = a;
            a=b;
            b= temp%b;
        }

        return a;
    }


    public int hIndex(int[] citations) {
        int left =citations[0];
        int n = citations.length;
        int right = citations[n-1];
        while(left<right){
            int mid = right -(right-left)/2;
            if(isValid(citations,mid)){
                left = mid;
            }else{
                right = mid-1;
            }
        }

        return left;
    }

    public boolean isValid(int[] citations,int target){
        int left =0;
        int right = citations.length;
        while(left<right){
            int mid = left+(right-left)/2;
            if(citations[mid]<target){
                left = mid+1;
            }else{
                right = mid;
            }
        }

        int count = citations.length - left;
        return count>=target;
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for(int i=0;i<nums.length;i++){
            int num = nums[i];
            Integer floor = treeSet.floor(num);
            Integer ceiling = treeSet.ceiling(num);
            //找到了校验差值
            if((floor!=null&&num-floor<=t)||(ceiling!=null&&ceiling-num<=t)){
                return true;
            }

            treeSet.add(num);

            //移除index距离不满足条件的值
            if(i-k>=0){
                treeSet.remove(nums[i-k]);
            }
        }
        return false;
    }

    public int divide(int dividend, int divisor) {
        if(dividend==0){
            return 0;
        }
        int sign = dividend/Math.abs(dividend);
        sign *= divisor/Math.abs(divisor);

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        return (int) (sign*Math.floor(dividend/divisor));
    }

    public int getMoneyAmount(int n) {
        if(n==1){
            return 0;
        }
        //值在区间[i,j]的最小花费
        int[][] dp = new int[n+1][n+1];
        //区间长度，1~n-1,
        for(int len = 1;len<n;len++){
            for(int i=0;i+len<=n;i++){
                int j = i+len;
                dp[i][j] =Integer.MAX_VALUE;
                //[i,j区间任意一个值都可能是结果，包括端点
                //因为端点只有一侧有值，所以，不存在的一侧设置为0
                for(int k=i;k<=j;k++){
                    dp[i][j]=Math.min(dp[i][j],k+Math.max(k-1>=i?dp[i][k-1]:0,j>=k+1?dp[k+1][j]:0));
                }
            }
        }

        return dp[1][n];
    }

    public int longestPalindrome(String s) {
        if(s.length()<=1){
            return s.length();
        }
        int[] lower = new int[26];
        int[] upper = new int[26];

        for(char c:s.toCharArray()){
            if(Character.isLowerCase(c)){
                lower[c-'a']++;
            }else {
                upper[c-'A']++;
            }
        }

        int res = 0;
        int odd=0;
        for(int num:lower){
            if(num%2==0){
                res+=num;
            }else {
                odd = Math.max(odd,num);
                res+=num-1;
            }
        }

        for(int num:upper){
            if(num%2==0){
                res+=num;
            }else {
                odd = Math.max(odd,num);
                res+=num-1;
            }
        }
        if(odd>1){
            res++;
        }

        return res;

    }

    public boolean canCross(int[] stones) {
        if(stones[1]-stones[0]>1){
            return false;
        }
        int n = stones.length;
        int m = 1+n;
        boolean[][] dp = new boolean[n][m];

        dp[1][1] = true;

        for(int i=2;i<n;i++){
            for(int j=1;j<i;j++){
                int pre = stones[j];
                int tail = stones[i];
                int k = tail-pre;
                if((k>0&&k<m&&dp[j][k])||(k-1>0&&k-1<m&&dp[j][k-1])||(k+1<m&&dp[j][k+1])){

                    dp[i][k] = true;
                }
            }
        }

        for(boolean res:dp[n-1]){
            if(res){
                return true;
            }
        }

        return false;
    }

    public int checkRecord(int n) {
        int[][][] dp =new int[n+1][2][3];
        int mod = 1000000007;
        dp[0] = new int[][]{{1,1,1},{1,1,1}};
        for(int i=1;i<=n;i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<3;k++){
                    //追加的字符是P, 所以j和k无所谓，
                    //为什么这里k是2, 不是3层循环吗代表最多几个
                    //因为后面追加的是p，所以不管dp[i-1]后面是什么都合法
                    int val = dp[i-1][j][k];
                    if(j>0){
                        val=(val+dp[i-1][j-1][k])%mod;
                    }
                    if(k>0){
                        val=(val+dp[i-1][j][k-1])%mod;
                    }
                    dp[i][j][k] = val;
                }
            }
        }

        return dp[n][1][2];
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        res.add(1);
        for(int i=1;i<=rowIndex;i++){
            List<Integer>  next =  new ArrayList<>();
            next.add(1);
            for(int j=0;j<res.size()-1;j++){
                next.add(res.get(j)+res.get(j+1));
            }
            next.add(1);
            res = next;
        }
        return res;
    }


    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for(int i=0;i<n-1;i++){
            char a = s.charAt(i);
            char b = s.charAt(i+1);
            if(isValid(a,b)){
                dp[i][i+1]=true;
            }
        }

        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                char a = s.charAt(i);
                char b = s.charAt(j);
                int len = j-i+1;
                if(isValid(a,b)){
                   if(len==3&&s.charAt(i+1)=='*'){
                       dp[i][j] = true;
                   }else if(dp[i+1][j-1]){
                       dp[i][j] = true;
                   }

                }

                if(dp[0][n-1]){
                    return true;
                }
            }
        }

        return dp[0][n-1];

    }

    public boolean isValid(char a, char b){
        if(a=='('&&b==')'){
            return true;
        }
        if(a=='('&&b=='*'){
            return true;
        }
        if(a=='*'&&b==')'){
            return true;
        }

        return false;
    }




        public static void main(String[] args) {
        BinarySearch test = new BinarySearch();
        String str ="civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
//        int[] arr = new int[]{0,1,2147483647};
//        boolean res = test.canCross(arr);
        test.checkValidString("(*))");
//        System.out.println(res);
    }
}
