package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.Stack;

import java.util.*;

public class Logger {
    private Map<String,Integer> map;

    public Logger(){
        map = new HashMap<>();
    }

    public boolean shouldPrintMessage(int timestamp, String message){
        if(map.getOrDefault(message,0)>timestamp){
            return false;
        }

        map.put(message,timestamp+10);
        return true;
    }


    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c:s.toCharArray()){
            if(c=='{'){
                stack.push('}');
            }else if(c=='['){
                stack.push(']');
            }else if(c=='('){
                stack.push(')');
            }else if(stack.isEmpty()||c!=stack.pop()){
                return false;
            }
        }
        return stack.isEmpty();
    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] heights = new int[m][n];
        for(int j=0;j<n;j++){
            heights[0][j] = matrix[0][j]=='1'?1:0;
        }

        for(int i=1;i<m;i++){
            for(int j=0; j<n;j++){
                heights[i][j] = matrix[i][j]=='1'?heights[i-1][j]+1:0;
            }
        }

        int max = 0;
        for(int i=0;i<m;i++){
            max = Math.max(max,largestRectangleArea(heights[i]));
        }

        return max;
    }


    public int largestRectangleArea(int[] height){
        Stack<Integer> stack = new Stack<>();

        int max = 0;
        for(int i=0;i<=height.length;i++){
            int cur = i==height.length ? -1:height[i];
            while(!stack.isEmpty()&&height[stack.peek()]>=cur){

                int  h = height[stack.pop()];//旧的栈顶元素值
                //矩形宽度，就是当前元素-栈顶元素的index,因为不包括新的栈顶元素所以减一
                //若弹出之后没有值，那么说明栈中原先只有一个元素，说明左边右边都比栈中的元素高，
                // 那么宽度当然可以取到当前元素的index
                int width = stack.isEmpty()?i:i-stack.peek()-1;
                int area = width*h;
                max = Math.max(max,area);
            }
            stack.push(i);
        }

        return max;

    }

    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int num =0;
        char sign = '+';
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                num=num*10+(c-'0');
            }

            if(!Character.isDigit(c)&&' '!=c||i==s.length()-1){
                if(sign=='-'){
                    stack.push(-num);
                }
                if(sign=='+'){
                    stack.push(num);
                }

                if(sign=='*'){
                    stack.push(stack.pop()*num);
                }

                if(sign=='/'){
                    stack.push(stack.pop()/num);
                }

                sign = c;
                num=0;
            }
        }

        int result =0;
        for(int i:stack){
            result+=i;
        }

        return result;
    }

    public String decodeString(String s) {
        Stack<Object> stack=new Stack<>();
        int number =0;
        for(char c:s.toCharArray()){
            if(Character.isDigit(c)){
                number=number*10+(c-'a');
            }else if(c=='['){
                //数字也以object的方式入栈
                stack.push(Integer.valueOf(number));
                number = 0;
            }else if(c==']'){//遇到右括号，出栈
                //将出栈的字符拼接起来成一个整体
                String newStr  = popStack(stack);
                //最后弹出重复次数
                Integer count = (Integer) stack.pop();
                //拼接重复结果
                for(int i = 0;i<count;i++){
                    stack.push(newStr);
                }
            }else {
                //每个字符都入栈
                stack.push(String.valueOf(c));
            }
        }


        return popStack(stack);
    }

    private String popStack(Stack<Object> stack) {
        Stack<String> buffer = new Stack<>();
        //数字之后的字符都组装拼接
        while (!stack.isEmpty()&&(stack.peek() instanceof String)){
            buffer.push((String) stack.pop());
        }

        StringBuilder sb = new StringBuilder();
        while (!buffer.isEmpty()){
            sb.append(buffer.pop());
        }
       return sb.toString();
    }

    public String getString(int count,String str){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<count;i++){
            sb.append(str);
        }
        return sb.toString();
    }


    public String minRemoveToMakeValid(String s) {
       StringBuilder sb = new StringBuilder(s);
       Stack<Integer> stack = new Stack<>();
       for(int i=0;i<s.length();i++){
           if(s.charAt(i)=='('){
               stack.push(i);
           }else if(s.charAt(i)==')'){
               if(stack.isEmpty()){
                   sb.setCharAt(i,'*');
               }else {
                   stack.pop();
               }
           }
       }

       while (!stack.isEmpty()){
           sb.setCharAt(stack.pop(),'*');
       }

       return sb.toString().replaceAll("\\*","");
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp =new int[n];
        Arrays.fill(dp,1);
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[j]+1,dp[i]);
                }
            }
        }

        int res = 1;
        for(int num:dp){
            if(num>res){
                res = num;
            }
        }

        return res;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n-k+1];
        Deque<Integer> deque = new LinkedList<>();
        int index = 0;
        for(int i=0;i<nums.length;i++){
            //当队列的第一个元素不在窗口范围，弹出
            while (!deque.isEmpty()&&deque.peek()<i-k+1){
                deque.poll();
            }

            //当队列的最后一个元素比新加入的元素小，
            //那它不再可能成为之后的候选最大值，因为它比当前元素先出局，又比它小
            //直接剔除
            while (!deque.isEmpty()&&nums[deque.peekLast()]<nums[i]){
                deque.pollLast();
            }

            deque.offer(nums[i]);
            if(i>=k-1){
                res[index++] = nums[deque.peek()];
            }
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
    public int[] nextLargerNodes(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        ListNode cur = head;
        Stack<Integer> stack = new Stack<>();
        while(cur!=null){
            list.add(cur);
            cur = cur.next;
        }
        int n = list.size();
        int[] res = new int[n];

        stack.push(list.get(list.size()-1).val);
        for(int i=n-2;i>=0;i--){
            int val = list.get(i).val;
            while (!stack.isEmpty()&&stack.peek()<val){
                stack.pop();
            }
            res[i] = stack.isEmpty()?0:stack.peek();
            stack.push(val);
        }

        return res;
    }

        public static void main(String[] args) {
        Logger test = new Logger();

            int[] l1 =new int[]{2,1,5};
            ListNode one = new ListNode(l1[0]);
            ListNode cur = one;
            for(int i=1;i<l1.length;i++){
                cur.next = new ListNode(l1[i]);
                cur = cur.next;
            }
        test.nextLargerNodes(one);
    }
}
