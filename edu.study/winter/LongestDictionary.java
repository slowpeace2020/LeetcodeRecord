package winter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class LongestDictionary {
    class Trie{
        boolean isWord;
        Trie[] children;
        public Trie(){
            children = new Trie[26];
        }
    }
    public String longestWord(String[] words) {
        Trie root = new Trie();
        for(String word:words){
            Trie cur = root;
            for(char c:word.toCharArray()){
                if(cur.children[c-'a']==null){
                    cur.children[c-'a'] = new Trie();
                }
                cur = cur.children[c-'a'];
            }
            cur.isWord = true;
        }

        String res = null;
        for(String word:words){
            boolean find = searchInTrie(root,word.substring(0,word.length()-1));
            if(find){
                if(res==null){
                    res = word;
                }else{
                    if(word.length()>res.length()){
                        res = word;
                    }else if(word.length()==res.length()&&word.compareTo(res)<0){
                        res = word;
                    }
                }
            }
        }

        return res;
    }


    private boolean searchInTrie(Trie root,String word){
        Trie cur = root;
        for(char c:word.toCharArray()){
            cur = cur.children[c-'a'];
        }

        return cur.isWord;
    }


    public static void main(String[] args) {
        LongestDictionary dict = new LongestDictionary();
        dict.longestWord(new String[]{"a","banana","app","appl","ap","apply","apple"});

    }
}
