package companyOA.imc;

import java.util.*;

public class OA {
    public static List<Integer> getResult(List<Integer> arrival,List<Integer> street){
        //0 main street, 1 avenue
        PriorityQueue<int[]> avenue = new PriorityQueue<>((a,b)->(a[0]-b[0]));
        PriorityQueue<int[]> main = new PriorityQueue<>((a,b)->(a[0]-b[0]));
        for(int i=0;i<arrival.size();i++){
            if(street.get(i)==0){
                avenue.add(new int[]{arrival.get(i),i});
            }else{
                main.add(new int[]{arrival.get(i),i});
            }
        }

        int time = 0;
        int previous =-1;
        Map<Integer,Integer> map = new HashMap<>();
        while (!avenue.isEmpty()&&!main.isEmpty()){
            int[] a = avenue.peek();
            int[] b = main.peek();
            time++;
            if(a[0]<b[0]){
                previous = 0;
                map.put(a[1],time);
                avenue.poll();
            }else if(a[0]>b[0]){
                previous = 1;
                map.put(b[1],time);
                main.poll();
            }else{
                if(previous<=0){
                    previous = 0;
                    map.put(a[1],time);
                    avenue.poll();
                }else{
                    previous = 1;
                    map.put(b[1],time);
                    main.poll();
                }
            }

        }
        while (!avenue.isEmpty()){
            time++;
            int[] a = avenue.poll();
            map.put(a[1],time);
        }

        while (!main.isEmpty()){
            time++;
            int[] b = main.poll();
            map.put(b[1],time);
        }

        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<arrival.size();i++){
            ans.add(map.get(i));
        }

        return ans;
    }

    long solution(String[] queryType, int[][] query) {
        long ans = 0;
        int addKey = 0;
        int addValue = 0;
        int n = queryType.length;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            String operation = queryType[i];
            int[] oneQuery = query[i];
            if(operation.equals("insert")){
                int key = oneQuery[0]-addKey;
                int value = oneQuery[1]-addValue;
                map.put(key, value);
            }else if(operation.equals("get")){
                int key = oneQuery[0]+addKey;
                int value = map.getOrDefault(key, 0);
                if(map.containsKey(key)){
                    value+=addValue;
                }
                ans+=value;
            }else if(operation.equals("addToValue")){
                addValue+=oneQuery[0];
            }else if(operation.equals("addToKey")){
                addKey+=oneQuery[0];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        OA test = new OA();
        test.solution(new String[]{"insert",
                "insert",
                "addToValue",
                "addToKey",
                "get"},new int[][]{{1,2},
                {2,3},
                {2},
                {1},
                {3}});
    }
}
