package winter;

import java.util.*;

public class Segtest {

    class SegmentNode {
        int start;
        int end;
        int info;
        SegmentNode left, right;

        public SegmentNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public void init(SegmentNode root, int start, int end) {
        if (start == end) {
            root.info = 0;
            return;
        }

        int mid = start + (end - start) / 2;
        if (root.left == null) {
            root.left = new SegmentNode(start, mid);
            root.right = new SegmentNode(mid + 1, end);
        }

        init(root.left, start, mid);
        init(root.right, mid + 1, end);
    }

    public void updateSingle(SegmentNode root, int id, int val) {
        if (id < root.start || id > root.end) {
            return;
        }

        if (root.start == root.end) {
            root.info += val;
            return;
        }

        updateSingle(root.left, id, val);
        updateSingle(root.right, id, val);
        root.info = root.left.info + root.right.info;
    }

    public int queryRange(SegmentNode root, int start, int end) {
        if (end < root.start || start > root.end) {
            return 0;
        }

        if (start <= root.start && end >= root.end) {
            return root.info;
        }

        return queryRange(root.left, start, end) + queryRange(root.right, start, end);
    }

    public int createSortedArray(int[] instructions) {
        long res = 0;
        long mod = (long) (1e9 + 7);
        List<Integer> list = new ArrayList<>();
        list.add(instructions[0]);

        for (int i = 1; i < instructions.length; i++) {
            int num = instructions[i];
            int index1 = findLower(list, num);
            int index2 = findUpper(list, num);
            res += Math.min(index1, list.size() - index2);
            res %= mod;
            list.add(index1, num);
        }

        return (int) res;
    }


    private int findUpper(List<Integer> list, int target) {
        int left = 0;
        int right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private int findLower(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = right - (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }


    public int createSortedArrayI(int[] instructions) {
        List<Integer> list = new ArrayList<>();
        for (int num : instructions) {
            if (list.contains(num)) {
                continue;
            }
            list.add(num);
        }
        Collections.sort(list);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }

        int n = list.size();
        SegmentNode root = new SegmentNode(0, n - 1);
        init(root, 0, n - 1);


        long res = 0;
        long mod = (long) (1e9 + 7);

        for (int num : instructions) {
            int index = map.get(num);
            int count1 = queryRange(root, 0, index - 1);
            int count2 = queryRange(root, index + 1, n - 1);
            res += Math.min(count1, count2);
            res %= mod;
            updateSingle(root, index, 1);
        }

        return (int) res;
    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] heights = new int[m][n];
        for (int j = 0; j < n; j++) {
            heights[0][j] = matrix[0][j] == '1' ? 1 : 0;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                heights[i][j] = matrix[i][j] == '1' ? heights[i - 1][j] + 1 : 0;

            }
        }

        int max = 0;
        for (int i = 0; i < m; i++) {
            max = Math.max(max, largestRectangleArea(heights[i]));
        }

        return max;
    }

    public int largestRectangleArea(int[] height) {
        Stack<Integer> stack = new Stack<>();

        int max = 0;
        for (int i = 0; i <= height.length; i++) {
            int cur = i == height.length ? -1 : height[i];
            while (!stack.isEmpty() && height[stack.peek()] > cur) {

                int h = height[stack.pop()];//旧的栈顶元素值
                //矩形宽度，就是当前元素-栈顶元素的index,因为不包括新的栈顶元素所以减一
                //若弹出之后没有值，那么说明栈中原先只有一个元素，说明左边右边都比栈中的元素高，
                // 那么宽度当然可以取到当前元素的index
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                int area = width * h;
                max = Math.max(max, area);
            }
            stack.push(i);
        }

        return max;
    }

    public String removeDuplicateLetters(String s) {
        if (s == null || s.equals("")) {
            return null;
        }

        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        Stack<Character> stack = new Stack<>();
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            count[c - 'a']--;
            if (!set.add(c)) {
                continue;
            }

            while (!stack.isEmpty() && count[stack.peek() - 'a'] > 0 && c <= stack.peek()) {
                set.remove(stack.pop());
            }
            stack.push(c);
        }

        StringBuffer sb = new StringBuffer();
        while (!stack.isEmpty()) {
            char c = stack.pop();
            sb.insert(0, c);
        }

        return sb.toString();


    }


    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        int[] res = new int[k];

        for (int i = Math.max(0, k - m); i <= k && i <= n; i++) {
            int[] array1 = findMax(nums1, i);
            int[] array2 = findMax(nums2, k - i);
            int[] candidates = merge(array1, array2, k);
            res = greater(candidates, 0, res, 0) ? candidates : res;
        }
        return res;
    }

    private int[] merge(int[] array1, int[] array2, int k) {
        int[] res = new int[k];
        for (int i = 0, j = 0, r = 0; r < k; r++) {
            res[r] = greater(array1, i, array2, j) ? array1[i++] : array2[j++];
        }
        return res;
    }

    private boolean greater(int[] array1, int i, int[] array2, int j) {
        while (i < array1.length && j < array2.length && array1[i] == array2[j]) {
            i++;
            j++;
        }

        return j == array2.length || (i < array1.length && array1[i] > array2[j]);
    }

    public int[] findMax(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[k];
        int j = 0;

        for (int i = 0; i < n; i++) {
            //可以挑选的名额n-i+j
            while (n - i + j > k && j > 0 && nums[i] > res[j - 1]) {
                j--;
            }
            if (j < k) {
                res[j++] = nums[i];
            }

        }

        return res;
    }

    public String removeKdigits(String num, int k) {
        int n = num.length();
        if (k >= n) {
            return "0";
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            char c = num.charAt(i);
            while (!stack.isEmpty() && n - i + stack.size() > (n - k) && stack.peek() > c) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }

        String ans = sb.toString();
        while (ans.startsWith("0")) {
            ans = ans.substring(1);
        }


        return ans.length() == 0 ? "0" : ans;

    }

    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return false;
        }

        Stack<Integer> stack = new Stack<>();
        int third = Integer.MIN_VALUE;
        for(int i=n-1;i>=0;i--){
            //找到1就返回
            if(nums[i]<third){
                return true;
            }
            //保证了32模式，同时用third变量记录2的值
            while (!stack.isEmpty()&&nums[i]>stack.peek()){
                third = stack.pop();

            }

            stack.push(nums[i]);
        }

        return false;
    }


    public int maxChunksToSorted(int[] arr) {
       int count =0;
       int[] nums = arr.clone();
       Arrays.sort(nums);
       long sum1 =0;
       long sum2 = 0;
       for(int i=0;i<arr.length;i++){
           sum1+=arr[i];
           sum2+=nums[i];
           if(sum1==sum2){
               count++;
           }
       }

       return count;
    }

    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        double[][] car = new double[n][2];

        for(int i=0;i<n;i++){
            car[i][0] = position[i];
            //到达终点的时间
            car[i][1] = (double)(target-position[i])/speed[i];
        }

        //按位置从小到大排序
        Arrays.sort(car,Comparator.comparingDouble(a->a[0]));

        //从离终点最近的那一辆车开始算，后面的车能不能追上它
        int ans = 0;
        for(int i=n-1;i>=1;i--){
            if(car[i][1]<car[i-1][1]){//离终点较近那辆车先到达终点，不属于一个车队
                ans++;
            }else {
                car[i-1] = car[i];//离终点较近那辆车后到达终点，以它的速度为准，
            }
        }

        return ans;
    }

        public static void main(String[] args) {
//        int[] nums = new int[]{1, 0, 1, -4, -3};
        int[] nums = new int[]{4,3,2,1,0};
        Segtest segtest = new Segtest();
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{};

        segtest.maxChunksToSorted(nums);
    }

}
