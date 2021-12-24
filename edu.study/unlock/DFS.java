package unlock;

import java.util.*;

import unlock.BinaryTree.ResultData;
import unlock.Codec.TreeNode;

public class DFS {

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.equals("")) {
            return res;
        }
        Map<Integer, String> map = new HashMap<>();
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");
        dfsLetterCombinations(map, digits, res, new StringBuilder(), 0);
        return res;
    }

    public void dfsLetterCombinations(Map<Integer, String> map, String digits, List<String> res,
                                      StringBuilder sb, int index) {
        if (sb.length() == digits.length()) {
            res.add(sb.toString());
            return;
        }

        String cur = map.get(digits.charAt(index) - '0');
        for (char c : cur.toCharArray()) {
            sb.append(c);
            dfsLetterCombinations(map, digits, res, sb, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }

    }

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        dfsGenerateParenthesis(res, n, n, "");
        return res;

    }

    public void dfsGenerateParenthesis(List<String> res, int left, int right, String str) {
        if (left > right) {
            return;
        }

        if (left == 0 && right == 0) {
            res.add(str);
            return;
        }
        if (left > 0) {
            dfsGenerateParenthesis(res, left - 1, right, str + "(");
        }
        if (right > 0) {
            dfsGenerateParenthesis(res, left, right - 1, str + ")");
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList();
        dfs(res, new ArrayList(), n);
        return res;
    }

    public void dfs(List<List<String>> res, List<String> list, int n) {
        if (list.size() == n) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(".");
            }
            sb.append("Q");
            for (int j = i + 1; j < n; j++) {
                sb.append(".");
            }

            list.add(sb.toString());
            if (canHere(list, i)) {
                dfs(res, list, n);
            }
            list.remove(list.size() - 1);

        }

    }

    public boolean canHere(List<String> list, int y) {
        if (list.size() == 1) {
            return true;
        }
        int m = list.size() - 1;
        int n = list.get(0).length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < list.size() - 1; i++) {
            grid[i] = list.get(i).toCharArray();
        }

        for (int i = 0; i < m; i++) {
            if (grid[i][y] == 'Q') {
                return false;
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            int diff = m - i;
            int y1 = y - diff;
            int y2 = y + diff;
            if (y1 >= 0 && grid[i][y1] == 'Q') {
                return false;
            }
            if (y2 < n && grid[i][y2] == 'Q') {
                return false;
            }
        }

        return true;

    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();

        dfsGetFactors(n, 2, res, new ArrayList<>());
        return res;
    }

    public void dfsGetFactors(int n, int cur, List<List<Integer>> res, List<Integer> list) {
        if (1 == n) {
            if (list.size() > 1) {
                res.add(new ArrayList<>(list));
                return;
            }
        }

        if (1 > n) {
            return;
        }

        for (int i = cur; i <= n; i++) {
            if (n % i != 0) {
                continue;
            }
            list.add(i);
            dfsGetFactors(n / i, i, res, list);
            list.remove(list.size() - 1);
        }
    }


    public void solveSudoku(char[][] board) {
        dfsSolveSudoku(board, 0, 0);
    }

    public boolean dfsSolveSudoku(char[][] board, int x, int y) {
        if (x == 9) {
            return true;
        }
        if (y >= 9) {
            return dfsSolveSudoku(board, x + 1, 0);
        }
        if (board[x][y] != '.') {
            return dfsSolveSudoku(board, x, y + 1);
        }
        for (char c = '1'; c <= '9'; c++) {
            if (!isValid(board, x, y, c)) {
                continue;
            }
            board[x][y] = c;
            if (dfsSolveSudoku(board, x, y + 1)) {
                return true;
            }
            board[x][y] = '.';
        }
        return false;
    }


    public boolean isValid(char[][] board, int x, int y, char num) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            if (i == x) {
                continue;
            }
            if (board[i][y] == num) {
                return false;
            }
        }

        for (int j = 0; j < n; j++) {
            if (j == y) {
                continue;
            }
            if (board[x][j] == num) {
                return false;
            }
        }

        int row = x - x % 3;
        int col = y - y % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[row + i][col + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        dfsrestoreIpAddresses(res, s, 0, "");
        return res;
    }

    public void dfsrestoreIpAddresses(List<String> res, String s, int start, String pre) {
        String[] cut = pre.split("\\.");
        if (cut.length > 4 || cut.length == 4 && start < s.length()) {
            return;
        }
        if (start == s.length()) {
            if (cut.length == 4) {
                res.add(pre.substring(0, pre.length() - 1));
            }
            return;
        }
        char cur = s.charAt(start);
        if (cur == '0') {
            pre += "0.";
            dfsrestoreIpAddresses(res, s, start + 1, pre);
            return;
        }
        for (int i = start; i < s.length() && i < start + 3; i++) {
            String cu = s.substring(start, i + 1);
            if (Integer.valueOf(cu) > 255) {
                continue;
            }
            pre += cu + ".";
            dfsrestoreIpAddresses(res, s, i + 1, pre);
            pre = pre.substring(0, pre.length() - (i - start + 1 + 1));
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        dfsPartition(res, new ArrayList<>(), s, 0);
        return res;
    }

    public void dfsPartition(List<List<String>> res, List<String> list, String s, int start) {
        if (start >= s.length()) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i < s.length(); i++) {
            String cut = s.substring(start, i + 1);
            if (!isPalindrome(cut)) {
                continue;
            }
            list.add(cut);
            dfsPartition(res, list, s, i + 1);
            list.remove(list.size() - 1);
        }

    }

    public boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public int theMissing;

    public int findMissing2(int n, String str) {
        theMissing = -1;
        boolean[] theFind = new boolean[n + 1];
        dfsFind(n, str, 0, theFind);
        return theMissing;
    }

    private void dfsFind(int n, String str, int start, boolean[] theFind) {
        if (theMissing != -1) {
            return;
        }

        if (start == str.length()) {
            for (int i = 1; i < theFind.length; i++) {
                if (!theFind[i]) {
                    theMissing = i;
                    return;
                }
            }
            return;
        }

        if (str.charAt(start) == '0') {
            return;
        }

        for (int i = 1; i <= 2 && start + i <= str.length(); i++) {
            int num = Integer.parseInt(str.substring(start, start + i));
            if (num > 0 && num <= n && !theFind[num]) {
                theFind[num] = true;
                dfsFind(n, str, start + i, theFind);
                theFind[num] = false;
            }
        }
    }


    public List<List<String>> splitString(String s) {
        // write your code here
        List<List<String>> res = new ArrayList<>();
        List<String> list = new ArrayList<>();
        dfsSplit(s, 0, res, list);
        return res;
    }

    public void dfsSplit(String s, int start, List<List<String>> res, List<String> list) {
        if (start == s.length()) {
            res.add(new ArrayList(list));
            return;
        }

        for (int i = 1; i <= 2 && start + i <= s.length(); i++) {
            String num = s.substring(start, start + i);
            if (num.startsWith("0") && i == 2) {
                continue;
            }
            list.add(num);
            dfsSplit(s, start + i, res, list);
            list.remove(list.size() - 1);
        }
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();

        dfsPath(root, res, new StringBuilder());
        return res;

    }

    public void dfsPath(TreeNode root, List<String> res, StringBuilder sb) {
        if (root == null) {
            return;
        }
        String val = sb.length() == 0 ? root.val + "" : "->" + root.val;
        sb.append(val);
        if (root.left == null && root.right == null) {
            res.add(sb.toString());
        } else {
            dfsPath(root.left, res, new StringBuilder(sb));
            dfsPath(root.right, res, new StringBuilder(sb));
        }
    }


    private void dfs(TreeNode root, List<List<Integer>> result,
                     int remain, List<Integer> path) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        if (root.left == null && root.right == null && remain == root.val) {
            result.add(new ArrayList<>(path));
        }

        dfs(root.left, result, remain - root.val, path);
        dfs(root.right, result, remain - root.val, path);
        path.remove(path.size() - 1);
    }

    public void dfsPathSum(TreeNode root, int target, List<List<Integer>> res, List<Integer> list) {
        if (root == null || target < 0) {
            return;
        }

        list.add(root.val);
        if (root.left == null && root.right == null) {
            if (root.val == target) {
                res.add(new ArrayList(list));
            }
        } else {
            if (root.left != null) {
                dfsPathSum(root.left, target - root.val, res, list);
            }
            if (root.right != null) {
                dfsPathSum(root.right, target - root.val, res, list);
            }
        }
        list.remove(list.size() - 1);

    }


    class ParentTreeNode {
        int val;
        ParentTreeNode parent, left, right;
    }

    public List<List<Integer>> binaryTreePathSum3(ParentTreeNode root, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        List<ParentTreeNode> nodeList = new ArrayList<>();
        inorder(root, nodeList);

        for (ParentTreeNode node : nodeList) {
            HashSet<ParentTreeNode> set = new HashSet<>();
            dfsbinaryTreePathSum3(node, target, set, new ArrayList<>(), res);
        }

        return res;
    }

    private void inorder(ParentTreeNode root, List<ParentTreeNode> nodeList) {
        if (root == null) {
            inorder(root.left, nodeList);
            nodeList.add(root);
            inorder(root.right, nodeList);
        }
    }

    public void dfsbinaryTreePathSum3(ParentTreeNode root, int target, HashSet<ParentTreeNode> set, List<Integer> cur, List<List<Integer>> res) {
        if (root == null || set.contains(root)) {
            return;
        }
        set.add(root);
        cur.add(root.val);
        target -= root.val;
        if (target == 0) {
            res.add(new ArrayList<>(cur));
        }
        dfsbinaryTreePathSum3(root.left, target, set, cur, res);
        dfsbinaryTreePathSum3(root.right, target, set, cur, res);
        dfsbinaryTreePathSum3(root.parent, target, set, cur, res);
        set.remove(root);
        cur.remove(cur.size() - 1);
        target += root.val;

    }

    int maxLength = 0;

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if (s == null && s.equals("")) {
            return res;
        }

        for (String word : wordDict) {
            if (word.length() > maxLength) {
                maxLength = word.length();
            }
        }

        dfswordBreak(s, wordDict, res, 0, "");
        return res;
    }

    public void dfswordBreak(String s, List<String> wordDict, List<String> res, int start,
                             String sb) {

        if (start == s.length()) {
            res.add(new String(sb.trim()));
            return;
        }

        for (int i = 1; i <= maxLength && start + i <= s.length(); i++) {
            String cur = s.substring(start, start + i);
            if (wordDict.contains(cur)) {
                dfswordBreak(s, wordDict, res, start + i, sb + " " + cur);
            }
        }

    }

    public int findTargetSumWays(int[] nums, int target) {
        return dfsfindTargetSumWays(nums, 0, target);
    }

    public int dfsfindTargetSumWays(int[] nums, int start, int target) {
        int res = 0;
        if (start == nums.length) {
            if (target == 0) {
                res++;
            }

            return res;
        }

        res += dfsfindTargetSumWays(nums, start + 1, target + nums[start]);
        res += dfsfindTargetSumWays(nums, start + 1, target - nums[start]);
        return res;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (beginWord == null || beginWord.equals("")) {
            return res;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int step = 0;
        queue.add(beginWord);
        visited.add(beginWord);

        boolean find = false;
        while (!queue.isEmpty() && !find) {
            step++;
            int size = queue.size();
            while (size > 0) {
                size--;
                String cur = queue.poll();
                if (cur.equals(endWord)) {
                    find = true;
                    break;
                }
                List<String> nextList = getNextLadderList(cur, wordList);
                for (String next : nextList) {
                    if (visited.add(next)) {
                        queue.add(next);
                    }
                }
            }
        }

        List<String> list = new ArrayList<>();
        list.add(beginWord);
        dfsLadderList(beginWord, endWord, wordList, step, res, list);

        return res;
    }


    public void dfsLadderList(String beginWord, String endWord, List<String> wordList, int step, List<List<String>> res, List<String> list) {
        if (list.size() == step) {
            if (beginWord.equals(endWord)) {
                res.add(new ArrayList<>(list));
            }
            return;
        }


        List<String> nextList = getNextLadderList(beginWord, wordList);
        for (String next : nextList) {
            if (wordList.contains(next) && !list.contains(next)) {
              list.add(next);
              dfsLadderList(next, endWord, wordList, step, res, list);
              list.remove(list.size() - 1);
            }
        }


    }

    public List<String> getNextLadderList(String word, List<String> wordList) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] characters = word.toCharArray();
            for (int j = 0; j < 26; j++) {
                char replace = (char) ('a' + j);
                if (replace == characters[i]) {
                    continue;
                }
                characters[i] = replace;
                String next = new String(characters);
                if (wordList.contains(next)) {
                    res.add(next);
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        DFS test = new DFS();
//        test.restoreIpAddresses("25525511135");
        String c = "2.3.";
        String[] t = c.split("\\.");
        System.out.println(t.length);
        String beginWord = "hit", endWord = "cog";
        String[] wordList = new String[]{"hot","dot","dog","lot","log","cog"};
        test.findLadders(beginWord,endWord,Arrays.asList(wordList));
    }

}
