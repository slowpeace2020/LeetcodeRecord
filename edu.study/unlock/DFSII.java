package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.SET;

import java.util.*;

public class DFSII {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Map<String,ArrayList<String>> nextWordMap = new HashMap<>();
        Map<String,Integer> distance = new HashMap<>();
        bfsLadders(beginWord,endWord,wordList,nextWordMap,distance);
        dfsLadders(beginWord,endWord,wordList,nextWordMap,distance,0,res,new ArrayList<>(Arrays.asList(beginWord)));
        return res;
    }

    public void dfsLadders(String beginWord, String endWord,List<String> wordList,Map<String,ArrayList<String>> nextWordMap,Map<String,Integer> distance,int step, List<List<String>> res,List<String> list){
        if(list.get(list.size()-1).equals(endWord)){
            res.add(new ArrayList<>(list));
        }

        for(String word:nextWordMap.get(list.get(list.size()-1))){
            list.add(word);
            if(distance.get(word)==step+1){
                dfsLadders(beginWord,endWord,wordList,nextWordMap,distance,step+1,res,list);
            }
            list.remove(list.size()-1);
        }
    }
    public void bfsLadders(String beginWord, String endWord,List<String> wordList,Map<String,ArrayList<String>> nextWordMap,Map<String,Integer> distance){
        for(String word:wordList){
            nextWordMap.put(word,new ArrayList<>());
        }

        nextWordMap.put(beginWord,new ArrayList<>());
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        distance.put(beginWord,0);
        boolean find = false;
        int step = 0;
        while (!queue.isEmpty()){
            step++;
            int size = queue.size();
            while (size>0){
                size--;
                String word = queue.poll();
                for(String next:getNextLadderList(word,wordList)){
                    nextWordMap.getOrDefault(word,new ArrayList<>()).add(next);
                    if(next.equals(endWord)){
                        find = true;
                    }

                    if(!distance.containsKey(next)){
                        distance.put(next,step);
                        queue.offer(next);
                    }
                }

            }

            if(find){
                break;
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

    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if(words.length!=pattern.length()){
            return false;
        }

        Map<Character,String> map = new HashMap<>();
        Set<String> mapString = new HashSet<>();
        for(int i=0;i<pattern.length();i++){
            char c = pattern.charAt(i);
            if(map.containsKey(c)){
                if(!map.get(c).equals(words[i])){
                    return false;
                }
            }else{
                if(mapString.contains(words[i])){
                    return false;
                }
                mapString.add(words[i]);
                map.put(c,words[i]);
            }
        }


        return true;
    }

    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        int[] count = new int[n + 1];
        for (int num : nums) {
            count[num]++;
            if (count[num] == 2) {
                list.add(num);
            }
        }

        return list;
    }


    Map<Character,String> characterStringMap;
    Set<String> stringSet;
    boolean matchRes;
    public boolean wordPatternMatchII(String pattern, String str) {
        characterStringMap = new HashMap<>();
        stringSet = new HashSet<>();
        matchRes = false;

        dfswordPatternMatchII(pattern,str,0,0);
        return matchRes;
    }

    public void dfswordPatternMatchII(String pattern,String str,int i,int j){
        // 如果pattern匹配完了而且str也正好匹配完了，说明有解
        if(i==pattern.length()&&j==str.length()){
            matchRes = true;
            return;
        }

        // 如果某个匹配超界了，则结束递归
        if(i>pattern.length()||j>str.length()){
            return;
        }

        char c = pattern.charAt(i);

        // 尝试从当前位置到结尾的所有划分方式
        for(int cut = j+1;cut<=str.length();cut++){
            // 拆出一个子串
            String subStr = str.substring(j,cut);
            if(!stringSet.contains(subStr)&&!characterStringMap.containsKey(c)){
                characterStringMap.put(c,subStr);
                stringSet.add(subStr);
                dfswordPatternMatchII(pattern,str,i+1,cut);
                if(matchRes){
                    return;
                }
                characterStringMap.remove(c);
                stringSet.remove(subStr);
                // 如果已经有映射了，但是是匹配的，也继续求解
            }else if(characterStringMap.containsKey(c)&&characterStringMap.get(c).equals(subStr)){
                dfswordPatternMatchII(pattern,str,i+1,cut);
                if(matchRes){
                    return;
                }
            }
        }
    }

    public void rotateI(int[][] matrix) {
        int n = matrix.length;
        for(int i=0;i<n/2;i++){
            for(int j=i;j<n-1;j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;


            }
        }
    }

    public List<Integer> rotate(int[][] matrix) {
        List<Integer> res = new ArrayList<>();

        if(matrix==null||matrix.length==0){
          return res;
      }
      int m = matrix.length;
      int n = matrix[0].length;
      int count =0;
      int i=0;
      int j=0;

      int[][] dirs = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
      for(int k=0;k<m*n;k++){
          res.add(matrix[i][j]);
          matrix[i][j] = 101;
          int x = i+dirs[count][0];
          int y = j+dirs[count][1];
          while (x<0||x>=m||y<0||y>=n||matrix[x][y]==101){
               count = (count+1)%4;
               x = i+dirs[count][0];
               y = j+dirs[count][1];
          }

          i = x;
          j = y;
      }

      return res;
    }

    public void setZeroes(int[][] matrix) {
        boolean[][] needchange = new boolean[matrix.length][matrix[0].length];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    for(int k=0;k<matrix.length;k++){
                        needchange[k][j]=true;
                    }
                    for(int k=0;k<matrix[0].length;k++){
                        needchange[i][k]=true;
                    }
                }
            }
        }

        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(needchange[i][j]){
                    matrix[i][j]=0;
                }
            }
        }
    }

    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] needchange = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(getCellsChange(i,j,board)){
                    needchange[i][j] = true;
                }
            }
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(needchange[i][j]){
                    board[i][j] = 1-board[i][j];
                }
            }
        }


    }

    public boolean getCellsChange(int x,int y,int[][] board){
        int m = board.length;
        int n = board[0].length;
        int res=0;

        int[][] positions = new int[][]{{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for(int[] pos:positions){
            int i = pos[0]+x;
            int j= pos[1]+y;
            if(i>=0&&i<m&&j>=0&&j<n&&board[i][j]==1){
                res++;
            }
        }


        int val = board[x][y];
        if(val==0){
            if(res==3){
                return true;
            }
            return false;
        }else{
            if(res==2||res==3){
                return false;
            }

            return true;
        }

    }

    public String convert(String s, int numRows) {
        char[][] scartter = new char[numRows][s.length()];
        int column = -1;
        for(int i=0;i<s.length();){
            column++;
            int j=0;
            for(;j<numRows;j++){
                if(i>=s.length()){
                    break;
                }
                scartter[column][j] = s.charAt(i++);
            }

            for(int k=numRows-2;k>0;k--){
                if(i>=s.length()){
                    break;
                }
                column++;
                scartter[k][column]=s.charAt(i++);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<numRows;i++){
            for(int j=0;j<s.length();j++){
                if(scartter[i][j]!=0){
                    sb.append(scartter[i][j]);
                }
            }
        }

        return sb.toString();
    }

    public String convertI(String s, int numRows) {
        char[][] scartter = new char[numRows][s.length()];
        int collum=-1;
        for(int i=0;i<s.length();){
            collum++;
            int j=0;
            for(;j<numRows;j++){
                if(i>=s.length()){
                    break;
                }
                scartter[j][collum]=s.charAt(i++);

            }
            for(int k=numRows-2;k>0;k--){
                if(i>=s.length()){
                    break;
                }
                collum++;
                scartter[k][collum]=s.charAt(i++);
            }

        }

        String res ="";
        for(int i=0;i<numRows;i++){
            for(int j=0;j<s.length();j++){
                if(scartter[i][j]==0){
                    continue;
                }
                res+=scartter[i][j];
            }
        }

        return res;
    }

    public int romanToInt(String s) {
        if(s==null||s.equals("")){
            return 0;
        }

        Map<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);

        int len = s.length();
        int res = map.get(s.charAt(len-1));
        for(int i=len-2;i>=0;i--){
            if(map.get(s.charAt(i+1))<=map.get(s.charAt(i))){
                res+=map.get(s.charAt(i));
            }else {
                res-=map.get(s.charAt(i));
            }
        }

        return res;
    }

    public String longestCommonPrefix(String[] strs) {
        if(strs==null||strs.length==0){
            return "";
        }

        String common =strs[0];
        for(int i=1;i<strs.length;i++){
            String cur = strs[i];
            while (!cur.startsWith(common)&&common.length()>0){
                common =common.substring(0,common.length()-1);
            }
            if(common.equals("")){
                return "";
            }
        }
        return common;
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        int n = words.length;
        List<String> res = new ArrayList<>();
        int left=0;
        while (left<n){
            //找出每一行最右单词的index
            int right = findRight(left,words,maxWidth);
            //将此范围内的一行单词处理成字符串添加到结果
            res.add(justify(left,right,words,maxWidth));
            //下一行单词的起始位置index
            left=right+1;
        }
        return res;
    }

    private String justify(int left, int right, String[] words, int maxWidth) {
        //当这一行只有一个单词时，单词靠左，往后填充空格
        if(right-left==0){
            return padResult(words[left],maxWidth);
        }

        //当一行右多个单词时，区分是否为最后一行
        boolean isLastLine = right==words.length-1;
        //算出一行单词之间的空格数量
        int numSpace = right-left;
        //总的空格数量为总长度减去这一行单词长度
        int totalSpace = maxWidth - wordsLength(left,right,words);
        //单词之间的空格字符串的值，这样拼接的时候不用再计算，直接追加
        //如果是最后一行，单词之间只有一个空格，后面全是空格，
        // 若不是则单词之间的空格均匀分布，单词左右都要对齐
        String space = isLastLine?" ":blank(totalSpace/numSpace);
        //不能分匀的空格，也就是分布均匀之后剩下的空格数量
        //题目要求尽可能分匀，那就给这些空格分布到前面的单词之间
        int remainder = isLastLine?0:totalSpace%numSpace;

        //单词之间的拼接
        StringBuilder sb = new StringBuilder();
        for(int i=left;i<=right;i++){
            //首先追加单词，再追加单词之间的均匀空格数量，
            //最后剩下的不能分匀的空格，一个个分布到单词之间，直到数量为0
            sb.append(words[i]).append(space).append(remainder-->0?" ":"");

        }
        //单词之间尽可能分匀了，还剩下的空间，在末尾填充补齐
        return padResult(sb.toString().trim(),maxWidth);
    }

    private String blank(int length) {
        return new String(new char[length]).replace('\0', ' ');
    }

    private int wordsLength(int left, int right, String[] words) {
        int len =0;
        for(int i=left;i<=right;i++){
            len+=words[i].length();
        }
        return len;
    }

    private String padResult(String word, int maxWidth) {
        return word+blank(maxWidth-word.length());
    }

    /**
     * 找到最右单词的位置index
     * @param left
     * @param words
     * @param maxWidth
     * @return
     */
    private int findRight(int left, String[] words, int maxWidth) {
        int right = left;
        int len = words[left].length();
        //一行尽可能多塞单词，只要当前长度加空格加下一个单词的长度不超过限制
        while (right<words.length&&(len+1+words[right+1].length()<=maxWidth)){
            len+=1+words[right+1].length();
            right++;
        }

        return right;
    }


    public int compress(char[] chars) {
        int index=0;
        for(int i=0;i<chars.length;i++){
            int j=i;
            while (j+1<chars.length&&chars[j]==chars[j+1]){
                j++;
            }

            chars[index]=chars[i];
            int count=j-i+1;
            if(count>1){
                String str =""+count;
                for(char c:str.toCharArray()){
                    chars[++index] =c;
                }
            }
            i=j;
            index++;
        }

        return index;
    }


    public static void main(String[] args) {
        DFSII test = new DFSII();
//        test.wordPattern("abba", "dog dog dog dog");
        test.gameOfLife(new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}});
    }
}
