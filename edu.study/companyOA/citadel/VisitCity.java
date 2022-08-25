package companyOA.citadel;

import java.util.*;

public class VisitCity {

    public static List<Integer> minimumCost(int[] red,int[] blue,int blueCost){
        int n = red.length;
        int[] res = new int[n+1];
        Arrays.fill(res,-1);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0,0});
        while (!queue.isEmpty()){
            int[] cur = queue.poll();
            if(res[cur[0]]==-1||res[cur[0]]>cur[2]){
                res[cur[0]] = cur[2];
            }
            if(cur[0]<n) {
                if (cur[1] == 0) {
                    queue.add(new int[]{cur[0] + 1, 0, cur[2] + red[cur[0]]});
                    queue.add(new int[]{cur[0] + 1, 1, blueCost + cur[2] + blue[cur[0]]});
                } else {
                    queue.add(new int[]{cur[0] + 1, 0, cur[2] + red[cur[0]]});
                    queue.add(new int[]{cur[0] + 1, 1, cur[2] + blue[cur[0]]});
                }
            }


        }

        return null;
    }

    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        int n = groupSizes.length;
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int i=0;i<n;i++){
            int size = groupSizes[i];
            List<Integer> list = map.getOrDefault(size,new ArrayList<>());
            list.add(i);
            if(list.size()==size-1){
                res.add(list);
                map.remove(size);
            }else{
                map.put(size,list);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        minimumCost(new int[]{2,3,4},new int[]{3,1,1},2);
    }
}
