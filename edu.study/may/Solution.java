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

    public int sumRootToLeaf(TreeNode root) {
        return sumRootToLeaf(root,0);
    }

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }


    public int sumRootToLeaf(TreeNode root,int pre) {
        if(root==null){
            return pre;
        }

        if(root.left==null&&root.right==null){
            System.out.println(root.val+2*pre);
            System.out.println(root.val);
            return root.val+2*pre;
        }

        if(root.left==null){
            return sumRootToLeaf(root.right,root.val+2*pre);
        }

        if(root.right==null){
            return sumRootToLeaf(root.left,root.val+2*pre);
        }
        return sumRootToLeaf(root.right,root.val+2*pre)+sumRootToLeaf(root.right,root.val+2*pre);
    }

    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if(n<=2){
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for(int i=n-1;i>=0;i--){
            if(!stack.isEmpty()&&stack.peek()>=nums[i]){
                if(stack.size()>=2){
                    int max =stack.pop();
                    int min = max;
                    while(!stack.isEmpty()){
                        min = stack.pop();
                        if(min>nums[i]){
                            return true;
                        }
                    }

                    stack.push(min);
                    stack.push(max);
                }

            }else{
                stack.push(nums[i]);
            }

        }

        return false;

    }

    public int minOperations(int[] nums1, int[] nums2) {
        int target = 0;
        int sum = 0;

        for (int num : nums1) {
            sum += num;
        }

        for (int num : nums2) {
            target += num;
        }

        if (target == sum) {
            return 0;
        }

        Arrays.sort(nums1);
        int res = 0;
        if (target < sum) {
            int count = sum - target;
            for (int i = nums1.length - 1; i >= 0; i--) {

                res++;
                count -= nums1[i] - 1;
                if (count <= 0) {
                    return res;
                }
            }

        } else {
            int count = target - sum;
            for (int i = 0; i < nums1.length; i++) {
                res++;
                count -= 6 - nums1[i];
                if (count <= 0) {
                    return res;
                }
            }

        }

        return -1;

    }

    public int subarraysWithKDistinct(int[] nums, int k) {
        int res =0;
        Map<Integer,Integer> map = new HashMap<>();
        int start =0;
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            while(map.size()>k){
                if(map.get(nums[start])==1){
                    map.remove(nums[start]);
                }
                if(map.getOrDefault(nums[start],0)>1){
                    map.put(nums[start],map.get(nums[start])-1);
                }
                start++;
            }

            if(map.size()==k){
                res+=(i-start+1-k+1);
            }
        }

        return res;
    }

    public int countDistinct(int[] nums, int k, int p) {
        int start = 0;
        Map<Integer,Integer> map = new HashMap<>();

        int count=0;
        int res = 0;
        for(int i=0;i<nums.length;i++){
            int value = nums[i];
            map.put(value,map.getOrDefault(value,0)+1);
            if(value%p==0){
                count++;
            }

            while(count>k){
                if(map.get(nums[start])==1){
                    map.remove(nums[start]);
                }else{
                    map.put(nums[start],map.getOrDefault(nums[start],0)-1);
                }
                start++;
                if(nums[start]%p==0){
                    count--;
                }
            }
            res+=i-start+1;
        }

        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(0);
//        root.left.left = new TreeNode(0);
//        root.left.right = new TreeNode(1);
//        root.right = new TreeNode(1);
//        root.right.left = new TreeNode(0);
//        root.right.right = new TreeNode(1);
//        solution.sumRootToLeaf(root);

//        solution.largestRectangleArea(new int[]{2,1,2});

        solution.countDistinct(new int[]{2,3,3,2,2},2,2);
    }

    public String removeOuterParentheses(String S) {
        StringBuilder s = new StringBuilder();
        int opened = 0;
        for (char c : S.toCharArray()) {
            if (c == '(' && opened++ > 0) s.append(c);
            if (c == ')' && opened-- > 1) s.append(c);
        }
        return s.toString();
    }

    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer,Integer> map = new HashMap<>();
        for(List<Integer> list:wall){
            int sum =0;
            for(int i=0;i<list.size()-1;i++){
                sum+=list.get(i);
                map.put(sum,map.getOrDefault(sum,0)+1);
            }
        }

        int count = 0;
        for(int val:map.values()){
            count = Math.max(count,val);
        }

        return wall.size()-count;
    }


    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        LinkedList<TreeNode> nodes = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        if(canFind(root,target,nodes)){
            int len = nodes.size();
            //not over the distance
            for(int i=len-1;i>=len-1-k&&i>=0;i--){
                int distance = len-1-i;//current distance
                //keep search leftover distance
                //avoid repeate path by nodes
                search(res,nodes,k-distance,nodes.get(i));

            }
        }

        return res;
    }

    //judge if could find the node, and list record the path from root to target
    public boolean canFind(TreeNode root, TreeNode target,LinkedList<TreeNode> list){
        if(root==null){
            return false;
        }
        if(root.val==target.val){
            list.add(root);
            return true;
        }

        //recursion find target
        if(canFind(root.left,target,list)||canFind(root.right,target,list)){
            //add parent node
            list.addFirst(root);
            return true;
        }

        return false;

    }

    //from parent node to child node with distance
    private void search(List<Integer> list,List<TreeNode> visited,int distance,TreeNode root){
        if(root==null){
            return;
        }

        if(distance==0){
            list.add(root.val);
            return;
        }

        if(!visited.contains(root.left)){
            visited.add(root.left);
            search(list,visited,distance-1,root.left);
        }
        if(!visited.contains(root.right)){
            visited.add(root.right);
            search(list,visited,distance-1,root.right);
        }

    }

    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n];
        long windowSum = 0;
        Arrays.fill(res,-1);
        for(int i=0;i<2*k&&i<n;i++){
            windowSum+=nums[i];
        }

        int start = 0;
        for(int i=2*k;i<n;i++){
            windowSum+=nums[i];
            if(i>2*k){
                windowSum-=nums[i-2*k-1];
            }
            res[i-k]=(int)(windowSum/(2*k+1));
        }

        return res;
    }
}
