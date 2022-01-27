package unlock;

import java.util.*;

public class Context {
    int ans=0;
    public int wordCount(String[] startWords, String[] targetWords) {
        Arrays.sort(startWords,(a, b)->(a.length()-b.length()));
        Map<Integer,List<Integer>> map = new HashMap<>();
        int m = startWords.length;
        int n = targetWords.length;
        int[][] words = new int[m][26];

        int res =0;
        for(int i=0;i<m;i++){
            for(char c:startWords[i].toCharArray()){
                words[i][c-'a']++;
            }
            map.putIfAbsent(startWords[i].length(),new ArrayList<>());
            map.get(startWords[i].length()).add(i);
        }




        dfs(new int[m],words,map,targetWords,0,0);


        return ans;

    }

    public void dfs(int[] visited,int[][] words , Map<Integer,List<Integer>> map, String[] targetWords, int start, int count){
        if(count==targetWords.length||start==targetWords.length){
            ans = Math.max(ans,count);
            return;
        }

        for(int i=start;i<targetWords.length;i++){
            int[] target = new int[26];
            for(char c:targetWords[i].toCharArray()){
                target[c-'a']++;
            }
            List<Integer> list = map.getOrDefault(targetWords[i].length(),new ArrayList<>());
            List<Integer> list1 = map.getOrDefault(targetWords[i].length()-1,new ArrayList<>());
            list.addAll(list1);
            for(int j=0;j<list.size();j++){
                int index = list.get(j);
                if(visited[index]==0&&canGet(target,words[list.get(j)])){
                    visited[index]=1;
                    dfs(visited,words,map,targetWords,i+1,count+1);
                    visited[index] =0;
                }

            }
        }


    }


    public boolean canGet(int[] target,int[] word){
        int diff=0;
        for(int i=0;i<26;i++){
            if(target[i]==word[i]){
                continue;
            }
            if(target[i]>word[i]){
                diff+=target[i]-word[i];
            }else{
                return false;
            }
            if(diff>1){
                return false;
            }
        }
        return diff<=1;
    }
}
