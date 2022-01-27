package winter;

import java.util.ArrayList;
import java.util.List;

public class WordFilter {
    class PrefixTrie{
        List<Integer> list;
        PrefixTrie[] children;
        public PrefixTrie(){
            children =  new PrefixTrie[26];
            list = new ArrayList<>();
        }
    }

    class SuffixTrie{
        List<Integer> list;
        SuffixTrie[] children;
        public SuffixTrie(){
            children =  new SuffixTrie[26];
            list = new ArrayList<>();
        }
    }

    PrefixTrie prefixRoot;
    SuffixTrie suffixRoot;

    public WordFilter(String[] words) {
        prefixRoot = new PrefixTrie();
        suffixRoot = new SuffixTrie();
        for(int i=0;i<words.length;i++){
            String word = words[i];
            PrefixTrie cur = prefixRoot;
            for(char c:word.toCharArray()){
                if(cur.children[c-'a']==null){
                    cur.children[c-'a'] =new PrefixTrie();
                }
                cur.children[c-'a'].list.add(i);
                cur = cur.children[c-'a'];
            }

            SuffixTrie  suffix = suffixRoot;
            for(int j=word.length()-1;j>=0;j--){
                if(suffix.children[word.charAt(j)-'a']==null){
                    suffix.children[word.charAt(j)-'a'] = new SuffixTrie();
                }
                suffix.children[word.charAt(j)-'a'].list.add(i);
                suffix = suffix.children[word.charAt(j)-'a'];
            }
        }
    }



    public int f(String prefix, String suffix) {
        PrefixTrie cur = prefixRoot;
        for(char c:prefix.toCharArray()){
            if(cur.children[c-'a']==null){
                return -1;
            }
            cur = cur.children[c-'a'];
        }

        List<Integer> preList = cur.list;

        SuffixTrie  suf = suffixRoot;
        for(int j=suffix.length()-1;j>=0;j--){
            if(suf.children[suffix.charAt(j)-'a']==null){
                return -1;
            }
            suf = suf.children[suffix.charAt(j)-'a'];
        }

        List<Integer> sufList = suf.list;
        for(int i=preList.size()-1;i>=0;i--){
            if(sufList.contains(preList.get(i))){
                return preList.get(i);
            }
        }

            return -1;
    }

    public String boldWords(String[] words, String S) {
        int len = S.length();
        boolean[] bold = new boolean[len];
        for(String word:words){
            int wordLen = word.length();
            int beginIndex = 0;
            while (beginIndex>=0){
                beginIndex = S.indexOf(word,beginIndex);
                if(beginIndex>0){
                    for(int i=0;i<wordLen;i++){
                        bold[beginIndex+i]= true;
                    }
                    beginIndex++;
                }
            }
        }

        StringBuffer sb = new StringBuffer(S);
        if(bold[len-1]){
            sb.append("</b>");
        }
        for(int i = len-1;i>0;i++){
            if(bold[i]&&!bold[i-1]){
                sb.insert(i,"<b>");
            }else if(!bold[i]&&bold[i-1]){
                sb.insert(i,"</b>");
            }
        }

        if(bold[0]){
            sb.insert(0,"<br>");
        }

        return sb.toString();
    }

    class Trie {
        Trie[] children;

        public Trie() {
            this.children = new Trie[26];
        }
    }

}
