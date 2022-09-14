package septemper;

import java.util.*;

public class Thousand {
    public int longestPath(int[] parent, String s) {
        int step = 1;

        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int i=0;i<parent.length;i++){
            int p = parent[i];
            List<Integer> child = map.getOrDefault(p,new ArrayList<>());
            if(p!=-1||s.charAt(p)!=s.charAt(i)){
                child.add(i);
                map.put(p,child);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[parent.length];
        queue.add(0);
        visited[0] = true;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                int cur = queue.poll();
                List<Integer> child = map.getOrDefault(cur,new ArrayList<>());
                for(int next:child){
                    if(!visited[next]){
                        queue.offer(next);
                        visited[next] = true;
                    }
                }

            }
            step++;
        }


        return step;
    }

    public int countHighestScoreNodes(int[] parents) {

        int n = parents.length;
        int[][] memory = new int[n][2];
        for(int i=0;i<n;i++){
            Arrays.fill(memory[i],-1);
        }

        int count = 0;
        int product = 0;
        getChildNum(memory,parents,0);

        for(int i=0;i<n;i++){
            int left = memory[i][0];
            int right = memory[i][1];
            int p = n-1-left-right;
            int cur = Math.max(left,1)*Math.max(right,1)*Math.max(p,1);
            if(cur>product){
                count = 1;
                product = cur;
            }else if(cur==product){
                count++;
            }
        }

        return count;
    }


    public int getChildNum(int[][] memory,int[] parents, int cur){
        if(memory[cur][0]!=-1){
            return memory[cur][0]+Math.max(memory[cur][1],0);
        }

        int left = 0;
        int right = 0;
        for(int i=0;i<parents.length;i++){
            if(parents[i]==cur){
                if(left==0){
                    left = 1+getChildNum(memory,parents,i);
                }else{
                    right = 1+getChildNum(memory,parents,i);
                }

            }
        }

        memory[cur][0] = left;
        memory[cur][1] = right;

        return left+right;
    }

    public static void main(String[] args) {
        Thousand test = new Thousand();
        test.countHighestScoreNodes(new int[]{-1,2,0});
    }
}
