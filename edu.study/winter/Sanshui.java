package winter;

import java.util.*;

public class Sanshui {

    public String longestDupSubstring(String s) {
        int left=0;
        int right = s.length()-1;
        String res = "";
        while(left<right){
            int mid = right-(right-left)/2;
            String str = check(s,mid);
            if(str!=null){
                left = mid;
                res = str;
            }else{
                right = mid-1;
            }
        }
        return res;
    }

    public String check(String s, int len){
        for(int i=0;i<=s.length()-len;i++){
            String pre = s.substring(i,i+len);
            if(s.substring(i+1).contains(pre)){
                return pre;
            }
        }

        return null;
    }

    int N = (int)1e5+10, P = 131313;
    int[] h = new int[N], p = new int[N];
    public List<String> findRepeatedDnaSequences(String s) {
        int n = s.length();
        List<String> ans = new ArrayList<>();
        p[0] = 1;
        for (int i = 1; i <= n; i++) {
            h[i] = h[i - 1] * P + s.charAt(i - 1);
            p[i] = p[i - 1] * P;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i + 10 - 1 <= n; i++) {
            int j = i + 10 - 1;
            int hash = h[j] - h[i - 1] * p[j - i + 1];
            int cnt = map.getOrDefault(hash, 0);
            if (cnt == 1) ans.add(s.substring(i - 1, i + 10 - 1));
            map.put(hash, cnt + 1);
        }
        return ans;
    }


    private String rabinKarp(String s, int len, int[] power) {
        if(len==0)return "";
        int n =s.length();
        int cur =0;
        int M = (int) (1e7+7);
        Map<Integer,List<Integer>> hash=new HashMap<>();
        for(int i=0;i<len;i++){
            cur = (cur*26+(s.charAt(i)-'a'))%M;
        }

        hash.putIfAbsent(cur,new ArrayList<>());
        hash.get(cur).add(0);
        for(int i=len;i<n;i++){
            cur = ((cur-power[len-i]*(s.charAt(i-len)-'a'))%M+M)%M;
            cur =   cur*26+(s.charAt(i)-'a')%M;
            if(!hash.containsKey(cur)){
                hash.putIfAbsent(cur,new ArrayList<>());
                hash.get(cur).add(i-len+1);
            }else {
                for(int idx:hash.get(cur)){
                    if(s.substring(idx,idx+len).equals(s.substring(idx-len+1,idx))){
                        return s.substring(idx,idx+len);
                    }
                    hash.get(cur).add(i-len+1);
                }
            }

        }


        return "";
    }

    public int equalSubstring(String s, String t, int maxCost) {
        int m = s.length();
        int n=t.length();
        int len = Math.min(m,n);
        int[] preSum = new int[len+1];
        //preSum[i]表示s和t的前i个字符的ASCII差值
        for(int i=0;i<len;i++){
            preSum[i+1]=preSum[i]+Math.abs(s.charAt(i)-t.charAt(i));
        }

        int left =0;
        int right = len;//最短自然是0，最长就是s和t的最短长度
        int ans = 0;
        while (left<=right){
            int mid = left+right>>1;
            if(check(s,t,maxCost,mid,preSum)){//可以转换出mid长度的字符串，说明可能可以更长，排除小的范围
                ans = mid;//保存有效值
                left = mid+1;
            }else {//不能转换出mid长度的字符串，说明mid太长，排除大的范围
                right = mid-1;
            }
        }

        return ans;
    }

    private boolean check(String s, String t, int maxCost, int len, int[] preSum) {
        int m = s.length();
        int n=t.length();
        for(int start=0;start+len-1<Math.min(m,n);start++){
            int end = start+len-1;
            int cost = preSum[end]-preSum[start];
            if(cost<=maxCost){
                return true;
            }
        }

        return false;
    }

    public int longestSubarray(int[] nums, int limit) {
//        maxDeque and minDeque are used to store the max and min value
//        in this particular subarray which range is from the left pointer(l) to right pointer(r).
                Deque<Integer> maxQueue = new LinkedList<>();
        Deque<Integer> minQueue = new LinkedList<>();

        int res =1;
        int left =0;

        // find the longest subarray for every right pointer by shrinking left pointer
        for(int right =0;right<nums.length;right++){
            // update maxDeque with new right pointer
            while (!maxQueue.isEmpty()&&maxQueue.peekLast()<nums[right]){
                maxQueue.removeLast();
            }

            maxQueue.addLast(nums[right]);

            // update minDeque with new right pointer
            while (!minQueue.isEmpty()&&minQueue.peekLast()>nums[right]){
                minQueue.removeLast();
            }
            minQueue.addLast(nums[right]);
            while (maxQueue.peekFirst()-minQueue.peekFirst()>limit){
                if(maxQueue.peekFirst()==nums[left]){
                    maxQueue.pollFirst();
                }
                if(minQueue.peekFirst()==nums[left]){
                    minQueue.pollFirst();
                }

                left++;
            }

            res=Math.max(res,right-left+1);
        }

        return res;
    }

    public int chalkReplacer(int[] chalk, int k) {
        long sum=0;
        for(int i=0;i<chalk.length;i++){
            sum+=chalk[i];
        }

        //跳过完整的轮数
        k = (int) (k%sum);
        int count =0;
        while (k>0){
            k-=chalk[count++];
        }

        //k=0说明count-1那个人需要的数量刚好满足
        //<0说明count-1那个人需要的数量还没有满足
        return k==0? count:count-1;
    }

    public int findMaximumXOR(int[] nums) {
        int maxResult = 0;
        int mask = 0;
        /*The maxResult is a record of the largest XOR we got so far.
        if it's 11100 at i = 2, it means
        before we reach the last two bits,
        11100 is the biggest XOR we have, and we're going to explore
        whether we can get another two '1's and put them into maxResult

        This is a greedy part, since we're looking for the largest XOR,
        we start from the very begining, aka,
        the 31st postition of bits. */
        for (int i = 31; i >= 0; i--) {

            //The mask will grow like  100..000 , 110..000, 111..000,  then 1111...111
            //for each iteration, we only care about the left parts
            mask = mask | (1 << i);

            Set<Integer> set = new HashSet<>();
            for (int num : nums) {

/*                we only care about the left parts, for example, if i = 2, then we have
                {1100, 1000, 0100, 0000} from {1110, 1011, 0111, 0010}*/
                int leftPartOfNum = num & mask;
                set.add(leftPartOfNum);
            }

            // if i = 1 and before this iteration, the maxResult we have now is 1100,
            // my wish is the maxResult will grow to 1110, so I will try to find a candidate
            // which can give me the greedyTry;
            int greedyTry = maxResult | (1 << i);

            for (int leftPartOfNum : set) {
                //This is the most tricky part, coming from a fact that if a ^ b = c, then a ^ c = b;
                // now we have the 'c', which is greedyTry, and we have the 'a', which is leftPartOfNum
                // If we hope the formula a ^ b = c to be valid, then we need the b,
                // and to get b, we need a ^ c, if a ^ c exisited in our set, then we're good to go
                int anotherNum = leftPartOfNum ^ greedyTry;
                if (set.contains(anotherNum)) {
                    maxResult= greedyTry;
                    break;
                }
            }

            // If unfortunately, we didn't get the greedyTry, we still have our max,
            // So after this iteration, the max will stay at 1100.
        }

        return maxResult;
    }


        public static void main(String[] args) {
        Sanshui sanshui = new Sanshui();
    }
}
