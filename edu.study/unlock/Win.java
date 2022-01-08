package unlock;

import java.util.HashMap;
import java.util.Map;

public class Win {
    Map<Integer, Boolean> map;
    boolean[] used;

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
        if (sum < desiredTotal) {
            return false;
        }

        if (desiredTotal <= 0) {
            return true;
        }

        map = new HashMap<>();
        used = new boolean[maxChoosableInteger + 1];
        return hepler(desiredTotal);
    }

    private boolean hepler(int desiredTotal) {
        if (desiredTotal <= 0) {
            return true;
        }

        //状态位格式化
        int key = format(used);
        if (!map.containsKey(key)) {
            for (int i = 1; i < used.length; i++) {
                if (used[i]) {
                    continue;
                }

                used[i] = true;
                //dfs 递归
                if (!hepler(desiredTotal - i)) {
                    map.put(key, true);
                    used[i] = false;
                    return true;
                }
                //状态还原
                used[i] = false;
            }
        }

        map.put(key, false);
        return map.get(key);
    }

    private int format(boolean[] used) {
        int num = 0;
        for (boolean b : used) {
            num <<= 1;
            if (b) {
                num = num | 1;
            }
        }
        return num;
    }

    int[][] countMap;
    int cnt = Integer.MAX_VALUE;

    public int minStickers(String[] stickers, String target) {
        if (target == null) {
            return -1;
        }
        if (target.length() == 0) {
            return 0;
        }
        if (stickers == null || stickers.length == 0) {
            return -1;
        }

        //统计每个单词的每个字符数量，便于后续计算
        for (int i = 0; i < stickers.length; i++) {
            String s = stickers[i];
            for (char c : s.toCharArray()) {
                countMap[i][c - 'a']++;
            }
        }

        //从第0个字母开始匹配
        count(0, 0, new int[26], target, stickers);
        return cnt == Integer.MAX_VALUE ? -1 : cnt;
    }

    private void count(int curCount, int pos, int[] charAvailable, String target, String[] stickers) {
        //当前策略并非最优解，直接返回
        if (curCount > cnt) return;

        int m = stickers.length;
        int n = target.length();
        //到达终点，更新结果
        if (pos == n) {
            cnt = Math.min(cnt, curCount);
            return;
        }

        char c = target.charAt(curCount);
        //剩余可用的字符数组中还有，往后挪一位继续递归
        if (charAvailable[c - 'a'] > 0) {
            charAvailable[c - 'a']--;
            count(curCount, pos, charAvailable, target, stickers);
        } else {
            //当前字母在提供的字符数组中不够用了，需要加一个新单词
            for (int i = 0; i < m; i++) {
                if (countMap[i][c - 'a'] == 0) continue;
                //追加的新单词，包含的所有字母都可以提供给可用字符数组
                for (int j = 0; j < 26; j++) {
                    charAvailable[j] += countMap[i][j];
                }
                //递归计算
                count(curCount + 1, pos + 1, charAvailable, target, stickers);
                //还原，方便后续继续查找最优解
                for (int j = 0; j < 26; j++) {
                    charAvailable[j] -= countMap[i][j];
                }
            }
        }

    }




}
