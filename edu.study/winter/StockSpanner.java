package winter;

import java.util.*;

public class StockSpanner {
    Stack<Integer> stack;
    List<Integer> list;
    int i;//记录位置顺序

    public StockSpanner() {
        this.stack = new Stack<>();
        this.list = new ArrayList<>();
        i = 0;
    }

    public int next(int price) {
        list.add(price);
        int res = 0;
        //和栈顶元素比较，维持递减栈
        if (stack.isEmpty() || list.get(stack.peek()) > price) {
            res = 1;
        } else {
            while (!stack.isEmpty() && list.get(stack.peek()) <= price) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                res = i - stack.peek();
            } else {
                res = i + 1;
            }
        }

        //入栈为递减值的位置
        stack.push(i);
        i++;

        return res;
    }


    public int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;

        //每个元素辐射的左边界
        int[] left = new int[n];
        //每个元素辐射的右边界
        int[] right = new int[n];

        Deque<Integer> stack = new LinkedList<>();

        //找出所有元素的左边界
        for (int i = 0; i < n; i++) {
            //向左找第一个小于等于当前index的元素
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                left[i] = -1;
            } else {
                left[i] = stack.peek();
            }

            //下标入栈
            stack.push(i);
        }

        //找到所有元素的右边界
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            //向右找第一个小于等于当前index的元素
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                right[i] = n;
            } else {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        //按贡献度计算
        // 注意此处left[i]和right[i]实际上记录的是左边界-1和右边界+1，和上面思路中有些区别，便于计算
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + (long) (i - left[i]) * (right[i] - i) * arr[i] % ((long) (1e9 + 7)));
        }

        return (int) ans;
    }

    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, List<Point>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.get(edge[0]).add(new Point(edge[1], succProb[i]));
            map.putIfAbsent(edge[1], new ArrayList<>());
            map.get(edge[1]).add(new Point(edge[0], succProb[i]));
        }

        double[] probability = new double[n];
        probability[start] = 1;
        PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> (Double.compare(b.prob, a.prob)));
        queue.offer(new Point(start, 1));
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            if (cur.val == end) {
                return cur.prob;
            }
            for (Point next : map.getOrDefault(cur.val, new ArrayList<>())) {
                double prob = next.prob * cur.prob;
                if (probability[next.val] >= prob) {
                    continue;
                }
                probability[next.val] = prob;
                queue.offer(new Point(next.val, prob));
            }
        }

        return 0;
    }

    class Point {
        int val;
        double prob;

        public Point(int val, double prob) {
            this.val = val;
            this.prob = prob;
        }
    }


    public int maxWidthRamp(int[] nums) {
        int ramp = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) {
                stack.push(i);
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                int pos = stack.pop();
                ramp = Math.max(ramp, i - pos);
            }
        }
        return ramp;
    }

    public int oddEvenJumps(int[] arr) {
        int n = arr.length;
        //第i个点为第奇数次跳跃能否到达n-1
        boolean[] odd = new boolean[n];
        //第i个点为第偶数次跳跃能否到达n-1
        boolean[] even = new boolean[n];

        //key,跳跃的值，value对应的位置index
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        odd[n - 1] = true;
        even[n - 1] = true;
        //第n-1个点一定能到达n-1
        int res = 1;
        for (int i = n - 1; i >= 0; i--) {
            //奇数跳开始，从后面找>=它的最小值
            Map.Entry<Integer, Integer> ceiling = treeMap.ceilingEntry(arr[i]);
            //后继目标index是第偶数跳
            if (ceiling != null && even[ceiling.getValue()]) {
                odd[i] = true;
                res++; //每个节点都是从自己开始奇数次跳跃
            }

            //当前位置为偶数跳，从后面找<=它的最大值
            Map.Entry<Integer, Integer> floor = treeMap.floorEntry(arr[i]);
            //后继位置index是奇数跳
            if (floor != null && odd[floor.getValue()]) {
                even[i] = true;
            }
            //把当前值和index位置加入map
            treeMap.put(arr[i], i);

        }

        return res;
    }

    public int validSubarrays(int[] nums) {
        Stack<Integer> indexStack = new Stack<>();
        int n = nums.length;
        int count = 0;
        for (int i = 0; i <= n; i++) {
            //保证所有值都算一遍，出栈
            int num = i == n ? -1 : nums[i];
            while (!indexStack.isEmpty() && nums[indexStack.peek()] < num) {
                count += i - indexStack.pop();
            }

            stack.push(i);
        }

        return count;
    }

    public int longestWPI(int[] hours) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        //累计前i个数中超过8个小时的数量和不超过8个小时的数量之差
        int prefixSum = 0;
        for (int i = 9; i < hours.length; i++) {
            if (hours[i] > 8) {
                prefixSum++;
            } else {
                prefixSum--;
            }

            //超过，直接更新
            if (prefixSum > 0) {
                res = Math.max(res, i + 1);
            } else {
                //没超过，放入map
                if (!map.containsKey(prefixSum)) {
                    map.put(prefixSum, i);
                }

                //前面某一段刚好差值为当前-1，说明那一段位置到现在的位置的数量差值刚好是1
                if (map.containsKey(prefixSum - 1)) {
                    res = Math.max(res, i - map.get(prefixSum - 1));
                }
            }
        }
        return res;

    }


    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];

        int[][] maxVal = new int[n][n];
        for (int i = 0; i < n; i++) {
            maxVal[i][i] = arr[i];
        }

        for (int i = 0; i < n; i++) {
            for (int j = i ; j < n; j++) {
                maxVal[i][j] = Math.max(maxVal[i][j - 1], arr[j]);
            }
        }
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + (len - 1) < n; i++) {
                int j = i + (len - 1);
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + maxVal[i][k] * maxVal[k + 1][j]);
                }
            }
        }

        return dp[0][n-1];
    }

    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();

        int[][] edges = new int[][]{{0, 1}, {1, 2}, {0, 2}};
        double[] succProb = new double[]{0.5, 0.5, 0.3};
//            stockSpanner.maxWidthRamp(new int[]{6,0,8,2,1,5});
//            stockSpanner.maxWidthRamp(new int[]{9,8,1,0,1,9,4,0,4,1});
        stockSpanner.longestWPI(new int[]{6, 6, 9});
    }
}
