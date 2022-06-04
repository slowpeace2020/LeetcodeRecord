package feb;

import algs4.src.main.java.edu.princeton.cs.algs4.Stack;

import java.util.*;

public class CBTInserter {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    TreeNode root;

    public CBTInserter(TreeNode root) {
        this.root = root;
    }

    public int insert(int val) {
        List<TreeNode> list = new ArrayList<>();
        list.add(new TreeNode());
        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while (cur.size() != 0) {
            list.addAll(cur);
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode node : cur) {
                if (node.left != null) {
                    next.add(node.left);
                }
                if (node.right != null) {
                    next.add(node.right);
                }
            }

            cur = next;
        }
        int n = list.size();
        TreeNode child = new TreeNode(val);
        TreeNode parent = list.get(n / 2);
        if (n % 2 == 1) {
            parent.right = child;
        } else {
            parent.left = child;
        }

        return parent.val;

    }

    public TreeNode get_root() {
        return root;
    }

    public String reverseOnlyLetters(String s) {
        char[] arr = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetter(arr[i])) {
                i++;
            }
            while (i < j && !Character.isLetter(arr[j])) {
                j--;
            }
            if (i != j) {
                char temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

        }
        return new String(arr);

    }

    public List<String> wordSubsets(String[] words1, String[] words2) {
        int n = words2.length;
        int[] compare = new int[26];
        for (int i = 0; i < n; i++) {
            String word = words2[i];
            int[] count = new int[26];
            for (char c : word.toCharArray()) {
                count[c - 'a']++;
            }

            for (int j = 0; j < 26; j++) {
                if (count[j] > compare[j]) {
                    compare[j] = count[j];
                }
            }
        }

        List<String> list = new ArrayList<>();
        for (String word : words1) {
            if (isSubset(word, compare)) {
                list.add(word);
            }
        }

        return list;


    }

    private boolean isSubset(String word, int[] count) {
        int[] chs = new int[26];
        for (char c : word.toCharArray()) {
            chs[c - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] > chs[i]) {
                return false;
            }
        }
        return true;
    }


    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        if (power < tokens[0]) {
            return 0;
        }

        int n = tokens.length;
        int i = 0;
        int j = n - 1;
        int score = 0;
        int max = 0;
        while (i <= j) {
            if (power >= tokens[i]) {
                power -= tokens[i];
                score++;
                i++;
            } else {
                if (score > 0) {
                    power += tokens[j];
                    j--;
                    score--;
                } else {
                    break;
                }
            }

            max = Math.max(score, max);
        }

        return max;
    }


    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }
        Map<Integer, Character> map = new HashMap<>();
        map.put(10, 'a');
        map.put(11, 'b');
        map.put(12, 'c');
        map.put(13, 'd');
        map.put(14, 'e');
        map.put(15, 'f');
        boolean transfer = num >= 0 ? false : true;
        num = Math.abs(num);
        StringBuilder res = new StringBuilder();
        while (num > 0) {
            int current = num % 16;

            if (current >= 10) {
                res.insert(0, map.get(current));
            } else {
                res.insert(0, current);
            }

            num = num / 16;
        }

        if (transfer) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < res.length(); i++) {
                char cur = res.charAt(i);
                if (Character.isDigit(cur)) {
                    if (cur != '0') {
                        if (cur < '6') {
                            cur = map.get(15 - (cur - '0'));
                        } else {
                            cur = (char) ('0' + 15 - (cur - '0'));
                        }
                    }
                    sb.append(cur);
                } else {
                    int number = 'f' - cur;
                    sb.append(number);
                }
            }
            int count = 8 - res.length();
            while (count > 0) {
                sb.insert(0, 'f');
                count--;
            }
            res = sb;
        }

        return res.toString();
    }

    public TreeNode bstToGst(TreeNode root) {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.right;
            } else {
                cur = stack.pop();
                cur.val += sum;
                sum = cur.val;
                cur = cur.left;
            }
        }

        return root;
    }

    private TreeNode contsruct() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(1);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(2);
        root.left.right.right = new TreeNode(3);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(8);
        return root;
    }

    public boolean checkPerfectNumber(int num) {
        int n = (int) Math.sqrt(num);
        int sum = 1;
        Set<Integer> set = new HashSet<>();
        for (int i = 2; i <= n; i++) {
            if (num % i == 0 && set.add(i)) {
                sum += i;
                if (set.add(num / i)) {
                    sum += num / i;
                }
            }
        }

        return sum == num;
    }


    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, Set<Point>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int pre = edges[i][0];
            int behind = edges[i][1];
            double pos = succProb[i];
            map.putIfAbsent(pre, new HashSet<>());
            map.putIfAbsent(behind, new HashSet<>());
            map.get(pre).add(new Point(behind, pos));
            map.get(behind).add(new Point(pre, pos));
        }

        double[] pos = new double[n];
        pos[start] = 1;
        PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> (Double.compare(b.posibility, a.posibility)));
        queue.offer(new Point(start, 1));
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            for (Point next : map.getOrDefault(cur.vetex, new HashSet<>())) {
                double val = next.posibility * cur.posibility;
                if (val > pos[next.vetex]) {
                    pos[next.vetex] = val;
                    queue.offer(new Point(next.vetex, val));
                }
            }
        }

        return pos[end];
    }

    class Point {
        int vetex;
        double posibility;

        public Point(int vetex, double posibility) {
            this.vetex = vetex;
            this.posibility = posibility;
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            int target = -nums[i];
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }

        }

        return res;
    }

    public int threeSumSmaller(int[] nums, int target) {
        // Write your code here
        int res = 0;
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int temp = target - nums[i];
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                if (nums[left] + nums[right] > temp) {
                    right--;
                } else {
                    left++;
                }
            }

            if (nums[left] + nums[right] <= temp) {
                res += -left;
            }
        }

        return res;
    }


    public long minimumDifference(int[] nums) {
        int m = nums.length;
        int n = m / 3;
        long[] sum = new long[m + 1];

        for (int i = 0; i < m; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        long res = Integer.MAX_VALUE;

        for (int i = n; i <= m; i++) {
            //first
            long first;
            //remove
            long third;
            //second
            long second;

            if (i <= 2 * n) {
                first = sum[i] - sum[i - n];
                second = sum[2 * n] - first;
                third = sum[m] - sum[2 * n];
                res = Math.min(res, first - third);
                res = Math.min(res, second - third);
            } else {
                first = sum[n];
                second = sum[i] - sum[i - n];
                third = sum[m] - first - second;
                res = Math.min(res, first - third);
            }

        }
        return res;
    }

    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[] res = new int[n];
        int[] map = new int[n];
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> (intervals[a][0] - intervals[b][0] == 0 ? intervals[a][1] - intervals[b][1] : intervals[a][0] - intervals[b][0]));
        int k = 0;
        for (int i = 0; i < n; i++) {
            queue.offer(i);
        }

        while (!queue.isEmpty()) {
            res[queue.peek()] = k;
            map[k] = queue.poll();
            k++;
        }

        int[] count = new int[n];


        for (int i = 0; i < n; i++) {
            int[] cur = intervals[i];
            int index = res[i] - 1;
            if (index >= 0) {
                int next = map[index];
                while (index >= 0 && (intervals[next][1] <= cur[1] || intervals[next][0] <= cur[0])) {
                    index--;
                    if (index >= 0) {
                        next = map[index];
                    }
                }
            }

            if (index >= 0) {
                count[i] = map[index];
            } else {
                count[i] = -1;
            }
        }

        return count;
    }

    public boolean validPath(int n, int[][] edges, int source, int destination) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        boolean[] visited = new boolean[n];
        for (int[] edge : edges) {
            int first = edge[0];
            int second = edge[1];
            map.putIfAbsent(first, new HashSet<>());
            map.putIfAbsent(second, new HashSet<>());
            map.get(first).add(second);
            map.get(second).add(first);
        }

        Queue<Integer> queue = new LinkedList<>();

        visited[source] = true;

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            if (cur == destination) {
                return true;
            }
            Set<Integer> set = map.getOrDefault(cur, new HashSet<>());
            for (Integer next : set) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }

        return false;
    }


    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        List<String> res = new ArrayList<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, Set<String>> map = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            List<String> ingredit = ingredients.get(i);
            inDegree.put(recipe, ingredit.size());
            for (String pre : ingredit) {
                map.putIfAbsent(pre, new HashSet<>());
                map.get(pre).add(recipe);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (String supply : supplies) {
            queue.add(supply);
        }

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            Set<String> set = map.getOrDefault(cur, new HashSet<>());
            for (String next : set) {
                if (inDegree.containsKey(next)) {
                    int depend = inDegree.get(next);
                    if (depend == 1) {
                        queue.offer(next);
                        res.add(next);
                        inDegree.remove(next);
                    } else {
                        inDegree.put(next, depend - 1);
                    }
                }
            }
        }

        return res;

    }

    public String getDirections(TreeNode root, int startValue, int destValue) {


        TreeNode anc = getAncestor(root, startValue, destValue);
        StringBuilder ancToStart = new StringBuilder();
        StringBuilder ancToDest = new StringBuilder();


        traverse(anc, startValue, ancToStart);
        traverse(anc, destValue, ancToDest);

        String s = "U";

        s = s.repeat(ancToStart.length());

        return s + ancToDest.toString();

    }

    TreeNode getAncestor(TreeNode root, int p, int q) {

        if (root == null || root.val == p || root.val == q) return root;

        TreeNode left = getAncestor(root.left, p, q);

        TreeNode right = getAncestor(root.right, p, q);

        if (left != null && right != null) return root;


        return left == null ? right : left;

    }

    boolean traverse(TreeNode start, int dest, StringBuilder sb) {

        if (start == null) return false;

        if (start.val == dest) return true;

        sb.append('L');

        if (traverse(start.left, dest, sb)) return true;

        sb.setCharAt(sb.length() - 1, 'R');

        if (traverse(start.right, dest, sb)) return true;

        else sb.setLength(sb.length() - 1);

        return false;

    }


    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        List<Integer> res = new ArrayList<>();
        TreeMap<Integer, Map<Integer, Set<Integer>>> relation = new TreeMap<>();
        Map<Integer, Set<Integer>> occur = new HashMap<>();
        for (int[] meeting : meetings) {
            relation.putIfAbsent(meeting[2], new HashMap<>());
            occur.putIfAbsent(meeting[2], new HashSet<>());
            occur.get(meeting[2]).add(meeting[0]);
            occur.get(meeting[2]).add(meeting[1]);
            Map<Integer, Set<Integer>> inner = relation.get(meeting[2]);
            inner.putIfAbsent(meeting[0], new HashSet<>());
            inner.putIfAbsent(meeting[1], new HashSet<>());
            inner.get(meeting[0]).add(meeting[1]);
            inner.get(meeting[1]).add(meeting[0]);
            relation.put(meeting[2], inner);
        }
        res.add(0);
        res.add(firstPerson);

        for (Integer time : relation.keySet()) {
            Map<Integer, Set<Integer>> connection = relation.get(time);
            Set<Integer> source = occur.get(time);
            bfsSecrete(connection, source, res);
            if (res.size() == n) {
                break;
            }
        }

        return res;

    }


    private void bfsSecrete(Map<Integer, Set<Integer>> connection, Set<Integer> source, List<Integer> res) {
        Queue<Integer> queue = new LinkedList<>();
        for (Integer s : source) {
            if (res.contains(s)) {
                queue.offer(s);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (connection.containsKey(cur)) {
                for (Integer next : connection.get(cur)) {
                    if (!res.contains(next)) {
                        res.add(next);
                        queue.add(next);
                    }
                }
            }
        }
    }

    public int[] findOriginalArray(int[] changed) {
        if (changed.length % 2 != 0) {
            return new int[]{};
        }
        Arrays.sort(changed);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < changed.length; i++) {
            map.putIfAbsent(changed[i], new ArrayList<>());
            map.get(changed[i]).add(i);
        }
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[changed.length];
        for (int i = 0; i < changed.length; i++) {
            if (visited[i]) {
                continue;
            }
            int num = changed[i];
            visited[i] = true;
            map.get(num).remove(Integer.valueOf(i));
            if (map.containsKey(2 * num)) {
                List<Integer> cur = map.get(2 * num);
                Integer index = -1;
                for (Integer j : cur) {
                    if (!visited[j]) {
                        index = j;
                        break;
                    }
                }

                if (index > 0) {
                    visited[index] = true;
                    map.get(2 * num).remove(Integer.valueOf(index));
                    list.add(num);
                }
            }

        }

        if (list.size() == changed.length / 2) {
            int[] res = new int[changed.length / 2];
            for (int i = 0; i < changed.length / 2; i++) {
                res[i] = list.get(i);
            }

            return res;
        }


        return new int[]{};
    }

    public int numSplits(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        Set<Character> left = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            int fequency = map.get(s.charAt(i));
            left.add(s.charAt(i));
            if (fequency == 1) {
                map.remove(s.charAt(i));
            } else {
                map.put(s.charAt(i), fequency - 1);
            }
            if (map.size() == left.size()) {
                res++;
            }

            if (left.size() > map.size()) {
                break;
            }
        }

        return res;
    }


//    [["John","johnsmith@mail.com","john_newyork@mail.com","john00@mail.com"],
//            ["Mary","mary@mail.com"],
//            ["John","johnnybravo@mail.com"]]
//[["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],
//        ["Mary","mary@mail.com"],
//        ["John","johnnybravo@mail.com"]]

    public String[] findWords(String[] words) {
        List<String> list = new ArrayList<>();
        String one = "qwertyuiop";
        String two = "asdfghjkl";
        String three = "zxcvbnm";
        for (String word : words) {
            String lower = word.toLowerCase();
            char c = lower.charAt(0);
            String w = "";
            if (one.contains(c + "")) {
                w = one;
            } else if (two.contains(c + "")) {
                w = two;
            } else if (three.contains(c + "")) {
                w = three;
            }

            if (check(lower, w)) {
                list.add(word);
            }
        }

        String[] res = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private boolean check(String word, String provide) {
        for (char c : word.toCharArray()) {
            if (!provide.contains(c + "")) {
                return false;
            }
        }

        return true;
    }


    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> res = new ArrayList<>();
        Map<String, Set<String>> map = new HashMap<>();
        for (List<String> account : accounts) {
            List<String> list = new ArrayList<>(account);
            list.remove(0);
            for (String email : list) {
                map.putIfAbsent(email, new HashSet<>());
                map.get(email).addAll(list);
                map.get(email).remove(email);
            }
        }

        Set<String> visited = new HashSet<>();
        for (List<String> account : accounts) {
            List<String> first = new ArrayList<>(account);
            String name = first.get(0);
            first.remove(0);
            if (visited.contains(first.get(0))) {
                continue;
            }

            Queue<String> queue = new LinkedList<>();
            queue.addAll(first);
            visited.addAll(first);
            while (!queue.isEmpty()) {
                String cur = queue.poll();
                if (map.containsKey(cur)) {
                    for (String next : map.get(cur)) {
                        if (!first.contains(next)) {
                            first.add(next);
                            queue.offer(next);
                            visited.add(next);
                        }
                    }
                }
            }

            Set<String> set = new HashSet<>();
            set.addAll(first);
            List<String> element = new ArrayList<>();
            element.addAll(set);
            Collections.sort(element);
            element.add(0, name);
            res.add(element);
        }

        return res;
    }


    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int cutOffTree(List<List<Integer>> forest) {
        if (forest == null || forest.size() == 0) return 0;
        int m = forest.size(), n = forest.get(0).size();

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    pq.add(new int[]{i, j, forest.get(i).get(j)});
                }
            }
        }

        int[] start = new int[2];
        int sum = 0;
        while (!pq.isEmpty()) {
            int[] tree = pq.poll();
            int step = minStep(forest, start, tree, m, n);

            if (step < 0) return -1;
            sum += step;

            start[0] = tree[0];
            start[1] = tree[1];
        }

        return sum;
    }

    private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n) {
        int step = 0;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (curr[0] == tree[0] && curr[1] == tree[1]) return step;

                for (int[] d : dir) {
                    int nr = curr[0] + d[0];
                    int nc = curr[1] + d[1];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n
                            || forest.get(nr).get(nc) == 0 || visited[nr][nc]) continue;
                    queue.add(new int[]{nr, nc});
                    visited[nr][nc] = true;
                }
            }
            step++;
        }

        return -1;
    }


    public String baseNeg2(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int i = n % (-2);
            n = n / (-2);
            sb.append(i);

        }

        return sb.toString();
    }

    public void flatten(TreeNode root) {
        flattenNode(root);
    }

    public TreeNode flattenNode(TreeNode root) {
        if (root == null) {
            return null;
        }

        if (root.left == null && root.right == null) {
            return root;
        } else if (root.left == null) {
            root.right = flattenNode(root.right);
        } else if (root.right == null) {
            root.right = flattenNode(root.left);
        } else {
            TreeNode left = flattenNode(root.left);
            TreeNode right = flattenNode(root.right);
            TreeNode leftTail = left;
            while (leftTail.right != null) {
                leftTail = leftTail.right;
            }

            root.left = null;
            root.right = left;
            leftTail.right = right;
        }

        return root;
    }


    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // write your code here
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> (Double.compare(Math.abs(b - target), Math.abs(a - target))));
        iterateTree(root, k, queue);
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            res.add(queue.poll());
        }
        return res;
    }

    private void iterateTree(TreeNode root, int k, PriorityQueue<Integer> queue) {
        if (root != null) {
            queue.offer(root.val);
            if (queue.size() > k) {
                queue.poll();
            }
            iterateTree(root.left, k, queue);
            iterateTree(root.right, k, queue);
        }
    }


    public int rangeSum(int[] nums, int n, int left, int right) {
        int mod = (int) (1e9 + 7);
        int len = n * (n + 1) / 2;
        long[] arr = new long[len];
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int k = len - 1;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                arr[k] = sum[j + 1] - sum[i];
                arr[k] %= mod;
                k--;
            }
            arr[i] = nums[i];
        }

        long res = 0;
        Arrays.sort(arr);
        for (int i = left - 1; i < right; i++) {
            res = (res + arr[i]) % mod;
        }

        return (int) res % mod;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur = head;
        int count = 0;
        while (cur != null && count != k) {
            cur = cur.next;
            count++;
        }
        if (count == k) {
            cur = reverseKGroup(cur, k);
            while (count-- > 0) {
                ListNode temp = head.next;
                head.next = cur;
                cur = head;
                head = temp;
            }
        }

        return head;

    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode couldReverse(ListNode head, int k) {
        while (k > 1 && head != null) {
            head = head.next;
            k--;
        }

        return k == 1 ? head : null;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        List<Integer> res = new ArrayList<>();
        int row = m;
        int col = n;
        int x = 0;
        int y = 0;
        while (row > 0 && col > 0) {
            int val = iterate(res, matrix, x, y, row, col);
            row -= 2;
            col -= 2;
            x = (val + n - 1) / n - 1;
            y = val % n == 0 ? n - 1 : val % n - 1;
        }
        List<Integer> list = new ArrayList<>();
        for (int val : res) {
            int i = (val + n - 1) / n - 1;
            int j = val % n == 0 ? n - 1 : val % n - 1;
            list.add(matrix[i][j]);
        }
        return list;
    }

    private int iterate(List<Integer> res, int[][] matrix, int x, int y, int row, int column) {
        int m = matrix.length;
        int n = matrix[0].length;
        int start = x * n + y + 1;
        for (int i = 1; i <= column && res.size() < m * n; i++) {
            res.add(start++);
        }
        start--;
        if (row > 1) {
            for (int i = 2; i <= row && res.size() < m * n; i++) {
                start += n;
                res.add(start);
            }
            for (int i = column - 1; i >= 1 && res.size() < m * n; i--) {
                start--;
                res.add(start);
            }

            for (int i = row - 1; i > 1 && res.size() < m * n; i--) {
                start -= n;
                res.add(start);
            }
        }

        start++;
        return start;
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode cur = head;
        int i = 0;
        while (cur != null) {
            cur = cur.next;
            i++;
        }
        n = i - n;
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        int k = 0;
        cur = head;
        while (k < n) {
            ListNode next = cur.next;
            pre = cur;
            cur = next;
            k++;
        }

        pre.next = cur.next;
        return dummy.next;

    }

    public ListNode swapNodes(ListNode head, int k) {
        int i = 0;
        ListNode cur = head;
        while (cur != null) {
            i++;
            cur = cur.next;
        }

        int pos = i - k + 1;//注意位置加1
        cur = head;
        int index = 1;//起始位置为第一个节点
        while (index < pos) {
            cur = cur.next;
            index++;
        }
        //恰好数到第pos个位置的节点
        ListNode pre = cur;

        index = 1;//起始位置为第一个节点
        cur = head;
        while (index < k) {
            cur = cur.next;
            index++;
        }
        //当前位置即为第K个节点
        int temp = pre.val;
        pre.val = cur.val;
        cur.val = temp;
        return head;
    }


    public ListNode deleteMiddle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            pre = slow;
            slow = slow.next;
        }

        pre.next = slow.next;
        return dummy.next;
    }

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int res = 0;

        int m = grid1.length;
        int n = grid1[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i, j});
                    boolean in = true;
                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        int x = cur[0];
                        int y = cur[1];
                        if (grid1[x][y] == 0) {
                            in = false;
                        }
                        for (int[] dir : dirs) {
                            int k = x + dir[0];
                            int s = y + dir[1];
                            if (k < 0 || s < 0 || k >= m || s >= n || grid2[k][s] == 0 || visited[k][s]) {
                                continue;

                            }

                            visited[k][s] = true;
                            queue.offer(new int[]{k, s});

                        }
                    }

                    if (in) {
                        res++;
                    }
                }
            }
        }


        return res;
    }

    public int[] findEvenNumbers(int[] digits) {
        List<Integer> list = new ArrayList<>();
        Arrays.sort(digits);
        int n = digits.length;
        for (int i = 0; i < n; i++) {
            if (digits[i] % 2 == 0) {
                boolean[] visited = new boolean[n];
                visited[i] = true;
                int sum = digits[i];
                dfs(digits, visited, sum, list, 2);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private void dfs(int[] digits, boolean[] visited, int sum, List<Integer> list, int k) {
        if (k == 0) {
            if (!list.contains(sum)) {
                list.add(sum);
            }
            return;
        }
        int n = digits.length;

        Set<Integer> exist = new HashSet<Integer>();
        for (int i = 0; i < n; i++) {
            if (visited[i] || !exist.add(digits[i])) {
                continue;
            }
            if (k == 1 && digits[i] == 0) {
                continue;
            }
            visited[i] = true;
            sum += digits[i] * Math.pow(10, 3 - k);
            dfs(digits, visited, sum, list, k - 1);
            sum -= digits[i] * Math.pow(10, 3 - k);
            visited[i] = false;

        }
    }

    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        ListNode cur= head;
        ListNode pre = null;
        while(cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public ListNode reverseEvenLengthGroups(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        head.next = reverseEvenLengthGroups(head.next,2);
        return head;
    }

    private ListNode reverseEvenLengthGroups(ListNode head,int len) {
        if(head==null){
            return head;
        }
        int count =0;
        ListNode nextHead = head;
        ListNode last = null;
        while (count<len&&nextHead!=null){
            last = nextHead;
            nextHead =nextHead.next;
            count++;
        }

        if(count<len){
            len =count;
        }

            if(len%2==1){
                //奇数，当前长度部分节点不用变，最后一个节点的next要指向下一个翻转之后的头结点
                last.next = reverseEvenLengthGroups(nextHead,len+1);
                return head;
            }

            //偶数，当前长度节点翻转
            ListNode pre = null;
            ListNode cur = head;
            count=0;
            while (count<len){
                ListNode next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
                count++;
            }

            //当前长度节点的头结点是翻转之后的最后一个一个节点，它指向之后的节点长度的头结点
            head.next = reverseEvenLengthGroups(nextHead,len+1);
            return pre;
    }

    public ListNode constructNode(int[] nums){
        ListNode dummy = new ListNode();
        ListNode cur = new ListNode(nums[0]);
        dummy.next = cur;
        for(int i=1;i<nums.length;i++){
            ListNode next = new ListNode(nums[i]);
            cur.next = next;
            cur = next;
        }

        return dummy.next;
    }



    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        CBTInserter test = new CBTInserter(root);
//        [5,2,6,3,9,1,7,3,8,4]

//        head.next.next.next.next.next = new ListNode(6);
        ListNode head = test.constructNode(new int[]{5,2,6,3,9,1,7,3,8,4});

        test.reverseEvenLengthGroups(head);


        int n = (int) Math.sqrt(1);

//        String[] str1 =new String[]{"amazon","apple","facebook","google","leetcode"};
//        String[] str2 =new String[]{"e","o"};
//        test.findAllPeople(6, new int[][]{{1,2,5},{2,3,8},{1,5,10}},1);
//        test.findWords(new String[]{"Hello","Alaska","Dad","Peace"});
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        test.spiralOrder(matrix);

        String s = "U";

        StringBuilder ancToStart = new StringBuilder("RL");
        StringBuilder ancToDest = new StringBuilder("UU");
        s = s.repeat(ancToStart.length());

        s += ancToDest.toString();
        System.out.println();
    }
}
