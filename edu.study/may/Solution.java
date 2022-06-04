package may;

import java.util.*;

public class Solution {
    Map<Character,Integer> map = new HashMap<>(){{
        put('-',1);
        put('+',1);
        put('/',2);
        put('%',2);
        put('*',2);
        put('^',3);
    }};

    public int calculate(String s){
        s = s.replaceAll(" ","");
        char[] cs = s.toCharArray();
        int n = s.length();
        //store numbers
        Deque<Integer> nums = new ArrayDeque<>();
        nums.add(0);
        //store operations
        Deque<Character> ops = new ArrayDeque<>();

        for(int i=0;i<n;i++){
            char c = cs[i];
            if(c=='('){
                ops.addLast(c);
            }else if(c==')'){
                while (!ops.isEmpty()){//calculate result in ()
                    if(ops.peekLast()!='('){
                        calc(nums,ops);
                    }else {
                        ops.pollLast();
                        break;
                    }
                }
            }else {
                if(Character.isDigit(c)){
                    int number =0;
                    int j=i;
                    while (j<n&&Character.isDigit(cs[j])){
                        number=number*10+(cs[j++]-'0');
                    }
                    nums.addLast(number);
                    i=j-1;
                }else {
                    if(i>0&&(cs[i-1]=='(' || cs[i-1]=='+'||cs[i-1]=='-')){
                        nums.addLast(0);
                    }
                    while (!ops.isEmpty()&&ops.peekLast()!='('){
                        char prev = ops.peekLast();
                        if(map.get(prev)>=map.get(c)){//calculate left and right of operation
                            calc(nums,ops);
                        }else {
                            break;
                        }
                    }
                    ops.addLast(c);
                }
            }
        }

        while (!ops.isEmpty()){
            calc(nums,ops);
        }

        return nums.peekLast();
    }

    private void calc(Deque<Integer> nums, Deque<Character> ops) {
            if(nums.isEmpty()||nums.size()<2)return;
            if(ops.isEmpty())return;

            int b = nums.pollLast();
            int a = nums.pollLast();
            char op = ops.pollLast();
            int ans = 0;
            if(op=='+'){
                ans = a+b;
            }else if(op=='-'){
                ans=a-b;
            }else if(op=='/'){
                ans = a/b;
            }else if(op=='*'){
                ans = a*b;
            }else if(op=='^'){
                ans = a^b;
            }else if(op=='%'){
                ans = a%b;
            }
            //add the result operation to nums
            nums.addLast(ans);
    }

    public String addBinary(String a, String b) {
        StringBuilder res =new StringBuilder();
        int m = a.length()-1;
        int n = b.length()-1;
        int cur =0;
        while(m>=0&&n>=0){
            int num1= a.charAt(m--);
            int num2=  b.charAt(n--);
            cur = cur+num1+num2;
            res.append(cur%2+"");
            cur=cur/2;
        }
        while(m>=0){
            int num1= a.charAt(m--);
            cur = cur+num1;
            res.append(cur%2+"");
            cur=cur/2;
        }
        while(n>=0){
            int num2=  b.charAt(n--);
            cur = cur+num2;
            res.append(cur%2+"");
            cur=cur/2;
        }
        if(cur==1){
            res.append(cur);
        }
        return res.reverse().toString();
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int[] dp = new int[k];
        for(int num:nums){
            dp[num]+=1;
            int i=(k-1)/num;
            for(;i>1;i--){
                if(dp[i*num]!=0){
                    dp[i*num]+=dp[i];
                }
            }
        }

        int res = 0;
        for(int num:dp){
            res+=num;
        }
        return res;
    }

      public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public void reorderList(ListNode head) {
        if(head==null||head.next==null||head.next.next==null){
            return;
        }

        Deque<ListNode> queue = new LinkedList<>();

        ListNode cur = head.next;
        while(cur!=null){
            queue.offer(cur);
            cur = cur.next;
        }

        cur = head;
        while(!queue.isEmpty()){
            cur.next = queue.pollLast();
            if(!queue.isEmpty()){
                cur.next.next = queue.poll();
                cur = cur.next.next;
            }else {
                cur = cur.next;
            }
        }

        cur.next = null;


    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();

        int max =0;
        int n = heights.length;
        for(int i=0;i<=n;i++){
            int h = i<n? heights[i]:-1;
            while(!stack.isEmpty()&&heights[stack.peek()]>=h){
                int x = stack.pop();
                int width = stack.isEmpty()? i:i-stack.peek()-1;
                int size = heights[x] * width;
                max = Math.max(max,size);
            }
            stack.push(i);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
//        solution.reorderList(head);

        solution.largestRectangleArea(new int[]{2,1,2});
    }
}
