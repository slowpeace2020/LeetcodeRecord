package august;

import java.util.*;

public class Encrypter {
    Map<Character,String> map;
    Map<String, List<Character>> strToChar;
    Trie root;
    public Encrypter(char[] keys, String[] values, String[] dictionary) {
        map = new HashMap<>();
        strToChar = new HashMap<>();
        root = new Trie();
        buildTrie(dictionary);
        for(int i=0;i<keys.length;i++){
            map.put(keys[i],values[i]);
            List<Character> characters = strToChar.getOrDefault(values[i],new ArrayList<>());
            characters.add(keys[i]);
            strToChar.put(values[i],characters);
        }
    }

    public String encrypt(String word1) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<word1.length();i++){
            if(map.containsKey(word1.charAt(i))){
                sb.append(map.get(word1.charAt(i)));
            }
        }

        return sb.toString();
    }

    public int decrypt(String word2) {
        return dfs(word2,root,0);
    }

    private int dfs(String word,Trie cur, int start) {
        if(start==word.length()){
            return cur.isWord?1:0;
        }

        int ans = 0;
        return 0;
    }

    private void buildTrie(String[] dictionary){
        for(String word:dictionary){
            Trie cur = root;
            for(int i=0;i<word.length();i++){
                if(cur.child[word.charAt(i)-'a']==null){
                    cur.child[word.charAt(i)-'a'] = new Trie();
                }
                cur = cur.child[word.charAt(i)-'a'];
            }
            cur.isWord = true;
        }
    }

    class Trie{
        Trie[] child;
        boolean isWord;
        public Trie() {
            child = new Trie[26];
        }
    }
}
