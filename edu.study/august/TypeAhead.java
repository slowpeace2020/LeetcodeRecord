package august;

import java.util.*;

public class TypeAhead {
    class Trie{
        Trie[] next;
        List<String> words;
        Trie(){
            words = new ArrayList<>();
            next = new Trie[26];
        }
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        Trie root = new Trie();
        for(String product:products){
            Trie cur = root;
            for(char ch:product.toCharArray()){
                int index = ch-'a';
                if(cur.next[index]==null){
                    cur.next[index] = new Trie();
                }

                cur = cur.next[index];
                if(cur.words.size()<3){
                    cur.words.add(product);
                }
            }
        }

        List<List<String>> res = new ArrayList<>();
        Trie cur = root;

        for(int i=0;i<searchWord.length();i++){
            cur = cur.next[searchWord.charAt(i)-'a'];
            if(cur==null){
                for(int j=i;j<searchWord.length();j++){
                    res.add(Collections.EMPTY_LIST);
                }
                break;
            }
            res.add(cur.words);
        }

        return res;
    }

    public int subarraysDivByK(int[] nums, int k) {
        int prefixSum = 0;
        int res = 0;
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        for(int num:nums){
            prefixSum = (prefixSum+num%k+k)%k;
            res+=map.getOrDefault(prefixSum,0);
            map.put(prefixSum,map.getOrDefault(prefixSum,0)+1);
        }

        return res;
    }
}

