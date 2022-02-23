package feb;

import algs4.src.main.java.edu.princeton.cs.algs4.Stack;

import java.util.*;

public class CBTInserter {
    static class TreeNode{
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


    TreeNode root;
    public CBTInserter(TreeNode root) {
        this.root = root;
    }

    public int insert(int val) {
        List<TreeNode> list = new ArrayList<>();
        list.add(new TreeNode());
        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while (cur.size()!=0){
            list.addAll(cur);
            List<TreeNode> next = new ArrayList<>();
            for(TreeNode node:cur){
                if(node.left!=null){
                    next.add(node.left);
                }
                if(node.right!=null){
                    next.add(node.right);
                }
            }

            cur = next;
        }
        int n = list.size();
        TreeNode child = new TreeNode(val);
        TreeNode parent = list.get(n/2);
        if(n%2==1){
            parent.right= child;
        }else{
            parent.left=child;
        }

        return parent.val;

    }
    public TreeNode get_root() {
        return root;
    }

    public String reverseOnlyLetters(String s) {
        char[] arr = s.toCharArray();
        int i=0;
        int j=s.length()-1;
        while(i<j){
            while(i<j&&!Character.isLetter(arr[i])){
                i++;
            }
            while(i<j&&!Character.isLetter(arr[j])){
                j--;
            }
            if(i!=j){
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
        for(int i=0;i<n;i++){
            String word = words2[i];
            int[] count = new int[26];
            for(char c:word.toCharArray()){
                count[c-'a']++;
            }

            for(int j=0;j<26;j++){
                if(count[j]>compare[j]){
                    compare[j] = count[j];
                }
            }
        }

        List<String> list = new ArrayList<>();
        for(String word:words1){
            if(isSubset(word,compare)){
                list.add(word);
            }
        }

        return list;


    }

    private boolean isSubset(String word,int[] count){
        int[] chs = new int[26];
        for(char c:word.toCharArray()){
            chs[c-'a']++;
        }

        for(int i=0;i<26;i++){
            if(count[i]>chs[i]){
                return false;
            }
        }
        return true;
    }


    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        if(power<tokens[0]){
            return 0;
        }

        int n = tokens.length;
        int i=0;
        int j=n-1;
        int score = 0;
        int max = 0;
        while(i<=j){
            if(power>=tokens[i]){
                power-=tokens[i];
                score++;
                i++;
            }else{
                if(score>0){
                    power+=tokens[j];
                    j--;
                    score--;
                }else{
                    break;
                }
            }

            max = Math.max(score,max);
        }

        return max;
    }


    public String toHex(int num) {
        if(num==0){
            return "0";
        }
        Map<Integer,Character> map = new HashMap<>();
        map.put(10,'a');
        map.put(11,'b');
        map.put(12,'c');
        map.put(13,'d');
        map.put(14,'e');
        map.put(15,'f');
        boolean transfer = num>=0 ? false:true;
        num = Math.abs(num);
        StringBuilder res = new StringBuilder();
        while(num>0){
            int current = num%16;

                if(current>=10){
                    res.insert(0,map.get(current));
                }else{
                    res.insert(0,current);
                }

            num = num/16;
        }

        if (transfer){
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<res.length();i++){
                char cur = res.charAt(i);
                if(Character.isDigit(cur)){
                    if(cur!='0'){
                        if(cur<'6'){
                            cur = map.get(15-(cur-'0'));
                        }else {
                            cur = (char) ('0'+15-(cur-'0'));
                        }
                    }
                    sb.append(cur);
                }else{
                    int number = 'f'-cur;
                    sb.append(number);
                }
            }
            int count = 8-res.length();
            while (count>0){
                sb.insert(0,'f');
                count--;
            }
            res = sb;
        }

        return res.toString();
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        CBTInserter test = new CBTInserter(root);


        String[] str1 =new String[]{"amazon","apple","facebook","google","leetcode"};
        String[] str2 =new String[]{"e","o"};
        test.toHex(-100000);
    }
}
