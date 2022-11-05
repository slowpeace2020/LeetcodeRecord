package septemper;

import java.util.*;

public class Thousand {
    public int longestPath(int[] parent, String s) {
        int step = 1;

        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int i=0;i<parent.length;i++){
            int p = parent[i];
            List<Integer> child = map.getOrDefault(p,new ArrayList<>());
            if(p!=-1||s.charAt(p)!=s.charAt(i)){
                child.add(i);
                map.put(p,child);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[parent.length];
        queue.add(0);
        visited[0] = true;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                int cur = queue.poll();
                List<Integer> child = map.getOrDefault(cur,new ArrayList<>());
                for(int next:child){
                    if(!visited[next]){
                        queue.offer(next);
                        visited[next] = true;
                    }
                }

            }
            step++;
        }


        return step;
    }

    public int countHighestScoreNodes(int[] parents) {

        int n = parents.length;
        int[][] memory = new int[n][2];
        for(int i=0;i<n;i++){
            Arrays.fill(memory[i],-1);
        }

        int count = 0;
        int product = 0;
        getChildNum(memory,parents,0);

        for(int i=0;i<n;i++){
            int left = memory[i][0];
            int right = memory[i][1];
            int p = n-1-left-right;
            int cur = Math.max(left,1)*Math.max(right,1)*Math.max(p,1);
            if(cur>product){
                count = 1;
                product = cur;
            }else if(cur==product){
                count++;
            }
        }

        return count;
    }


    public int getChildNum(int[][] memory,int[] parents, int cur){
        if(memory[cur][0]!=-1){
            return memory[cur][0]+Math.max(memory[cur][1],0);
        }

        int left = 0;
        int right = 0;
        for(int i=0;i<parents.length;i++){
            if(parents[i]==cur){
                if(left==0){
                    left = 1+getChildNum(memory,parents,i);
                }else{
                    right = 1+getChildNum(memory,parents,i);
                }

            }
        }

        memory[cur][0] = left;
        memory[cur][1] = right;

        return left+right;
    }

    public int numberOfWeakCharactersI(int[][] envelopes) {
        Arrays.sort(envelopes,(a,b)->(a[0]==b[0]?b[1]-a[1]:a[0]-b[0]));
        int[] dp = new int[envelopes.length];
        int size = 0;

        for(int[] envelope: envelopes) {
            // binary search
            int left = 0, right = size, middle = 0;     // right = size
            while(left < right) {
                middle = left + (right - left) / 2;
                if(dp[middle] < envelope[1]) left = middle + 1;
                else right = middle;
            }

            // left is the right position to 'replace' in dp array
            dp[left] = envelope[1];
            if(left == size) size++;
        }
        return size;
    }
    public int numberOfWeakCharacters(int[][] properties) {
        Arrays.sort(properties,(a,b)->(a[0]==b[0]?b[1]-a[1]:a[0]-b[0]));

        int n = properties.length;
        int size =0;
        int[] dp = new int[n];
        for(int i=0;i<n;i++){
            int val = properties[i][1];
            int left = 0;
            int right = size;
            while(left<right){
                int mid = left+(right-left)/2;
                if(dp[mid]<val){
                    left=mid+1;
                }else{
                    right = mid;
                }
            }
            dp[left] = val;
            if(left==size){
                size++;
            }
        }

        return size-1;
    }

    public int nthUglyNumber(int n, int a, int b, int c) {
        int min = Math.min(a,Math.min(b,c));
        long val = (long)min*n;
        long right = (long)Math.min(val,2*1e9);
        long left = min;
        while(left<right){
            long mid = left+(right-left)/2;
            int count =(int)count(a,b,c,mid);
            if(count<n){
                left=mid+1;
            }else{
                right = mid;
            }
        }

        return (int)left;
    }

    private long count(long a, long b, long c,long target){
        long ab = a*b/getMaxCommon((int)a,(int)b);
        long ac = a*c/getMaxCommon((int)a,(int)c);
        long bc = b*c/getMaxCommon((int)b,(int)c);
        long abc = ac*b/getMaxCommon((int)ac,(int)b);
        return target/a+target/b+target/c-target/ab-target/ac-target/bc+target/abc;
    }

    private int getMaxCommon(int a, int b){
        if(a<b){
            return getMaxCommon(b,a);
        }
        while (b!=0){
            int temp = a%b;
            a = b;
            b = temp;
        }

        return a;
    }


    public int maximizeSweetness(int[] sweetness, int k) {
        int left =1;
        int right = (int)1e9;
        while(left<right){
            int mid = left+(right-left)/2;
            if(canFitI(sweetness,mid,k)){
                left=mid+1;
            }else{
                right = mid;
            }
        }

        return left;
    }

    private boolean  canFitI(int[] sweetness,int target,int k){
        int count = 0;
        int chunkSum = 0;
        int i = 0;
        int n = sweetness.length;
        while(i<n){
            chunkSum+=sweetness[i++];
            if(chunkSum>=target){
                count++;
                chunkSum = 0;
            }

            if(count>=k+1){
                return true;
            }
        }

        return false;
    }


    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int left = 1;
        int right =position[position.length-1]-position[0];
        while(left<=right){
            int mid = left+(right-left)/2;
            if(canFit(position,m,mid)){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return left-1;
    }

    private boolean canFit(int[] position, int m,int target){
        int prev = 0;
        int count = 1;
        int i =1;
        int n = position.length;
        while(i<n){
            if(position[i]-position[prev]>=target){
                count++;
                prev = i;
            }
            if(count==m){
                return true;
            }
            i++;
        }

        return false;
    }

    boolean solution(int[] numbers) {
        int cur = -1;
        int n = numbers.length;
        for(int i=1;i<n;i++){
            if(numbers[i]<numbers[i-1]){
                if(cur==-1){
                    cur=i;
                }else{
                    return false;
                }
            }
        }

        if(cur!=-1){
            if(couldSwap(cur, numbers)||couldSwap(cur-1, numbers)||couldSwap(cur+1, numbers)){
                return true;
            }
            return false;
        }



        return true;

    }


    private boolean couldSwap(int cur,int[] numbers){
        int n = numbers.length;
        if(cur!=-1&&cur<n){
            int value = numbers[cur];
            int val = 0;
            while(value!=0){
                val=val*10+value%10;
                value = value/10;
            }
            System.out.println(val);
            if(cur>0&&numbers[cur-1]>=val||(cur+1<n&&numbers[cur+1]<=val)){
                return false;
            }
        }
        return true;
    }

    public int minSpeedOnTime(int[] dist, double hour) {
        int left =1;
        int right = (int)1e10;
        double time = 0;
        while(Math.abs(hour-time)>1e-7&&left<right){
            int mid = left + (right-left)/2;
            time = canFit(dist,mid);
            if(time<=hour){
                right = mid;
            }else{
                left = mid+1;
            }
        }
        time = canFit(dist,right);
        return time<=hour? right:-1;
    }


    private double canFit(int[] dist, double speed){
        double time = 0;
        int n = dist.length;
        for(int i=0;i<n-1;i++){
            time+=Math.ceil(dist[i]/speed);
        }

        time+=dist[n-1]/speed;
        return time;
    }

    public int findBestValue(int[] arr, int target) {
        int ans = 0;
        int max =0;
        for(int num:arr){
            ans+=num;
            if(max<num){
                max = num;
            }
        }
        if(ans<=target){
            return max;
        }
        int left = 0;
        int right = max;
        while(left<right){
            int mid = left+(right-left)/2;
            ans = sumFit(arr,mid);
            if(ans<target){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        ans = sumFit(arr,left);
        int ans2 = sumFit(arr,left-1);
        return Math.abs(target-ans)>=Math.abs(target-ans2)? left-1:left;
    }

    private int sumFit(int[] arr, int mid){
        int sum = 0;
        for(int num:arr){
            int val = num>mid?mid:num;
            sum+=val;
        }

        return sum;
    }


    public int maxLength(int[] ribbons, int k) {
        int left =1;
        int right = 100000;
        while(left<=right){
            int mid = left+(right-left)/2;
            if(canCut(ribbons,k,mid)){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return left-1;
    }

    private boolean canCut(int[] ribbons, int k,int len){
        int count =0;
        for(int rib:ribbons){
            count+=rib/len;
            if(count>=k){
                return true;
            }
        }

        return false;
    }

    public long[] kthPalindrome(int[] queries, int intLength) {
        long[] result = new long[queries.length];
        int i = 0;
        for(int num:queries){
            long half = (intLength+1)/2;
            long start = (long)Math.pow(10,half-1);
            long end = (long)Math.pow(10,half)-1;
            if(num<=(end-start+1)){
                String firstHalf = (start+num-1)+"";
                String secondHalf = (new StringBuilder(firstHalf)).reverse().toString();
                result[i++]=Long.parseLong(firstHalf+secondHalf.substring(intLength%2));
            }else {
                result[i++] = -1;
            }
        }

        return result;
    }

    public long kMirror(int k, int n) {
        long res = 0;
        // Maintain 2 lists for odd-lenth numbers & even-length numbers
        List<String> list1 = new ArrayList<>(), list2 = new ArrayList<>();

        list1.add("");
        list2.add("0");
        for (int i = 1; i < k && n > 0; i++){
            list2.add(Integer.toString(i));
            res += i;
            n--;
        }

        return res + constructMirrorNumbers(2, k, n, list1, list2);
    }

    long constructMirrorNumbers(int len, int k, int n, List<String> list1, List<String> list2) {
        // Found n smallest numbers
        if (n == 0)
            return 0;

        long res = 0;
        List<String> cur = new ArrayList<>();
        for (int i = 0; i < k && n > 0; i++) {
            // To construct numbers of length len, use list1 if len is even and list2 if len is odd
            List<String> list = len % 2 == 0 ? list1 : list2;
            for (int j = 0; j < list.size() && n > 0 ; j++) {
                String s = i + list.get(j) + i;
                cur.add(s);
                long num = Long.parseLong(s, k);
                // Not consider numbers with leading zeros
                if (i != 0 && isMirror(Long.toString(num))) {
                    n--;
                    res += num;
                }
            }
        }

        //replace the list with the current list
        if (len % 2 == 0) {
            list1 = cur;
        } else {
            list2 = cur;
        }

        return res + constructMirrorNumbers(len+1, k, n, list1, list2);
    }

    boolean isMirror(String s) {
        int len = s.length();
        int middle = len / 2 - 1;
        for (int i = 0; i <= middle; i++) {
            if (s.charAt(i) != s.charAt(len-1-i)) {
                return false;
            }
        }
        return true;
    }

    public int largestPalindrome(int n) {
        int left = (int)Math.pow(10,n);
        left--;
        while(left>9){
            StringBuilder right = new StringBuilder(left+"");
            right.reverse();
            Integer value = Integer.valueOf(left+right.toString());
            if(isValid(value,n)){
                return value%1337;
            }
            left--;
        }

        return 9;
    }

    private boolean isValid(int value,int n){
        int up = (int)Math.pow(10,n);
        up--;
        for(int i=up;i>=2;i--){
            if(value/i>up){
                return false;
            }
            if(value/i<=up&&value%i==0){
                return true;
            }
        }

        return false;

    }

    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int m = piles.size();
        int[][] memo = new int[m][k+1];
        return search(piles,memo,0,k);
    }

    private int search(List<List<Integer>> piles,int[][] memo,int start,int k){
        if(k==0||start>=piles.size()){
            return 0;
        }
        if(memo[start][k]!=0){
            return memo[start][k];
        }

        List<Integer> pile = piles.get(start);
        int max = search(piles,memo,start+1,k);
        int sum = 0;
        for(int i=0;i<k&&i<pile.size();i++){
            sum+=pile.get(i);
            int cur = sum+search(piles,memo,start+1,k-i-1);
            max = Math.max(max,cur);
        }
        memo[start][k] = max;
        return max;
    }

        public static void main(String[] args) {
        Thousand test = new Thousand();
//        test.findBestValue(new int[]{4,9,3}, 10);
           int[] queries = new int[]{1,2,3,4,5,90};
           int   intLength = 3;
        test.largestPalindrome(2);
    }
}
